package org.firstinspires.ftc.teamcode.opmode.auton.front;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.stuyfission.fissionlib.command.Command;
import com.stuyfission.fissionlib.command.CommandSequence;
import com.stuyfission.fissionlib.command.AutoCommandMachine;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.hardware.mechanisms.Arm;
import org.firstinspires.ftc.teamcode.hardware.mechanisms.Claw;
import org.firstinspires.ftc.teamcode.hardware.mechanisms.Intake;
import org.firstinspires.ftc.teamcode.hardware.mechanisms.Slides2;
import org.firstinspires.ftc.teamcode.hardware.mechanisms.Webcam;
import org.firstinspires.ftc.teamcode.hardware.mechanisms.Wrist;
import org.firstinspires.ftc.teamcode.hardware.mechanisms.Webcam.Position;
import org.firstinspires.ftc.teamcode.opmode.auton.util.Color;

@Config
public class FrontAuto extends LinearOpMode {

    private boolean reflect;
    private Color color;
    private FrontConstants constants;
    private Position pos = Position.CENTER;

    private Arm arm;
    private SampleMecanumDrive drive;
    private Claw claw;
    private Intake intake;
    private Slides2 slides;
    private Webcam webcam;
    private Wrist wrist;

    private boolean busy = false;
    private int cycle = 0;

    public static double ARM_DELAY = 0.1;
    public static double SCORE_DELAY = 0.5;
    public static double SLIDES_DELAY = 0.5;
    public static double TRUSS_DELAY = 6;
    public static double PLATFORM_DELAY = 1.3;

    private TrajectorySequence[] spikeMarkTraj = new TrajectorySequence[3];
    private TrajectorySequence[] stackTraj = new TrajectorySequence[3];
    private TrajectorySequence[] trussFirstTraj = new TrajectorySequence[3];
    private TrajectorySequence[][] trussBackTraj = new TrajectorySequence[2][3];
    private TrajectorySequence[][] trussTraj = new TrajectorySequence[2][3];
    private TrajectorySequence[] parkTraj = new TrajectorySequence[3];

    private Command incrementCycle = () -> cycle++;
    private Command releaseLeftCommand = () -> {
        claw.leftOpen();
    };
    private Command releaseRightCommand = () -> {
        claw.rightOpen();
    };
    private Command slidesCommandFirst = () -> {
        slides.autoPos();
    };
    private Command slidesCommand = () -> {
        slides.setTarget(300);
    };
    private Command busyFalse = () -> busy = false;
    private Command busyTrue = () -> busy = true;
    private Command slidesSecondCommand = () -> slides.goToPos(1);
    private Command armIntakeCommand = () -> {
        arm.intakePos();
        wrist.intakePos();
    };
    private Command sensePixels = () -> {
        long start = System.nanoTime();
        boolean hadThird = false;
        while (System.nanoTime() - start < 1000000000) {
            if (intake.numPixels() == 2 && intake.isThirdPixel()) {
                intake.autoOuttake();
                hadThird = true;
            } else if (intake.numPixels() == 2 && hadThird) {
                intake.intake();
            }
            telemetry.addData("num pixels", intake.numPixels());
            telemetry.addData("three pixels", intake.isThirdPixel());
            telemetry.update();
        }
        telemetry.clearAll();
    };
    private Command intakeStartCommand = () -> intake.intake();
    private Command intakeStopCommand = () -> {
        intake.stop();
        intake.down();
    };
    private Command armAutoCommand = () -> {
        arm.autoPos();
        wrist.autoPos(reflectPos(pos));
    };
    private Command outtake = () -> intake.autoOuttake();
    private Command intakeUpFirst = () -> intake.upAuto(1);
    private Command intakeUpSecond = () -> intake.upAuto(2 * cycle + 3);
    private Command pixelPlatformUp = () -> {
        if (intake.numPixels() == 2) {
            intake.pixelUp();
        } else {
            intake.outtake();
        }
    };
    private Command pixelPlatformDown = () -> intake.pixelDown();
    private Command grabCommand = () -> claw.close();
    private Command armCommand = () -> arm.scorePos();
    private Command wristCommand = () -> wrist.autoPos();
    private Command wristIntake = () -> wrist.intakePos();
    private Command retractFirstCommand = () -> {
        claw.leftOpen();
        claw.rightOpen();
        wrist.intakePos();
    };
    private Command retractSecondCommand = () -> {
        arm.intakePos();
        slides.intakePos();
    };

    private Command spikeMarkCommand = () -> drive.followTrajectorySequenceAsync(spikeMarkTraj[pos.index]);
    private Command stackCommand = () -> drive.followTrajectorySequenceAsync(stackTraj[pos.index]);
    private Command trussFirstCommand = () -> drive.followTrajectorySequenceAsync(trussFirstTraj[pos.index]);
    private Command trussBackCommand = () -> drive.followTrajectorySequenceAsync(trussBackTraj[cycle][pos.index]);
    private Command trussCommand = () -> drive.followTrajectorySequenceAsync(trussTraj[cycle][pos.index]);
    private Command parkCommand = () -> drive.followTrajectorySequenceAsync(parkTraj[pos.index]);

    private CommandSequence spikeMarkSequence = new CommandSequence()
            .addCommand(spikeMarkCommand)
            .addCommand(armAutoCommand)
            .build();
    private CommandSequence stackSequence = new CommandSequence()
            .addCommand(stackCommand)
            .addCommand(releaseLeftCommand)
            .addCommand(releaseRightCommand)
            .addWaitCommand(0.2)
            .addCommand(armIntakeCommand)
            .addCommand(intakeUpFirst)
            .build();
    private CommandSequence trussFirstSequence = new CommandSequence()
            .addCommand(trussFirstCommand)
            .addCommand(intakeStartCommand)
            .addCommand(sensePixels)
            .addCommand(pixelPlatformUp)
            .addWaitCommand(PLATFORM_DELAY)
            .addCommand(grabCommand)
            .addCommand(releaseLeftCommand)
            .addWaitCommand(0.6)
            .addCommand(grabCommand)
            .addWaitCommand(0.1)
            .addCommand(pixelPlatformDown)
            .addWaitCommand(0.1)
            .addCommand(slidesCommandFirst)
            .addWaitCommand(0.1)
            .addCommand(armCommand)
            .addWaitCommand(ARM_DELAY)
            .addCommand(wristCommand)
            .build();
    private CommandSequence trussBackSequence = new CommandSequence()
            .addCommand(trussBackCommand)
            .addCommand(releaseRightCommand)
            .addCommand(releaseLeftCommand)
            .addWaitCommand(0.6)
            .addCommand(retractFirstCommand)
            .addWaitCommand(0.7)
            .addCommand(retractSecondCommand)
            .addCommand(intakeUpSecond)
            .addCommand(intakeStartCommand)
            .build();
    private CommandSequence trussSequence = new CommandSequence()
            .addCommand(trussCommand)
            .addCommand(incrementCycle)
            .addCommand(sensePixels)
            .addCommand(pixelPlatformUp)
            .addWaitCommand(PLATFORM_DELAY)
            .addCommand(grabCommand)
            .addCommand(releaseLeftCommand)
            .addWaitCommand(0.6)
            .addCommand(grabCommand)
            .addWaitCommand(0.1)
            .addCommand(pixelPlatformDown)
            .addWaitCommand(0.1)
            .addCommand(slidesCommand)
            .addWaitCommand(0.1)
            .addCommand(armCommand)
            .addWaitCommand(ARM_DELAY)
            .addCommand(wristCommand)
            .build();
    private CommandSequence parkSequence = new CommandSequence()
            .addCommand(releaseRightCommand)
            .addCommand(releaseLeftCommand)
            .addCommand(parkCommand)
            .addWaitCommand(0.3)
            .addCommand(retractFirstCommand)
            .addWaitCommand(0.4)
            .addCommand(retractSecondCommand)
            .build();

    private AutoCommandMachine commandMachine = new AutoCommandMachine()
            .addCommandSequence(spikeMarkSequence)
            .addCommandSequence(stackSequence)
            .addCommandSequence(trussFirstSequence)
            .addCommandSequence(trussBackSequence)
            .addCommandSequence(trussSequence)
            .addCommandSequence(trussBackSequence)
            .addCommandSequence(trussSequence)
            .addCommandSequence(parkSequence)
            .addCommandSequence(parkSequence)
            .build();

    public FrontAuto(Color color, FrontConstants constants) {
        this.color = color;
        this.constants = constants;
    }

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        reflect = color == Color.RED;
        arm = new Arm(this);
        drive = new SampleMecanumDrive(hardwareMap);
        claw = new Claw(this);
        intake = new Intake(this);
        slides = new Slides2(this);
        webcam = new Webcam(this, color);
        wrist = new Wrist(this);

        arm.init(hardwareMap);
        claw.init(hardwareMap);
        intake.init(hardwareMap);
        slides.init(hardwareMap);
        webcam.init(hardwareMap);
        wrist.init(hardwareMap);

        claw.close();

        for (int i = 0; i < 3; i++) {
            spikeMarkTraj[i] = drive
                    .trajectorySequenceBuilder(reflectX(FrontConstantsDash.START_POSE))
                    .lineToLinearHeading(reflectX(new Pose2d(constants.SPIKE.getV(i),
                            constants.SPIKE.getH(i))))
                    .build();
            stackTraj[i] = drive
                    .trajectorySequenceBuilder(spikeMarkTraj[i].end())
                    .setTangent(FrontConstantsDash.SPIKE_TANGENT)
                    .splineToLinearHeading(
                            reflectX(new Pose2d(constants.STACK_1.getV(i),
                                    constants.STACK_1.getH(i))),
                            reflectX(constants.STACK_1.getH(i)))
                    .build();
            trussFirstTraj[i] = drive
                    .trajectorySequenceBuilder(stackTraj[i].end())
                    .setReversed(true)
                    .splineTo(reflectX(constants.FRONT_TRUSS_1.getV(i)),
                            reflectX(constants.FRONT_TRUSS_1.getH(i)))
                    .splineTo(reflectX(constants.END_TRUSS_1.getV(i)),
                            reflectX(constants.END_TRUSS_1.getH(i)))
                    .splineToConstantHeading(reflectX(constants.TAG_1.getV(i)),
                            reflectX(constants.TAG_1.getH(i)))
                    .build();
            trussBackTraj[0][i] = drive
                    .trajectorySequenceBuilder(trussFirstTraj[i].end())
                    .setReversed(false)
                    .splineToConstantHeading(reflectX(constants.END_TRUSS_BACK_1.getV(i)),
                            reflectX(constants.END_TRUSS_BACK_1.getH(i)))
                    .splineToConstantHeading(reflectX(constants.FRONT_TRUSS_BACK_1.getV(i)),
                            reflectX(constants.FRONT_TRUSS_BACK_1.getH(i)))
                    .splineTo(reflectX(constants.STACK_2.getV(i)),
                            reflectX(constants.STACK_2.getH(i)))
                    .build();
            trussTraj[0][i] = drive
                    .trajectorySequenceBuilder(trussBackTraj[0][i].end())
                    .setReversed(true)
                    .splineTo(reflectX(constants.FRONT_TRUSS_2.getV(i)),
                            reflectX(constants.FRONT_TRUSS_2.getH(i)))
                    .splineTo(reflectX(constants.END_TRUSS_2.getV(i)),
                            reflectX(constants.END_TRUSS_2.getH(i)))
                    .splineToConstantHeading(reflectX(constants.TAG_2.getV(i)),
                            reflectX(constants.TAG_2.getH(i)))
                    .build();
            trussBackTraj[1][i] = drive
                    .trajectorySequenceBuilder(trussTraj[0][i].end())
                    .setReversed(false)
                    .splineToConstantHeading(reflectX(constants.END_TRUSS_BACK_2.getV(i)),
                            reflectX(constants.END_TRUSS_BACK_2.getH(i)))
                    .splineToConstantHeading(reflectX(constants.FRONT_TRUSS_BACK_2.getV(i)),
                            reflectX(constants.FRONT_TRUSS_BACK_2.getH(i)))
                    .splineTo(reflectX(constants.STACK_3.getV(i)),
                            reflectX(constants.STACK_3.getH(i)))
                    .build();
            trussTraj[1][i] = drive
                    .trajectorySequenceBuilder(trussBackTraj[1][i].end())
                    .setReversed(true)
                    .splineTo(reflectX(constants.FRONT_TRUSS_3.getV(i)),
                            reflectX(constants.FRONT_TRUSS_3.getH(i)))
                    .splineTo(reflectX(constants.END_TRUSS_3.getV(i)),
                            reflectX(constants.END_TRUSS_3.getH(i)))
                    .splineToConstantHeading(reflectX(constants.TAG_3.getV(i)),
                            reflectX(constants.TAG_3.getH(i)))
                    .build();
            parkTraj[i] = drive
                    .trajectorySequenceBuilder(trussTraj[1][i].end())
                    .setReversed(false)
                    .splineToConstantHeading(reflectX(constants.PARK.getV(i)),
                            reflectX(constants.PARK.getH(i)))
                    .build();
        }

        while (opModeInInit()) {
            pos = webcam.getPosition();
            telemetry.addData("Position: ", pos);
            telemetry.update();
        }

        drive.setPoseEstimate(reflectX(FrontConstantsDash.START_POSE));

        waitForStart();

        webcam.stopStreaming();
        webcam.aprilTagInit();

        while (opModeIsActive() && !isStopRequested() && !commandMachine.hasCompleted()) {
            drive.update();
            slides.update();
            commandMachine.run(drive.isBusy() || busy);
        }
    }

    public Pose2d reflectX(Pose2d pose) {
        if (reflect) {
            return new Pose2d(pose.getX(), pose.getY() * -1, -pose.getHeading());
        }
        return pose;
    }

    public Vector2d reflectX(Vector2d vector) {
        if (reflect) {
            return new Vector2d(vector.getX(), vector.getY() * -1);
        }
        return vector;
    }

    public double reflectX(double theta) {
        if (reflect) {
            return -theta;
        }
        return theta;
    }

    public int reflectPos(Position pos) {
        if (!reflect) {
            return 2 - pos.index;
        }
        return pos.index;
    }
}

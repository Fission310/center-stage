package org.firstinspires.ftc.teamcode.opmode.auton.backdrop;

import com.acmerobotics.dashboard.config.Config;
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
public class BackDropAuto extends LinearOpMode {

    private boolean reflect;
    private Color color;
    private BackDropConstants constants;
    private Position pos = Position.CENTER;

    private Arm arm;
    private Claw claw;
    private Intake intake;
    private SampleMecanumDrive drive;
    private Slides2 slides;
    private Webcam webcam;
    private Wrist wrist;

    private int cycle = 0;
    private boolean busy = false;

    public static double ARM_DELAY = 0.1;
    public static double SCORE_DELAY = 0.5;
    public static double SLIDES_DELAY = 0.5;
    public static double PLATFORM_DELAY = 1.3;

    private TrajectorySequence[] spikeMarkTraj = new TrajectorySequence[3];
    private TrajectorySequence[] backDropTraj = new TrajectorySequence[3];
    private TrajectorySequence[][] trussBackTraj = new TrajectorySequence[2][3];
    private TrajectorySequence[][] trussTraj = new TrajectorySequence[2][3];
    private TrajectorySequence[] parkTraj = new TrajectorySequence[3];

    private Command busyFalse = () -> busy = false;
    private Command busyTrue = () -> busy = true;
    private Command incCycle = () -> cycle++;
    private Command releaseCommand = () -> {
        claw.leftOpen();
        claw.rightOpen();
    };
    private Command releaseLeftCommand = () -> {
        claw.leftOpen();
    };
    private Command releaseRightCommand = () -> {
        claw.rightOpen();
    };
    private Command sensePixels = () -> {
        long start = System.nanoTime();
        while (intake.numPixels() < 2 && System.nanoTime() - start < 3000000) {
        }
    };
    private Command intakeStartCommand = () -> intake.intake();
    private Command intakeStopCommand = () -> {
        intake.stop();
        intake.down();
    };
    private Command armAutoCommand = () -> {
        arm.autoPos();
        if (reflect) {
            wrist.autoPos(reflectPos(pos));
        } else {
            wrist.autoPos(pos.index % 2 + 1);
        }
    };
    private Command releaseLeft = () -> claw.leftOpen();
    private Command intakeCommand = () -> intake.up();
    private Command pixelPlatformUp = () -> intake.pixelUp();
    private Command pixelPlatformDown = () -> intake.pixelDown();
    private Command slidesCommand = () -> slides.setTarget(200);
    private Command slidesFirstCommand = () -> slides.autoPos();
    private Command armCommand = () -> arm.scorePos();
    private Command armIntakeCommand = () -> arm.intakePos();
    private Command wristCommand = () -> {
        if (!reflect) {
            wrist.autoPos(0);
        } else {
            wrist.scorePos();
        }
    };
    private Command grabCommand = () -> claw.close();
    private Command intakeUp = () -> intake.upAuto(cycle * 2 + 2);
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
    private Command backDropCommand = () -> drive.followTrajectorySequenceAsync(backDropTraj[pos.index]);
    private Command trussBackCommand = () -> drive.followTrajectorySequenceAsync(trussBackTraj[cycle][pos.index]);
    private Command trussCommand = () -> drive.followTrajectorySequenceAsync(trussTraj[cycle][pos.index]);
    private Command parkCommand = () -> drive.followTrajectorySequenceAsync(parkTraj[pos.index]);

    private CommandSequence spikeMarkSequence = new CommandSequence()
            .addCommand(spikeMarkCommand)
            .addCommand(armAutoCommand)
            .build();
    private CommandSequence backDropSequence = new CommandSequence()
            .addCommand(backDropCommand)
            .addCommand(releaseLeft)
            .addWaitCommand(0.2)
            .addCommand(slidesFirstCommand)
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
            .addCommand(intakeUp)
            .addCommand(intakeStartCommand)
            .build();
    private CommandSequence trussSequence = new CommandSequence()
            .addCommand(trussCommand)
            .addCommand(incCycle)
            .addCommand(intakeStartCommand)
            .addCommand(sensePixels)
            .addWaitCommand(0.7)
            .addCommand(pixelPlatformUp)
            .addWaitCommand(PLATFORM_DELAY)
            .addCommand(grabCommand)
            .addCommand(releaseLeftCommand)
            .addWaitCommand(0.6)
            .addCommand(grabCommand)
            .addWaitCommand(0.2)
            .addCommand(pixelPlatformDown)
            .addWaitCommand(0.1)
            .addCommand(slidesCommand)
            .addWaitCommand(0.1)
            .addCommand(armCommand)
            .addWaitCommand(ARM_DELAY)
            .addCommand(wristCommand)
            .build();
    private CommandSequence parkSequence = new CommandSequence()
            .addCommand(releaseCommand)
            .addWaitCommand(SCORE_DELAY)
            .addCommand(parkCommand)
            .addWaitCommand(4)
            .addCommand(retractFirstCommand)
            .addWaitCommand(0.4)
            .addCommand(retractSecondCommand)
            .addWaitCommand(1)
            .build();

    private AutoCommandMachine commandMachine = new AutoCommandMachine()
            .addCommandSequence(spikeMarkSequence)
            .addCommandSequence(backDropSequence)
            .addCommandSequence(trussBackSequence)
            .addCommandSequence(trussSequence)
            .addCommandSequence(trussBackSequence)
            .addCommandSequence(trussSequence)
            .addCommandSequence(parkSequence)
            .addCommandSequence(parkSequence)
            .build();

    public BackDropAuto(Color color, BackDropConstants constants) {
        this.color = color;
        this.constants = constants;
    }

    @Override
    public void runOpMode() throws InterruptedException {
        reflect = color == Color.RED;
        arm = new Arm(this);
        drive = new SampleMecanumDrive(hardwareMap);
        intake = new Intake(this);
        claw = new Claw(this);
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
                    .trajectorySequenceBuilder(reflectX(BackDropConstantsDash.START_POSE))
                    .lineToLinearHeading(new Pose2d(reflectX(constants.SPIKE.getV(i)),
                            reflectX(constants.SPIKE.getH(i))))
                    .build();
            backDropTraj[i] = drive
                    .trajectorySequenceBuilder(spikeMarkTraj[i].end())
                    .setReversed(true)
                    .lineToLinearHeading(new Pose2d(reflectX(constants.TAG_1.getV(i)),
                            reflectX(constants.TAG_1.getH(i))))
                    .build();
            trussBackTraj[0][i] = drive
                    .trajectorySequenceBuilder(backDropTraj[i].end())
                    .setReversed(false)
                    .splineToConstantHeading(reflectX(constants.END_TRUSS_BACK_1.getV(i)),
                            reflectX(constants.END_TRUSS_BACK_1.getH(i)))
                    .splineTo(reflectX(constants.FRONT_TRUSS_BACK_1.getV(i)),
                            reflectX(constants.FRONT_TRUSS_BACK_1.getH(i)))
                    .splineToConstantHeading(reflectX(constants.STACK_1.getV(i)),
                            reflectX(constants.STACK_1.getH(i)))
                    .build();
            trussTraj[0][i] = drive
                    .trajectorySequenceBuilder(trussBackTraj[0][i].end())
                    .setReversed(true)
                    .splineToConstantHeading(reflectX(constants.FRONT_TRUSS_1.getV(i)),
                            reflectX(constants.FRONT_TRUSS_1.getH(i)))
                    .splineTo(reflectX(constants.END_TRUSS_1.getV(i)),
                            reflectX(constants.END_TRUSS_1.getH(i)))
                    .splineToConstantHeading(reflectX(constants.TAG_2.getV(i)),
                            reflectX(constants.TAG_2.getH(i)))
                    .build();
            trussBackTraj[1][i] = drive
                    .trajectorySequenceBuilder(trussTraj[0][i].end())
                    .setReversed(false)
                    .splineToConstantHeading(reflectX(constants.END_TRUSS_BACK_2.getV(i)),
                            reflectX(constants.END_TRUSS_BACK_2.getH(i)))
                    .splineTo(reflectX(constants.FRONT_TRUSS_BACK_2.getV(i)),
                            reflectX(constants.FRONT_TRUSS_BACK_2.getH(i)))
                    .splineTo(reflectX(constants.STACK_2.getV(i)),
                            reflectX(constants.STACK_2.getH(i)))
                    .build();
            trussTraj[1][i] = drive
                    .trajectorySequenceBuilder(trussBackTraj[1][i].end())
                    .setReversed(true)
                    .splineTo(reflectX(constants.FRONT_TRUSS_2.getV(i)),
                            reflectX(constants.FRONT_TRUSS_2.getH(i)))
                    .splineTo(reflectX(constants.END_TRUSS_2.getV(i)),
                            reflectX(constants.END_TRUSS_2.getH(i)))
                    .splineToConstantHeading(reflectX(constants.TAG_3.getV(i)),
                            reflectX(constants.TAG_3.getH(i)))
                    .build();
            parkTraj[i] = drive
                    .trajectorySequenceBuilder(trussTraj[1][i].end())
                    .setReversed(false)
                    .splineToConstantHeading(reflectX(constants.PARK.getV(i)), reflectX(constants.PARK.getH(i)))
                    .build();
        }

        while (opModeInInit()) {
            pos = webcam.getPosition();
            telemetry.addData("Position: ", pos);
            telemetry.update();
        }

        drive.setPoseEstimate(reflectX(BackDropConstantsDash.START_POSE));

        waitForStart();

        webcam.stopStreaming();
        webcam.aprilTagInit();

        webcam.setDesiredTag(pos.index);

        while (opModeIsActive() && !isStopRequested() && !commandMachine.hasCompleted()) {
            drive.update();
            slides.update();
            commandMachine.run(drive.isBusy() || busy);

            if (commandMachine.getCurrentCommandIndex() > 1 && webcam.detectAprilTag(telemetry)) {
                webcam.relocalize(drive);
            }
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

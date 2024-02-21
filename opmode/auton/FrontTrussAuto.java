package org.firstinspires.ftc.teamcode.opmode.auton;

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
import org.firstinspires.ftc.teamcode.opmode.auton.AutoConstants.Color;

@Config
public class FrontTrussAuto extends LinearOpMode {

    private boolean reflect;
    private Color color;
    private Position pos = Position.CENTER;

    private Arm arm;
    private SampleMecanumDrive drive;
    private Claw claw;
    private Intake intake;
    private Slides2 slides;
    private Webcam webcam;
    private Wrist wrist;

    public static double ARM_DELAY = 0.1;
    public static double SCORE_DELAY = 0.5;
    public static double SLIDES_DELAY = 0.5;
    public static double TRUSS_DELAY = 6;
    public static double PLATFORM_DELAY = 1.4;

    private TrajectorySequence[] spikeMarkTraj = new TrajectorySequence[3];
    private TrajectorySequence[] stackTraj = new TrajectorySequence[3];
    private TrajectorySequence[] trussTraj = new TrajectorySequence[3];
    private TrajectorySequence[] tagBackTraj = new TrajectorySequence[3];
    private TrajectorySequence[] tagTraj = new TrajectorySequence[3];
    private TrajectorySequence[] backTrussTraj = new TrajectorySequence[3];
    private TrajectorySequence[] tagCenterTraj = new TrajectorySequence[3];
    private TrajectorySequence[] parkTraj = new TrajectorySequence[3];

    private Command releaseLeftCommand = () -> {
        claw.leftOpen();
    };
    private Command releaseRightCommand = () -> {
        claw.rightOpen();
    };
    private Command slidesCommand = () -> slides.goToPos(0);
    private Command armIntakeCommand = () -> {
        arm.intakePos();
        wrist.intakePos();
    };
    private Command armAutoCommand = () -> {
        arm.autoPos();
        wrist.autoPos(reflectPos(pos));
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
    private Command outtake = () -> intake.outtake();
    private Command intakeUp = () -> intake.upAuto();
    private Command pixelPlatformUp = () -> intake.pixelUp();
    private Command pixelPlatformDown = () -> intake.pixelDown();
    private Command grabCommand = () -> claw.close();
    private Command armCommand = () -> arm.scorePos();
    private Command wristCommand = () -> wrist.autoPos();
    private Command retractFirstCommand = () -> {
        claw.leftOpen();
        claw.rightOpen();
        wrist.intakePos();
    };
    private Command retractSecondCommand = () -> {
        arm.intakePos();
        slides.intakePos();
        claw.close();
    };

    private Command spikeMarkCommand = () -> drive.followTrajectorySequenceAsync(spikeMarkTraj[reflectPos(pos)]);
    private Command stackCommand = () -> drive.followTrajectorySequenceAsync(stackTraj[reflectPos(pos)]);
    private Command trussCommand = () -> drive.followTrajectorySequenceAsync(trussTraj[reflectPos(pos)]);
    private Command tagBackCommand = () -> drive.followTrajectorySequenceAsync(tagBackTraj[reflectPos(pos)]);
    private Command tagCommand = () -> drive.followTrajectorySequenceAsync(tagTraj[reflectPos(pos)]);
    private Command backTrussCommand = () -> drive.followTrajectorySequenceAsync(backTrussTraj[reflectPos(pos)]);
    private Command tagCenterCommand = () -> drive.followTrajectorySequenceAsync(tagCenterTraj[reflectPos(pos)]);
    private Command parkCommand = () -> drive.followTrajectorySequenceAsync(parkTraj[reflectPos(pos)]);

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
            .addCommand(intakeUp)
            .build();
    private CommandSequence trussSequence = new CommandSequence()
            .addCommand(intakeStartCommand)
            .addCommand(trussCommand)
            .addCommand(sensePixels)
            .addWaitCommand(0.5)
            .addCommand(intakeStopCommand)
            .addWaitCommand(0.4)
            .addCommand(pixelPlatformUp)
            .addWaitCommand(PLATFORM_DELAY)
            .addCommand(grabCommand)
            .addWaitCommand(2)
            .addCommand(pixelPlatformDown)
            .addWaitCommand(0.1)
            .addCommand(slidesCommand)
            .addWaitCommand(0.1)
            .addCommand(armCommand)
            .addWaitCommand(ARM_DELAY)
            .addCommand(wristCommand)
            .addWaitCommand(SCORE_DELAY)
            .addCommand(releaseRightCommand)
            .build();
    private CommandSequence tagBackSequence = new CommandSequence()
            .addCommand(tagBackCommand)
            .build();
    private CommandSequence tagSequence = new CommandSequence()
            .addCommand(tagCommand)
            .addWaitCommand(0.3)
            .addCommand(releaseLeftCommand)
            .build();
    private CommandSequence backTrussSequence = new CommandSequence()
            .addCommand(backTrussCommand)
            .addWaitCommand(0.2)
            .addCommand(retractFirstCommand)
            .addWaitCommand(0.4)
            .addCommand(retractSecondCommand)
            .build();
    private CommandSequence tagCenterSequence = new CommandSequence()
            .addCommand(intakeStartCommand)
            .addCommand(tagCenterCommand)
            .addCommand(trussCommand)
            .addCommand(sensePixels)
            .addWaitCommand(0.5)
            .addCommand(intakeStopCommand)
            .addWaitCommand(0.4)
            .addCommand(pixelPlatformUp)
            .addWaitCommand(PLATFORM_DELAY)
            .addCommand(grabCommand)
            .addWaitCommand(2)
            .addCommand(pixelPlatformDown)
            .addWaitCommand(0.1)
            .addCommand(slidesCommand)
            .addWaitCommand(0.1)
            .addCommand(armCommand)
            .addWaitCommand(ARM_DELAY)
            .addCommand(wristCommand)
            .addWaitCommand(SCORE_DELAY)
            .addCommand(releaseRightCommand)
            .addCommand(releaseLeftCommand)
            .build();
    private CommandSequence parkSequence = new CommandSequence()
            .addWaitCommand(2)
            .addCommand(parkCommand)
            .addWaitCommand(4)
            .addCommand(retractFirstCommand)
            .addWaitCommand(0.4)
            .addCommand(retractSecondCommand)
            .build();

    private AutoCommandMachine commandMachine = new AutoCommandMachine()
            .addCommandSequence(spikeMarkSequence)
            .addCommandSequence(stackSequence)
            .addCommandSequence(trussSequence)
            .addCommandSequence(tagBackSequence)
            .addCommandSequence(tagSequence)
            .addCommandSequence(backTrussSequence)
            .addCommandSequence(tagCenterSequence)
            .addCommandSequence(parkSequence)
            .addCommandSequence(parkSequence)
            .build();

    public FrontTrussAuto(Color color) {
        this.color = color;
    }

    @Override
    public void runOpMode() throws InterruptedException {
        AutoConstants.init();
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
                    .trajectorySequenceBuilder(reflectX(AutoConstants.FR_START_POSE))
                    .lineToLinearHeading(reflectX(new Pose2d(AutoConstants.FR_SPIKE_VECTORS[i],
                            AutoConstants.FR_SPIKE_HEADINGS[i])))
                    .build();
            stackTraj[i] = drive
                    .trajectorySequenceBuilder(spikeMarkTraj[i].end())
                    .setTangent(AutoConstants.FR_SPIKE_TANGENT)
                    .splineToLinearHeading(
                            reflectX(new Pose2d(AutoConstants.FR_TRUSS_STACK_VECTOR,
                                    AutoConstants.FR_STACK_HEADING)),
                            reflectX(AutoConstants.FR_STACK_HEADING))
                    .build();
            trussTraj[i] = drive
                    .trajectorySequenceBuilder(stackTraj[i].end())
                    .setReversed(true)
                    .splineTo(reflectX(AutoConstants.TRUSS_VECTOR),
                            reflectX(AutoConstants.TRUSS_HEADING))
                    .splineToConstantHeading(reflectX(AutoConstants.TAG_VECTORS[i]),
                            reflectX(AutoConstants.TAG_HEADINGS[i]))
                    .build();
            tagBackTraj[i] = drive
                    .trajectorySequenceBuilder(trussTraj[i].end())
                    .setReversed(false)
                    .splineToConstantHeading(reflectX(AutoConstants.FR_TAG_BACK_VECTOR),
                            reflectX(AutoConstants.FR_TAG_BACK_HEADING))
                    .build();
            tagTraj[i] = drive
                    .trajectorySequenceBuilder(tagBackTraj[i].end())
                    .setReversed(true)
                    .splineToConstantHeading(reflectX(AutoConstants.TAG_VECTORS[2 - i + i % 2]),
                            reflectX(AutoConstants.TAG_HEADINGS[2 - i + i % 2]))
                    .build();
            backTrussTraj[i] = drive
                    .trajectorySequenceBuilder(tagTraj[i].end())
                    .setReversed(false)
                    .splineToConstantHeading(reflectX(AutoConstants.TRUSS_VECTOR),
                            reflectX(AutoConstants.TRUSS_BACK_HEADING))
                    .splineTo(reflectX(AutoConstants.FR_TRUSS_STACK_VECTOR),
                            reflectX(AutoConstants.FR_STACK_HEADING))
                    .build();
            tagCenterTraj[i] = drive
                    .trajectorySequenceBuilder(backTrussTraj[i].end())
                    .setReversed(true)
                    .splineTo(reflectX(AutoConstants.TRUSS_VECTOR),
                            reflectX(AutoConstants.TRUSS_HEADING))
                    .splineToConstantHeading(reflectX(AutoConstants.TAG_VECTORS[1]),
                            reflectX(AutoConstants.TAG_HEADINGS[1]))
                    .build();
            parkTraj[i] = drive
                    .trajectorySequenceBuilder(trussTraj[i].end())
                    .setReversed(false)
                    .splineToConstantHeading(reflectX(AutoConstants.PARK_VECTOR),
                            reflectX(AutoConstants.PARK_HEADING))
                    .build();
        }

        while (opModeInInit()) {
            pos = webcam.getPosition();
            telemetry.addData("Position: ", pos);
            telemetry.update();
        }

        drive.setPoseEstimate(reflectX(AutoConstants.FR_START_POSE));

        waitForStart();

        webcam.stopStreaming();

        while (opModeIsActive() && !isStopRequested() && !commandMachine.hasCompleted()) {
            drive.update();
            slides.update();
            commandMachine.run(drive.isBusy());
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

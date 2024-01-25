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
public class FrontAuto extends LinearOpMode {

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

    public static double ARM_DELAY = 0.5;
    public static double INTAKE_DELAY = 0.5;
    public static double SCORE_DELAY = 0.5;
    public static double SLIDES_DELAY = 0.5;
    public static double PLATFORM_DELAY = 0.9;
    public static double GRAB_DELAY = 0.4;

    private TrajectorySequence[] spikeMarkTraj;
    private TrajectorySequence[] backTraj;
    private TrajectorySequence[] stackTraj;
    private TrajectorySequence[] trussTraj;
    private TrajectorySequence[] backDropTraj;
    private TrajectorySequence[] backTrussTraj;
    private TrajectorySequence[] parkTraj;

    private Command releaseCommand = () -> claw.leftOpen();
    private Command slidesCommand = () -> slides.goToPos(1);
    private Command intakeStartCommand = () -> {
        intake.up();
        intake.intake();
    };
    private Command intakeStopCommand = () -> {
        intake.stop();
        intake.down();
    };
    private Command pixelPlatformUp = () -> intake.pixelUp();
    private Command pixelPlatformDown = () -> intake.pixelDown();
    private Command grabCommand = () -> claw.close();
    private Command armCommand = () -> arm.scorePos();
    private Command wristCommand = () -> wrist.scorePos();
    private Command retractCommand = () -> {
        claw.leftOpen();
        claw.rightOpen();
        arm.intakePos();
        wrist.intakePos();
        slides.intakePos();
        claw.close();
    };

    private Command spikeMarkCommand = () -> drive.followTrajectorySequenceAsync(spikeMarkTraj[pos.index]);
    private Command backCommand = () -> drive.followTrajectorySequenceAsync(backTraj[pos.index]);
    private Command stackCommand = () -> drive.followTrajectorySequenceAsync(stackTraj[pos.index]);
    private Command trussCommand = () -> drive.followTrajectorySequenceAsync(trussTraj[pos.index]);
    private Command backDropCommand = () -> drive.followTrajectorySequenceAsync(backDropTraj[pos.index]);
    private Command backTrussCommand = () -> drive.followTrajectorySequenceAsync(backTrussTraj[pos.index]);
    private Command parkCommand = () -> drive.followTrajectorySequenceAsync(parkTraj[pos.index]);

    private CommandSequence pixelSequence = new CommandSequence()
            .addCommand(pixelPlatformUp)
            .addWaitCommand(PLATFORM_DELAY)
            .addCommand(grabCommand)
            .addWaitCommand(GRAB_DELAY)
            .addCommand(pixelPlatformDown)
            .build();
    private CommandSequence spikeMarkSequence = new CommandSequence()
            .addCommand(spikeMarkCommand)
            .build();
    private CommandSequence backSequence = new CommandSequence()
            .addCommand(backCommand)
            .build();
    private CommandSequence stackSequence = new CommandSequence()
            .addCommand(stackCommand)
            .addCommand(intakeStartCommand)
            .addWaitCommand(INTAKE_DELAY)
            .addCommand(intakeStopCommand)
            .build();
    private CommandSequence trussSequence = new CommandSequence()
            .addCommand(trussCommand)
            .build();
    private CommandSequence scoreSequence = new CommandSequence()
            .addCommand(backDropCommand)
            // .addWaitCommand(10)
            .addCommand(slidesCommand)
            .addWaitCommand(0.1)
            .addCommand(wristCommand)
            .addWaitCommand(ARM_DELAY)
            .addCommand(armCommand)
            .addWaitCommand(SLIDES_DELAY)
            .addCommand(releaseCommand)
            .addWaitCommand(SCORE_DELAY)
            .addCommand(retractCommand)
            .build();
    private CommandSequence backTrussSequence = new CommandSequence()
            .addCommand(backTrussCommand)
            .build();
    private CommandSequence parkSequence = new CommandSequence()
            .addCommand(parkCommand)
            .build();

    private AutoCommandMachine commandMachine = new AutoCommandMachine()
            .addCommandSequence(spikeMarkSequence)
            .addCommandSequence(backSequence)
            .addCommandSequence(stackSequence)
            .addCommandSequence(pixelSequence)
            .addCommandSequence(trussSequence)
            .addCommandSequence(scoreSequence)
            .addCommandSequence(backTrussSequence)
            .addCommandSequence(stackSequence)
            .addCommandSequence(pixelSequence)
            .addCommandSequence(trussSequence)
            .addCommandSequence(scoreSequence)
            .addCommandSequence(parkSequence)
            .build();

    public FrontAuto(Color color) {
        this.color = color;
    }

    @Override
    public void runOpMode() throws InterruptedException {
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

        for (int i = 0; i < 3; i++) {
            spikeMarkTraj[i] = drive
                    .trajectorySequenceBuilder(reflectX(AutoConstants.FR_START_POSE))
                    .splineTo(reflectX(AutoConstants.FR_SPIKE_VECTORS[pos.index]),
                            reflectX(AutoConstants.FR_SPIKE_HEADINGS[pos.index]))
                    .build();
            backTraj[i] = drive
                    .trajectorySequenceBuilder(spikeMarkTraj[i].end())
                    .setReversed(true)
                    .splineTo(reflectX(AutoConstants.FR_BACK_VECTOR),
                            reflectX(AutoConstants.FR_BACK_HEADING))
                    .build();
            stackTraj[i] = drive
                    .trajectorySequenceBuilder(backTraj[i].end())
                    .setReversed(false)
                    .splineTo(reflectX(AutoConstants.FR_STACK_VECTOR),
                            reflectX(AutoConstants.FR_STACK_HEADING))
                    .build();
            trussTraj[i] = drive
                    .trajectorySequenceBuilder(stackTraj[i].end())
                    .setReversed(true)
                    .splineTo(reflectX(AutoConstants.TRUSS_VECTOR),
                            reflectX(AutoConstants.TRUSS_HEADING))
                    .build();
            backDropTraj[i] = drive
                    .trajectorySequenceBuilder(trussTraj[i].end())
                    .splineTo(reflectX(AutoConstants.TAG_VECTORS[pos.index]),
                            reflectX(AutoConstants.TAG_HEADINGS[pos.index]))
                    .build();
            backTrussTraj[i] = drive
                    .trajectorySequenceBuilder(stackTraj[i].end())
                    .setReversed(true)
                    .splineTo(reflectX(AutoConstants.TRUSS_VECTOR),
                            reflectX(AutoConstants.TRUSS_HEADING))
                    .build();
            parkTraj[i] = drive
                    .trajectorySequenceBuilder(backDropTraj[i].end())
                    .strafeTo(reflectX(AutoConstants.PARK_VECTOR))
                    .build();
        }

        while (opModeIsActive() && isStopRequested()) {
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
}

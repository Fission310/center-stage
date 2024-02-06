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
public class BackDropAuto extends LinearOpMode {

    private boolean reflect;
    private Color color;
    private Position pos = Position.CENTER;

    private Arm arm;
    private Claw claw;
    private Intake intake;
    private SampleMecanumDrive drive;
    private Slides2 slides;
    private Webcam webcam;
    private Wrist wrist;

    public static double ARM_DELAY = 1;
    public static double SCORE_DELAY = 1;
    public static double SLIDES_DELAY = 1;

    private TrajectorySequence[] spikeMarkTraj = new TrajectorySequence[3];
    private TrajectorySequence[] backDropTraj = new TrajectorySequence[3];
    private TrajectorySequence[] parkTraj = new TrajectorySequence[3];

    private Command releaseCommand = () -> {
        claw.leftOpen();
        claw.rightOpen();
    };
    private Command intakeCommand = () -> intake.up();
    private Command slidesCommand = () -> slides.goToPos(0);
    private Command armCommand = () -> arm.scorePos();
    private Command wristCommand = () -> wrist.scorePos();
    private Command retractCommand = () -> {
        claw.leftOpen();
        claw.rightOpen();
        wrist.intakePos();
        arm.intakePos();
        slides.intakePos();
    };

    private Command spikeMarkCommand = () -> drive.followTrajectorySequenceAsync(spikeMarkTraj[reflectPos(pos)]);
    private Command backDropCommand = () -> drive.followTrajectorySequenceAsync(backDropTraj[reflectPos(pos)]);
    private Command parkCommand = () -> drive.followTrajectorySequenceAsync(parkTraj[reflectPos(pos)]);

    private CommandSequence spikeMarkSequence = new CommandSequence()
            .addCommand(spikeMarkCommand)
            .build();
    private CommandSequence backDropSequence = new CommandSequence()
            .addCommand(intakeCommand)
            .addCommand(backDropCommand)
            .build();
    private CommandSequence scoreSequence = new CommandSequence()
            .addWaitCommand(SLIDES_DELAY)
            .addCommand(slidesCommand)
            .addWaitCommand(0.1)
            .addCommand(wristCommand)
            .addCommand(armCommand)
            .addWaitCommand(ARM_DELAY)
            .addCommand(releaseCommand)
            .addWaitCommand(SCORE_DELAY)
            .addCommand(retractCommand)
            .build();
    private CommandSequence parkSequence = new CommandSequence()
            .addWaitCommand(3)
            .addCommand(parkCommand)
            .build();

    private AutoCommandMachine commandMachine = new AutoCommandMachine()
            .addCommandSequence(spikeMarkSequence)
            .addCommandSequence(backDropSequence)
            .addCommandSequence(scoreSequence)
            .addCommandSequence(parkSequence)
            .addCommandSequence(parkSequence)
            .build();

    public BackDropAuto(Color color) {
        this.color = color;
    }

    @Override
    public void runOpMode() throws InterruptedException {
        AutoConstants.init();
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
                    .trajectorySequenceBuilder(reflectX(AutoConstants.BD_START_POSE))
                    .splineTo(reflectX(AutoConstants.BD_SPIKE_VECTORS[i]),
                            reflectX(AutoConstants.BD_SPIKE_HEADINGS[i]))
                    .build();
            backDropTraj[i] = drive
                    .trajectorySequenceBuilder(spikeMarkTraj[i].end())
                    .setReversed(true)
                    .splineTo(reflectX(AutoConstants.TAG_VECTORS[i]),
                            reflectX(AutoConstants.TAG_HEADINGS[i]))
                    .build();
            parkTraj[i] = drive
                    .trajectorySequenceBuilder(backDropTraj[i].end())
                    .strafeTo(reflectX(AutoConstants.PARK_VECTOR))
                    .build();
        }

        while (opModeInInit()) {
            pos = webcam.getPosition();
            telemetry.addData("Position: ", pos);
            telemetry.update();
        }

        drive.setPoseEstimate(reflectX(AutoConstants.BD_START_POSE));

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

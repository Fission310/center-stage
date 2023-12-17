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
import org.firstinspires.ftc.teamcode.hardware.mechanisms.Hopper;
import org.firstinspires.ftc.teamcode.hardware.mechanisms.Slides2;
import org.firstinspires.ftc.teamcode.hardware.mechanisms.Webcam;
import org.firstinspires.ftc.teamcode.hardware.mechanisms.Webcam.Position;
import org.firstinspires.ftc.teamcode.opmode.auton.AutoConstants.Color;

@Config
public class BackDropAuto extends LinearOpMode {

    private boolean reflect;
    private Color color;
    private Position pos = Position.CENTER;

    private Arm arm;
    private SampleMecanumDrive drive;
    private Hopper hopper;
    private Slides2 slides;
    private Webcam webcam;

    public static double ARM_DELAY = 0.5;
    public static double SCORE_DELAY = 0.5;
    public static double SLIDES_DELAY = 0.5;

    private TrajectorySequence spikeMarkTraj;
    private TrajectorySequence backDropTraj;
    private TrajectorySequence parkTraj;

    private Command releaseCommand = () -> hopper.release();
    private Command slidesCommand = () -> slides.mediumLowPos();
    private Command armCommand = () -> arm.scorePos();
    private Command retractCommand = () -> {
        hopper.release();
        arm.intakePos();
        slides.intakePos();
        hopper.close();
    };

    private Command spikeMarkCommand = () -> drive.followTrajectorySequenceAsync(spikeMarkTraj);
    private Command backDropCommand = () -> drive.followTrajectorySequenceAsync(backDropTraj);
    private Command parkCommand = () -> drive.followTrajectorySequenceAsync(parkTraj);

    private CommandSequence spikeMarkSequence = new CommandSequence()
            .addCommand(spikeMarkCommand)
            .build();
    private CommandSequence scoreSequence = new CommandSequence()
            .addCommand(backDropCommand)
            .addCommand(slidesCommand)
            .addWaitCommand(ARM_DELAY)
            .addCommand(armCommand)
            .addWaitCommand(SLIDES_DELAY)
            .addCommand(releaseCommand)
            .addWaitCommand(SCORE_DELAY)
            .addCommand(retractCommand)
            .build();
    private CommandSequence parkSequence = new CommandSequence()
            .addCommand(parkCommand)
            .build();

    private AutoCommandMachine commandMachine = new AutoCommandMachine()
            .addCommandSequence(spikeMarkSequence)
            .addCommandSequence(scoreSequence)
            .addCommandSequence(parkSequence)
            .build();

    public BackDropAuto(Color color) {
        this.color = color;
    }

    @Override
    public void runOpMode() throws InterruptedException {
        reflect = color == Color.RED ? true : false;
        arm = new Arm(this);
        drive = new SampleMecanumDrive(hardwareMap);
        hopper = new Hopper(this);
        slides = new Slides2(this);
        webcam = new Webcam(this, color);

        arm.init(hardwareMap);
        hopper.init(hardwareMap);
        slides.init(hardwareMap);
        webcam.init(hardwareMap);

        while (opModeIsActive() && isStopRequested()) {
            pos = webcam.getPosition();
            telemetry.addData("Position: ", pos);
            telemetry.update();
        }

        drive.setPoseEstimate(AutoConstants.BD_START_POSE);

        spikeMarkTraj = drive
                .trajectorySequenceBuilder(AutoConstants.BD_START_POSE)
                .splineTo(reflectX(AutoConstants.BD_SPIKE_VECTORS[pos.index]),
                        reflectX(AutoConstants.BD_SPIKE_HEADINGS[pos.index]))
                .build();
        backDropTraj = drive
                .trajectorySequenceBuilder(spikeMarkTraj.end())
                .setReversed(true)
                .splineTo(reflectX(AutoConstants.TAG_VECTORS[pos.index]),
                        reflectX(AutoConstants.TAG_HEADINGS[pos.index]))
                .build();
        parkTraj = drive
                .trajectorySequenceBuilder(backDropTraj.end())
                .strafeTo(reflectX(AutoConstants.BD_PARK_VECTOR))
                .build();

        waitForStart();

        webcam.stopStreaming();

        while (opModeIsActive() && !isStopRequested()) {
            drive.update();
            slides.update();
            commandMachine.run(drive.isBusy());
        }
    }

    public Vector2d reflectX(Vector2d vector) {
        if (reflect) {
            return new Vector2d(vector.getX() * -1, vector.getY());
        }
        return vector;
    }

    public double reflectX(double theta) {
        if (reflect) {
            return theta * -1;
        }
        return theta;
    }
}

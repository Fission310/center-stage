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

    public static double ARM_DELAY = 1;
    public static double SCORE_DELAY = 1;
    public static double SLIDES_DELAY = 1;

    private TrajectorySequence[] spikeMarkTraj = new TrajectorySequence[3];
    private TrajectorySequence[] backDropTraj = new TrajectorySequence[3];
    private TrajectorySequence[] parkTraj = new TrajectorySequence[3];

    private Command releaseCommand = () -> hopper.release();
    private Command slidesCommand = () -> slides.mediumLowPos();
    private Command armCommand = () -> arm.scorePos();
    private Command retractCommand = () -> {
        hopper.release();
        arm.intakePos();
        slides.intakePos();
        hopper.close();
    };

    private Command spikeMarkCommand = () -> drive.followTrajectorySequenceAsync(spikeMarkTraj[pos.index]);
    private Command backDropCommand = () -> drive.followTrajectorySequenceAsync(backDropTraj[pos.index]);
    private Command parkCommand = () -> drive.followTrajectorySequenceAsync(parkTraj[pos.index]);

    private CommandSequence spikeMarkSequence = new CommandSequence()
            .addCommand(spikeMarkCommand)
            .build();
    private CommandSequence scoreSequence = new CommandSequence()
            .addCommand(backDropCommand)
            // .addWaitCommand(10)
            .addWaitCommand(SLIDES_DELAY)
            .addCommand(slidesCommand)
            .addCommand(armCommand)
            .addWaitCommand(ARM_DELAY)
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
            .addCommandSequence(parkSequence)
            .build();

    public BackDropAuto(Color color) {
        this.color = color;
    }

    @Override
    public void runOpMode() throws InterruptedException {
        reflect = color == Color.RED;
        arm = new Arm(this);
        drive = new SampleMecanumDrive(hardwareMap);
        hopper = new Hopper(this);
        slides = new Slides2(this);
        webcam = new Webcam(this, color);

        arm.init(hardwareMap);
        hopper.init(hardwareMap);
        slides.init(hardwareMap);
        webcam.init(hardwareMap);

        for (int i = 0; i < 3; i++) {
            drive.setPoseEstimate(reflectX(AutoConstants.BD_START_POSE));

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

        while (!isStopRequested()) {
            pos = webcam.getPosition();
            telemetry.addData("Position: ", pos);
            telemetry.update();
        }

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
            return new Vector2d(vector.getX() * -1, vector.getY());
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

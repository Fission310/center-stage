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
import static org.firstinspires.ftc.teamcode.opmode.auton.backdrop.BackDropConstants.*;
import org.firstinspires.ftc.teamcode.opmode.auton.util.Color;

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

    public static double ARM_DELAY = 0.1;
    public static double SCORE_DELAY = 0.5;
    public static double SLIDES_DELAY = 0.5;
    public static double PLATFORM_DELAY = 1.4;

    private TrajectorySequence[] spikeMarkTraj = new TrajectorySequence[3];
    private TrajectorySequence[] backDropTraj = new TrajectorySequence[3];
    private TrajectorySequence[] trussBackTraj = new TrajectorySequence[3];
    private TrajectorySequence[] trussTraj = new TrajectorySequence[3];
    private TrajectorySequence[] parkTraj = new TrajectorySequence[3];

    private Command releaseCommand = () -> {
        claw.leftOpen();
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
    private Command intakeCommand = () -> intake.up();
    private Command pixelPlatformUp = () -> intake.pixelUp();
    private Command pixelPlatformDown = () -> intake.pixelDown();
    private Command slidesCommand = () -> slides.goToPos(0);
    private Command armCommand = () -> arm.scorePos();
    private Command wristCommand = () -> wrist.scorePos();
    private Command grabCommand = () -> claw.close();
    private Command intakeUpSecond = () -> intake.upAuto(2);
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
    private Command backDropCommand = () -> drive.followTrajectorySequenceAsync(backDropTraj[reflectPos(pos)]);
    private Command trussBackCommand = () -> drive.followTrajectorySequenceAsync(trussBackTraj[reflectPos(pos)]);
    private Command trussCommand = () -> drive.followTrajectorySequenceAsync(trussTraj[reflectPos(pos)]);
    private Command parkCommand = () -> drive.followTrajectorySequenceAsync(parkTraj[reflectPos(pos)]);

    private CommandSequence spikeMarkSequence = new CommandSequence()
            .addCommand(spikeMarkCommand)
            .build();
    private CommandSequence backDropSequence = new CommandSequence()
            .addCommand(intakeCommand)
            .addCommand(backDropCommand)
            .build();
    private CommandSequence trussBackSequence = new CommandSequence()
            .addCommand(trussBackCommand)
            .addWaitCommand(0.2)
            .addCommand(retractFirstCommand)
            .addWaitCommand(0.4)
            .addCommand(retractSecondCommand)
            .addCommand(intakeUpSecond)
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
            .addCommand(releaseCommand)
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
            .addCommandSequence(backDropSequence)
            .addCommandSequence(trussBackSequence)
            .addCommandSequence(trussSequence)
            .addCommandSequence(parkSequence)
            .addCommandSequence(parkSequence)
            .build();

    public BackDropAuto(Color color) {
        this.color = color;
    }

    @Override
    public void runOpMode() throws InterruptedException {
        BackDropConstants.init();
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
                    .trajectorySequenceBuilder(reflectX(START_POSE))
                    .lineToLinearHeading(new Pose2d(reflectX(SPIKE.getV(i)),
                            reflectX(SPIKE.getH(i))))
                    .build();
            backDropTraj[i] = drive
                    .trajectorySequenceBuilder(spikeMarkTraj[i].end())
                    .setReversed(true)
                    .lineToLinearHeading(new Pose2d(reflectX(TAG_1.getV(i)),
                            reflectX(TAG_1.getH(i))))
                    .build();
            trussBackTraj[i] = drive
                    .trajectorySequenceBuilder(backDropTraj[i].end())
                    .setReversed(false)
                    .splineToConstantHeading(reflectX(BACK_TRUSS.getV(i)),
                            reflectX(BACK_TRUSS.getH(i)))
                    .splineTo(reflectX(STACK.getV(i)),
                            reflectX(STACK.getH(i)))
                    .build();
            trussTraj[i] = drive
                    .trajectorySequenceBuilder(trussBackTraj[i].end())
                    .setReversed(true)
                    .splineTo(reflectX(TRUSS.getV(i)),
                            reflectX(TRUSS.getH(i)))
                    .splineToConstantHeading(reflectX(TAG_2.getV(i)),
                            reflectX(TAG_2.getH(i)))
                    .build();
            parkTraj[i] = drive
                    .trajectorySequenceBuilder(trussTraj[i].end())
                    .setReversed(false)
                    .splineToConstantHeading(reflectX(PARK.getV(i)), reflectX(PARK.getH(i)))
                    .build();
        }

        while (opModeInInit()) {
            pos = webcam.getPosition();
            telemetry.addData("Position: ", pos);
            telemetry.update();
        }

        drive.setPoseEstimate(reflectX(START_POSE));

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

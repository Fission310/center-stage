package org.firstinspires.ftc.teamcode.opmode.auton.fronttruss;

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
import static org.firstinspires.ftc.teamcode.opmode.auton.fronttruss.TrussConstants.*;
import org.firstinspires.ftc.teamcode.opmode.auton.util.Color;

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
    private TrajectorySequence[] trussFirstTraj = new TrajectorySequence[3];
    private TrajectorySequence[] trussBackTraj = new TrajectorySequence[3];
    private TrajectorySequence[] trussSecondTraj = new TrajectorySequence[3];
    private TrajectorySequence[] parkTraj = new TrajectorySequence[3];

    private Command releaseLeftCommand = () -> {
        claw.leftOpen();
    };
    private Command releaseRightCommand = () -> {
        claw.rightOpen();
    };
    private Command slidesCommand = () -> slides.goToPos(0);
    private Command slidesSecondCommand = () -> slides.goToPos(1);
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
    private Command intakeUpFirst = () -> intake.upAuto(1);
    private Command intakeUpSecond = () -> intake.upAuto(2);
    private Command pixelPlatformUp = () -> intake.pixelUp();
    private Command pixelPlatformDown = () -> intake.pixelDown();
    private Command grabCommand = () -> claw.close();
    private Command armCommand = () -> arm.scorePos();
    private Command wristCommand = () -> wrist.autoPos(reflectPos(pos));
    private Command retractFirstCommand = () -> {
        claw.leftOpen();
        claw.rightOpen();
        wrist.intakePos();
    };
    private Command retractSecondCommand = () -> {
        arm.intakePos();
        slides.intakePos();
    };

    private Command spikeMarkCommand = () -> drive.followTrajectorySequenceAsync(spikeMarkTraj[reflectPos(pos)]);
    private Command stackCommand = () -> drive.followTrajectorySequenceAsync(stackTraj[reflectPos(pos)]);
    private Command trussFirstCommand = () -> drive.followTrajectorySequenceAsync(trussFirstTraj[reflectPos(pos)]);
    private Command trussBackCommand = () -> drive.followTrajectorySequenceAsync(trussBackTraj[reflectPos(pos)]);
    private Command tagCenterCommand = () -> drive.followTrajectorySequenceAsync(trussSecondTraj[reflectPos(pos)]);
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
            .addCommand(intakeUpFirst)
            .build();
    private CommandSequence trussFirstSequence = new CommandSequence()
            .addCommand(intakeStartCommand)
            .addCommand(trussFirstCommand)
            .addCommand(sensePixels)
            .addWaitCommand(0.5)
            .addCommand(intakeStopCommand)
            .addWaitCommand(0.4)
            .addCommand(pixelPlatformUp)
            .addWaitCommand(PLATFORM_DELAY)
            .addCommand(grabCommand)
            .addWaitCommand(1.5)
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
    private CommandSequence trussBackSequence = new CommandSequence()
            .addCommand(trussBackCommand)
            .addWaitCommand(0.2)
            .addCommand(retractFirstCommand)
            .addWaitCommand(0.4)
            .addCommand(retractSecondCommand)
            .addCommand(intakeUpSecond)
            .build();
    private CommandSequence trussSecondSequence = new CommandSequence()
            .addCommand(intakeStartCommand)
            .addCommand(tagCenterCommand)
            .addCommand(trussFirstCommand)
            .addCommand(sensePixels)
            .addWaitCommand(0.5)
            .addCommand(intakeStopCommand)
            .addWaitCommand(0.4)
            .addCommand(pixelPlatformUp)
            .addWaitCommand(PLATFORM_DELAY)
            .addCommand(grabCommand)
            .addWaitCommand(1.5)
            .addCommand(pixelPlatformDown)
            .addWaitCommand(0.1)
            .addCommand(slidesSecondCommand)
            .addWaitCommand(0.1)
            .addCommand(armCommand)
            .addWaitCommand(ARM_DELAY)
            .addCommand(wristCommand)
            .addWaitCommand(SCORE_DELAY)
            .addCommand(releaseRightCommand)
            .addCommand(releaseLeftCommand)
            .build();
    private CommandSequence parkSequence = new CommandSequence()
            .addWaitCommand(0.6)
            .addCommand(parkCommand)
            .addWaitCommand(2)
            .addCommand(retractFirstCommand)
            .addWaitCommand(0.4)
            .addCommand(retractSecondCommand)
            .build();

    private AutoCommandMachine commandMachine = new AutoCommandMachine()
            .addCommandSequence(spikeMarkSequence)
            .addCommandSequence(stackSequence)
            .addCommandSequence(trussFirstSequence)
            //.addCommandSequence(trussBackSequence)
            //.addCommandSequence(trussSecondSequence)
            .addCommandSequence(parkSequence)
            .addCommandSequence(parkSequence)
            .build();

    public FrontTrussAuto(Color color) {
        this.color = color;
    }

    @Override
    public void runOpMode() throws InterruptedException {
        TrussConstants.init();
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
                    .trajectorySequenceBuilder(reflectX(START_POSE))
                    .lineToLinearHeading(reflectX(new Pose2d(SPIKE.getV(i),
                            SPIKE.getH(i))))
                    .build();
            stackTraj[i] = drive
                    .trajectorySequenceBuilder(spikeMarkTraj[i].end())
                    .setTangent(SPIKE_TANGENT)
                    .splineToLinearHeading(
                            reflectX(new Pose2d(STACK_1.getV(i),
                                    STACK_1.getH(i))),
                            reflectX(STACK_1.getH(i)))
                    .build();
            trussFirstTraj[i] = drive
                    .trajectorySequenceBuilder(stackTraj[i].end())
                    .setReversed(true)
                    .splineTo(reflectX(TRUSS.getV(i)),
                            reflectX(TRUSS.getH(i)))
                    .splineToConstantHeading(reflectX(TAG_1.getV(i)),
                            reflectX(TAG_1.getH(i)))
                    .build();
            trussBackTraj[i] = drive
                    .trajectorySequenceBuilder(trussFirstTraj[i].end())
                    .setReversed(false)
                    .splineToConstantHeading(reflectX(BACK_TRUSS.getV(i)),
                            reflectX(BACK_TRUSS.getH(i)))
                    .splineTo(reflectX(STACK_2.getV(i)),
                            reflectX(STACK_2.getH(i)))
                    .build();
            trussSecondTraj[i] = drive
                    .trajectorySequenceBuilder(trussBackTraj[i].end())
                    .setReversed(true)
                    .splineTo(reflectX(TRUSS.getV(i)),
                            reflectX(TRUSS.getH(i)))
                    .splineToConstantHeading(reflectX(TAG_2.getV(2 - i + i % 2)),
                            reflectX(TAG_2.getH(2 - i + i % 2)))
                    .build();
            parkTraj[i] = drive
                    .trajectorySequenceBuilder(trussFirstTraj[i].end())
                    .setReversed(false)
                    .splineToConstantHeading(reflectX(PARK.getV(i)),
                            reflectX(PARK.getH(i)))
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

package org.firstinspires.ftc.teamcode.opmode.auton;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@Config
@Autonomous(name = "ParkBlue", group = "_ablue", preselectTeleOp = "Main")
public class ParkBlue extends LinearOpMode {

    private SampleMecanumDrive drive;

    public static int DIST = 80;

    @Override
    public void runOpMode() throws InterruptedException {
        drive = new SampleMecanumDrive(hardwareMap);

        drive.setPoseEstimate(new Pose2d(70.5 / 6.0, 70.5 - (70.5 / 10.0), Math.toRadians(-90)));

        TrajectorySequence traj = drive
                .trajectorySequenceBuilder(
                        new Pose2d(70.5 / 6.0, 70.5 - (70.5 / 10.0), Math.toRadians(-90)))
                .strafeRight(DIST)
                .build();

        waitForStart();

        drive.followTrajectorySequenceAsync(traj);

        while (opModeIsActive() && !isStopRequested()) {
            drive.update();
        }
    }
}

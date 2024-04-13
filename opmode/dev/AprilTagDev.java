package org.firstinspires.ftc.teamcode.opmode.dev;

import org.firstinspires.ftc.teamcode.hardware.mechanisms.Drivetrain;
import org.firstinspires.ftc.teamcode.hardware.mechanisms.Webcam;
import org.firstinspires.ftc.teamcode.opmode.auton.util.Color;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "April Tag test", group = "dev")
public class AprilTagDev extends LinearOpMode {
    private Webcam webcam = new Webcam(this, Color.BLUE);
    private Drivetrain drivetrain = new Drivetrain(this);

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        webcam.init(hardwareMap);
        drivetrain.init(hardwareMap);

        waitForStart();

        webcam.stopStreaming();
        webcam.aprilTagInit();

        webcam.setDesiredTag(1);

        while (opModeIsActive() && !isStopRequested()) {
            drivetrain.loop(gamepad1);
            webcam.detectAprilTag(telemetry);
        }
    }
}

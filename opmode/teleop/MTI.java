package org.firstinspires.ftc.teamcode.opmode.teleop;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.hardware.MTIRobot;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "MTI", group = "_amain")
public class MTI extends LinearOpMode {

    private MTIRobot robot = new MTIRobot(this);

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        Telemetry telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            robot.loop(gamepad1);

            robot.telemetry(telemetry);
        }
    }
}

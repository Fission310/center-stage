package org.firstinspires.ftc.teamcode.opmode.dev;

import org.firstinspires.ftc.teamcode.hardware.mechanisms.Hopper;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Hopper test", group = "dev")
public class HopperDev extends LinearOpMode {

    private Hopper hopper = new Hopper(this);

    @Override
    public void runOpMode() throws InterruptedException {
        hopper.init(hardwareMap);

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            hopper.loop(gamepad1);
        }
    }
}

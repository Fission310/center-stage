package org.firstinspires.ftc.teamcode.opmode.dev;

import org.firstinspires.ftc.teamcode.hardware.mechanisms.Claw;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Claw test", group = "dev")
public class ClawDev extends LinearOpMode {

    private Claw claw = new Claw(this);

    @Override
    public void runOpMode() throws InterruptedException {
        claw.init(hardwareMap);

        waitForStart();
        
        while (opModeIsActive() && !isStopRequested()) {
            claw.loop(gamepad1);
        }
    }
}

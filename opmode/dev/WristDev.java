package org.firstinspires.ftc.teamcode.opmode.dev;

import org.firstinspires.ftc.teamcode.hardware.mechanisms.Wrist;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Wrist test", group = "dev")
public class WristDev extends LinearOpMode {

    private Wrist wrist = new Wrist(this);

    @Override
    public void runOpMode() throws InterruptedException {
        wrist.init(hardwareMap);

        waitForStart();
        
        while (opModeIsActive() && !isStopRequested()) {
            wrist.loop(gamepad1);
        }
    }
}

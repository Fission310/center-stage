package org.firstinspires.ftc.teamcode.opmode.dev;

import org.firstinspires.ftc.teamcode.hardware.mechanisms.Intake;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Intake test", group = "dev")
public class IntakeDev extends LinearOpMode {

    private Intake intake = new Intake(this);

    @Override
    public void runOpMode() throws InterruptedException {
        intake.init(hardwareMap);

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            intake.loop(gamepad1);
        }
    }
}

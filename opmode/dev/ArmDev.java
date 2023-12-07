package org.firstinspires.ftc.teamcode.opmode.dev;

import org.firstinspires.ftc.teamcode.hardware.mechanisms.Arm;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Arm test", group = "dev")
public class ArmDev extends LinearOpMode {

    private Arm arm = new Arm(this);

    @Override
    public void runOpMode() throws InterruptedException {
        arm.init(hardwareMap);

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            arm.loop(gamepad1);
        }
    }
}

package org.firstinspires.ftc.teamcode.opmode.dev;

import org.firstinspires.ftc.teamcode.hardware.mechanisms.Intake;
import org.firstinspires.ftc.teamcode.opmode.teleop.Controls;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.stuyfission.fissionlib.input.GamepadStatic;

@TeleOp(name = "Intake test", group = "dev")
public class IntakeDev extends LinearOpMode {

    private Intake intake = new Intake(this);

    @Override
    public void runOpMode() throws InterruptedException {
        intake.init(hardwareMap);

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            intake.loop(gamepad1);

            if (GamepadStatic.isButtonPressed(gamepad1, Controls.INTAKE)) {
                intake.intake();
            } else if (GamepadStatic.isButtonPressed(gamepad1, Controls.OUTTAKE)) {
                intake.outtake();
            } else {
                intake.stop();
            }
        }
    }
}

package org.firstinspires.ftc.teamcode.opmode.dev;

import org.firstinspires.ftc.teamcode.hardware.mechanisms.Drivetrain;
import org.firstinspires.ftc.teamcode.hardware.mechanisms.Intake;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Milo test", group = "dev")
public class DriveIntakeDev extends LinearOpMode {

    private Drivetrain drivetrain = new Drivetrain(this);
    private Intake intake = new Intake(this);

    @Override
    public void runOpMode() throws InterruptedException {
        drivetrain.init(hardwareMap);
        intake.init(hardwareMap);

        intake.pixelUp();

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            drivetrain.loop(gamepad1);
            intake.loop(gamepad1);
        }
    }
}

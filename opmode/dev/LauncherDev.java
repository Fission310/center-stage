package org.firstinspires.ftc.teamcode.opmode.dev;

import org.firstinspires.ftc.teamcode.hardware.mechanisms.Drivetrain;
import org.firstinspires.ftc.teamcode.hardware.mechanisms.Launcher;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Launcher test", group = "_amain")
public class LauncherDev extends LinearOpMode {

    private Launcher launcher = new Launcher(this);

    @Override
    public void runOpMode() throws InterruptedException {
        launcher.init(hardwareMap);

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            launcher.loop(gamepad1);
        }
    }
}
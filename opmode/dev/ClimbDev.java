package org.firstinspires.ftc.teamcode.opmode.dev;

import org.firstinspires.ftc.teamcode.hardware.mechanisms.Climb;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Climb test", group = "dev")
public class ClimbDev extends LinearOpMode {

    private Climb climb = new Climb(this);

    @Override
    public void runOpMode() throws InterruptedException {
        climb.init(hardwareMap);

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            climb.loop(gamepad1);
        }
    }
}

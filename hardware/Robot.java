package org.firstinspires.ftc.teamcode.hardware;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.hardware.mechanisms.Drivetrain;
import org.firstinspires.ftc.teamcode.hardware.mechanisms.Launcher;
import org.firstinspires.ftc.teamcode.hardware.mechanisms.Scoring;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.stuyfission.fissionlib.util.Mechanism;

public class Robot extends Mechanism {

    private Scoring scoring = new Scoring(opMode);
    private Launcher launcher = new Launcher(opMode);

    public Robot(LinearOpMode opMode) {
        this.opMode = opMode;
    }

    @Override
    public void init(HardwareMap hwMap) {
        scoring.init(hwMap);
        launcher.init(hwMap);
    }

    @Override
    public void telemetry(Telemetry telemetry) {
        scoring.telemetry(telemetry);
    }

    @Override
    public void loop(Gamepad gamepad1, Gamepad gamepad2) {
        scoring.loop(gamepad1, gamepad2);
        launcher.loop(gamepad2);
    }
}

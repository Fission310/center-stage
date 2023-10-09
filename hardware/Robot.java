package org.firstinspires.ftc.teamcode.hardware;

import org.firstinspires.ftc.teamcode.hardware.mechanisms.Drivetrain;
import org.firstinspires.ftc.teamcode.hardware.mechanisms.Scoring;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.stuyfission.fissionlib.util.Mechanism;

public class Robot extends Mechanism {

    private Drivetrain drivetrain = new Drivetrain(opMode);
    private Scoring scoring = new Scoring(opMode);

    public Robot(LinearOpMode opMode) {
        this.opMode = opMode;
    }

    @Override
    public void init(HardwareMap hwMap) {
        drivetrain.init(hwMap);
        scoring.init(hwMap);
    }

    @Override
    public void loop(Gamepad gamepad) {
        drivetrain.loop(gamepad);
        scoring.loop(gamepad);
    }
}

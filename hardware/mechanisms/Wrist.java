package org.firstinspires.ftc.teamcode.hardware.mechanisms;

import org.firstinspires.ftc.teamcode.opmode.teleop.Controls;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.stuyfission.fissionlib.input.GamepadStatic;
import com.stuyfission.fissionlib.util.Mechanism;

@Config
public class Wrist extends Mechanism {

    private Servo wristServo;

    public static double SCORE_POS = 0.0;
    public static double INTAKE_POS = 0.0;

    public Wrist(LinearOpMode opMode) {
        this.opMode = opMode;
    }

    @Override
    public void init(HardwareMap hwMap) {
        wristServo = hwMap.get(Servo.class, "wristServo");

        down();
    }

    public void up() {
        wristServo.setPosition(SCORE_POS);
    }

    public void down() {
        wristServo.setPosition(INTAKE_POS);
    }

    @Override
    public void loop(Gamepad gamepad) {
        if (GamepadStatic.isButtonPressed(gamepad, Controls.INTAKE_UP)) {
            up();
        }
        if (GamepadStatic.isButtonPressed(gamepad, Controls.INTAKE_DOWN)) {
            down();
        }
    }
}

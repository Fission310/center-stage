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
public class Claw extends Mechanism {

    private Servo leftServo;
    private Servo rightServo;

    public static double OPEN_POS = 0.5;
    public static double CLOSE_POS = 0.0;

    public Claw(LinearOpMode opMode) {
        this.opMode = opMode;
    }

    @Override
    public void init(HardwareMap hwMap) {
        leftServo = hwMap.get(Servo.class, "clawLeftServo");
        rightServo = hwMap.get(Servo.class, "clawRightServo");

        leftServo.setDirection(Servo.Direction.REVERSE);

        leftOpen();
        rightOpen();
    }

    public void leftOpen() {
        leftServo.setPosition(OPEN_POS);
    }

    public void rightOpen() {
        rightServo.setPosition(OPEN_POS);
    }

    public void close() {
        leftServo.setPosition(CLOSE_POS);
        rightServo.setPosition(CLOSE_POS);
    }

    @Override
    public void loop(Gamepad gamepad) {
        if (GamepadStatic.isButtonPressed(gamepad, Controls.SCORE_LEFT)) {
            leftOpen();
        }
        if (GamepadStatic.isButtonPressed(gamepad, Controls.SCORE_RIGHT)) {
            rightOpen();
        }
        if (GamepadStatic.isButtonPressed(gamepad, Controls.GRAB)) {
            close();
        }
    }
}

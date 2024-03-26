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

    private boolean leftOpen = true;
    private boolean rightOpen = true;

    private Servo leftServo;
    private Servo rightServo;

    public static double OPEN_POS_R = 0.55;
    public static double OPEN_POS_L = 0.98;
    public static double CLOSE_POS_R = 0.37;
    public static double CLOSE_POS_L = 0.8;

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
        leftServo.setPosition(OPEN_POS_L);
        leftOpen = true;
    }

    public void rightOpen() {
        rightServo.setPosition(OPEN_POS_R);
        rightOpen = true;
    }

    public void close() {
        leftServo.setPosition(CLOSE_POS_L);
        rightServo.setPosition(CLOSE_POS_R);
        leftOpen = false;
        rightOpen = false;
    }

    public int numPixels() {
        return (leftOpen ? 0 : 1) + (rightOpen ? 0 : 1);
    }

    @Override
    public void loop(Gamepad gamepad) {
        if (GamepadStatic.isButtonPressed(gamepad, Controls.SCORE_TWO)) {
            leftOpen();
            rightOpen();
        }
        if (GamepadStatic.isButtonPressed(gamepad, Controls.SCORE_ONE)) {
            if (numPixels() == 2) {
                leftOpen();
            } else {
                rightOpen();
            }
        }
        if (GamepadStatic.isButtonPressed(gamepad, Controls.GRAB)) {
            close();
        }
    }
}

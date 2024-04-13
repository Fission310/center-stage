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

    private boolean left = false;
    private boolean right = false;

    public static double START_POS = 0.285;
    public static double AUTO_SCORE_POS = 0.86;
    public static double[] AUTO_POSITIONS = { 0.1, 0.1, 0.1 };
    public static double[] POSITIONS = {
            0.01, 0.2, 0.38, 0.57, 0.76, 0.94
    };

    private int pos = 0;

    public Wrist(LinearOpMode opMode) {
        this.opMode = opMode;
    }

    @Override
    public void init(HardwareMap hwMap) {
        wristServo = hwMap.get(Servo.class, "wristServo");

        intakePos();
    }

    public void scorePos() {
        pos = 3;
        wristServo.setPosition(POSITIONS[3]);
    }

    public void intakePos() {
        wristServo.setPosition(START_POS);
    }

    public void autoPos(int i) {
        if (i == 0) {
            wristServo.setPosition(POSITIONS[0]);
        } else {
            wristServo.setPosition(POSITIONS[3]);
        }
    }

    public void autoPos() {
        wristServo.setPosition(AUTO_SCORE_POS);
    }

    public void right() {
        if (right)
            return;
        if (pos == 5) {
            pos = 0;
        } else {
            pos++;
        }
        wristServo.setPosition(POSITIONS[pos]);
    }

    public void left() {
        if (left)
            return;
        if (pos == 0) {
            pos = 5;
        } else {
            pos--;
        }
        wristServo.setPosition(POSITIONS[pos]);
    }

    public void setPos(int pos) {
        this.pos = pos;
        wristServo.setPosition(POSITIONS[pos]);
    }

    public int getPos() {
        return pos;
    }

    @Override
    public void loop(Gamepad gamepad) {
        if (GamepadStatic.isButtonPressed(gamepad, Controls.WRIST_LEFT)) {
            left();
            left = true;
        } else {
            left = false;
        }
        if (GamepadStatic.isButtonPressed(gamepad, Controls.WRIST_RIGHT)) {
            right();
            right = true;
        } else {
            right = false;
        }
    }
}

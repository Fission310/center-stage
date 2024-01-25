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

    public static double START_POS = 0.29;
    public static double VERTICAL_UP_POS = 0.49;
    public static double HORIZONTAL_LEFT_POS = (VERTICAL_UP_POS + 0.25) % 1.0;
    public static double VERTICAL_DOWN_POS = (VERTICAL_UP_POS + 0.5) % 1.0;
    public static double HORIZONTAL_RIGHT_POS = (VERTICAL_UP_POS + 0.75) % 1.0;
    public static double[] POSITIONS = {
        0.01, 0.19, 0.57, 0.76
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
        wristServo.setPosition(POSITIONS[0]);
    }

    public void intakePos() {
        wristServo.setPosition(START_POS);
    }

    public void right() {
        if (right) return;
        if (pos == 3) {
            pos = 0;
        } else {
            pos++;
        }
        wristServo.setPosition(POSITIONS[pos]);
    }

    public void left() {
        if (left) return;
        if (pos == 0) {
            pos = 3;
        } else {
            pos--;
        }
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
        //HORIZONTAL_LEFT_POS = (VERTICAL_UP_POS + 0.25) % 1.0;
        //VERTICAL_DOWN_POS = (VERTICAL_UP_POS + 0.5) % 1.0;
        //HORIZONTAL_RIGHT_POS = (VERTICAL_UP_POS + 0.75) % 1.0;
        //POSITIONS[0] = VERTICAL_UP_POS;
        //POSITIONS[1] = HORIZONTAL_LEFT_POS;
        //POSITIONS[2] = VERTICAL_DOWN_POS;
        //POSITIONS[3] = HORIZONTAL_RIGHT_POS;
    }
}

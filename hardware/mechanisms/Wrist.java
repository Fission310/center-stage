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

    public static double VERTICAL_UP_POS = 0.0;
    public static double HORIZONTAL_LEFT_POS = (VERTICAL_UP_POS + 0.25) % 1.0;
    public static double VERTICAL_DOWN_POS = (VERTICAL_UP_POS + 0.5) % 1.0;
    public static double HORIZONTAL_RIGHT_POS = (VERTICAL_UP_POS + 0.75) % 1.0;
    public static double[] POSITIONS = {VERTICAL_UP_POS, HORIZONTAL_LEFT_POS, VERTICAL_DOWN_POS, HORIZONTAL_RIGHT_POS};

    private int pos = 0;

    public Wrist(LinearOpMode opMode) {
        this.opMode = opMode;
    }

    @Override
    public void init(HardwareMap hwMap) {
        wristServo = hwMap.get(Servo.class, "wristServo");
    }

    public void left() {
        if (pos == 3) {
            pos = 0; 
        } else {
            pos++;
        }
        wristServo.setPosition(POSITIONS[pos]);
    }

    public void right() {
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
        }
        if (GamepadStatic.isButtonPressed(gamepad, Controls.WRIST_RIGHT)) {
            right();
        }
    }
}

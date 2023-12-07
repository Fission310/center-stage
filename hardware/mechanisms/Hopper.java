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
public class Hopper extends Mechanism {

    private Servo releaseServo;

    public static double RELEASE_POS = 0.44;
    public static double CLOSE_POS = 0.59;

    public Hopper(LinearOpMode opMode) {
        this.opMode = opMode;
    }

    @Override
    public void init(HardwareMap hwMap) {
        releaseServo = hwMap.get(Servo.class, "hopperServo");
        close();
    }

    public void release() {
        releaseServo.setPosition(RELEASE_POS);
    }

    public void close() {
        releaseServo.setPosition(CLOSE_POS);
    }

    public void loop(Gamepad gamepad) {
        if (GamepadStatic.isButtonPressed(gamepad, Controls.SCORE)) {
            release();
        } else if (GamepadStatic.isButtonPressed(gamepad, Controls.GRAB)) {
            close();
        }
    }
}

package org.firstinspires.ftc.teamcode.hardware.mechanisms;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.stuyfission.fissionlib.util.Mechanism;
import com.stuyfission.fissionlib.input.GamepadStatic;
import org.firstinspires.ftc.teamcode.opmode.teleop.Controls;

@Config
public class Climb extends Mechanism {

    private Servo leftRelease;
    private Servo rightRelease;

    public static double RELEASE_POS = 1;

    public Climb(LinearOpMode opMode) {
        this.opMode = opMode;
    }

    @Override
    public void init(HardwareMap hwMap) {
        leftRelease = hwMap.get(Servo.class, "climbServoLeft");
        rightRelease = hwMap.get(Servo.class, "climbServoRight");

        leftRelease.setDirection(Servo.Direction.REVERSE);
    }

    public void release() {
        leftRelease.setPosition(RELEASE_POS);
        rightRelease.setPosition(RELEASE_POS);
    }

    @Override
    public void loop(Gamepad gamepad) {
        if (GamepadStatic.isButtonPressed(gamepad, Controls.CLIMB)) {
            release();
        }
    }
}

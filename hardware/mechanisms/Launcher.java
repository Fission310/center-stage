package org.firstinspires.ftc.teamcode.hardware.mechanisms;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.stuyfission.fissionlib.util.Mechanism;
import com.stuyfission.fissionlib.input.GamepadStatic;

import org.firstinspires.ftc.teamcode.opmode.teleop.Controls;

@Config
public class Launcher extends Mechanism {

    private Servo launchServo;

    public static double LAUNCH_POS = 0;
    public static double CLOSE_POS = 0.25;

    public Launcher(LinearOpMode opMode) {
        this.opMode = opMode;
    }

    @Override
    public void init(HardwareMap hwMap) {
        launchServo = hwMap.get(Servo.class, "launcherServo");

        launchServo.setPosition(CLOSE_POS);
    }

    @Override
    public void loop(Gamepad gamepad) {
        if (GamepadStatic.isButtonPressed(gamepad, Controls.LAUNCH)) {
            launchServo.setPosition(LAUNCH_POS);
        }
        if (GamepadStatic.isButtonPressed(gamepad, Controls.RETRACT)) {
            launchServo.setPosition(CLOSE_POS);
        }
    }
}

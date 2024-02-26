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
public class Arm extends Mechanism {

    private Servo leftServo;
    private Servo rightServo;

    public static double INTAKE_POS = 0.847;
    public static double SCORE_POS = 0.285;
    public static double AUTO_POS = 0;

    public Arm(LinearOpMode opMode) {
        this.opMode = opMode;
    }

    @Override
    public void init(HardwareMap hwMap) {
        leftServo = hwMap.get(Servo.class, "armLeftServo");
        rightServo = hwMap.get(Servo.class, "armRightServo");

        leftServo.setDirection(Servo.Direction.REVERSE);

        intakePos();
    }

    public void autoPos() {
        leftServo.setPosition(AUTO_POS);
        rightServo.setPosition(AUTO_POS);
    }

    public void scorePos() {
        leftServo.setPosition(SCORE_POS);
        rightServo.setPosition(SCORE_POS);
    }

    public void intakePos() {
        leftServo.setPosition(INTAKE_POS);
        rightServo.setPosition(INTAKE_POS);
    }

    public boolean isUp() {
        return leftServo.getPosition() < 0.34;
    }

    @Override
    public void loop(Gamepad gamepad) {
        if (GamepadStatic.isButtonPressed(gamepad, Controls.GRAB)) {
            scorePos();
        } else if (GamepadStatic.isButtonPressed(gamepad, Controls.SCORE_TWO)) {
            intakePos();
        }
    }
}

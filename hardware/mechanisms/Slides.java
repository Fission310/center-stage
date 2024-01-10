package org.firstinspires.ftc.teamcode.hardware.mechanisms;

import org.firstinspires.ftc.teamcode.opmode.teleop.Controls;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.stuyfission.fissionlib.input.GamepadStatic;
import com.stuyfission.fissionlib.motion.MotionProfiledDcMotor;
import com.stuyfission.fissionlib.util.Mechanism;

@Config
public class Slides extends Mechanism {

    private MotionProfiledDcMotor leftMotor;
    private MotionProfiledDcMotor rightMotor;

    public static double WHEEL_RADIUS = 1.378;
    public static double GEAR_RATIO = 1;
    public static double TICKS_PER_REV = 145.1;

    public static double MAX_VEL = 100;
    public static double MAX_ACCEL = 100;

    public static double RETRACTION_MULTIPLIER = 0.75;

    public static double KP = 0.26;
    public static double KI = 0;
    public static double KD = 0;
    public static double KF = 0;

    public static double LOW_POS = 15;
    public static double MEDIUM_LOW_POS = 30;
    public static double MEDIUM_HIGH_POS = 45;
    public static double HIGH_POS = 63;
    public static double COLLECT_POS = 1.3;

    public Slides(LinearOpMode opMode) {
        this.opMode = opMode;
    }

    @Override
    public void init(HardwareMap hwMap) {
        leftMotor = new MotionProfiledDcMotor(hwMap, "slidesLeftMotor");
        rightMotor = new MotionProfiledDcMotor(hwMap, "slidesRightMotor");

        for (MotionProfiledDcMotor motor : new MotionProfiledDcMotor[] { leftMotor, rightMotor }) {
            motor.setWheelConstants(WHEEL_RADIUS, GEAR_RATIO, TICKS_PER_REV);
            motor.setMotionConstraints(MAX_VEL, MAX_ACCEL);
            motor.setRetractionMultiplier(RETRACTION_MULTIPLIER);
            motor.setPIDCoefficients(KP, KI, KD, KF);
        }

        //leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        leftMotor.setTargetPosition(0);
        rightMotor.setTargetPosition(0);

       // intakePos();
    }

    public void lowPos() {
        leftMotor.setTargetPosition(LOW_POS);
        rightMotor.setTargetPosition(LOW_POS);
    }

    public void mediumLowPos() {
        leftMotor.setTargetPosition(MEDIUM_LOW_POS);
        rightMotor.setTargetPosition(MEDIUM_LOW_POS);
    }

    public void mediumHighPos() {
        leftMotor.setTargetPosition(MEDIUM_HIGH_POS);
        rightMotor.setTargetPosition(MEDIUM_HIGH_POS);
    }

    public void highPos() {
        leftMotor.setTargetPosition(HIGH_POS);
        rightMotor.setTargetPosition(HIGH_POS);
    }

    public void intakePos() {
        leftMotor.setTargetPosition(COLLECT_POS);
        rightMotor.setTargetPosition(COLLECT_POS);
    }

    public void update() {
        leftMotor.update();
        rightMotor.update();
    }

    @Override
    public void loop(Gamepad gamepad) {
        update();
        if (GamepadStatic.isButtonPressed(gamepad, Controls.HIGH)) {
            highPos();
        } else if (GamepadStatic.isButtonPressed(gamepad, Controls.LOW)) {
            lowPos();
        } else if (GamepadStatic.isButtonPressed(gamepad, Controls.MEDIUM_LOW)) {
            mediumLowPos();
        } else if (GamepadStatic.isButtonPressed(gamepad, Controls.MEDIUM_HIGH)) {
            mediumHighPos();
        } else if (GamepadStatic.isButtonPressed(gamepad, Controls.RESET)) {
            intakePos();
        }
    }
}

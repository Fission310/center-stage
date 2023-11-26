package org.firstinspires.ftc.teamcode.hardware.mechanisms;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
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

    public static double KP = 0;
    public static double KI = 0;
    public static double KD = 0;
    public static double KF = 0;

    public static double LOW_POS = 0;
    public static double MEDIUM_POS = 0;
    public static double HIGH_POS = 0;
    public static double COLLECT_POS = 0;

    public Slides(LinearOpMode opMode) {
        this.opMode = opMode;
    }

    @Override
    public void init(HardwareMap hwMap) {
        leftMotor = new MotionProfiledDcMotor(hwMap, "leftSlidesMotor");
        rightMotor = new MotionProfiledDcMotor(hwMap, "rightSlidesMotor");

        for (MotionProfiledDcMotor motor : new MotionProfiledDcMotor[] { leftMotor, rightMotor }) {
            motor.setWheelConstants(WHEEL_RADIUS, GEAR_RATIO, TICKS_PER_REV);
            motor.setMotionConstraints(MAX_VEL, MAX_ACCEL);
            motor.setRetractionMultiplier(RETRACTION_MULTIPLIER);
            motor.setPIDCoefficients(KP, KI, KD, KF);
            motor.setTargetPosition(0);
        }

        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void lowPos() {
        leftMotor.setTargetPosition(LOW_POS);
        rightMotor.setTargetPosition(LOW_POS);
    }

    public void mediumPos() {
        leftMotor.setTargetPosition(MEDIUM_POS);
        rightMotor.setTargetPosition(MEDIUM_POS);
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
}

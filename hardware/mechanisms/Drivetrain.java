package org.firstinspires.ftc.teamcode.hardware.mechanisms;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.stuyfission.fissionlib.util.Mechanism;

@Config
public class Drivetrain extends Mechanism {

    public static double TURN_SPEED = 0.9;
    public static double POWER = 2;

    private double speed = 1;
    private int reverse = 1;

    private SampleMecanumDrive drivetrain;

    public Drivetrain(LinearOpMode opMode) {
        this.opMode = opMode;
    }

    @Override
    public void init(HardwareMap hwMap) {
        drivetrain = new SampleMecanumDrive(hwMap);
        drivetrain.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void setReverse(boolean reverse) {
        if (reverse) {
            this.reverse = -1;
        } else {
            this.reverse = 1;
        }
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public SampleMecanumDrive getDrive() {
        return this.drivetrain;
    }

    @Override
    public void loop(Gamepad gamepad) {
        double leftStickY = max(gamepad.left_stick_y, gamepad.left_stick_y);
        double leftStickX = max(gamepad.left_stick_x, gamepad.left_stick_x);
        double rightStickX = max(gamepad.right_stick_x, gamepad.right_stick_x);
        drivetrain.setWeightedDrivePower(
                new Pose2d(pow(-leftStickY * speed, POWER), pow(-leftStickX * speed, POWER),
                        pow(-rightStickX * TURN_SPEED * speed, POWER)));
        drivetrain.update();
    }

    private static double max(double num1, double num2) {
        if (Math.abs(num1) > Math.abs(num2)) {
            return num1;
        } else {
            return num2;
        }
    }

    private static double pow(double base, double power) {
        return Math.signum(base) * Math.pow(Math.abs(base), power);
    }
}

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

    private SampleMecanumDrive drivetrain;

    public Drivetrain(LinearOpMode opMode) {
        this.opMode = opMode;
    }

    @Override
    public void init(HardwareMap hwMap) {
        drivetrain = new SampleMecanumDrive(hwMap);
        drivetrain.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void loop(Gamepad gamepad) {
        drivetrain.setWeightedDrivePower(
                new Pose2d(pow(-gamepad.left_stick_y, POWER), pow(-gamepad.left_stick_x, POWER),
                        pow(-gamepad.right_stick_x * TURN_SPEED, POWER)));
        drivetrain.update();
    }

    private static double pow(double base, double power) {
        return Math.signum(base) * Math.pow(Math.abs(base), power);
    }
}

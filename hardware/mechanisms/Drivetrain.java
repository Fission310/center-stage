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
                new Pose2d(Math.pow(-gamepad.left_stick_y, 3), Math.pow(-gamepad.left_stick_x, 3), Math.pow(-gamepad.right_stick_x * TURN_SPEED, 3)));
        drivetrain.update();
    }
}

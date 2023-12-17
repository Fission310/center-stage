package org.firstinspires.ftc.teamcode.hardware.mechanisms;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.stuyfission.fissionlib.util.Mechanism;
import com.stuyfission.fissionlib.input.GamepadStatic;
import org.firstinspires.ftc.teamcode.opmode.teleop.Controls;

@Config
public class Intake extends Mechanism {

    private DcMotorSimple lowerMotor;
    private DcMotorSimple upperMotor;

    private Servo leftServo;
    private Servo rightServo;

    public static double SPEED = 1;
    
    public static double UP_POS = 0;
    public static double DOWN_POS = 0.67;

    public Intake(LinearOpMode opMode) {
        this.opMode = opMode;
    }

    @Override
    public void init(HardwareMap hwMap) {
        lowerMotor = hwMap.get(DcMotorSimple.class, "lowerIntakeMotor");
        upperMotor = hwMap.get(DcMotorSimple.class, "upperIntakeMotor");

        leftServo = hwMap.get(Servo.class, "leftIntakeServo");
        rightServo = hwMap.get(Servo.class, "rightIntakeServo");

        leftServo.setDirection(Servo.Direction.REVERSE);

//        down();
    }

    public void intake() {
        lowerMotor.setPower(-SPEED);
        upperMotor.setPower(SPEED);
    }

    public void outtake() {
        lowerMotor.setPower(SPEED);
        upperMotor.setPower(-SPEED);
    }

    public void stop() {
        lowerMotor.setPower(0);
        upperMotor.setPower(0);
    }

    public void up() {
        leftServo.setPosition(UP_POS);
        rightServo.setPosition(UP_POS);
    }

    public void down() {
        leftServo.setPosition(DOWN_POS);
        rightServo.setPosition(DOWN_POS);
    }

    @Override
    public void loop(Gamepad gamepad) {
        if (GamepadStatic.isButtonPressed(gamepad, Controls.INTAKE_UP)) {
            up();
        } else if (GamepadStatic.isButtonPressed(gamepad, Controls.INTAKE_DOWN)) {
            down();
        }

        if (GamepadStatic.isButtonPressed(gamepad, Controls.INTAKE)) {
            intake();
        } else if (GamepadStatic.isButtonPressed(gamepad, Controls.OUTTAKE)) {
            outtake();
        } else {
            stop();
        }
    }
}

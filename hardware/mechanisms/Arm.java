package org.firstinspires.ftc.teamcode.hardware.mechanisms;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.stuyfission.fissionlib.util.Mechanism;

@Config
public class Arm extends Mechanism {

    private Servo leftServo;
    private Servo rightServo;

    public static double INTAKE_POS = 0.0;
    public static double SCORE_POS = 0.5;

    public Arm(LinearOpMode opMode) {
        this.opMode = opMode;
    }

    @Override
    public void init(HardwareMap hwMap) {
        leftServo = hwMap.get(Servo.class, "leftArmServo");
        rightServo = hwMap.get(Servo.class, "rightArmServo");

        leftServo.setDirection(Servo.Direction.REVERSE);
    }

    public void scorePos() {
        leftServo.setPosition(SCORE_POS);
        rightServo.setPosition(SCORE_POS);
    }

    public void intakePos() {
        leftServo.setPosition(INTAKE_POS);
        rightServo.setPosition(INTAKE_POS);
    }
}

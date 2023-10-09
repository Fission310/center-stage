package org.firstinspires.ftc.teamcode.hardware.mechanisms;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.stuyfission.fissionlib.util.Mechanism;

@Config
public class Dropper extends Mechanism {

    private Servo releaseServo;

    public static double RELEASE_POS_1 = 0.0;
    public static double RELEASE_POS_2 = 0.0;
    public static double CLOSE_POS = 0.5;

    private boolean firstRelease = false;

    public Dropper(LinearOpMode opMode) {
        this.opMode = opMode;
    }

    @Override
    public void init(HardwareMap hwMap) {
        releaseServo = hwMap.get(Servo.class, "dropperServo");
    }

    public void release() {
        if (firstRelease) {
            releaseServo.setPosition(RELEASE_POS_2);
        } else {
            releaseServo.setPosition(RELEASE_POS_1);
            firstRelease = true;
        }
    }

    public void hook() {
        releaseServo.setPosition(CLOSE_POS);
        firstRelease = false;
    }
}

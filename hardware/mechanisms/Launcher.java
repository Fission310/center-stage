package org.firstinspires.ftc.teamcode.hardware.mechanisms;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.stuyfission.fissionlib.util.Mechanism;
import com.stuyfission.fissionlib.command.Command;
import com.stuyfission.fissionlib.command.CommandSequence;
import com.stuyfission.fissionlib.input.GamepadStatic;

import org.firstinspires.ftc.teamcode.opmode.teleop.Controls;

@Config
public class Launcher extends Mechanism {

    private Servo launchServo;
    private Servo leftAngleServo;
    private Servo rightAngleServo;

    public static double LAUNCH_POS = 0.6;
    public static double CLOSE_POS = 0.4;
    public static double FLAT_POS = 0;
    public static double ANGLE_POS = 0.17;

    public static double ANGLE_DELAY = 1;

    public Launcher(LinearOpMode opMode) {
        this.opMode = opMode;
    }

    @Override
    public void init(HardwareMap hwMap) {
        launchServo = hwMap.get(Servo.class, "launcherServo");
        leftAngleServo = hwMap.get(Servo.class, "launcherLeftServo");
        rightAngleServo = hwMap.get(Servo.class, "launcherRightServo");

        rightAngleServo.setDirection(Servo.Direction.REVERSE);

        launchServo.setPosition(CLOSE_POS);
        leftAngleServo.setPosition(FLAT_POS);
        rightAngleServo.setPosition(FLAT_POS);
    }

    private Command launch = () -> launchServo.setPosition(LAUNCH_POS);
    private Command angle = () -> {
        leftAngleServo.setPosition(ANGLE_POS);
        rightAngleServo.setPosition(ANGLE_POS);
    };
    private CommandSequence launchSequence = new CommandSequence()
            .addCommand(angle)
            .addWaitCommand(ANGLE_DELAY)
            .addCommand(launch)
            .build();

    @Override
    public void loop(Gamepad gamepad) {
        if (GamepadStatic.isButtonPressed(gamepad, Controls.LAUNCH)) {
            launchSequence.trigger();
        }
    }
}

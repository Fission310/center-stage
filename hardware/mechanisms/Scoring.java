package org.firstinspires.ftc.teamcode.hardware.mechanisms;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.opmode.teleop.Controls;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.stuyfission.fissionlib.command.Command;
import com.stuyfission.fissionlib.command.CommandSequence;
import com.stuyfission.fissionlib.input.GamepadStatic;
import com.stuyfission.fissionlib.util.Mechanism;

@Config
public class Scoring extends Mechanism {

    private Arm arm = new Arm(opMode);
    private Hopper hopper = new Hopper(opMode);
    private Slides2 slides = new Slides2(opMode);

    public static double SCORE_DELAY = 1;
    public static double ARM_DELAY = 0.5;

    public Scoring(LinearOpMode opMode) {
        this.opMode = opMode;
    }

    private Command releaseCommand = () -> hopper.release();
    private Command armCommand = () -> arm.scorePos();
    private Command retractCommand = () -> {
        hopper.release();
        arm.intakePos();
        slides.intakePos();
        hopper.close();
    };

    private CommandSequence armSequence = new CommandSequence()
            .addWaitCommand(ARM_DELAY)
            .addCommand(armCommand)
            .build();
    private CommandSequence scoreSequence = new CommandSequence()
            .addCommand(releaseCommand)
            .addWaitCommand(SCORE_DELAY)
            .addCommand(retractCommand)
            .build();
    private CommandSequence retractSequence = new CommandSequence()
            .addCommand(retractCommand)
            .build();

    @Override
    public void init(HardwareMap hwMap) {
        arm.init(hwMap);
        hopper.init(hwMap);
        slides.init(hwMap);
    }

    @Override
    public void telemetry(Telemetry telemetry) {
        slides.telemetry(telemetry);
    }

    @Override
    public void loop(Gamepad gamepad) {
        slides.update();

        if (GamepadStatic.isButtonPressed(gamepad, Controls.SCORE)) {
            scoreSequence.run();
        }

        if (GamepadStatic.isButtonPressed(gamepad, Controls.RESET)) {
            retractSequence.run();
        }

        if (GamepadStatic.isButtonPressed(gamepad, Controls.LOW)) {
            slides.lowPos();
            armSequence.run();
        } else if (GamepadStatic.isButtonPressed(gamepad, Controls.MEDIUM_LOW)) {
            slides.mediumLowPos();
            armSequence.run();
        } else if (GamepadStatic.isButtonPressed(gamepad, Controls.MEDIUM_HIGH)) {
            slides.mediumHighPos();
            armSequence.run();
        } else if (GamepadStatic.isButtonPressed(gamepad, Controls.HIGH)) {
            slides.highPos();
            armSequence.run();
        }
    }
}

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
    private Claw claw = new Claw(opMode);
    private Intake intake = new Intake(opMode);
    private Slides2 slides = new Slides2(opMode);
    private Wrist wrist = new Wrist(opMode);

    public static double SLIDES_INTAKE = -55;

    public static double SCORE_DELAY = 1;
    public static double SLIDES_DELAY = 0.5;
    public static double ARM_DELAY = 0.5;
    public static double PLATFORM_DELAY = 2;
    public static double GRAB_DELAY = 0.5;

    public Scoring(LinearOpMode opMode) {
        this.opMode = opMode;
    }

    private Command pixelPlatformUp = () -> intake.pixelUp();
    private Command pixelPlatformDown = () -> {
        claw.close();
        intake.pixelDown();
    };
    private Command grabCommand = () -> claw.close();
    private Command slidesUp = () -> slides.setTarget(SLIDES_INTAKE);
    private Command releaseLeftCommand = () -> claw.leftOpen();
    private Command releaseRightCommand = () -> claw.rightOpen();
    private Command armCommand = () -> arm.scorePos();
    private Command wristCommand = () -> wrist.left();
    private Command retractCommand = () -> {
        claw.leftOpen();
        claw.rightOpen();

        while (wrist.getPos() != 0) {
            wrist.left();
        }

        arm.intakePos();
        slides.intakePos();
    };

    private CommandSequence pixelSequence = new CommandSequence()
            .addCommand(slidesUp)
            .addCommand(pixelPlatformUp)
            .addWaitCommand(PLATFORM_DELAY)
            .addCommand(grabCommand)
            .addWaitCommand(GRAB_DELAY)
            .addCommand(pixelPlatformDown)
            .build();
    private CommandSequence armSequence = new CommandSequence()
            .addWaitCommand(SLIDES_DELAY)
            .addCommand(armCommand)
            .addWaitCommand(ARM_DELAY)
            .addCommand(wristCommand)
            .build();
    private CommandSequence scoreLeft = new CommandSequence()
            .addCommand(releaseLeftCommand)
            .build();
    private CommandSequence scoreRight = new CommandSequence()
            .addCommand(releaseRightCommand)
            .build();
    private CommandSequence retractSequence = new CommandSequence()
            .addWaitCommand(SCORE_DELAY)
            .addCommand(retractCommand)
            .build();

    @Override
    public void init(HardwareMap hwMap) {
        arm.init(hwMap);
        claw.init(hwMap);
        intake.init(hwMap);
        slides.init(hwMap);
        wrist.init(hwMap);
    }

    @Override
    public void telemetry(Telemetry telemetry) {
        slides.telemetry(telemetry);
    }

    @Override
    public void loop(Gamepad gamepad) {
        slides.update();

        if (intake.numPixels() > 1 || GamepadStatic.isButtonPressed(gamepad, Controls.GRAB)) {
            pixelSequence.trigger();
        }

        if (claw.numPixels() == 0 && arm.isUp()) {
            retractSequence.trigger();
        }

        if (arm.isUp()) {
            wrist.loop(gamepad);
        }

        if (GamepadStatic.isButtonPressed(gamepad, Controls.SCORE_ONE)) {
            if (claw.numPixels() == 2) {
                scoreLeft.trigger();
            } else {
                scoreRight.trigger();
            }
        }

        if (GamepadStatic.isButtonPressed(gamepad, Controls.SCORE_TWO)) {
            scoreLeft.trigger();
            scoreRight.trigger();
        }

        if (GamepadStatic.isButtonPressed(gamepad, Controls.RESET)) {
            retractSequence.trigger();
        }

        for (int i = 0; i < 4; i++) {
            if (GamepadStatic.isButtonPressed(gamepad, Controls.SLIDES[i])) {
                slides.goToPos(i);
                if (!arm.isUp()) {
                    armSequence.trigger();
                }
                break;
            }
        }
    }
}

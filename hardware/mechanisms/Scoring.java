package org.firstinspires.ftc.teamcode.hardware.mechanisms;

import java.lang.Thread.State;

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
    private Drivetrain drive = new Drivetrain(opMode);
    private Intake intake = new Intake(opMode);
    private Slides2 slides = new Slides2(opMode);
    private Wrist wrist = new Wrist(opMode);
    private Climb climb = new Climb(opMode);
    private Launcher launcher = new Launcher(opMode);

    public static double SLIDES_INTAKE = -55;

    public static double RESET_DELAY = 1.3;
    public static double SCORE_DELAY = 0.4;
    public static double SLIDES_DELAY = 0.1;
    public static double ARM_DELAY = 0.1;
    public static double PLATFORM_DELAY = 1;
    public static double PLATFORM_DOWN_DELAY = 0.1;
    public static double DT_SLOW = 0.8;

    private boolean clawClicked = false;
    private boolean stackClicked = false;
    private boolean hasTwo = false;
    private boolean up = false;
    private boolean down = false;
    private boolean makeDown = false;
    private boolean isGrab = false;

    private int slidesPos = 0;

    private State state = State.INTAKE;

    private enum State {
        INTAKE,
        TRANSFER,
        CLAW,
        SCORING
    }

    public Scoring(LinearOpMode opMode) {
        this.opMode = opMode;
    }

    private Command setStateClaw = () -> state = State.CLAW;
    private Command slidesScore = () -> slides.goToPos(slidesPos);
    private Command pixelPlatformUp = () -> intake.pixelUp();
    private Command pixelPlatformDown = () -> intake.pixelDown();
    private Command grabCommand = () -> claw.close();
    private Command slidesUp = () -> slides.setTarget(SLIDES_INTAKE);
    private Command releaseLeftCommand = () -> claw.leftOpen();
    private Command releaseRightCommand = () -> claw.rightOpen();
    private Command armCommand = () -> arm.scorePos();
    private Command intakeCommand = () -> intake.intake();
    private Command wristCommand = () -> wrist.scorePos();
    private Command intakeUp = () -> intake.up();
    private Command intakeUpClimb = () -> intake.climbUp();
    private Command outtakeCommand = () -> intake.outtake();
    private Command setPixels = () -> {
        if (intake.numPixels() == 2)
            hasTwo = true;
    };
    private Command stopCommand = () -> intake.stop();
    private Command retractCommand = () -> {
        claw.leftOpen();
        claw.rightOpen();
        wrist.intakePos();
        arm.intakePos();
        slides.intakePos();
        intake.intake();
        intake.down();
    };

    private CommandSequence countPixels = new CommandSequence()
            .addWaitCommand(0.1)
            .addCommand(setPixels)
            .build();
    private CommandSequence intakeABit = new CommandSequence()
            .addCommand(intakeCommand)
            .addWaitCommand(0.2)
            .addCommand(stopCommand)
            .build();
    private CommandSequence outtakeABit = new CommandSequence()
            .addCommand(outtakeCommand)
            .addWaitCommand(0.6)
            .addCommand(stopCommand)
            .build();
    private CommandSequence pixelSequence = new CommandSequence()
            .addCommand(stopCommand)
            .addCommand(intakeUpClimb)
            .addCommand(slidesUp)
            .addCommand(intakeCommand)
            .addCommand(pixelPlatformUp)
            .addWaitCommand(PLATFORM_DELAY)
            .addCommand(grabCommand)
            .addCommand(setStateClaw)
            .build();
    private CommandSequence pixelDown = new CommandSequence()
            .addCommand(pixelPlatformDown)
            .build();
    private CommandSequence pixelReset = new CommandSequence()
            .addCommand(pixelPlatformUp)
            .addWaitCommand(RESET_DELAY)
            .addCommand(grabCommand)
            .build();
    private CommandSequence armSequence = new CommandSequence()
            .addCommand(pixelPlatformDown)
            .addWaitCommand(PLATFORM_DOWN_DELAY)
            .addCommand(slidesScore)
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
        climb.init(hwMap);
        arm.init(hwMap);
        claw.init(hwMap);
        drive.init(hwMap);
        intake.init(hwMap);
        slides.init(hwMap);
        wrist.init(hwMap);
        launcher.init(hwMap);
    }

    @Override
    public void telemetry(Telemetry telemetry) {
        slides.telemetry(telemetry);
        intake.telemetry(telemetry);
        telemetry.addData("State", state);
        telemetry.addData("3rd Pixel", intake.isThirdPixel());
        telemetry.addData("Num Pixels", intake.numPixels());
        telemetry.addData("lock", intake.isLocked());
        telemetry.update();
    }

    @Override
    public void loop(Gamepad gamepad) {
        drive.loop(gamepad);

        slides.update();

        intake.isThirdPixel();
        switch (state) {
            case INTAKE:
                drive.setSpeed(1);
                drive.setReverse(false);
                intake.loop(gamepad);

                if (hasTwo || (GamepadStatic.isButtonPressed(gamepad, Controls.GRAB) && !isGrab)) {
                    state = State.TRANSFER;
                    hasTwo = false;
                    makeDown = false;
                    if (GamepadStatic.isButtonPressed(gamepad, Controls.GRAB)) {
                        pixelSequence.trigger();
                    }
                    break;
                }

                if (GamepadStatic.isButtonPressed(gamepad, Controls.SCORE_TWO)) {
                    intake.lockThird();
                }

                if (isGrab && !GamepadStatic.isButtonPressed(gamepad, Controls.GRAB)) {
                    isGrab = false;
                }

                if (intake.numPixels() > 1 && !hasTwo && !makeDown) {
                    countPixels.trigger();
                }

                if (intake.numPixels() < 2) {
                    makeDown = false;
                }

                if (GamepadStatic.isButtonPressed(gamepad, Controls.CLIMB)) {
                    intake.climbUp();
                    climb.release();
                }

                launcher.loop(gamepad);

                if (GamepadStatic.isButtonPressed(gamepad, Controls.INTAKE_TOGGLE)) {
                    if (!stackClicked) {
                        intake.toggle();
                        stackClicked = true;
                    }
                } else {
                    stackClicked = false;
                }

                for (int i = 0; i < 4; i++) {
                    if (GamepadStatic.isButtonPressed(gamepad, Controls.SLIDES[i])) {
                        slidesPos = i;
                        claw.close();
                        armSequence.trigger();
                        state = State.SCORING;
                        break;
                    }
                }
                break;
            case TRANSFER:
                if (!pixelSequence.hasCompleted) {
                    break;
                }
                drive.setSpeed(1);
                drive.setReverse(false);
                if (intake.numPixels() == 0 && !intake.isPixelUp()) {
                    state = State.INTAKE;
                    intake.down();
                    break;
                }

                if (GamepadStatic.isButtonPressed(gamepad, Controls.SCORE_TWO)) {
                    intake.lockThird();
                }

                if (intake.isThirdPixel() && intake.numPixels() == 2) {
                    intake.outtake();
                } else if (intake.isThirdPixel() && intake.numPixels() == 1) {
                    intakeABit.trigger();
                } else {
                    pixelSequence.trigger();
                }

                break;
            case CLAW:
                if (intake.isThirdPixel()) {
                    intake.outtake();
                }

                if (GamepadStatic.isButtonPressed(gamepad, Controls.SCORE_TWO)) {
                    intake.lockThird();
                }

                if (GamepadStatic.isButtonPressed(gamepad, Controls.OUTTAKE)) {
                    intake.outtake();
                }

                if (GamepadStatic.isButtonPressed(gamepad, Controls.GRAB)) {
                    claw.leftOpen();
                    claw.rightOpen();
                    pixelDown.trigger();
                    intake.down();
                    makeDown = true;
                    isGrab = true;
                    state = State.INTAKE;
                    break;
                }

                if (GamepadStatic.isButtonPressed(gamepad, Controls.SCORE_TWO)) {
                    claw.leftOpen();
                    claw.rightOpen();
                    pixelReset.trigger();
                }

                for (int i = 0; i < 4; i++) {
                    if (GamepadStatic.isButtonPressed(gamepad, Controls.SLIDES[i])) {
                        slidesPos = i;
                        armSequence.trigger();
                        state = State.SCORING;
                        break;
                    }
                }
                break;
            case SCORING:
                drive.setSpeed(DT_SLOW);
                drive.setReverse(true);
                wrist.loop(gamepad);

                if (claw.numPixels() == 0) {
                    retractSequence.trigger();
                    state = State.INTAKE;
                    break;
                }

                if (GamepadStatic.isButtonPressed(gamepad, Controls.SLIDES_UP)) {
                    if (!up)
                        slides.upABit();
                    up = true;
                } else {
                    up = false;
                }

                if (GamepadStatic.isButtonPressed(gamepad, Controls.SLIDES_DOWN)) {
                    if (!down)
                        slides.downABit();
                    down = true;
                } else {
                    down = false;
                }

                if (GamepadStatic.isButtonPressed(gamepad, Controls.SCORE_ONE)) {
                    if (!clawClicked && claw.numPixels() == 2) {
                        scoreLeft.trigger();
                        clawClicked = true;
                    } else if (!clawClicked) {
                        scoreRight.trigger();
                        clawClicked = true;
                    }
                } else {
                    clawClicked = false;
                }

                if (GamepadStatic.isButtonPressed(gamepad, Controls.SCORE_TWO)) {
                    scoreLeft.trigger();
                    scoreRight.trigger();
                }

                for (int i = 0; i < 4; i++) {
                    if (GamepadStatic.isButtonPressed(gamepad, Controls.SLIDES[i])) {
                        slidesPos = i;
                        slides.goToPos(i);
                        break;
                    }
                }
                break;
        }
    }
}

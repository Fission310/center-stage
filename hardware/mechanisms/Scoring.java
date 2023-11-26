package org.firstinspires.ftc.teamcode.hardware.mechanisms;

import org.firstinspires.ftc.teamcode.opmode.teleop.Controls;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.stuyfission.fissionlib.command.Command;
import com.stuyfission.fissionlib.command.CommandMachine;
import com.stuyfission.fissionlib.command.CommandSequence;
import com.stuyfission.fissionlib.input.GamepadStatic;
import com.stuyfission.fissionlib.util.Mechanism;

public class Scoring extends Mechanism {

    private Arm arm = new Arm(opMode);
    private Hopper hopper = new Hopper(opMode);
    private Intake intake = new Intake(opMode);
    private IntakeSensor intakeSensor = new IntakeSensor(opMode);
    private Slides slides = new Slides(opMode);

    public static double INTAKE_DELAY = 1;
    public static double SCORE_DELAY = 1;

    public Scoring(LinearOpMode opMode) {
        this.opMode = opMode;
    }

    private Command releaseCommand = () -> hopper.release();
    private Command intakeCommand = () -> intake.intake();
    private Command intakeStopCommand = () -> intake.stop();
    private Command retractCommand = () -> {
        hopper.release();
        slides.intakePos();
        arm.intakePos();
        hopper.close();
    };

    private CommandSequence intakeSequence = new CommandSequence()
            .addCommand(intakeCommand)
            .addWaitCommand(INTAKE_DELAY)
            .addCommand(intakeStopCommand)
            .build();
    private CommandSequence scoreSequence = new CommandSequence()
            .addCommand(releaseCommand)
            .addWaitCommand(SCORE_DELAY)
            .addCommand(retractCommand)
            .build();
    private CommandSequence retractSequence = new CommandSequence()
            .addCommand(retractCommand)
            .build();

    private CommandMachine scoreMachine = new CommandMachine()
            .addCommandSequence(intakeSequence, Controls.INTAKE)
            .addCommandSequence(intakeSequence, Controls.INTAKE)
            .addCommandSequence(scoreSequence, Controls.SCORE)
            .build();

    @Override
    public void init(HardwareMap hwMap) {
        arm.init(hwMap);
        hopper.init(hwMap);
        intake.init(hwMap);
        intakeSensor.init(hwMap);
        slides.init(hwMap);
    }

    @Override
    public void loop(Gamepad gamepad) {
        slides.update();
        scoreMachine.run(gamepad);

        if (GamepadStatic.isButtonPressed(gamepad, Controls.RESET)) {
            retractSequence.run();
            scoreMachine.reset();
        }

        if (scoreMachine.getCurrentCommandIndex() == 0 || scoreMachine.getCurrentCommandIndex() == 1) {
            if (intakeSensor.isPixel()) {
                scoreMachine.next();
            }
        }

        if (scoreMachine.getCurrentCommandIndex() == 2) {
            if (GamepadStatic.isButtonPressed(gamepad, Controls.LOW)) {
                slides.lowPos();
                arm.scorePos();
            } else if (GamepadStatic.isButtonPressed(gamepad, Controls.MEDIUM)) {
                slides.mediumPos();
                arm.scorePos();
            } else if (GamepadStatic.isButtonPressed(gamepad, Controls.HIGH)) {
                slides.highPos();
                arm.scorePos();
            }
        }
    }
}

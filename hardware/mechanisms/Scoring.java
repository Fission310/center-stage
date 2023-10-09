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

    private Dropper dropper = new Dropper(opMode);
    private Slides slides = new Slides(opMode);

    public Scoring(LinearOpMode opMode) {
        this.opMode = opMode;
    }

    private Command releaseCommand = () -> {
        dropper.release();
    };

    private Command preScoreCommand = () -> {
        dropper.hook();
        slides.scorePos();
    };

    private Command retractCommand = () -> {
        dropper.release();
        slides.intakePos();
    };

    private CommandSequence scoreSequence = new CommandSequence().addCommand(releaseCommand).build();
    private CommandSequence preScoreSequence = new CommandSequence().addCommand(preScoreCommand).build();
    private CommandSequence resetSequence = new CommandSequence().addCommand(retractCommand).build();

    private CommandMachine scoreMachine = new CommandMachine().addCommandSequence(preScoreSequence, Controls.PRE_SCORE)
            .addCommandSequence(scoreSequence, Controls.SCORE)
            .addCommandSequence(scoreSequence, Controls.SCORE)
            .addCommandSequence(resetSequence, Controls.RESET).build();

    @Override
    public void init(HardwareMap hwMap) {
        dropper.init(hwMap);
        slides.init(hwMap);
    }

    @Override
    public void loop(Gamepad gamepad) {
        slides.update();
        scoreMachine.run(gamepad);

        if (GamepadStatic.isButtonPressed(gamepad, Controls.RESET)) {
            resetSequence.run();
            scoreMachine.reset();
        }
    }
}

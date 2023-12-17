package org.firstinspires.ftc.teamcode.hardware;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.hardware.mechanisms.Climb;
import org.firstinspires.ftc.teamcode.hardware.mechanisms.Drivetrain;
import org.firstinspires.ftc.teamcode.hardware.mechanisms.Intake;
import org.firstinspires.ftc.teamcode.hardware.mechanisms.Launcher;
import org.firstinspires.ftc.teamcode.hardware.mechanisms.Scoring;
import org.firstinspires.ftc.teamcode.hardware.mechanisms.Webcam;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.stuyfission.fissionlib.util.Mechanism;

public class Robot extends Mechanism {

    private Drivetrain drivetrain = new Drivetrain(opMode);
    private Scoring scoring = new Scoring(opMode);
    private Intake intake = new Intake(opMode);
    private Climb climb = new Climb(opMode);
    private Launcher launcher = new Launcher(opMode);

    public Robot(LinearOpMode opMode) {
        this.opMode = opMode;
    }

    @Override
    public void init(HardwareMap hwMap) {
        drivetrain.init(hwMap);
        scoring.init(hwMap);
        intake.init(hwMap);
        climb.init(hwMap);
        launcher.init(hwMap);
    }

    @Override
    public void telemetry(Telemetry telemetry) {
        scoring.telemetry(telemetry);
    }

    @Override
    public void loop(Gamepad gamepad) {
        drivetrain.loop(gamepad);
        scoring.loop(gamepad);
        intake.loop(gamepad);
        climb.loop(gamepad);
        launcher.loop(gamepad);
    }
}

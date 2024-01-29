package org.firstinspires.ftc.teamcode.hardware.mechanisms;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.stuyfission.fissionlib.input.GamepadStatic;
import com.stuyfission.fissionlib.util.Mechanism;

import org.firstinspires.ftc.teamcode.opmode.teleop.Controls;
import org.firstinspires.ftc.teamcode.util.PIDController;
import org.firstinspires.ftc.robotcore.external.Telemetry;


@Config
public class Slides2 extends Mechanism {

    public static double LOW_POS = 10;
    public static double MEDIUM_LOW_POS = 350;
    public static double MEDIUM_HIGH_POS = 600;
    public static double HIGH_POS = 845;
    public static double COLLECT_POS = -55;
    public static int SPEED = 50;

    private static double[] POSITIONS = {LOW_POS, MEDIUM_LOW_POS, MEDIUM_HIGH_POS, HIGH_POS};

    public static double KP = 0.003;
    public static double KI = 0;
    public static double KD = 0;

    public static double MIN_POWER = -0.3;

    public static double target = 0;
    public static double power = 0;

    private final PIDController controller = new PIDController(KP, KI, KD);

    private final DcMotorEx[] motors = new DcMotorEx[2];

    public Slides2(LinearOpMode opMode) {
        this.opMode = opMode;
    }

    @Override
    public void init(HardwareMap hwMap) {
        motors[0] = hwMap.get(DcMotorEx.class, "slidesLeftMotor");
        motors[1] = hwMap.get(DcMotorEx.class, "slidesRightMotor");

        motors[0].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motors[1].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motors[0].setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER); // might be wrong RunMode
        motors[1].setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        motors[0].setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        motors[1].setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        motors[0].setDirection(DcMotorEx.Direction.REVERSE);
        motors[1].setDirection(DcMotorEx.Direction.FORWARD);

        intakePos();
    }

    public void telemetry(Telemetry telemetry) {
        telemetry.addData("Current Position", getPosition());
        telemetry.addData("Target", target);
        telemetry.addData("Power", power);
        telemetry.update();
    }

    public void intakePos() {
        setTarget(COLLECT_POS);
    }

    public void goToPos(int pos) {
        setTarget(POSITIONS[pos]);
    }

    public void setTarget(double target) {
        Slides2.target = target;
    }

    public double getPosition() {
        return motors[0].getCurrentPosition();
    }

    public void upABit() {
        setTarget(target + SPEED);
    }

    public void downABit() {
        setTarget(target - SPEED);
    }

    public void update() {
        controller.setTarget(target);
        power = controller.calculate(getPosition());
        if (power < MIN_POWER) {
            power = MIN_POWER;
        }
        motors[0].setPower(power);
        motors[1].setPower(power);
    }       
    
    @Override
    public void loop(Gamepad gamepad) {
        update();
        if (GamepadStatic.isButtonPressed(gamepad, Controls.LOW)) {
            goToPos(0);
        } else if (GamepadStatic.isButtonPressed(gamepad, Controls.MEDIUM_LOW)) {
            goToPos(1);
        } else if (GamepadStatic.isButtonPressed(gamepad, Controls.MEDIUM_HIGH)) {
            goToPos(2);
        } else if(GamepadStatic.isButtonPressed(gamepad, Controls.HIGH)) {
            goToPos(3);
        }
    }
}

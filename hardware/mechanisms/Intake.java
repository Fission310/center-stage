package org.firstinspires.ftc.teamcode.hardware.mechanisms;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorRangeSensor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.stuyfission.fissionlib.util.Mechanism;

import com.stuyfission.fissionlib.command.Command;
import com.stuyfission.fissionlib.command.CommandSequence;
import com.stuyfission.fissionlib.input.GamepadStatic;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.opmode.teleop.Controls;

@Config
public class Intake extends Mechanism {

    private DcMotorSimple intakeMotor;

    private Servo leftServo;
    private Servo rightServo;
    private Servo pixelServo;

    private IntakeSensor topSensor;
    private IntakeSensor bottomSensor;

    public static double SPEED = 0.82;
    public static double SLOW_SPEED = 0.6;

    public double motorSpeed = SPEED;

    public static double UP_AUTO_POS = 1;
    public static double UP_POS = 0.88;
    public static double DOWN_POS = 0.72;

    public static double PIXEL_UP_POS = 0.85;
    public static double PIXEL_MIDDLE_POS = 0.7;
    public static double PIXEL_DOWN_POS = 0.38;

    public static double INTAKE_DOWN_DELAY = 1;
    public static double INTAKE_UP_DELAY = 0.6;
    public static double SENSOR_DELAY = 0.5;

    public static int FAR = 18;

    private boolean isPixelUp = false;
    private boolean isUp = false;

    private boolean lock = false;

    private Command pixelDown = () -> {
        lock = true;
        motorSpeed = SLOW_SPEED;
        outtake();
        pixelServo.setPosition(PIXEL_DOWN_POS);
    };

    private Command pixelUp = () -> {
        motorSpeed = SLOW_SPEED;
        intake();
        pixelServo.setPosition(PIXEL_MIDDLE_POS);
    };

    private Command pixelFullyUp = () -> pixelServo.setPosition(PIXEL_UP_POS);

    private Command setPixelUp = () -> isPixelUp = false;

    private Command intakeStop = () -> {
        stop();
        motorSpeed = SPEED;
    };

    private CommandSequence pixelDownSequence = new CommandSequence()
            .addCommand(pixelDown)
            .addWaitCommand(INTAKE_DOWN_DELAY)
            .addCommand(setPixelUp)
            .addCommand(intakeStop)
            .build();

    private CommandSequence pixelUpSequence = new CommandSequence()
            .addWaitCommand(SENSOR_DELAY)
            .addCommand(pixelUp)
            .addWaitCommand(INTAKE_UP_DELAY)
            .addCommand(pixelFullyUp)
            .addCommand(intakeStop)
            .build();

    public Intake(LinearOpMode opMode) {
        this.opMode = opMode;
        topSensor = new IntakeSensor(opMode, "intakeTopSensor");
        bottomSensor = new IntakeSensor(opMode, "intakeBottomSensor");
    }

    @Override
    public void init(HardwareMap hwMap) {
        intakeMotor = hwMap.get(DcMotorSimple.class, "intakeMotor");

        leftServo = hwMap.get(Servo.class, "intakeLeftServo");
        rightServo = hwMap.get(Servo.class, "intakeRightServo");
        pixelServo = hwMap.get(Servo.class, "intakePixelServo");

        leftServo.setDirection(Servo.Direction.REVERSE);

        topSensor.init(hwMap);
        bottomSensor.init(hwMap);

        pixelServo.setPosition(PIXEL_DOWN_POS);
        down();
    }

    public void intake() {
        intakeMotor.setPower(-motorSpeed);
    }

    public void outtake() {
        intakeMotor.setPower(motorSpeed);
    }

    public void stop() {
        intakeMotor.setPower(0);
    }

    public void toggle() {
        if (isUp) {
            down();
        } else {
            up();
        }
    }

    public void up() {
        leftServo.setPosition(UP_POS);
        rightServo.setPosition(UP_POS);
        isUp = true;
    }

    public void upAuto() {
        leftServo.setPosition( UP_AUTO_POS);
        rightServo.setPosition(UP_AUTO_POS);
        isUp = true;
    }

    public void down() {
        leftServo.setPosition(DOWN_POS);
        rightServo.setPosition(DOWN_POS);
        isUp = false;
    }

    public void pixelUp() {
        isPixelUp = true;
        pixelUpSequence.trigger();
    }

    public void pixelDown() {
        pixelDownSequence.trigger();
    }

    public boolean isPixelUp() {
        return isPixelUp;
    }

    public int numPixels() {
        return (topSensor.isPixel() ? 1 : 0) + (bottomSensor.isPixel() ? 1 : 0);
    }

    @Override
    public void loop(Gamepad gamepad) {
        if (motorSpeed == SLOW_SPEED) return;
        if (GamepadStatic.isButtonPressed(gamepad, Controls.INTAKE_UP)) {
            //up();
            pixelUp();
        } else if (GamepadStatic.isButtonPressed(gamepad, Controls.INTAKE_DOWN)) {
            //down();
            pixelDown();
        }

        if (GamepadStatic.isButtonPressed(gamepad, Controls.INTAKE)) {
            lock = false;
            intake();
        } else if (GamepadStatic.isButtonPressed(gamepad, Controls.OUTTAKE)) {
            outtake();
        } else if (!lock) {
            stop();
        }
    }

    private class IntakeSensor extends Mechanism {

        private ColorRangeSensor sensor;
        private String name;

        public IntakeSensor(LinearOpMode opMode, String name) {
            this.opMode = opMode;
            this.name = name;
        }

        public void init(HardwareMap hwMap) {
            sensor = hwMap.get(ColorRangeSensor.class, name);

            sensor.enableLed(false);
        }

        public boolean isPixel() {
            Telemetry t = FtcDashboard.getInstance().getTelemetry();
            t.addData(name + " dist", sensor.getDistance(DistanceUnit.MM));
            t.update();
            return sensor.getDistance(DistanceUnit.MM) < FAR;
        }
    }
}

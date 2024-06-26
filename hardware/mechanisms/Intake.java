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
    private IntakeSensor leftSensor;
    private IntakeSensor middleSensor;
    private IntakeSensor rightSensor;

    public static double SPEED = 1;
    public static double AUTO_OUTTAKE_SPEED = 0.91;
    public static double SLOW_SPEED = 0.9;

    public double motorSpeed = SPEED;

    public static double UP_AUTO_ONE_PIXEL = 0.2;
    public static double UP_AUTO_TWO_PIXELS = 0.171;
    public static double UP_AUTO_THREE_PIXELS = 0.131;
    public static double UP_AUTO_FOUR_PIXELS = 0.04;
    public static double UP_AUTO_FIVE_PIXELS = 0.015;
    public static double UP_POS = 0.17;
    public static double CLIMB_UP_POS = 0.24;
    public static double DOWN_POS = 0.07;

    public static double PIXEL_UP_POS = 0.73;
    public static double PIXEL_MIDDLE_POS = 0.54;
    public static double PIXEL_DOWN_POS = 0.26;

    public static double INTAKE_DOWN_DELAY = 1;
    public static double INTAKE_UP_DELAY = 0.7;
    public static double AUTO_INTAKE_UP_DELAY = 1;
    public static double SENSOR_DELAY = 0;
    public static double OUTTAKE_DOWN_DELAY = 1;

    public static double FAR_LEFT_INTAKE = 55;
    public static double FAR_MIDDLE_INTAKE = 18;
    public static double FAR_RIGHT_INTAKE = 18;
    public static double FAR_PIXEL = 18;

    public static int YELLOW = 10;
    public static int PURPLE = 10;
    public static int GREEN = 10;
    public static int WHITE = 10;

    private boolean isPixelUp = false;
    private boolean isUp = false;

    private boolean lock = false;
    private boolean colorFail = false;

    private Command pixelDown = () -> {
        lock = true;
        motorSpeed = SLOW_SPEED;
        outtake();
        pixelServo.setPosition(PIXEL_DOWN_POS);
    };

    private Command pixelUp = () -> {
        motorSpeed = SLOW_SPEED;
        intake();
        middlePos();
    };

    private Command pixelFullyUp = () -> {
        pixelServo.setPosition(PIXEL_UP_POS);
        isPixelUp = true;
    };

    private Command setPixelUp = () -> isPixelUp = false;

    private Command intakeStop = () -> {
        stop();
        motorSpeed = SPEED;
    };

    private CommandSequence pixelDownSequence = new CommandSequence()
            .addCommand(pixelDown)
            .addWaitCommand(INTAKE_DOWN_DELAY)
            .addCommand(setPixelUp)
            .addWaitCommand(OUTTAKE_DOWN_DELAY)
            .addCommand(intakeStop)
            .build();

    private CommandSequence autoPixelUpSequence = new CommandSequence()
            .addWaitCommand(SENSOR_DELAY)
            .addCommand(pixelUp)
            .addWaitCommand(AUTO_INTAKE_UP_DELAY)
            .addCommand(pixelFullyUp)
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
        topSensor = new IntakeSensor(opMode, "intakeTopSensor", FAR_PIXEL);
        bottomSensor = new IntakeSensor(opMode, "intakeBottomSensor", FAR_PIXEL);
        leftSensor = new IntakeSensor(opMode, "intakeLeftSensor", FAR_LEFT_INTAKE);
        middleSensor = new IntakeSensor(opMode, "intakeMiddleSensor", FAR_MIDDLE_INTAKE);
        rightSensor = new IntakeSensor(opMode, "intakeRightSensor", FAR_RIGHT_INTAKE);
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
        leftSensor.init(hwMap);
        middleSensor.init(hwMap);
        rightSensor.init(hwMap);

        pixelServo.setPosition(PIXEL_DOWN_POS);
        down();
    }

    public void intake() {
        intakeMotor.setPower(-motorSpeed);
    }

    public void outtake() {
        intakeMotor.setPower(motorSpeed);
    }

    public void autoOuttake() {
        intakeMotor.setPower(AUTO_OUTTAKE_SPEED);
    }

    public boolean isLocked() {
        return lock;
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

    public void middlePos() {
        pixelServo.setPosition(PIXEL_MIDDLE_POS);
    }

    public void up() {
        leftServo.setPosition(UP_POS);
        rightServo.setPosition(UP_POS);
        isUp = true;
    }

    public void climbUp() {
        leftServo.setPosition(CLIMB_UP_POS);
        rightServo.setPosition(CLIMB_UP_POS);
        isUp = true;
    }

    public void upAuto(int pixels) {
        double[] positions = {
                UP_AUTO_ONE_PIXEL,
                UP_AUTO_TWO_PIXELS,
                UP_AUTO_THREE_PIXELS,
                UP_AUTO_FOUR_PIXELS,
                UP_AUTO_FIVE_PIXELS,
        };
        double upPos = positions[pixels - 1];
        leftServo.setPosition(upPos);
        rightServo.setPosition(upPos);
        isUp = true;
    }

    public void down() {
        leftServo.setPosition(DOWN_POS);
        rightServo.setPosition(DOWN_POS);
        isUp = false;
    }

    public void pixelUp() {
        pixelUpSequence.trigger();
    }

    public void autoPixelUp() {
        autoPixelUpSequence.trigger();
    }

    public void pixelDown() {
        pixelDownSequence.trigger();
    }

    public boolean isPixelUp() {
        return isPixelUp;
    }

    public void lockThird() {
        colorFail = true;
    }

    public int numPixels() {
        return (topSensor.isPixel() ? 1 : 0) + (bottomSensor.isPixel() ? 1 : 0);
    }

    public boolean isThirdPixel() {
        if (colorFail) {
            return false;
        } else {
            return leftSensor.isPixel() || middleSensor.isPixel() || rightSensor.isPixel();
        }
    }

    @Override
    public void loop(Gamepad gamepad) {
        if (motorSpeed == SLOW_SPEED)
            return;
        if (GamepadStatic.isButtonPressed(gamepad, Controls.INTAKE_UP)) {
            // up();
            pixelUp();
        } else if (GamepadStatic.isButtonPressed(gamepad, Controls.INTAKE_DOWN)) {
            // down();
            pixelDown();
        }

        if (lock) {
            intake();
        }

        if (GamepadStatic.isButtonPressed(gamepad, Controls.INTAKE)) {
            lock = false;
            intake();
        } else if (GamepadStatic.isButtonPressed(gamepad, Controls.OUTTAKE)) {
            lock = false;
            outtake();
        } else if (!lock) {
            stop();
        }

    }

    @Override
    public void telemetry(Telemetry telemetry) {
        topSensor.telemetry(telemetry);
        bottomSensor.telemetry(telemetry);
        leftSensor.telemetry(telemetry);
        middleSensor.telemetry(telemetry);
        rightSensor.telemetry(telemetry);
    }

    private class IntakeSensor extends Mechanism {

        private ColorRangeSensor sensor;
        private String name;

        private double far;

        public IntakeSensor(LinearOpMode opMode, String name, double far) {
            this.opMode = opMode;
            this.name = name;
            this.far = far;
        }

        public void init(HardwareMap hwMap) {
            sensor = hwMap.get(ColorRangeSensor.class, name);

            sensor.enableLed(false);
        }

        public boolean isPixel() {
            return sensor.getDistance(DistanceUnit.MM) < far;
        }

        public boolean isPixelColor() {
            int blue = sensor.blue();
            int red = sensor.red();
            int green = sensor.green();
            int white = (blue + red + green) / 3;
            int yellow = (red + green) / 2;
            int purple = (red + blue) / 2;
            boolean isPixel = white > WHITE || yellow > YELLOW || purple > PURPLE || green > GREEN;
            Telemetry t = FtcDashboard.getInstance().getTelemetry();
            t.addData(name + " white", white);
            t.addData(name + " yellow", yellow);
            t.addData(name + " purple", purple);
            t.addData(name + " green", green);
            t.addData(name + " isPixel", isPixel);
            t.update();
            return isPixel;
        }

        @Override
        public void telemetry(Telemetry telemetry) {
            telemetry.addData(name + " dist", sensor.getDistance(DistanceUnit.MM));
            telemetry.update();
        }
    }
}

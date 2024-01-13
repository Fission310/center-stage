package org.firstinspires.ftc.teamcode.hardware.mechanisms;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.stuyfission.fissionlib.util.Mechanism;
import com.stuyfission.fissionlib.command.Command;
import com.stuyfission.fissionlib.command.CommandSequence;
import com.stuyfission.fissionlib.input.GamepadStatic;

import org.firstinspires.ftc.teamcode.opmode.teleop.Controls;

@Config
public class Intake extends Mechanism {

    private DcMotorSimple intakeMotor;

    private Servo leftServo;
    private Servo rightServo;
    private Servo pixelServo;

    private IntakeSensor sensor;

    public static double SPEED = 1;

    public static double UP_POS = 0;
    public static double DOWN_POS = 0.67;

    public static double PIXEL_UP_POS = 0.55;
    public static double PIXEL_DOWN_POS = 0.23;

    public static double OUTTAKE_WAIT = 0.67;

    public static int GREEN = 100;
    public static int YELLOW = 100;
    public static int WHITE = 100;
    public static int PURPLE = 100;

    private Command pixelDown = () -> {
        outtake();
        pixelServo.setPosition(PIXEL_DOWN_POS);
    };

    private Command intakeStop = () -> stop();

    private CommandSequence pixelSequence = new CommandSequence()
            .addCommand(pixelDown)
            .addWaitCommand(OUTTAKE_WAIT)
            .addCommand(intakeStop)
            .build();

    public Intake(LinearOpMode opMode) {
        this.opMode = opMode;
        sensor = new IntakeSensor(opMode);
    }

    @Override
    public void init(HardwareMap hwMap) {
        intakeMotor = hwMap.get(DcMotorSimple.class, "intakeMotor");

        leftServo = hwMap.get(Servo.class, "intakeLeftServo");
        rightServo = hwMap.get(Servo.class, "intakeRightServo");
        pixelServo = hwMap.get(Servo.class, "intakePixelServo");

        leftServo.setDirection(Servo.Direction.REVERSE);

        sensor.init(hwMap);

        down();
    }

    public void intake() {
        intakeMotor.setPower(SPEED);
    }

    public void outtake() {
        intakeMotor.setPower(-SPEED);
    }

    public void stop() {
        intakeMotor.setPower(0);
    }

    public void up() {
        leftServo.setPosition(UP_POS);
        rightServo.setPosition(UP_POS);
    }

    public void down() {
        leftServo.setPosition(DOWN_POS);
        rightServo.setPosition(DOWN_POS);
    }

    public void pixelUp() {
        pixelServo.setPosition(PIXEL_UP_POS);
    }

    public void pixelDown() {
        pixelSequence.trigger();
        sensor.reset();
    }

    public int numPixels() {
        return sensor.getNumPixels();
    }

    @Override
    public void loop(Gamepad gamepad) {
        sensor.loop(gamepad);
        if (GamepadStatic.isButtonPressed(gamepad, Controls.INTAKE_UP)) {
            up();
        } else if (GamepadStatic.isButtonPressed(gamepad, Controls.INTAKE_DOWN)) {
            down();
        }

        if (GamepadStatic.isButtonPressed(gamepad, Controls.INTAKE)) {
            intake();
        } else if (GamepadStatic.isButtonPressed(gamepad, Controls.OUTTAKE)) {
            outtake();
        } else {
            stop();
        }
    }

    private class IntakeSensor extends Mechanism {

        private int numPixels = 0;
        private boolean detecting = false;

        private ColorSensor sensor;

        public IntakeSensor(LinearOpMode opMode) {
            this.opMode = opMode;
        }

        @Override
        public void init(HardwareMap hwMap) {
            sensor = hwMap.get(ColorSensor.class, "intakeSensor");
        }

        public boolean isGreen() {
            return sensor.green() > GREEN;
        }

        public boolean isYellow() {
            return sensor.green() + sensor.red() > YELLOW;
        }

        public boolean isWhite() {
            return (sensor.red() + sensor.green() + sensor.blue()) / 3 > WHITE;
        }

        public boolean isPurple() {
            return (sensor.red() + sensor.blue()) / 2 > PURPLE;
        }

        public boolean isPixel() {
            return isGreen() || isWhite() || isPurple() || isYellow();
        }

        public int getNumPixels() {
            return numPixels;
        }

        public void reset() {
            numPixels = 0;
            detecting = false;
        }

        @Override
        public void loop(Gamepad gamepad) {
            if (isPixel()) {
                if (!detecting) {
                    detecting = true;
                    numPixels++;
                }
            } else {
                detecting = false;
            }
        }
    }
}

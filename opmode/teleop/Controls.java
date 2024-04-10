package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.stuyfission.fissionlib.input.GamepadStatic.Input;

public class Controls {

    public static final Input CLIMB = Input.DPAD_DOWN;
    public static final Input SCORE_ONE = Input.RIGHT_BUMPER;
    public static final Input SCORE_TWO = Input.LEFT_BUMPER;
    public static final Input GRAB = Input.RIGHT_STICK_BUTTON;
    public static final Input LOW = Input.X; // Square
    public static final Input MEDIUM_LOW = Input.A; // Cross
    public static final Input MEDIUM_HIGH = Input.B; // Circle
    public static final Input HIGH = Input.Y; // Triangle
    public static final Input[] SLIDES = { LOW, MEDIUM_LOW, MEDIUM_HIGH, HIGH };
    public static final Input INTAKE_TOGGLE = Input.LEFT_STICK_BUTTON;
    public static final Input LAUNCH = Input.DPAD_UP;
    public static final Input RETRACT = Input.DPAD_DOWN;
    public static final Input INTAKE = Input.RIGHT_TRIGGER;
    public static final Input OUTTAKE = Input.LEFT_TRIGGER;
    public static final Input INTAKE_UP = Input.NONE;
    public static final Input INTAKE_DOWN = Input.NONE;
    public static final Input WRIST_LEFT = Input.DPAD_LEFT;
    public static final Input WRIST_RIGHT = Input.DPAD_RIGHT;
    public static final Input SLIDES_UP = Input.DPAD_UP;
    public static final Input SLIDES_DOWN = Input.DPAD_DOWN;
}

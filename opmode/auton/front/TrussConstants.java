package org.firstinspires.ftc.teamcode.opmode.auton.front;

import org.firstinspires.ftc.teamcode.opmode.auton.util.Constant;
import static org.firstinspires.ftc.teamcode.opmode.auton.util.GameConstants.*;

import com.acmerobotics.roadrunner.geometry.Pose2d;

public class TrussConstants {
    // Start Pose
    public static final double START_HEADING = UP;

    public static final double START_X = -3.0 * TILE_LENGTH / 2.0;
    public static final double START_Y = WALL_POS - BOT_LENGTH / 2.0;

    public static Pose2d START_POSE = new Pose2d(START_X, START_Y, START_HEADING);

    // Spike Mark
    public static final double SPIKE_LEFT_HEADING = LEFT;
    public static final double SPIKE_CENTER_HEADING = Math.toRadians(135);
    public static final double SPIKE_RIGHT_HEADING = LEFT;

    public static final double SPIKE_TANGENT = LEFT;

    public static final double SPIKE_X = -3.0 * TILE_LENGTH / 2.0;
    public static final double SPIKE_Y = 3.0 * TILE_LENGTH / 2.0;

    public static Constant SPIKE;

    // Stack Position
    public static final double STACK_HEADING = LEFT;

    public static final double STACK_X = -WALL_POS + 3.0 * BOT_LENGTH / 5.0;
    public static final double STACK_Y = TILE_LENGTH * 0.5;

    public static Constant STACK_1;
    public static Constant STACK_2;
    public static Constant STACK_3;

    // Front Truss Position
    public static final double FRONT_TRUSS_HEADING = RIGHT;
    public static final double FRONT_TRUSS_BACK_HEADING = LEFT;

    public static final double FRONT_TRUSS_X = 3.0 * TILE_LENGTH / 2.0;
    public static final double FRONT_TRUSS_Y = TILE_LENGTH / 2.0;

    public static Constant FRONT_TRUSS_1;
    public static Constant FRONT_TRUSS_2;
    public static Constant FRONT_TRUSS_3;

    public static Constant FRONT_TRUSS_BACK_1;
    public static Constant FRONT_TRUSS_BACK_2;

    // End Truss Position
    public static final double END_TRUSS_HEADING = RIGHT;
    public static final double END_TRUSS_BACK_HEADING = LEFT;

    public static final double END_TRUSS_X = 3.0 * TILE_LENGTH / 2.0;
    public static final double END_TRUSS_Y = TILE_LENGTH / 2.0;

    public static Constant END_TRUSS_1;
    public static Constant END_TRUSS_2;
    public static Constant END_TRUSS_3;

    public static Constant END_TRUSS_BACK_1;
    public static Constant END_TRUSS_BACK_2;

    // Tag Positions
    public static final double TAG_HEADING = RIGHT;

    public static final double TAG_X = WALL_POS - TILE_LENGTH / 2.0 - BOT_LENGTH / 2.0;
    public static final double TAG_Y = 3.0 * TILE_LENGTH / 2.0;

    public static Constant TAG_1;
    public static Constant TAG_2;
    public static Constant TAG_3;

    // Park Position
    public static final double PARK_HEADING = DOWN;

    public static final double PARK_X = TAG_X;
    public static final double PARK_Y = BOT_LENGTH;

    public static Constant PARK;
}

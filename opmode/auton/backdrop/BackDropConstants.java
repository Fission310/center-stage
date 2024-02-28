package org.firstinspires.ftc.teamcode.opmode.auton.backdrop;

import org.firstinspires.ftc.teamcode.opmode.auton.util.Constant;
import static org.firstinspires.ftc.teamcode.opmode.auton.util.GameConstants.*;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;

@Config
public class BackDropConstants {
    // Start Pose
    public static final double START_HEADING = UP;

    public static final double START_X = TILE_LENGTH / 2.0;
    public static final double START_Y = WALL_POS - BOT_LENGTH / 2.0;

    public static Pose2d START_POSE;

    // Spike Mark
    public static final double SPIKE_LEFT_HEADING = Math.toRadians(-45);
    public static final double SPIKE_CENTER_HEADING = Math.toRadians(45);
    public static final double SPIKE_RIGHT_HEADING = LEFT;

    public static final double SPIKE_TANGENT = RIGHT;

    public static final double SPIKE_X = TILE_LENGTH / 2.0;
    public static final double SPIKE_Y = 3.0 * TILE_LENGTH / 2.0;

    public static double SPIKE_CENTER_OFFSET_X = 2;
    public static double SPIKE_CENTER_OFFSET_Y = -2;

    public static Constant SPIKE;

    // Stack Position
    public static final double STACK_HEADING = LEFT;

    public static final double STACK_X = -WALL_POS + 3.0 * BOT_LENGTH / 5.0;
    public static final double STACK_Y = TILE_LENGTH * 0.5;

    public static double STACK_Y_OFFSET = 5;
    public static double STACK_X_OFFSET = 1;

    public static Constant STACK;

    // Truss Position
    public static final double TRUSS_HEADING = RIGHT;
    public static final double TRUSS_BACK_HEADING = LEFT;

    public static final double TRUSS_X = TILE_LENGTH;
    public static final double TRUSS_Y = TILE_LENGTH / 2.0;

    public static Constant TRUSS;
    public static Constant BACK_TRUSS;

    // Tag Positions
    public static final double TAG_HEADING_1 = LEFT;
    public static final double TAG_HEADING_2 = RIGHT;

    public static final double TAG_X = WALL_POS - TILE_LENGTH / 2.0 - BOT_LENGTH / 2.0;
    public static final double TAG_Y = 3.0 * TILE_LENGTH / 2.0;

    public static double TAG_X_OFFSET_1 = 4;
    public static double TAG_X_OFFSET_2 = 4;
    public static double TAG_LEFT_OFFSET = -6.15;
    public static double TAG_CENTER_OFFSET = -2.35;
    public static double TAG_RIGHT_OFFSET = 4.35;

    public static Constant TAG_1;
    public static Constant TAG_2;

    // Park Position
    public static final double PARK_HEADING = DOWN;

    public static final double PARK_X = TAG_X;
    public static final double PARK_Y = BOT_LENGTH;

    public static Constant PARK;

    public static void init() {
        final double[] ZERO = array(0);

        START_POSE = new Pose2d(START_X, START_Y, START_HEADING);

        SPIKE = new Constant(SPIKE_X, SPIKE_Y, new double[] { 0, SPIKE_CENTER_OFFSET_X, 0 },
                new double[] { 0, SPIKE_CENTER_OFFSET_Y, 0 },
                new double[] { SPIKE_LEFT_HEADING, SPIKE_CENTER_HEADING, SPIKE_RIGHT_HEADING });

        STACK = new Constant(STACK_X, STACK_Y, array(STACK_X_OFFSET), array(STACK_Y_OFFSET), array(STACK_HEADING));

        TRUSS = new Constant(TRUSS_X, TRUSS_Y, ZERO, ZERO, array(TRUSS_HEADING));
        BACK_TRUSS = new Constant(TRUSS_X, TRUSS_Y, ZERO, ZERO, array(TRUSS_BACK_HEADING));

        TAG_1 = new Constant(TAG_X, TAG_Y, new double[] { TAG_LEFT_OFFSET, TAG_CENTER_OFFSET, TAG_RIGHT_OFFSET },
                array(TAG_X_OFFSET_1),
                array(TAG_HEADING_1));
        TAG_2 = new Constant(TAG_X, TAG_Y, new double[] { TAG_LEFT_OFFSET, TAG_CENTER_OFFSET, TAG_RIGHT_OFFSET },
                array(TAG_X_OFFSET_2),
                array(TAG_HEADING_2));

        PARK = new Constant(PARK_X, PARK_Y, ZERO, ZERO, array(PARK_HEADING));
    }

    private static double[] array(double t) {
        double[] ret = new double[3];
        for (int i = 0; i < 3; i++) {
            ret[i] = t;
        }
        return ret;
    }
}

package org.firstinspires.ftc.teamcode.opmode.auton;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

@Config
public class AutoConstants {

    public enum Color {
        BLUE,
        RED
    }

    /**
     * DEFINITIONS OF PREFIXES
     *
     * BD - Back Drop
     * FR - Front (Opposite from Back Drop)
     *
     **/

    // Field and Robot Constants
    public static final double BOT_LENGTH = 14.0;
    public static final double WALL_POS = 70.5;
    public static final double TILE_LENGTH = WALL_POS / 3.0;
    public static final double RIGHT = Math.toRadians(0);
    public static final double UP = Math.toRadians(90);
    public static final double LEFT = Math.toRadians(180);
    public static final double DOWN = Math.toRadians(270);

    /** ======= TAG POSITIONS ======= **/
    public static double TAG_HEADING = RIGHT;
    public static double TAG_X = WALL_POS - TILE_LENGTH / 2.0 - BOT_LENGTH / 2.0;

    public static double TAG_CENTER_Y = WALL_POS - 3.0 * TILE_LENGTH / 2.0;
    public static Vector2d TAG_CENTER_VECTOR = new Vector2d(TAG_X, TAG_CENTER_Y);

    public static double TAG_RIGHT_Y = WALL_POS - 7.0 * TILE_LENGTH / 4.0;
    public static Vector2d TAG_RIGHT_VECTOR = new Vector2d(TAG_X, TAG_RIGHT_Y);

    public static double TAG_LEFT_Y = WALL_POS - 5.0 * TILE_LENGTH / 4.0;
    public static Vector2d TAG_LEFT_VECTOR = new Vector2d(TAG_X, TAG_LEFT_Y);

    public static double[] TAG_HEADINGS = {TAG_HEADING, TAG_HEADING, TAG_HEADING};
    public static Vector2d[] TAG_VECTORS = {TAG_CENTER_VECTOR, TAG_RIGHT_VECTOR, TAG_LEFT_VECTOR};
    /** ======= END TAG POSITIONS ======= **/

    /** ======= CONSTANTS FOR BACK DROP ======= **/
    // Start Pose
    public static double BD_START_HEADING = DOWN;
    public static double BD_START_X = TILE_LENGTH / 2.0;
    public static double BD_START_Y = WALL_POS - BOT_LENGTH / 2.0;
    public static Pose2d BD_START_POSE = new Pose2d(BD_START_X, BD_START_Y, BD_START_HEADING);

    // Spike Mark
    public static double BD_SPIKE_CENTER_HEADING = DOWN;
    public static double BD_SPIKE_CENTER_X = TILE_LENGTH / 2.0;
    public static double BD_SPIKE_CENTER_Y = WALL_POS - 2.0 * TILE_LENGTH + BOT_LENGTH / 2.0;
    public static Vector2d BD_SPIKE_VECTOR = new Vector2d(BD_SPIKE_CENTER_X, BD_SPIKE_CENTER_Y);

    public static double BD_SPIKE_RIGHT_HEADING = LEFT;
    public static double BD_SPIKE_RIGHT_X = BOT_LENGTH / 2.0;
    public static double BD_SPIKE_RIGHT_Y = WALL_POS - 7.0 * TILE_LENGTH / 4.0;
    public static Vector2d BD_SPIKE_RIGHT_VECTOR = new Vector2d(BD_SPIKE_RIGHT_X, BD_SPIKE_RIGHT_Y);

    public static double BD_SPIKE_LEFT_HEADING = DOWN;
    public static double BD_SPIKE_LEFT_X = TILE_LENGTH;
    public static double BD_SPIKE_LEFT_Y = WALL_POS - 7.0 * TILE_LENGTH / 4.0 + BOT_LENGTH / 2.0;
    public static Vector2d BD_SPIKE_LEFT_VECTOR = new Vector2d(BD_SPIKE_LEFT_X, BD_SPIKE_LEFT_Y);

    public static double[] BD_SPIKE_HEADINGS = {BD_SPIKE_CENTER_HEADING, BD_SPIKE_RIGHT_HEADING, BD_SPIKE_LEFT_HEADING};
    public static Vector2d[] BD_SPIKE_VECTORS = {BD_SPIKE_VECTOR, BD_SPIKE_RIGHT_VECTOR, BD_SPIKE_LEFT_VECTOR};

    // Park Position
    public static double BD_PARK_X = WALL_POS - TILE_LENGTH / 2.0 - BOT_LENGTH / 2.0;
    public static double BD_PARK_Y = WALL_POS - BOT_LENGTH;
    public static Vector2d BD_PARK_VECTOR = new Vector2d(BD_PARK_X, BD_PARK_Y);

    /** ======= END CONSTANTS FOR BACK DROP ======= **/
}

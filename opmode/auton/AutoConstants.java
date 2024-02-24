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
    public static double TAG_X = WALL_POS - TILE_LENGTH / 2.0 - BOT_LENGTH / 2.0 + 5;
    public static double TAG_LEFT_Y = TILE_LENGTH + 5.6;
    public static Vector2d TAG_LEFT_VECTOR;
    public static double TAG_CENTER_Y = WALL_POS - 8.8 * TILE_LENGTH / 5.0 + 12;
    public static Vector2d TAG_CENTER_VECTOR;

    public static double TAG_RIGHT_Y = WALL_POS - 7.0 * TILE_LENGTH / 5.0 + 9;
    public static Vector2d TAG_RIGHT_VECTOR;

    public static double[] TAG_HEADINGS;
    public static Vector2d[] TAG_VECTORS;

    /** ======= END TAG POSITIONS ======= **/

    // Park Position
    public static double PARK_HEADING = DOWN;
    public static double PARK_X = TAG_X;
    public static double PARK_Y = BOT_LENGTH;
    public static Vector2d PARK_VECTOR;

    /** ======= CONSTANTS FOR BACK DROP ======= **/
    // Start Pose
    public static double BD_START_HEADING = DOWN;
    public static double BD_START_X = TILE_LENGTH / 2.0;
    public static double BD_START_Y = WALL_POS - BOT_LENGTH / 2.0;
    public static Pose2d BD_START_POSE;

    // Spike Mark
    public static double BD_SPIKE_CENTER_HEADING = DOWN;
    public static double BD_SPIKE_CENTER_X = TILE_LENGTH / 2.0;
    public static double BD_SPIKE_CENTER_Y = WALL_POS - 2.0 * TILE_LENGTH + BOT_LENGTH / 2.0;
    public static Vector2d BD_SPIKE_CENTER_VECTOR;

    public static double BD_SPIKE_LEFT_HEADING = LEFT;
    public static double BD_SPIKE_LEFT_X = BOT_LENGTH / 2.0;
    public static double BD_SPIKE_LEFT_Y = WALL_POS - 3.0 * TILE_LENGTH / 2.0;
    public static Vector2d BD_SPIKE_LEFT_VECTOR;

    public static double BD_SPIKE_RIGHT_HEADING = DOWN;
    public static double BD_SPIKE_RIGHT_X = TILE_LENGTH;
    public static double BD_SPIKE_RIGHT_Y = WALL_POS - 7.0 * TILE_LENGTH / 4.0 + BOT_LENGTH / 2.0;
    public static Vector2d BD_SPIKE_RIGHT_VECTOR;

    public static double[] BD_SPIKE_HEADINGS;
    public static Vector2d[] BD_SPIKE_VECTORS;
    /** ======= END CONSTANTS FOR BACK DROP ======= **/

    /** ======= CONSTANTS FOR FRONT ======= **/
    // Start Pose
    public static double FR_START_HEADING = UP;
    public static double FR_START_X = -3.0 * TILE_LENGTH / 2.0;
    public static double FR_START_Y = WALL_POS - BOT_LENGTH / 2.0;
    public static Pose2d FR_START_POSE;

    // Spike Mark
    public static double FR_SPIKE_TANGENT = LEFT;

    public static double FR_SPIKE_CENTER_HEADING = Math.toRadians(135);
    public static double FR_SPIKE_CENTER_X = -3.0 * TILE_LENGTH / 2.0 - 2;
    public static double FR_SPIKE_CENTER_Y = WALL_POS - 3.0 * TILE_LENGTH / 2.0 - 2;
    public static Vector2d FR_SPIKE_CENTER_VECTOR;

    public static double FR_SPIKE_LEFT_HEADING = LEFT;
    public static double FR_SPIKE_LEFT_X = -3.0 * TILE_LENGTH / 2.0;
    public static double FR_SPIKE_LEFT_Y = WALL_POS - 3.0 * TILE_LENGTH / 2.0;
    public static Vector2d FR_SPIKE_LEFT_VECTOR;

    public static double FR_SPIKE_RIGHT_HEADING = LEFT;
    public static double FR_SPIKE_RIGHT_X = -3.0 * TILE_LENGTH / 2.0;
    public static double FR_SPIKE_RIGHT_Y = WALL_POS - 3.0 * TILE_LENGTH / 2.0;
    public static Vector2d FR_SPIKE_RIGHT_VECTOR;

    public static double[] FR_SPIKE_HEADINGS;
    public static Vector2d[] FR_SPIKE_VECTORS;

    // Back Position
    public static double FR_BACK_HEADING = Math.toRadians(45);
    public static double FR_BACK_X = -2.0 * TILE_LENGTH;
    public static double FR_BACK_Y = WALL_POS - TILE_LENGTH;
    public static Vector2d FR_BACK_VECTOR;

    // Stack Position
    public static double FR_STACK_HEADING = LEFT;
    public static double FR_STACK_FIRST_X = -WALL_POS + 3.0 * BOT_LENGTH / 5.0;
    public static double FR_STACK_SECOND_X = -WALL_POS + 3.0 * BOT_LENGTH / 5.0 + 3;
    public static double[] FR_STACK_X = {FR_STACK_FIRST_X, FR_STACK_SECOND_X};
    public static double FR_TRUSS_STACK_Y = WALL_POS - 5.0 * TILE_LENGTH / 2.0 + 8;
    public static double FR_WALL_STACK_Y = TILE_LENGTH * 1.5;
    public static Vector2d[] FR_TRUSS_STACK_VECTOR = new Vector2d[FR_STACK_X.length];
    public static Vector2d[] FR_WALL_STACK_VECTOR = new Vector2d[FR_STACK_X.length];

    // Tag Back Position
    public static double FR_TAG_BACK_HEADING = LEFT;
    public static double FR_TAG_BACK_X = WALL_POS - TILE_LENGTH;
    public static double FR_TAG_BACK_Y = WALL_POS - 7.0 * TILE_LENGTH / 5.0 + 4;
    public static Vector2d FR_TAG_BACK_VECTOR;
    /** ======= END CONSTANTS FOR BACK DROP ======= **/

    // Truss Position
    public static double TRUSS_HEADING = RIGHT;
    public static double TRUSS_BACK_HEADING = LEFT;
    public static double TRUSS_X = 3.0 * TILE_LENGTH / 2.0;
    public static double TRUSS_Y = TILE_LENGTH / 2.0;
    public static Vector2d TRUSS_VECTOR;

    // Wall Position

    public static double WALL_HEADING = RIGHT;
    public static double WALL_START_X = FR_START_X;
    public static double WALL_END_X = TRUSS_X - 7;
    public static double WALL_Y = TILE_LENGTH * 2.5;
    public static Vector2d WALL_START_VECTOR;
    public static Vector2d WALL_END_VECTOR;

    public static void init() {
        TAG_CENTER_VECTOR = new Vector2d(TAG_X, TAG_CENTER_Y);
        TAG_LEFT_VECTOR = new Vector2d(TAG_X, TAG_LEFT_Y);
        TAG_RIGHT_VECTOR = new Vector2d(TAG_X, TAG_RIGHT_Y);
        TAG_HEADINGS = new double[] { TAG_HEADING, TAG_HEADING, TAG_HEADING };
        TAG_VECTORS = new Vector2d[] { TAG_LEFT_VECTOR, TAG_CENTER_VECTOR, TAG_RIGHT_VECTOR };

        PARK_VECTOR = new Vector2d(PARK_X, PARK_Y);

        BD_START_POSE = new Pose2d(BD_START_X, BD_START_Y, BD_START_HEADING);
        BD_SPIKE_CENTER_VECTOR = new Vector2d(BD_SPIKE_CENTER_X, BD_SPIKE_CENTER_Y);
        BD_SPIKE_LEFT_VECTOR = new Vector2d(BD_SPIKE_LEFT_X, BD_SPIKE_LEFT_Y);
        BD_SPIKE_RIGHT_VECTOR = new Vector2d(BD_SPIKE_RIGHT_X, BD_SPIKE_RIGHT_Y);
        BD_SPIKE_HEADINGS = new double[] { BD_SPIKE_LEFT_HEADING, BD_SPIKE_CENTER_HEADING, BD_SPIKE_RIGHT_HEADING };
        BD_SPIKE_VECTORS = new Vector2d[] { BD_SPIKE_LEFT_VECTOR, BD_SPIKE_CENTER_VECTOR, BD_SPIKE_RIGHT_VECTOR };

        FR_START_POSE = new Pose2d(FR_START_X, FR_START_Y, FR_START_HEADING);
        FR_SPIKE_CENTER_VECTOR = new Vector2d(FR_SPIKE_CENTER_X, FR_SPIKE_CENTER_Y);
        FR_SPIKE_LEFT_VECTOR = new Vector2d(FR_SPIKE_LEFT_X, FR_SPIKE_LEFT_Y);
        FR_SPIKE_RIGHT_VECTOR = new Vector2d(FR_SPIKE_RIGHT_X, FR_SPIKE_RIGHT_Y);
        FR_SPIKE_HEADINGS = new double[] { FR_SPIKE_LEFT_HEADING, FR_SPIKE_CENTER_HEADING, FR_SPIKE_RIGHT_HEADING };
        FR_SPIKE_VECTORS = new Vector2d[] { FR_SPIKE_LEFT_VECTOR, FR_SPIKE_CENTER_VECTOR, FR_SPIKE_RIGHT_VECTOR };
        FR_BACK_VECTOR = new Vector2d(FR_BACK_X, FR_BACK_Y);
        for(int i = 0; i < FR_STACK_X.length; i++){
            FR_TRUSS_STACK_VECTOR[i] = new Vector2d(FR_STACK_X[i], FR_TRUSS_STACK_Y);
            FR_WALL_STACK_VECTOR[i] = new Vector2d(FR_STACK_X[i], FR_WALL_STACK_Y);
        }
        FR_TAG_BACK_VECTOR = new Vector2d(FR_TAG_BACK_X, FR_TAG_BACK_Y);

        TRUSS_VECTOR = new Vector2d(TRUSS_X, TRUSS_Y);
        WALL_START_VECTOR = new Vector2d(WALL_START_X, WALL_Y);
        WALL_END_VECTOR = new Vector2d(WALL_END_X,WALL_Y);
    }
}

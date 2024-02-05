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
    public static double TAG_X = WALL_POS - TILE_LENGTH / 2.0 - BOT_LENGTH / 2.0 + 2;

    public static double TAG_CENTER_Y = WALL_POS - 7.0 * TILE_LENGTH / 5.0 - 2;
    public static Vector2d TAG_CENTER_VECTOR = new Vector2d(TAG_X, TAG_CENTER_Y);

    public static double TAG_LEFT_Y = WALL_POS - 8.8 * TILE_LENGTH / 5.0;
    public static Vector2d TAG_LEFT_VECTOR = new Vector2d(TAG_X, TAG_LEFT_Y);

    public static double TAG_RIGHT_Y = WALL_POS - 6.0 * TILE_LENGTH / 5.0;
    public static Vector2d TAG_RIGHT_VECTOR = new Vector2d(TAG_X, TAG_RIGHT_Y);

    public static double[] TAG_HEADINGS = { TAG_HEADING, TAG_HEADING, TAG_HEADING };
    public static Vector2d[] TAG_VECTORS = { TAG_LEFT_VECTOR, TAG_CENTER_VECTOR, TAG_RIGHT_VECTOR };
    /** ======= END TAG POSITIONS ======= **/

    // Park Position
    public static double PARK_X = WALL_POS - TILE_LENGTH / 2.0 - BOT_LENGTH / 2.0;
    public static double PARK_Y = WALL_POS - BOT_LENGTH / 2.0;
    public static Vector2d PARK_VECTOR = TAG_CENTER_VECTOR.plus(new Vector2d(0, 0.01));// new Vector2d(PARK_X, PARK_Y);

    /** ======= CONSTANTS FOR BACK DROP ======= **/
    // Start Pose
    public static double BD_START_HEADING = DOWN;
    public static double BD_START_X = TILE_LENGTH / 2.0;
    public static double BD_START_Y = WALL_POS - BOT_LENGTH / 2.0;
    public static Pose2d BD_START_POSE = new Pose2d(BD_START_X, BD_START_Y, BD_START_HEADING);

    // Spike Mark
    public static double BD_SPIKE_CENTER_HEADING = DOWN;
    public static double BD_SPIKE_CENTER_X = TILE_LENGTH / 2.0;
    public static double BD_SPIKE_CENTER_Y = WALL_POS - 2.0 * TILE_LENGTH + BOT_LENGTH / 2.0 + 1;
    public static Vector2d BD_SPIKE_CENTER_VECTOR = new Vector2d(BD_SPIKE_CENTER_X, BD_SPIKE_CENTER_Y);

    public static double BD_SPIKE_LEFT_HEADING = LEFT;
    public static double BD_SPIKE_LEFT_X = BOT_LENGTH / 2.0;
    public static double BD_SPIKE_LEFT_Y = WALL_POS - 3.0 * TILE_LENGTH / 2.0;
    public static Vector2d BD_SPIKE_LEFT_VECTOR = new Vector2d(BD_SPIKE_LEFT_X, BD_SPIKE_LEFT_Y);

    public static double BD_SPIKE_RIGHT_HEADING = DOWN;
    public static double BD_SPIKE_RIGHT_X = TILE_LENGTH;
    public static double BD_SPIKE_RIGHT_Y = WALL_POS - 7.0 * TILE_LENGTH / 4.0 + BOT_LENGTH / 2.0;
    public static Vector2d BD_SPIKE_RIGHT_VECTOR = new Vector2d(BD_SPIKE_RIGHT_X, BD_SPIKE_RIGHT_Y);

    public static double[] BD_SPIKE_HEADINGS = { BD_SPIKE_LEFT_HEADING, BD_SPIKE_CENTER_HEADING,
            BD_SPIKE_RIGHT_HEADING };
    public static Vector2d[] BD_SPIKE_VECTORS = { BD_SPIKE_LEFT_VECTOR, BD_SPIKE_CENTER_VECTOR, BD_SPIKE_RIGHT_VECTOR };
    /** ======= END CONSTANTS FOR BACK DROP ======= **/

    /** ======= CONSTANTS FOR FRONT ======= **/
    // Start Pose
    public static double FR_START_HEADING = DOWN;
    public static double FR_START_X = -3.0 * TILE_LENGTH / 2.0;
    public static double FR_START_Y = WALL_POS - BOT_LENGTH / 2.0;
    public static Pose2d FR_START_POSE = new Pose2d(FR_START_X, FR_START_Y, FR_START_HEADING);

    // Spike Mark
    public static double FR_SPIKE_CENTER_HEADING = DOWN;
    public static double FR_SPIKE_CENTER_X = -3.0 * TILE_LENGTH / 2.0;
    public static double FR_SPIKE_CENTER_Y = WALL_POS - 2.0 * TILE_LENGTH + BOT_LENGTH / 2.0 + 4;
    public static Vector2d FR_SPIKE_CENTER_VECTOR = new Vector2d(FR_SPIKE_CENTER_X, FR_SPIKE_CENTER_Y);

    public static double FR_SPIKE_LEFT_HEADING = DOWN;
    public static double FR_SPIKE_LEFT_X = -2.0 * TILE_LENGTH;
    public static double FR_SPIKE_LEFT_Y = WALL_POS - TILE_LENGTH - BOT_LENGTH / 2.0;
    public static Vector2d FR_SPIKE_LEFT_VECTOR = new Vector2d(FR_SPIKE_LEFT_X, FR_SPIKE_LEFT_Y);

    public static double FR_SPIKE_RIGHT_HEADING = Math.toRadians(-45);
    public static double FR_SPIKE_RIGHT_X = -TILE_LENGTH - BOT_LENGTH / 2.0;
    public static double FR_SPIKE_RIGHT_Y = WALL_POS - 8.0 * TILE_LENGTH / 4.0 + BOT_LENGTH / 2.0;
    public static Vector2d FR_SPIKE_RIGHT_VECTOR = new Vector2d(FR_SPIKE_RIGHT_X, FR_SPIKE_RIGHT_Y);

    public static double[] FR_SPIKE_HEADINGS = { FR_SPIKE_LEFT_HEADING, FR_SPIKE_CENTER_HEADING,
            FR_SPIKE_RIGHT_HEADING };
    public static Vector2d[] FR_SPIKE_VECTORS = { FR_SPIKE_LEFT_VECTOR, FR_SPIKE_CENTER_VECTOR, FR_SPIKE_RIGHT_VECTOR };

    // Back Position
    public static double FR_BACK_HEADING = Math.toRadians(45);
    public static double FR_BACK_X = -2.0 * TILE_LENGTH;
    public static double FR_BACK_Y = WALL_POS - TILE_LENGTH;
    public static Vector2d FR_BACK_VECTOR = new Vector2d(FR_BACK_X, FR_BACK_Y);

    // Stack Position
    public static double FR_STACK_HEADING = LEFT;
    public static double FR_STACK_X = -WALL_POS + 3.0 * BOT_LENGTH / 5.0 + 2;
    public static double FR_STACK_Y = WALL_POS - 3.0 * TILE_LENGTH / 2.0;
    public static Vector2d FR_STACK_VECTOR = new Vector2d(FR_STACK_X, FR_STACK_Y);
    /** ======= END CONSTANTS FOR BACK DROP ======= **/

    // Truss Position
    public static double TRUSS_HEADING = RIGHT;
    public static double TRUSS_X_1 = -9.0 * TILE_LENGTH / 4.0;
    public static double TRUSS_Y_1 = BOT_LENGTH / 2.0;
    public static Vector2d TRUSS_VECTOR_1 = new Vector2d(TRUSS_X_1, TRUSS_Y_1);

    public static double TRUSS_X_2 = 3.0 * TILE_LENGTH / 2.0;
    public static double TRUSS_Y_2 = BOT_LENGTH / 2.0;
    public static Vector2d TRUSS_VECTOR_2 = new Vector2d(TRUSS_X_2, TRUSS_Y_2);
}

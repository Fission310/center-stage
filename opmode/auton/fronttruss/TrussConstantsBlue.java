package org.firstinspires.ftc.teamcode.opmode.auton.fronttruss;

import com.acmerobotics.dashboard.config.Config;

@Config
public class TrussConstantsBlue extends TrussConstants {
    public static double SPIKE_LEFT_OFFSET_X_B = 0;
    public static double SPIKE_LEFT_OFFSET_Y_B = -4;
    public static double SPIKE_CENTER_OFFSET_X_B = -2;
    public static double SPIKE_CENTER_OFFSET_Y_B = -2;
    public static double SPIKE_RIGHT_OFFSET_X_B = 0;
    public static double SPIKE_RIGHT_OFFSET_Y_B = 0;

    public static double STACK_Y_OFFSET_1_B = 5;
    public static double STACK_Y_OFFSET_2_B = 8;
    public static double STACK_X_OFFSET_1_B = 1;
    public static double STACK_X_OFFSET_2_B = 3;

    public static double TAG_X_OFFSET_1_B = 2;
    public static double TAG_X_OFFSET_2_B = -4;
    public static double TAG_LEFT_OFFSET_B = -4;
    public static double TAG_CENTER_OFFSET_B = 0;
    public static double TAG_RIGHT_OFFSET_B = 10;

    public static void set() {
        SPIKE_LEFT_OFFSET_X = SPIKE_LEFT_OFFSET_X_B;
        SPIKE_LEFT_OFFSET_Y = SPIKE_LEFT_OFFSET_Y_B;
        SPIKE_CENTER_OFFSET_X = SPIKE_CENTER_OFFSET_X_B;
        SPIKE_CENTER_OFFSET_Y = SPIKE_CENTER_OFFSET_Y_B;
        SPIKE_RIGHT_OFFSET_X = SPIKE_RIGHT_OFFSET_X_B;
        SPIKE_RIGHT_OFFSET_Y = SPIKE_RIGHT_OFFSET_Y_B;

        STACK_Y_OFFSET_1 = STACK_Y_OFFSET_1_B;
        STACK_Y_OFFSET_2 = STACK_Y_OFFSET_2_B;
        STACK_X_OFFSET_1 = STACK_X_OFFSET_1_B;
        STACK_X_OFFSET_2 = STACK_X_OFFSET_2_B;

        TAG_X_OFFSET_1 = TAG_X_OFFSET_1_B;
        TAG_X_OFFSET_2 = TAG_X_OFFSET_2_B;
        TAG_LEFT_OFFSET = TAG_LEFT_OFFSET_B;
        TAG_CENTER_OFFSET = TAG_CENTER_OFFSET_B;
        TAG_RIGHT_OFFSET = TAG_RIGHT_OFFSET_B;

        init();
    }
}

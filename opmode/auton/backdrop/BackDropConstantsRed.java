package org.firstinspires.ftc.teamcode.opmode.auton.backdrop;

import com.acmerobotics.dashboard.config.Config;

@Config
public class BackDropConstantsRed extends BackDropConstants {
    public static double SPIKE_LEFT_OFFSET_X_R = -1;
    public static double SPIKE_LEFT_OFFSET_Y_R = 3;
    public static double SPIKE_CENTER_OFFSET_X_R = 15;
    public static double SPIKE_CENTER_OFFSET_Y_R = -6;
    public static double SPIKE_RIGHT_OFFSET_X_R = 19.11;
    public static double SPIKE_RIGHT_OFFSET_Y_R = 4;

    public static double STACK_Y_OFFSET_R = 5;
    public static double STACK_X_OFFSET_R = 1;

    public static double TAG_X_OFFSET_1_R = 3.11;
    public static double TAG_X_OFFSET_2_R = 4;
    public static double TAG_LEFT_OFFSET_R = -8;
    public static double TAG_CENTER_OFFSET_R = -2.11;
    public static double TAG_RIGHT_OFFSET_R = 3.10;

    public static void set() {
        SPIKE_LEFT_OFFSET_X = SPIKE_LEFT_OFFSET_X_R;
        SPIKE_LEFT_OFFSET_Y = SPIKE_LEFT_OFFSET_Y_R;
        SPIKE_CENTER_OFFSET_X = SPIKE_CENTER_OFFSET_X_R;
        SPIKE_CENTER_OFFSET_Y = SPIKE_CENTER_OFFSET_Y_R;
        SPIKE_RIGHT_OFFSET_X = SPIKE_RIGHT_OFFSET_X_R;
        SPIKE_RIGHT_OFFSET_Y = SPIKE_RIGHT_OFFSET_Y_R;

        STACK_Y_OFFSET = STACK_Y_OFFSET_R;
        STACK_X_OFFSET = STACK_X_OFFSET_R;

        TAG_X_OFFSET_1 = TAG_X_OFFSET_1_R;
        TAG_X_OFFSET_2 = TAG_X_OFFSET_2_R;
        TAG_LEFT_OFFSET = TAG_LEFT_OFFSET_R;
        TAG_CENTER_OFFSET = TAG_CENTER_OFFSET_R;
        TAG_RIGHT_OFFSET = TAG_RIGHT_OFFSET_R;

        init();
    }
}

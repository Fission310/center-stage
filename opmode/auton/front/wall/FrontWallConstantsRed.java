package org.firstinspires.ftc.teamcode.opmode.auton.front.wall;

import com.acmerobotics.dashboard.config.Config;

import org.firstinspires.ftc.teamcode.opmode.auton.front.TrussConstants;
import org.firstinspires.ftc.teamcode.opmode.auton.util.Constant;

@Config
public class FrontWallConstantsRed extends TrussConstants {
    public static void setConstants() {
        SPIKE = new Constant(
                SPIKE_X,
                SPIKE_Y,
                -21,
                -4,
                SPIKE_LEFT_HEADING,
                -5,
                -5,
                SPIKE_CENTER_HEADING,
                1.5,
                -3,
                SPIKE_RIGHT_HEADING);

        STACK_1 = new Constant(
                STACK_X,
                STACK_Y,
                6,
                3,
                STACK_HEADING,
                2.2,
                3,
                STACK_HEADING,
                0.8,
                3,
                STACK_HEADING);

        STACK_2 = new Constant(
                STACK_X,
                STACK_Y,
                9,
                5,
                STACK_HEADING,
                8,
                5,
                STACK_HEADING,
                6,
                5,
                STACK_HEADING);

        STACK_3 = new Constant(
                STACK_X,
                STACK_Y,
                9,
                5,
                STACK_HEADING,
                8,
                5,
                STACK_HEADING,
                6,
                5,
                STACK_HEADING);

        TAG_1 = new Constant(
                TAG_X,
                TAG_Y,
                3,
                -3,
                TAG_HEADING,
                3,
                1.1,
                TAG_HEADING,
                1,
                6.5,
                TAG_HEADING);

        TAG_2 = new Constant(
                TAG_X,
                TAG_Y,
                5,
                -3,
                TAG_HEADING,
                6,
                1.1,
                TAG_HEADING,
                6,
                6.5,
                TAG_HEADING);

        TAG_3 = new Constant(
                TAG_X,
                TAG_Y,
                5,
                -3,
                TAG_HEADING,
                6,
                1.1,
                TAG_HEADING,
                6,
                6.5,
                TAG_HEADING);

        FRONT_TRUSS_1 = new Constant(FRONT_TRUSS_X, FRONT_TRUSS_Y, 0, -1, FRONT_TRUSS_HEADING, 0, -1,
                FRONT_TRUSS_HEADING, 0, -1, FRONT_TRUSS_HEADING);

        FRONT_TRUSS_2 = new Constant(FRONT_TRUSS_X, FRONT_TRUSS_Y, 0, -1, FRONT_TRUSS_HEADING, 0, -1,
                FRONT_TRUSS_HEADING, 0, -1, FRONT_TRUSS_HEADING);

        FRONT_TRUSS_3 = new Constant(FRONT_TRUSS_X, FRONT_TRUSS_Y, 0, -1, FRONT_TRUSS_HEADING, 0, -1,
                FRONT_TRUSS_HEADING, 0, -1, FRONT_TRUSS_HEADING);

        FRONT_TRUSS_BACK_1 = new Constant(FRONT_TRUSS_X, FRONT_TRUSS_Y, 0, -1, FRONT_TRUSS_BACK_HEADING, 0, -1,
                FRONT_TRUSS_BACK_HEADING, 0, -1, FRONT_TRUSS_BACK_HEADING);

        FRONT_TRUSS_BACK_2 = new Constant(FRONT_TRUSS_X, FRONT_TRUSS_Y, 0, -1, FRONT_TRUSS_BACK_HEADING, 0, -1,
                FRONT_TRUSS_BACK_HEADING, 0, -1, FRONT_TRUSS_BACK_HEADING);

        END_TRUSS_1 = new Constant(END_TRUSS_X, END_TRUSS_Y, 0, -1, END_TRUSS_HEADING, 0, -1, END_TRUSS_HEADING, 0, -1,
                END_TRUSS_HEADING);

        END_TRUSS_2 = new Constant(END_TRUSS_X, END_TRUSS_Y, 0, -1, END_TRUSS_HEADING, 0, -1, END_TRUSS_HEADING, 0, -1,
                END_TRUSS_HEADING);

        END_TRUSS_3 = new Constant(END_TRUSS_X, END_TRUSS_Y, 0, -1, END_TRUSS_HEADING, 0, -1, END_TRUSS_HEADING, 0, -1,
                END_TRUSS_HEADING);

        END_TRUSS_BACK_1 = new Constant(END_TRUSS_X, END_TRUSS_Y, 0, -1, END_TRUSS_BACK_HEADING, 0, -1,
                END_TRUSS_BACK_HEADING, 0, -1, END_TRUSS_BACK_HEADING);

        END_TRUSS_BACK_2 = new Constant(END_TRUSS_X, END_TRUSS_Y, 0, -1, END_TRUSS_BACK_HEADING, 0, -1,
                END_TRUSS_BACK_HEADING, 0, -1, END_TRUSS_BACK_HEADING);
    }
}

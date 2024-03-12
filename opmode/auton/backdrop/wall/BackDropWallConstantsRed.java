package org.firstinspires.ftc.teamcode.opmode.auton.backdrop.wall;

import org.firstinspires.ftc.teamcode.opmode.auton.backdrop.BackDropConstants;
import org.firstinspires.ftc.teamcode.opmode.auton.util.Constant;

import com.acmerobotics.dashboard.config.Config;

@Config
public class BackDropWallConstantsRed extends BackDropConstants {
    public static void setConstants() {
        SPIKE = new Constant(
                SPIKE_X,
                SPIKE_Y,
                -1,
                3,
                SPIKE_LEFT_HEADING,
                12,
                -2,
                SPIKE_CENTER_HEADING,
                18,
                8,
                SPIKE_RIGHT_HEADING);

        STACK_1 = new Constant(
                STACK_X,
                STACK_Y,
                5,
                1,
                STACK_HEADING,
                5,
                1,
                STACK_HEADING,
                5,
                1,
                STACK_HEADING);

        STACK_2 = new Constant(
                STACK_X,
                STACK_Y,
                5,
                1,
                STACK_HEADING,
                5,
                1,
                STACK_HEADING,
                5,
                1,
                STACK_HEADING);

        TAG_1 = new Constant(
                TAG_X,
                TAG_Y,
                1,
                -4.7,
                TAG_HEADING_1,
                1,
                1,
                TAG_HEADING_1,
                1,
                8,
                TAG_HEADING_1);

        TAG_2 = new Constant(
                TAG_X,
                TAG_Y,
                4,
                -4.7,
                TAG_HEADING_2,
                4,
                1,
                TAG_HEADING_2,
                4,
                8,
                TAG_HEADING_2);

        TAG_3 = new Constant(
                TAG_X,
                TAG_Y,
                4,
                -4.7,
                TAG_HEADING_2,
                4,
                1,
                TAG_HEADING_2,
                4,
                8,
                TAG_HEADING_2);

        FRONT_TRUSS_1 = new Constant(FRONT_TRUSS_X, FRONT_TRUSS_Y, 0, 0, FRONT_TRUSS_HEADING, 0, 0, FRONT_TRUSS_HEADING,
                0, 0, FRONT_TRUSS_HEADING);

        FRONT_TRUSS_2 = new Constant(FRONT_TRUSS_X, FRONT_TRUSS_Y, 0, 0, FRONT_TRUSS_HEADING, 0, 0, FRONT_TRUSS_HEADING,
                0, 0, FRONT_TRUSS_HEADING);

        FRONT_TRUSS_BACK_1 = new Constant(FRONT_TRUSS_X, FRONT_TRUSS_Y, 0, 0, FRONT_TRUSS_HEADING, 0, 0,
                FRONT_TRUSS_HEADING, 0, 0, FRONT_TRUSS_HEADING);

        FRONT_TRUSS_BACK_2 = new Constant(FRONT_TRUSS_X, FRONT_TRUSS_Y, 0, 0, FRONT_TRUSS_HEADING, 0, 0,
                FRONT_TRUSS_HEADING, 0, 0, FRONT_TRUSS_HEADING);

        END_TRUSS_1 = new Constant(END_TRUSS_X, END_TRUSS_Y, 0, 0, END_TRUSS_HEADING, 0, 0, END_TRUSS_HEADING,
                0, 0, END_TRUSS_HEADING);

        END_TRUSS_2 = new Constant(END_TRUSS_X, END_TRUSS_Y, 0, 0, END_TRUSS_HEADING, 0, 0, END_TRUSS_HEADING,
                0, 0, END_TRUSS_HEADING);

        END_TRUSS_BACK_1 = new Constant(END_TRUSS_X, END_TRUSS_Y, 0, 0, END_TRUSS_HEADING, 0, 0,
                END_TRUSS_HEADING, 0, 0, END_TRUSS_HEADING);

        END_TRUSS_BACK_2 = new Constant(END_TRUSS_X, END_TRUSS_Y, 0, 0, END_TRUSS_HEADING, 0, 0,
                END_TRUSS_HEADING, 0, 0, END_TRUSS_HEADING);
    }
}

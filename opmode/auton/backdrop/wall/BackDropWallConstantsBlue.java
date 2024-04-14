package org.firstinspires.ftc.teamcode.opmode.auton.backdrop.wall;

import org.firstinspires.ftc.teamcode.opmode.auton.backdrop.BackDropConstants;
import org.firstinspires.ftc.teamcode.opmode.auton.util.Constant;
import static org.firstinspires.ftc.teamcode.opmode.auton.util.GameConstants.*;

public class BackDropWallConstantsBlue extends BackDropConstants {
    public BackDropWallConstantsBlue() {
        SPIKE = new Constant(
                29.75,
                43.25,
                Math.toRadians(45),
                25,
                31,
                Math.toRadians(45),
                10.75,
                38.25,
                Math.toRadians(15));
    
        STACK_1 = new Constant(
                -61.5,
                40,
                Math.toRadians(180),
                -59.5,
                39,
                Math.toRadians(180),
                -52,
                33,
                Math.toRadians(180));
    
        STACK_2 = new Constant(
                -56.5,
                30.5,
                Math.toRadians(180),
                -56,
                32,
                Math.toRadians(180),
                -39,
                18,
                Math.toRadians(180));
    
        TAG_1 = new Constant(
                52.5,
                41,
                Math.toRadians(180),
                54,
                40,
                Math.toRadians(180),
                54,
                33,
                Math.toRadians(180));
    
        TAG_2 = new Constant(
                56,
                31,
                Math.toRadians(0),
                54,
                31.5,
                Math.toRadians(0),
                62,
                28,
                Math.toRadians(0));
    
        TAG_3 = new Constant(
                59,
                31,
                Math.toRadians(0),
                50,
                35.25,
                Math.toRadians(0),
                68,
                20,
                Math.toRadians(0));
    
        FRONT_TRUSS_1 = new Constant(
                -30,
                60,
                Math.toRadians(0),
                -30,
                61,
                Math.toRadians(0),
                -44,
                52,
                Math.toRadians(0));
    
        FRONT_TRUSS_2 = new Constant(
                -30,
                54.5,
                Math.toRadians(0),
                -30,
                61,
                Math.toRadians(0),
                -38,
                37,
                Math.toRadians(0));
    
        FRONT_TRUSS_BACK_1 = new Constant(
                -30,
                65.7,
                Math.toRadians(180),
                -30,
                67.3,
                Math.toRadians(180),
                -43,
                65,
                Math.toRadians(180));
    
        FRONT_TRUSS_BACK_2 = new Constant(
                -30,
                57,
                Math.toRadians(180),
                -30,
                61,
                Math.toRadians(180),
                -36,
                52,
                Math.toRadians(180));
    
        END_TRUSS_1 = new Constant(
                1,
                60,
                Math.toRadians(0),
                5,
                61,
                Math.toRadians(0),
                1,
                52,
                Math.toRadians(0));
    
        END_TRUSS_2 = new Constant(
                1,
                54.5,
                Math.toRadians(0),
                5,
                61,
                Math.toRadians(0),
                1,
                37,
                Math.toRadians(0));
    
        END_TRUSS_BACK_1 = new Constant(
                1,
                65.7,
                Math.toRadians(180),
                5,
                67.3,
                Math.toRadians(180),
                1,
                65,
                Math.toRadians(180));
    
        END_TRUSS_BACK_2 = new Constant(
                1,
                57,
                Math.toRadians(180),
                5,
                61,
                Math.toRadians(180),
                1,
                52,
                Math.toRadians(180));
    
        PARK = new Constant(
                51.75,
                50,
                Math.toRadians(270),
                51.75,
                63.5,
                Math.toRadians(270),
                51.75,
                46,
                Math.toRadians(270));
    }
}
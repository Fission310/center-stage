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
                32,
                Math.toRadians(45),
                10.75,
                38.25,
                Math.toRadians(15));
    
        STACK_1 = new Constant(
                -61.5,
                36,
                Math.toRadians(180),
                -61,
                37,
                Math.toRadians(180),
                -52,
                33,
                Math.toRadians(180));
    
        STACK_2 = new Constant(
                -56.5,
                30.5,
                Math.toRadians(180),
                -63,
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
                41,
                Math.toRadians(180),
                54,
                33,
                Math.toRadians(180));
    
        TAG_2 = new Constant(
                56,
                35.25,
                Math.toRadians(0),
                54,
                35.25,
                Math.toRadians(0),
                62,
                28,
                Math.toRadians(0));
    
        TAG_3 = new Constant(
                59,
                34,
                Math.toRadians(0),
                51.75,
                35.25,
                Math.toRadians(0),
                68,
                20,
                Math.toRadians(0));
    
        FRONT_TRUSS_1 = new Constant(
                -26,
                60,
                Math.toRadians(0),
                -26,
                61.5,
                Math.toRadians(0),
                -44,
                52,
                Math.toRadians(0));
    
        FRONT_TRUSS_2 = new Constant(
                -26,
                54.5,
                Math.toRadians(0),
                -26,
                66,
                Math.toRadians(0),
                -38,
                37,
                Math.toRadians(0));
    
        FRONT_TRUSS_BACK_1 = new Constant(
                -26,
                65.7,
                Math.toRadians(180),
                -26,
                67.3,
                Math.toRadians(180),
                -43,
                65,
                Math.toRadians(180));
    
        FRONT_TRUSS_BACK_2 = new Constant(
                -26,
                57,
                Math.toRadians(180),
                -26,
                52,
                Math.toRadians(180),
                -36,
                52,
                Math.toRadians(180));
    
        END_TRUSS_1 = new Constant(
                1,
                60,
                Math.toRadians(0),
                1,
                61.5,
                Math.toRadians(0),
                1,
                52,
                Math.toRadians(0));
    
        END_TRUSS_2 = new Constant(
                1,
                54.5,
                Math.toRadians(0),
                1,
                66,
                Math.toRadians(0),
                1,
                37,
                Math.toRadians(0));
    
        END_TRUSS_BACK_1 = new Constant(
                1,
                65.7,
                Math.toRadians(180),
                1,
                67.3,
                Math.toRadians(180),
                1,
                65,
                Math.toRadians(180));
    
        END_TRUSS_BACK_2 = new Constant(
                1,
                57,
                Math.toRadians(180),
                1,
                52,
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
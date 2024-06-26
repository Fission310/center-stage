package org.firstinspires.ftc.teamcode.opmode.auton.backdrop.wall;

import org.firstinspires.ftc.teamcode.opmode.auton.backdrop.BackDropConstants;
import org.firstinspires.ftc.teamcode.opmode.auton.util.Constant;
import static org.firstinspires.ftc.teamcode.opmode.auton.util.GameConstants.*;

public class BackDropWallConstantsRed extends BackDropConstants {
    public BackDropWallConstantsRed() {
        SPIKE = new Constant(
                10.75,
                38.25,
                Math.toRadians(15),
                25.5,
                31,
                Math.toRadians(45),
                29.75,
                43.25,
                Math.toRadians(45));
    
        STACK_1 = new Constant(
                -62.1,
                35.75,
                Math.toRadians(180),
                -62.5,
                33.75,
                Math.toRadians(180),
                -62.1,
                35.75,
                Math.toRadians(180));
    
        STACK_2 = new Constant(
                -62.1,
                35.75,
                Math.toRadians(180),
                -60,
                43.5,
                Math.toRadians(180),
                -62.1,
                35.75,
                Math.toRadians(180));
    
        TAG_1 = new Constant(
                51.75,
                35.25,
                Math.toRadians(180),
                51.75,
                34.5,
                Math.toRadians(180),
                51.75,
                35.25,
                Math.toRadians(180));
    
        TAG_2 = new Constant(
                51.75,
                35.25,
                Math.toRadians(0),
                51.75,
                35.25,
                Math.toRadians(0),
                51.75,
                35.25,
                Math.toRadians(0));
    
        TAG_3 = new Constant(
                51.75,
                35.25,
                Math.toRadians(0),
                51.75,
                35.25,
                Math.toRadians(0),
                51.75,
                35.25,
                Math.toRadians(0));
    
        FRONT_TRUSS_1 = new Constant(
                -20,
                63,
                Math.toRadians(0),
                -22,
                58.5,
                Math.toRadians(0),
                -20,
                63,
                Math.toRadians(0));
    
        FRONT_TRUSS_2 = new Constant(
                -20,
                63,
                Math.toRadians(0),
                -22,
                58.5,
                Math.toRadians(0),
                -20,
                63,
                Math.toRadians(0));
    
        FRONT_TRUSS_BACK_1 = new Constant(
                -20,
                63,
                Math.toRadians(180),
                -20,
                63,
                Math.toRadians(180),
                -20,
                63,
                Math.toRadians(180));
    
        FRONT_TRUSS_BACK_2 = new Constant(
                -20,
                63,
                Math.toRadians(180),
                -20,
                63,
                Math.toRadians(180),
                -20,
                63,
                Math.toRadians(180));
    
        END_TRUSS_1 = new Constant(
                20,
                63,
                Math.toRadians(0),
                20,
                58,
                Math.toRadians(0),
                20,
                63,
                Math.toRadians(0));
    
        END_TRUSS_2 = new Constant(
                20,
                63,
                Math.toRadians(0),
                20,
                58,
                Math.toRadians(0),
                20,
                63,
                Math.toRadians(0));
    
        END_TRUSS_BACK_1 = new Constant(
                20,
                63,
                Math.toRadians(180),
                20,
                60,
                Math.toRadians(180),
                20,
                63,
                Math.toRadians(180));
    
        END_TRUSS_BACK_2 = new Constant(
                20,
                63,
                Math.toRadians(180),
                20,
                52,
                Math.toRadians(180),
                20,
                63,
                Math.toRadians(180));
    
        PARK = new Constant(
                51.75,
                63.5,
                Math.toRadians(270),
                51.75,
                63.5,
                Math.toRadians(270),
                51.75,
                63.5,
                Math.toRadians(270));
    }
}
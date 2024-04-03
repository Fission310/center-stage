package org.firstinspires.ftc.teamcode.opmode.auton.backdrop.truss;

import org.firstinspires.ftc.teamcode.opmode.auton.backdrop.BackDropConstants;
import org.firstinspires.ftc.teamcode.opmode.auton.util.Constant;
import static org.firstinspires.ftc.teamcode.opmode.auton.util.GameConstants.*;

public class BackDropTrussConstantsBlue extends BackDropConstants {
    public BackDropTrussConstantsBlue() {
        SPIKE = new Constant(
                10.75,
                38.25,
                Math.toRadians(15),
                22.75,
                33.25,
                Math.toRadians(45),
                29.75,
                43.25,
                Math.toRadians(45));
    
        STACK_1 = new Constant(
                -62.1,
                13,
                Math.toRadians(180),
                -62,
                23,
                Math.toRadians(180),
                -62.1,
                15,
                Math.toRadians(180));
    
        STACK_2 = new Constant(
                -62.1,
                11.75,
                Math.toRadians(180),
                -73,
                26,
                Math.toRadians(180),
                -62.1,
                13,
                Math.toRadians(180));
    
        TAG_1 = new Constant(
                53.2,
                32.25,
                Math.toRadians(180),
                53.2,
                37,
                Math.toRadians(180),
                53.2,
                34.5,
                Math.toRadians(180));
    
        TAG_2 = new Constant(
                42,
                35.25,
                Math.toRadians(0),
                42,
                40,
                Math.toRadians(0),
                42,
                35.25,
                Math.toRadians(0));
    
        TAG_3 = new Constant(
                34,
                35.25,
                Math.toRadians(0),
                34,
                43,
                Math.toRadians(0),
                34,
                35.25,
                Math.toRadians(0));
    
        FRONT_TRUSS_1 = new Constant(
                -20,
                17.5,
                Math.toRadians(0),
                -20,
                17.5,
                Math.toRadians(0),
                -20,
                17.5,
                Math.toRadians(0));
    
        FRONT_TRUSS_2 = new Constant(
                -20,
                22,
                Math.toRadians(0),
                -20,
                22,
                Math.toRadians(0),
                -20,
                22,
                Math.toRadians(0));
    
        FRONT_TRUSS_BACK_1 = new Constant(
                -20,
                17.5,
                Math.toRadians(180),
                -20,
                17.5,
                Math.toRadians(180),
                -20,
                17.5,
                Math.toRadians(180));
    
        FRONT_TRUSS_BACK_2 = new Constant(
                -20,
                22,
                Math.toRadians(180),
                -20,
                22,
                Math.toRadians(180),
                -20,
                22,
                Math.toRadians(180));
    
        END_TRUSS_1 = new Constant(
                20,
                17.5,
                Math.toRadians(0),
                20,
                17.5,
                Math.toRadians(0),
                20,
                17.5,
                Math.toRadians(0));
    
        END_TRUSS_2 = new Constant(
                20,
                22,
                Math.toRadians(0),
                20,
                22,
                Math.toRadians(0),
                20,
                22,
                Math.toRadians(0));
    
        END_TRUSS_BACK_1 = new Constant(
                20,
                17.5,
                Math.toRadians(180),
                20,
                17.5,
                Math.toRadians(180),
                20,
                17.5,
                Math.toRadians(180));
    
        END_TRUSS_BACK_2 = new Constant(
                20,
                22,
                Math.toRadians(180),
                20,
                22,
                Math.toRadians(180),
                20,
                22,
                Math.toRadians(180));
    
        PARK = new Constant(
                48,
                15,
                Math.toRadians(270),
                48,
                15,
                Math.toRadians(270),
                48,
                15,
                Math.toRadians(270));
    }
}
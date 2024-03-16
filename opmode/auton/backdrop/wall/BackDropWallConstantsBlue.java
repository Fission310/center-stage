package org.firstinspires.ftc.teamcode.opmode.auton.backdrop.wall;

import org.firstinspires.ftc.teamcode.opmode.auton.backdrop.BackDropConstants;
import org.firstinspires.ftc.teamcode.opmode.auton.util.Constant;
import static org.firstinspires.ftc.teamcode.opmode.auton.util.GameConstants.*;

import com.acmerobotics.dashboard.config.Config;

@Config
public class BackDropWallConstantsBlue extends BackDropConstants {
    public BackDropWallConstantsBlue() {
        SPIKE = new Constant(
                10.75,
                38.25,
                Math.toRadians(15),
                23.75,
                33.25,
                Math.toRadians(45),
                29.75,
                43.25,
                Math.toRadians(45));

        STACK_1 = new Constant(
                -62.1,
                11.75,
                LEFT,
                -62.1,
                11.75,
                LEFT,
                -62.1,
                11.75,
                LEFT);

        STACK_2 = new Constant(
                -62.1,
                11.75,
                LEFT,
                -62.1,
                11.75,
                LEFT,
                -62.1,
                11.75,
                LEFT);

        TAG_1 = new Constant(
                51.75,
                35.25,
                LEFT,
                51.75,
                35.25,
                LEFT,
                51.75,
                35.25,
                LEFT);

        TAG_2 = new Constant(
                51.75,
                35.25,
                RIGHT,
                51.75,
                35.25,
                RIGHT,
                51.75,
                35.25,
                RIGHT);

        TAG_3 = new Constant(
                51.75,
                35.25,
                RIGHT,
                51.75,
                35.25,
                RIGHT,
                51.75,
                35.25,
                RIGHT);

        FRONT_TRUSS_1 = new Constant(
                23.5,
                11.75,
                RIGHT,
                23.5,
                11.75,
                RIGHT,
                23.5,
                11.75,
                RIGHT);

        FRONT_TRUSS_2 = new Constant(
                23.5,
                11.75,
                RIGHT,
                23.5,
                11.75,
                RIGHT,
                23.5,
                11.75,
                RIGHT);

        FRONT_TRUSS_BACK_1 = new Constant(
                23.5,
                11.75,
                LEFT,
                23.5,
                11.75,
                LEFT,
                23.5,
                11.75,
                LEFT);

        FRONT_TRUSS_BACK_2 = new Constant(
                23.5,
                11.75,
                LEFT,
                23.5,
                11.75,
                LEFT,
                23.5,
                11.75,
                LEFT);

        END_TRUSS_1 = new Constant(
                23.5, 
                11.75,
                RIGHT,
                23.5, 
                11.75,
                RIGHT,
                23.5, 
                11.75,
                RIGHT);

        END_TRUSS_2 = new Constant(
                23.5, 
                11.75,
                RIGHT,
                23.5, 
                11.75,
                RIGHT,
                23.5, 
                11.75,
                RIGHT);

        END_TRUSS_BACK_1 = new Constant(
                23.5, 
                11.75,
                LEFT,
                23.5, 
                11.75,
                LEFT,
                23.5, 
                11.75,
                LEFT);

        END_TRUSS_BACK_2 = new Constant(
                23.5, 
                11.75,
                LEFT,
                23.5, 
                11.75,
                LEFT,
                23.5, 
                11.75,
                LEFT);

        PARK = new Constant(
                51.75,
                63.5,
                DOWN,
                51.75,
                63.5,
                DOWN,
                51.75,
                63.5,
                DOWN);
    }
}

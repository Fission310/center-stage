package org.firstinspires.ftc.teamcode.opmode.auton.backdrop;

import static org.firstinspires.ftc.teamcode.opmode.auton.util.GameConstants.*;

import org.firstinspires.ftc.teamcode.opmode.auton.backdrop.truss.BackDropTrussConstantsBlue;
import org.firstinspires.ftc.teamcode.opmode.auton.backdrop.truss.BackDropTrussConstantsRed;
import org.firstinspires.ftc.teamcode.opmode.auton.backdrop.wall.BackDropWallConstantsBlue;
import org.firstinspires.ftc.teamcode.opmode.auton.backdrop.wall.BackDropWallConstantsRed;

import com.acmerobotics.roadrunner.geometry.Pose2d;

public class BackDropConstantsDash {
    // Start Pose
    public static final double START_HEADING = UP;

    public static final double START_X = TILE_LENGTH / 2.0;
    public static final double START_Y = WALL_POS - BOT_LENGTH / 2.0;

    public static Pose2d START_POSE;

    public static final double SPIKE_TANGENT = RIGHT;

    public static BackDropConstants wallBlue = new BackDropWallConstantsBlue();
    public static BackDropConstants wallRed = new BackDropWallConstantsRed();
    public static BackDropConstants trussBlue = new BackDropTrussConstantsBlue();
    public static BackDropConstants trussRed = new BackDropTrussConstantsRed();
}

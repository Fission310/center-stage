package org.firstinspires.ftc.teamcode.opmode.auton.front;

import static org.firstinspires.ftc.teamcode.opmode.auton.util.GameConstants.*;

import org.firstinspires.ftc.teamcode.opmode.auton.front.truss.FrontTrussConstantsBlue;
import org.firstinspires.ftc.teamcode.opmode.auton.front.truss.FrontTrussConstantsRed;
import org.firstinspires.ftc.teamcode.opmode.auton.front.wall.FrontWallConstantsBlue;
import org.firstinspires.ftc.teamcode.opmode.auton.front.wall.FrontWallConstantsRed;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;

@Config
public class FrontConstantsDash {
    // Start Pose
    public static final double START_HEADING = UP;

    public static final double START_X = -3.0 * TILE_LENGTH / 2.0;
    public static final double START_Y = WALL_POS - BOT_LENGTH / 2.0;

    public static Pose2d START_POSE = new Pose2d(START_X, START_Y, START_HEADING);

    public static final double SPIKE_TANGENT = LEFT;

    public static FrontConstants wallBlue = new FrontWallConstantsBlue();
    public static FrontConstants wallRed = new FrontWallConstantsRed();
    public static FrontConstants trussBlue = new FrontTrussConstantsBlue();
    public static FrontConstants trussRed = new FrontTrussConstantsRed();
}

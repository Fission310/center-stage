package org.firstinspires.ftc.teamcode.opmode.auton.backdrop.wall;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.opmode.auton.backdrop.BackDropAuto;
import org.firstinspires.ftc.teamcode.opmode.auton.backdrop.BackDropConstantsDash;
import org.firstinspires.ftc.teamcode.opmode.auton.util.Color;

@Autonomous(name = "BackDropWallAutoBlue", preselectTeleOp = "Main")
public class BackDropWallAutoBlue extends BackDropAuto {
    public BackDropWallAutoBlue() {
        super(Color.BLUE, BackDropConstantsDash.wallBlue);
    }
}

package org.firstinspires.ftc.teamcode.opmode.auton.front.wall;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.opmode.auton.front.FrontAuto;
import org.firstinspires.ftc.teamcode.opmode.auton.front.FrontConstantsDash;
import org.firstinspires.ftc.teamcode.opmode.auton.util.Color;

@Autonomous(name = "FrontWallAutoRed", preselectTeleOp = "Main")
public class FrontWallAutoRed extends FrontAuto {
    public FrontWallAutoRed() {
        super(Color.RED, FrontConstantsDash.wallRed);
    }
}

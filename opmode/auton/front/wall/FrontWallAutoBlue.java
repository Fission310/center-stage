package org.firstinspires.ftc.teamcode.opmode.auton.front.wall;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.opmode.auton.front.FrontAuto;
import org.firstinspires.ftc.teamcode.opmode.auton.front.FrontConstantsDash;
import org.firstinspires.ftc.teamcode.opmode.auton.util.Color;

@Autonomous(name = "FrontWallAutoBlue", preselectTeleOp = "Main")
public class FrontWallAutoBlue extends FrontAuto {
    public FrontWallAutoBlue() {
        super(Color.BLUE, FrontConstantsDash.wallBlue);
    }
}

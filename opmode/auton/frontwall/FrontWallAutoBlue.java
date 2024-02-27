package org.firstinspires.ftc.teamcode.opmode.auton.frontwall;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.opmode.auton.util.Color;

@Autonomous(name = "FrontWallAutoBlue", preselectTeleOp = "Main")
public class FrontWallAutoBlue extends FrontWallAuto {
    public FrontWallAutoBlue() {
        super(Color.BLUE);
    }
}

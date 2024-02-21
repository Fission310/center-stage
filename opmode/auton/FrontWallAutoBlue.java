package org.firstinspires.ftc.teamcode.opmode.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.opmode.auton.AutoConstants.Color;

@Autonomous(name = "FrontWallAutoBlue", preselectTeleOp = "Main")
public class FrontWallAutoBlue extends FrontWallAuto {
    public FrontWallAutoBlue() {
        super(Color.BLUE);
    }
}

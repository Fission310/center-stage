package org.firstinspires.ftc.teamcode.opmode.auton.frontwall;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.opmode.auton.util.Color;

@Autonomous(name = "FrontWallAutoRed", preselectTeleOp = "Main")
public class FrontWallAutoRed extends FrontWallAuto {
    public FrontWallAutoRed() {
        super(Color.RED);
    }
}

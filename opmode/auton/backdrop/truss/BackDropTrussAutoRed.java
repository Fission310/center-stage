package org.firstinspires.ftc.teamcode.opmode.auton.backdrop.truss;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.opmode.auton.backdrop.BackDropAuto;
import org.firstinspires.ftc.teamcode.opmode.auton.util.Color;

@Autonomous(name = "BackDropTrussAutoRed", preselectTeleOp = "Main")
public class BackDropTrussAutoRed extends BackDropAuto {
    public BackDropTrussAutoRed() {
        super(Color.RED);
        BackDropTrussConstantsRed.setConstants();
    }
}

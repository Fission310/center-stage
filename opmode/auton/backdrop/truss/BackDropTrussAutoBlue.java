package org.firstinspires.ftc.teamcode.opmode.auton.backdrop.truss;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.opmode.auton.backdrop.BackDropAuto;
import org.firstinspires.ftc.teamcode.opmode.auton.util.Color;

@Autonomous(name = "BackDropTrussAutoBlue", preselectTeleOp = "Main")
public class BackDropTrussAutoBlue extends BackDropAuto {
    public BackDropTrussAutoBlue() {
        super(Color.BLUE);
        BackDropTrussConstantsBlue.setConstants();
    }
}

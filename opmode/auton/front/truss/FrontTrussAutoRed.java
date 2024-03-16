package org.firstinspires.ftc.teamcode.opmode.auton.front.truss;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.opmode.auton.front.FrontAuto;
import org.firstinspires.ftc.teamcode.opmode.auton.front.FrontConstantsDash;
import org.firstinspires.ftc.teamcode.opmode.auton.util.Color;

@Autonomous(name = "FrontTrussAutoRed", preselectTeleOp = "Main")
public class FrontTrussAutoRed extends FrontAuto {
    public FrontTrussAutoRed() {
        super(Color.RED, FrontConstantsDash.trussRed);
    }
}

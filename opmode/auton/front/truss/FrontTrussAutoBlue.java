package org.firstinspires.ftc.teamcode.opmode.auton.front.truss;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.opmode.auton.front.FrontAuto;
import org.firstinspires.ftc.teamcode.opmode.auton.front.FrontConstantsDash;
import org.firstinspires.ftc.teamcode.opmode.auton.util.Color;

@Autonomous(name = "FrontTrussAutoBlue", preselectTeleOp = "MTI")
public class FrontTrussAutoBlue extends FrontAuto {
    public FrontTrussAutoBlue() {
        super(Color.BLUE, FrontConstantsDash.trussBlue);
    }
}

package org.firstinspires.ftc.teamcode.opmode.auton.fronttruss;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.opmode.auton.util.Color;

@Autonomous(name = "FrontTrussAutoRed", preselectTeleOp = "Main")
public class FrontTrussAutoRed extends FrontTrussAuto {
    public FrontTrussAutoRed() {
        super(Color.RED);
    }
}

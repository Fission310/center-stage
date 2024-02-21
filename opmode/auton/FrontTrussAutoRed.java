package org.firstinspires.ftc.teamcode.opmode.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.opmode.auton.AutoConstants.Color;

@Autonomous(name = "FrontTrussAutoRed", preselectTeleOp = "Main")
public class FrontTrussAutoRed extends FrontTrussAuto {
    public FrontTrussAutoRed() {
        super(Color.RED);
    }
}

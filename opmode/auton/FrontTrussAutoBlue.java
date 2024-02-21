package org.firstinspires.ftc.teamcode.opmode.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.opmode.auton.AutoConstants.Color;

@Autonomous(name = "FrontTrussAutoBlue", preselectTeleOp = "Main")
public class FrontTrussAutoBlue extends FrontTrussAuto {
    public FrontTrussAutoBlue() {
        super(Color.BLUE);
    }
}

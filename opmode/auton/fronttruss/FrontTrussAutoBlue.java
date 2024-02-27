package org.firstinspires.ftc.teamcode.opmode.auton.fronttruss;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.opmode.auton.util.Color;

@Autonomous(name = "FrontTrussAutoBlue", preselectTeleOp = "Main")
public class FrontTrussAutoBlue extends FrontTrussAuto {
    public FrontTrussAutoBlue() {
        super(Color.BLUE);
    }
}

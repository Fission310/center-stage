package org.firstinspires.ftc.teamcode.opmode.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.opmode.auton.AutoConstants.Color;

@Autonomous(name = "FrontAutoBlue", preselectTeleOp = "Main")
public class FrontAutoBlue extends FrontAuto {
    public FrontAutoBlue() {
        super(Color.BLUE);
    }
}

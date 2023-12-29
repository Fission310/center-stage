package org.firstinspires.ftc.teamcode.opmode.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.opmode.auton.AutoConstants.Color;

@Autonomous(name = "FrontAutoRed", preselectTeleOp = "Main")
public class FrontAutoRed extends FrontAuto {
    public FrontAutoRed() {
        super(Color.BLUE);
    }
}

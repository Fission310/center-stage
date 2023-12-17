package org.firstinspires.ftc.teamcode.opmode.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.opmode.auton.AutoConstants.Color;

@Autonomous(name = "BackDropAutoBlue", preselectTeleOp = "Main")
public class BackDropAutoBlue extends BackDropAuto {
    public BackDropAutoBlue() {
        super(Color.BLUE);
    }
}

package org.firstinspires.ftc.teamcode.opmode.auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.opmode.auton.AutoConstants.Color;

@Autonomous(name = "BackDropAutoRed", preselectTeleOp = "Main")
public class BackDropAutoRed extends BackDropAuto {
    public BackDropAutoRed() {
        super(Color.RED);
    }
}

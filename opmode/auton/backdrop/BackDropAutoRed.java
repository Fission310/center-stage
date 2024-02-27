package org.firstinspires.ftc.teamcode.opmode.auton.backdrop;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.opmode.auton.util.Color;

@Autonomous(name = "BackDropAutoRed", preselectTeleOp = "Main")
public class BackDropAutoRed extends BackDropAuto {
    public BackDropAutoRed() {
        super(Color.RED);
    }
}

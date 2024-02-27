package org.firstinspires.ftc.teamcode.opmode.auton.backdrop;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.opmode.auton.util.Color;

@Autonomous(name = "BackDropAutoBlue", preselectTeleOp = "Main")
public class BackDropAutoBlue extends BackDropAuto {
    public BackDropAutoBlue() {
        super(Color.BLUE);
    }
}

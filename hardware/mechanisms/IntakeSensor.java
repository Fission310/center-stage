package org.firstinspires.ftc.teamcode.hardware.mechanisms;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.stuyfission.fissionlib.util.Mechanism;

@Config
public class IntakeSensor extends Mechanism {

    private ColorSensor sensor;

    public static int GREEN = 100;
    public static int WHITE = 100;
    public static int PURPLE = 100;

    public IntakeSensor(LinearOpMode opMode) {
        this.opMode = opMode;
    }

    @Override
    public void init(HardwareMap hwMap) {
        sensor = hwMap.get(ColorSensor.class, "intakeSensor");
    }

    public boolean isGreen() {
        return sensor.green() > GREEN;
    }

    public boolean isWhite() {
        return (sensor.red() + sensor.green() + sensor.blue()) / 3 > WHITE;
    }

    public boolean isPurple() {
        return (sensor.red() + sensor.blue()) / 2 > PURPLE;
    }

    public boolean isPixel() {
        return isGreen() || isWhite() || isPurple();
    }
}

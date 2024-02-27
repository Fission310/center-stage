package org.firstinspires.ftc.teamcode.opmode.auton.util;

import com.acmerobotics.roadrunner.geometry.Vector2d;

public class Constant {
    private Vector2d[] vectors;
    private double[] headings;

    public Constant(double x, double y, double[] ox, double[] oy) {
        this(x, y, ox, oy, new double[3]);
    }

    public Constant(double x, double y, double[] ox, double[] oy, double[] heading) {
        vectors = new Vector2d[3];
        headings = heading;
        for (int i = 0; i < 3; i++) {
            vectors[i] = new Vector2d(x + ox[i], y + oy[i]);
        }
    }

    public Vector2d getV(int i) {
        return vectors[i];
    }

    public double getH(int i) {
        return headings[i];
    }
}

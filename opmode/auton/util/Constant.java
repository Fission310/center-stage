package org.firstinspires.ftc.teamcode.opmode.auton.util;

import com.acmerobotics.roadrunner.geometry.Vector2d;

public class Constant {
    public double LEFT_X;
    public double LEFT_Y;
    public double LEFT_H;
    public double CENTER_X;
    public double CENTER_Y;
    public double CENTER_H;
    public double RIGHT_X;
    public double RIGHT_Y;
    public double RIGHT_H;

    public Constant(double lx, double ly, double lh, double cx, double cy, double ch, double rx, double ry, double rh) {
        LEFT_X = lx;
        LEFT_Y = ly;
        LEFT_H = lh;
        CENTER_X = lx;
        CENTER_Y = ly;
        CENTER_H = lh;
        RIGHT_X = lx;
        RIGHT_Y = ly;
        RIGHT_H = lh;
    }

    public Vector2d getV(int i) {
        switch (i) {
            case 0:
                return new Vector2d(LEFT_X, LEFT_Y);
            case 1:
                return new Vector2d(CENTER_X, CENTER_Y);
            case 2:
                return new Vector2d(RIGHT_X, RIGHT_Y);
        }
        return new Vector2d(CENTER_X, CENTER_Y);
    }

    public double getH(int i) {
        switch (i) {
            case 0:
                return LEFT_H;
            case 1:
                return CENTER_H;
            case 2:
                return RIGHT_H;
        }
        return CENTER_H;
    }
}

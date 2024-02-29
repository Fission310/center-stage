package org.firstinspires.ftc.teamcode.opmode.auton;

import org.firstinspires.ftc.teamcode.drive.DriveConstants;

import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import org.firstinspires.ftc.teamcode.hardware.mechanisms.Webcam.Position;

import org.firstinspires.ftc.teamcode.opmode.auton.util.Color;
import static org.firstinspires.ftc.teamcode.opmode.auton.backdrop.BackDropConstantsRed.*;

public class Meep {

    public static boolean reflect;

    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        Position pos = Position.RIGHT;
        int i = pos.index;
        Color color = Color.RED;

        reflect = color == Color.RED;

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setConstraints(DriveConstants.MAX_VEL, DriveConstants.MAX_ACCEL, DriveConstants.MAX_ANG_VEL,
                        DriveConstants.MAX_ANG_ACCEL, DriveConstants.TRACK_WIDTH)
                .setDimensions(17, 15)
                .followTrajectorySequence(
                        drive -> drive.trajectorySequenceBuilder(reflectX(START_POSE))
                                .lineToLinearHeading(new Pose2d(reflectX(SPIKE.getV(i)),
                                        reflectX(SPIKE.getH(i))))
                                .setReversed(true)
                                .lineToLinearHeading(new Pose2d(reflectX(TAG_1.getV(i)),
                                        reflectX(TAG_1.getH(i))))
                                .setReversed(false)
                                .splineToConstantHeading(reflectX(BACK_TRUSS.getV(i)),
                                        reflectX(BACK_TRUSS.getH(i)))
                                .splineTo(reflectX(STACK.getV(i)),
                                        reflectX(STACK.getH(i)))
                                .setReversed(true)
                                .splineTo(reflectX(TRUSS.getV(i)),
                                        reflectX(TRUSS.getH(i)))
                                .splineToConstantHeading(reflectX(TAG_2.getV(i)),
                                        reflectX(TAG_2.getH(i)))
                                .setReversed(false)
                                .splineToConstantHeading(reflectX(PARK.getV(i)), reflectX(PARK.getH(i)))
                                .build());

        meepMeep.setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }

    public static Vector2d reflectX(Vector2d vector) {
        if (reflect) {
            return new Vector2d(vector.getX(), vector.getY() * -1);
        }
        return vector;
    }

    public static Pose2d reflectX(Pose2d pose) {
        if (reflect) {
            return new Pose2d(pose.getX(), pose.getY() * -1, pose.getHeading() * -1);
        }
        return pose;
    }

    public static double reflectX(double theta) {
        if (reflect) {
            return theta * -1;
        }
        return theta;
    }
}

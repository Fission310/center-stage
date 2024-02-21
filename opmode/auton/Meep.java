package org.firstinspires.ftc.teamcode.opmode.auton;

import org.firstinspires.ftc.teamcode.drive.DriveConstants;

import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import org.firstinspires.ftc.teamcode.hardware.mechanisms.Webcam.Position;
import org.firstinspires.ftc.teamcode.opmode.auton.AutoConstants.Color;

public class Meep {

    public static boolean reflect;

    public static void main(String[] args) {
        AutoConstants.init();
        MeepMeep meepMeep = new MeepMeep(800);

        Position pos = Position.CENTER;
        Color color = Color.RED;

        reflect = color == Color.RED;

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setConstraints(DriveConstants.MAX_VEL, DriveConstants.MAX_ACCEL, DriveConstants.MAX_ANG_VEL,
                        DriveConstants.MAX_ANG_ACCEL, DriveConstants.TRACK_WIDTH)
                .setDimensions(17, 15)
                .followTrajectorySequence(
                        drive -> drive.trajectorySequenceBuilder(reflectX(AutoConstants.FR_START_POSE))
                                .lineToLinearHeading(reflectX(new Pose2d(AutoConstants.FR_SPIKE_VECTORS[pos.index],
                                        AutoConstants.FR_SPIKE_HEADINGS[pos.index])))
                                .setTangent(Math.toRadians(180))
                                .splineToLinearHeading(
                                        reflectX(new Pose2d(AutoConstants.FR_WALL_STACK_VECTOR,
                                                AutoConstants.FR_STACK_HEADING)),
                                        reflectX(AutoConstants.FR_STACK_HEADING))
                                .setReversed(true)
                                .splineToConstantHeading(reflectX(AutoConstants.WALL_START_VECTOR), reflectX(AutoConstants.TRUSS_HEADING))
                                .splineToConstantHeading(reflectX(AutoConstants.WALL_END_VECTOR),
                                        reflectX(AutoConstants.TRUSS_HEADING))
                                .splineToConstantHeading(reflectX(AutoConstants.TAG_VECTORS[pos.index]), AutoConstants.TAG_HEADING)
                                .back(reflectX(2))
                                .splineToConstantHeading(reflectX(AutoConstants.TAG_VECTORS[2 - pos.index + pos.index % 2]),
                                        reflectX(AutoConstants.TAG_HEADINGS[2 - pos.index + pos.index % 2]))
                                .setReversed(false)
//                                .splineToConstantHeading(reflectX(TRUSS_VECTOR), reflectX(LEFT))
//                                .splineToConstantHeading(reflectX(new Vector2d(FR_START_X, TRUSS_Y)), reflectX(LEFT))
//                                .splineToConstantHeading(reflectX(FR_STACK_VECTOR), FR_STACK_HEADING)
//                                .setReversed(true)
//                                .splineToConstantHeading(reflectX(new Vector2d(FR_START_X, TRUSS_Y)), reflectX(TRUSS_HEADING))
//                                .splineToConstantHeading(reflectX(TRUSS_VECTOR),
//                                        reflectX(TRUSS_HEADING))
//                                .splineToConstantHeading(reflectX(WP_VECTORS[Math.abs(numPos - 1)]), WP_HEADING)
//                                .setReversed(false)
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

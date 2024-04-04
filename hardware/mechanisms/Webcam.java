package org.firstinspires.ftc.teamcode.hardware.mechanisms;

import java.util.List;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.stuyfission.fissionlib.util.Mechanism;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.opmode.auton.util.Color;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

@Config
public class Webcam extends Mechanism {

    private Detector detector;
    private OpenCvCamera camera;
    private VisionPortal visionPortal;
    private AprilTagProcessor aprilTag;
    private AprilTagDetection desiredTag = null;
    private WebcamName webcamName;

    private Color color;

    public static int NONE = 6;

    public static int LOW_H_R = 1;
    public static int LOW_H_B = 105;

    public static int HIGH_H_R = 5;
    public static int HIGH_H_B = 120;

    public static float DECIMATION = 3;

    private int DESIRED_TAG_ID;


    public Webcam(LinearOpMode opMode, Color color) {
        this.opMode = opMode;
        this.color = color;
    }

    @Override
    public void init(HardwareMap hwMap) {
        int cameraMonitorViewId = hwMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id",
                hwMap.appContext.getPackageName());

        webcamName = hwMap.get(WebcamName.class, "webcam");
        camera = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMonitorViewId);

        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(Detector.SCREEN_WIDTH, Detector.SCREEN_HEIGHT, OpenCvCameraRotation.UPRIGHT);
                FtcDashboard.getInstance().startCameraStream(camera, 0);
            }

            @Override
            public void onError(int errorCode) {
            }
        });

        detector = new Detector(color);
        camera.setPipeline(detector);
    }

    public void aprilTagInit(){
        aprilTag = new AprilTagProcessor.Builder().build();
        aprilTag.setDecimation(DECIMATION);
        visionPortal = new VisionPortal.Builder()
                .setCamera(webcamName)
                .addProcessor(aprilTag)
                .build();
    }

    public void setDesiredTag(int tag) {
        DESIRED_TAG_ID = tag + 1;
    }

    public boolean detectAprilTag(Telemetry telemetry) {
        boolean targetFound = false;

        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                telemetry.addData("detected ID", detection.id);
                if ((DESIRED_TAG_ID < 0) || (detection.id == DESIRED_TAG_ID)) {
                    targetFound = true;
                    desiredTag = detection;
                    break;
                } else {
                    telemetry.addData("Skipping", "Tag ID %d is not desired", detection.id);
                }
            } else {
                telemetry.addData("Unknown", "Tag ID %d is not in TagLibrary", detection.id);
            }
        }

        telemetry.addData("DESIRED TAG", DESIRED_TAG_ID);
        return targetFound;
    }

    public void relocalize(SampleMecanumDrive drive) {
        drive.setPoseEstimate(new Pose2d(
                desiredTag.metadata.fieldPosition.get(0) - desiredTag.ftcPose.x - 0.0394 * 205.6,
                desiredTag.metadata.fieldPosition.get(1) - desiredTag.ftcPose.y,
                Math.toRadians(0) + desiredTag.ftcPose.bearing));
    }

    public enum Position {
        CENTER(1),
        RIGHT(2),
        LEFT(0);

        public int index;

        Position(int index) {
            this.index = index;
        }
    }

    public Position getPosition() {
        return detector.getPosition();
    }

    public void stopStreaming() {
        camera.stopStreaming();
        camera.closeCameraDevice();
    }

    private static class Detector extends OpenCvPipeline {

        private Color color;

        private Position pos = Position.CENTER;

        private Mat mat = new Mat();

        private FtcDashboard dashboard = FtcDashboard.getInstance();
        private Telemetry telemetry = dashboard.getTelemetry();

        private static Scalar lowHSV;
        private static Scalar highHSV;

        private static final int SCREEN_WIDTH = 1280;
        private static final int SCREEN_HEIGHT = 720;

        private static final Rect LEFT_ROI = new Rect(
                new Point(0, 0),
                new Point(SCREEN_WIDTH / 2.0, SCREEN_HEIGHT));

        private static final Rect CENTER_ROI = new Rect(
                new Point(SCREEN_WIDTH / 2.0, 0),
                new Point(SCREEN_WIDTH, SCREEN_HEIGHT));

        private static final Rect RIGHT_ROI = new Rect(
                new Point(SCREEN_WIDTH / 3.0 * 2, 0),
                new Point(SCREEN_WIDTH, SCREEN_HEIGHT));

        private static double leftPercent;
        private static double centerPercent;
        private static double rightPercent;

        public Detector(Color color) {
            this.color = color;
        }

        public Position getPosition() {
            return pos;
        }

        @Override
        public Mat processFrame(Mat input) {
            Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);

            switch (color) {
                case RED:
                    lowHSV = new Scalar(LOW_H_R, 50, 50);
                    highHSV = new Scalar(HIGH_H_R, 255, 255);
                    break;
                case BLUE:
                    lowHSV = new Scalar(LOW_H_B, 50, 50);
                    highHSV = new Scalar(HIGH_H_B, 255, 255);
                    break;
            }

            Core.inRange(mat, lowHSV, highHSV, mat);

            // submats for the boxes, these are the regions that'll detect the color
            Mat leftBox = mat.submat(LEFT_ROI);
            Mat centerBox = mat.submat(CENTER_ROI);
            // Mat rightBox = mat.submat(RIGHT_ROI);

            // how much in each region is white aka the color we filtered through the mask
            leftPercent = Core.sumElems(leftBox).val[0] / LEFT_ROI.area();
            centerPercent = Core.sumElems(centerBox).val[0] / CENTER_ROI.area();
            // rightPercent = Core.sumElems(rightBox).val[0] / RIGHT_ROI.area() / 255;

            telemetry.addData("LEFT", leftPercent);
            telemetry.addData("CENTER", centerPercent);
            // telemetry.addData("RIGHT", rightPercent);

            if (leftPercent > centerPercent && leftPercent > NONE) {
                Imgproc.rectangle(mat, LEFT_ROI, new Scalar(60, 255, 255), 10);
                pos = Position.LEFT;
            } else if (centerPercent > leftPercent && centerPercent > NONE) {
                Imgproc.rectangle(mat, CENTER_ROI, new Scalar(60, 255, 255), 10);
                pos = Position.CENTER;
            } else {
                // Imgproc.rectangle(mat, RIGHT_ROI, new Scalar(60, 255, 255), 10);
                pos = Position.RIGHT;
            }

            telemetry.addData("pos", pos);
            telemetry.update();

            leftBox.release();
            centerBox.release();
            // rightBox.release();

            return mat;
        }
    }
}

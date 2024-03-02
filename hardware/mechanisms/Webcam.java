package org.firstinspires.ftc.teamcode.hardware.mechanisms;

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
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.stuyfission.fissionlib.util.Mechanism;

import org.firstinspires.ftc.teamcode.opmode.auton.util.Color;

@Config
public class Webcam extends Mechanism {

    private Detector detector;
    private OpenCvCamera camera;

    private Color color;

    public static int NONE = 6;

    public static int LOW_H_R = 0;
    public static int LOW_H_B = 100;

    public static int HIGH_H_R = 10;
    public static int HIGH_H_B = 120;

    public Webcam(LinearOpMode opMode, Color color) {
        this.opMode = opMode;
        this.color = color;
    }

    @Override
    public void init(HardwareMap hwMap) {
        int cameraMonitorViewId = hwMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id",
                hwMap.appContext.getPackageName());

        WebcamName webcamName = hwMap.get(WebcamName.class, "webcam");
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
            //Mat rightBox = mat.submat(RIGHT_ROI);

            // how much in each region is white aka the color we filtered through the mask
            leftPercent = Core.sumElems(leftBox).val[0] / LEFT_ROI.area();
            centerPercent = Core.sumElems(centerBox).val[0] / CENTER_ROI.area();
            //rightPercent = Core.sumElems(rightBox).val[0] / RIGHT_ROI.area() / 255;

            telemetry.addData("LEFT", leftPercent);
            telemetry.addData("CENTER", centerPercent);
            //telemetry.addData("RIGHT", rightPercent);

            if (leftPercent > centerPercent && leftPercent > NONE) {
                Imgproc.rectangle(mat, LEFT_ROI, new Scalar(60, 255, 255), 10);
                pos = Position.LEFT;
            } else if (centerPercent > leftPercent && centerPercent > NONE) {
                Imgproc.rectangle(mat, CENTER_ROI, new Scalar(60, 255, 255), 10);
                pos = Position.CENTER;
            } else {
                //Imgproc.rectangle(mat, RIGHT_ROI, new Scalar(60, 255, 255), 10);
                pos = Position.RIGHT;
            }

            telemetry.addData("pos", pos);
            telemetry.update();

            leftBox.release();
            centerBox.release();
            //rightBox.release();

            return mat;
        }
    }
}

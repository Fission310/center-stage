package org.firstinspires.ftc.teamcode.hardware.mechanisms;

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
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.stuyfission.fissionlib.util.Mechanism;

public class Webcam extends Mechanism {

    private Detector detector;

    public static int LOW_H = 23;
    public static int LOW_S = 30;
    public static int LOW_V = 50;

    public static int HIGH_H = 32;
    public static int HIGH_S = 255;
    public static int HIGH_V = 255;

    public static int THRESHHOLD = 1;

    public Webcam(LinearOpMode opMode) {
        this.opMode = opMode;
    }

    @Override
    public void init(HardwareMap hwMap) {
        int cameraMonitorViewId = hwMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id",
                hwMap.appContext.getPackageName());

        WebcamName webcamName = hwMap.get(WebcamName.class, "webcam");
        OpenCvCamera camera = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMonitorViewId);

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

        detector = new Detector();
        camera.setPipeline(detector);
    }

    public enum Position {
        LEFT,
        RIGHT,
        CENTER
    }

    public Position getPosition() {
        return detector.getPosition();
    }

    private static class Detector extends OpenCvPipeline {

        private Position pos;

        private Mat mat = new Mat();

        private static final Scalar COLOR_FOUND = new Scalar(0, 255, 0);
        private static final Scalar lowHSV = new Scalar(LOW_H, LOW_S, LOW_V);
        private static final Scalar highHSV = new Scalar(HIGH_H, HIGH_S, HIGH_V);

        private static final int SCREEN_WIDTH = 1280;
        private static final int SCREEN_HEIGHT = 720;

        private static final Rect LEFT_ROI = new Rect(
                new Point(0, 0),
                new Point(SCREEN_WIDTH / 3.0, SCREEN_HEIGHT));

        private static final Rect CENTER_ROI = new Rect(
                new Point(SCREEN_WIDTH / 3.0, 0),
                new Point(SCREEN_WIDTH / 3.0 * 2, SCREEN_HEIGHT));

        private static final Rect RIGHT_ROI = new Rect(
                new Point(SCREEN_WIDTH / 3.0 * 2, 0),
                new Point(SCREEN_WIDTH, SCREEN_HEIGHT));

        @Override
        public Mat processFrame(Mat input) {
            Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);

            Core.inRange(mat, lowHSV, highHSV, mat);

            Mat left = mat.submat(LEFT_ROI);
            Mat middle = mat.submat(CENTER_ROI);
            Mat right = mat.submat(RIGHT_ROI);

            double leftValue = Core.sumElems(left).val[0] / LEFT_ROI.area() / 255;
            double middleValue = Core.sumElems(middle).val[0] / CENTER_ROI.area() / 255;
            double rightValue = Core.sumElems(right).val[0] / RIGHT_ROI.area() / 255;

            left.release();
            middle.release();
            right.release();

            Imgproc.cvtColor(mat, mat, Imgproc.COLOR_GRAY2RGB);
            if (leftValue > THRESHHOLD) {
                pos = Position.LEFT;
                Imgproc.rectangle(mat, LEFT_ROI, COLOR_FOUND);
            } else if (rightValue > THRESHHOLD) {
                pos = Position.RIGHT;
                Imgproc.rectangle(mat, RIGHT_ROI, COLOR_FOUND);
            } else if (middleValue > THRESHHOLD) {
                pos = Position.CENTER;
                Imgproc.rectangle(mat, CENTER_ROI, COLOR_FOUND);
            }

            return mat;
        }

        public Position getPosition() {
            return pos;
        }
    }

}

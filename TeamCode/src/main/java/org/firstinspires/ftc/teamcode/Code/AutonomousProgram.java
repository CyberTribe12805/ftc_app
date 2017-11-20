package org.firstinspires.ftc.teamcode.Code;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import java.util.Locale;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

@Autonomous(name = "AutonomousCode", group = "Pushbot")

public class AutonomousProgram extends LinearOpMode {

    private HardwarePushbot robot = new HardwarePushbot();
    private ElapsedTime runtime = new ElapsedTime();
    public static final String TAG = "Vuforia VuMark Sample";

    OpenGLMatrix lastLocation = null;
    String side;
    VuforiaLocalizer vuforia;


    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        robot.sensorColor = hardwareMap.get(ColorSensor.class, "sensor_color_distance");
        robot.sensorDistance = hardwareMap.get(DistanceSensor.class, "sensor_color_distance");
        float hsvValues[] = {0F, 0F, 0F};
        final float values[] = hsvValues;
        final double SCALE_FACTOR = 255;
        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AbNP8JL/////AAAAGWE3uZNhpkFMi10mTwbjVaA9K+f4HAVnVACKlJ2wA2bGJJHDbdksYtY1MDhny/i8YYSIY6CcTN7nnPwxWJBWe6mgbeW6YwtwK4S6P6PuXbff0J7TzT2IB9ERlFkGTLVNfxOgKyo3KmKLrPY8RkKaUsZsyNV1jo0f9YHUhPJOwt0wX4B0Xc3K1BJKmQju/eqFG0rQN4BxhUgZjX2sWbAFfLeSBHrxZtTkUCOT1/7zEnBVr7v7uFGrNSV4YA/DEY5uTiLzGVttzGVqTDGx+0dEUtSyPVwcDxKEbCz2ak8DmOR0xFuGTzAQ+YRqtPBmZy4a52aRj/1oIU8CSYTOn4jBjsEBSOR23EAwNp3BELDo16nU";

        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary


        telemetry.addData("Status", "Ready to run");
        telemetry.update();

        waitForStart();

        Color.RGBToHSV((int) (robot.sensorColor.red() * SCALE_FACTOR),
                (int) (robot.sensorColor.green() * SCALE_FACTOR),
                (int) (robot.sensorColor.blue() * SCALE_FACTOR),
                hsvValues);

        telemetry.addData("Distance (cm)",
                String.format(Locale.US, "%.02f", robot.sensorDistance.getDistance(DistanceUnit.CM)));
        telemetry.addData("Alpha", robot.sensorColor.alpha());
        telemetry.addData("Red  ", robot.sensorColor.red());
        telemetry.addData("Green", robot.sensorColor.green());
        telemetry.addData("Blue ", robot.sensorColor.blue());
        telemetry.addData("Hue", hsvValues[0]);

        relativeLayout.post(new Runnable() {
            public void run() {
                relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
            }
        });

        if (side == "Left")
        {
//            TODO minimize the if statements by creating classses to replace them
            CloseGrippie(1);
            StopGrippie();
            ColorArmOut(1);
            StopColorArm();

            if (robot.sensorColor.red() > 10 && robot.sensorDistance.getDistance(DistanceUnit.CM) < 5) {
                ColorArmIn(1);
                DrivePowerTime(1, -1, 2);
                StopDriving();
            } else {
                ColorArmIn(1);
                DrivePowerTime(-1, 1, 2);
                StopDriving();
            }

            LiftUp(1);
            StopLift();
            DrivePowerTime(1, -1, 1);
            TurnRight(1);
            LiftDown(1);
            OpenGrippie(1);
            StopGrippie();

        }
        else if (side == "Right")
        {

        }
        else if (side == "Center")
        {

        }
        else{
            StopDriving();
        }

        while (opModeIsActive()) {
            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
            if (vuMark != RelicRecoveryVuMark.UNKNOWN) {
                telemetry.addData("VuMark", "%s visible", vuMark);

                OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) relicTemplate.getListener()).getPose();
                telemetry.addData("Pose", format(pose));

                if (pose != null) {
                    VectorF trans = pose.getTranslation();
                    Orientation rot = Orientation.getOrientation(pose, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);

                    double tX = trans.get(0);
                    double tY = trans.get(1);
                    double tZ = trans.get(2);

                    double rX = rot.firstAngle;
                    double rY = rot.secondAngle;
                    double rZ = rot.thirdAngle;
                }
            } // TODO add actions after reading pictographs
            if (vuMark == RelicRecoveryVuMark.LEFT) { // Test to see if Image is the "LEFT" image and display value.
                telemetry.addData("VuMark is", "Left");
                telemetry.addData("X =", tX);
                telemetry.addData("Y =", tY);
                telemetry.addData("Z =", tZ);
                String side = "Left";
            } else if (vuMark == RelicRecoveryVuMark.RIGHT) { // Test to see if Image is the "RIGHT" image and display values.
                telemetry.addData("VuMark is", "Right");
                telemetry.addData("X =", tX);
                telemetry.addData("Y =", tY);
                telemetry.addData("Z =", tZ);
                String side = "Right";
            } else if (vuMark == RelicRecoveryVuMark.CENTER) { // Test to see if Image is the "CENTER" image and display values.
                telemetry.addData("VuMark is", "Center");
                telemetry.addData("X =", tX);
                telemetry.addData("Y =", tY);
                telemetry.addData("Z =", tZ);
                String side = "Center";
            } else {
                telemetry.addData("VuMark", "not visible");
            }

            telemetry.update();
        }

    }

    String format(OpenGLMatrix transformationMatrix) {
        return (transformationMatrix != null) ? transformationMatrix.formatAsTransform() : "null";
    }

    private void DrivePowerTime(int lPower, int rPower, double driveTime) {
        robot.motor_bLeft.setPower(lPower);
        robot.motor_bRight.setPower(rPower);
        robot.motor_fLeft.setPower(lPower);
        robot.motor_fRight.setPower(rPower);
        smartSleep(driveTime);
    }

    private void TurnRight(double turnRightTime) {
        robot.motor_bLeft.setPower(-1);
        robot.motor_bRight.setPower(1);
        robot.motor_fLeft.setPower(-1);
        robot.motor_fRight.setPower(1);
        smartSleep(turnRightTime);
    }

    public void TurnLeft(long turnLeftTime) {
        robot.motor_bLeft.setPower(1);
        robot.motor_bRight.setPower(-1);
        robot.motor_fLeft.setPower(1);
        robot.motor_fRight.setPower(-1);
        smartSleep(turnLeftTime);
    }

    private void CloseGrippie(double closeGrippieTime) {
        robot.grippie.setPower(1);
        smartSleep(closeGrippieTime);
    }

    private void OpenGrippie(double openGrippietime) {
        robot.grippie.setPower(-1);
        smartSleep(openGrippietime);
    }

    public void ColorArmOut(double armOutTime) {
        robot.color_arm.setPosition(0);
        smartSleep(armOutTime);
    }

    public void ColorArmIn(double armInTime) {
        robot.color_arm.setPosition(1.0);
        smartSleep(armInTime);
    }

    private void LiftUp(double liftUpTime) {
        robot.right_arm.setPosition(0);
        robot.left_arm.setPosition(1.0);
        smartSleep(liftUpTime);
    }

    private void LiftDown(double liftDownTime) {
        robot.right_arm.setPosition(1.0);
        robot.left_arm.setPosition(0);
        smartSleep(liftDownTime);
    }

    public void StopColorArm() {
        robot.color_arm.setPosition(0);
        smartSleep(.1);
    }

    private void StopLift() {
        robot.right_arm.setPosition(.5);
        robot.left_arm.setPosition(.5);
        smartSleep(.1);
    }

    public void StopDriving() {
        robot.motor_bLeft.setPower(0);
        robot.motor_bRight.setPower(0);
        robot.motor_fLeft.setPower(0);
        robot.motor_fRight.setPower(0);
        smartSleep(1);
    }

    private void StopGrippie() {
        robot.grippie.setPower(0);
        smartSleep(1);
    }

    private void smartSleep(double runTime) {
        while (opModeIsActive() && (runtime.seconds() < runTime)) {
   int true = 1;
        }
    }

    }
package org.firstinspires.ftc.teamcode.Code;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import java.util.Locale;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
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


  telemetry.addData("Status", "Ready to run"); //
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


  CloseGrippie(1);
  StopGrippie();
  LiftUp(1);
  StopLift();
  DrivePowerTime(1, 1, 1);
  TurnRight(1);
  LiftDown(1);
  OpenGrippie(1);
  StopGrippie();

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

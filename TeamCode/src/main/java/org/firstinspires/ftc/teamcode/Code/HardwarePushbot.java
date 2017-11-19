package org.firstinspires.ftc.teamcode.Code;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.Locale;

public class HardwarePushbot
{
    public DcMotor  motor_bLeft   = null;
    public DcMotor  motor_bRight  = null;
    public DcMotor  motor_fLeft   = null;
    public DcMotor  motor_fRight  = null;
    public Servo  left_arm     = null;
    public Servo  right_arm     = null;
    public Servo    color_arm    = null;
    public DcMotor  grippie   = null;
    ColorSensor sensorColor;
    DistanceSensor sensorDistance;
    

    public static final double MID_SERVO       =  0.5 ;
    public static final double ARM_UP_POWER    =  0.45 ;
    public static final double ARM_DOWN_POWER  = -0.45 ;

    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public HardwarePushbot(){

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        motor_bLeft  = hwMap.get(DcMotor.class, "motor1");
        motor_bRight = hwMap.get(DcMotor.class, "motor2");
        motor_fLeft  = hwMap.get(DcMotor.class, "motor3");
        motor_fRight = hwMap.get(DcMotor.class, "motor4");
        grippie    = hwMap.get(DcMotor.class, "grippie");
        motor_bLeft.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        motor_bRight.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors
        motor_fLeft.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        motor_fRight.setDirection(DcMotor.Direction.REVERSE);// Set to FORWARD if using AndyMark motors

        // Set all motors to zero power
        motor_bLeft.setPower(0);
        motor_bRight.setPower(0);
        motor_fLeft.setPower(0);
        motor_fRight.setPower(0);
        grippie.setPower(0);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        motor_bLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor_bRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor_fLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor_fRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        grippie.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        
        // Define and initialize ALL installed servos.
        left_arm  = hwMap.get(Servo.class, "left_arm");
        right_arm  = hwMap.get(Servo.class, "right_arm");
        color_arm  = hwMap.get(Servo.class, "color_arm");
        color_arm.setPosition(MID_SERVO);
        right_arm.setPosition(MID_SERVO);
        left_arm.setPosition(MID_SERVO);

    }
 }


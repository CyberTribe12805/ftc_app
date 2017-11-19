package org.firstinspires.ftc.teamcode.Code;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp

public class DriverControl extends LinearOpMode {
    private DcMotor motor_bLeft;
    private DcMotor motor_bRight;
    private DcMotor motor_fLeft;
    private DcMotor motor_fRight;
    private DcMotor grippie;
    private Servo left_arm;
    private Servo right_arm;
    private Servo color_arm;

    @Override
    public void runOpMode() {
        motor_bLeft = hardwareMap.get(DcMotor.class, "motor1");
        motor_bRight = hardwareMap.get(DcMotor.class, "motor2");
        motor_fLeft = hardwareMap.get(DcMotor.class, "motor3");
        motor_fRight = hardwareMap.get(DcMotor.class, "motor4");
        grippie = hardwareMap.get(DcMotor.class, "grippie");
        left_arm = hardwareMap.get(Servo.class, "left_arm");
        right_arm = hardwareMap.get(Servo.class, "right_arm");
        color_arm = hardwareMap.get(Servo.class, "color_arm");

        telemetry.addData("Status", "Initialized. HELLO");
        telemetry.update();
        waitForStart();

        double LtgtPower;
        double RtgtPower;
        double rightTrigger;
        double leftTrigger;
        
        while (opModeIsActive()) {
            
            leftTrigger = this.gamepad1.left_trigger;
            rightTrigger = this.gamepad1.right_trigger;
            
            if(gamepad1.dpad_left) {
                    color_arm.setPosition(1);
             } else if (gamepad1.dpad_down || gamepad1.dpad_up) {
                    color_arm.setPosition(0.5);
              } else if (gamepad1.dpad_right) {
                  color_arm.setPosition(0);
    }
            
            if(gamepad1.y) {
                    right_arm.setPosition(1);
                    left_arm.setPosition(0);
             } else if (gamepad1.x || gamepad1.b) {
                    right_arm.setPosition(0.5);
                    left_arm.setPosition(0.5);
              } else if (gamepad1.a) {
                  right_arm.setPosition(0);
                  left_arm.setPosition(1);
    }
    
            if(rightTrigger > 0) {
                    grippie.setPower(1);
            } else if (leftTrigger > 0) {
                    grippie.setPower(-1);
            } else {
                grippie.setPower(0);
            }
            
            LtgtPower = -this.gamepad1.left_stick_y;
            RtgtPower = this.gamepad1.right_stick_y;
            motor_bLeft.setPower(LtgtPower);
            motor_bRight.setPower(RtgtPower);
            motor_fLeft.setPower(LtgtPower);
            motor_fRight.setPower(RtgtPower);
            telemetry.addData("Motor 1 Power", motor_bLeft.getPower());
            telemetry.addData("Motor 2 Power", motor_bRight.getPower());
            telemetry.addData("Motor 3 Power", motor_fLeft.getPower());
            telemetry.addData("Motor 4 Power", motor_fRight.getPower());
            telemetry.addData("Grippie Power", grippie.getPower());
            telemetry.addData("Status", "Running");
            telemetry.update();
        }
    }
}

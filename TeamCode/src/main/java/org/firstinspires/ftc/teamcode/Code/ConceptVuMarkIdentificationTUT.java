package org.firstinspires.ftc.teamcode.Code;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

@Autonomous(name="Concept: VuMark Id", group ="Concept")

public class ConceptVuMarkIdentificationTUT extends LinearOpMode {

    public static final String TAG = "Vuforia VuMark Sample";

    OpenGLMatrix lastLocation = null;

    VuforiaLocalizer vuforia;

    @Override public void runOpMode() {

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AbNP8JL/////AAAAGWE3uZNhpkFMi10mTwbjVaA9K+f4HAVnVACKlJ2wA2bGJJHDbdksYtY1MDhny/i8YYSIY6CcTN7nnPwxWJBWe6mgbeW6YwtwK4S6P6PuXbff0J7TzT2IB9ERlFkGTLVNfxOgKyo3KmKLrPY8RkKaUsZsyNV1jo0f9YHUhPJOwt0wX4B0Xc3K1BJKmQju/eqFG0rQN4BxhUgZjX2sWbAFfLeSBHrxZtTkUCOT1/7zEnBVr7v7uFGrNSV4YA/DEY5uTiLzGVttzGVqTDGx+0dEUtSyPVwcDxKEbCz2ak8DmOR0xFuGTzAQ+YRqtPBmZy4a52aRj/1oIU8CSYTOn4jBjsEBSOR23EAwNp3BELDo16nU";
        
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary

        telemetry.addData(">", "Press Play to start");
        telemetry.update();
        waitForStart();

        relicTrackables.activate();

        while (opModeIsActive()) {
            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
            if (vuMark != RelicRecoveryVuMark.UNKNOWN) {
                telemetry.addData("VuMark", "%s visible", vuMark);
                
                OpenGLMatrix pose = ((VuforiaTrackableDefaultListener)relicTemplate.getListener()).getPose();
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
            if (vuMark == RelicRecoveryVuMark.LEFT)
            { // Test to see if Image is the "LEFT" image and display value.
                telemetry.addData("VuMark is", "Left");
                telemetry.addData("X =", tX);
                telemetry.addData("Y =", tY);
                telemetry.addData("Z =", tZ);
            } else if (vuMark == RelicRecoveryVuMark.RIGHT)
            { // Test to see if Image is the "RIGHT" image and display values.
                telemetry.addData("VuMark is", "Right");
                telemetry.addData("X =", tX);
                telemetry.addData("Y =", tY);
                telemetry.addData("Z =", tZ);
            } else if (vuMark == RelicRecoveryVuMark.CENTER)
            { // Test to see if Image is the "CENTER" image and display values.
                telemetry.addData("VuMark is", "Center");
                telemetry.addData("X =", tX);
                telemetry.addData("Y =", tY);
                telemetry.addData("Z =", tZ);

            }
            else {
                telemetry.addData("VuMark", "not visible");
            }
            
            telemetry.update();
        }
    }

    String format(OpenGLMatrix transformationMatrix) {
        return (transformationMatrix != null) ? transformationMatrix.formatAsTransform() : "null";
    }
}

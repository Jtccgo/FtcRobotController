package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "RobotCentric", group = "drive")
public class RobotCentric extends LinearOpMode {
    private DcMotorEx flm;
    private DcMotorEx frm;
    private DcMotorEx blm;
    private DcMotorEx brm;

    private Servo ex;

    @Override
    public void runOpMode() {

        flm = hardwareMap.get(DcMotorEx.class, "fl");
        frm = hardwareMap.get(DcMotorEx.class, "fr");
        frm.setDirection(DcMotorEx.Direction.REVERSE);
        blm = hardwareMap.get(DcMotorEx.class, "bl");
        brm = hardwareMap.get(DcMotorEx.class, "br");

        flm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        blm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        brm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // 3. Wait for the DS play button to be pressed
        waitForStart();

        // 4. Active Driver Control Loop
        while (opModeIsActive()) {
            double straight = -gamepad1.left_stick_y;//inverse because 1.0 is down
            double strafe = gamepad1.left_stick_x;
            double rotate = gamepad1.right_stick_x;

            double flmPower = straight + strafe + rotate;
            double frmPower = straight - strafe - rotate;
            double blmPower = straight - strafe + rotate;
            double brmPower = straight + strafe - rotate;

            double maxSpeed = 1.0;
            maxSpeed = Math.max(maxSpeed, Math.abs(flmPower));
            maxSpeed = Math.max(maxSpeed, Math.abs(frmPower));
            maxSpeed = Math.max(maxSpeed, Math.abs(blmPower));
            maxSpeed = Math.max(maxSpeed, Math.abs(brmPower));

            flmPower /= maxSpeed;
            frmPower /= maxSpeed;
            blmPower /= maxSpeed;
            brmPower /= maxSpeed;

            flm.setPower(flmPower);
            frm.setPower(frmPower);
            blm.setPower(blmPower);
            brm.setPower(brmPower);

            telemetry.addData("Status", "Running");
            telemetry.update();
        }
    }
}

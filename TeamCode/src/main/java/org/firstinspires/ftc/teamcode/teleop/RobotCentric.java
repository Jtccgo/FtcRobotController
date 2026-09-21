package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "RobotCentric", group = "drive")
public class RobotCentric extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private double savedTime = 0;
    private DcMotorEx flm;
    private DcMotorEx frm;
    private DcMotorEx blm;
    private DcMotorEx brm;
    private DcMotorEx chamberMotor;//controls the cylinders that swivel in the center of the robot
    private DcMotorEx intakeMotor;//connects to the shaft with several compliant wheels
    private DcMotorEx pushServo;//positioned over the swivel cylinders to push the ball forwards into the flywheels
    private DcMotorEx leftFlywheel;
    private DcMotorEx rightFlywheel;

    //relative to the robot rotated with wheels facing forwards
    @Override
    public void runOpMode() {
        //wheel motors
        flm = hardwareMap.get(DcMotorEx.class, "fl");
        frm = hardwareMap.get(DcMotorEx.class, "fr");
        frm.setDirection(DcMotorEx.Direction.REVERSE);
        blm = hardwareMap.get(DcMotorEx.class, "bl");
        brm = hardwareMap.get(DcMotorEx.class, "br");
        //intake & outtake
        intakeMotor = hardwareMap.get(DcMotorEx.class, "intake");
        chamberMotor = hardwareMap.get(DcMotorEx.class, "chamber");
        pushServo = hardwareMap.get(DcMotorEx.class, "push");
        leftFlywheel = hardwareMap.get(DcMotorEx.class, "lfly");
        rightFlywheel= hardwareMap.get(DcMotorEx.class, "rfly");
        //motor modes
        rightFlywheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftFlywheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        pushServo.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        chamberMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
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
            runtime.startTime();
            //default controls(like what i choose)
            //driver controls
            intakeControls();
            outtakeControls();
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
    public void intakeControls() {
        leftFlywheel.setPower(gamepad1.left_trigger);
        rightFlywheel.setPower(gamepad1.right_trigger);
        if (gamepad1.right_bumper) {

            pushServo.setTargetPosition(1);
        }
        if (runtime.seconds() > 1.0) {
        }
        pushServo.setTargetPosition(0);
    }
    public void outtakeControls() {


    }
    private double timeSinceLastSave() {
        return runtime.seconds() - savedTime;
    }
    private void saveTime() {
        savedTime = runtime.seconds();
    }
    private void resetTime() {
        runtime.reset();
        savedTime = 0;
    }
}

package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "FieldCentric", group = "Drive")
public class FieldCentric extends LinearOpMode {
    private DcMotorEx flm;
    private DcMotorEx frm;
    private DcMotorEx blm;
    private DcMotorEx brm;

    @Override
    public void runOpMode() {

        flm = hardwareMap.get(DcMotorEx.class, "fl");
        frm = hardwareMap.get(DcMotorEx.class, "fr");
        frm.setDirection(DcMotorEx.Direction.REVERSE);
        blm = hardwareMap.get(DcMotorEx.class, "bl");
        brm = hardwareMap.get(DcMotorEx.class, "br");
        brm.setDirection(DcMotorSimple.Direction.REVERSE);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // 3. Wait for the DS play button to be pressed
        waitForStart();

        // 4. Active Driver Control Loop
        while (opModeIsActive()) {
            flm.setPower(gamepad1.x ? 1:0);
            frm.setPower(gamepad1.y ? 1:0);
            blm.setPower(gamepad1.a ? 1:0);
            brm.setPower(gamepad1.b ? 1:0);
            telemetry.addData("Status", "Running");
            telemetry.update();
        }
    }
}

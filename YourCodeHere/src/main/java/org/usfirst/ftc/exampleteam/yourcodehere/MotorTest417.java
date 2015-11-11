package org.usfirst.ftc.exampleteam.yourcodehere;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

import org.swerverobotics.library.SynchronousOpMode;
import org.swerverobotics.library.interfaces.TeleOp;

/**
 * An example of a synchronous opmode that's a little more complex than
 * SynchTeleOp, in that it supports multiple different drive modes that are switched
 * between using the A, B, and Y gamepad buttons.
 *
 * TODO: Perhaps consolidate the two examples 
 */
@TeleOp(name="417 Motor Test", group="Swerve Examples")
public class MotorTest417 extends SynchronousOpMode
{
    // All hardware variables can only be initialized inside the main() function,
    // not here at their member variable declarations.
    DcMotor motorFrontLeft  = null;
    DcMotor motorFrontRight = null;
    DcMotor motorBackLeft  = null;
    DcMotor motorBackRight = null;
    DcMotor motorCollector = null;
    DcMotor motorHook = null;



    @Override protected void main() throws InterruptedException {
        // Initialize our hardware variables
        this.motorFrontLeft = this.hardwareMap.dcMotor.get("motorFrontLeft");
        this.motorFrontRight = this.hardwareMap.dcMotor.get("motorFrontRight");
        this.motorBackLeft = this.hardwareMap.dcMotor.get("motorBackLeft");
        this.motorBackRight = this.hardwareMap.dcMotor.get("motorBackRight");
        this.motorCollector = this.hardwareMap.dcMotor.get("motorCollector");
        this.motorHook = this.hardwareMap.dcMotor.get("motorHook");

        // Configure the knobs of the hardware according to how you've wired your
        // robot. Here, we assume that there are no encoders connected to the motors,
        // so we inform the motor objects of that fact.
        this.motorFrontLeft.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        this.motorFrontRight.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        this.motorBackLeft.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        this.motorBackRight.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);

        // One of the two motors (here, the left) should be set to reversed direction
        // so that it can take the same power level values as the other motor.
        this.motorFrontLeft.setDirection(DcMotor.Direction.REVERSE);
        this.motorBackLeft.setDirection(DcMotor.Direction.REVERSE);

        // Wait until the game begins
        this.waitForStart();

        // Enter a loop processing all the input we receive
        while (this.opModeIsActive()) {
            if (this.updateGamepads()) {
                if (this.gamepad1.a) {
                    this.motorFrontLeft.setPower(1);
                }

                if (this.gamepad1.b) {
                    this.motorFrontRight.setPower(1);
                }

                if (this.gamepad1.y) {
                    this.motorBackLeft.setPower(1);
                }

                if (this.gamepad1.x) {
                    this.motorBackRight.setPower(1);
                }

                if (this.gamepad1.dpad_left) {
                    this.motorCollector.setPower(1);
                }

                if (this.gamepad1.dpad_right) {
                    this.motorHook.setPower(1);
                }

                if (this.gamepad1.left_bumper) {
                    this.motorFrontLeft.setPower(0);
                    this.motorFrontRight.setPower(0);
                    this.motorBackLeft.setPower(0);
                    this.motorBackRight.setPower(0);
                    this.motorCollector.setPower(0);
                    this.motorHook.setPower(0);
                }
            }

            // Let the rest of the system run until there's a stimulus from the robot controller runtime.
            this.idle();
        }
    }
}

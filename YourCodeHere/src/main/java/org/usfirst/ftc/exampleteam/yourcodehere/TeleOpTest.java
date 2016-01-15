package org.usfirst.ftc.exampleteam.yourcodehere;

import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

import org.swerverobotics.library.interfaces.TeleOp;

/**
 * 417 teleop
 */
@TeleOp(name="TeleOpTest", group="Swerve Examples")
public class TeleOpTest extends MasterOpmode417
{

    boolean controllerSwitch = false;


    @Override protected void main() throws InterruptedException
    {
        // Initialize our hardware variables
        initializeHardware();


        // Configure the knobs of the hardware according to how you've wired your
        // robot. Here, we assume that there are no encoders connected to the motors,
        // so we inform the motor objects of that fact.
        setRunModesTeleop();

        // Wait until the game begins
        this.waitForStart();
        // Enter a loop processing all the input we receive
        while (this.opModeIsActive())
        {
            if (this.updateGamepads()) {
                float leftPower = gamepad1.left_stick_y;
                float rightPower = gamepad1.right_stick_y;
                float powerLeft = Range.clip(leftPower, -1f, 1f);
                float powerRight = Range.clip(rightPower, -1f, 1f);
                // Tell the motors
                this.motorFrontLeft.setPower(powerLeft );
                this.motorFrontRight.setPower(powerRight );
                this.motorBackLeft.setPower(powerLeft *.9);
                this.motorBackRight.setPower(powerRight * .9);
            }
            // Emit telemetry
            this.telemetry.addData("Drive mode", driveModeLabel[driveMode.ordinal()]);
            telemetry.addData("leftstick", this.gamepad1.left_stick_y);
            telemetry.addData("rightstick", this.gamepad1.right_stick_y);
            telemetry.addData("frontmultiply", this.frontWheelMultiply);
            telemetry.addData("backmultiply", this.backWheelMultiply);
            //telemetry.addData("controllerSwitch", controllerSwitch);
            this.telemetry.update();

            // Let the rest of the system run until there's a stimulus from the robot controller runtime.
            this.idle();
        }
    }
}

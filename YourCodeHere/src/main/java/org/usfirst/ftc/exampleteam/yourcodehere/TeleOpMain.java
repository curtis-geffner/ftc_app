package org.usfirst.ftc.exampleteam.yourcodehere;

import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.Range;

/**
 * 417 teleop
 */
@org.swerverobotics.library.interfaces.TeleOp(name="TeleOpMain", group="417")
public class TeleOpMain extends MasterTeleOp
{

    boolean controllerSwitch = false;

    @Override protected void main() throws InterruptedException
    {
        initialize();

        // Wait until the game begins
        this.waitForStart();

        // Enter a loop processing all the input we receive
        while (this.opModeIsActive())
        {
            telemetry.addData("motorDeliverySlider_encoder",this.motorDeliverySlider.getCurrentPosition());
            if (this.updateGamepads())
            {
                //Controller 2

                //delivery mech
                if(this.gamepad2.x || this.gamepad2.dpad_left)
                {
                    deliveryToggler.moveForward();
                }
                else if(this.gamepad2.y || this.gamepad2.dpad_right)
                {
                    deliveryToggler.moveReverse();
                }
                else
                {
                    deliveryToggler.stop();
                }

                //left climber
                if(this.gamepad2.left_trigger > 0)
                {
                    climberLeftToggler.moveReverse();
                }
                else if(this.gamepad2.left_bumper)
                {
                    climberLeftToggler.moveForward();
                }
                else
                {
                    climberLeftToggler.stop();
                }

                //right climber
                if(this.gamepad2.right_trigger > 0)
                {
                    climberRightToggler.moveForward();
                }
                else if(this.gamepad2.right_bumper)
                {
                    climberRightToggler.moveReverse();
                }
                else
                {
                    climberRightToggler.stop();
                }


                if(this.gamepad1.start || this.gamepad2.start)
                {
                    this.motorDeliverySlider.setMode(DcMotorController.RunMode.RESET_ENCODERS);
                    this.motorDeliverySlider.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);

                }
                

                // Tell the motors
                driveLeft(Range.clip(gamepad1.left_stick_y, -1f, 1f));
                driveRight(Range.clip(gamepad1.right_stick_y, -1f, 1f));
                collectorToggler.setSpeed(gamepad2.right_stick_y);
                slideToggler.setSpeed(gamepad2.left_stick_y);

            }

            slideToggler.checkPositionInRange();


            // Emit telemetry
            this.telemetry.addData("Drive mode", driveModeLabel[driveMode.ordinal()]);
            telemetry.addData("leftstick", this.gamepad1.left_stick_y);
            telemetry.addData("rightstick", this.gamepad1.right_stick_y);
            this.telemetry.update();

            this.idle(); // Let the rest of the system run until there's a stimulus from the robot controller runtime.
        }
    }


}

package org.usfirst.ftc.exampleteam.yourcodehere;

import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.Range;

import org.swerverobotics.library.interfaces.TeleOp;

/**
 * 417 teleop
 */
@org.swerverobotics.library.interfaces.TeleOp(name="TeleOpMain", group="417")
public class TeleOpMain extends MasterTeleOp
{

    boolean controllerSwitch = false;
    boolean motorCollectorInMotion = false;
    boolean servoDeliveryInMotion = false;
    boolean servoClimberLeftInMotion = false;
    boolean servoClimberRightInMotion = false;
    boolean motorLiftInMotion = false;
    boolean motorHookInMotion = false;

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
                //control collector motor
                if (this.gamepad1.left_bumper || this.gamepad2.left_bumper)
                {
                    this.motorCollector.setPower(FULL_SPEED_REVERSE);
                    motorCollectorInMotion = true;
                }
                else if(this.gamepad1.right_bumper || this.gamepad2.right_bumper)
                {
                    this.motorCollector.setPower(FULL_SPEED);
                    motorCollectorInMotion = true;
                }
                else if (motorCollectorInMotion)
                {
                    this.motorCollector.setPower(STOPPED);
                    motorCollectorInMotion = false;
                }
                //control delivery mechanism
                if (this.gamepad1.dpad_left || this.gamepad2.dpad_left) {
                    //servoDeliveryPosition += 0.1;
                    servoDeliveryPosition = 0.55;
                    servoDelivery.setPosition(servoDeliveryPosition);
                    servoDeliveryInMotion = true;
                } else if (this.gamepad1.dpad_right || this.gamepad2.dpad_right) {

                    //servoDeliveryPosition -= 0.1;
                    servoDeliveryPosition = 0.45;
                    servoDelivery.setPosition(servoDeliveryPosition);
                    servoDeliveryInMotion = true;
                }
                else if (servoDeliveryInMotion)
                {
                    servoDelivery.setPosition(0.5);
                    servoDeliveryInMotion = false;
                }

                if(this.gamepad1.dpad_up || this.gamepad2.dpad_up )
                {
                    if (this.motorDeliverySlider.getCurrentPosition() < 13000) {
                        this.motorDeliverySlider.setPower(FULL_SPEED);
                        motorSliderState = enumMotorSliderState.forwards;
                    }
                }
                else if(this.gamepad1.dpad_down || this.gamepad2.dpad_down)
                {
                    this.motorDeliverySlider.setPower(FULL_SPEED_REVERSE);
                    motorSliderState = enumMotorSliderState.reverse;
                }
                else if (motorSliderState != enumMotorSliderState.stopped)
                {
                    this.motorDeliverySlider.setPower(STOPPED);
                    motorSliderState = enumMotorSliderState.stopped;
                }


                if(this.gamepad1.left_trigger >0.1 || this.gamepad2.left_trigger >0.1)
                {
                    controllerSwitch = true;
                }
                else
                {
                    controllerSwitch = false;
                }
                if(!controllerSwitch)
                {

                    if (servoClimberLeftInMotion)
                    {
                        this.servoClimberLeft.setPosition(.5);
                        servoClimberLeftInMotion = false;
                    }
                    if (servoClimberRightInMotion)
                    {
                        this.servoClimberRight.setPosition(.5);
                        servoClimberRightInMotion = false;
                    }

                    if(this.gamepad1.y || this.gamepad2.y)
                    {
                        this.motorLift.setPower(FULL_SPEED);
                        motorLiftInMotion = true;
                    }
                    else if(this.gamepad1.a || this.gamepad2.a)
                    {
                        this.motorLift.setPower(FULL_SPEED_REVERSE);
                        motorLiftInMotion = true;
                    }
                    else if (motorLiftInMotion)
                    {
                        this.motorLift.setPower(STOPPED);
                        motorLiftInMotion = false;
                    }

                    if(this.gamepad1.x || this.gamepad2.x)
                    {
                        this.motorHook.setPower(FULL_SPEED);
                        motorHookInMotion = true;
                    }
                    else if(this.gamepad1.b || this.gamepad2.b)
                    {
                        this.motorHook.setPower(FULL_SPEED_REVERSE);
                        motorHookInMotion = true;
                    }
                    else if (motorHookInMotion)
                    {
                        this.motorHook.setPower(STOPPED);
                        motorHookInMotion = false;
                    }
                }
                else//-----switch the controller to control zip line hitters-------------------------
                {
                    if(this.gamepad1.y || this.gamepad2.y)
                    {
                        this.servoClimberLeft.setPosition(1);
                        servoClimberLeftInMotion = true;
                    }
                    else if(this.gamepad1.b || this.gamepad2.b)
                    {
                        this.servoClimberLeft.setPosition(0);
                        servoClimberLeftInMotion = true;
                    }
                    else if (servoClimberLeftInMotion)
                    {
                        this.servoClimberLeft.setPosition(.5);
                        servoClimberLeftInMotion = false;
                    }

                    if(this.gamepad1.x || this.gamepad2.x)
                    {
                        this.servoClimberRight.setPosition(1);
                        servoClimberRightInMotion = true;
                    }
                    else if(this.gamepad1.a || this.gamepad2.a)
                    {
                        this.servoClimberRight.setPosition(0);
                        servoClimberRightInMotion = true;
                    }
                    else if (servoClimberRightInMotion)
                    {
                        this.servoClimberRight.setPosition(.5);
                        servoClimberRightInMotion = false;
                    }
                }

                if(this.gamepad1.start || this.gamepad2.start)
                {
                    this.motorDeliverySlider.setMode(DcMotorController.RunMode.RESET_ENCODERS);
                    this.motorDeliverySlider.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);

                }

                float powerLeft = Range.clip(gamepad1.left_stick_y, -1f, 1f);
                float powerRight = Range.clip(gamepad1.right_stick_y, -1f, 1f);
                // Tell the motors
                this.motorFrontLeft.setPower(powerLeft );
                this.motorFrontRight.setPower(powerRight );
                this.motorBackLeft.setPower(powerLeft *.9);
                this.motorBackRight.setPower(powerRight * .9);

            }

            //prevent delivery slide from over-extending
            if ((motorSliderState == enumMotorSliderState.forwards) && (this.motorDeliverySlider.getCurrentPosition() >= 13000 ))
            {
                this.motorDeliverySlider.setPower(STOPPED);
                motorSliderState = enumMotorSliderState.stopped;

            }

            // Emit telemetry
            this.telemetry.addData("Drive mode", driveModeLabel[driveMode.ordinal()]);
            telemetry.addData("leftstick", this.gamepad1.left_stick_y);
            telemetry.addData("rightstick", this.gamepad1.right_stick_y);
            this.telemetry.update();

            this.idle(); // Let the rest of the system run until there's a stimulus from the robot controller runtime.
        }
    }


}

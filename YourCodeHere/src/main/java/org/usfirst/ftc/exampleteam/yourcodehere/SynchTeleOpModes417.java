package org.usfirst.ftc.exampleteam.yourcodehere;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.Range;

import org.swerverobotics.library.SynchronousOpMode;
import org.swerverobotics.library.interfaces.TeleOp;

/**
 * 417 teleop
 */
@TeleOp(name="417 TeleOp Demo", group="Swerve Examples")
public class SynchTeleOpModes417 extends SynchronousOpMode
{
    enum DriveModeEnum { TANK, ARCADE, LEFT_STICK,X4,X2,X3 };
    String[]  driveModeLabel = new String[] { "tank", "arcade", "left stick","X1.5","X2","X3"};

    // All hardware variables can only be initialized inside the main() function,
    // not here at their member variable declarations.
    DcMotor motorFrontLeft  = null;
    DcMotor motorFrontRight = null;
    DcMotor motorBackLeft  = null;
    DcMotor motorBackRight = null;
    DcMotor motorCollector = null;
    DcMotor motorDeliverySlider = null;
    DcMotor motorHook = null;
    Servo   servoDelivery = null;
    Servo   servoCollectorLift = null;
    TouchSensor endStop = null;



    DriveModeEnum driveMode = DriveModeEnum.TANK;

    //motor speed constants
    final double FULL_SPEED = 1.0;
    final double STOPPED = 0.0;
    final double FULL_SPEED_REVERSE = -1.0;
    double frontWheelMultiply = 1.0;
    double backWheelMultiply = 1.0;
    //servo collector value

    double servoDeliveryPosition = 0;

    @Override protected void main() throws InterruptedException
    {
        // Initialize our hardware variables
        this.motorFrontLeft = this.hardwareMap.dcMotor.get("motorFrontLeft");
        this.motorFrontRight = this.hardwareMap.dcMotor.get("motorFrontRight");
        this.motorBackLeft = this.hardwareMap.dcMotor.get("motorBackLeft");
        this.motorBackRight = this.hardwareMap.dcMotor.get("motorBackRight");
        this.motorCollector = this.hardwareMap.dcMotor.get("motorCollector");
        this.motorDeliverySlider = this.hardwareMap.dcMotor.get("motorDeliverySlider");
        this.motorHook = this.hardwareMap.dcMotor.get("motorHook");
        this.endStop = this.hardwareMap.touchSensor.get("endStop");

        // Configure the knobs of the hardware according to how you've wired your
        // robot. Here, we assume that there are no encoders connected to the motors,
        // so we inform the motor objects of that fact.
        this.motorFrontLeft.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        this.motorFrontRight.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        this.motorBackLeft.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        this.motorBackRight.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        this.motorDeliverySlider.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        this.motorCollector.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        this.motorHook.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);

        // Two of the four motors (here, the left) should be set to reversed direction
        // so that it can take the same power level values as the other motor.
        this.motorFrontLeft.setDirection(DcMotor.Direction.REVERSE);
        this.motorBackLeft.setDirection(DcMotor.Direction.REVERSE);

        this.servoCollectorLift = this.hardwareMap.servo.get("servoCollectorLift");
        this.servoDelivery = this.hardwareMap.servo.get("servoDelivery");

        // Wait until the game begins
        this.waitForStart();



        // Enter a loop processing all the input we receive
        while (this.opModeIsActive())
        {
            boolean touchSensorIsActive = this.endStop.isPressed();

            if (this.updateGamepads()) {

                touchSensorIsActive = this.endStop.isPressed();

                //Below is the old drivemode switch
               /* if (this.gamepad1.dpad_up) {
                    this.driveMode = DriveModeEnum.TANK;
                } else if (this.gamepad1.dpad_down) {
                    this.driveMode = DriveModeEnum.ARCADE;
                } else if (this.gamepad1.dpad_left) {
                    this.driveMode = DriveModeEnum.X4;
                } else if (this.gamepad1.dpad_right) {
                    this.driveMode = DriveModeEnum.X3;
                }
                */

                //control collector motor
                if (this.gamepad1.left_bumper || this.gamepad2.left_bumper)
                {
                    this.motorCollector.setPower(FULL_SPEED_REVERSE);
                }
                else if(this.gamepad1.right_bumper || this.gamepad2.right_bumper)
                {
                    this.motorCollector.setPower(FULL_SPEED);
                }
                else
                {
                    this.motorCollector.setPower(STOPPED);
                }
                //control delivery mechanism
                if (this.gamepad1.dpad_left || this.gamepad2.dpad_left) {
                    //servoDeliveryPosition += 0.1;
                    servoDeliveryPosition = 0.55;
                    servoDelivery.setPosition(servoDeliveryPosition);
                } else if (this.gamepad1.dpad_right || this.gamepad2.dpad_right) {

                    //servoDeliveryPosition -= 0.1;
                    servoDeliveryPosition = 0.45;
                    servoDelivery.setPosition(servoDeliveryPosition);
                }
                else
                {
                    servoDelivery.setPosition(0.5);
                }

                //servoDelivery.setPosition(servoDeliveryPosition);

                if(this.gamepad1.x || this.gamepad2.x)
                {
                    servoCollectorLift.setPosition(1);
                }
                else if(this.gamepad1.b || this.gamepad2.b)
                {
                    servoCollectorLift.setPosition(0);
                }
                else
                {
                    servoCollectorLift.setPosition(.5);
                }

                if(this.gamepad1.left_trigger >0)
                {
                    frontWheelMultiply = 0.0;
                }
                else
                {
                    frontWheelMultiply = 1.0;
                }

                if(this.gamepad1.right_trigger >0)
                {
                    backWheelMultiply =0.0;
                }
                else
                {
                    backWheelMultiply = 1.0;
                }


                if(this.gamepad1.dpad_up || this.gamepad2.dpad_up )
                {
                    if (touchSensorIsActive)
                    {
                        this.motorDeliverySlider.setPower(FULL_SPEED);
                    }
                    else
                    {
                        this.motorDeliverySlider.setPower(STOPPED);
                    }
                }
                else if(this.gamepad1.dpad_down || this.gamepad2.dpad_down)
                {
                    this.motorDeliverySlider.setPower(FULL_SPEED_REVERSE);
                }
                else
                {
                    this.motorDeliverySlider.setPower(STOPPED);
                }

                if(this.gamepad1.y || this.gamepad2.y)
                {
                    this.motorHook.setPower(FULL_SPEED);
                }
                else if(this.gamepad1.a || this.gamepad2.a)
                {
                    this.motorHook.setPower(FULL_SPEED_REVERSE);
                }
                else
                {
                    this.motorHook.setPower(STOPPED);
                }

                //this.telemetry.log.add();
                this.doManualDrivingControl(this.gamepad1);

            }

            // Emit telemetry
            this.telemetry.addData("Drive mode", driveModeLabel[driveMode.ordinal()]);
            this.telemetry.update();

            // Let the rest of the system run until there's a stimulus from the robot controller runtime.
            this.idle();
        }
    }

    /**
     * Implement a simple four-motor driving logic using the left and right
     * joysticks on gamepad 1.
     */
    void doManualDrivingControl(Gamepad pad) throws InterruptedException
    {
        float powerLeft = 0;
        float powerRight = 0;

        switch (this.driveMode)
        {
            case TANK:
            {
                float leftPower = pad.left_stick_y;
                float rightPower = pad.right_stick_y;
                powerLeft = Range.clip(leftPower, -1f, 1f);
                powerRight = Range.clip(rightPower, -1f, 1f);
            }
            break;
            case X4:
            {   //this DriveMode is not currently used
                float leftPower = xformWithExponent(pad.left_stick_y, 4f);
                float rightPower = xformWithExponent(pad.right_stick_y, 4f);
                powerLeft = Range.clip(leftPower, -1f, 1f);
                powerRight = Range.clip(rightPower, -1f, 1f);;
            }
            break;
            case X2:
            {   //this DriveMode is not currently used
                float leftPower = xformWithExponent(pad.left_stick_y, 2f);
                float rightPower = xformWithExponent(pad.right_stick_y, 2f);
                powerLeft = Range.clip(leftPower, -1f, 1f);
                powerRight = Range.clip(rightPower, -1f, 1f);
            }
            break;
            case X3:
            {   //this DriveMode is not currently used
                float leftPower =  Math.signum(pad.left_stick_y) * xformWithExponent(pad.left_stick_y, 3f);
                float rightPower = Math.signum(pad.right_stick_y) *xformWithExponent(pad.right_stick_y, 3f);
                powerLeft = Range.clip(leftPower, -1f, 1f);
                powerRight = Range.clip(rightPower, -1f, 1f);
            }
            break;

            case ARCADE:
            case LEFT_STICK:
            {
                // Remember that the gamepad sticks range from -1 to +1, and that the motor
                // power levels range over the same amount
                float ctlPower    = pad.left_stick_y;
                float ctlSteering = this.driveMode== DriveModeEnum.ARCADE? pad.right_stick_x : pad.left_stick_x;

                // We're going to assume that the deadzone processing has been taken care of for us
                // already by the underlying system (that appears to be the intent). Were that not
                // the case, then we would here process ctlPower and ctlSteering to be exactly zero
                // within the deadzone.

                // Map the power and steering to have more oomph at low values (optional)
                ctlPower = this.xformDrivingPowerLevels(ctlPower);
                ctlSteering = -1 * this.xformDrivingPowerLevels(ctlSteering);

                // Dampen power to avoid clipping so we can still effectively steer even
                // under heavy throttle.
                //
                // We want
                //      -1 <= ctlPower - ctlSteering <= 1
                //      -1 <= ctlPower + ctlSteering <= 1
                // i.e
                //      ctlSteering -1 <= ctlPower <=  ctlSteering + 1
                //     -ctlSteering -1 <= ctlPower <= -ctlSteering + 1
                ctlPower = Range.clip(ctlPower, ctlSteering - 1, ctlSteering + 1);
                ctlPower = Range.clip(ctlPower, -ctlSteering - 1, -ctlSteering + 1);

                // Figure out how much power to send to each motor. Be sure
                // not to ask for too much, or the motor will throw an exception.
                powerLeft = Range.clip(ctlPower + ctlSteering, -1f, 1f);
                powerRight = Range.clip(ctlPower - ctlSteering, -1f, 1f);
            }
            break;

            // end switch
        }

        // Tell the motors
        this.motorFrontLeft.setPower(powerLeft * frontWheelMultiply);
        this.motorFrontRight.setPower(powerRight * frontWheelMultiply);
        this.motorBackLeft.setPower(powerLeft * backWheelMultiply);
        this.motorBackRight.setPower(powerRight * backWheelMultiply);
    }

    float xformDrivingPowerLevels(float level)
    // A useful thing to do in some robots is to map the power levels so that
    // low power levels have more power than they otherwise would. This sometimes
    // help give better driveability.
    {
        // We use a log function here as a simple way to transform the levels.
        // You might want to try something different: perhaps construct a
        // manually specified function using a table of values over which
        // you interpolate.
        float zeroToOne = Math.abs(level);
        float oneToTen  = zeroToOne * 9 + 1;
        return (float)(Math.log10(oneToTen) * Math.signum(level));
    }

    float xformWithExponent(float level , float exponent)
    {
        return (float)Math.pow(level, exponent) * Math.signum(level);
    }


}

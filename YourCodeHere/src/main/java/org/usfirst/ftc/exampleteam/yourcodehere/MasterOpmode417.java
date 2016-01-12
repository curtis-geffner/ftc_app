package org.usfirst.ftc.exampleteam.yourcodehere;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;

import org.swerverobotics.library.SynchronousOpMode;
import org.swerverobotics.library.interfaces.TeleOp;

/**
 * 417 master opmode
 */
public abstract class MasterOpmode417 extends SynchronousOpMode
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
    DcMotor motorLift = null;
    Servo   servoDelivery = null;
    Servo   servoCollectorLift = null;


    DriveModeEnum driveMode = DriveModeEnum.TANK;

    //motor speed constants
    final double FULL_SPEED = 1.0;
    final double STOPPED = 0.0;
    final double FULL_SPEED_REVERSE = -1.0;
    double frontWheelMultiply = 1.0;
    double backWheelMultiply = 1.0;
    //servo collector value

    double servoDeliveryPosition = 0;

    enum enumMotorSliderState
    {
        stopped,
        forwards,
        reverse
    }

    enumMotorSliderState motorSliderState = enumMotorSliderState.stopped;

    void initializeHardware()
    {
        // Initialize our hardware variables
        this.motorFrontLeft = this.hardwareMap.dcMotor.get("motorFrontLeft");
        this.motorFrontRight = this.hardwareMap.dcMotor.get("motorFrontRight");
        this.motorBackLeft = this.hardwareMap.dcMotor.get("motorBackLeft");
        this.motorBackRight = this.hardwareMap.dcMotor.get("motorBackRight");
        this.motorCollector = this.hardwareMap.dcMotor.get("motorCollector");
        this.motorDeliverySlider = this.hardwareMap.dcMotor.get("motorDeliverySlider");
        this.motorHook = this.hardwareMap.dcMotor.get("motorHook");
        this.motorLift = this.hardwareMap.dcMotor.get("motorLift");

        this.servoCollectorLift = this.hardwareMap.servo.get("servoCollectorLift");
        this.servoDelivery = this.hardwareMap.servo.get("servoDelivery");






    }

    void setRunModesTeleop()
    {
        // Configure the knobs of the hardware according to how you've wired your
        // robot. Here, we assume that there are no encoders connected to the motors,
        // so we inform the motor objects of that fact.
        this.motorFrontLeft.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        this.motorFrontRight.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        this.motorBackLeft.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        this.motorBackRight.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        this.motorDeliverySlider.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        this.motorCollector.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        this.motorHook.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        this.motorLift.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);


        this.motorDeliverySlider.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);

        // Two of the four motors (here, the left) should be set to reversed direction
        // so that it can take the same power level values as the other motor.
        this.motorFrontLeft.setDirection(DcMotor.Direction.REVERSE);
        this.motorBackLeft.setDirection(DcMotor.Direction.REVERSE);
    }

    void setRunModesAuto()
    {
        // Configure the knobs of the hardware according to how you've wired your
        // robot. Here, we assume that there are no encoders connected to the motors,
        // so we inform the motor objects of that fact.
        this.motorFrontLeft.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        this.motorFrontRight.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        this.motorBackLeft.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        this.motorBackRight.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        this.motorDeliverySlider.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        this.motorCollector.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        this.motorHook.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        this.motorLift.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);


        this.motorDeliverySlider.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);

        // Two of the four motors (here, the left) should be set to reversed direction
        // so that it can take the same power level values as the other motor.
        this.motorFrontLeft.setDirection(DcMotor.Direction.REVERSE);
        this.motorBackLeft.setDirection(DcMotor.Direction.REVERSE);
    }

    void driveTo(double power, int position , boolean resetEncoders)
    {

        if(resetEncoders)
        {
            this.motorFrontLeft.setMode(DcMotorController.RunMode.RESET_ENCODERS);
            this.motorFrontRight.setMode(DcMotorController.RunMode.RESET_ENCODERS);
            this.motorBackLeft.setMode(DcMotorController.RunMode.RESET_ENCODERS);
            this.motorBackRight.setMode(DcMotorController.RunMode.RESET_ENCODERS);

        }
        this.motorFrontLeft.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        this.motorFrontRight.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        this.motorBackLeft.setMode(DcMotorController.RunMode.RUN_TO_POSITION);
        this.motorBackRight.setMode(DcMotorController.RunMode.RUN_TO_POSITION);


        this.motorBackLeft.setTargetPosition(position);
        this.motorBackRight.setTargetPosition(position);
        this.motorFrontLeft.setTargetPosition(position);
        this.motorFrontRight.setTargetPosition(position);
        this.motorBackLeft.setPower(power);
        this.motorBackRight.setPower(power);
        this.motorFrontLeft.setPower(power);
        this.motorBackRight.setPower(power);
        while ( this.motorBackLeft.isBusy() ||
                this.motorBackRight.isBusy() ||
                this.motorFrontLeft.isBusy() ||
                this.motorFrontRight.isBusy())
        {

        }
        this.motorBackLeft.setPower(0);
        this.motorBackRight.setPower(0);
        this.motorFrontLeft.setPower(0);
        this.motorBackRight.setPower(0);

    }


}

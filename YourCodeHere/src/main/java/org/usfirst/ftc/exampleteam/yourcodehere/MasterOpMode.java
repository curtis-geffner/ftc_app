package org.usfirst.ftc.exampleteam.yourcodehere;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;

import org.swerverobotics.library.SynchronousOpMode;
import org.swerverobotics.library.interfaces.TeleOp;
import org.swerverobotics.library.internal.ThunkedIrSeekerSensor;

/**
 * 417 master opmode
 */
public abstract class MasterOpMode extends SynchronousOpMode
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
    Servo   servoClimberLeft = null;
    Servo   servoClimberRight = null;


    DriveModeEnum driveMode = DriveModeEnum.TANK;

    //motor speed constants
    final double FULL_SPEED = 1.0;
    final double STOPPED = 0.0;
    final double FULL_SPEED_REVERSE = -1.0;

    //servo collector value
    double servoDeliveryPosition = 0;

    enum enumMotorSliderState
    {
        stopped,
        forwards,
        reverse
    }

    enumMotorSliderState motorSliderState = enumMotorSliderState.stopped;

    void initialize()
    {
        // Initialize our hardware variables
        this.motorFrontLeft = this.hardwareMap.dcMotor.get("motorFrontLeft");
        this.motorFrontRight = this.hardwareMap.dcMotor.get("motorFrontRight");
        this.motorBackLeft = this.hardwareMap.dcMotor.get("motorBackLeft");
        this.motorBackRight = this.hardwareMap.dcMotor.get("motorBackRight");
        this.motorCollector = this.hardwareMap.dcMotor.get("motorCollector");
        this.motorDeliverySlider = this.hardwareMap.dcMotor.get("motorDeliverySlider");
        this.servoDelivery = this.hardwareMap.servo.get("servoDelivery");
        this.servoClimberLeft = this.hardwareMap.servo.get("servoClimberLeft");
        this.servoClimberRight = this.hardwareMap.servo.get("servoClimberRight");
//        this.motorHook = this.hardwareMap.dcMotor.get("motorHook");
//        this.motorLift = this.hardwareMap.dcMotor.get("motorLift");

        // Two of the four motors (here, the left) should be set to reversed direction
        // so that it can take the same power level values as the other motor.
        this.motorFrontLeft.setDirection(DcMotor.Direction.REVERSE);
        this.motorBackLeft.setDirection(DcMotor.Direction.REVERSE);
        this.servoClimberLeft.setPosition(.5);
        this.servoClimberRight.setPosition(.5);
        this.servoDelivery.setPosition(.5);
    }

    public void delay(long millis) throws InterruptedException
    {
        long startTime = System.currentTimeMillis();
        while(System.currentTimeMillis() - startTime < millis)
        {
            telemetry.update();
            idle();
        }
    }

}

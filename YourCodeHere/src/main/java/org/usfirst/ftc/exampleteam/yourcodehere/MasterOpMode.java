package org.usfirst.ftc.exampleteam.yourcodehere;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;

import org.swerverobotics.library.SynchronousOpMode;
import org.swerverobotics.library.interfaces.EulerAngles;
import org.swerverobotics.library.interfaces.IBNO055IMU;
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
    //DcMotor motorHook = null;
    //DcMotor motorLift = null;
    Servo   servoDelivery = null;
    Servo   servoClimberLeft = null;
    Servo   servoClimberRight = null;
    Servo   servoDebrisMover = null;

    //sensors
    IBNO055IMU imu = null;

    //Variables to control the accessories
    CRServoToggler climberLeftToggler = null;
    CRServoToggler climberRightToggler = null;
    CRServoToggler deliveryToggler = null;
    CRServoToggler debrisMoverToggler = null;
    MotorRangedToggler slideToggler = null;
    MotorToggler collectorToggler = null;

    MotorToggler motorFrontRightToggle = null;
    MotorToggler motorBackRightToggle = null;
    MotorToggler motorFrontLeftToggle = null;
    MotorToggler motorBackLeftToggle = null;




    DriveModeEnum driveMode = DriveModeEnum.TANK;

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
        this.servoDebrisMover = this.hardwareMap.servo.get("servoDebrisMover");
       // this.imu = this.hardwareMap.i2cDevice.get("imu");
//        this.motorHook = this.hardwareMap.dcMotor.get("motorHook");
//        this.motorLift = this.hardwareMap.dcMotor.get("motorLift");

        telemetry.log.add("motors and servos found");

        // Two of the four motors (here, the left) should be set to reversed direction
        // so that it can take the same power level values as the other motor.
        this.motorFrontLeft.setDirection(DcMotor.Direction.REVERSE);
        this.motorBackLeft.setDirection(DcMotor.Direction.REVERSE);
        this.servoClimberLeft.setPosition(.5);
        this.servoClimberRight.setPosition(.5);
        this.servoDelivery.setPosition(.5);
        this.servoDebrisMover.setPosition(.5);

        telemetry.log.add("servo initial positions set");

        //Variables to control the accessories
        climberLeftToggler = new CRServoToggler(this.servoClimberLeft);
        climberRightToggler = new CRServoToggler(this.servoClimberRight);
        deliveryToggler = new CRServoToggler(this.servoDelivery);
        slideToggler = new MotorRangedToggler(this.motorDeliverySlider, -100000, 130000);
        collectorToggler = new MotorToggler(this.motorCollector);
        debrisMoverToggler = new CRServoToggler(this.servoDebrisMover);

        motorFrontRightToggle = new MotorToggler(this.motorFrontRight);
        motorBackRightToggle = new MotorToggler(this.motorBackRight);
        motorFrontLeftToggle = new MotorToggler(this.motorFrontLeft);
        motorBackLeftToggle = new MotorToggler(this.motorBackLeft);


        telemetry.log.add("togglers initialized");

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




    // COMPLETE THE ABOVE PATTERN FOR:
    // RIGHT CLIMBER
    // COLLECTOR
    // SLIDE
    // TROUGH SERVO

    final double FULL_SPEED = 1.0;
    final double STOPPED = 0.0;
    final double FULL_SPEED_REVERSE = -1.0;



    // DRIVE FUNCTIONS
    public void driveLeft(double power)
    {
        motorFrontLeftToggle.setSpeed(power);
        motorBackLeftToggle.setSpeed(power);
    }
    public void driveRight(double power)
    {
        motorFrontRightToggle.setSpeed(power);
        motorBackRightToggle.setSpeed(power);
    }
    public void driveForward(double power)
    {
        driveLeft(power);
        driveRight(power);
    }
    public void driveBackward(double power)
    {
        driveForward(-power);
    }
    public void driveStop()
    {
        driveForward(0);
    }
    public void driveTurn(double leftPower,double rightPower)
    {
        driveLeft(leftPower);
        driveRight(rightPower);
    }

}

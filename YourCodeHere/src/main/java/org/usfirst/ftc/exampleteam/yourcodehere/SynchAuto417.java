package org.usfirst.ftc.exampleteam.yourcodehere;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.swerverobotics.library.SynchronousOpMode;
import org.swerverobotics.library.interfaces.TeleOp;

/**
 * 417 teleop
 */
@TeleOp(name="417 Auto", group="Swerve Examples")
public class SynchAuto417 extends MasterOpmode417
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

    @Override protected void main() throws InterruptedException
    {

        initializeHardware();

        setRunModesAuto();


        // Wait until the game begins
        this.waitForStart();




    }




}

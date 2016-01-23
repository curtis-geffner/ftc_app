package org.usfirst.ftc.exampleteam.yourcodehere;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Curtis on 1/23/16.
 */
public class MotorToggler implements IMotionToggler {

    DcMotor motor = null;
    boolean isMoving = false;


    //TO DO move common constants to another file
    final double FULL_SPEED = 1.0;
    final double STOPPED = 0.0;
    final double FULL_SPEED_REVERSE = -1.0;

    public MotorToggler(){}

    public MotorToggler(DcMotor theMotor)
    {
        //TO DO  do we want to stop the motor here?
        motor = theMotor;
    }

    public void moveForward()
    {
        motor.setPower(FULL_SPEED);
        isMoving = true;
    }

    public void moveReverse()
    {
        motor.setPower(FULL_SPEED_REVERSE);
        isMoving = true;
    }

    public void stop()
    {
        if (isMoving)
        {
            motor.setPower(STOPPED);
        }

        isMoving = false;
    }

    public void setSpeed(double speed)
    {
        motor.setPower(speed);

        if(speed == 0)
        {
            isMoving = false;
        }
        else
        {
            isMoving = true;
        }
    }
}

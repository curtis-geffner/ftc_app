package org.usfirst.ftc.exampleteam.yourcodehere;

import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Curtis on 1/23/16.
 */
public class MotorRangedToggler extends MotorToggler {

    int minLimit =0;
    int maxLimit =0;

    public MotorRangedToggler(DcMotor theMotor, int min, int max)
    {
        //TO DO  do we want to stop the motor here?
        motor = theMotor;
        minLimit = min;
        maxLimit = max;
    }

    @Override
    public void moveForward()
    {
        if (motor.getCurrentPosition() < maxLimit) {
            motor.setPower(FULL_SPEED);
            isMoving = true;
        }
    }


    @Override
    public void moveReverse()
    {
        if (motor.getCurrentPosition() > minLimit)
        {
            motor.setPower(FULL_SPEED_REVERSE);
            isMoving = true;
        }
    }

    @Override
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

        if(speed == 0)
        {
            stop();
        }
        else if  ( (speed > 0) && (motor.getCurrentPosition() < maxLimit) )
        {
            motor.setPower(speed);
            isMoving = true;
        }
        else if ( (speed < 0) && (motor.getCurrentPosition() > minLimit) )
        {
            motor.setPower(speed);
            isMoving = true;
        }

    }

    public void checkPositionInRange()
    {
        int currentPosition = motor.getCurrentPosition();

        if ((currentPosition > maxLimit) || (currentPosition < minLimit))
        {
            stop();
        }
    }

}

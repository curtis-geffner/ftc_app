package org.usfirst.ftc.exampleteam.yourcodehere;

import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Curtis on 1/23/16.
 */
public class CRServoToggler implements IMotionToggler
{
    Servo servo = null;
    boolean isMoving = false;

    //TO DO move common constants to another file
    final double CR_REVERSE = 0.0;
    final double CR_FORWARD = 1.0;
    final double CR_STOP = 0.5;

    public CRServoToggler(Servo theServo)
    {
        servo = theServo;
    }

    public void moveForward()
    {
        servo.setPosition(CR_FORWARD);
        isMoving = true;
    }

    public void moveReverse()
    {
        servo.setPosition(CR_REVERSE);
        isMoving = true;
    }

    public void stop()
    {
        if (isMoving)
        {
            servo.setPosition(CR_STOP);
        }
        isMoving = false;
    }

}


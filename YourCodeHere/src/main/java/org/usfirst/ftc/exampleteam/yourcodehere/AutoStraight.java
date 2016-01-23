package org.usfirst.ftc.exampleteam.yourcodehere;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.swerverobotics.library.interfaces.TeleOp;

/**
 * 417 teleop
 */
@TeleOp(name="417 Auto", group="Swerve Examples")
public class AutoStraight extends MasterAuto
{
    //motor speed constants
    final double FULL_SPEED = 1.0;
    final double STOPPED = 0.0;
    final double FULL_SPEED_REVERSE = -1.0;

    //servo collector value
    double servoDeliveryPosition = 0;

    @Override protected void main() throws InterruptedException
    {
        initialize();

        // Wait until the game begins
        this.waitForStart();

        telemetry.log.add("starting wait");
        delay(9500);
        driveTo(-1,-13000,true);
    }
}

package org.usfirst.ftc.exampleteam.yourcodehere;
import org.swerverobotics.library.interfaces.Autonomous;
/*
	Autonomous program turns 90 degrees.
*/
@Autonomous(name = "AUTO Red 1 -> Park", group = "417")
public class Red1ToPark extends MasterAuto
{
    @Override
    protected void main() throws InterruptedException
    {
        //Initialize our hardware
        initialize();

        // Wait until we've been given the ok to go
        waitForStart();

        setAutoStartPosition(90);

        wait(9000);
        driveStraight(270, Constants.BACKWARDS, false);
        turnTo(135);
        driveStraight(77, Constants.BACKWARDS, false);
        wait(500);
//        HikerDropper.deploy();
        wait(2000);
        driveStraight(77, Constants.SLOW_FORWARDS, false);
//        HikerDropper.retract();
        driveStraight(77, Constants.SLOW_BACKWARDS, false);
    }
}

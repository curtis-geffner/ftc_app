package org.usfirst.ftc.exampleteam.yourcodehere;

/**
 * Created by Cole on 12/30/2015.
 */
/**
 Type for handling future autonomous tasks
 */
public class Transform
{
    private final double[] values;
    double xPosition;
    double yPosition;
    double orientation;

    //constructors
    public Transform(double[] values)
    {
        this.values = values;
        this.xPosition = values[0];
        this.yPosition = values[1];
        this.orientation = values[2];
    }

    public Transform(double xI, double yI, double zI)
    {
        this.values = new double[]{xI, yI, zI};
        this.xPosition = values[0];
        this.yPosition = values[1];
        this.orientation = values[2];
    }
}



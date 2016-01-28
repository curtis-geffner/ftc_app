package org.usfirst.ftc.exampleteam.yourcodehere;

/*
    Proportional-Integral-Derivative
    Calculates a weighted sum of a input, it's first integral, and first derivative.
    Generally used to produce efficient, non-oscillating motion in a one dimensional system.
    Feed this the difference between the target value and real value
*/
//TODO move average elsewere
public class PIDFilter implements IFilter
{
    double lastTime = 0;
    double nextTime = 0;


    //must be >=2
    //makes reading from discreet sensors smoother
    private final int RECORD_DEPTH = 2;

    //Proportional coefficient
    private double εP;
    //Integral coefficient
    private double εI;
    //Derivative coefficient
    private double εD;

    //construct with the coefficients
    public PIDFilter(double P, double I, double D)
    {
        εP = P;
        εI = I;
        εD = D;
    }

    public double[] values = new double[RECORD_DEPTH];
    private double[] dVArray = new double[RECORD_DEPTH-1];
    public double sum = 0;
    public double dV  = 0;

    //update all non-actual values
    public void update()
    {
        sum += values[0];
        dV = average(dVArray);
    }

    //update actual
    public void roll(double newValue)
    {
        for (int i=0; i<(values.length-1); i++ ){
            values[i+1] = values[i];
        }
        values[0] = newValue;
    }

    public double getLastDiff()
    {
        return values[0] - values[1];
    }
    //update dVArray
    private void rolldV()
    {
        for (int i=0; i<dVArray.length; i++ ){
            dVArray[i] = values[i] - values[i+1];
        }
    }

    public double getFilteredValue()
    {
        return (εP*values[0] ) + ( εI* sum) + ( εD*dV );
    }

    //return the mean values of a list
    private double average(double[] list)
    {
        double total = 0;
        for (int i=0; i<list.length; i++ ){
            total += list[i];
        }
        return total / list.length;
    }
}
package org.usfirst.ftc.exampleteam.yourcodehere;

/*
    This is implimented by any control filter classes.
    roll() and update() are kept separate due to properties of certain potential filters
 */
public interface IFilter
{
    void roll(double newValue);

    double getFilteredValue();

}

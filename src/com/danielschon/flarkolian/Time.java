package com.danielschon.flarkolian;

public class Time 
{
	/* The number of milliseconds elapsed between updates */
    static int delta = 0;
    
    /* System time upon last check */
    static long lastTime = 0;
    
    public static int getDelta()
    {
    	long time = System.currentTimeMillis();
    	if (lastTime != 0)
    		delta = (int) time - (int) lastTime;
    	lastTime = time;
    	return delta;
    }
}

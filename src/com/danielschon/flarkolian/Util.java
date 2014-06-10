package com.danielschon.flarkolian;

import java.util.Random;

import android.util.Log;

/**
 * Some general utility functions
 */
public class Util 
{
	static Random r = new Random();
	
	public static int randInt(int min, int max)
	{
		if (max < min)
		{
			Log.e("random error", "Max cannot be less than min, idiot!");
			return(0);
		}
		
		return r.nextInt(max - min + 1) + min;
	}
	
	public static float randFloat(float min, float max)
	{
		if (max < min)
		{
			Log.e("random error", "Max cannot be less than min, idiot!");
			return(0);
		}
		
		return r.nextFloat() * (max - min) + min;
	}
}

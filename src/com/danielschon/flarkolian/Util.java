package com.danielschon.flarkolian;

import java.util.Random;

import android.util.Log;

/**
 * Some general utility functions
 */
public class Util 
{
	public static int randInt(int min, int max)
	{
		if (max < min)
		{
			Log.e("random error", "Max cannot be greater than min, idiot!");
			return(0);
		}
		Random r = new Random();
		return r.nextInt(max - min + 1) + min;
	}
	
	public static float randFloat(float min, float max)
	{
		if (max < min)
		{
			Log.e("random error", "Max cannot be greater than min, idiot!");
			return(0);
		}
		Random r = new Random();
		return r.nextFloat() * (max - min) + min;
	}
}

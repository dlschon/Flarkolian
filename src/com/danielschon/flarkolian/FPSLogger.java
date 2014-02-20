package com.danielschon.flarkolian;

import android.util.Log;

/**
 * Calculates the framerate and logs it to logcat every second
 */
public class FPSLogger extends Entity
{
	private int frameCount = 0;
	private long lastTime = 0;
	private long time = 0;
	
	private static int LOGINTERVAL = 1000;
	
	@Override
	public void update() 
	{
		frameCount++;
		time = System.currentTimeMillis();
		if (time - lastTime >= LOGINTERVAL)
		{
			Log.i("fps:", String.valueOf(frameCount));
			frameCount = 0;
			lastTime = time;
		}
	}
}

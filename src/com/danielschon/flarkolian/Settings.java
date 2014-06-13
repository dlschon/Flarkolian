package com.danielschon.flarkolian;

import android.graphics.RectF;

public class Settings 
{
	public static enum ctrlStyle
	{
		VIRTUAL_KEYPAD,
		VIRTUAL_KEYPAD_MIRROR,
		TILT,
	}
	public static int stars = 30;
	public static ctrlStyle controlStyle = ctrlStyle.VIRTUAL_KEYPAD;
	
	public static float getScoreboardX()
	{
		return (controlStyle == ctrlStyle.VIRTUAL_KEYPAD ? 0 : controlStyle == ctrlStyle.VIRTUAL_KEYPAD_MIRROR ? Game.widthWindow * .75f : 0);
	}
	
	public static Rectangle getFieldRect()
	{
		return new Rectangle(getFieldMin(), 0f, getFieldMax(), (float) Game.heightWindow);
	}
	/**
	 * Get the minimum x-value of the playing field
	 * @return
	 */
	public static float getFieldMin()
	{
		return (getScoreboardX() == 0 ? Game.widthWindow * .25f : 0);
	}
	
	/**
	 * Get the minimum x-value of the playing field
	 * @return
	 */
	public static float getFieldMax()
	{
		return (getScoreboardX() == 0 ? Game.widthWindow : Game.widthWindow * .75f);
	}
}

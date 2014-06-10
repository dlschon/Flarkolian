package com.danielschon.flarkolian;

public class Settings 
{
	public static enum ctrlStyle
	{
		VIRTUAL_KEYPAD,
		TILT,
		HARDWARE_OVERRIDE,
	}
	public static int stars = 30;
	public static ctrlStyle controlStyle = ctrlStyle.VIRTUAL_KEYPAD;
}

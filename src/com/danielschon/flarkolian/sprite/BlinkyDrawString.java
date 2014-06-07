package com.danielschon.flarkolian.sprite;

import com.danielschon.flarkolian.Vec2;

public class BlinkyDrawString extends DrawString
{
	int count = 0;
	int period = 30;
	int index = 0;
	
	
	public BlinkyDrawString(Vec2 position, Vec2 size, String text) 
	{
		super(position, size, text);
	}
	
	public BlinkyDrawString(Vec2 position, String text)
	{
		super(position, text);
	}
	
	@Override
	public void update()
	{
		count++;
		if (count >= period)
		{
			if (visible)
			{
				visible = false;
			}
			else
			{
				visible = true;
			}
			count = 0;
		}
		super.update();
	}
}

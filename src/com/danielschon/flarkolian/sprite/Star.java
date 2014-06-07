package com.danielschon.flarkolian.sprite;

import com.danielschon.flarkolian.Game;
import com.danielschon.flarkolian.SubTexture;
import com.danielschon.flarkolian.Util;
import com.danielschon.flarkolian.Vec2;

public class Star extends Sprite
{
	
	private float speed;
	
	public Star(Vec2 position) 
	{
		super(position);
		depth = 0;
		int starType = Util.randInt(0, 2);
		st = new SubTexture(2, starType, 2);
		size.x = Util.randFloat(50, 100);
		size.y = size.x;
		speed = size.x / 10f;
		depth = (int)size.x -100;
		refresh();
	}
	
	@Override
	public void update()
	{
		loc.y -= speed;
		if (loc.y < -size.y)
		{
			//Recycle as new and different star
			loc.y = Game.heightWindow;
			loc.x = Util.randInt(0, Game.widthWindow);
			size.x = Util.randFloat(50, 100);
			size.y = size.x;
			speed = size.x / 10f;
			depth = (int)size.x -100;
		}
		super.update();
		
	}
	
}

package com.danielschon.flarkolian.sprite;

import com.danielschon.flarkolian.Game;
import com.danielschon.flarkolian.Settings;
import com.danielschon.flarkolian.SubTexture;
import com.danielschon.flarkolian.Util;
import com.danielschon.flarkolian.Vec2;
import com.danielschon.flarkolian.Game.GameState;

public class Star extends Sprite
{
	private static final float[] STAR_TYPE_WEIGHTS = 
		{
			.30f,
			.50f,
			.20f
		};
	private float speed;
	
	public Star(Vec2 position) 
	{
		super(position);
		depth = 0;
		
		//Get a random weighted star type
		int starType = 0;
		float n = Util.randFloat(0, 1);
		if (n < STAR_TYPE_WEIGHTS[0])
			starType = 0;
		else if (n < STAR_TYPE_WEIGHTS[0] + STAR_TYPE_WEIGHTS[1])
			starType = 1;
		else
			starType = 2;
		
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
			
			//Set the range that stars appear
			float min = (Game.gameState == GameState.INGAME ? Settings.getFieldMin() : 0);
			float max = (Game.gameState == GameState.INGAME ? Settings.getFieldMax() : Game.widthWindow);
			loc.x = Util.randFloat(min, max);
			size.x = Util.randFloat(50, 100);
			size.y = size.x;
			speed = size.x / 10f;
			depth = (int)size.x -100;
		}
		super.update();
		
	}
	
}

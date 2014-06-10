package com.danielschon.flarkolian.sprite;

import com.danielschon.flarkolian.Game;
import com.danielschon.flarkolian.SubTexture;
import com.danielschon.flarkolian.Util;
import com.danielschon.flarkolian.Vec2;

public class Star extends Sprite
{
	private static float[] typeWeights = 
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
		if (n < typeWeights[0])
			starType = 0;
		else if (n < typeWeights[0] + typeWeights[1])
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
			loc.x = Util.randInt(Game.instance.starRange[0], Game.instance.starRange[1]);
			size.x = Util.randFloat(50, 100);
			size.y = size.x;
			speed = size.x / 10f;
			depth = (int)size.x -100;
		}
		super.update();
		
	}
	
}

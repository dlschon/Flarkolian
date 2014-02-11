package com.danielschon.flarkolian;

import android.util.Log;

public class Player2 extends Sprite
{
	
	private float speed = 20f;
	
	public Player2(int program, Vec2 position)
	{
		super(program, new SubTexture(1,4,6), position);
		st = new SubTexture(1,4,6);
		stSequence = 
		new SubTexture[]	
			{
				new SubTexture(1,4,6),
				new SubTexture(1,5,6),
				new SubTexture(1,6,6),
				new SubTexture(1,7,6),
				new SubTexture(1,0,5),
				new SubTexture(1,1,5),
				new SubTexture(1,2,5)
			};
		size = new Vec2(200, 200); 
		this.isAnimated = true;
		this.animSpeed = 5;
		this.refresh();
	}
	
	@Override
	public void update()
	{
		loc.x += speed;
		if (loc.x > Game.widthWindow - size.x)
		{
			speed *= -1;
			loc.x = Game.widthWindow - size.x;
		}
		if (loc.x < 0)
		{
			speed *= -1;
			loc.x = 0;
		}
		super.update();
	}
}

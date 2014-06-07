package com.danielschon.flarkolian.sprite.enemy;

import com.danielschon.flarkolian.Game;
import com.danielschon.flarkolian.SubTexture;
import com.danielschon.flarkolian.Vec2;
import com.danielschon.flarkolian.sprite.Enemy;

/**
 * The 4th enemy of the 1st tier
 */
public class Enemy14 extends Enemy
{
	public Enemy14(Vec2 position) 
	{
		super(position);
		stSequence = 
		new SubTexture[]	
			{
				new SubTexture(1,2,6),
				new SubTexture(1,3,6)
			};
		st = stSequence[0];
		size = new Vec2(200, 200); 
		this.isAnimated = true;
		this.animSpeed = 10;
		this.speed.x = 20;
		this.refresh();
	}
	
	@Override
	public void update()
	{
		loc.x += speed.x;
		if (loc.x > Game.widthWindow - size.x)
		{
			speed.x *= -1;
			loc.x = Game.widthWindow - size.x;
		}
		if (loc.x < 0)
		{
			speed.x *= -1;
			loc.x = 0;
		}
		super.update();
	}

}

package com.danielschon.flarkolian.sprite.enemy;

import com.danielschon.flarkolian.Game;
import com.danielschon.flarkolian.Rectangle;
import com.danielschon.flarkolian.SubTexture;
import com.danielschon.flarkolian.Vec2;

/**
 * The flying saucer
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
		size = new Vec2(160, 160); 
		this.isAnimated = true;
		this.animSpeed = 10;
		this.speed.x = 20;
		this.health = 1;
		this.relColRect = new Rectangle(0, 120, 160, 160);
	}
	
	@Override
	public void update()
	{
		
		super.update();
	}

}

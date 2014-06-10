package com.danielschon.flarkolian.sprite;

import com.danielschon.flarkolian.Vec2;

/**
 * The things that you shoot
 *
 */
public abstract class Enemy extends Sprite
{
	
	protected Vec2 speed = new Vec2(0,0);
	
	public Enemy(Vec2 position)
	{
		super(position);
		depth = 1;
	}
}

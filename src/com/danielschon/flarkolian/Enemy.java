package com.danielschon.flarkolian;

import android.util.Log;

/**
 * The things that you shoot
 *
 */
public abstract class Enemy extends Sprite
{
	
	protected Vec2 speed = new Vec2(0,0);
	
	public Enemy(int program, Vec2 position)
	{
		super(program, position);
	}
}
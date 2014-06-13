package com.danielschon.flarkolian.sprite.enemy;

import com.danielschon.flarkolian.Game;
import com.danielschon.flarkolian.Vec2;
import com.danielschon.flarkolian.sprite.Sprite;

/**
 * The things that you shoot
 *
 */
public abstract class Enemy extends Sprite
{
	
	protected Vec2 speed = new Vec2(0,0);
	protected int health = 1;
	
	public Enemy(Vec2 position)
	{
		super(position);
		depth = 1;
	}
	
	@Override
	public void update()
	{
		loc.x += speed.x;
		if (loc.x > Game.fieldRect.right - size.x)
		{
			speed.x *= -1;
			loc.x = Game.fieldRect.right - size.x;
		}
		if (loc.x < Game.fieldRect.left)
		{
			speed.x *= -1;
			loc.x = Game.fieldRect.left;
		}
		super.update();
	}
}

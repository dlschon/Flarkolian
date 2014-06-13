package com.danielschon.flarkolian.sprite;

import com.danielschon.flarkolian.Game;
import com.danielschon.flarkolian.SubTexture;
import com.danielschon.flarkolian.Vec2;

public class Shot extends Sprite 
{

	public static final int SPEED = 30;
	float  velocity = 0;
	
	public Shot(Vec2 position, boolean isEnemy) 
	{
		super(position);
		this.st = new SubTexture(2, isEnemy ? 3 : 2, 1);
		this.velocity = isEnemy ? -SPEED : SPEED;
		this.centered = true;
		this.size = new Vec2(40, 40);
	}
	
	@Override
	public void update()
	{
		loc.y += velocity;
		if (loc.y > Game.heightWindow || loc.y < 0)
		{
			Game.instance.removeEntity(this);
		}
		super.update();
	}

}

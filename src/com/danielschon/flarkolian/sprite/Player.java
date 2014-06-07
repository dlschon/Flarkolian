package com.danielschon.flarkolian.sprite;

import com.danielschon.flarkolian.Game;
import com.danielschon.flarkolian.SubTexture;
import com.danielschon.flarkolian.Vec2;

import android.util.Log;

public class Player extends Sprite
{
	
	private float speed = 0f;
	private float acceleration = 2f;
	private float decceleration = 2f;
	private float topSpeed = 30f;

	boolean entering = true;
	
	public Player(boolean enterFromTop)
	{
		super(new Vec2((3f/8f) * (float)Game.widthWindow, enterFromTop ? Game.heightActual : -200));
		st = new SubTexture(0,0,0);
		size = new Vec2(200, 200); 
		depth = 1;
		this.refresh();
	}
	
	@Override
	public void update()
	{
		if (entering)
		{
			//Settle to y = 50
			if (loc.y > 50)
			{
				loc.y -= 11;
				if (loc.y < 50)
					loc.y = 50;
			}
			
			if (loc.y < 50)
			{
				loc.y += 5;
				if (loc.y > 50)
					loc.y = 50;
			}
			
			if (loc.y == 50)
				entering = false;
		}
		else
		{
			if (Game.pressState)
			{
				if (Game.press.getX() > Game.widthActual/2)
				{
					speed += acceleration;
				}
				else
				{
					speed -= acceleration;
				}
				if (speed > topSpeed)
					speed = topSpeed;
				if (speed < -topSpeed)
					speed = -topSpeed;
			}
			else
			{
				if (speed > 0)
				{
					if (speed > decceleration)
						speed -= decceleration;
					else
						speed = 0;
				}
				if (speed < 0)
				{
					if (speed < -decceleration)
						speed += decceleration;
					else
						speed = 0;
				}
			}
			
			loc.x += speed;
			
			if (loc.x < 0)
			{
				if (speed < 0)
					speed = 0;
				loc.x = 0;
			}
			if (loc.x > Game.widthWindow - size.x)
			{
				if (speed > 0)
					speed = 0;
				loc.x = Game.widthWindow - size.x;
			}
		}
		super.update();
	}
}

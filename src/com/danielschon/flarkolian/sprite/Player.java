package com.danielschon.flarkolian.sprite;

import android.util.Log;

import com.danielschon.flarkolian.Game;
import com.danielschon.flarkolian.Settings;
import com.danielschon.flarkolian.SubTexture;
import com.danielschon.flarkolian.Vec2;

public class Player extends Sprite
{
	private static final int Y_POSITION = 150;
	
	public float speed = 0f;
	private float acceleration = 2f;
	private float decceleration = 2f;
	private float topSpeed = 30f;

	boolean entering = true;
	
	public Player(boolean enterFromTop)
	{
		super(new Vec2((3f/8f) * (float)Game.widthWindow, enterFromTop ? Game.heightActual : -150));
		st = new SubTexture(0,0,0);
		size = new Vec2(150, 150); 
		depth = 1;
		this.refresh();
	}
	
	@Override
	public void update()
	{
		if (entering)
		{
			//Settle to y = Y_POSITION
			if (loc.y > Y_POSITION)
			{
				loc.y -= 11;
				if (loc.y < Y_POSITION)
					loc.y = Y_POSITION;
			}
			
			if (loc.y < Y_POSITION)
			{
				loc.y += 5;
				if (loc.y > Y_POSITION)
					loc.y = Y_POSITION;
			}
			
			if (loc.y == Y_POSITION)
				entering = false;
		}
		else
		{
			if (Settings.controlStyle == Settings.ctrlStyle.VIRTUAL_KEYPAD)
				virtualKeypad();
		}
		super.update();
	}
	
	/**
	 * The movement logic for the "Virtual Buttons" control style
	 */
	private void virtualKeypad()
	{
		if (Game.instance.keypad == null)
			return;
		
		Keypad keypad = Game.instance.keypad;
		if (keypad.state != Keypad.State.NONE)
		{
			//A movement key is being pressed
			
			if (keypad.state == Keypad.State.RIGHT)
			{
				//Accelerate right
				speed += acceleration;
			}
			if (keypad.state == Keypad.State.LEFT)
			{
				//Accelerate left
				speed -= acceleration;
			}
			
			//Limit speed to topSpeed
			if (speed > topSpeed)
				speed = topSpeed;
			if (speed < -topSpeed)
				speed = -topSpeed;
		}
		else
		{
			//No movement key is being pressed
			
			//Decelerate
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
		
		//Move it
		loc.x += speed;
		
		if (loc.x < 0)
		{
			if (speed < 0)
				speed = 0;
			loc.x = 0;
		}
		if (loc.x > (Game.widthWindow * .75) - size.x)
		{
			if (speed > 0)
				speed = 0;
			loc.x = (float) ((Game.widthWindow * .75) - size.x);
		}
	}
}

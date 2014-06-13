package com.danielschon.flarkolian.sprite;

import com.danielschon.flarkolian.Game;
import com.danielschon.flarkolian.Input;
import com.danielschon.flarkolian.Settings;
import com.danielschon.flarkolian.Sound;
import com.danielschon.flarkolian.SubTexture;
import com.danielschon.flarkolian.Vec2;

public class Player extends Sprite
{
	private static final int Y_POSITION = 75;
	private float xMin = 0;
	private float xMax = 1080;
	
	public float speed = 0f;
	private float acceleration = 2f;
	private float decceleration = 2f;
	private float topSpeed = 30f;

	boolean entering = true;
	
	boolean fired = false;	//Used to detect if a pointer down should fire
	
	public Player(boolean enterFromTop)
	{
		super(new Vec2((Settings.getFieldMin() + Settings.getFieldMax()) / 2 - 80, enterFromTop ? Game.heightActual : -160));
		st = new SubTexture(0,0,0);
		size = new Vec2(160, 160); 
		depth = 1;
		relColRect.set(new Vec2(0,0), size);
		
		this.xMin = Game.fieldRect.left;
		this.xMax = Game.fieldRect.right - this.size.x;
		
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
			//Do movement
			if (Settings.controlStyle == Settings.ctrlStyle.VIRTUAL_KEYPAD || Settings.controlStyle == Settings.ctrlStyle.VIRTUAL_KEYPAD_MIRROR)
				virtualKeypad();
			
			//Fire if necessary
			if (Input.testRegion(Game.fieldRect))
			{
				if (!fired)
				{
					fired = true;
					fire();
				}
			}
			else
			{
				if (fired)
				{
					fired = false;
				}
			}
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
		
		if (loc.x < xMin)
		{
			if (speed < 0)
				speed = 0;
			loc.x = xMin;
		}
		if (loc.x > xMax)
		{
			if (speed > 0)
				speed = 0;
			loc.x = xMax;
		}
	}

	public void fire() 
	{
		Sound.play(Sound.ID_LASER);
		Shot shot = new Shot(new Vec2(loc.x + size.x / 2, loc.y + size.y), false);
		Game.instance.addEntity(shot);
	}
}

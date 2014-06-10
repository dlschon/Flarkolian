package com.danielschon.flarkolian.sprite;

import com.danielschon.flarkolian.Game;
import com.danielschon.flarkolian.SubTexture;
import com.danielschon.flarkolian.Vec2;

public class Keypad extends Sprite 
{
	public enum State
	{
		LEFT,
		NONE,
		RIGHT
	}
	
	SubTexture none = new SubTexture(7,0,1);
	SubTexture left = new SubTexture(7,1,1);
	SubTexture right = new SubTexture(7,0,0);
	
	
	public State state;
	
	public Keypad() 
	{
		super(new Vec2(Game.widthWindow * (3f/4f), 0));
		this.st = this.none;
		this.size = new Vec2(Game.widthWindow * .25f, Game.heightWindow * (2f / 9f));
	}
	
	@Override
	public void update()
	{
		if (Game.pressState && Game.press.getX() > loc.x)
		{
			if (Game.press.getX() > loc.x + size.x/2)
			{
				//It's a right key press
				this.state = State.RIGHT;
				this.st = this.right;
			}
			else
			{
				//It's a left key press
				this.state = State.LEFT;
				this.st = this.left;
			}
		}
		else
		{
			this.state = State.NONE;
			this.st = this.none;
		}
		super.update();
	}

}

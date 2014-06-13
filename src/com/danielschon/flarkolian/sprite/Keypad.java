package com.danielschon.flarkolian.sprite;

import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;

import com.danielschon.flarkolian.Game;
import com.danielschon.flarkolian.Input;
import com.danielschon.flarkolian.Rectangle;
import com.danielschon.flarkolian.Settings;
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
	
	Rectangle leftRect;
	Rectangle rightRect;
	
	public Keypad() 
	{
		super(new Vec2(Settings.getScoreboardX(), 0));
		this.st = this.none;
		this.size = new Vec2(Game.widthWindow * .25f, Game.heightWindow * (2f / 9f));
		relColRect = new Rectangle(new Vec2(0,0), size);
		leftRect = new Rectangle(loc.x, loc.y, loc.x + size.x / 2, loc.y + size.y);
		rightRect = new Rectangle(loc.x + size.x / 2, loc.y , loc.x + size.x, loc.y + size.y);
	}
	
	@Override
	public void update()
	{
		this.state = State.NONE;
		this.st = this.none;
		if (Input.numPointers > 0)
		{
			//Check for directional presses
			if (Input.testRegion(absColRect))
			{
				if (Input.testRegion(rightRect) && state == State.NONE)
				{
					//It's a right key press
					this.state = State.RIGHT;
					this.st = this.right;
				}
				if (Input.testRegion(leftRect) && state == State.NONE)
				{
					//It's a left key press
					this.state = State.LEFT;
					this.st = this.left;
				}
			}
		}

		super.update();
	}

}

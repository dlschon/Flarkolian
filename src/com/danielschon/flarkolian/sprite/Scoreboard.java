package com.danielschon.flarkolian.sprite;

import com.danielschon.flarkolian.Game;
import com.danielschon.flarkolian.SubTexture;
import com.danielschon.flarkolian.Vec2;

public class Scoreboard extends Sprite
{

	public Scoreboard() 
	{
		super(new Vec2(Game.widthWindow * (3f/4f), Game.heightWindow * (2f / 9f)));
		this.size = new Vec2(Game.widthWindow/4, Game.heightWindow * (7f / 9f));
		this.st = new SubTexture(6,0,0);
	}

}

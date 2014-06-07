package com.danielschon.flarkolian.group;

import com.danielschon.flarkolian.Game;
import com.danielschon.flarkolian.Util;
import com.danielschon.flarkolian.Vec2;
import com.danielschon.flarkolian.sprite.Gear;
import com.danielschon.flarkolian.sprite.Star;
import com.danielschon.flarkolian.sprite.Title;

public class StarGroup extends Group 
{
	public StarGroup(Game game)
	{
		super(game);
	}
	
	@Override
	public void initContents()
	{
		//Add 30 stars
        for (int i = 0; i < 30; i++)
        {
        	this.add(new Star(new Vec2(Util.randInt(0, game.widthWindow), Util.randInt(0, game.heightWindow))));
        }
	}
}

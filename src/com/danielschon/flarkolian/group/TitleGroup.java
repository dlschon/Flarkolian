package com.danielschon.flarkolian.group;

import com.danielschon.flarkolian.Game;
import com.danielschon.flarkolian.TitleCallback;
import com.danielschon.flarkolian.Vec2;
import com.danielschon.flarkolian.sprite.BlinkyDrawString;
import com.danielschon.flarkolian.sprite.Gear;
import com.danielschon.flarkolian.sprite.Title;

public class TitleGroup extends Group 
{
	TitleCallback tcb;
	public Gear gear;
	
	public TitleGroup(Game game, TitleCallback tcb)
	{
		super(game);
		this.tcb = tcb;
	}
	
	@Override
	public void initContents()
	{
		this.add(new Title(this, new Vec2(0, Game.heightWindow - Game.widthActual/4)));
		gear = new Gear(new Vec2(Game.widthWindow-256, 0));
		this.add(gear);
		this.add(new BlinkyDrawString(new Vec2(0,0), new Vec2(128,128), "TAP TO START"));
	}

	public void begin() 
	{
		tcb.onBegin(this.game);
	}
}

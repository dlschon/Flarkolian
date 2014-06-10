package com.danielschon.flarkolian.group;

import com.danielschon.flarkolian.Game;
import com.danielschon.flarkolian.Settings;
import com.danielschon.flarkolian.Util;
import com.danielschon.flarkolian.Vec2;
import com.danielschon.flarkolian.sprite.ExhaustParticle;
import com.danielschon.flarkolian.sprite.Player;
import com.danielschon.flarkolian.sprite.Star;

public class PlayerGroup extends Group 
{
	private Player player;
	
	public PlayerGroup(Game game)
	{
		super(game);
	}
	
	@Override
	public void initContents()
	{
		player = new Player(true);
		this.add(player);
		
		//Add 30 exhaust particles
        for (int i = 0; i < Settings.stars; i++)
        {
        	this.add(new ExhaustParticle(player));
        }
	}
	
	public Player getPlayer()
	{
		return player;
	}
}

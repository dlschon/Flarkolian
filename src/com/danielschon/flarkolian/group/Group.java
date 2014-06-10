package com.danielschon.flarkolian.group;

import java.util.ArrayList;

import com.danielschon.flarkolian.Entity;
import com.danielschon.flarkolian.Game;

public class Group 
{
	public ArrayList<Entity> contents = new ArrayList<Entity>();
	protected Game game;
	
	public Group(Game game)
	{
		this.game = game;
		initContents();
	}
	
	public  void initContents()
	{
		
	}
	
	/**
	 * Adds a sprite to the room
	 * @param s
	 */
	public void add(Entity e)
	{
		contents.add(e);
	}
	
	/**
	 * Adds the contents of the room into the game
	 * @param game
	 */
	public void deploy()
	{
		for (Entity e : contents)
		{
			game.addEntity(e);
		}
	}
	
	/**
	 * Destroys all entities in the room
	 */
	public void destroy()
	{
		if (game != null)
		{
			for (Entity e: contents)
			{
				game.removeEntity(e);	
			}
		}
	}
}

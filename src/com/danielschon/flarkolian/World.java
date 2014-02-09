package com.danielschon.flarkolian;

import java.util.ArrayList;

/**
 * Contains all of the Entities
 *
 */
public abstract class World 
{
	ArrayList<Entity> entities;
	int readIndex = 0;
	
	public World()
	{
		entities = new ArrayList<Entity>();
		//init();
	}
	
	/**
	 * initialize the world here; Add Entities, change properties, whatever
	 */
	protected abstract void init();
	
	/**
	 * returns true if there is a next entity in the list.
	 * @return 
	 */
	public boolean hasNext()
	{
		boolean result = entities.size() > readIndex;
		if (!result)
			readIndex = 0;
		return (result);
	}
	
	public Entity getNext()
	{
		Entity entity = entities.get(readIndex);
		readIndex++;
		return entity;
	}
	
	/**
	 * inits and adds an entity to the world
	 * @param e
	 */
	public void create(Entity e)
	{
		entities.add(e);
	}
}

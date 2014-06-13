package com.danielschon.flarkolian;
/**
 * A 2-dimensional vector. Because screw PointF
 */
public class Vec2 
{
	public float x;
	public float y;
	
	public Vec2(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void set(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Vec2 clone()
	{
		return new Vec2(x,y);
	}
	
	/**
	 * Mirrors the y-value about the center of the screen
	 * @return
	 */
	public Vec2 mirrorY()
	{
		y = -y + Game.heightWindow;
		return this;
	}
	
	/**
	 * Mirrors the x-value about the center of the screen
	 * @return
	 */
	public Vec2 mirrorX()
	{
		x = -x + Game.widthWindow;
		return this;
	}
}

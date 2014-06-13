package com.danielschon.flarkolian;

/**
 * Because screw RectF
 */
public class Rectangle 
{
	public float left;
	public float bottom;
	public float right;
	public float top;
	
	public Rectangle()
	{
		left = 0;
		bottom = 0;
		right = 0;
		top = 0;
	}
	
	public Rectangle(Vec2 loc, Vec2 size)
	{
		set(loc, size);
	}
	
	public Rectangle(float left, float bottom, float right, float top)
	{
		set(left, bottom, right, top);
	}
	
	public void set(Vec2 loc, Vec2 size)
	{
		left = loc.x;
		bottom = loc.y;
		right = loc.x + size.x;
		top = loc.y + size.y;
	}
	
	public void set(float left, float bottom, float right, float top)
	{
		this.left = left;
		this.bottom = bottom;
		this.right = right;
		this.top = top;
	}
	
	/**
	 * Returns true if the rectangle contains a point
	 * @param pt
	 * @return
	 */
	public boolean contains(Vec2 pt)
	{
		if (pt.x < left)
			return false;
		if (pt.x > right)
			return false;
		if (pt.y < bottom)
			return false;
		if (pt.y > top)
			return false;
		return true;
	}
	
	public boolean contains(float x, float y)
	{
		return contains(new Vec2(x, y));
	}
	
	public boolean intersects(Rectangle rect)
	{
		if (rect.right < left)
			return false;
		if (rect.left > right)
			return false;
		if (rect.top < bottom)
			return false;
		if (rect.bottom > top)
			return false;
		return false;
	}
	
	/**
	 * Returns a new Rectangle that has been translated by a Vec2
	 * @param vec
	 * @return
	 */
	public Rectangle addVec2(Vec2 vec)
	{
		return new Rectangle(left + vec.x, bottom + vec.y, right + vec.x, top + vec.y);
	}
}

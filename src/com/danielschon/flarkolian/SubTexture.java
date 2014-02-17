package com.danielschon.flarkolian;

/**
 * SubTexture is a class used to locate an individual sprite within a spritesheet. Sheet refers to the spritesheet's index, and 
 * texCoordX and texCoordY indicate the sprite's position on the spritesheet.
 */
public class SubTexture 
{
	public int sheet;
	public int x;
	public int y;
	
	public SubTexture(int sheet, int x, int y)
	{
		this.sheet = sheet;
		this.x = x;
		this.y = y;
	}
	
	/**
	 * returns true if the coordinates of the given SubTexture match this one's
	 * @return isEqual
	 */
	public boolean equals(SubTexture st)
	{
		return (st.sheet == sheet && st.x == x && st.y ==y);
	}
}

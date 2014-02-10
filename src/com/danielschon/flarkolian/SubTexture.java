package com.danielschon.flarkolian;

/**
 * SubTexture is a class used to locate an individual sprite within a spritesheet. Sheet refers to the spritesheet's index, and 
 * texCoordX and texCoordY indicate the sprite's position on the spritesheet.
 */
public class SubTexture 
{
	public int sheet;
	public float texCoordX;
	public float texCoordY;
	
	public SubTexture(int sheet, float x, float y)
	{
		this.sheet = sheet;
		this.texCoordX = x;
		this.texCoordY = y;
	}
}

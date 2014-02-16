package com.danielschon.flarkolian;

public class Star extends Sprite
{

	public Star(Vec2 position) 
	{
		super(position);
		int starType = Util.randInt(0, 2);
		st = new SubTexture(2, starType, 2);
		size.x = Util.randFloat(50, 100);
		size.y = size.x;
		refresh();
	}
	
}

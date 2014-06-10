package com.danielschon.flarkolian.sprite;

import com.danielschon.flarkolian.SubTexture;
import com.danielschon.flarkolian.Vec2;

public class Gear extends Sprite 
{

	public Gear(Vec2 position) 
	{
		super(position);
		st = new SubTexture(4,0,0);
		size = new Vec2(256, 256); 
		depth = 1;
		this.refresh();
	}

}

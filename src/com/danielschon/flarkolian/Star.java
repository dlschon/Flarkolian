package com.danielschon.flarkolian;

public class Star extends Sprite
{
	
	private float speed;
	
	public Star(Vec2 position) 
	{
		super(position);
		depth = 0;
		int starType = Util.randInt(0, 2);
		st = new SubTexture(2, starType, 2);
		size.x = Util.randFloat(50, 100);
		size.y = size.x;
		speed = size.x / 10f;
		refresh();
	}
	
	@Override
	public void update()
	{
		loc.y -= speed;
		if (loc.y < -size.y)
		{
			//Recycle as new and different star
			loc.y = Game.heightWindow;
			loc.x = Util.randInt(0, Game.widthWindow);
			speed = size.x / 10f;
			size.x = Util.randFloat(50, 100);
			size.y = size.x;
		}
		super.update();
		
	}
	
}

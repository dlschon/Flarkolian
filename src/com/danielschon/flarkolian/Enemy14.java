package com.danielschon.flarkolian;

/**
 * The 4th enemy of the 1st tier
 */
public class Enemy14 extends Enemy
{
	public Enemy14(Vec2 position) 
	{
		super(position);
		st = new SubTexture(1,4,5);
		stSequence = 
		new SubTexture[]	
			{
				new SubTexture(1,4,5),
				new SubTexture(1,5,5),
				new SubTexture(1,6,5),
				new SubTexture(1,7,5),
				new SubTexture(1,0,4),
				new SubTexture(1,1,4),
				new SubTexture(1,2,4)
			};
		size = new Vec2(200, 200); 
		this.isAnimated = true;
		this.animSpeed = 5;
		this.speed.x = 20;
		this.refresh();
	}
	
	@Override
	public void update()
	{
		loc.x += speed.x;
		if (loc.x > Game.widthWindow - size.x)
		{
			speed.x *= -1;
			loc.x = Game.widthWindow - size.x;
		}
		if (loc.x < 0)
		{
			speed.x *= -1;
			loc.x = 0;
		}
		super.update();
	}

}

package com.danielschon.flarkolian;

/**
 * The 4th enemy of the 1st tier
 */
public class Enemy14 extends Enemy
{
	public Enemy14(int program, Vec2 position) 
	{
		super(program, position);
		st = new SubTexture(1,4,6);
		stSequence = 
		new SubTexture[]	
			{
				new SubTexture(1,4,6),
				new SubTexture(1,5,6),
				new SubTexture(1,6,6),
				new SubTexture(1,7,6),
				new SubTexture(1,0,5),
				new SubTexture(1,1,5),
				new SubTexture(1,2,5)
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

package com.danielschon.flarkolian.sprite;

import com.danielschon.flarkolian.SubTexture;
import com.danielschon.flarkolian.Util;
import com.danielschon.flarkolian.Vec2;

public class ExhaustParticle extends Sprite 
{

	float direction;
	float speed;
	Vec2 initVel = new Vec2(0,0);
	
	Player player;
	
	public ExhaustParticle(Player player) 
	{
		super(new Vec2(player.loc.x + player.size.x * Util.randFloat(.25f, .75f), player.loc.y));
		this.size = new Vec2(16,16);
		this.st = new SubTexture(8, Util.randInt(0, 3), 3);
		this.direction = Util.randFloat((float)(Math.PI * 1.2), (float)(Math.PI * 1.8));
		this.speed = Util.randFloat(5, 10);
		this.player = player;
		this.centered = true;
		this.depth = -1;
		refresh();
	}
	
	@Override
	public void update()
	{
		st.x++;
		if (st.x > 3)
		{
			st.x = 0;
			st.y--;
			if (st.y < 0)
			{
				recycle();
			}
		}
		
		this.loc.x += speed * Math.cos(direction) + initVel.x;
		this.loc.y += speed * Math.sin(direction) + initVel.y;
		
		size.y+=.5;
		size.x+=.5;
		
		super.update();
	}

	private void recycle() 
	{
		this.initVel.x = player.speed;
		this.initVel.y = (player.entering ? -11 : 0);
		this.st.x = Util.randInt(0, 3);
		this.st.y = 3;
		this.direction = Util.randFloat((float)(Math.PI * 1.2), (float)(Math.PI * 1.8));
		this.speed = Util.randFloat(5, 10);
		this.loc.x = player.loc.x + player.size.x * Util.randFloat(.25f, .75f) - initVel.x;
		this.loc.y = player.loc.y;
		this.size.x = 16;
		this.size.y = 16;
		
	}

}

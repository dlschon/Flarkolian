package com.danielschon.flarkolian.sprite;

import com.danielschon.flarkolian.Entity;
import com.danielschon.flarkolian.Game;
import com.danielschon.flarkolian.Input;
import com.danielschon.flarkolian.Sound;
import com.danielschon.flarkolian.SubTexture;
import com.danielschon.flarkolian.Vec2;
import com.danielschon.flarkolian.group.TitleGroup;

public class Title extends Sprite 
{
	TitleGroup group;
	int intro = -1;

	public Title(TitleGroup group, Vec2 position) 
	{
		super(position);
		st = new SubTexture(3,0,0);
		size = new Vec2(Game.widthWindow, Game.widthWindow/4); 
		depth = 1;
		this.group = group;
		this.refresh();
	}
	
	@Override
	public void update()
	{
		//If the screen was tapped and it wasn't on the gear thingy
		if (Input.testRegion(Game.fieldRect) && !Input.testRegion(group.gear.absColRect) && intro == -1)
		{
			Sound.play(Sound.ID_CONFIRM);
			intro = 0;
		}
		
		if (intro >= 0)
		{
			for (Entity e : group.contents)
			{
				if (e instanceof Sprite)
				{
					((Sprite) e).loc.y -= 11;	//11 is just faster than the fastest star, giving the title a foreground effect
					((Sprite) e).refresh();
				}
			}
			intro +=11;
			if (intro >= Game.heightWindow)
			{
				group.begin();
				intro = -2;
			}
		}
		
		super.update();
	}

}

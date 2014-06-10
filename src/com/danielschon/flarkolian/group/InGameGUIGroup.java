package com.danielschon.flarkolian.group;

import com.danielschon.flarkolian.Game;
import com.danielschon.flarkolian.sprite.Keypad;
import com.danielschon.flarkolian.sprite.Scoreboard;

public class InGameGUIGroup extends Group 
{
	private Keypad keypad;
	
	public InGameGUIGroup(Game game)
	{
		super(game);
	}
	
	@Override
	public void initContents()
	{
		this.add(new Scoreboard());
		keypad = new Keypad();
		this.add(keypad);
	}

	public Keypad getKeypad() 
	{
		return keypad;
	}
}

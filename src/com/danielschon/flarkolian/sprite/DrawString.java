package com.danielschon.flarkolian.sprite;

import com.danielschon.flarkolian.Vec2;

public class DrawString extends Sprite 
{
	//Pixel width of each character (max 32)
	static final int[] charWidths = 
		{20,19,20,21,23,21,20,20,20,20,26,22,22,22,19,19,23,22,5,19,22,19,30,24,23,22,24,22,23,22,22,25,32,24,26,21};

	String text;
	/**
	 * An array of all the TextChars that make up this TextBox
	 */
	DrawChar[] chars = null;
	
	/**
	 * The space between characters
	 */
	public float padding = .0625f;
	
	/**
	 * The space a space takes
	 */
	public float spaceWidth = .5f;
	
	public DrawString(Vec2 position, Vec2 size, String text) 
	{
		super(position);
		this.size = size;
		this.setText(text);
	}
	
	public DrawString(Vec2 position, String text) 
	{
		super(position);
		this.size = new Vec2(32, 32);
		this.setText(text);
	}
	
	/**
	 * Sets the text being displayed to a String and creates the TextChars necessary to do so
	 * @param text
	 */
	public void setText(String text) 
	{
		//Filter out all chars that aren't numbers or digits
		String nText = "";
		int spaces = 0;
		for (char c : text.toCharArray())
		{
			if (Character.isLetterOrDigit(c) || Character.isWhitespace(c))
				nText += Character.toUpperCase(c);
			if (Character.isWhitespace(c))
				spaces++;
		}
		text = nText;
		this.text = text;
		
		chars = new DrawChar[text.length() - spaces];
		float offset = 0;
		int index = 0;
		//Create a DrawChar for each char in text
		for (int i = 0; i < text.length(); i++)
		{
			char c = text.charAt(i);
			if (Character.isWhitespace(c))
			{
				offset += this.size.x * this.spaceWidth;
			}
			else
			{
				chars[index] = new DrawChar(new Vec2(this.loc.x + offset, this.loc.y), c, this.size);
				index++;	//we use this variable to avoid problems with spaces
				offset += this.size.x * getCharWidth(c) + padding * this.size.x;
			}
		}
	}
	
	/**
	 * Sets the size of the Text
	 */
	public void setSize(Vec2 size)
	{
		this.size = size;
		//Resize each DrawChar
		refresh();
	}
	
	/**
	 * Sets the position of the Text
	 */
	public void setPosition(Vec2 position)
	{
		this.loc = position;
		//Move each DrawChar
		refresh();
	}
	
	/**
	 * Just call .draw for every DrawChar
	 */
	@Override
	public void draw(float[] mvpMatrix)
	{
		if (chars != null && this.visible)
		{
			for (DrawChar dc : chars)
			{
				dc.draw(mvpMatrix);
			}
		}
	}
	
	/**
	 * Just call .refresh for every DrawChar
	 */
	@Override
	public void refresh()
	{
		float offset = 0;
		int index = 0;
		for (int i = 0; i < text.length(); i++)
		{
			char c = text.charAt(i);
			if (Character.isWhitespace(c))
			{
				offset += this.size.x * this.spaceWidth;
			}
			else
			{
				chars[index].size = this.size;
				chars[index].loc = new Vec2(this.loc.x + offset, this.loc.y);
				index++;	//we use this variable to avoid problems with spaces
				offset += this.size.x * getCharWidth(c) + padding * this.size.x;
			}
		}
		if (chars != null)
		{
			for (DrawChar dc : chars)
			{
				dc.refresh();
			}
		}
	}

	/**
	 * Returns the width of a character (from 0 to 1)
	 * @param c
	 * @return
	 */
	public static float getCharWidth(char c)
	{
		byte index = 0;
		if (48 <= c && c <=57) //It's a number
		{
			index = (byte) (c - 48);
		}
		if (65 <= c && c <=90) //It's a letter
		{
			index = (byte) (c - 55);
		}
		return ((float)charWidths[index] / 32f);
	}
	
	/**
	 * Returns the height of a character (from 0 to 1)
	 * @param c
	 * @return
	 */
	public static float getCharHeight(char c)
	{
		return 0.78125f; //That's right they're ALL .78125f!!!!
	}

}

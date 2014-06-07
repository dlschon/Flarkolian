package com.danielschon.flarkolian.sprite;

import com.danielschon.flarkolian.SubTexture;
import com.danielschon.flarkolian.Vec2;

public class DrawChar extends Sprite 
{

	public DrawChar(Vec2 position, char text, Vec2 size) 
	{
		super(position);
		this.st = getSubTexture(text);
		this.size = size;
	}
	
	public static SubTexture getSubTexture(char c)
	{
		c = Character.toUpperCase(c);
		SubTexture st = new SubTexture(5,0,0);
		switch (c)
		{
		case('U'):
		{
			st.x = 0;
			st.y = 0;
			break;
		}
		case('V'):
		{
			st.x = 1;
			st.y = 0;
			break;
		}
		case('W'):
		{
			st.x = 2;
			st.y = 0;
			break;
		}
		case('X'):
		{
			st.x = 3;
			st.y = 0;
			break;
		}
		case('Y'):
		{
			st.x = 4;
			st.y = 0;
			break;
		}
		case('Z'):
		{
			st.x = 5;
			st.y = 0;
			break;
		}
		
		case('O'):
		{
			st.x = 0;
			st.y = 1;
			break;
		}
		case('P'):
		{
			st.x = 1;
			st.y = 1;
			break;
		}
		case('Q'):
		{
			st.x = 2;
			st.y = 1;
			break;
		}
		case('R'):
		{
			st.x = 3;
			st.y = 1;
			break;
		}
		case('S'):
		{
			st.x = 4;
			st.y = 1;
			break;
		}
		case('T'):
		{
			st.x = 5;
			st.y = 1;
			break;
		}
		
		case('I'):
		{
			st.x = 0;
			st.y = 2;
			break;
		}
		case('J'):
		{
			st.x = 1;
			st.y = 2;
			break;
		}
		case('K'):
		{
			st.x = 2;
			st.y = 2;
			break;
		}
		case('L'):
		{
			st.x = 3;
			st.y = 2;
			break;
		}
		case('M'):
		{
			st.x = 4;
			st.y = 2;
			break;
		}
		case('N'):
		{
			st.x = 5;
			st.y = 2;
			break;
		}
		
		case('C'):
		{
			st.x = 0;
			st.y = 3;
			break;
		}
		case('D'):
		{
			st.x = 1;
			st.y = 3;
			break;
		}
		case('E'):
		{
			st.x = 2;
			st.y = 3;
			break;
		}
		case('F'):
		{
			st.x = 3;
			st.y = 3;
			break;
		}
		case('G'):
		{
			st.x = 4;
			st.y = 3;
			break;
		}
		case('H'):
		{
			st.x = 5;
			st.y = 3;
			break;
		}
		
		case('6'):
		{
			st.x = 0;
			st.y = 4;
			break;
		}
		case('7'):
		{
			st.x = 1;
			st.y = 4;
			break;
		}
		case('8'):
		{
			st.x = 2;
			st.y = 4;
			break;
		}
		case('9'):
		{
			st.x = 3;
			st.y = 4;
			break;
		}
		case('A'):
		{
			st.x = 4;
			st.y = 4;
			break;
		}
		case('B'):
		{
			st.x = 5;
			st.y = 4;
			break;
		}
		
		case('0'):
		{
			st.x = 0;
			st.y = 5;
			break;
		}
		case('1'):
		{
			st.x = 1;
			st.y = 5;
			break;
		}
		case('2'):
		{
			st.x = 2;
			st.y = 5;
			break;
		}
		case('3'):
		{
			st.x = 3;
			st.y = 5;
			break;
		}
		case('4'):
		{
			st.x = 4;
			st.y = 5;
			break;
		}
		case('5'):
		{
			st.x = 5;
			st.y = 5;
			break;
		}
		default:
		{
			break;
		}
		}
		return st;
	}

}

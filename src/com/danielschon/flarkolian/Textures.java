package com.danielschon.flarkolian;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import static android.opengl.GLES20.*;

public class Textures 
{
	//The color of the textures. lol jk
	private static int NUMBER_OF_TEXTURES = 9;
		
	//Used by OpenGL to store textures
	private static int[] textures = new int[NUMBER_OF_TEXTURES];
	
	//Number of images per row in each sheet
	public static int[] sheetSizes = 
		{
			1,	//the player sheet
			8,	//the enemies sheet
			4,	//the misc sheet
			1,  //The title
			1,  //The gear
			6,  //Text char sheet
			1,  //The scoreboard
			2,  //The arrow keys
			4,	//The flame particles
		};
	
	//Number of texels in each subimage
	public static boolean[] isPadded = 
		{
			false,	//player
			true,	//enemies
			false,	//misc
			false,	//title
			false,	//gear
			true,	//char
			false,	//scoreboard
			false,	//keypad
			false, 	//flame
			
		};
	
	public static void createTextures(Context context)
	{
		assert(sheetSizes.length == NUMBER_OF_TEXTURES && isPadded.length == NUMBER_OF_TEXTURES);
		
		glGenTextures(NUMBER_OF_TEXTURES, textures, 0);
		create(context, R.drawable.player, 0, GL_TEXTURE0);
		create(context, R.drawable.enemies, 1, GL_TEXTURE1);
		create(context, R.drawable.misc, 2, GL_TEXTURE2);
		create(context, R.drawable.title, 3, GL_TEXTURE3);
		create(context, R.drawable.gear, 4, GL_TEXTURE4);
		create(context, R.drawable.chars, 5, GL_TEXTURE5);
		create(context, R.drawable.scoreboard, 6, GL_TEXTURE6);
		create(context, R.drawable.keypad, 7, GL_TEXTURE7);
		create(context, R.drawable.flames, 8, GL_TEXTURE8);
		
	}
	
	private static void create(Context context, int resource, int index, int tex)
	{
		//This is only temporary 
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resource);

		// Bind texture to the array index
		glActiveTexture(tex);
		glBindTexture(GL_TEXTURE_2D, textures[index]);
		 
		// Set filtering (Used to resize image resolution)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		 
        // Load the bitmap into the bound texture.
        GLUtils.texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);
 
        // We are done using the bitmap so we recycle it.
        bitmap.recycle();
        
	}

	public static int getBitmap(SubTexture st)
	{
		return textures[st.sheet];
	}
}

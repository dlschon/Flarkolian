package com.danielschon.flarkolian;

import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import static android.opengl.GLES20.*;

public class Textures 
{
	//Used by OpenGL to store textures
	private static int[] textures = new int[2];
	
	//Number of images per row in each sheet
	public static float[] sheetsizes = 
		{
			1f, //the player sheet
			8f	//the enemies sheet
		};
	
	public static void createTextures(Context context)
	{
		glGenTextures(2, textures, 0);
		create(context, R.drawable.player, 0, GL_TEXTURE0);
		create(context, R.drawable.enemiess, 1, GL_TEXTURE1);
		
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
 
        // We are done using the bitmap so we should recycle it.
        bitmap.recycle();
        
	}
	
	public static int getBitmap(SubTexture st)
	{
		return textures[st.sheet];
	}
}

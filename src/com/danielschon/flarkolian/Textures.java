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
	private static int[] textures = new int[1];
	//Used by this class to easily locate texture IDs
	private static HashMap<BmpId, Integer> bitmaps = new HashMap<BmpId, Integer>();
	
	public static void createTextures(Context context)
	{
		int[] textures = new int[1];
		glGenTextures(1, textures, 0);
		create(context, BmpId.ICON, 0);
		
	}
	
	private static void create(Context context, BmpId id, int index)
	{
		//This is only temporary
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
				
		// Bind texture to texturename
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, textures[index]);
		 
		// Set filtering (Used to resize image resolution)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		 
        // Load the bitmap into the bound texture.
        GLUtils.texImage2D(GL_TEXTURE_2D, 0, bitmap, 0);
 
        // We are done using the bitmap so we should recycle it.
        bitmap.recycle();
        
		bitmaps.put(BmpId.ICON, index);
	}
	
	public static int getBitmap(BmpId id)
	{
		return bitmaps.get(id);
	}
}

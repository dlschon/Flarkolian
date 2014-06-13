package com.danielschon.flarkolian.sprite;

import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_UNSIGNED_SHORT;
import static android.opengl.GLES20.glDisableVertexAttribArray;
import static android.opengl.GLES20.glDrawElements;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform1i;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.glVertexAttribPointer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import android.graphics.Rect;
import android.graphics.RectF;
import android.opengl.GLES20;
import android.util.Log;

import com.danielschon.flarkolian.Entity;
import com.danielschon.flarkolian.Game;
import com.danielschon.flarkolian.Rectangle;
import com.danielschon.flarkolian.SubTexture;
import com.danielschon.flarkolian.Textures;
import com.danielschon.flarkolian.Vec2;

/**
 * An entity that can be drawn. Holds information for location, size, and texture
 */
public abstract class Sprite extends Entity
{
	
    //Basic sprite information
	public Vec2 loc = new Vec2(0, 0);
	public Vec2 size = new Vec2(200, 200); 
	public int depth = 0;	//Sprites with a lesser depth are drawn first and thus behind others
	public SubTexture st;
	public SubTexture[] stSequence;
	public boolean centered = false;
	
    //Some buffers
	private FloatBuffer vertexBuffer;
	private ShortBuffer drawListBuffer;
	public FloatBuffer uvBuffer;
	
	//Animation info
	public boolean isAnimated = false;
	private int frameCount = 0;
	private int currentFrame = 0;
	public int animSpeed = 0;	//The number of frames it takes to change animation
	
	//Other info
	public boolean visible = true;
	
	/**
	 * Collision rectangle that has been translated to the Sprites location
	 */
	public Rectangle absColRect = new Rectangle();
	/**
	 * Collision rectangle that has not bee translated
	 */
	public Rectangle relColRect = new Rectangle();
	
    //number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;
    float vertexCoords[] = 
    {   	// in counterclockwise order:
            loc.x, loc.y, 0.0f, 		// top-left
            loc.x, loc.y + size.y, 0.0f, 		// bottom-left
            loc.x + size.x, loc.y + size.y, 0.0f, 		// bottom-right
            loc.x + size.x, loc.y, 0.0f 			// top-right
    };
    
    //Texcoords
    float[] uvs = new float[] 
    {
    	0.0f, 0.0f,	//top-left
        0.0f, -1.0f, //bottom-left
        1.0f, -1.0f, //bottom-right
        1.0f, 0.0f  //top-right
    };
    
    float[] rotationMatrix = new float[16];
    
    private short drawOrder[] = { 0, 1, 2, 0, 2, 3 }; // order to draw vertices

    int vertexCount = vertexCoords.length/COORDS_PER_VERTEX; //Vertex count is the array divided by the size of the vertex ex. (x,y) or (x,y,z) 
    int vertexStride = COORDS_PER_VERTEX * 4;                //4 are how many bytes in a float
    
    /**
     * Subclasses must call .refresh() at the end of their constructors
     * @param program
     * @param st 
     */
    public Sprite(Vec2 position) 
    {
    	this.loc = position;
        
    	if (relColRect == null)
    		relColRect = new Rectangle(new Vec2(0,0), this.size);
    	
        // initialize byte buffer for the draw list
        ByteBuffer dlb = ByteBuffer.allocateDirect(
        // (# of coordinate values * 2 bytes per short)
                drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);
        
        // The vertex buffer.
        ByteBuffer vb = ByteBuffer.allocateDirect(vertexCoords.length * 4);
        vb.order(ByteOrder.nativeOrder());
        vertexBuffer = vb.asFloatBuffer();
        
        // The texture buffer
    	ByteBuffer bb = ByteBuffer.allocateDirect(uvs.length * 4);
    	bb.order(ByteOrder.nativeOrder());
    	uvBuffer = bb.asFloatBuffer();
    }

	@Override
	public void update() 
	{
		//Do animations
		if (isAnimated)
		{
			if (stSequence == null)
			{
				Log.e("Animation", "stSequence must not be null if isAnimated is true");
			}
			else
			{
				frameCount++;
				if (frameCount >= animSpeed)
				{
					frameCount = 0;
					currentFrame++;
					if (currentFrame >= stSequence.length)
					{
						currentFrame = 0;
					}
					st = stSequence[currentFrame];
				}
			}
		}
		
		refresh();
	}

	public void draw(float[] mvpMatrix) 
	{
		if (visible)
		{
			// get handle to vertex shader's vPosition member
			int mPositionHandle = glGetAttribLocation(Game.program, "vPosition");

	    	// Enable a handle to the triangle vertices
	    	glEnableVertexAttribArray(mPositionHandle);

	    	// Prepare the vertex coordinate data
	    	glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
	                                 GL_FLOAT, false,
	                                 vertexStride, vertexBuffer);
	    	
	    
        	// Get handle to texture coordinates location
        	int texCoordLoc = glGetAttribLocation(Game.program, "a_texCoord" );

        	// Prepare the texturecoordinates
      		GLES20.glVertexAttribPointer( texCoordLoc, 2, 
        		GL_FLOAT, false,
        		0, uvBuffer);

        	
  			// Enable generic vertex attribute array
  			glEnableVertexAttribArray(texCoordLoc);

  			// get handle to shape's transformation matrix
  			int mvpMatrixHandle = glGetUniformLocation(Game.program, "uMVPMatrix");
  			
	    	// Pass the projection and view transformation to the shader
	    	glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0);
	    	
	    	// Get handle to textures locations
        	int samplerLoc = glGetUniformLocation (Game.program, "s_texture" );
	    
        	//Set the sampler texture unit to the id of our desired texture
        	glUniform1i(samplerLoc, st.sheet);
        	
        	// Draw the square
	    	glDrawElements(GL_TRIANGLES, drawOrder.length, GL_UNSIGNED_SHORT, drawListBuffer);

	    	// Disable vertex attrib arrays
	    	glDisableVertexAttribArray(mPositionHandle);
	    	glDisableVertexAttribArray(texCoordLoc);
		}
	}
	
	/**
	 * Called to update the buffers to match new information
	 */
	public void refresh()
    {
		//Update abssolute collision rectangle
		absColRect = relColRect.addVec2(this.loc);
				
		//set texCoords
		float isize = 1f / Textures.sheetSizes[st.sheet];	//The size (from 0f to 1f) of each image
		float padding = 0;
		if (Textures.isPadded[st.sheet])
			padding = isize / 64f;	//1 pixel off each side to avoid graphical errors
    	
		//update the texcoord array
    		    	uvs[0] = st.x * isize + padding; uvs[1] =  -(st.y * isize + padding);	//top-left
    		    	uvs[2] = st.x * isize + padding; uvs[3] = -((st.y + 1) * isize - padding); //bottom-left
    		    	uvs[4] = (st.x + 1) * isize - padding; uvs[5] = -((st.y + 1) * isize - padding); //bottom-right
    		    	uvs[6] = (st.x + 1) * isize - padding; uvs[7] = -(st.y  * isize + padding); //top-right

    	
    	uvBuffer.put(uvs);
    	uvBuffer.position(0);
		
		 //Update the vertexcoord array
				if (!centered)//non-centered	
			    {   	// in counterclockwise order:
					vertexCoords[0] = loc.x; vertexCoords[1] = loc.y; vertexCoords[2] = 0.0f; 		// top-left
					vertexCoords[3] = loc.x; vertexCoords[4] = loc.y + size.y; vertexCoords[5] = 0.0f; 		// bottom-left
					vertexCoords[6] = loc.x + size.x; vertexCoords[7] = loc.y + size.y; vertexCoords[8] = 0.0f; 		// bottom-right
					vertexCoords[9] = loc.x + size.x; vertexCoords[10] = loc.y; vertexCoords[11] = 0.0f ;			// top-right
			    }
				else//centered	
				{   	// in counterclockwise order:
					vertexCoords[0] = loc.x - size.x/2; vertexCoords[1] = loc.y - size.y/2; vertexCoords[2] = 0.0f; 		// top-left
					vertexCoords[3] = loc.x - size.x/2; vertexCoords[4] = loc.y + size.y/2; vertexCoords[5] = 0.0f; 		// bottom-left
					vertexCoords[6] = loc.x + size.x/2; vertexCoords[7] = loc.y + size.y/2; vertexCoords[8] = 0.0f; 		// bottom-right
					vertexCoords[9] = loc.x + size.x/2; vertexCoords[10] = loc.y - size.y/2; vertexCoords[11] = 0.0f ;			// top-right
				}
		
        vertexBuffer.put(vertexCoords);
        vertexBuffer.position(0);
    }


}

package com.danielschon.flarkolian;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import android.opengl.GLES20;
import android.util.Log;

import static android.opengl.GLES20.*;

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
	
    //Some buffers
	private FloatBuffer vertexBuffer;
	private ShortBuffer drawListBuffer;
	public FloatBuffer uvBuffer;
	
	//Animation info
	public boolean isAnimated = false;
	private int frameCount = 0;
	private int currentFrame = 0;
	public int animSpeed = 0;	//The number of frames it takes to change animation
	
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

	    // Disable vertex arrays
	    glDisableVertexAttribArray(mPositionHandle);
	    glDisableVertexAttribArray(texCoordLoc);
	}
	
	/**
	 * Called to update the buffers to match new information
	 */
	public void refresh()
    {
		//set texCoords
		float isize = 1f / Textures.sheetsizes[st.sheet];	//The size (from 0f to 1f) of each image
		float padding = isize / 64f;	//1 pixel off each size to avoid graphical errors
    	uvs = new float[] 
    		    {
    		    	st.x * isize + padding, -(st.y * isize + padding),	//top-left
    		    	st.x * isize + padding, -((st.y + 1) * isize - padding), //bottom-left
    		    	(st.x + 1) * isize - padding, -((st.y + 1) * isize - padding), //bottom-right
    		    	(st.x + 1) * isize - padding, -(st.y  * isize + padding) //top-right
    		    };
    	
    	uvBuffer.position(0);
    	uvBuffer.put(uvs);
    	uvBuffer.position(0);
		
		vertexCoords = new float[]
			    {   	// in counterclockwise order:
			            loc.x, loc.y, 0.0f, 		// top-left
			            loc.x, loc.y + size.y, 0.0f, 		// bottom-left
			            loc.x + size.x, loc.y + size.y, 0.0f, 		// bottom-right
			            loc.x + size.x, loc.y, 0.0f 			// top-right
			    };
		
        vertexBuffer.position(0);
        vertexBuffer.put(vertexCoords);
        vertexBuffer.position(0);
    }


}

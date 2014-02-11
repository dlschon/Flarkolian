package com.danielschon.flarkolian;



import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.*;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;
import android.view.MotionEvent;

public class Game implements GLSurfaceView.Renderer{

	// some fancy shaders
		public final static String vertexShaderCode =
				"uniform mat4 uMVPMatrix;" +
				"attribute vec4 vPosition;" +
				"attribute vec2 a_texCoord;" +
		        "varying vec2 v_texCoord;" +
			    "void main() {" +
			    "  gl_Position = uMVPMatrix * vPosition;" +
			    "  v_texCoord = a_texCoord;" +
			    "}";

		public final static String fragmentShaderCode =
			  "precision mediump float;" +
			  "varying vec2 v_texCoord;" +
			  "uniform vec4 vColor;" +
			  "uniform sampler2D s_texture;" +
			  "void main() {" +
		      "  gl_FragColor = texture2D( s_texture, v_texCoord );" +
			  "}";

	    //OpenGL ES program
	    public int program;
	    
	//Dimensions of the actual screen
	public static int widthActual = 1920;
	public static int heightActual = 1080;
	
	//Dimensions of the window, scaled to be proportional to different screen sizes
	public static int widthWindow = 1920;
	public static int heightWindow = 1080;
	
	//Whether or not the screen is currently being pressed
	public static boolean pressState = false;
	public static long pressTime;
	public static long releaseTime;
	public static MotionEvent press;
	
	private ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	
	private Player player;
	
	private float[] projectionMatrix = new float[16];
	private float[] viewMatrix = new float[16];
	private float[] mvpMatrix = new float[16]; //Model View Projection, not Most Valuable Player

	private Context context;
	private GLSurfaceView sv;
	
	private int frameCount = 0;
	private long lastTime = 0;
	private long time = 0;
	
	public Game(Context context, GLSurfaceView sv)
	{
		super();
		this.context = context;
		this.sv = sv;
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) 
	{
		// Set the background frame color
        glClearColor(.2f, .2f, .2f, 1.0f);

        //Load shaders
    	int vertexShader = Game.loadShader(GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = Game.loadShader(GL_FRAGMENT_SHADER, fragmentShaderCode);

        //Create program
        program = glCreateProgram();         // create empty OpenGL ES Program
        glAttachShader(program, vertexShader);   // add the vertex shader to program
        glAttachShader(program, fragmentShader); // add the fragment shader to program
        glLinkProgram(program);    // creates OpenGL ES program executables
        
        Textures.createTextures(context);
        
        player = new Player(program, new Vec2(100,50));
        addSprite(player);
        
        //This only exists to test multitexturing 
        Player2 player2 = new Player2(program, new Vec2(100,500));
        addSprite(player2);
        
	}
	
	/**
	 * The main game loop. Handles updating and drawing
	 */
	@Override
	public void onDrawFrame(GL10 gl) 
	{

		//fps check
		frameCount++;
		time = System.currentTimeMillis();
		if (time - lastTime >= 1000)
		{
			Log.i("fps:", String.valueOf(frameCount));
			frameCount = 0;
			lastTime = time;
		}
		
		  /////////
		 //INPUT//
		/////////
		
		
		
		  //////////
		 //UPDATE//
		//////////

		//Update entities
        for (Entity entity : entities)
        {
        	entity.update();
        }
        
          ////////
         //DRAW//
        ////////
        
		// Redraw background color
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        
        // Draw sprites
        for (Sprite sprite : sprites)
        {
        	sprite.draw(mvpMatrix);
        }
        
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) 
	{
		float ratio = (float)width/height;
		Log.i("width", String.valueOf(width));
		Log.i("height", String.valueOf(height));
		Log.i("ratio", String.valueOf(ratio));
		
		Game.widthActual = width;
		Game.heightActual = height;
		
		Game.widthWindow = (int) (ratio * 1080);
		Game.heightWindow = 1080;
		
		glViewport(0, 0, Game.widthActual, Game.heightActual);

	    // this projection matrix is applied to object coordinates
	    // in the onDrawFrame() method
	    Matrix.frustumM(projectionMatrix, 0, 0, ratio * 1080, 0, 1080, 3, 7);
	    // Set the camera position (View matrix)
        Matrix.setLookAtM(viewMatrix, 0, 0, 0, 3f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        // Calculate the projection and view transformation
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, viewMatrix, 0);

	}
	
	public static int loadShader(int type, String shaderCode)
	{

	    // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
	    // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
	    int shader = glCreateShader(type);

	    // add the source code to the shader and compile it
	    glShaderSource(shader, shaderCode);
	    glCompileShader(shader);

	    return shader;
	}

	/**
	 * Add a sprite to both the sprites and entities lists
	 * @param s
	 */
	public void addSprite(Sprite s)
	{
		sprites.add(s);
		entities.add(s);
	}
	
	/**
	 * Add an entity to the entities list
	 * @param s
	 */
	public void addEntity(Entity e)
	{
		entities.add(e);
	}
	
	public void processTouchEvent(MotionEvent e) 
	{
		press = e;
		if (e.getAction() == MotionEvent.ACTION_DOWN)
		{
			pressState = true;
			pressTime = System.currentTimeMillis();
		}
		if (e.getAction() == MotionEvent.ACTION_UP || e.getAction() == MotionEvent.ACTION_CANCEL)
		{
			pressState = false;
			releaseTime = System.currentTimeMillis();
		}
	}

}

package com.danielschon.flarkolian;



import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_DEPTH_BUFFER_BIT;
import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_VERTEX_SHADER;
import static android.opengl.GLES20.glAttachShader;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glCompileShader;
import static android.opengl.GLES20.glCreateProgram;
import static android.opengl.GLES20.glCreateShader;
import static android.opengl.GLES20.glLinkProgram;
import static android.opengl.GLES20.glShaderSource;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glViewport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Color;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;
import android.view.MotionEvent;

import com.danielschon.flarkolian.activity.MainActivity;
import com.danielschon.flarkolian.group.Group;
import com.danielschon.flarkolian.group.StarGroup;
import com.danielschon.flarkolian.group.TitleGroup;
import com.danielschon.flarkolian.sprite.Player;
import com.danielschon.flarkolian.sprite.Sprite;

public class Game implements GLSurfaceView.Renderer
{

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

	//Singleton instance
	public static Game instance;	
	
	//OpenGL ES program
	public static int program;
	    
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
	
	//Background color
	private int clearColor = 0x515151;
	
	//Used to add entities and sprites in the main loop, to avoid concurrent modification
	Queue<Entity> addLaterE = new LinkedList<Entity>();
	Queue<Sprite> addLaterS = new LinkedList<Sprite>();
	
	Queue<Entity> removeLater = new LinkedList<Entity>();
	
	//Groups
	StarGroup starGroup;
	TitleGroup titleGroup;
	
	public Game(Context context, GLSurfaceView sv)
	{
		super();
		this.context = context;
		instance = this;
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) 
	{
		// Set the background frame color
        glClearColor((float) Color.red(clearColor) / 255f, (float) Color.blue(clearColor) / 255f, (float) Color.green(clearColor) / 255f, 1f);

        //Load shaders
    	int vertexShader = Game.loadShader(GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = Game.loadShader(GL_FRAGMENT_SHADER, fragmentShaderCode);

        //Create program
        program = glCreateProgram();         // create empty OpenGL ES Program
        glAttachShader(program, vertexShader);   // add the vertex shader to program
        glAttachShader(program, fragmentShader); // add the fragment shader to program
        glLinkProgram(program);    // creates OpenGL ES program executables
        
        Textures.createTextures(context);
        
        //Alpha blending
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        
        // Add program to OpenGL ES environment
	    glUseProgram(program);
	       
	    //Add the fpslogger
	    addEntity(new FPSLogger());
        
        /*//Add an enemy
        Enemy enemy = new Enemy14(new Vec2(100,500));
        addSprite(enemy);*/
	    
	    titleGroup = new TitleGroup(this, new TitleCallback(){

	    	//Called when the user chooses to begin the game
			@Override
			public void onBegin(Game game) 
			{
				game.begin();
			}
			
	    });
	    titleGroup.deploy();
	  
	    starGroup = new StarGroup(this);
	    starGroup.deploy();
        
	}

	/**
	 * Called when the user chooses to begin the game
	 */
	protected void begin() 
	{
		titleGroup.destroy();
		player = new Player(true);
		this.addEntity(player);
	}

	/**
	 * The main game loop. Handles adding/removing, updating, and drawing
	 */
	@Override
	public void onDrawFrame(GL10 gl) 
	{
		if (!((MainActivity) context).paused)
		{
			boolean spritesChanged = false;
			//Add and remove sprites and entities
			if (addLaterE.size() > 0)
			{
				while (addLaterE.size() > 0)
				{
					Entity e = addLaterE.poll();
					//Add entity to list of entities
					entities.add(e);
					//Also add it to a list of Sprites if it's a sprite
					if (e instanceof Sprite)
					{
						sprites.add((Sprite) e);
						spritesChanged = true;
					}
				}	
			}
			
			if (removeLater.size() > 0)
			{
				while (removeLater.size() > 0)
				{
					Entity e = removeLater.poll();
					if (entities.contains(e))
						entities.remove(e);
					if (sprites.contains(e))
					{
						spritesChanged = true;
						sprites.remove(e);
					}
				}
			}
			if (spritesChanged)
				this.depthSort();
        
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
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) 
	{
		if (!((MainActivity) context).paused)
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
	 * Queues up an entity to be added to the list
	 * @param s
	 */
	public void addEntity(Entity e)
	{
		addLaterE.add(e);
	}	
	
	/**
	 * Queues up an entity to be removed from the list
	 * @param s
	 */
	public void removeEntity(Entity e)
	{
		removeLater.add(e);
	}	
	
	/**
	 * Sorts the sprites arraylist such that lower depth sprites are rendered first
	 */
	private void depthSort() 
	{
		Collections.sort(sprites, new DepthComparator());
	}
	
	/**
	 * Handles touch events sent from MySurfaceView
	 * @param e
	 */
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
	
	/**
	 * Comparator to sort sprites by depth and subtexture
	 */
	class DepthComparator implements Comparator<Sprite>
	{
		@Override
		public int compare(Sprite arg0, Sprite arg1) 
		{
			return (arg0.depth < arg1.depth) ? -1 : (arg0.depth > arg1.depth) ? 1 : arg0.equals(arg1) ? 0 : -1;
		}
		
	}

}

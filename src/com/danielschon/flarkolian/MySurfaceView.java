package com.danielschon.flarkolian;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class MySurfaceView extends GLSurfaceView implements SurfaceHolder.Callback
{
	Game renderer;
	
	public MySurfaceView(Context context) 
	{
		super(context);
		setEGLContextClientVersion(2);
		renderer = new Game(context);
		setRenderer(renderer);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent e) 
	{
	        renderer.processTouchEvent(e);
	        return true;
	}
}

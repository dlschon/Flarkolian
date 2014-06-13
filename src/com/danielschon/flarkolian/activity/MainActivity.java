package com.danielschon.flarkolian.activity;

import com.danielschon.flarkolian.MySurfaceView;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.KeyEvent;

public class MainActivity extends Activity {

	MySurfaceView surfaceView;
	public boolean paused;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		surfaceView = new MySurfaceView(this);
		setContentView(surfaceView);
	}
	
	@Override
	protected void onPause()
	{
		this.paused = true;
		Log.i("fdas", "Game paused");
		super.onPause();
	}
	
	@Override
	protected void onResume()
	{
		this.paused = false;
		Log.i("fdas", "Game resume");
		super.onResume();
	}
	
	protected void onStop()
	{
		Log.i("fdas", "Game stopped");
		super.onStop();
	}
	
	protected void onStart()
	{
		Log.i("fdas", "Game started");
		super.onStart();
	}
}

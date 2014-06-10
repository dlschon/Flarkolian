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
		super.onPause();
	}
	
	@Override
	protected void onResume()
	{
		this.paused = false;
		super.onResume();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
		Log.d("Key down", String.valueOf(keyCode));
		return true;
	}
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		return true;
	}
}

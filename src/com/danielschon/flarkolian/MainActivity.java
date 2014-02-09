package com.danielschon.flarkolian;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {

	MySurfaceView surfaceView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		surfaceView = new MySurfaceView(this);
		setContentView(surfaceView);
	}
}

package com.danielschon.flarkolian;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

public class Sound 
{
	public static final int ID_BEEP = 0;
	public static final int ID_CONFIRM = 1;
	public static final int ID_EXPLOSION = 2;
	public static final int ID_FATAL = 3;
	public static final int ID_HEALTH = 4;
	public static final int ID_HIGHBEEP = 5;
	public static final int ID_HIT = 6;
	public static final int ID_LASER = 7;
	public static final int ID_LASER2 = 8;
	public static final int ID_LASER3 = 9;
	public static final int ID_LIFE = 10;
	public static final int ID_WHOOP = 11;
	public static final int ID_ZAP = 12;
	
	static SoundPool pool;
	static int[] soundIds;

	public static void createSounds(Context context) 
	{
		soundIds = new int[13];
		pool = new SoundPool(13, AudioManager.STREAM_MUSIC, 0);
		
		soundIds[ID_BEEP] = pool.load(context, R.raw.beep, 1);
		soundIds[ID_CONFIRM] = pool.load(context, R.raw.confirm, 1);
		soundIds[ID_EXPLOSION] = pool.load(context, R.raw.explosion, 1);
		soundIds[ID_CONFIRM] = pool.load(context, R.raw.confirm, 1);
		soundIds[ID_FATAL] = pool.load(context, R.raw.fatal, 1);
		soundIds[ID_HEALTH] = pool.load(context, R.raw.health, 1);
		soundIds[ID_HIGHBEEP] = pool.load(context, R.raw.highbeep, 1);
		soundIds[ID_HIT] = pool.load(context, R.raw.hit, 1);
		soundIds[ID_LASER] = pool.load(context, R.raw.laser, 1);
		soundIds[ID_LASER2] = pool.load(context, R.raw.laser2, 1);
		soundIds[ID_LASER3] = pool.load(context, R.raw.laser3, 1);
		soundIds[ID_LIFE] = pool.load(context, R.raw.life, 1);
		soundIds[ID_WHOOP] = pool.load(context, R.raw.whoop, 1);
		soundIds[ID_ZAP] = pool.load(context, R.raw.zap, 1);
	}
	
	public static void play(int soundId)
	{
		if (pool == null)
		{
			Log.e("Sound error", ".createSounds() should be called before .play()");
			return;
		}
		pool.play(soundIds[soundId], 1, 1, 1, 0, 1.0f);
	}
}

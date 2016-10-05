package com.sootnoon.android.bicyclebell;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.SeekBar;

public class BicycleBell extends Activity {
	private static final String tag = "BicycleBell";

	private AudioManager audioManager;
	private SoundPool soundPool;
	private boolean isSoundPoolLoaded = false;
	private int bellId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		// audio
		audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
		int maxVolume = audioManager
				.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		int currentVolume = audioManager
				.getStreamVolume(AudioManager.STREAM_MUSIC);

		// seekbar
		SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar1);
		seekBar.setMax(maxVolume);
		seekBar.setProgress(currentVolume);
		seekBar.setOnSeekBarChangeListener(seekBarListener);

		// view1 big
		View view1 = findViewById(R.id.imageView1);
		view1.setOnTouchListener(view1TouchListener);

		// view2 small
		View view2 = findViewById(R.id.imageView2);
		view2.setOnTouchListener(view2TouchListener);

		// soundPool
		soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		soundPool.setOnLoadCompleteListener(soudPoolLoadCompleteListener);
		bellId = soundPool.load(this, R.raw.bicycle_bell, 1);
		Log.d(tag, "onCreate = " + isSoundPoolLoaded);
	}

	// seekbar
	private SeekBar.OnSeekBarChangeListener seekBarListener = new SeekBar.OnSeekBarChangeListener() {

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// do nothing
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// do nothing
		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			audioManager
					.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
		}
	};

	// soundPool
	OnLoadCompleteListener soudPoolLoadCompleteListener = new OnLoadCompleteListener() {
		@Override
		public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
			isSoundPoolLoaded = true;
			Log.d(tag, "load = " + isSoundPoolLoaded);
		}
	};

	// view1 touch
	private View.OnTouchListener view1TouchListener = new View.OnTouchListener() {

		@SuppressLint("ClickableViewAccessibility")
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			Log.d(tag, "view1 = " + isSoundPoolLoaded);
			if(isSoundPoolLoaded) {
				soundPool.play(bellId, 1, 1, 0, 0, 1);
			}
			return false;
		}
	};

	// view2 touch
	private View.OnTouchListener view2TouchListener = new View.OnTouchListener() {

		@SuppressLint("ClickableViewAccessibility")
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			Log.d(tag, "view2 = " + isSoundPoolLoaded);
			if(isSoundPoolLoaded) {
				soundPool.play(bellId, 0.5f, 0.5f, 0, 0, 1);
			}
			return false;
		}
	};
}

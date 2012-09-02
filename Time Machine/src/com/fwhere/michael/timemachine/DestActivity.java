package com.fwhere.michael.timemachine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class DestActivity extends Activity {
	private static final int RESULT_CLOSE_ALL = 0;
	/** Called when the activity is first created. */

	Button cancelButton;
	MediaPlayer mp;
	MediaPlayer mp2;
	public int year;
	public int month;
	public int day;
	TextView dateTv;
	AudioManager audioManager;
	int currentVolume;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dest);
		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

		Bundle bundle = this.getIntent().getExtras();
		year = (Integer) bundle.get("year");
		month = (Integer) bundle.get("month");
		day = (Integer) bundle.get("day");
		String date = "";
		date = month + "." + day + "." + year;
		dateTv = (TextView) findViewById(R.id.dateTv);
		dateTv.setText(date);
		mp = MediaPlayer.create(DestActivity.this, R.raw.startmachine);
		mp.setVolume(currentVolume, currentVolume);
		mp2 = MediaPlayer.create(DestActivity.this, R.raw.buttonclick);
		mp2.setVolume(currentVolume, currentVolume);
		mp.start();
		mp.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				mp.release();
				finish();
				Intent intent = new Intent(DestActivity.this, EndActivity.class);
				startActivity(intent);
			}
		});

		// cancel button
		cancelButton = (Button) findViewById(R.id.cancelButton);
		cancelButton.setBackgroundColor(Color.rgb(166, 0, 0));
		cancelButton.setTextColor(Color.rgb(255, 255, 255));
		cancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (null != mp) {
					Log.d("michael's debug", "do1");
					mp.release();
				}
				finish();
			}
		});
		cancelButton.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					mp2 = MediaPlayer.create(DestActivity.this,
							R.raw.buttonclick);
					mp2.start();
					mp2.setOnCompletionListener(new OnCompletionListener() {
						@Override
						public void onCompletion(MediaPlayer mp) {
							mp.release();
						}
					});
					cancelButton.setBackgroundColor(Color.rgb(200, 0, 0));
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					cancelButton.setBackgroundColor(Color.rgb(166, 0, 0));
				}
				return false;
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case RESULT_CLOSE_ALL:
			setResult(RESULT_CLOSE_ALL);
			finish();
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 如果是返回键,直接返回到桌面
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (null != mp) {
				mp.release();
			}
			if (null != mp2) {
				mp2.release();
			}
			this.setResult(0);
			finish();
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		finish();
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if(mp != null) 
			mp.release();
		if(mp2 != null) 
			mp2.release();
		finish();
		super.onDestroy();
	}
}
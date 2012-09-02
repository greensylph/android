package com.fwhere.michael.timerbomb;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class ExplodeActivity extends Activity {
	/** Called when the activity is first created. */

	MediaPlayer mp1;
	MediaPlayer mp2;
	AudioManager audioManager;
	int currentVolume;
	Button dismissButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.explode);

		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		mp1 = MediaPlayer.create(ExplodeActivity.this, R.raw.bomb1);
		mp1.start();
		mp2 = MediaPlayer.create(ExplodeActivity.this, R.raw.buttonclick);
		dismissButton = (Button) findViewById(R.id.dismissButton);
		dismissButton.setBackgroundColor(Color.rgb(166, 0, 0));
		dismissButton.setTextColor(Color.rgb(255, 255, 255));
		dismissButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(mp2.isPlaying()) {
					mp2.stop();
					try {
						mp2.prepare();
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				mp2.start();
				setResult(10);
				finish();
			}

		});
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 如果是返回键,直接返回到桌面
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (null != mp1)
				mp1.release();
			if (null != mp2)
				mp2.release();
			this.setResult(10);
			this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		finish();
		super.onDestroy();
	}

}
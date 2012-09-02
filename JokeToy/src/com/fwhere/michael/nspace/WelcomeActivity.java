package com.fwhere.michael.nspace;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

public class WelcomeActivity extends Activity {
	/** Called when the activity is first created. */

	MediaPlayer mp;
	AudioManager audioManager;
	int currentVolume;
	 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		
		Thread timer = new Thread() {
			public void run() {
				try {
					sleep(2500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
//					Intent intent = new Intent("com.fwhere.michael.nspace.JOKETOYACTIVITY");
					Intent intent = new Intent();
					intent.setClass(WelcomeActivity.this, JokeToyActivity.class);     //B为你按退出按钮所在的activity
				    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				}
			}
		};
		timer.start();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			// 横向
			setContentView(R.layout.mainland);
		} else {
			// 竖向
			setContentView(R.layout.main);
		}
	}
	
}
package com.fwhere.michael.magicpainter;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;

public class WelcomeActivity extends Activity {
	private static final int RESULT_CLOSE_ALL = 0;
	/** Called when the activity is first created. */

	MediaPlayer mp;
	AudioManager audioManager;
	int currentVolume;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.welcome);

		Thread timer = new Thread() {
			public void run() {
				try {
					sleep(2500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					Intent intent = new Intent();
					intent.setClass(WelcomeActivity.this,
							HomeActivity.class); // B为你按退出按钮所在的activity
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivityForResult(intent,0);
				}
			}
		};
		timer.start();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    switch(resultCode)
	    {
	    case RESULT_CLOSE_ALL:
	        setResult(RESULT_CLOSE_ALL);
	        finish();
	    }
	    super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		this.setResult(0);
		finish();
		super.onDestroy();
	}
	
}
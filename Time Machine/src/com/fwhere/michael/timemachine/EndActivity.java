package com.fwhere.michael.timemachine;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class EndActivity extends Activity {
	private static final int RESULT_CLOSE_ALL = 0;
	/** Called when the activity is first created. */

	Button dismissButton;
	MediaPlayer mp;
	TextView dateTv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.end);

		mp = MediaPlayer.create(EndActivity.this, R.raw.buttonclick);
		dismissButton = (Button) findViewById(R.id.dismissButton);
		dismissButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
				if (mp.isPlaying()) {
					mp.stop();
					try {
						mp.prepare();
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				mp.start();
				setResult(10);
				finish();
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
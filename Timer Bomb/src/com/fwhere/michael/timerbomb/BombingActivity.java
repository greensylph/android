package com.fwhere.michael.timerbomb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.ads.AdView;

public class BombingActivity extends Activity {
	/** Called when the activity is first created. */

	MediaPlayer mp1;
	MediaPlayer mp2;
	AudioManager audioManager;
	int currentVolume;
	Button cancelButton;
	MichaelCount mCount;
	TextView minTv;
	TextView secTv;
	private int mins;
	private int secs;
	int index;
	ImageView imgView;
	AdView adView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		mp2 = MediaPlayer.create(BombingActivity.this, R.raw.buttonclick);
		setContentView(R.layout.bombing);

		minTv = (TextView) findViewById(R.id.minTV);
		secTv = (TextView) findViewById(R.id.secTV);
		imgView = (ImageView) findViewById(R.id.imgView2);

		Bundle bundle = this.getIntent().getExtras();
		mins = (Integer) bundle.get("mins");
		secs = (Integer) bundle.get("secs");
		index = (Integer) bundle.get("img");

		switch (index) {
		case 0:
			imgView.setImageResource(R.drawable.choice1);
			break;
		case 1:
			imgView.setImageResource(R.drawable.choice2);
			break;
		case 2:
			imgView.setImageResource(R.drawable.choice3);
			break;
		case 3:
			imgView.setImageResource(R.drawable.choice4);
			break;
		default:
			index = 0;
			imgView.setImageResource(R.drawable.choice1);
			break;
		}

		minTv.setText("0" + mins);
		secTv.setText("" + secs);

		if (!"00".equals(secTv.getText().toString())
				|| !"00".equals(minTv.getText().toString())) {
			mCount = new MichaelCount(secs * 1000, 1000);
			mCount.start();

			mp1 = MediaPlayer.create(BombingActivity.this, R.raw.ticktock0);
			mp1.setLooping(true);
			mp1.start();
			mp1.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer mp) {
					// TODO Auto-generated method stub
					mp1.release();
				}

			});
		}

		// cancel button
		cancelButton = (Button) findViewById(R.id.cancelButton);
		cancelButton.setBackgroundColor(Color.rgb(166, 0, 0));
		cancelButton.setTextColor(Color.rgb(255, 255, 255));
		cancelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (!"00".equals(secTv.getText().toString())
						|| !"00".equals(minTv.getText().toString())) {
					mCount.cancel();
					if (null != mp1)
						mp1.release();
					if (null != mp2)
						mp2.release();
					finish();
				}
			}

		});

		cancelButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					mp2 = MediaPlayer.create(BombingActivity.this,
							R.raw.buttonclick);
					mp2.start();
					mp2.setOnCompletionListener(new OnCompletionListener() {

						@Override
						public void onCompletion(MediaPlayer mp) {
							// TODO Auto-generated method stub
							mp2.release();
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

	public class MichaelCount extends CountDownTimer {

		public MichaelCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			// some script here
			if (null != mp1)
				mp1.release();
			if (null != mp2)
				mp2.release();
			mCount.cancel();
			minTv.setText("00");
			secTv.setText("00");
			finish();
			Intent intent = new Intent(
					"com.fwhere.michael.timerbomb.EXPLODEACTIVITY");
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			// some script here
			long leftMin;
			long leftSec = millisUntilFinished / 1000;
			if (leftSec < 10) {
				secTv.setText("0" + leftSec);
				minTv.setText("00");
			} else if (leftSec > 60) {
				leftMin = leftSec / 60;
				minTv.setText("0" + leftMin);
				secTv.setText("" + (leftSec - leftMin * 60));
			} else {
				secTv.setText("" + leftSec);
				minTv.setText("00");
			}

		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 如果是返回键,直接返回到桌面
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (null != mp1) {
				mp1.release();
			}
			if (null != mp2) {
				mp2.release();
			}
			mCount.cancel();
			this.setResult(0);
			finish();
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		finish();
		super.onDestroy();
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 10) {
			this.setResult(10);
			this.finish();
		}
	}

}
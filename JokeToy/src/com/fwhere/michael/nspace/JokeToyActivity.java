package com.fwhere.michael.nspace;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class JokeToyActivity extends Activity {
	/** Called when the activity is first created. */

	MediaPlayer mp;
	AudioManager audioManager;
	int currentVolume;
	AdView adView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		adView = new AdView(this, AdSize.BANNER, "97b58d746ae14549");
		// Lookup your LinearLayout assuming it’s been given
		// the attribute android:id="@+id/mainLayout"
		LinearLayout layout = (LinearLayout) findViewById(R.id.mainLayout);

		// Add the adView to it
		layout.addView(adView);

		// Initiate a generic request to load it with an ad
		adView.loadAd(new AdRequest());

		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		mp = MediaPlayer.create(JokeToyActivity.this, R.raw.fart1);
		// TextView tv = (TextView) findViewById(R.id.hint);
		Button snoreButton = (Button) findViewById(R.id.snoreButton);
		Button fartButton = (Button) findViewById(R.id.fartButton);
		Button screamButton = (Button) findViewById(R.id.screamButton);
		Button noseblowButton = (Button) findViewById(R.id.noseblowButton);
		Button laughButton = (Button) findViewById(R.id.laughButton);

		// 打呼按钮
		snoreButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (mp.isPlaying()) {
					mp.stop();
					mp.release();
				}
				int i = 0;
				Random r = new Random();
				i = r.nextInt(3 + 1);
				switch (i) {
				case 1:
					mp = MediaPlayer.create(JokeToyActivity.this,
							R.raw.snoring1);
					mp.setVolume(currentVolume, currentVolume);
					mp.start();
					break;
				case 2:
					mp = MediaPlayer.create(JokeToyActivity.this,
							R.raw.snoring2);
					mp.setVolume(currentVolume, currentVolume);
					mp.start();
					break;
				case 3:
					mp = MediaPlayer.create(JokeToyActivity.this,
							R.raw.snoring3);
					mp.setVolume(currentVolume, currentVolume);
					mp.start();
					break;
				default:
					mp = MediaPlayer.create(JokeToyActivity.this,
							R.raw.snoring1);
					mp.setVolume(currentVolume, currentVolume);
					mp.start();
					break;
				}
			}
		});
		// 放屁按钮
		fartButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (mp.isPlaying()) {
					mp.stop();
					mp.release();
				}
				int i = 0;
				Random r = new Random();
				i = r.nextInt(9 + 1);
				switch (i) {
				case 1:
					mp = MediaPlayer.create(JokeToyActivity.this, R.raw.fart1);
					mp.start();
					break;
				case 2:
					mp = MediaPlayer.create(JokeToyActivity.this, R.raw.fart2);
					mp.start();
					break;
				case 3:
					mp = MediaPlayer.create(JokeToyActivity.this, R.raw.fart3);
					mp.start();
					break;
				case 4:
					mp = MediaPlayer.create(JokeToyActivity.this, R.raw.fart4);
					mp.start();
					break;
				case 5:
					mp = MediaPlayer.create(JokeToyActivity.this, R.raw.fart5);
					mp.start();
					break;
				case 6:
					mp = MediaPlayer.create(JokeToyActivity.this, R.raw.fart6);
					mp.start();
					break;
				default:
					mp = MediaPlayer.create(JokeToyActivity.this, R.raw.fart1);
					mp.start();
					break;
				}
			}
		});
		// 尖叫按钮
		screamButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (mp.isPlaying()) {
					mp.stop();
					mp.release();
				}
				int i = 0;
				Random r = new Random();
				i = r.nextInt(6 + 1);
				switch (i) {
				case 1:
					mp = MediaPlayer
							.create(JokeToyActivity.this, R.raw.scream1);
					mp.setVolume(currentVolume, currentVolume);
					mp.start();
					break;
				case 2:
					mp = MediaPlayer
							.create(JokeToyActivity.this, R.raw.scream2);
					mp.setVolume(currentVolume, currentVolume);
					mp.start();
					break;
				case 3:
					mp = MediaPlayer
							.create(JokeToyActivity.this, R.raw.scream3);
					mp.setVolume(currentVolume, currentVolume);
					mp.start();
					break;
				case 4:
					mp = MediaPlayer
							.create(JokeToyActivity.this, R.raw.scream4);
					mp.setVolume(currentVolume, currentVolume);
					mp.start();
					break;
				case 5:
					mp = MediaPlayer
							.create(JokeToyActivity.this, R.raw.scream5);
					mp.setVolume(currentVolume, currentVolume);
					mp.start();
					break;
				case 6:
					mp = MediaPlayer
							.create(JokeToyActivity.this, R.raw.scream6);
					mp.setVolume(currentVolume, currentVolume);
					mp.start();
					break;
				default:
					mp = MediaPlayer.create(JokeToyActivity.this,
							R.raw.snoring1);
					mp.setVolume(currentVolume, currentVolume);
					mp.start();
					break;
				}
			}
		});
		// 擤鼻涕按钮
		noseblowButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (mp.isPlaying()) {
					mp.stop();
					mp.release();
				}
				int i = 0;
				Random r = new Random();
				i = r.nextInt(6 + 1);
				switch (i) {
				case 1:
					mp = MediaPlayer.create(JokeToyActivity.this,
							R.raw.noseblow1);
					mp.setVolume(currentVolume, currentVolume);
					mp.start();
					break;
				case 2:
					mp = MediaPlayer.create(JokeToyActivity.this,
							R.raw.noseblow2);
					mp.setVolume(currentVolume, currentVolume);
					mp.start();
					break;
				case 3:
					mp = MediaPlayer.create(JokeToyActivity.this,
							R.raw.noseblow3);
					mp.setVolume(currentVolume, currentVolume);
					mp.start();
					break;
				case 4:
					mp = MediaPlayer.create(JokeToyActivity.this,
							R.raw.noseblow4);
					mp.setVolume(currentVolume, currentVolume);
					mp.start();
					break;
				case 5:
					mp = MediaPlayer.create(JokeToyActivity.this,
							R.raw.noseblow5);
					mp.setVolume(currentVolume, currentVolume);
					mp.start();
					break;
				case 6:
					mp = MediaPlayer.create(JokeToyActivity.this,
							R.raw.noseblow6);
					mp.setVolume(currentVolume, currentVolume);
					mp.start();
					break;
				default:
					mp = MediaPlayer.create(JokeToyActivity.this,
							R.raw.snoring1);
					mp.setVolume(currentVolume, currentVolume);
					mp.start();
					break;
				}
			}
		});
		// 笑声按钮
		laughButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (mp.isPlaying()) {
					mp.stop();
					mp.release();
				}
				int i = 0;
				Random r = new Random();
				i = r.nextInt(6 + 1);
				switch (i) {
				case 1:
					mp = MediaPlayer.create(JokeToyActivity.this, R.raw.laugh1);
					mp.setVolume(currentVolume, currentVolume);
					mp.start();
					break;
				case 2:
					mp = MediaPlayer.create(JokeToyActivity.this, R.raw.laugh2);
					mp.setVolume(currentVolume, currentVolume);
					mp.start();
					break;
				case 3:
					mp = MediaPlayer.create(JokeToyActivity.this, R.raw.laugh3);
					mp.setVolume(currentVolume, currentVolume);
					mp.start();
					break;
				case 4:
					mp = MediaPlayer.create(JokeToyActivity.this, R.raw.laugh4);
					mp.setVolume(currentVolume, currentVolume);
					mp.start();
					break;
				case 5:
					mp = MediaPlayer.create(JokeToyActivity.this, R.raw.laugh5);
					mp.setVolume(currentVolume, currentVolume);
					mp.start();
					break;
				case 6:
					mp = MediaPlayer.create(JokeToyActivity.this, R.raw.laugh6);
					mp.setVolume(currentVolume, currentVolume);
					mp.start();
					break;
				default:
					mp = MediaPlayer.create(JokeToyActivity.this, R.raw.laugh1);
					mp.setVolume(currentVolume, currentVolume);
					mp.start();
					break;
				}
			}
		});

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			// 横向
			setContentView(R.layout.mainland);
			audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
			currentVolume = audioManager
					.getStreamVolume(AudioManager.STREAM_MUSIC);
			mp = MediaPlayer.create(JokeToyActivity.this, R.raw.fart1);
			// TextView tv = (TextView) findViewById(R.id.hint);
			Button snoreButton = (Button) findViewById(R.id.snoreButton);
			Button fartButton = (Button) findViewById(R.id.fartButton);
			Button screamButton = (Button) findViewById(R.id.screamButton);
			Button noseblowButton = (Button) findViewById(R.id.noseblowButton);
			Button laughButton = (Button) findViewById(R.id.laughButton);

			// 打呼按钮
			snoreButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					if (mp.isPlaying()) {
						mp.stop();
						mp.release();
					}
					int i = 0;
					Random r = new Random();
					i = r.nextInt(3 + 1);
					switch (i) {
					case 1:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.snoring1);
						mp.setVolume(currentVolume, currentVolume);
						mp.start();
						break;
					case 2:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.snoring2);
						mp.setVolume(currentVolume, currentVolume);
						mp.start();
						break;
					case 3:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.snoring3);
						mp.setVolume(currentVolume, currentVolume);
						mp.start();
						break;
					default:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.snoring1);
						mp.setVolume(currentVolume, currentVolume);
						mp.start();
						break;
					}
				}
			});
			// 放屁按钮
			fartButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					if (mp.isPlaying()) {
						mp.stop();
						mp.release();
					}
					int i = 0;
					Random r = new Random();
					i = r.nextInt(9 + 1);
					switch (i) {
					case 1:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.fart1);
						mp.start();
						break;
					case 2:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.fart2);
						mp.start();
						break;
					case 3:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.fart3);
						mp.start();
						break;
					case 4:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.fart4);
						mp.start();
						break;
					case 5:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.fart5);
						mp.start();
						break;
					case 6:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.fart6);
						mp.start();
						break;
					default:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.fart1);
						mp.start();
						break;
					}
				}
			});
			// 尖叫按钮
			screamButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					if (mp.isPlaying()) {
						mp.stop();
						mp.release();
					}
					int i = 0;
					Random r = new Random();
					i = r.nextInt(6 + 1);
					switch (i) {
					case 1:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.scream1);
						mp.setVolume(currentVolume, currentVolume);
						mp.start();
						break;
					case 2:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.scream2);
						mp.setVolume(currentVolume, currentVolume);
						mp.start();
						break;
					case 3:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.scream3);
						mp.setVolume(currentVolume, currentVolume);
						mp.start();
						break;
					case 4:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.scream4);
						mp.setVolume(currentVolume, currentVolume);
						mp.start();
						break;
					case 5:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.scream5);
						mp.setVolume(currentVolume, currentVolume);
						mp.start();
						break;
					case 6:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.scream6);
						mp.setVolume(currentVolume, currentVolume);
						mp.start();
						break;
					default:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.snoring1);
						mp.setVolume(currentVolume, currentVolume);
						mp.start();
						break;
					}
				}
			});
			// 擤鼻涕按钮
			noseblowButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					if (mp.isPlaying()) {
						mp.stop();
						mp.release();
					}
					int i = 0;
					Random r = new Random();
					i = r.nextInt(6 + 1);
					switch (i) {
					case 1:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.noseblow1);
						mp.setVolume(currentVolume, currentVolume);
						mp.start();
						break;
					case 2:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.noseblow2);
						mp.setVolume(currentVolume, currentVolume);
						mp.start();
						break;
					case 3:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.noseblow3);
						mp.setVolume(currentVolume, currentVolume);
						mp.start();
						break;
					case 4:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.noseblow4);
						mp.setVolume(currentVolume, currentVolume);
						mp.start();
						break;
					case 5:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.noseblow5);
						mp.setVolume(currentVolume, currentVolume);
						mp.start();
						break;
					case 6:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.noseblow6);
						mp.setVolume(currentVolume, currentVolume);
						mp.start();
						break;
					default:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.snoring1);
						mp.setVolume(currentVolume, currentVolume);
						mp.start();
						break;
					}
				}
			});
			// 笑声按钮
			laughButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					if (mp.isPlaying()) {
						mp.stop();
						mp.release();
					}
					int i = 0;
					Random r = new Random();
					i = r.nextInt(6 + 1);
					switch (i) {
					case 1:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.laugh1);
						mp.setVolume(currentVolume, currentVolume);
						mp.start();
						break;
					case 2:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.laugh2);
						mp.setVolume(currentVolume, currentVolume);
						mp.start();
						break;
					case 3:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.laugh3);
						mp.setVolume(currentVolume, currentVolume);
						mp.start();
						break;
					case 4:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.laugh4);
						mp.setVolume(currentVolume, currentVolume);
						mp.start();
						break;
					case 5:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.laugh5);
						mp.setVolume(currentVolume, currentVolume);
						mp.start();
						break;
					case 6:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.laugh6);
						mp.setVolume(currentVolume, currentVolume);
						mp.start();
						break;
					default:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.laugh1);
						mp.setVolume(currentVolume, currentVolume);
						mp.start();
						break;
					}
				}
			});
		} else {
			// 竖向
			setContentView(R.layout.main);
			audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
			currentVolume = audioManager
					.getStreamVolume(AudioManager.STREAM_MUSIC);
			mp = MediaPlayer.create(JokeToyActivity.this, R.raw.fart1);
			// TextView tv = (TextView) findViewById(R.id.hint);
			Button snoreButton = (Button) findViewById(R.id.snoreButton);
			Button fartButton = (Button) findViewById(R.id.fartButton);
			Button screamButton = (Button) findViewById(R.id.screamButton);
			Button noseblowButton = (Button) findViewById(R.id.noseblowButton);
			Button laughButton = (Button) findViewById(R.id.laughButton);

			// 打呼按钮
			snoreButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					if (mp.isPlaying()) {
						mp.stop();
						mp.release();
					}
					int i = 0;
					Random r = new Random();
					i = r.nextInt(3 + 1);
					switch (i) {
					case 1:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.snoring1);
						mp.setVolume(currentVolume, currentVolume);
						mp.start();
						break;
					case 2:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.snoring2);
						mp.setVolume(currentVolume, currentVolume);
						mp.start();
						break;
					case 3:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.snoring3);
						mp.setVolume(currentVolume, currentVolume);
						mp.start();
						break;
					default:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.snoring1);
						mp.setVolume(currentVolume, currentVolume);
						mp.start();
						break;
					}
				}
			});
			// 放屁按钮
			fartButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					if (mp.isPlaying()) {
						mp.stop();
						mp.release();
					}
					int i = 0;
					Random r = new Random();
					i = r.nextInt(9 + 1);
					switch (i) {
					case 1:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.fart1);
						mp.start();
						break;
					case 2:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.fart2);
						mp.start();
						break;
					case 3:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.fart3);
						mp.start();
						break;
					case 4:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.fart4);
						mp.start();
						break;
					case 5:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.fart5);
						mp.start();
						break;
					case 6:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.fart6);
						mp.start();
						break;
					default:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.fart1);
						mp.start();
						break;
					}
				}
			});
			// 尖叫按钮
			screamButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					if (mp.isPlaying()) {
						mp.stop();
						mp.release();
					}
					int i = 0;
					Random r = new Random();
					i = r.nextInt(6 + 1);
					switch (i) {
					case 1:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.scream1);
						mp.setVolume(currentVolume, currentVolume);
						mp.start();
						break;
					case 2:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.scream2);
						mp.setVolume(currentVolume, currentVolume);
						mp.start();
						break;
					case 3:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.scream3);
						mp.setVolume(currentVolume, currentVolume);
						mp.start();
						break;
					case 4:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.scream4);
						mp.setVolume(currentVolume, currentVolume);
						mp.start();
						break;
					case 5:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.scream5);
						mp.setVolume(currentVolume, currentVolume);
						mp.start();
						break;
					case 6:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.scream6);
						mp.setVolume(currentVolume, currentVolume);
						mp.start();
						break;
					default:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.snoring1);
						mp.setVolume(currentVolume, currentVolume);
						mp.start();
						break;
					}
				}
			});
			// 擤鼻涕按钮
			noseblowButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					if (mp.isPlaying()) {
						mp.stop();
						mp.release();
					}
					int i = 0;
					Random r = new Random();
					i = r.nextInt(6 + 1);
					switch (i) {
					case 1:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.noseblow1);
						mp.setVolume(currentVolume, currentVolume);
						mp.start();
						break;
					case 2:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.noseblow2);
						mp.setVolume(currentVolume, currentVolume);
						mp.start();
						break;
					case 3:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.noseblow3);
						mp.setVolume(currentVolume, currentVolume);
						mp.start();
						break;
					case 4:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.noseblow4);
						mp.setVolume(currentVolume, currentVolume);
						mp.start();
						break;
					case 5:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.noseblow5);
						mp.setVolume(currentVolume, currentVolume);
						mp.start();
						break;
					case 6:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.noseblow6);
						mp.setVolume(currentVolume, currentVolume);
						mp.start();
						break;
					default:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.snoring1);
						mp.setVolume(currentVolume, currentVolume);
						mp.start();
						break;
					}
				}
			});
			// 笑声按钮
			laughButton.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					if (mp.isPlaying()) {
						mp.stop();
						mp.release();
					}
					int i = 0;
					Random r = new Random();
					i = r.nextInt(6 + 1);
					switch (i) {
					case 1:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.laugh1);
						mp.setVolume(currentVolume, currentVolume);
						mp.start();
						break;
					case 2:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.laugh2);
						mp.setVolume(currentVolume, currentVolume);
						mp.start();
						break;
					case 3:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.laugh3);
						mp.setVolume(currentVolume, currentVolume);
						mp.start();
						break;
					case 4:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.laugh4);
						mp.setVolume(currentVolume, currentVolume);
						mp.start();
						break;
					case 5:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.laugh5);
						mp.setVolume(currentVolume, currentVolume);
						mp.start();
						break;
					case 6:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.laugh6);
						mp.setVolume(currentVolume, currentVolume);
						mp.start();
						break;
					default:
						mp = MediaPlayer.create(JokeToyActivity.this,
								R.raw.laugh1);
						mp.setVolume(currentVolume, currentVolume);
						mp.start();
						break;
					}
				}
			});
		}
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 如果是返回键,直接返回到桌面
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			showExitAlert();
		}
		return super.onKeyDown(keyCode, event);
	}

	public void showExitAlert() {
		// TODO Auto-generated method stub
		AlertDialog alert = new AlertDialog.Builder(JokeToyActivity.this)
				.create();
		alert.setTitle("Confirm to Exit");
		alert.setMessage("Do you want to exit JokeToy now?");
		alert.setButton("Exit", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		alert.setButton2("Cancle", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub

			}
		});
		alert.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		MenuInflater blowup = getMenuInflater();
		blowup.inflate(R.menu.coolmenu, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case R.id.aboutUs:
			Dialog d = new Dialog(this);
			d.setTitle("About Us");
			TextView tv = new TextView(this);
			String text = "Thank you so much to read this. If you have any advice or suggestion, to change and make our App more interesting. Please Contact us Email: jasonzqc@gmail.com. \n\r";
			tv.setText(text);
			d.setContentView(tv);
			d.show();
			break;
		default:
			break;
		}
		return false;
	}

	@Override
	protected void onPause() {
		super.onPause();
		mp.release();
		finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.exit(0);
	}
}
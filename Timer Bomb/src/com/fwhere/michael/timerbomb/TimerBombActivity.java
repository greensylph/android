package com.fwhere.michael.timerbomb;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TimerBombActivity extends Activity {
	/** Called when the activity is first created. */

	MediaPlayer mp;
	AudioManager audioManager;
	int currentVolume;
	GestureDetector gestureDetector;
	Button startButton;
	Button setButton;
	ImageButton leftButton;
	ImageButton rightButton;
	TextView minTv;
	TextView secTv;
	private int mins;
	private int secs;
	static final int TIME_DIALOG_ID = 0;
	public int index;
	ImageView imgView;
	AdView adView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		index = 0;
		adView = new AdView(this, AdSize.BANNER, "a14fa0f3c3dccb3");
		// Lookup your LinearLayout assuming it’s been given
	    // the attribute android:id="@+id/mainLayout"
	    LinearLayout layout = (LinearLayout)findViewById(R.id.mainLayout);

	    // Add the adView to it
	    layout.addView(adView);

	    // Initiate a generic request to load it with an ad
	    adView.loadAd(new AdRequest());
		minTv = (TextView) findViewById(R.id.minsTV);
		secTv = (TextView) findViewById(R.id.secsTV);

		gestureDetector = new GestureDetector(new DefaultGestureDetector());

		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		mp = MediaPlayer.create(TimerBombActivity.this, R.raw.buttonclick);

		imgView = (ImageView) findViewById(R.id.imageView1);

		leftButton = (ImageButton) findViewById(R.id.leftButton);
		leftButton.getBackground().setAlpha(0);
		leftButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				index -= 1;
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
			}

		});
		leftButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					leftButton.getBackground().setAlpha(150);
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					mp = MediaPlayer.create(TimerBombActivity.this,
							R.raw.buttonclick);
					mp.start();
					mp.setOnCompletionListener(new OnCompletionListener() {

						@Override
						public void onCompletion(MediaPlayer mp) {
							// TODO Auto-generated method stub
							mp.release();
						}

					});
					leftButton.getBackground().setAlpha(0);
				}
				return false;
			}

		});
		rightButton = (ImageButton) findViewById(R.id.rightButton);
		rightButton.getBackground().setAlpha(0);
		rightButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				index = index + 1;
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
			}

		});
		rightButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					rightButton.getBackground().setAlpha(150);
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					mp = MediaPlayer.create(TimerBombActivity.this,
							R.raw.buttonclick);
					mp.start();
					mp.setOnCompletionListener(new OnCompletionListener() {

						@Override
						public void onCompletion(MediaPlayer mp) {
							// TODO Auto-generated method stub
							mp.release();
						}

					});
					rightButton.getBackground().setAlpha(0);
				}
				return false;
			}

		});

		setButton = (Button) findViewById(R.id.setButton);
		setButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				final CharSequence[] items = { "15 secs", "30 secs", "60 secs",
						"120 secs" };
				AlertDialog alertDialog = new AlertDialog.Builder(
						TimerBombActivity.this)
						.setTitle("Set a time for timer bomb")
						.setPositiveButton("Reset",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										mp = MediaPlayer.create(
												TimerBombActivity.this,
												R.raw.buttonclick);
										mp.start();
										mp.setOnCompletionListener(new OnCompletionListener() {

											@Override
											public void onCompletion(
													MediaPlayer mp) {
												// TODO Auto-generated method
												// stub
												mp.release();
											}

										});
										secTv.setText("00");
										minTv.setText("00");
									}
								})
						.setSingleChoiceItems(items, -1,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										mp = MediaPlayer.create(
												TimerBombActivity.this,
												R.raw.buttonclick);
										mp.start();
										mp.setOnCompletionListener(new OnCompletionListener() {

											@Override
											public void onCompletion(
													MediaPlayer mp) {
												// TODO Auto-generated method
												// stub
												mp.release();
											}

										});
										switch (which) {
										case 0:
											secTv.setText("15");
											minTv.setText("00");
											mins = 0;
											secs = 15;
											dialog.dismiss();
											break;
										case 1:
											secTv.setText("30");
											minTv.setText("00");
											secs = 30;
											mins = 0;
											dialog.dismiss();
											break;
										case 2:
											minTv.setText("01");
											secTv.setText("00");
											mins = 1;
											secs = 60;
											dialog.dismiss();
											break;
										case 3:
											secTv.setText("00");
											minTv.setText("02");
											mins = 2;
											secs = 120;
											dialog.dismiss();
											break;
										default:
											secTv.setText("00");
											minTv.setText("00");
											dialog.dismiss();
											break;
										}
									}
								}).create();
				alertDialog.show();
			}
		});
		setButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					mp = MediaPlayer.create(TimerBombActivity.this,
							R.raw.buttonclick);
					mp.start();
					mp.setOnCompletionListener(new OnCompletionListener() {

						@Override
						public void onCompletion(MediaPlayer mp) {
							// TODO Auto-generated method stub
							mp.release();
						}

					});
				} else if (event.getAction() == MotionEvent.ACTION_UP) {

				}

				return false;
			}

		});
		startButton = (Button) findViewById(R.id.startButton);
		startButton.setBackgroundColor(Color.rgb(0, 166, 0));
		startButton.setTextColor(Color.rgb(255, 255, 255));

		startButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (!"00".equals(secTv.getText().toString())
						|| !"00".equals(minTv.getText().toString())) {
					mins = Integer.parseInt(minTv.getText().toString());
					secs = Integer.parseInt(secTv.getText().toString());
					if (secTv.getText().toString().equals("00")) {
						secs = mins * 60;
					}
					Intent intent = new Intent(
							"com.fwhere.michael.timerbomb.BOMBINGACTIVITY");
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					Bundle bundle = new Bundle();
					bundle.putInt("mins", mins);
					bundle.putInt("secs", secs);
					bundle.putInt("img", index);
					intent.putExtras(bundle);
					startActivityForResult(intent, 0);
				} else {
					Toast.makeText(TimerBombActivity.this, "For yourself's safe, Please set a time first.",
							Toast.LENGTH_SHORT).show();
				}

			}

		});

		// start button
		startButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					startButton.setBackgroundColor(Color.rgb(0, 200, 0));
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					mp = MediaPlayer.create(TimerBombActivity.this,
							R.raw.buttonclick);
					mp.start();
					mp.setOnCompletionListener(new OnCompletionListener() {

						@Override
						public void onCompletion(MediaPlayer mp) {
							// TODO Auto-generated method stub
							mp.release();
						}

					});
					startButton.setBackgroundColor(Color.rgb(0, 166, 0));
				}
				return false;
			}

		});

	}

	class DefaultGestureDetector extends SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			final int FLING_MIN_DISTANCE = 100;// X或者y轴上移动的距离(像素)
			final int FLING_MIN_VELOCITY = 200;// x或者y轴上的移动速度(像素/秒)
			if ((e1.getX() - e2.getX()) > FLING_MIN_DISTANCE
					&& Math.abs(velocityX) > FLING_MIN_VELOCITY) {
				// Toast.makeText(TimerBombActivity.this, "向左滑动",
				// Toast.LENGTH_SHORT).show();
				mp = MediaPlayer.create(TimerBombActivity.this,
						R.raw.buttonclick);
				mp.start();
				mp.setOnCompletionListener(new OnCompletionListener() {

					@Override
					public void onCompletion(MediaPlayer mp) {
						// TODO Auto-generated method stub
						mp.release();
					}

				});
				index += 1;
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

			} else if ((e2.getX() - e1.getX()) > FLING_MIN_DISTANCE
					&& Math.abs(velocityX) > FLING_MIN_VELOCITY) {
				// Toast.makeText(TimerBombActivity.this, "向右滑动",
				// Toast.LENGTH_SHORT).show();
				mp = MediaPlayer.create(TimerBombActivity.this,
						R.raw.buttonclick);
				mp.start();
				mp.setOnCompletionListener(new OnCompletionListener() {

					@Override
					public void onCompletion(MediaPlayer mp) {
						// TODO Auto-generated method stub
						mp.release();
					}

				});
				index -= 1;
				if (index == -1)
					index = 3;
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
			}
			return false;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return gestureDetector.onTouchEvent(event);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 如果是返回键,直接返回到桌面
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			showExitAlert();
		}
		return super.onKeyDown(keyCode, event);
	}

	public void showExitAlert() {
		AlertDialog alert = new AlertDialog.Builder(TimerBombActivity.this)
				.create();
		alert.setTitle("Confirm to Exit");
		alert.setMessage("Do you want to exit Timer Bomb now?");
		alert.setButton("Exit", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				finish();
				System.exit(0);
			}
		});
		alert.setButton2("Cancle", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
			}
		});
		alert.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater blowup = getMenuInflater();
		blowup.inflate(R.menu.coolmenu, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
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
	protected void onDestroy() {
		finish();
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		minTv.setText("00");
		secTv.setText("00");
		super.onResume();
	}

}
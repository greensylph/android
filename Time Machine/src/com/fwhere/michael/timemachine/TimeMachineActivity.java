package com.fwhere.michael.timemachine;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TimeMachineActivity extends Activity {

	Button startButton;
	MediaPlayer mp;
	ImageButton upButton1;
	ImageButton downButton1;
	ImageButton upButton2;
	ImageButton downButton2;
	ImageButton upButton3;
	ImageButton downButton3;
	EditText eText1;
	EditText eText2;
	EditText eText3;
	public int year = 0;
	public int month = 0;
	public int day = 0;
	AdView adView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);

		adView = new AdView(this, AdSize.BANNER, "a14fa652705f63b");
		// Lookup your LinearLayout assuming it’s been given
		// the attribute android:id="@+id/mainLayout"
		LinearLayout layout = (LinearLayout) findViewById(R.id.mainLayout);

		// Add the adView to it
		layout.addView(adView);

		// Initiate a generic request to load it with an ad
		adView.loadAd(new AdRequest());

		SimpleDateFormat formatterYear = new SimpleDateFormat("yyyy");// hh12小时年MM月dd日
		SimpleDateFormat formatterMonth = new SimpleDateFormat("MM");
		SimpleDateFormat formatterDay = new SimpleDateFormat("dd");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间 HH24小时
		String yearStr = "";
		String monthStr = "";
		String dayStr = "";
		yearStr = formatterYear.format(curDate);
		monthStr = formatterMonth.format(curDate);
		dayStr = formatterDay.format(curDate);

		eText1 = (EditText) findViewById(R.id.eText1);
		eText1.setText(monthStr);
		eText2 = (EditText) findViewById(R.id.eText2);
		eText2.setText(dayStr);
		eText3 = (EditText) findViewById(R.id.eText3);
		eText3.setText(yearStr);
		year = Integer.parseInt(yearStr);
		month = Integer.parseInt(monthStr);
		day = Integer.parseInt(dayStr);

		upButton1 = (ImageButton) findViewById(R.id.upButton1);
		upButton1.getBackground().setAlpha(0);
		upButton2 = (ImageButton) findViewById(R.id.upButton2);
		upButton2.getBackground().setAlpha(0);
		upButton3 = (ImageButton) findViewById(R.id.upButton3);
		upButton3.getBackground().setAlpha(0);

		downButton1 = (ImageButton) findViewById(R.id.downButton1);
		downButton1.getBackground().setAlpha(0);
		downButton2 = (ImageButton) findViewById(R.id.downButton2);
		downButton2.getBackground().setAlpha(0);
		downButton3 = (ImageButton) findViewById(R.id.downButton3);
		downButton3.getBackground().setAlpha(0);

		upButton1.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					upButton1.getBackground().setAlpha(150);
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					upButton1.getBackground().setAlpha(0);
				}
				return false;
			}
		});
		upButton2.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					upButton2.getBackground().setAlpha(150);
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					upButton2.getBackground().setAlpha(0);
				}
				return false;
			}
		});
		upButton3.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					upButton3.getBackground().setAlpha(150);
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					upButton3.getBackground().setAlpha(0);
				}
				return false;
			}
		});
		downButton1.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					downButton1.getBackground().setAlpha(150);
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					downButton1.getBackground().setAlpha(0);
				}
				return false;
			}
		});
		downButton2.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					downButton2.getBackground().setAlpha(150);
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					downButton2.getBackground().setAlpha(0);
				}
				return false;
			}
		});
		downButton3.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					downButton3.getBackground().setAlpha(150);
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					downButton3.getBackground().setAlpha(0);
				}
				return false;
			}
		});

		upButton1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				month = Integer.parseInt(eText1.getText() + "");
				month += 1;
				if (month == 13) {
					month = 1;
				}
				if (month > 9)
					eText1.setText("" + month);
				else
					eText1.setText("0" + month);
			}
		});
		downButton1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				month = Integer.parseInt(eText1.getText() + "");
				month -= 1;
				if (month == 0)
					month = 12;
				if (month > 9)
					eText1.setText("" + month);
				else
					eText1.setText("0" + month);

			}
		});
		upButton2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				day = Integer.parseInt(eText2.getText() + "");
				day += 1;
				if (day == 31) {
					day = 1;
				}
				if (day > 9)
					eText2.setText("" + day);
				else
					eText2.setText("0" + day);

			}
		});
		downButton2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				day = Integer.parseInt(eText2.getText() + "");
				day -= 1;
				if (day == 0)
					day = 31;
				if (day > 9)
					eText2.setText("" + day);
				else
					eText2.setText("0" + day);

			}
		});
		upButton3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				year = Integer.parseInt(eText3.getText() + "");
				year += 1;
				if (year > 999)
					eText3.setText("" + year);
				else if (year > 99 && year < 999)
					eText3.setText("0" + year);
				else if (year > 9 && year < 99)
					eText3.setText("00" + year);
				else if (year > 0 && year < 9)
					eText3.setText("000" + year);

			}
		});
		downButton3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				year = Integer.parseInt(eText3.getText() + "");
				year -= 1;
				if (year > 999)
					eText3.setText("" + year);
				else if (year > 99 && year < 999)
					eText3.setText("0" + year);
				else if (year > 9 && year < 99)
					eText3.setText("00" + year);
				else if (year > 0 && year < 9)
					eText3.setText("000" + year);

			}
		});

		// start button
		startButton = (Button) findViewById(R.id.startButton);
		startButton.setBackgroundColor(Color.rgb(0, 166, 0));
		startButton.setTextColor(Color.rgb(255, 255, 255));
		startButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(TimeMachineActivity.this, DestActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				Bundle bundle = new Bundle();
				bundle.putInt("month", month);
				bundle.putInt("day", day);
				bundle.putInt("year", year);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		startButton.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View arg0, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					startButton.setBackgroundColor(Color.rgb(0, 200, 0));
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					mp = MediaPlayer.create(TimeMachineActivity.this,
							R.raw.buttonclick);
					mp.start();
					mp.setOnCompletionListener(new OnCompletionListener() {
						@Override
						public void onCompletion(MediaPlayer mp) {
							mp.release();
						}
					});
					startButton.setBackgroundColor(Color.rgb(0, 166, 0));
				}
				return false;
			}
		});
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 如果是返回键,直接返回到桌面
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			showExitAlert();
		}
		return super.onKeyDown(keyCode, event);
	}

	public void showExitAlert() {
		AlertDialog alert = new AlertDialog.Builder(TimeMachineActivity.this)
				.create();
		alert.setTitle("Confirm to Exit");
		alert.setMessage("Do you want to exit Time Machine now?");
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
}
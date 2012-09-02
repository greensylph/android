package com.fwhere.michael.magicpainter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.ads.AdView;

public class HomeActivity extends Activity {

	/** Called when the activity is first created. */
	Button blankButton;
	TextView tv;
	AdView adView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.home);
		
//		adView = new AdView(this, AdSize.BANNER, "c9f718ead58f47e4");
//		// Lookup your LinearLayout assuming it’s been given
//	    // the attribute android:id="@+id/mainLayout"
//	    LinearLayout layout = (LinearLayout)findViewById(R.id.mainLayout);
//
//	    // Add the adView to it
//	    layout.addView(adView);
//
//	    // Initiate a generic request to load it with an ad
//	    adView.loadAd(new AdRequest());

		tv = (TextView) findViewById(R.id.textView1);
		tv.setTextColor(Color.rgb(81, 110, 181));
		blankButton = (Button) findViewById(R.id.blankButton);

		blankButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(HomeActivity.this,
						MagicPainterActivity.class); // B为你按退出按钮所在的activity
				startActivity(intent);
			}

		});
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

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK)
			showExitAlert();
		return super.onKeyDown(keyCode, event);
	}

	public void showExitAlert() {
		AlertDialog alert = new AlertDialog.Builder(HomeActivity.this).create();
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

}
package com.fwhere.michael.yespad;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.fwhere.michael.yespad.NotePad.Notes;

public class HomeActivity extends Activity implements OnClickListener {

	/** Called when the activity is first created. */
	Button aboutButton;
	Button exitButton;
	Button newButton;
	Button listButton;
	TextView tv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		
		// If no data was given in the intent (because we were started
        // as a MAIN activity), then use our default content provider.
        Intent intent = getIntent();
        if (intent.getData() == null) {
            intent.setData(Notes.CONTENT_URI);
        }
		
		tv = (TextView) findViewById(R.id.title);
		tv.setTextColor(Color.rgb(255, 255, 255));
		newButton = (Button) findViewById(R.id.newButton);
		newButton.setOnClickListener(this);
		listButton = (Button) findViewById(R.id.listButton);
		listButton.setOnClickListener(this);
		aboutButton = (Button) findViewById(R.id.aboutButton);
		aboutButton.setOnClickListener(this);
		exitButton = (Button) findViewById(R.id.exitButton);
		exitButton.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.listButton:
			listNote();
			break;
		case R.id.aboutButton:
			Intent i = new Intent(this, About.class);
			startActivity(i);
			break;
		// More buttons go here (if any) ...
		case R.id.newButton:
			// openNewGameDialog();
			newNote();
			break;
		case R.id.exitButton:
			showExitAlert();
			break;

		}
	}

	private void listNote() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(HomeActivity.this,NotesList.class);
		startActivity(intent);
	}

	private void newNote() {
		// TODO Auto-generated method stub
		startActivity(new Intent(Intent.ACTION_INSERT, getIntent().getData()));
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
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
		alert.setMessage("Do you want to exit Yes Notepad now?");
		alert.setButton("Exit", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				finish();
				System.exit(0);
			}
		});
		alert.setButton2("Cancel", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
			}
		});
		alert.show();
	}

}
package com.fwhere.michael.compass;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DigitalCompassActivity extends Activity implements
		SensorEventListener {
	/** Called when the activity is first created. */

	private ImageView imageView;
	private float currentDegree;
	AdView adView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		adView = new AdView(this, AdSize.BANNER, "a14faa9ac6db6b9");
		// Lookup your LinearLayout assuming it’s been given
		// the attribute android:id="@+id/mainLayout"
		LinearLayout layout = (LinearLayout) findViewById(R.id.mainLayout);

		// Add the adView to it
		layout.addView(adView);

		// Initiate a generic request to load it with an ad
		adView.loadAd(new AdRequest());

		imageView = (ImageView) findViewById(R.id.imageView1);

		// 获得SensorManager对象
		SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

		// 注册加速度传感器
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_FASTEST);

		// 注册磁场传感器
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
				SensorManager.SENSOR_DELAY_FASTEST);

		// 注册光线传感器
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),
				SensorManager.SENSOR_DELAY_FASTEST);

		// 注册方向传感器
		sensorManager.registerListener(this,
				sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
				SensorManager.SENSOR_DELAY_FASTEST);

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		/**
		 * digital compass
		 */
		if (event.sensor.getType() == Sensor.TYPE_ORIENTATION)

		{

			float degree = event.values[0];

			// 以指南针图像中心为轴逆时针旋转degree度
			RotateAnimation ra = new RotateAnimation(currentDegree, -degree,

			Animation.RELATIVE_TO_SELF, 0.5f,

			Animation.RELATIVE_TO_SELF, 0.5f);

			// 在200毫秒之内完成旋转动作
			ra.setDuration(200);

			// 开始旋转图像
			imageView.startAnimation(ra);

			// 保存旋转后的度数，currentDegree是一个在类中定义的float类型变量
			currentDegree = -degree;
		}
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 如果是返回键,直接返回到桌面
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			showExitAlert();
		}
		return super.onKeyDown(keyCode, event);
	}

	public void showExitAlert() {
		AlertDialog alert = new AlertDialog.Builder(DigitalCompassActivity.this)
				.create();
		alert.setTitle("Confirm to Exit");
		alert.setMessage("Do you want to exit Digital Compass now?");
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
}
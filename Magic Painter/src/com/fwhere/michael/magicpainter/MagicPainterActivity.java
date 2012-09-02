package com.fwhere.michael.magicpainter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MagicPainterActivity extends GraphicsActivity implements
		ColorPickerDialog.OnColorChangedListener {

	int displayWidth;
	int displayHeight;
	WindowManager mWinMgr;
	Context context;
	private Bitmap mBitmap;
	private Canvas mCanvas;
	private Paint mPaint;
	private MaskFilter mEmboss;
	private MaskFilter mBlur;
	private AdView adView;

	public class MyView extends View {

		// private static final float MINP = 0.25f;
		// private static final float MAXP = 0.75f;

		private Path mPath;
		private Paint mBitmapPaint;

		public MyView(Context c) {
			super(c);

			context = MagicPainterActivity.this;
			mWinMgr = (WindowManager) context
					.getSystemService(Context.WINDOW_SERVICE);
			displayWidth = mWinMgr.getDefaultDisplay().getWidth();
			displayHeight = mWinMgr.getDefaultDisplay().getHeight() - 160;

			mBitmap = Bitmap.createBitmap(displayWidth, displayHeight,
					Bitmap.Config.ARGB_8888);
			mCanvas = new Canvas(mBitmap);
			mPath = new Path();
			mBitmapPaint = new Paint(Paint.DITHER_FLAG);
		}

		@Override
		protected void onSizeChanged(int w, int h, int oldw, int oldh) {
			super.onSizeChanged(w, h, oldw, oldh);
		}

		@Override
		protected void onDraw(Canvas canvas) {
			canvas.drawColor(Color.rgb(255, 250, 250));// CANVAS COLOR
			canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
			canvas.drawPath(mPath, mPaint);
		}

		private float mX, mY;
		private static final float TOUCH_TOLERANCE = 4;

		private void touch_start(float x, float y) {
			mPath.reset();
			mPath.moveTo(x, y);
			mX = x;
			mY = y;
		}

		private void touch_move(float x, float y) {
			float dx = Math.abs(x - mX);
			float dy = Math.abs(y - mY);
			if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
				mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
				mX = x;
				mY = y;
			}
		}

		private void touch_up() {
			mPath.lineTo(mX, mY);
			// commit the path to our offscreen
			mCanvas.drawPath(mPath, mPaint);
			// kill this so we don't double draw
			mPath.reset();
		}

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			float x = event.getX();
			float y = event.getY();

			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				touch_start(x, y);
				invalidate();
				break;
			case MotionEvent.ACTION_MOVE:
				touch_move(x, y);
				invalidate();
				break;
			case MotionEvent.ACTION_UP:
				touch_up();
				invalidate();
				break;
			}
			return true;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// setContentView(new MyView(this));
		setContentView(R.layout.main);
		
		
		
		LinearLayout mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
		mainLayout.addView(new MyView(this));
		
		adView = new AdView(this, AdSize.BANNER, "a14fae52facb80f");
		// Lookup your LinearLayout assuming it’s been given
		// the attribute android:id="@+id/mainLayout"
		LinearLayout headlayout = (LinearLayout) findViewById(R.id.headLayout);
		// Add the adView to it
		headlayout.addView(adView);
		// Initiate a generic request to load it with an ad
		adView.loadAd(new AdRequest());
		

		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setDither(true);
		mPaint.setColor(Color.rgb(102, 153, 204));
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeJoin(Paint.Join.ROUND);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStrokeWidth(12);

		mEmboss = new EmbossMaskFilter(new float[] { 1, 1, 1 }, 0.4f, 6, 3.5f);

		mBlur = new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL);
	}

	public void colorChanged(int color) {
		mPaint.setColor(color);
	}

	private static final int EMBOSS_MENU_ID = Menu.FIRST;
	private static final int BLUR_MENU_ID = Menu.FIRST + 1;
	private static final int ERASE_MENU_ID = Menu.FIRST + 2;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.mainmenu, menu);

		menu.add(0, EMBOSS_MENU_ID, 0, "Emboss").setShortcut('4', 's');
		menu.add(0, BLUR_MENU_ID, 0, "Blur").setShortcut('5', 'z');
		menu.add(0, ERASE_MENU_ID, 0, "Erase").setShortcut('5', 'z');

		/****
		 * Is this the mechanism to extend with filter effects? Intent intent =
		 * new Intent(null, getIntent().getData());
		 * intent.addCategory(Intent.CATEGORY_ALTERNATIVE);
		 * menu.addIntentOptions( Menu.ALTERNATIVE, 0, new ComponentName(this,
		 * NotesList.class), null, intent, 0, null);
		 *****/
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		mPaint.setXfermode(null);
		mPaint.setAlpha(0xFF);

		switch (item.getItemId()) {
		case R.id.newphoto:
			finish();
			Intent intent = new Intent();
			intent.setClass(MagicPainterActivity.this,
					MagicPainterActivity.class); // B为你按退出按钮所在的activity
			startActivity(intent);
			break;
		case R.id.savephoto:
			if (mBitmap != null) {
				Toast.makeText(this, "Saved to your Gallery",
						Toast.LENGTH_SHORT).show();

				SaveBitmap(mBitmap);
			}
			return true;
		case R.id.palette:
			new ColorPickerDialog(this, this, mPaint.getColor()).show();
			return true;
		case EMBOSS_MENU_ID:
			if (mPaint.getMaskFilter() != mEmboss) {
				mPaint.setMaskFilter(mEmboss);
			} else {
				mPaint.setMaskFilter(null);
			}
			return true;
		case BLUR_MENU_ID:
			if (mPaint.getMaskFilter() != mBlur) {
				mPaint.setMaskFilter(mBlur);
			} else {
				mPaint.setMaskFilter(null);
			}
			return true;
		case ERASE_MENU_ID:
			mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	// 保存到本地
	public void SaveBitmap(Bitmap bmp) {
		Bitmap newBitmap = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(),
				bmp.getConfig());
		Canvas canvas = new Canvas(newBitmap);
		canvas.drawColor(Color.rgb(255, 250, 250));// CANVAS COLOR
		canvas.drawBitmap(newBitmap, 0, 0, null);
		canvas.drawBitmap(bmp, 0, 0, null);

		// 存储路径
		String path = Environment.getExternalStorageDirectory().toString();
		File file = new File(path, "screentest.jpg");
		if (!file.exists())
			file.mkdirs();
		try {
			if (newBitmap != null) {
				try {
					OutputStream fOut = null;
					fOut = new FileOutputStream(file);
					newBitmap.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
					fOut.flush();
					fOut.close();
					Log.e("ImagePath",
							"Image Path : "
									+ MediaStore.Images.Media.insertImage(
											getContentResolver(),
											file.getAbsolutePath(),
											file.getName(), file.getName()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if (keyCode == KeyEvent.KEYCODE_BACK)
//			finish();
//		return super.onKeyDown(keyCode, event);
//	}
}

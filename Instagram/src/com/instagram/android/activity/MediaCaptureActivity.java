package com.instagram.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import com.instagram.android.Log;
import com.instagram.android.Preferences;
import com.instagram.android.fragment.CameraFragment;
import com.instagram.android.fragment.FilterFragment;
import com.instagram.camera.CameraUtil;

public class MediaCaptureActivity extends FragmentActivity
{
  private static final int ACTIVITY_REQUEST_MEDIA_PICKER = 0;
  public static final String INTENT_EXTRA_FROM_CAMERA = "fromCamera";
  public static final String INTENT_EXTRA_IS_SHARE_INTENT = "isShareIntent";
  public static final String INTENT_EXTRA_MEDIA_FILE_PATH = "mediaFilePath";
  public static final String INTENT_EXTRA_MEDIA_SOURCE = "mediaSource";
  public static final String INTENT_EXTRA_MIRROR_MEDIA = "mirrorMedia";
  public static final int MEDIA_SOURCE_FROM_CAMERA = 1;
  public static final int MEDIA_SOURCE_FROM_GALLERY = 0;
  protected static final String TAG = "MediaCaptureActivity";
  private boolean mFromCamera;
  private ActivityKeyEventDelegate mKeyEventDelegate = null;

  public void onAttachFragment(Fragment paramFragment)
  {
    super.onAttachFragment(paramFragment);
    int i = getWindow().getAttributes().format;
    if ((i == -1) || (i == 4));
    while (true)
    {
      Log.d("PixelFormat", "format = " + getWindow().getAttributes().format + ", 565 = " + 4);
      this.mKeyEventDelegate = null;
      try
      {
        this.mKeyEventDelegate = ((ActivityKeyEventDelegate)paramFragment);
        label83: return;
      }
      catch (ClassCastException localClassCastException)
      {
        break label83;
      }
    }
  }

  public void onBackPressed()
  {
    if (this.mFromCamera)
    {
      Intent localIntent = new Intent(this, MediaCaptureActivity.class);
      localIntent.setFlags(65536);
      startActivity(localIntent);
      finish();
    }
    while (true)
    {
      return;
      try
      {
        if (this.mKeyEventDelegate != null)
        {
          boolean bool = this.mKeyEventDelegate.onBackPressed();
          if (bool)
            continue;
        }
        super.onBackPressed();
      }
      catch (IllegalStateException localIllegalStateException)
      {
        while (true)
          this.mKeyEventDelegate = null;
      }
    }
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (!Preferences.getInstance(this).isLoggedIn())
      ActivityHelper.redirectToSignedOutState(this);
    getWindow().setFormat(1);
    getWindow().addFlags(4096);
    CameraUtil.initializeScreenBrightness(getWindow(), getContentResolver());
    setContentView(2130903052);
    Intent localIntent = getIntent();
    Bundle localBundle = localIntent.getExtras();
    this.mFromCamera = localIntent.getBooleanExtra("fromCamera", false);
    String str;
    if (localIntent.hasExtra("mediaFilePath"))
      str = localBundle.getString("mediaFilePath");
    for (Object localObject = FilterFragment.newInstance(localBundle.getInt("mediaSource", 1), localBundle.getBoolean("isShareIntent", false), str, localBundle.getBoolean("mirrorMedia", false)); ; localObject = new CameraFragment())
    {
      if (getSupportFragmentManager().findFragmentById(2131492900) == null)
      {
        FragmentTransaction localFragmentTransaction = getSupportFragmentManager().beginTransaction();
        localFragmentTransaction.replace(2131492900, (Fragment)localObject);
        localFragmentTransaction.commit();
      }
      return;
    }
  }

  protected void onDestroy()
  {
    super.onDestroy();
    this.mKeyEventDelegate = null;
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if ((this.mKeyEventDelegate != null) && (this.mKeyEventDelegate.onKeyDown(paramInt, paramKeyEvent)));
    for (boolean bool = true; ; bool = super.onKeyDown(paramInt, paramKeyEvent))
      return bool;
  }

  public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent)
  {
    try
    {
      if (this.mKeyEventDelegate != null)
      {
        boolean bool2 = this.mKeyEventDelegate.onKeyUp(paramInt, paramKeyEvent);
        if (bool2)
        {
          bool1 = true;
          return bool1;
        }
      }
    }
    catch (IllegalStateException localIllegalStateException)
    {
      while (true)
      {
        this.mKeyEventDelegate = null;
        boolean bool1 = super.onKeyUp(paramInt, paramKeyEvent);
      }
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.activity.MediaCaptureActivity
 * JD-Core Version:    0.6.0
 */
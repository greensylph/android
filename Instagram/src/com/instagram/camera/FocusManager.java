package com.instagram.camera;

import android.annotation.TargetApi;
import android.content.res.AssetFileDescriptor;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.Camera.Area;
import android.hardware.Camera.Parameters;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout.LayoutParams;
import com.instagram.android.Log;
import com.instagram.camera.ui.FocusIndicator;
import com.instagram.camera.ui.FocusIndicatorView;
import java.util.ArrayList;
import java.util.List;

public class FocusManager
{
  private static final int RESET_TOUCH_FOCUS = 0;
  private static final int RESET_TOUCH_FOCUS_DELAY = 3000;
  private static final int STATE_FAIL = 4;
  private static final int STATE_FOCUSING = 1;
  private static final int STATE_FOCUSING_SNAP_ON_FINISH = 2;
  private static final int STATE_IDLE = 0;
  private static final int STATE_SUCCESS = 3;
  private static final String TAG = "FocusManager";
  private boolean mAeAwbLock;
  private String mDefaultFocusMode;
  private List<Camera.Area> mFocusArea;
  private boolean mFocusAreaSupported;
  private FocusIndicatorView mFocusIndicator;
  private View mFocusIndicatorRotateLayout;
  private String mFocusMode;
  private Handler mHandler;
  private boolean mInitialized;
  Listener mListener;
  private boolean mLockAeAwbNeeded;
  private Matrix mMatrix;
  private List<Camera.Area> mMeteringArea;
  private String mOverrideFocusMode;
  private Camera.Parameters mParameters;
  private ComboPreferences mPreferences;
  private View mPreviewFrame;
  private SoundPlayer mSoundPlayer;
  private int mState = 0;

  public FocusManager(ComboPreferences paramComboPreferences, String paramString)
  {
    this.mPreferences = paramComboPreferences;
    this.mDefaultFocusMode = paramString;
    this.mHandler = new MainHandler(null);
    this.mMatrix = new Matrix();
  }

  private void autoFocus()
  {
    Log.v("FocusManager", "Start autofocus.");
    this.mListener.autoFocus();
    this.mState = 1;
    updateFocusUI();
    this.mHandler.removeMessages(0);
  }

  private void cancelAutoFocus()
  {
    Log.v("FocusManager", "Cancel autofocus.");
    resetTouchFocus();
    this.mListener.cancelAutoFocus();
    this.mState = 0;
    updateFocusUI();
    this.mHandler.removeMessages(0);
  }

  private void capture()
  {
    if (this.mListener.capture())
    {
      this.mState = 0;
      this.mHandler.removeMessages(0);
    }
  }

  @TargetApi(14)
  private void getLockAeAwbNeeded()
  {
    if ((this.mParameters.isAutoExposureLockSupported()) || (this.mParameters.isAutoWhiteBalanceLockSupported()));
    for (boolean bool = true; ; bool = false)
    {
      this.mLockAeAwbNeeded = bool;
      return;
    }
  }

  @TargetApi(14)
  private int getMaxNumFocusAreas()
  {
    return this.mParameters.getMaxNumFocusAreas();
  }

  private static boolean isSupported(String paramString, List<String> paramList)
  {
    if ((paramList != null) && (paramList.indexOf(paramString) >= 0));
    for (int i = 1; ; i = 0)
      return i;
  }

  private boolean needAutoFocusCall()
  {
    String str = getFocusMode();
    if ((!str.equals("infinity")) && (!str.equals("fixed")) && (!str.equals("edof")));
    for (int i = 1; ; i = 0)
      return i;
  }

  public void calculateTapArea(int paramInt1, int paramInt2, float paramFloat, int paramInt3, int paramInt4, int paramInt5, int paramInt6, Rect paramRect)
  {
    int i = (int)(paramFloat * paramInt1);
    int j = (int)(paramFloat * paramInt2);
    int k = CameraUtil.clamp(paramInt3 - i / 2, 0, paramInt5 - i);
    int m = CameraUtil.clamp(paramInt4 - j / 2, 0, paramInt6 - j);
    RectF localRectF = new RectF(k, m, k + i, m + j);
    this.mMatrix.mapRect(localRectF);
    CameraUtil.rectFToRect(localRectF, paramRect);
  }

  public void doSnap()
  {
    if (!this.mInitialized);
    while (true)
    {
      return;
      if ((!needAutoFocusCall()) || (this.mState == 3) || (this.mState == 4))
      {
        capture();
        continue;
      }
      if (this.mState == 1)
      {
        this.mState = 2;
        continue;
      }
      if (this.mState != 0)
        continue;
      capture();
    }
  }

  public boolean getAeAwbLock()
  {
    return this.mAeAwbLock;
  }

  public List<Camera.Area> getFocusAreas()
  {
    return this.mFocusArea;
  }

  public String getFocusMode()
  {
    String str;
    if (this.mOverrideFocusMode != null)
    {
      str = this.mOverrideFocusMode;
      return str;
    }
    if ((this.mFocusAreaSupported) && (this.mFocusArea != null))
    {
      this.mFocusMode = "auto";
      label34: if (!isSupported(this.mFocusMode, this.mParameters.getSupportedFocusModes()))
        if (!isSupported("auto", this.mParameters.getSupportedFocusModes()))
          break label100;
    }
    label100: for (this.mFocusMode = "auto"; ; this.mFocusMode = this.mParameters.getFocusMode())
    {
      str = this.mFocusMode;
      break;
      this.mFocusMode = this.mPreferences.getString("pref_camera_focusmode_key", this.mDefaultFocusMode);
      break label34;
    }
  }

  public List<Camera.Area> getMeteringAreas()
  {
    return this.mMeteringArea;
  }

  public void initialize(View paramView1, View paramView2, Listener paramListener, boolean paramBoolean, int paramInt)
  {
    this.mFocusIndicatorRotateLayout = paramView1;
    this.mFocusIndicator = ((FocusIndicatorView)paramView1.findViewById(2131492924));
    this.mPreviewFrame = paramView2;
    this.mListener = paramListener;
    Matrix localMatrix = new Matrix();
    CameraUtil.prepareMatrix(localMatrix, paramBoolean, paramInt, paramView2.getWidth(), paramView2.getHeight());
    localMatrix.invert(this.mMatrix);
    if (this.mParameters != null)
      this.mInitialized = true;
    while (true)
    {
      updateFocusUI();
      resetTouchFocus();
      return;
      Log.e("FocusManager", "mParameters is not initialized.");
    }
  }

  public void initializeParameters(Camera.Parameters paramParameters)
  {
    this.mParameters = paramParameters;
    int i = 0;
    if (Build.VERSION.SDK_INT >= 14)
      i = getMaxNumFocusAreas();
    if ((i > 0) && (isSupported("auto", this.mParameters.getSupportedFocusModes())));
    for (boolean bool = true; ; bool = false)
    {
      this.mFocusAreaSupported = bool;
      if (Build.VERSION.SDK_INT >= 14)
        getLockAeAwbNeeded();
      return;
    }
  }

  public void initializeSoundPlayer(AssetFileDescriptor paramAssetFileDescriptor)
  {
    this.mSoundPlayer = new SoundPlayer(paramAssetFileDescriptor);
  }

  public boolean isFocusCompleted()
  {
    if ((this.mState == 3) || (this.mState == 4));
    for (int i = 1; ; i = 0)
      return i;
  }

  public void onAutoFocus(boolean paramBoolean)
  {
    if (this.mState == 2)
      if (paramBoolean)
      {
        this.mState = 3;
        updateFocusUI();
        capture();
      }
    while (true)
    {
      return;
      this.mState = 4;
      break;
      if (this.mState == 1)
      {
        if (paramBoolean)
        {
          this.mState = 3;
          if ((!"continuous-picture".equals(this.mFocusMode)) && (this.mSoundPlayer != null))
            this.mSoundPlayer.play();
        }
        while (true)
        {
          updateFocusUI();
          if ((this.mFocusArea == null) && (Build.VERSION.SDK_INT >= 14))
            break;
          this.mHandler.sendEmptyMessageDelayed(0, 3000L);
          break;
          this.mState = 4;
        }
      }
      if (this.mState != 0)
        continue;
    }
  }

  public void onCameraReleased()
  {
    onPreviewStopped();
  }

  public void onPreviewStarted()
  {
    this.mState = 0;
  }

  public void onPreviewStopped()
  {
    this.mState = 0;
    resetTouchFocus();
    updateFocusUI();
  }

  public void onShutter()
  {
    resetTouchFocus();
    updateFocusUI();
  }

  public void onShutterDown()
  {
    if (!this.mInitialized);
    while (true)
    {
      return;
      if ((this.mLockAeAwbNeeded) && (!this.mAeAwbLock))
      {
        this.mAeAwbLock = true;
        this.mListener.setFocusParameters();
      }
      if ((!needAutoFocusCall()) || (this.mState == 3) || (this.mState == 4))
        continue;
      autoFocus();
    }
  }

  public void onShutterUp()
  {
    if (!this.mInitialized);
    while (true)
    {
      return;
      if ((needAutoFocusCall()) && ((this.mState == 1) || (this.mState == 3) || (this.mState == 4)))
        cancelAutoFocus();
      if ((!this.mLockAeAwbNeeded) || (!this.mAeAwbLock) || (this.mState == 2))
        continue;
      this.mAeAwbLock = false;
      this.mListener.setFocusParameters();
    }
  }

  public boolean onTouch(MotionEvent paramMotionEvent)
  {
    if ((!this.mInitialized) || (this.mState == 2));
    for (int i = 0; ; i = 1)
    {
      return i;
      if (Build.VERSION.SDK_INT >= 14)
        break;
      autoFocus();
    }
    if ((this.mFocusArea != null) && ((this.mState == 1) || (this.mState == 3) || (this.mState == 4)))
      cancelAutoFocus();
    int j = Math.round(paramMotionEvent.getX());
    int k = Math.round(paramMotionEvent.getY());
    int m = this.mFocusIndicatorRotateLayout.getWidth();
    int n = this.mFocusIndicatorRotateLayout.getHeight();
    int i1 = this.mPreviewFrame.getWidth();
    int i2 = this.mPreviewFrame.getHeight();
    if (this.mFocusArea == null)
    {
      this.mFocusArea = new ArrayList();
      this.mFocusArea.add(new Camera.Area(new Rect(), 1));
      this.mMeteringArea = new ArrayList();
      this.mMeteringArea.add(new Camera.Area(new Rect(), 1));
    }
    calculateTapArea(m, n, 1.0F, j, k, i1, i2, ((Camera.Area)this.mFocusArea.get(0)).rect);
    calculateTapArea(m, n, 1.5F, j, k, i1, i2, ((Camera.Area)this.mMeteringArea.get(0)).rect);
    RelativeLayout.LayoutParams localLayoutParams = (RelativeLayout.LayoutParams)this.mFocusIndicatorRotateLayout.getLayoutParams();
    localLayoutParams.setMargins(CameraUtil.clamp(j - m / 2, 0, i1 - m), CameraUtil.clamp(k - n / 2, 0, i2 - n), 0, 0);
    localLayoutParams.getRules()[13] = 0;
    this.mFocusIndicatorRotateLayout.requestLayout();
    this.mListener.setFocusParameters();
    if ((this.mFocusAreaSupported) && (paramMotionEvent.getAction() == 1))
      autoFocus();
    while (true)
    {
      i = 1;
      break;
      updateFocusUI();
      this.mHandler.removeMessages(0);
      this.mHandler.sendEmptyMessageDelayed(0, 3000L);
    }
  }

  public void overrideFocusMode(String paramString)
  {
    this.mOverrideFocusMode = paramString;
  }

  public void releaseSoundPlayer()
  {
    if (this.mSoundPlayer != null)
    {
      this.mSoundPlayer.release();
      this.mSoundPlayer = null;
    }
  }

  public void removeMessages()
  {
    this.mHandler.removeMessages(0);
  }

  public void resetTouchFocus()
  {
    if (!this.mInitialized);
    while (true)
    {
      return;
      ViewGroup.LayoutParams localLayoutParams = this.mFocusIndicator.getLayoutParams();
      RelativeLayout.LayoutParams localLayoutParams1 = (RelativeLayout.LayoutParams)this.mFocusIndicatorRotateLayout.getLayoutParams();
      localLayoutParams1.setMargins(this.mPreviewFrame.getWidth() / 2 - localLayoutParams.width / 2, this.mPreviewFrame.getWidth() / 2 - localLayoutParams.height / 2, 0, 0);
      localLayoutParams1.getRules()[13] = 0;
      this.mFocusIndicatorRotateLayout.requestLayout();
      this.mFocusArea = null;
      this.mMeteringArea = null;
    }
  }

  public void setAeAwbLock(boolean paramBoolean)
  {
    this.mAeAwbLock = paramBoolean;
  }

  public void updateFocusUI()
  {
    if (!this.mInitialized);
    while (true)
    {
      return;
      int i = Math.min(this.mPreviewFrame.getWidth(), this.mPreviewFrame.getHeight()) / 4;
      ViewGroup.LayoutParams localLayoutParams = this.mFocusIndicator.getLayoutParams();
      localLayoutParams.width = i;
      localLayoutParams.height = i;
      FocusIndicatorView localFocusIndicatorView = this.mFocusIndicator;
      if (this.mState == 0)
      {
        if (this.mFocusArea == null)
        {
          localFocusIndicatorView.clear();
          continue;
        }
        localFocusIndicatorView.showStart();
        continue;
      }
      if ((this.mState == 1) || (this.mState == 2))
      {
        localFocusIndicatorView.showStart();
        continue;
      }
      if ("continuous-picture".equals(this.mFocusMode))
      {
        localFocusIndicatorView.showStart();
        continue;
      }
      if (this.mState == 3)
      {
        localFocusIndicatorView.showSuccess();
        continue;
      }
      if (this.mState != 4)
        continue;
      localFocusIndicatorView.showFail();
    }
  }

  public static abstract interface Listener
  {
    public abstract void autoFocus();

    public abstract void cancelAutoFocus();

    public abstract boolean capture();

    public abstract void setFocusParameters();
  }

  private class MainHandler extends Handler
  {
    private MainHandler()
    {
    }

    public void handleMessage(Message paramMessage)
    {
      switch (paramMessage.what)
      {
      default:
      case 0:
      }
      while (true)
      {
        return;
        FocusManager.this.cancelAutoFocus();
      }
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.camera.FocusManager
 * JD-Core Version:    0.6.0
 */
package com.instagram.android.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.hardware.Camera.Size;
import android.location.Location;
import android.media.CameraProfile;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.os.MessageQueue.IdleHandler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;
import com.instagram.android.Log;
import com.instagram.android.activity.ActivityKeyEventDelegate;
import com.instagram.android.activity.MediaCaptureActivity;
import com.instagram.android.service.AppContext;
import com.instagram.android.widget.ActionBarCameraFlashButton;
import com.instagram.android.widget.ActionBarCameraFlashButton.FlashButtonOnClickListener;
import com.instagram.android.widget.ActionBarCameraFlashButton.FlashMode;
import com.instagram.android.widget.ActionBarHighlightButton;
import com.instagram.camera.CameraDisabledException;
import com.instagram.camera.CameraErrorCallback;
import com.instagram.camera.CameraHardwareException;
import com.instagram.camera.CameraHolder;
import com.instagram.camera.CameraSettings;
import com.instagram.camera.CameraUtil;
import com.instagram.camera.ComboPreferences;
import com.instagram.camera.Exif;
import com.instagram.camera.FocusManager;
import com.instagram.camera.FocusManager.Listener;
import com.instagram.camera.ShutterButton;
import com.instagram.camera.ShutterButton.OnShutterButtonListener;
import com.instagram.camera.SquarePreviewFrameLayout;
import com.instagram.camera.Storage;
import com.instagram.camera.ui.RotateLayout;
import com.instagram.util.CameraUsageReportingUtil;
import com.instagram.util.FileUtil;
import com.instagram.util.GalleryUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@TargetApi(9)
public class CameraFragment extends Fragment
  implements FocusManager.Listener, View.OnTouchListener, ShutterButton.OnShutterButtonListener, SurfaceHolder.Callback, ActivityKeyEventDelegate, View.OnClickListener
{
  private static final int ACTIVITY_REQUEST_LIBRARY = 1;
  private static final String BUNDLE_TEMP_FILE_GALLERY = "tempFileGallery";
  private static final int CHECK_DISPLAY_ROTATION = 2;
  private static final int CLEAR_SCREEN_DELAY = 3;
  private static final int FIRST_TIME_INIT = 1;
  private static final int FOCUSING = 2;
  private static final int IDLE = 1;
  private static final int PREVIEW_STOPPED = 0;
  private static final int REQUEST_CROP_PHOTO = 1;
  private static final int REQUEST_PICK_FROM_GALLERY = 0;
  private static final int SCREEN_DELAY = 120000;
  private static final int SET_CAMERA_PARAMETERS_WHEN_IDLE = 4;
  private static final int SHOW_TAP_TO_FOCUS_TOAST = 6;
  private static final int SNAPSHOT_IN_PROGRESS = 3;
  private static final String TAG = "CameraFragment";
  private static final int UPDATE_PARAM_ALL = -1;
  private static final int UPDATE_PARAM_INITIALIZE = 1;
  private static final int UPDATE_PARAM_PREFERENCE = 4;
  private static final int UPDATE_PARAM_ZOOM = 2;
  private boolean mAeLockSupported;
  private final AutoFocusCallback mAutoFocusCallback = new AutoFocusCallback(null);
  private boolean mAutoFocusModeEnabled;
  private boolean mAwbLockSupported;
  private int mBackCameraId;
  Camera mCamera;
  private Camera mCameraDevice;
  private boolean mCameraDisabled = false;
  private int mCameraId;
  private boolean mCameraNotSupported = false;
  Thread mCameraOpenThread = null;
  Thread mCameraPreviewThread = null;
  private int mCameraState = 0;
  private ContentResolver mContentResolver;
  int mDefaultCameraId;
  private boolean mDidRegister = false;
  private int mDisplayOrientation;
  private int mDisplayRotation;
  private final CameraErrorCallback mErrorCallback = new CameraErrorCallback();
  private boolean mFirstTimeInitialized;
  private boolean mFocusAreaSupported;
  private RotateLayout mFocusIndicator;
  private FocusManager mFocusManager;
  private int mFrontCameraId;
  private File mGalleryTempFile;
  private final Handler mHandler = new MainHandler(null);
  private ImageSaver mImageSaver;
  private Uri mImageUri = null;
  private Camera.Parameters mInitialParams;
  private ActionBarHighlightButton mLibraryButton;
  private boolean mMeteringAreaSupported;
  int mNumberOfCameras;
  private long mOnResumeTime;
  private boolean mOpenCameraFail = false;
  private int mOrientation = -1;
  private int mOrientationCompensation = 0;
  private MyOrientationEventListener mOrientationListener;
  private Camera.Parameters mParameters;
  private boolean mPausing;
  private long mPicturesRemaining;
  private ComboPreferences mPreferences;
  private View mPreviewFrame;
  private SquarePreviewFrameLayout mPreviewFrameLayout;
  private View mPreviewPanel;
  private final BroadcastReceiver mReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramContext, Intent paramIntent)
    {
      String str = paramIntent.getAction();
      Log.d("CameraFragment", "Received intent action=" + str);
      if ((str.equals("android.intent.action.MEDIA_MOUNTED")) || (str.equals("android.intent.action.MEDIA_UNMOUNTED")) || (str.equals("android.intent.action.MEDIA_CHECKING")))
        CameraFragment.this.checkStorage();
      while (true)
      {
        return;
        if (str.equals("android.intent.action.MEDIA_SCANNER_FINISHED"))
        {
          CameraFragment.this.checkStorage();
          continue;
        }
      }
    }
  };
  private String mSceneMode;
  private ShutterButton mShutterButton;
  private final ShutterCallback mShutterCallback = new ShutterCallback(null);
  private SurfaceHolder mSurfaceHolder = null;
  private int mUpdateSet;

  private void addIdleHandler()
  {
    Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler()
    {
      public boolean queueIdle()
      {
        Storage.ensureOSXCompatible();
        return false;
      }
    });
  }

  private boolean canTakePicture()
  {
    return isCameraIdle();
  }

  private void checkStorage()
  {
    this.mPicturesRemaining = Storage.getAvailableSpace();
    if (this.mPicturesRemaining > 50000000L)
      this.mPicturesRemaining = ((this.mPicturesRemaining - 50000000L) / 1500000L);
    while (true)
    {
      updateStorageHint();
      return;
      if (this.mPicturesRemaining <= 0L)
        continue;
      this.mPicturesRemaining = 0L;
    }
  }

  private void closeCamera()
  {
    if (this.mCameraDevice != null)
    {
      CameraHolder.instance().release();
      this.mCameraDevice.setErrorCallback(null);
      this.mCameraDevice = null;
      this.mCameraState = 0;
      this.mFocusManager.onCameraReleased();
    }
  }

  @TargetApi(14)
  private int getMaxFocusAreas()
  {
    return this.mInitialParams.getMaxNumFocusAreas();
  }

  private void getPreferredCameraId()
  {
    this.mPreferences = new ComboPreferences(getActivity());
    CameraSettings.upgradeGlobalPreferences(this.mPreferences.getGlobal());
    this.mCameraId = CameraSettings.readPreferredCameraId(this.mPreferences);
  }

  private void initializeCapabilities()
  {
    boolean bool1 = true;
    this.mInitialParams = this.mCameraDevice.getParameters();
    this.mFocusManager.initializeParameters(this.mInitialParams);
    boolean bool2;
    if (Build.VERSION.SDK_INT >= 14)
      if ((this.mInitialParams.getMaxNumFocusAreas() > 0) && (isSupported("auto", this.mInitialParams.getSupportedFocusModes())))
      {
        bool2 = bool1;
        this.mFocusAreaSupported = bool2;
        if (this.mInitialParams.getMaxNumMeteringAreas() <= 0)
          break label108;
        label75: this.mMeteringAreaSupported = bool1;
        this.mAeLockSupported = this.mInitialParams.isAutoExposureLockSupported();
        this.mAwbLockSupported = this.mInitialParams.isAutoWhiteBalanceLockSupported();
      }
    while (true)
    {
      return;
      bool2 = false;
      break;
      label108: bool1 = false;
      break label75;
      if (!this.mFocusManager.getFocusMode().equals("auto"))
        continue;
      this.mAutoFocusModeEnabled = bool1;
    }
  }

  private void initializeFirstTime()
  {
    if (this.mFirstTimeInitialized)
      return;
    this.mOrientationListener = new MyOrientationEventListener(getActivity());
    this.mOrientationListener.enable();
    checkStorage();
    this.mContentResolver = getActivity().getContentResolver();
    this.mPreviewFrame = getActivity().findViewById(2131492933);
    this.mPreviewFrame.setOnTouchListener(this);
    this.mFocusIndicator = ((RotateLayout)getActivity().findViewById(2131492923));
    if (CameraHolder.instance().getCameraInfo()[this.mCameraId].facing == 1);
    for (boolean bool = true; ; bool = false)
    {
      this.mFocusManager.initialize(this.mFocusIndicator, this.mPreviewFrame, this, bool, this.mDisplayOrientation);
      this.mFocusManager.initializeSoundPlayer(getResources().openRawResourceFd(2131099648));
      this.mImageSaver = new ImageSaver();
      installIntentFilter();
      if ((this.mFocusAreaSupported) && (this.mPreferences.getBoolean("pref_tap_to_focus_prompt_shown_key", true)))
        this.mHandler.sendEmptyMessageDelayed(6, 1000L);
      this.mFirstTimeInitialized = true;
      addIdleHandler();
      break;
    }
  }

  @TargetApi(14)
  private void initializeModernPlatformCapabilities()
  {
    if (this.mInitialParams.getMaxNumMeteringAreas() > 0);
    for (boolean bool = true; ; bool = false)
    {
      this.mMeteringAreaSupported = bool;
      this.mAeLockSupported = this.mInitialParams.isAutoExposureLockSupported();
      this.mAwbLockSupported = this.mInitialParams.isAutoWhiteBalanceLockSupported();
      return;
    }
  }

  private void initializeSecondTime()
  {
    this.mOrientationListener.enable();
    installIntentFilter();
    this.mFocusManager.initializeSoundPlayer(getResources().openRawResourceFd(2131099648));
    this.mImageSaver = new ImageSaver();
  }

  private void installIntentFilter()
  {
    IntentFilter localIntentFilter = new IntentFilter("android.intent.action.MEDIA_MOUNTED");
    localIntentFilter.addAction("android.intent.action.MEDIA_UNMOUNTED");
    localIntentFilter.addAction("android.intent.action.MEDIA_SCANNER_FINISHED");
    localIntentFilter.addAction("android.intent.action.MEDIA_CHECKING");
    localIntentFilter.addDataScheme("file");
    getActivity().registerReceiver(this.mReceiver, localIntentFilter);
    this.mDidRegister = true;
  }

  private boolean isCameraIdle()
  {
    int i = 1;
    if ((this.mCameraState == i) || (this.mFocusManager.isFocusCompleted()));
    while (true)
    {
      return i;
      i = 0;
    }
  }

  private static boolean isSupported(String paramString, List<String> paramList)
  {
    if ((paramList != null) && (paramList.indexOf(paramString) >= 0));
    for (int i = 1; ; i = 0)
      return i;
  }

  private void keepScreenOnAWhile()
  {
    this.mHandler.removeMessages(3);
    getActivity().getWindow().addFlags(128);
    this.mHandler.sendEmptyMessageDelayed(3, 120000L);
  }

  private void overrideCameraSettings(String paramString1, String paramString2, String paramString3)
  {
  }

  private void resetExposureCompensation()
  {
    if (!"0".equals(this.mPreferences.getString("pref_camera_exposure_key", "0")))
    {
      SharedPreferences.Editor localEditor = this.mPreferences.edit();
      localEditor.putString("pref_camera_exposure_key", "0");
      localEditor.apply();
    }
  }

  private void resetScreenOn()
  {
    this.mHandler.removeMessages(3);
    getActivity().getWindow().clearFlags(128);
  }

  private void setCameraParameters(int paramInt)
  {
    this.mParameters = this.mCameraDevice.getParameters();
    if ((paramInt & 0x1) != 0)
      updateCameraParametersInitialize();
    if ((paramInt & 0x4) != 0)
      updateCameraParametersPreference();
    try
    {
      this.mCameraDevice.setParameters(this.mParameters);
      return;
    }
    catch (RuntimeException localRuntimeException)
    {
      while (true)
      {
        Log.e("CameraFragment", "Failed to set parameters", localRuntimeException);
        this.mHandler.post(new Runnable()
        {
          public void run()
          {
            CameraFragment.this.getActivity().finish();
          }
        });
      }
    }
  }

  private void setCameraParametersWhenIdle(int paramInt)
  {
    this.mUpdateSet = (paramInt | this.mUpdateSet);
    if (this.mCameraDevice == null)
      this.mUpdateSet = 0;
    while (true)
    {
      return;
      if (isCameraIdle())
      {
        setCameraParameters(this.mUpdateSet);
        updateSceneModeUI();
        this.mUpdateSet = 0;
        continue;
      }
      if (this.mHandler.hasMessages(4))
        continue;
      this.mHandler.sendEmptyMessageDelayed(4, 1000L);
    }
  }

  private void setDisplayOrientation()
  {
    this.mDisplayRotation = CameraUtil.getDisplayRotation(getActivity());
    this.mDisplayOrientation = CameraUtil.getDisplayOrientation(this.mDisplayRotation, this.mCameraId);
    this.mCameraDevice.setDisplayOrientation(this.mDisplayOrientation);
  }

  private void setOrientationIndicator(int paramInt)
  {
    if (this.mFocusIndicator != null)
      this.mFocusIndicator.setOrientation(paramInt);
  }

  private void setPreviewDisplay(SurfaceHolder paramSurfaceHolder)
  {
    try
    {
      this.mCameraDevice.setPreviewDisplay(paramSurfaceHolder);
      return;
    }
    catch (Throwable localThrowable)
    {
      closeCamera();
    }
    throw new RuntimeException("setPreviewDisplay failed", localThrowable);
  }

  @TargetApi(14)
  private void setRecordingHint()
  {
    this.mParameters.setRecordingHint(false);
  }

  private void showGallery()
  {
    CameraUsageReportingUtil.didOpenBuiltinGallery(true);
    this.mGalleryTempFile = FileUtil.generateTempFile(AppContext.getContext());
    GalleryUtil.show(this, 0, this.mGalleryTempFile);
  }

  private void startPreview()
  {
    if ((this.mPausing) || (getActivity().isFinishing()));
    while (true)
    {
      return;
      this.mCameraDevice.setErrorCallback(this.mErrorCallback);
      if (this.mCameraState != 0)
        stopPreview();
      setPreviewDisplay(this.mSurfaceHolder);
      setDisplayOrientation();
      this.mFocusManager.setAeAwbLock(false);
      setCameraParameters(-1);
      if (this.mCameraPreviewThread != null)
        synchronized (this.mCameraPreviewThread)
        {
          this.mCameraPreviewThread.notify();
        }
      try
      {
        Log.v("CameraFragment", "startPreview");
        this.mCameraDevice.startPreview();
        this.mCameraState = 1;
        this.mFocusManager.onPreviewStarted();
        continue;
        localObject = finally;
        monitorexit;
        throw localObject;
      }
      catch (Throwable localThrowable)
      {
        closeCamera();
      }
    }
    throw new RuntimeException("startPreview failed", localThrowable);
  }

  private void stopPreview()
  {
    if ((this.mCameraDevice != null) && (this.mCameraState != 0))
    {
      Log.v("CameraFragment", "stopPreview");
      this.mCameraDevice.stopPreview();
    }
    this.mCameraState = 0;
    this.mFocusManager.onPreviewStopped();
  }

  private void updateCameraParametersInitialize()
  {
    List localList = this.mParameters.getSupportedPreviewFrameRates();
    if (localList != null)
    {
      Integer localInteger = (Integer)Collections.max(localList);
      this.mParameters.setPreviewFrameRate(localInteger.intValue());
    }
    if (Build.VERSION.SDK_INT >= 14)
      setRecordingHint();
    if ("true".equals(this.mParameters.get("video-stabilization-supported")))
      this.mParameters.set("video-stabilization", "false");
  }

  private void updateCameraParametersPreference()
  {
    if (Build.VERSION.SDK_INT >= 14)
      updateModernCameraParametersPreference();
    String str1 = this.mPreferences.getString("pref_camera_picturesize_key", null);
    label315: int j;
    label380: List localList3;
    label440: label483: label505: ActionBarCameraFlashButton localActionBarCameraFlashButton;
    String str2;
    List localList2;
    label560: View localView;
    if (str1 == null)
    {
      CameraSettings.initialCameraPictureSize(getActivity(), this.mParameters);
      Camera.Size localSize1 = this.mParameters.getPictureSize();
      this.mPreviewPanel = getView().findViewById(2131492925);
      List localList1 = this.mParameters.getSupportedPreviewSizes();
      Camera.Size localSize2 = CameraUtil.getOptimalPreviewSizeForPortrait(getActivity(), localList1, localSize1.height / localSize1.width);
      if (!this.mParameters.getPreviewSize().equals(localSize2))
      {
        this.mParameters.setPreviewSize(localSize2.width, localSize2.height);
        this.mCameraDevice.setParameters(this.mParameters);
        this.mParameters = this.mCameraDevice.getParameters();
      }
      Log.v("CameraFragment", "Preview size is " + localSize2.width + "x" + localSize2.height);
      this.mPreviewFrameLayout = ((SquarePreviewFrameLayout)getView().findViewById(2131492932));
      this.mPreviewFrameLayout.setAspectRatio(localSize2.height / localSize2.width);
      this.mSceneMode = this.mPreferences.getString("pref_camera_scenemode_key", getString(2131230901));
      if (!isSupported(this.mSceneMode, this.mParameters.getSupportedSceneModes()))
        break label634;
      if (!this.mParameters.getSceneMode().equals(this.mSceneMode))
      {
        this.mParameters.setSceneMode(this.mSceneMode);
        this.mCameraDevice.setParameters(this.mParameters);
        this.mParameters = this.mCameraDevice.getParameters();
      }
      int i = CameraProfile.getJpegEncodingQualityParameter(this.mCameraId, 2);
      this.mParameters.setJpegQuality(i);
      j = CameraSettings.readExposure(this.mPreferences);
      int k = this.mParameters.getMaxExposureCompensation();
      if ((j < this.mParameters.getMinExposureCompensation()) || (j > k))
        break label662;
      this.mParameters.setExposureCompensation(j);
      if (!"auto".equals(this.mSceneMode))
        break label781;
      String str3 = this.mPreferences.getString("pref_camera_flashmode_key", getString(2131230902));
      localList3 = this.mParameters.getSupportedFlashModes();
      if (!isSupported(str3, localList3))
        break label692;
      this.mParameters.setFlashMode(str3);
      String str5 = this.mPreferences.getString("pref_camera_whitebalance_key", getString(2131230905));
      if (!isSupported(str5, this.mParameters.getSupportedWhiteBalance()))
        break label758;
      this.mParameters.setWhiteBalance(str5);
      this.mFocusManager.overrideFocusMode(null);
      this.mParameters.setFocusMode(this.mFocusManager.getFocusMode());
      localActionBarCameraFlashButton = (ActionBarCameraFlashButton)getView().findViewById(2131492881);
      str2 = this.mParameters.getFlashMode();
      localList2 = this.mParameters.getSupportedFlashModes();
      if ((localList2 != null) && (localList2.size() != 0))
        break label798;
      localActionBarCameraFlashButton.setVisibility(8);
      6 local6 = new ActionBarCameraFlashButton.FlashButtonOnClickListener()
      {
        public void onClick(View paramView, ActionBarCameraFlashButton.FlashMode paramFlashMode)
        {
          String str;
          switch (CameraFragment.9.$SwitchMap$com$instagram$android$widget$ActionBarCameraFlashButton$FlashMode[paramFlashMode.ordinal()])
          {
          default:
            str = CameraFragment.this.getString(2131230902);
            CameraFragment.this.mParameters.setFlashMode("off");
          case 1:
          case 2:
          }
          while (true)
          {
            SharedPreferences.Editor localEditor = CameraFragment.this.mPreferences.edit();
            localEditor.putString("pref_camera_flashmode_key", str);
            localEditor.commit();
            CameraFragment.this.setCameraParameters(4);
            return;
            str = CameraFragment.this.getString(2131230903);
            CameraFragment.this.mParameters.setFlashMode("on");
            continue;
            str = CameraFragment.this.getString(2131230904);
            CameraFragment.this.mParameters.setFlashMode("auto");
          }
        }
      };
      localActionBarCameraFlashButton.setOnClickListener(local6);
      localView = getView().findViewById(2131492882);
      if (this.mNumberOfCameras <= 1)
        break label894;
      7 local7 = new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          ComboPreferences localComboPreferences = CameraFragment.this.mPreferences;
          if (CameraFragment.this.mCameraId == CameraFragment.this.mFrontCameraId);
          for (int i = CameraFragment.this.mBackCameraId; ; i = CameraFragment.this.mFrontCameraId)
          {
            CameraSettings.writePreferredCameraId(localComboPreferences, i);
            CameraFragment.this.onSharedPreferenceChanged();
            return;
          }
        }
      };
      localView.setOnClickListener(local7);
    }
    while (true)
    {
      return;
      CameraSettings.setCameraPictureSize(str1, this.mParameters.getSupportedPictureSizes(), this.mParameters);
      break;
      label634: this.mSceneMode = this.mParameters.getSceneMode();
      if (this.mSceneMode != null)
        break label315;
      this.mSceneMode = "auto";
      break label315;
      label662: Log.w("CameraFragment", "invalid exposure range: " + j);
      break label380;
      label692: String str4 = getString(2131230902);
      if (isSupported(str4, localList3))
      {
        this.mParameters.setFlashMode(str4);
        break label440;
      }
      Log.w("CameraFragment", "Unknown flash mode: " + this.mParameters.getFlashMode());
      break label440;
      label758: if (this.mParameters.getWhiteBalance() != null)
        break label483;
      this.mParameters.setWhiteBalance("auto");
      break label483;
      label781: this.mFocusManager.overrideFocusMode(this.mParameters.getFocusMode());
      break label505;
      label798: if ((localList2.size() == 1) && (((String)localList2.get(0)).equals("off")))
      {
        localActionBarCameraFlashButton.setVisibility(8);
        break label560;
      }
      if (str2.equals("on"))
      {
        localActionBarCameraFlashButton.showMode(ActionBarCameraFlashButton.FlashMode.ON);
        break label560;
      }
      if (str2.equals("auto"))
      {
        localActionBarCameraFlashButton.showMode(ActionBarCameraFlashButton.FlashMode.AUTO);
        break label560;
      }
      localActionBarCameraFlashButton.showMode(ActionBarCameraFlashButton.FlashMode.OFF);
      break label560;
      label894: localView.setVisibility(8);
    }
  }

  @TargetApi(14)
  private void updateModernCameraParametersPreference()
  {
    if (this.mAeLockSupported)
      this.mParameters.setAutoExposureLock(this.mFocusManager.getAeAwbLock());
    if (this.mAwbLockSupported)
      this.mParameters.setAutoWhiteBalanceLock(this.mFocusManager.getAeAwbLock());
    if (this.mFocusAreaSupported)
      this.mParameters.setFocusAreas(this.mFocusManager.getFocusAreas());
    if (this.mMeteringAreaSupported)
      this.mParameters.setMeteringAreas(this.mFocusManager.getMeteringAreas());
  }

  private void updateSceneModeUI()
  {
    if (!"auto".equals(this.mSceneMode))
      overrideCameraSettings(this.mParameters.getFlashMode(), this.mParameters.getWhiteBalance(), this.mParameters.getFocusMode());
    while (true)
    {
      return;
      overrideCameraSettings(null, null, null);
    }
  }

  private void updateStorageHint()
  {
    String str = null;
    if (this.mPicturesRemaining == -1L)
      str = getString(2131230732);
    while (true)
    {
      if (str != null)
      {
        Toast.makeText(getActivity(), str, 1).show();
        getActivity().finish();
      }
      return;
      if (this.mPicturesRemaining == -2L)
      {
        str = getString(2131230734);
        continue;
      }
      if (this.mPicturesRemaining == -3L)
      {
        str = getString(2131230907);
        continue;
      }
      if (this.mPicturesRemaining >= 1L)
        continue;
      str = getString(2131230733);
    }
  }

  public void autoFocus()
  {
    this.mCameraDevice.autoFocus(this.mAutoFocusCallback);
    this.mCameraState = 2;
  }

  public void cancelAutoFocus()
  {
    this.mCameraDevice.cancelAutoFocus();
    this.mCameraState = 1;
    setCameraParameters(4);
  }

  public boolean capture()
  {
    if ((this.mCameraState == 3) || (this.mCameraDevice == null));
    for (int i = 0; ; i = 1)
    {
      return i;
      CameraUtil.setRotationParameter(this.mParameters, this.mCameraId, this.mOrientation);
      this.mCameraDevice.setParameters(this.mParameters);
      this.mCameraDevice.takePicture(this.mShutterCallback, null, null, new JpegPictureCallback(null));
      this.mCameraState = 3;
    }
  }

  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    this.mPausing = false;
    this.mCameraOpenThread = new Thread(new Runnable()
    {
      public void run()
      {
        try
        {
          CameraFragment.access$1102(CameraFragment.this, CameraUtil.openCamera(CameraFragment.this.getActivity(), CameraFragment.this.mCameraId));
          return;
        }
        catch (CameraHardwareException localCameraHardwareException)
        {
          while (true)
            CameraFragment.access$1302(CameraFragment.this, true);
        }
        catch (CameraDisabledException localCameraDisabledException)
        {
          while (true)
            CameraFragment.access$1402(CameraFragment.this, true);
        }
      }
    });
    this.mCameraPreviewThread = new Thread(new Runnable()
    {
      public void run()
      {
        CameraFragment.this.initializeCapabilities();
        CameraFragment.this.startPreview();
      }
    });
    this.mCameraOpenThread.start();
    this.mPreferences.setLocalId(getActivity(), this.mCameraId);
    CameraSettings.upgradeLocalPreferences(this.mPreferences.getLocal());
    this.mNumberOfCameras = CameraHolder.instance().getNumberOfCameras();
    resetExposureCompensation();
    try
    {
      this.mCameraOpenThread.join();
      this.mCameraOpenThread = null;
      if (this.mOpenCameraFail)
        CameraUtil.showErrorAndFinish(getActivity(), 2131230900);
      else if (this.mCameraDisabled)
        CameraUtil.showErrorAndFinish(getActivity(), 2131230900);
    }
    catch (InterruptedException localInterruptedException1)
    {
      this.mCameraPreviewThread.start();
      this.mBackCameraId = CameraHolder.instance().getBackCameraId();
      this.mFrontCameraId = CameraHolder.instance().getFrontCameraId();
      try
      {
        label188: synchronized (this.mCameraPreviewThread)
        {
          this.mCameraPreviewThread.wait();
        }
        try
        {
          this.mCameraPreviewThread.join();
          label197: this.mCameraPreviewThread = null;
          return;
          localObject = finally;
          monitorexit;
          throw localObject;
        }
        catch (InterruptedException localInterruptedException3)
        {
          break label197;
        }
      }
      catch (InterruptedException localInterruptedException2)
      {
        break label188;
      }
    }
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (paramInt2 != -1)
      if (paramInt1 == 1)
        showGallery();
    while (true)
    {
      return;
      switch (paramInt1)
      {
      default:
        break;
      case 0:
        GalleryUtil.handleActivityResult(this, paramIntent, this.mGalleryTempFile, 1);
        break;
      case 1:
        if (this.mGalleryTempFile != null)
          this.mGalleryTempFile.delete();
        Intent localIntent = new Intent(getActivity(), MediaCaptureActivity.class);
        localIntent.putExtra("fromCamera", true);
        localIntent.putExtra("mediaFilePath", paramIntent.getAction());
        localIntent.putExtra("mediaSource", 0);
        localIntent.setFlags(65536);
        getActivity().startActivity(localIntent);
        getActivity().finish();
      }
    }
  }

  public void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
  }

  public boolean onBackPressed()
  {
    if (!isCameraIdle());
    for (int i = 1; ; i = 0)
      return i;
  }

  public void onClick(View paramView)
  {
    if (paramView == this.mLibraryButton)
      showGallery();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    getPreferredCameraId();
    this.mFocusManager = new FocusManager(this.mPreferences, getString(2131230906));
    if (paramBundle != null)
    {
      String str = paramBundle.getString("tempFileGallery");
      if (str != null)
        this.mGalleryTempFile = new File(str);
    }
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(2130903066, paramViewGroup, false);
    SurfaceView localSurfaceView = (SurfaceView)localView.findViewById(2131492933);
    localSurfaceView.setZOrderOnTop(false);
    SurfaceHolder localSurfaceHolder = localSurfaceView.getHolder();
    localSurfaceHolder.addCallback(this);
    localSurfaceHolder.setType(3);
    this.mShutterButton = ((ShutterButton)localView.findViewById(2131492934));
    this.mShutterButton.setOnShutterButtonListener(this);
    this.mShutterButton.setVisibility(0);
    this.mLibraryButton = ((ActionBarHighlightButton)localView.findViewById(2131492935));
    this.mLibraryButton.setOnClickListener(this);
    localView.findViewById(2131492880).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        CameraFragment.this.getActivity().finish();
      }
    });
    return localView;
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    boolean bool = true;
    switch (paramInt)
    {
    default:
      bool = false;
    case 80:
    case 27:
    case 23:
    }
    do
      while (true)
      {
        return bool;
        if ((!this.mFirstTimeInitialized) || (paramKeyEvent.getRepeatCount() != 0))
          continue;
        onShutterButtonFocus(bool);
        continue;
        if ((!this.mFirstTimeInitialized) || (paramKeyEvent.getRepeatCount() != 0))
          continue;
        onShutterButtonClick();
      }
    while ((!this.mFirstTimeInitialized) || (paramKeyEvent.getRepeatCount() != 0));
    onShutterButtonFocus(bool);
    if (this.mShutterButton.isInTouchMode())
      this.mShutterButton.requestFocusFromTouch();
    while (true)
    {
      this.mShutterButton.setPressed(bool);
      break;
      this.mShutterButton.requestFocus();
    }
  }

  public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent)
  {
    int i = 0;
    switch (paramInt)
    {
    default:
    case 80:
    }
    while (true)
    {
      return i;
      if (this.mFirstTimeInitialized)
        onShutterButtonFocus(false);
      i = 1;
    }
  }

  public void onPause()
  {
    this.mPausing = true;
    stopPreview();
    closeCamera();
    resetScreenOn();
    if (this.mFirstTimeInitialized)
    {
      this.mOrientationListener.disable();
      if (this.mImageSaver != null)
      {
        this.mImageSaver.finish();
        this.mImageSaver = null;
      }
    }
    if (this.mDidRegister)
    {
      getActivity().unregisterReceiver(this.mReceiver);
      this.mDidRegister = false;
    }
    this.mFocusManager.releaseSoundPlayer();
    this.mHandler.removeMessages(1);
    this.mHandler.removeMessages(2);
    this.mFocusManager.removeMessages();
    super.onPause();
  }

  public void onResume()
  {
    super.onResume();
    this.mPausing = false;
    if ((this.mOpenCameraFail) || (this.mCameraDisabled))
      return;
    if (this.mCameraState == 0);
    while (true)
    {
      try
      {
        this.mCameraDevice = CameraUtil.openCamera(getActivity(), this.mCameraId);
        initializeCapabilities();
        resetExposureCompensation();
        startPreview();
        if (this.mSurfaceHolder == null)
          continue;
        if (this.mFirstTimeInitialized)
          break label144;
        this.mHandler.sendEmptyMessage(1);
        keepScreenOnAWhile();
        if (this.mCameraState != 1)
          break;
        this.mOnResumeTime = SystemClock.uptimeMillis();
        this.mHandler.sendEmptyMessageDelayed(2, 100L);
      }
      catch (CameraHardwareException localCameraHardwareException)
      {
        CameraUtil.showErrorAndFinish(getActivity(), 2131230900);
      }
      catch (CameraDisabledException localCameraDisabledException)
      {
        CameraUtil.showErrorAndFinish(getActivity(), 2131230900);
      }
      break;
      label144: initializeSecondTime();
    }
  }

  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if (this.mGalleryTempFile != null)
      paramBundle.putString("tempFileGallery", this.mGalleryTempFile.toString());
  }

  public void onSharedPreferenceChanged()
  {
    if (this.mPausing);
    while (true)
    {
      return;
      FragmentActivity localFragmentActivity = getActivity();
      int i = CameraSettings.readPreferredCameraId(this.mPreferences);
      if (this.mCameraId != i)
      {
        Intent localIntent = localFragmentActivity.getIntent();
        localIntent.addFlags(67108864);
        localIntent.addFlags(33554432);
        localIntent.setClassName(localFragmentActivity.getPackageName(), localFragmentActivity.getClass().getName());
        CameraHolder.instance().keep();
        try
        {
          localFragmentActivity.startActivity(localIntent);
          localFragmentActivity.overridePendingTransition(0, 0);
          localFragmentActivity.finish();
        }
        catch (ActivityNotFoundException localActivityNotFoundException)
        {
          while (true)
          {
            localIntent.setComponent(null);
            localFragmentActivity.startActivity(localIntent);
          }
        }
      }
      setCameraParametersWhenIdle(4);
    }
  }

  public void onShutterButtonClick()
  {
    if (this.mPausing);
    while (true)
    {
      return;
      this.mFocusManager.doSnap();
    }
  }

  public void onShutterButtonFocus(boolean paramBoolean)
  {
    if ((this.mPausing) || (this.mCameraState == 3));
    while (true)
    {
      return;
      if ((!paramBoolean) || (canTakePicture()))
      {
        if (paramBoolean)
        {
          this.mFocusManager.onShutterDown();
          continue;
        }
        this.mFocusManager.onShutterUp();
        continue;
      }
    }
  }

  public void onStop()
  {
    super.onStop();
    this.mFirstTimeInitialized = false;
  }

  public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
  {
    boolean bool = false;
    if ((this.mPausing) || (this.mCameraDevice == null) || (!this.mFirstTimeInitialized) || (this.mCameraState == 3));
    while (true)
    {
      return bool;
      if ((!this.mFocusAreaSupported) && (!this.mMeteringAreaSupported) && (!this.mAutoFocusModeEnabled))
        continue;
      bool = this.mFocusManager.onTouch(paramMotionEvent);
    }
  }

  public void setFocusParameters()
  {
    setCameraParameters(4);
  }

  public void surfaceChanged(SurfaceHolder paramSurfaceHolder, int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramSurfaceHolder.getSurface() == null)
      Log.d("CameraFragment", "holder.getSurface() == null");
    while (true)
    {
      return;
      Log.v("CameraFragment", "surfaceChanged. w=" + paramInt2 + ". h=" + paramInt3);
      this.mSurfaceHolder = paramSurfaceHolder;
      if ((this.mCameraDevice == null) || (this.mPausing) || (getActivity().isFinishing()))
        continue;
      if (this.mCameraState == 0)
        startPreview();
      while (true)
      {
        if (this.mFirstTimeInitialized)
          break label150;
        this.mHandler.sendEmptyMessage(1);
        break;
        if (CameraUtil.getDisplayRotation(getActivity()) != this.mDisplayRotation)
          setDisplayOrientation();
        if (!paramSurfaceHolder.isCreating())
          continue;
        setPreviewDisplay(paramSurfaceHolder);
      }
      label150: initializeSecondTime();
    }
  }

  public void surfaceCreated(SurfaceHolder paramSurfaceHolder)
  {
  }

  public void surfaceDestroyed(SurfaceHolder paramSurfaceHolder)
  {
    stopPreview();
    this.mSurfaceHolder = null;
  }

  private final class AutoFocusCallback
    implements Camera.AutoFocusCallback
  {
    private AutoFocusCallback()
    {
    }

    public void onAutoFocus(boolean paramBoolean, Camera paramCamera)
    {
      if (CameraFragment.this.mPausing);
      while (true)
      {
        return;
        CameraFragment.this.mFocusManager.onAutoFocus(paramBoolean);
        if (!CameraFragment.this.mFocusManager.isFocusCompleted())
          continue;
      }
    }
  }

  private class ImageSaver extends Thread
  {
    private static final int QUEUE_LIMIT = 3;
    private ArrayList<CameraFragment.SaveRequest> mQueue = new ArrayList();
    private boolean mStop;

    public ImageSaver()
    {
      start();
    }

    private void storeImage(byte[] paramArrayOfByte, String paramString, Location paramLocation, int paramInt1, int paramInt2, long paramLong)
    {
      int i = Exif.getOrientation(paramArrayOfByte);
      CameraFragment.access$2402(CameraFragment.this, Storage.addImage(CameraFragment.this.mContentResolver, paramString, paramLong, paramLocation, i, paramArrayOfByte, paramInt1, paramInt2));
    }

    public String addImage(byte[] paramArrayOfByte, Location paramLocation, int paramInt1, int paramInt2)
    {
      Location localLocation = null;
      CameraFragment.SaveRequest localSaveRequest = new CameraFragment.SaveRequest(null);
      localSaveRequest.data = paramArrayOfByte;
      if (paramLocation == null);
      while (true)
      {
        localSaveRequest.loc = localLocation;
        localSaveRequest.width = paramInt1;
        localSaveRequest.height = paramInt2;
        localSaveRequest.dateTaken = System.currentTimeMillis();
        localSaveRequest.title = CameraUtil.createJpegName(localSaveRequest.dateTaken);
        monitorenter;
        try
        {
          while (true)
          {
            int i = this.mQueue.size();
            if (i >= 3)
            {
              try
              {
                wait();
              }
              catch (InterruptedException localInterruptedException)
              {
              }
              continue;
              localLocation = new Location(paramLocation);
              break;
            }
          }
          this.mQueue.add(localSaveRequest);
          notifyAll();
          return localSaveRequest.title;
        }
        finally
        {
          monitorexit;
        }
      }
      throw localObject;
    }

    // ERROR //
    public void finish()
    {
      // Byte code:
      //   0: aload_0
      //   1: invokevirtual 121	com/instagram/android/fragment/CameraFragment$ImageSaver:waitDone	()V
      //   4: aload_0
      //   5: monitorenter
      //   6: aload_0
      //   7: iconst_1
      //   8: putfield 123	com/instagram/android/fragment/CameraFragment$ImageSaver:mStop	Z
      //   11: aload_0
      //   12: invokevirtual 117	java/lang/Object:notifyAll	()V
      //   15: aload_0
      //   16: monitorexit
      //   17: aload_0
      //   18: invokevirtual 126	com/instagram/android/fragment/CameraFragment$ImageSaver:join	()V
      //   21: return
      //   22: astore_1
      //   23: aload_0
      //   24: monitorexit
      //   25: aload_1
      //   26: athrow
      //   27: astore_2
      //   28: goto -7 -> 21
      //
      // Exception table:
      //   from	to	target	type
      //   6	17	22	finally
      //   23	25	22	finally
      //   17	21	27	java/lang/InterruptedException
    }

    // ERROR //
    public void run()
    {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: getfield 29	com/instagram/android/fragment/CameraFragment$ImageSaver:mQueue	Ljava/util/ArrayList;
      //   6: invokevirtual 131	java/util/ArrayList:isEmpty	()Z
      //   9: ifeq +31 -> 40
      //   12: aload_0
      //   13: invokevirtual 117	java/lang/Object:notifyAll	()V
      //   16: aload_0
      //   17: getfield 123	com/instagram/android/fragment/CameraFragment$ImageSaver:mStop	Z
      //   20: ifeq +6 -> 26
      //   23: aload_0
      //   24: monitorexit
      //   25: return
      //   26: aload_0
      //   27: invokevirtual 105	java/lang/Object:wait	()V
      //   30: aload_0
      //   31: monitorexit
      //   32: goto -32 -> 0
      //   35: astore_1
      //   36: aload_0
      //   37: monitorexit
      //   38: aload_1
      //   39: athrow
      //   40: aload_0
      //   41: getfield 29	com/instagram/android/fragment/CameraFragment$ImageSaver:mQueue	Ljava/util/ArrayList;
      //   44: iconst_0
      //   45: invokevirtual 135	java/util/ArrayList:get	(I)Ljava/lang/Object;
      //   48: checkcast 59	com/instagram/android/fragment/CameraFragment$SaveRequest
      //   51: astore_2
      //   52: aload_0
      //   53: monitorexit
      //   54: aload_0
      //   55: aload_2
      //   56: getfield 66	com/instagram/android/fragment/CameraFragment$SaveRequest:data	[B
      //   59: aload_2
      //   60: getfield 96	com/instagram/android/fragment/CameraFragment$SaveRequest:title	Ljava/lang/String;
      //   63: aload_2
      //   64: getfield 70	com/instagram/android/fragment/CameraFragment$SaveRequest:loc	Landroid/location/Location;
      //   67: aload_2
      //   68: getfield 73	com/instagram/android/fragment/CameraFragment$SaveRequest:width	I
      //   71: aload_2
      //   72: getfield 76	com/instagram/android/fragment/CameraFragment$SaveRequest:height	I
      //   75: aload_2
      //   76: getfield 86	com/instagram/android/fragment/CameraFragment$SaveRequest:dateTaken	J
      //   79: invokespecial 137	com/instagram/android/fragment/CameraFragment$ImageSaver:storeImage	([BLjava/lang/String;Landroid/location/Location;IIJ)V
      //   82: aload_0
      //   83: monitorenter
      //   84: aload_0
      //   85: getfield 29	com/instagram/android/fragment/CameraFragment$ImageSaver:mQueue	Ljava/util/ArrayList;
      //   88: iconst_0
      //   89: invokevirtual 140	java/util/ArrayList:remove	(I)Ljava/lang/Object;
      //   92: pop
      //   93: aload_0
      //   94: invokevirtual 117	java/lang/Object:notifyAll	()V
      //   97: aload_0
      //   98: monitorexit
      //   99: goto -99 -> 0
      //   102: astore_3
      //   103: aload_0
      //   104: monitorexit
      //   105: aload_3
      //   106: athrow
      //   107: astore 5
      //   109: goto -79 -> 30
      //
      // Exception table:
      //   from	to	target	type
      //   2	25	35	finally
      //   26	30	35	finally
      //   30	38	35	finally
      //   40	54	35	finally
      //   84	105	102	finally
      //   26	30	107	java/lang/InterruptedException
    }

    public void waitDone()
    {
      monitorenter;
      try
      {
        while (true)
        {
          boolean bool = this.mQueue.isEmpty();
          if (bool)
            break;
          try
          {
            wait();
          }
          catch (InterruptedException localInterruptedException)
          {
          }
        }
        return;
      }
      finally
      {
        monitorexit;
      }
      throw localObject;
    }
  }

  private final class JpegPictureCallback
    implements Camera.PictureCallback
  {
    private JpegPictureCallback()
    {
    }

    public void onPictureTaken(byte[] paramArrayOfByte, Camera paramCamera)
    {
      if (CameraFragment.this.mPausing);
      while (true)
      {
        return;
        Camera.Size localSize = CameraFragment.this.mParameters.getPictureSize();
        CameraFragment.this.mImageSaver.addImage(paramArrayOfByte, null, localSize.width, localSize.height);
        CameraFragment.this.checkStorage();
        CameraFragment.this.mImageSaver.finish();
        if (CameraFragment.this.mImageUri != null)
          break;
        CameraFragment.this.updateStorageHint();
      }
      if (CameraFragment.this.mCameraId == CameraFragment.this.mFrontCameraId);
      for (boolean bool = true; ; bool = false)
      {
        Intent localIntent = new Intent(CameraFragment.this.getActivity(), MediaCaptureActivity.class);
        localIntent.putExtra("fromCamera", true);
        localIntent.putExtra("mediaFilePath", CameraFragment.this.mImageUri.toString());
        localIntent.putExtra("mediaSource", 1);
        localIntent.putExtra("mirrorMedia", bool);
        localIntent.setFlags(65536);
        CameraFragment.this.getActivity().startActivity(localIntent);
        CameraFragment.this.getActivity().finish();
        break;
      }
    }
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
      case 3:
      default:
      case 1:
      case 4:
      case 2:
      }
      while (true)
      {
        return;
        CameraFragment.this.initializeFirstTime();
        continue;
        CameraFragment.this.setCameraParametersWhenIdle(0);
        continue;
        if (CameraUtil.getDisplayRotation(CameraFragment.this.getActivity()) != CameraFragment.this.mDisplayRotation)
          CameraFragment.this.setDisplayOrientation();
        if (SystemClock.uptimeMillis() - CameraFragment.this.mOnResumeTime >= 5000L)
          continue;
        CameraFragment.this.mHandler.sendEmptyMessageDelayed(2, 100L);
      }
    }
  }

  private class MyOrientationEventListener extends OrientationEventListener
  {
    public MyOrientationEventListener(Context arg2)
    {
      super();
    }

    public void onOrientationChanged(int paramInt)
    {
      if (paramInt == -1);
      while (true)
      {
        return;
        CameraFragment.access$2602(CameraFragment.this, CameraUtil.roundOrientation(paramInt, CameraFragment.this.mOrientation));
        int i = CameraFragment.this.mOrientation + CameraUtil.getDisplayRotation(CameraFragment.this.getActivity());
        if (CameraFragment.this.mOrientationCompensation == i)
          continue;
        CameraFragment.access$2702(CameraFragment.this, i);
        CameraFragment.this.setOrientationIndicator(CameraFragment.this.mOrientationCompensation);
      }
    }
  }

  private static class SaveRequest
  {
    byte[] data;
    long dateTaken;
    int height;
    Location loc;
    String title;
    int width;
  }

  private final class ShutterCallback
    implements Camera.ShutterCallback
  {
    private ShutterCallback()
    {
    }

    public void onShutter()
    {
      CameraFragment.this.mFocusManager.onShutter();
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.CameraFragment
 * JD-Core Version:    0.6.0
 */
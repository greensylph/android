package com.instagram.camera;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.admin.DevicePolicyManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings.System;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import com.instagram.android.Log;
import com.instagram.android.widget.IgDialogBuilder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@TargetApi(9)
public class CameraUtil
{
  private static final float DEFAULT_CAMERA_BRIGHTNESS = 0.7F;
  public static final int ORIENTATION_HYSTERESIS = 5;
  private static final String TAG = "CameraUtil";
  private static ImageFileNamer sImageFileNamer;

  public static void Assert(boolean paramBoolean)
  {
    if (!paramBoolean)
      throw new AssertionError();
  }

  @TargetApi(14)
  private static void checkCameraDisabled(DevicePolicyManager paramDevicePolicyManager)
    throws CameraDisabledException
  {
    if (paramDevicePolicyManager.getCameraDisabled(null))
      throw new CameraDisabledException();
  }

  public static int clamp(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt1 > paramInt3);
    while (true)
    {
      return paramInt3;
      if (paramInt1 < paramInt2)
      {
        paramInt3 = paramInt2;
        continue;
      }
      paramInt3 = paramInt1;
    }
  }

  public static String createJpegName(long paramLong)
  {
    synchronized (sImageFileNamer)
    {
      String str = sImageFileNamer.generateName(paramLong);
      return str;
    }
  }

  @TargetApi(11)
  public static void enterLightsOutMode(Window paramWindow)
  {
    WindowManager.LayoutParams localLayoutParams = paramWindow.getAttributes();
    localLayoutParams.systemUiVisibility = 1;
    paramWindow.setAttributes(localLayoutParams);
  }

  public static int getDisplayOrientation(int paramInt1, int paramInt2)
  {
    Camera.CameraInfo localCameraInfo = new Camera.CameraInfo();
    Camera.getCameraInfo(paramInt2, localCameraInfo);
    if (localCameraInfo.facing == 1);
    for (int i = (360 - (paramInt1 + localCameraInfo.orientation) % 360) % 360; ; i = (360 + (localCameraInfo.orientation - paramInt1)) % 360)
      return i;
  }

  public static int getDisplayRotation(Activity paramActivity)
  {
    int i = 0;
    switch (paramActivity.getWindowManager().getDefaultDisplay().getRotation())
    {
    case 0:
    default:
    case 1:
    case 2:
    case 3:
    }
    while (true)
    {
      return i;
      i = 90;
      continue;
      i = 180;
      continue;
      i = 270;
    }
  }

  public static Camera.Size getOptimalPreviewSizeForPortrait(Activity paramActivity, List<Camera.Size> paramList, double paramDouble)
  {
    Object localObject;
    if (paramList == null)
      localObject = null;
    while (true)
    {
      return localObject;
      localObject = null;
      double d1 = 1.7976931348623157E+308D;
      Display localDisplay = paramActivity.getWindowManager().getDefaultDisplay();
      int i = Math.min(localDisplay.getHeight(), localDisplay.getWidth());
      if (i <= 0)
        i = localDisplay.getWidth();
      Iterator localIterator1 = paramList.iterator();
      while (localIterator1.hasNext())
      {
        Camera.Size localSize2 = (Camera.Size)localIterator1.next();
        if ((Math.abs(localSize2.height / localSize2.width - paramDouble) > 0.001D) || (Math.abs(localSize2.height - i) >= d1))
          continue;
        localObject = localSize2;
        d1 = Math.abs(localSize2.height - i);
      }
      if (localObject != null)
        continue;
      Log.w("CameraUtil", "No preview size match the aspect ratio");
      double d2 = 1.7976931348623157E+308D;
      Iterator localIterator2 = paramList.iterator();
      while (localIterator2.hasNext())
      {
        Camera.Size localSize1 = (Camera.Size)localIterator2.next();
        if (Math.abs(localSize1.height - i) >= d2)
          continue;
        localObject = localSize1;
        d2 = Math.abs(localSize1.height - i);
      }
    }
  }

  public static void initialize(Context paramContext)
  {
    sImageFileNamer = new ImageFileNamer(paramContext.getString(2131230898));
  }

  public static void initializeScreenBrightness(Window paramWindow, ContentResolver paramContentResolver)
  {
    if (Settings.System.getInt(paramContentResolver, "screen_brightness_mode", 0) == 1)
    {
      WindowManager.LayoutParams localLayoutParams = paramWindow.getAttributes();
      localLayoutParams.screenBrightness = 0.7F;
      paramWindow.setAttributes(localLayoutParams);
    }
  }

  public static Camera openCamera(Activity paramActivity, int paramInt)
    throws CameraHardwareException, CameraDisabledException
  {
    DevicePolicyManager localDevicePolicyManager = (DevicePolicyManager)paramActivity.getSystemService("device_policy");
    if (Build.VERSION.SDK_INT >= 14)
      checkCameraDisabled(localDevicePolicyManager);
    try
    {
      Camera localCamera = CameraHolder.instance().open(paramInt);
      return localCamera;
    }
    catch (CameraHardwareException localCameraHardwareException)
    {
      if ("eng".equals(Build.TYPE))
        throw new RuntimeException("openCamera failed", localCameraHardwareException);
    }
    throw localCameraHardwareException;
  }

  public static void prepareMatrix(Matrix paramMatrix, boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3)
  {
    float f;
    if (paramBoolean)
      f = -1.0F;
    while (true)
    {
      paramMatrix.setScale(f, 1.0F);
      paramMatrix.postRotate(paramInt1);
      paramMatrix.postScale(paramInt2 / 2000.0F, paramInt3 / 2000.0F);
      paramMatrix.postTranslate(paramInt2 / 2.0F, paramInt3 / 2.0F);
      return;
      f = 1.0F;
    }
  }

  public static void rectFToRect(RectF paramRectF, Rect paramRect)
  {
    paramRect.left = Math.round(paramRectF.left);
    paramRect.top = Math.round(paramRectF.top);
    paramRect.right = Math.round(paramRectF.right);
    paramRect.bottom = Math.round(paramRectF.bottom);
  }

  public static int roundOrientation(int paramInt1, int paramInt2)
  {
    if (paramInt2 == -1)
    {
      j = 1;
      if (j != 0)
        paramInt2 = 90 * ((paramInt1 + 45) / 90) % 360;
      return paramInt2;
    }
    int i = Math.abs(paramInt1 - paramInt2);
    if (Math.min(i, 360 - i) >= 50);
    for (int j = 1; ; j = 0)
      break;
  }

  public static void setRotationParameter(Camera.Parameters paramParameters, int paramInt1, int paramInt2)
  {
    int i = 0;
    Camera.CameraInfo localCameraInfo;
    if (paramInt2 != -1)
    {
      localCameraInfo = CameraHolder.instance().getCameraInfo()[paramInt1];
      if (localCameraInfo.facing != 1)
        break label49;
    }
    label49: for (i = (360 + (localCameraInfo.orientation - paramInt2)) % 360; ; i = (paramInt2 + localCameraInfo.orientation) % 360)
    {
      paramParameters.setRotation(i);
      return;
    }
  }

  public static void showErrorAndFinish(Activity paramActivity, int paramInt)
  {
    1 local1 = new DialogInterface.OnClickListener(paramActivity)
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        this.val$activity.finish();
      }
    };
    new IgDialogBuilder(paramActivity).setCancelable(false).setMessage(paramInt).setPositiveButton(2131230899, local1).create().show();
  }

  private static class ImageFileNamer
  {
    private SimpleDateFormat mFormat;
    private long mLastDate;
    private int mSameSecondCount;

    public ImageFileNamer(String paramString)
    {
      this.mFormat = new SimpleDateFormat(paramString);
    }

    public String generateName(long paramLong)
    {
      Date localDate = new Date(paramLong);
      String str = this.mFormat.format(localDate);
      if (paramLong / 1000L == this.mLastDate / 1000L)
      {
        this.mSameSecondCount = (1 + this.mSameSecondCount);
        str = str + "_" + this.mSameSecondCount;
      }
      while (true)
      {
        return str;
        this.mLastDate = paramLong;
        this.mSameSecondCount = 0;
      }
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.camera.CameraUtil
 * JD-Core Version:    0.6.0
 */
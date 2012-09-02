package com.instagram.camera;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import com.instagram.android.Log;
import java.util.Iterator;
import java.util.List;

@TargetApi(9)
public class CameraSettings
{
  public static final int CURRENT_LOCAL_VERSION = 0;
  public static final int CURRENT_VERSION = 0;
  public static final String EXPOSURE_DEFAULT_VALUE = "0";
  public static final String KEY_CAMERA_ID = "pref_camera_id_key";
  public static final String KEY_EXPOSURE = "pref_camera_exposure_key";
  public static final String KEY_FLASH_MODE = "pref_camera_flashmode_key";
  public static final String KEY_FOCUS_MODE = "pref_camera_focusmode_key";
  public static final String KEY_LOCAL_VERSION = "pref_local_version_key";
  public static final String KEY_PICTURE_SIZE = "pref_camera_picturesize_key";
  public static final String KEY_SCENE_MODE = "pref_camera_scenemode_key";
  public static final String KEY_TAP_TO_FOCUS_PROMPT_SHOWN = "pref_tap_to_focus_prompt_shown_key";
  public static final String KEY_VERSION = "pref_version_key";
  public static final String KEY_WHITE_BALANCE = "pref_camera_whitebalance_key";
  private static final int NOT_FOUND = -1;
  private static final String TAG = "CameraSettings";

  @TargetApi(9)
  public static void initialCameraPictureSize(Context paramContext, Camera.Parameters paramParameters)
  {
    List localList = paramParameters.getSupportedPictureSizes();
    if (localList == null);
    while (true)
    {
      return;
      String[] arrayOfString = paramContext.getResources().getStringArray(2131296264);
      int i = arrayOfString.length;
      for (int j = 0; ; j++)
      {
        if (j >= i)
          break label87;
        String str = arrayOfString[j];
        if (!setCameraPictureSize(str, localList, paramParameters))
          continue;
        SharedPreferences.Editor localEditor = ComboPreferences.get(paramContext).edit();
        localEditor.putString("pref_camera_picturesize_key", str);
        localEditor.apply();
        break;
      }
      label87: Log.e("CameraSettings", "No supported picture size found");
    }
  }

  public static int readExposure(ComboPreferences paramComboPreferences)
  {
    String str = paramComboPreferences.getString("pref_camera_exposure_key", "0");
    try
    {
      int j = Integer.parseInt(str);
      i = j;
      return i;
    }
    catch (Exception localException)
    {
      while (true)
      {
        Log.e("CameraSettings", "Invalid exposure: " + str);
        int i = 0;
      }
    }
  }

  public static int readPreferredCameraId(SharedPreferences paramSharedPreferences)
  {
    return Integer.parseInt(paramSharedPreferences.getString("pref_camera_id_key", "0"));
  }

  public static boolean setCameraPictureSize(String paramString, List<Camera.Size> paramList, Camera.Parameters paramParameters)
  {
    int i = 0;
    int j = paramString.indexOf('x');
    if (j == -1)
      break label52;
    while (true)
    {
      return i;
      int k = Integer.parseInt(paramString.substring(0, j));
      int m = Integer.parseInt(paramString.substring(j + 1));
      Iterator localIterator = paramList.iterator();
      label52: if (!localIterator.hasNext())
        continue;
      Camera.Size localSize = (Camera.Size)localIterator.next();
      if ((localSize.width != k) || (localSize.height != m))
        break;
      paramParameters.setPictureSize(k, m);
      i = 1;
    }
  }

  public static void upgradeGlobalPreferences(SharedPreferences paramSharedPreferences)
  {
    try
    {
      int j = paramSharedPreferences.getInt("pref_version_key", 0);
      i = j;
      if (i == 0)
        return;
    }
    catch (Exception localException)
    {
      while (true)
      {
        int i = 0;
        continue;
        SharedPreferences.Editor localEditor = paramSharedPreferences.edit();
        localEditor.putInt("pref_version_key", 0);
        localEditor.apply();
      }
    }
  }

  public static void upgradeLocalPreferences(SharedPreferences paramSharedPreferences)
  {
    try
    {
      int j = paramSharedPreferences.getInt("pref_local_version_key", 0);
      i = j;
      if (i == 0)
        return;
    }
    catch (Exception localException)
    {
      while (true)
      {
        int i = 0;
        continue;
        SharedPreferences.Editor localEditor = paramSharedPreferences.edit();
        if (i == 1);
        localEditor.putInt("pref_local_version_key", 0);
        localEditor.apply();
      }
    }
  }

  public static void writePreferredCameraId(SharedPreferences paramSharedPreferences, int paramInt)
  {
    SharedPreferences.Editor localEditor = paramSharedPreferences.edit();
    localEditor.putString("pref_camera_id_key", Integer.toString(paramInt));
    localEditor.apply();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.camera.CameraSettings
 * JD-Core Version:    0.6.0
 */
package com.instagram.util;

import com.instagram.android.Preferences;
import com.instagram.android.service.AppContext;
import com.instagram.api.request.ApiUrlHelper;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CameraUsageReportingUtil
{
  private static String BUILTIN_CAMERA_OPENED;
  private static String BUILTIN_SOURCE_PICKER_OPENED;
  private static String CROP_SCREEN_OPENED;
  private static String EXTERNAL_SHARE_INTENT_OPENED;
  private static String FILTER_SCREEN_OPENED;
  private static String GALLERY_OPENED_VIA_INSTAGRAM_CAMERA;
  private static String GALLERY_OPENED_VIA_SOURCE_PICKER;
  private static String INSTAGRAM_CAMERA_OPENED = "ic-camera-opened";
  private static String POST_METADATA_SCREEN_OPENED;
  public static final String TAG = "CameraUsageReportingUtil";
  private static Map<String, Integer> sCameraReportingSession;
  private static String sSessionGuid;

  static
  {
    GALLERY_OPENED_VIA_INSTAGRAM_CAMERA = "ic-gallery-opened";
    EXTERNAL_SHARE_INTENT_OPENED = "share-intent";
    BUILTIN_SOURCE_PICKER_OPENED = "sc-picker-opened";
    BUILTIN_CAMERA_OPENED = "sc-camera-opened";
    GALLERY_OPENED_VIA_SOURCE_PICKER = "sc-gallery-opened";
    CROP_SCREEN_OPENED = "crop-opened";
    FILTER_SCREEN_OPENED = "filters-opened";
    POST_METADATA_SCREEN_OPENED = "post-metadata-opened";
  }

  public static void didOpenBuiltinCamera()
  {
    reportUsage(BUILTIN_CAMERA_OPENED);
  }

  public static void didOpenBuiltinGallery(boolean paramBoolean)
  {
    if (paramBoolean);
    for (String str = GALLERY_OPENED_VIA_INSTAGRAM_CAMERA; ; str = GALLERY_OPENED_VIA_SOURCE_PICKER)
    {
      reportUsage(str);
      return;
    }
  }

  public static void didOpenBuiltinSourcePicker()
  {
    resetSession();
    reportUsage(BUILTIN_SOURCE_PICKER_OPENED);
  }

  public static void didOpenCropScreen()
  {
    reportUsage(CROP_SCREEN_OPENED);
  }

  public static void didOpenExternalShareIntent()
  {
    resetSession();
    reportUsage(EXTERNAL_SHARE_INTENT_OPENED);
  }

  public static void didOpenFilters()
  {
    reportUsage(FILTER_SCREEN_OPENED);
  }

  public static void didOpenInstagramCamera()
  {
    resetSession();
    reportUsage(INSTAGRAM_CAMERA_OPENED);
  }

  public static void didOpenPostMetadata()
  {
    reportUsage(POST_METADATA_SCREEN_OPENED);
  }

  private static int getSessionCounterForAction(String paramString)
  {
    int i = 0;
    if (sCameraReportingSession != null)
    {
      Integer localInteger = (Integer)sCameraReportingSession.get(paramString);
      if (localInteger != null)
        i = localInteger.intValue();
      sCameraReportingSession.put(paramString, Integer.valueOf(i + 1));
    }
    return i;
  }

  private static String getSessionGuid()
  {
    if (sSessionGuid == null)
      sSessionGuid = UUID.randomUUID().toString();
    return sSessionGuid;
  }

  private static void reportUsage(String paramString)
  {
    int i = getSessionCounterForAction(paramString);
    if (Preferences.getInstance(AppContext.getContext()).getHasAdvancedCameraEnabled());
    for (String str = "1"; ; str = "0")
    {
      Object[] arrayOfObject = new Object[4];
      arrayOfObject[0] = Integer.valueOf(i);
      arrayOfObject[1] = paramString;
      arrayOfObject[2] = getSessionGuid();
      arrayOfObject[3] = str;
      NoopUtil.sendNoopRequest(ApiUrlHelper.expandPath(String.format("usage/?n=%s&action=%s&sid=%s&ic=%s", arrayOfObject)));
      return;
    }
  }

  private static void resetSession()
  {
    sSessionGuid = UUID.randomUUID().toString();
    sCameraReportingSession = Collections.synchronizedMap(new HashMap());
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.util.CameraUsageReportingUtil
 * JD-Core Version:    0.6.0
 */
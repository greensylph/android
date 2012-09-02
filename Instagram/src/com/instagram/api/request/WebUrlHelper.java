package com.instagram.api.request;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Build.VERSION;
import java.net.URLEncoder;

public class WebUrlHelper
{
  public static final String HOST = "instagram.com";

  public static String addAndroidVersionToUrl(String paramString, Context paramContext)
  {
    String str1 = paramContext.getPackageName();
    try
    {
      str1 = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 0).versionName;
      label21: String[] arrayOfString = paramString.split("#", 2);
      String str2 = null;
      StringBuilder localStringBuilder = new StringBuilder(arrayOfString[0]);
      if (arrayOfString.length == 2)
        str2 = arrayOfString[1];
      if (!paramString.contains("?"))
        localStringBuilder.append("?android=1");
      while (true)
      {
        localStringBuilder.append("&instagram_android_version=" + encodeUrlParameterValue(str1));
        localStringBuilder.append("&android_version=" + encodeUrlParameterValue(Build.VERSION.RELEASE));
        localStringBuilder.append("&android_sdk=" + encodeUrlParameterValue(Build.VERSION.SDK));
        localStringBuilder.append("&android_device_model=" + encodeUrlParameterValue(Build.MODEL));
        localStringBuilder.append("&android_device_manuf=" + encodeUrlParameterValue(Build.MANUFACTURER));
        localStringBuilder.append("&android_device_brand=" + encodeUrlParameterValue(Build.BRAND));
        localStringBuilder.append("&android_device_name=" + encodeUrlParameterValue(Build.DEVICE));
        if (str2 != null)
        {
          localStringBuilder.append("#");
          localStringBuilder.append(str2);
        }
        return localStringBuilder.toString();
        localStringBuilder.append("&android=1");
      }
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      break label21;
    }
  }

  public static String encodeUrlParameterValue(String paramString)
  {
    Object localObject = null;
    try
    {
      String str = URLEncoder.encode(paramString, "utf-8");
      localObject = str;
      label11: return localObject;
    }
    catch (Exception localException)
    {
      break label11;
    }
  }

  public static String expandPath(String paramString)
  {
    return expandPath(paramString, false);
  }

  public static String expandPath(String paramString, boolean paramBoolean)
  {
    if (paramBoolean);
    for (String str = "https"; ; str = "http")
    {
      Object[] arrayOfObject = new Object[3];
      arrayOfObject[0] = str;
      arrayOfObject[1] = "instagram.com";
      arrayOfObject[2] = paramString;
      return String.format("%s://%s%s", arrayOfObject);
    }
  }

  public static String expandPathWithAndroidVersion(String paramString, Context paramContext)
  {
    return addAndroidVersionToUrl(expandPath(paramString, false), paramContext);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.request.WebUrlHelper
 * JD-Core Version:    0.6.0
 */
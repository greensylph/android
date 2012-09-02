package com.instagram.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class ViewUtil
{
  public static float dpToPx(Context paramContext, int paramInt)
  {
    DisplayMetrics localDisplayMetrics = paramContext.getResources().getDisplayMetrics();
    return TypedValue.applyDimension(1, paramInt, localDisplayMetrics);
  }

  public static String formatLargeNumber(Integer paramInteger)
  {
    int i;
    String str;
    if (paramInteger != null)
    {
      i = paramInteger.intValue();
      if (i < 100000)
        str = paramInteger.toString();
    }
    while (true)
    {
      return str;
      if (i < 1000000)
      {
        double d2 = Math.round(i / 1000.0D);
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = Double.valueOf(d2);
        str = String.format("%.0fk", arrayOfObject2);
        continue;
      }
      double d1 = Math.round(i / 10000.0D) / 100.0D;
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = Double.valueOf(d1);
      str = String.format("%.2fm", arrayOfObject1);
      continue;
      if (paramInteger == null)
      {
        str = "";
        continue;
      }
      str = paramInteger.toString();
    }
  }

  public static int getScreenDensity(Context paramContext)
  {
    return paramContext.getResources().getDisplayMetrics().densityDpi;
  }

  public static int getScreenHeightPixels(Context paramContext)
  {
    return paramContext.getResources().getDisplayMetrics().heightPixels;
  }

  public static int getScreenWidthPixels(Context paramContext)
  {
    return paramContext.getResources().getDisplayMetrics().widthPixels;
  }

  public static boolean isHdpi(Context paramContext)
  {
    if (getScreenDensity(paramContext) == 240);
    for (int i = 1; ; i = 0)
      return i;
  }

  public static boolean isLdpi(Context paramContext)
  {
    if (getScreenDensity(paramContext) == 120);
    for (int i = 1; ; i = 0)
      return i;
  }

  public static boolean isMdpi(Context paramContext)
  {
    if (getScreenDensity(paramContext) == 160);
    for (int i = 1; ; i = 0)
      return i;
  }

  public static boolean isXhdpi(Context paramContext)
  {
    if (getScreenDensity(paramContext) == 320);
    for (int i = 1; ; i = 0)
      return i;
  }

  public static int makeInvisibleIfBlank(String paramString, int paramInt)
  {
    if (StringUtil.isNullOrEmpty(paramString));
    while (true)
    {
      return paramInt;
      paramInt = 0;
    }
  }

  public static int makeVisibilityGoneIfBlank(String paramString)
  {
    return makeInvisibleIfBlank(paramString, 8);
  }

  public static String objectToStringOrBlank(Object paramObject)
  {
    if (paramObject == null);
    for (String str = ""; ; str = paramObject.toString())
      return str;
  }

  public static String objectToTrimmedStringOrBlank(Object paramObject)
  {
    return objectToStringOrBlank(paramObject).trim();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.util.ViewUtil
 * JD-Core Version:    0.6.0
 */
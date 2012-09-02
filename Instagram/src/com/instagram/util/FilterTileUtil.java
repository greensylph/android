package com.instagram.util;

import android.content.Context;
import android.content.res.Resources;
import com.instagram.android.gl.NativeBridge;
import com.instagram.android.gl.NativeFilter;
import java.util.HashMap;
import java.util.Map;

public class FilterTileUtil
{
  private static final String RES_NAME_FMT = "camera_edit_filter_%s_%s";
  public static final String RES_TYPE_DEFAULT = "default";
  public static final String RES_TYPE_SELECTED = "selected";
  private static final Map<String, Integer> sResIdCache = new HashMap();

  public static NativeFilter[] getFilters()
  {
    return NativeBridge.getFilters();
  }

  public static int getResId(Context paramContext, NativeFilter paramNativeFilter, String paramString)
  {
    String str = paramNativeFilter.getResInfix() + paramString;
    int i;
    if (sResIdCache.containsKey(str))
      i = ((Integer)sResIdCache.get(str)).intValue();
    while (true)
    {
      return i;
      Resources localResources = paramContext.getResources();
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = paramNativeFilter.getResInfix();
      arrayOfObject[1] = paramString;
      i = localResources.getIdentifier(String.format("camera_edit_filter_%s_%s", arrayOfObject), "drawable", paramContext.getPackageName());
      sResIdCache.put(str, Integer.valueOf(i));
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.util.FilterTileUtil
 * JD-Core Version:    0.6.0
 */
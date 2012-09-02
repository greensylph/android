package com.instagram.util;

import android.content.Context;
import android.content.res.Resources;
import com.instagram.android.service.AppContext;
import java.text.DecimalFormat;

public class NumberUtil
{
  public static String formatNumberOfPhotos(int paramInt)
  {
    String str;
    if (paramInt <= 0)
      str = "";
    while (true)
    {
      return str;
      if (paramInt == 1)
      {
        Resources localResources2 = AppContext.getContext().getResources();
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = Integer.valueOf(paramInt);
        str = localResources2.getString(2131231038, arrayOfObject2);
        continue;
      }
      DecimalFormat localDecimalFormat = new DecimalFormat();
      localDecimalFormat.setGroupingUsed(true);
      localDecimalFormat.setMaximumFractionDigits(0);
      Resources localResources1 = AppContext.getContext().getResources();
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = localDecimalFormat.format(paramInt);
      str = localResources1.getString(2131231037, arrayOfObject1);
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.util.NumberUtil
 * JD-Core Version:    0.6.0
 */
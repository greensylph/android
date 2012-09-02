package com.instagram.util;

import android.content.Context;
import android.content.res.Resources;
import java.util.Date;

public class TimespanUtils
{
  public static long getCurrentTimeInSeconds()
  {
    return new Date().getTime() / 1000L;
  }

  public static String getFormattedDateRelativeToNow(Context paramContext, long paramLong)
  {
    return getFormattedDateRelativeToNowShortened(paramContext, paramLong, false);
  }

  private static String getFormattedDateRelativeToNowShortened(Context paramContext, double paramDouble, boolean paramBoolean)
  {
    double d1 = getCurrentTimeInSeconds() - paramDouble;
    String str;
    if (d1 < -60.0D)
      str = "";
    while (true)
    {
      return str;
      if (d1 < 1.0D)
        d1 = 1.0D;
      double d2 = Math.floor(d1);
      if (d2 < 60.0D)
      {
        str = getStringForTimePeriod(paramContext, RelativeTimePeriod.TimePeriodSeconds, (int)Math.round(d2), paramBoolean);
        continue;
      }
      double d3 = d2 / 60.0D;
      if (d3 < 60.0D)
      {
        str = getStringForTimePeriod(paramContext, RelativeTimePeriod.TimePeriodMinutes, (int)Math.round(d3), paramBoolean);
        continue;
      }
      double d4 = d3 / 60.0D;
      if (d4 < 24.0D)
      {
        str = getStringForTimePeriod(paramContext, RelativeTimePeriod.TimePeriodHours, (int)Math.round(d4), paramBoolean);
        continue;
      }
      double d5 = d4 / 24.0D;
      if (d5 < 7.0D)
      {
        str = getStringForTimePeriod(paramContext, RelativeTimePeriod.TimePeriodDays, (int)Math.round(d5), paramBoolean);
        continue;
      }
      double d6 = d5 / 7.0D;
      str = getStringForTimePeriod(paramContext, RelativeTimePeriod.TimePeriodWeeks, (int)Math.round(d6), paramBoolean);
    }
  }

  private static String getFullStringForTimePeriod(Context paramContext, RelativeTimePeriod paramRelativeTimePeriod, int paramInt)
  {
    String str;
    switch (1.$SwitchMap$com$instagram$util$TimespanUtils$RelativeTimePeriod[paramRelativeTimePeriod.ordinal()])
    {
    default:
      Resources localResources5 = paramContext.getResources();
      Object[] arrayOfObject5 = new Object[1];
      arrayOfObject5[0] = Integer.valueOf(paramInt);
      str = localResources5.getQuantityString(2131558404, paramInt, arrayOfObject5);
    case 1:
    case 2:
    case 3:
    case 4:
    }
    while (true)
    {
      return str;
      Resources localResources4 = paramContext.getResources();
      Object[] arrayOfObject4 = new Object[1];
      arrayOfObject4[0] = Integer.valueOf(paramInt);
      str = localResources4.getQuantityString(2131558400, paramInt, arrayOfObject4);
      continue;
      Resources localResources3 = paramContext.getResources();
      Object[] arrayOfObject3 = new Object[1];
      arrayOfObject3[0] = Integer.valueOf(paramInt);
      str = localResources3.getQuantityString(2131558401, paramInt, arrayOfObject3);
      continue;
      Resources localResources2 = paramContext.getResources();
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = Integer.valueOf(paramInt);
      str = localResources2.getQuantityString(2131558402, paramInt, arrayOfObject2);
      continue;
      Resources localResources1 = paramContext.getResources();
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = Integer.valueOf(paramInt);
      str = localResources1.getQuantityString(2131558403, paramInt, arrayOfObject1);
    }
  }

  public static String getShortenedFormattedDateRelativeToNow(Context paramContext, double paramDouble)
  {
    return getFormattedDateRelativeToNowShortened(paramContext, paramDouble, true);
  }

  private static String getShortenedStringForTimePeriod(Context paramContext, RelativeTimePeriod paramRelativeTimePeriod, int paramInt)
  {
    String str;
    switch (1.$SwitchMap$com$instagram$util$TimespanUtils$RelativeTimePeriod[paramRelativeTimePeriod.ordinal()])
    {
    default:
      str = paramContext.getString(2131230915);
    case 1:
    case 2:
    case 3:
    case 4:
    }
    while (true)
    {
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Integer.valueOf(paramInt);
      arrayOfObject[1] = str;
      return String.format("%s%s", arrayOfObject);
      str = paramContext.getString(2131230911);
      continue;
      str = paramContext.getString(2131230912);
      continue;
      str = paramContext.getString(2131230913);
      continue;
      str = paramContext.getString(2131230914);
    }
  }

  private static String getStringForTimePeriod(Context paramContext, RelativeTimePeriod paramRelativeTimePeriod, int paramInt, boolean paramBoolean)
  {
    if (paramBoolean);
    for (String str = getShortenedStringForTimePeriod(paramContext, paramRelativeTimePeriod, paramInt); ; str = getFullStringForTimePeriod(paramContext, paramRelativeTimePeriod, paramInt))
      return str;
  }

  static enum RelativeTimePeriod
  {
    static
    {
      TimePeriodMinutes = new RelativeTimePeriod("TimePeriodMinutes", 1);
      TimePeriodHours = new RelativeTimePeriod("TimePeriodHours", 2);
      TimePeriodDays = new RelativeTimePeriod("TimePeriodDays", 3);
      TimePeriodWeeks = new RelativeTimePeriod("TimePeriodWeeks", 4);
      RelativeTimePeriod[] arrayOfRelativeTimePeriod = new RelativeTimePeriod[5];
      arrayOfRelativeTimePeriod[0] = TimePeriodSeconds;
      arrayOfRelativeTimePeriod[1] = TimePeriodMinutes;
      arrayOfRelativeTimePeriod[2] = TimePeriodHours;
      arrayOfRelativeTimePeriod[3] = TimePeriodDays;
      arrayOfRelativeTimePeriod[4] = TimePeriodWeeks;
      $VALUES = arrayOfRelativeTimePeriod;
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.util.TimespanUtils
 * JD-Core Version:    0.6.0
 */
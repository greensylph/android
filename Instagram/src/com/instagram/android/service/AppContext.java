package com.instagram.android.service;

import android.content.Context;

public class AppContext
{
  private static Context context;

  public static Context getContext()
  {
    return context;
  }

  public static void setContext(Context paramContext)
  {
    context = paramContext;
    paramContext.setTheme(2131623936);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.service.AppContext
 * JD-Core Version:    0.6.0
 */
package com.google.android.c2dm;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.instagram.android.Log;

public class C2DMessaging
{
  public static final String BACKOFF = "backoff";
  private static final long DEFAULT_BACKOFF = 30000L;
  public static final String EXTRA_APPLICATION_PENDING_INTENT = "app";
  public static final String EXTRA_SENDER = "sender";
  public static final String GSF_PACKAGE = "com.google.android.gsf";
  public static final String LAST_REGISTRATION_CHANGE = "last_registration_change";
  static final String PREFERENCE = "com.google.android.c2dm";
  public static final String REQUEST_REGISTRATION_INTENT = "com.google.android.c2dm.intent.REGISTER";
  public static final String REQUEST_UNREGISTRATION_INTENT = "com.google.android.c2dm.intent.UNREGISTER";

  static void clearRegistrationId(Context paramContext)
  {
    SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("com.google.android.c2dm", 0).edit();
    localEditor.putString("dm_registration", "");
    localEditor.putLong("last_registration_change", System.currentTimeMillis());
    localEditor.commit();
  }

  static long getBackoff(Context paramContext)
  {
    return paramContext.getSharedPreferences("com.google.android.c2dm", 0).getLong("backoff", 30000L);
  }

  public static long getLastRegistrationChange(Context paramContext)
  {
    return paramContext.getSharedPreferences("com.google.android.c2dm", 0).getLong("last_registration_change", 0L);
  }

  public static String getRegistrationId(Context paramContext)
  {
    return paramContext.getSharedPreferences("com.google.android.c2dm", 0).getString("dm_registration", "");
  }

  public static void register(Context paramContext, String paramString)
  {
    Intent localIntent = new Intent("com.google.android.c2dm.intent.REGISTER");
    localIntent.setPackage("com.google.android.gsf");
    localIntent.putExtra("app", PendingIntent.getBroadcast(paramContext, 0, new Intent(), 0));
    localIntent.putExtra("sender", paramString);
    Log.d("C2DMessaging", paramContext.startService(localIntent).toString());
  }

  static void setBackoff(Context paramContext, long paramLong)
  {
    SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("com.google.android.c2dm", 0).edit();
    localEditor.putLong("backoff", paramLong);
    localEditor.commit();
  }

  static void setRegistrationId(Context paramContext, String paramString)
  {
    SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("com.google.android.c2dm", 0).edit();
    localEditor.putString("dm_registration", paramString);
    localEditor.commit();
  }

  public static void unregister(Context paramContext)
  {
    Intent localIntent = new Intent("com.google.android.c2dm.intent.UNREGISTER");
    localIntent.setPackage("com.google.android.gsf");
    localIntent.putExtra("app", PendingIntent.getBroadcast(paramContext, 0, new Intent(), 0));
    paramContext.startService(localIntent);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.google.android.c2dm.C2DMessaging
 * JD-Core Version:    0.6.0
 */
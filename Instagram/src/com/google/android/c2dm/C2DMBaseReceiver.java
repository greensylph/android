package com.google.android.c2dm;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import com.instagram.android.Log;
import java.io.IOException;

public abstract class C2DMBaseReceiver extends IntentService
{
  private static final String C2DM_INTENT = "com.google.android.c2dm.intent.RECEIVE";
  private static final String C2DM_RETRY = "com.google.android.c2dm.intent.RETRY";
  public static final String ERR_ACCOUNT_MISSING = "ACCOUNT_MISSING";
  public static final String ERR_AUTHENTICATION_FAILED = "AUTHENTICATION_FAILED";
  public static final String ERR_INVALID_PARAMETERS = "INVALID_PARAMETERS";
  public static final String ERR_INVALID_SENDER = "INVALID_SENDER";
  public static final String ERR_PHONE_REGISTRATION_ERROR = "PHONE_REGISTRATION_ERROR";
  public static final String ERR_SERVICE_NOT_AVAILABLE = "SERVICE_NOT_AVAILABLE";
  public static final String ERR_TOO_MANY_REGISTRATIONS = "TOO_MANY_REGISTRATIONS";
  public static final String EXTRA_ERROR = "error";
  public static final String EXTRA_REGISTRATION_ID = "registration_id";
  public static final String EXTRA_UNREGISTERED = "unregistered";
  public static final String REGISTRATION_CALLBACK_INTENT = "com.google.android.c2dm.intent.REGISTRATION";
  private static final String TAG = "C2DM";
  private static final String WAKELOCK_KEY = "C2DM_LIB";
  private static PowerManager.WakeLock mWakeLock;
  private final String senderId;

  public C2DMBaseReceiver(String paramString)
  {
    super(paramString);
    this.senderId = paramString;
  }

  private void handleRegistration(Context paramContext, Intent paramIntent)
  {
    String str1 = paramIntent.getStringExtra("registration_id");
    String str2 = paramIntent.getStringExtra("error");
    if (paramIntent.getStringExtra("unregistered") != null)
    {
      C2DMessaging.clearRegistrationId(paramContext);
      onUnregistered(paramContext);
    }
    while (true)
    {
      
      if (str2 != null)
      {
        C2DMessaging.clearRegistrationId(paramContext);
        Log.e("C2DM", "Registration error " + str2);
        onError(paramContext, str2);
        if (!"SERVICE_NOT_AVAILABLE".equals(str2))
          continue;
        long l = C2DMessaging.getBackoff(paramContext);
        Log.d("C2DM", "Scheduling registration retry, backoff = " + l);
        PendingIntent localPendingIntent = PendingIntent.getBroadcast(paramContext, 0, new Intent("com.google.android.c2dm.intent.RETRY"), 0);
        ((AlarmManager)paramContext.getSystemService("alarm")).set(3, l, localPendingIntent);
        C2DMessaging.setBackoff(paramContext, l * 2L);
        continue;
      }
      try
      {
        onRegistered(paramContext, str1);
        C2DMessaging.setRegistrationId(paramContext, str1);
      }
      catch (IOException localIOException)
      {
        Log.e("C2DM", "Registration error " + localIOException.getMessage());
      }
      
      return; // Changed by Michael
    }
  }

  static void runIntentInService(Context paramContext, Intent paramIntent)
  {
    if (mWakeLock == null)
      mWakeLock = ((PowerManager)paramContext.getSystemService("power")).newWakeLock(1, "C2DM_LIB");
    mWakeLock.acquire();
    paramIntent.setClassName(paramContext, paramContext.getPackageName() + ".receiver.C2DMReceiver");
    paramContext.startService(paramIntent);
  }

  public abstract void onError(Context paramContext, String paramString);

  public final void onHandleIntent(Intent paramIntent)
  {
    while (true)
    {
      Context localContext;
      try
      {
        localContext = getApplicationContext();
        if (!paramIntent.getAction().equals("com.google.android.c2dm.intent.REGISTRATION"))
          continue;
        handleRegistration(localContext, paramIntent);
        
        if (paramIntent.getAction().equals("com.google.android.c2dm.intent.RECEIVE"))
        {
          onMessage(localContext, paramIntent);
          continue;
        }
      }
      finally
      {
        mWakeLock.release();
      }
      if (!paramIntent.getAction().equals("com.google.android.c2dm.intent.RETRY"))
        continue;
      C2DMessaging.register(localContext, this.senderId);
      return; // Changed by Michael
    }
  }

  protected abstract void onMessage(Context paramContext, Intent paramIntent);

  public void onRegistered(Context paramContext, String paramString)
    throws IOException
  {
  }

  public void onUnregistered(Context paramContext)
  {
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.google.android.c2dm.C2DMBaseReceiver
 * JD-Core Version:    0.6.0
 */
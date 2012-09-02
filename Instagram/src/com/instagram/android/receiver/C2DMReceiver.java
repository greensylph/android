package com.instagram.android.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import com.google.android.c2dm.C2DMBaseReceiver;
import com.google.android.c2dm.C2DMessaging;
import com.instagram.android.Log;
import com.instagram.android.activity.MainTabActivity;
import com.instagram.android.activity.NewsActivityInTab;
import com.instagram.android.service.AppContext;
import com.instagram.android.service.CustomObjectMapper;
import com.instagram.android.service.PushRegistrationService;
import java.io.IOException;
import org.codehaus.jackson.JsonNode;

public class C2DMReceiver extends C2DMBaseReceiver
{
  private static final int ANDROID_NOTIFICATION_ID = 1;
  public static final String C2DM_SENDER = "instagramandroidmarket@gmail.com";
  public static final String LOG_TAG = "InstagramC2DMReceiver";
  public static final String NOTIFICATION_RECEIVED_BROADCAST = "com.instagram.android.receiver.C2DMReceiver.NOTIFICATION_RECEIVED_BROADCAST";
  private static int unreadCount = 0;

  public C2DMReceiver()
  {
    super("instagramandroidmarket@gmail.com");
  }

  public static void clearUnreadCount()
  {
    ((NotificationManager)AppContext.getContext().getSystemService("notification")).cancelAll();
    unreadCount = 0;
  }

  public static void refreshAppC2DMRegistrationState(Context paramContext)
  {
    C2DMessaging.register(paramContext, "instagramandroidmarket@gmail.com");
  }

  public static void sendStatusBarNotification(Context paramContext, String paramString1, String paramString2)
  {
    NotificationManager localNotificationManager = (NotificationManager)paramContext.getSystemService("notification");
    long l = System.currentTimeMillis();
    unreadCount = 1 + unreadCount;
    Notification localNotification = new Notification(2130837726, paramString1, l);
    localNotification.flags = 24;
    int i;
    String str;
    Intent localIntent;
    Bundle localBundle;
    Uri localUri;
    if (unreadCount > 1)
    {
      i = unreadCount;
      localNotification.number = i;
      str = paramContext.getResources().getString(2131230975);
      localIntent = new Intent(paramContext, MainTabActivity.class);
      localIntent.setFlags(67108864);
      localBundle = new Bundle();
      localUri = Uri.parse("ig://" + paramString2);
      if (!localUri.getHost().equals("media"))
        break label208;
      localBundle.putString("screen", localUri.getHost());
      localBundle.putString("id", localUri.getQueryParameter("id"));
    }
    while (true)
    {
      localIntent.putExtra("com.instagram.android.activity.NewsActivityInTab.EXTRA_NEWS_LAUNCH_BUNDLE", localBundle);
      localNotification.setLatestEventInfo(paramContext, str, paramString1, PendingIntent.getActivity(paramContext, 0, localIntent, 268435456));
      localNotificationManager.notify(1, localNotification);
      return;
      i = 0;
      break;
      label208: if (localUri.getHost().equals("user"))
      {
        localBundle.putString("screen", localUri.getHost());
        localBundle.putString("id", localUri.getQueryParameter("username"));
        continue;
      }
      Log.d("InstagramC2DMReceiver", "Unrecognized action type, just opening app");
    }
  }

  public void onError(Context paramContext, String paramString)
  {
    Log.d("C2DMReceiver", "Received error " + paramString);
  }

  protected void onMessage(Context paramContext, Intent paramIntent)
  {
    Log.d("InstagramC2DMReceiver", paramIntent.getExtras().getString("data"));
    NewsActivityInTab.setLoadInboxFlag();
    try
    {
      JsonNode localJsonNode = CustomObjectMapper.getInstance(paramContext).readTree(paramIntent.getExtras().getString("data"));
      String str1 = localJsonNode.get("m").asText();
      String str2 = localJsonNode.get("ig").asText();
      if (!LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent("com.instagram.android.receiver.C2DMReceiver.NOTIFICATION_RECEIVED_BROADCAST")))
      {
        Log.d("InstagramC2DMReceiver", "Status bar notification sent");
        sendStatusBarNotification(paramContext, str1, str2);
      }
      else
      {
        Log.d("InstagramC2DMReceiver", "Localbroadcast sent");
      }
    }
    catch (Exception localException)
    {
      Log.d("InstagramC2DMReceiver", "Exception occurred trying to put up notification", localException);
    }
  }

  public void onRegistered(Context paramContext, String paramString)
    throws IOException
  {
    Log.d("C2DMReceiver", "Registered " + paramString);
    Intent localIntent = new Intent(paramContext, PushRegistrationService.class);
    localIntent.putExtra("com.instagram.android.service.PushRegistrationService.DEVICE_TOKEN", paramString);
    startService(localIntent);
    super.onRegistered(paramContext, paramString);
  }

  public void onUnregistered(Context paramContext)
  {
    Log.d("C2DMReceiver", "Unregistered");
    super.onUnregistered(paramContext);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.receiver.C2DMReceiver
 * JD-Core Version:    0.6.0
 */
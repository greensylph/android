package com.instagram.facebook;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.facebook.android.Facebook;
import com.instagram.android.Preferences;
import com.instagram.android.service.AppContext;

public class FacebookAccount
{
  private static final String PREF_FILE = "facebookPreferences";
  private static final String PREF_KEY_ACCESS_EXPIRES = "access_expires";
  private static final String PREF_KEY_ACCESS_TOKEN = "access_token";
  private static Facebook sFacebook = new Facebook(FacebookConstants.getFacebookAppId());
  private static boolean sIsLoaded;
  private static boolean sStoreTokenRequestQueued;

  public static void deleteCredentials(boolean paramBoolean)
  {
    if (paramBoolean)
      performClearTokenRequest();
    getPrefs().edit().clear().commit();
    sFacebook.setAccessToken(null);
    sFacebook.setAccessExpires(0L);
  }

  public static Facebook getFacebook()
  {
    if (!sIsLoaded)
    {
      loadCredentials();
      sIsLoaded = true;
    }
    return sFacebook;
  }

  private static SharedPreferences getPrefs()
  {
    return AppContext.getContext().getSharedPreferences("facebookPreferences", 0);
  }

  public static boolean hasQueuedStoreTokenRequest()
  {
    return sStoreTokenRequestQueued;
  }

  private static void loadCredentials()
  {
    SharedPreferences localSharedPreferences = AppContext.getContext().getSharedPreferences("facebookPreferences", 0);
    String str = localSharedPreferences.getString("access_token", null);
    long l = localSharedPreferences.getLong("access_expires", 0L);
    if (str != null)
      sFacebook.setAccessToken(str);
    if (l != 0L)
      sFacebook.setAccessExpires(l);
  }

  private static void performClearTokenRequest()
  {
    performTokenRequest("fb/clear_token/", sFacebook.getAccessToken());
  }

  public static void performStoreTokenRequest()
  {
    sStoreTokenRequestQueued = false;
    performTokenRequest("fb/store_token/", sFacebook.getAccessToken());
  }

  private static void performTokenRequest(String paramString1, String paramString2)
  {
    new FacebookAccount.1(paramString1, paramString2).perform();
  }

  private static void queueStoreTokenRequest()
  {
    sStoreTokenRequestQueued = true;
  }

  public static void saveCredentials()
  {
    SharedPreferences.Editor localEditor = getPrefs().edit();
    localEditor.putString("access_token", sFacebook.getAccessToken());
    localEditor.putLong("access_expires", sFacebook.getAccessExpires());
    localEditor.commit();
    if (Preferences.getInstance(AppContext.getContext()).isLoggedIn())
      performStoreTokenRequest();
    while (true)
    {
      return;
      queueStoreTokenRequest();
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.facebook.FacebookAccount
 * JD-Core Version:    0.6.0
 */
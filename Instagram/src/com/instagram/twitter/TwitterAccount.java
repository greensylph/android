package com.instagram.twitter;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.instagram.android.Preferences;
import com.instagram.android.service.AppContext;
import com.instagram.oauth.OAuthAccount;
import java.util.HashMap;

public class TwitterAccount extends OAuthAccount
{
  private static boolean sStoreTokenRequestQueued;
  private final String mUsername;

  private TwitterAccount(String paramString1, String paramString2, String paramString3)
  {
    super(paramString1, paramString2);
    this.mUsername = paramString3;
  }

  public static TwitterAccount get(Context paramContext)
  {
    TwitterAccount localTwitterAccount = null;
    SharedPreferences localSharedPreferences = getPreferences(paramContext);
    String str1 = localSharedPreferences.getString("oauth_token", null);
    String str2 = localSharedPreferences.getString("oauth_secret", null);
    String str3 = localSharedPreferences.getString("username", null);
    if ((str1 == null) || (str2 == null));
    while (true)
    {
      return localTwitterAccount;
      localTwitterAccount = new TwitterAccount(str1, str2, str3);
    }
  }

  private static SharedPreferences getPreferences(Context paramContext)
  {
    return paramContext.getSharedPreferences("twitterPreferences", 0);
  }

  public static boolean hasQueuedStoreTokenRequest()
  {
    return sStoreTokenRequestQueued;
  }

  public static boolean isConfigured(Context paramContext)
  {
    if (get(paramContext) != null);
    for (int i = 1; ; i = 0)
      return i;
  }

  private static void performClearTokenRequest()
  {
    performTokenRequest("twitter/clear_token/", get(AppContext.getContext()));
  }

  public static void performStoreTokenRequest()
  {
    sStoreTokenRequestQueued = false;
    performTokenRequest("twitter/store_token/", get(AppContext.getContext()));
  }

  private static void performTokenRequest(String paramString, TwitterAccount paramTwitterAccount)
  {
    new TwitterAccount.1(paramString, paramTwitterAccount).perform();
  }

  private static void queueStoreTokenRequest()
  {
    sStoreTokenRequestQueued = true;
  }

  public static void remove(Context paramContext, boolean paramBoolean)
  {
    if (paramBoolean)
      performClearTokenRequest();
    SharedPreferences.Editor localEditor = getPreferences(paramContext).edit();
    localEditor.remove("oauth_token");
    localEditor.remove("oauth_secret");
    localEditor.remove("username");
    localEditor.commit();
  }

  public static TwitterAccount store(Context paramContext, String paramString1, String paramString2, String paramString3)
  {
    SharedPreferences.Editor localEditor = getPreferences(paramContext).edit();
    localEditor.putString("oauth_token", paramString1);
    localEditor.putString("oauth_secret", paramString2);
    localEditor.putString("username", paramString3);
    localEditor.commit();
    if (Preferences.getInstance(AppContext.getContext()).isLoggedIn())
      performStoreTokenRequest();
    while (true)
    {
      return get(paramContext);
      queueStoreTokenRequest();
    }
  }

  public HashMap<String, String> getSharingInfo()
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("share_to_twitter", "1");
    localHashMap.put("twitter_access_token_key", getToken());
    localHashMap.put("twitter_access_token_secret", getSecret());
    if (getUsername() != null)
      localHashMap.put("twitter_username", getUsername());
    return localHashMap;
  }

  public String getUsername()
  {
    return this.mUsername;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.twitter.TwitterAccount
 * JD-Core Version:    0.6.0
 */
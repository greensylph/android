package com.instagram.tumblr;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.instagram.oauth.OAuthAccount;

public class TumblrAccount extends OAuthAccount
{
  private TumblrAccount(String paramString1, String paramString2)
  {
    super(paramString1, paramString2);
  }

  public static TumblrAccount get(Context paramContext)
  {
    TumblrAccount localTumblrAccount = null;
    SharedPreferences localSharedPreferences = getPreferences(paramContext);
    String str1 = localSharedPreferences.getString("oauth_token", null);
    String str2 = localSharedPreferences.getString("oauth_secret", null);
    if ((str1 == null) || (str2 == null));
    while (true)
    {
      return localTumblrAccount;
      localTumblrAccount = new TumblrAccount(str1, str2);
    }
  }

  private static SharedPreferences getPreferences(Context paramContext)
  {
    return paramContext.getSharedPreferences("tumblrPreferences", 0);
  }

  public static boolean isConfigured(Context paramContext)
  {
    if (get(paramContext) != null);
    for (int i = 1; ; i = 0)
      return i;
  }

  public static void remove(Context paramContext)
  {
    SharedPreferences.Editor localEditor = getPreferences(paramContext).edit();
    localEditor.remove("oauth_token");
    localEditor.remove("oauth_secret");
    localEditor.commit();
  }

  public static TumblrAccount store(Context paramContext, String paramString1, String paramString2)
  {
    SharedPreferences.Editor localEditor = getPreferences(paramContext).edit();
    localEditor.putString("oauth_token", paramString1);
    localEditor.putString("oauth_secret", paramString2);
    localEditor.commit();
    return get(paramContext);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.tumblr.TumblrAccount
 * JD-Core Version:    0.6.0
 */
package com.instagram.foursquare;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class FoursquareAccount
{
  private final String mAccessToken;

  public FoursquareAccount(String paramString)
  {
    this.mAccessToken = paramString;
  }

  public static void delete(Context paramContext)
  {
    SharedPreferences.Editor localEditor = getPrefs(paramContext).edit();
    localEditor.remove("accessToken");
    localEditor.commit();
  }

  public static FoursquareAccount get(Context paramContext)
  {
    FoursquareAccount localFoursquareAccount = null;
    String str = getPrefs(paramContext).getString("accessToken", null);
    if (str == null);
    while (true)
    {
      return localFoursquareAccount;
      localFoursquareAccount = new FoursquareAccount(str);
    }
  }

  private static SharedPreferences getPrefs(Context paramContext)
  {
    return paramContext.getSharedPreferences("foursquare.prefs", 0);
  }

  public String getAccessToken()
  {
    return this.mAccessToken;
  }

  public void store(Context paramContext)
  {
    SharedPreferences.Editor localEditor = getPrefs(paramContext).edit();
    localEditor.putString("accessToken", this.mAccessToken);
    localEditor.commit();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.foursquare.FoursquareAccount
 * JD-Core Version:    0.6.0
 */
package com.instagram.twitter;

import com.instagram.android.gl.NativeBridge;
import com.instagram.oauth.OAuthConstants;

public class TwitterConstants
  implements OAuthConstants
{
  public static final String INSTAGRAM_USERNAME = "instagram";
  public static final String PREF_FILE = "twitterPreferences";
  public static final String PREF_USERNAME = "username";

  public static String getConsumerKey()
  {
    return NativeBridge.getInstagramString("rettiwt_k");
  }

  public static String getConsumerSecret()
  {
    return NativeBridge.getInstagramString("rettiwt_s");
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.twitter.TwitterConstants
 * JD-Core Version:    0.6.0
 */
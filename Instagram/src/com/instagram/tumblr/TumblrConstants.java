package com.instagram.tumblr;

import com.instagram.android.gl.NativeBridge;
import com.instagram.oauth.OAuthConstants;

public class TumblrConstants
  implements OAuthConstants
{
  public static final String PREF_FILE = "tumblrPreferences";
  public static final String XAUTH_URL = "https://www.tumblr.com/oauth/access_token";

  public static String getConsumerKey()
  {
    return NativeBridge.getInstagramString("rlbmut_k");
  }

  public static String getConsumerSecret()
  {
    return NativeBridge.getInstagramString("rlbmut_s");
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.tumblr.TumblrConstants
 * JD-Core Version:    0.6.0
 */
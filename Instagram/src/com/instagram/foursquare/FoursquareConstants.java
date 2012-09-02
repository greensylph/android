package com.instagram.foursquare;

import com.instagram.android.gl.NativeBridge;
import com.instagram.oauth.OAuthConstants;

public class FoursquareConstants
  implements OAuthConstants
{
  public static final String FOURSQUARE_CALLBACK_URL = "https://instagram.com/foursquare/oauth_callback/";
  public static final String PREF_FILE = "foursquare.prefs";
  public static final String PREF_KEY_ACCESS_TOKEN = "accessToken";

  public static String getConsumerKey()
  {
    return NativeBridge.getInstagramString("erauqsruof_k");
  }

  public static String getConsumerSecret()
  {
    return NativeBridge.getInstagramString("erauqsruof_s");
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.foursquare.FoursquareConstants
 * JD-Core Version:    0.6.0
 */
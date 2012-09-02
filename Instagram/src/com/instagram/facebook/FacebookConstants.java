package com.instagram.facebook;

import com.instagram.android.gl.NativeBridge;

public class FacebookConstants
{
  public static final String[] FACEBOOK_PERMISSIONS;

  static
  {
    String[] arrayOfString = new String[2];
    arrayOfString[0] = "offline_access";
    arrayOfString[1] = "publish_stream";
    FACEBOOK_PERMISSIONS = arrayOfString;
  }

  public static String getFacebookAppId()
  {
    return NativeBridge.getInstagramString("koobecaf_k");
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.facebook.FacebookConstants
 * JD-Core Version:    0.6.0
 */
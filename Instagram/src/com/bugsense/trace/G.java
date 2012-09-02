package com.bugsense.trace;

public class G
{
  public static String ANDROID_VERSION;
  public static String API_KEY;
  public static String APP_PACKAGE;
  public static String APP_VERSION;
  public static String FILES_PATH = "null";
  public static String PHONE_BRAND;
  public static String PHONE_MODEL;
  public static String TAG;
  public static String TraceVersion;
  public static String URL;

  static
  {
    APP_VERSION = "unknown";
    APP_PACKAGE = "unknown";
    URL = "https://bugsense.appspot.com/api/errors";
    TAG = "BugSenseHandler";
    ANDROID_VERSION = null;
    PHONE_MODEL = null;
    PHONE_BRAND = null;
    API_KEY = null;
    TraceVersion = "1.1.1";
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.bugsense.trace.G
 * JD-Core Version:    0.6.0
 */
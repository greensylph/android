package com.instagram.api.request;

public class ApiUrlHelper
{
  public static final String API_HOST = "instagram.com";
  public static final String API_PATH = "/api/v1/";

  public static String expandPath(String paramString)
  {
    return expandPath(paramString, false);
  }

  public static String expandPath(String paramString, boolean paramBoolean)
  {
    if (paramBoolean);
    for (String str = "https"; ; str = "http")
    {
      Object[] arrayOfObject = new Object[4];
      arrayOfObject[0] = str;
      arrayOfObject[1] = "instagram.com";
      arrayOfObject[2] = "/api/v1/";
      arrayOfObject[3] = paramString;
      return String.format("%s://%s%s%s", arrayOfObject);
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.request.ApiUrlHelper
 * JD-Core Version:    0.6.0
 */
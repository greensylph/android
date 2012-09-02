package com.instagram.util;

import com.instagram.api.request.ApiUrlHelper;

public class NoopUtil
{
  public static final String TAG = "NoopUtil";

  public static void report(String paramString)
  {
    sendNoopRequest(ApiUrlHelper.expandPath("noop/?" + paramString));
  }

  public static void sendNoopRequest(String paramString)
  {
    new Thread(new NoopUtil.1(paramString)).start();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.util.NoopUtil
 * JD-Core Version:    0.6.0
 */
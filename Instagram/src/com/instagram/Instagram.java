package com.instagram;

import android.content.Context;
import android.net.Uri;
import android.net.Uri.Builder;
import com.instagram.android.Log;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

public class Instagram
{
  public static final String API_HOST = "instagram.com";
  public static final String API_PATH = "/api/v1/";
  private static final String TAG = "Instagram";
  private final PersistentCookieStore mCookieStore;
  private final AsyncHttpClient mHttpClient = new AsyncHttpClient();

  public Instagram(Context paramContext)
  {
    this.mHttpClient.setUserAgent("Instagram 0.0 (Android)");
    this.mCookieStore = new PersistentCookieStore(paramContext);
    this.mHttpClient.setCookieStore(this.mCookieStore);
  }

  private String getAbsoluteUrl(String paramString)
  {
    return getAbsoluteUrl(paramString, false);
  }

  private String getAbsoluteUrl(String paramString, boolean paramBoolean)
  {
    if (paramBoolean);
    for (String str1 = "https://"; ; str1 = "http://")
    {
      Uri.Builder localBuilder = Uri.parse(str1 + "instagram.com" + "/api/v1/").buildUpon();
      localBuilder.appendEncodedPath(paramString);
      String str2 = localBuilder.build().toString();
      Log.d("Instagram", "Generated URL: " + str2);
      return str2;
    }
  }

  public void get(String paramString, RequestParams paramRequestParams, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    this.mHttpClient.get(getAbsoluteUrl(paramString), paramRequestParams, paramAsyncHttpResponseHandler);
  }

  public void post(String paramString, RequestParams paramRequestParams, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    String str = getAbsoluteUrl(paramString);
    this.mHttpClient.post(str, paramRequestParams, paramAsyncHttpResponseHandler);
  }

  public void postSecure(String paramString, RequestParams paramRequestParams, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    String str = getAbsoluteUrl(paramString, true);
    this.mHttpClient.post(str, paramRequestParams, paramAsyncHttpResponseHandler);
  }

  private static abstract interface IRequestListener
  {
    public abstract void onRequestFinished(String paramString, Throwable paramThrowable);
  }

  public static class RequestListener
    implements Instagram.IRequestListener
  {
    public void onRequestFinished(String paramString, Throwable paramThrowable)
    {
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.Instagram
 * JD-Core Version:    0.6.0
 */
package com.instagram.foursquare;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class FoursquareClient
{
  private static final String BASE_URL = "https://api.foursquare.com/v2/";
  private static final AsyncHttpClient sClient = new AsyncHttpClient();

  public static void get(String paramString, RequestParams paramRequestParams, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    sClient.get(getAbsoluteUrl(paramString), paramRequestParams, paramAsyncHttpResponseHandler);
  }

  private static String getAbsoluteUrl(String paramString)
  {
    return "https://api.foursquare.com/v2/" + paramString;
  }

  public static RequestParams makeParams()
  {
    RequestParams localRequestParams = new RequestParams();
    localRequestParams.put("v", "20110501");
    localRequestParams.put("client_id", FoursquareConstants.getConsumerKey());
    localRequestParams.put("client_secret", FoursquareConstants.getConsumerSecret());
    return localRequestParams;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.foursquare.FoursquareClient
 * JD-Core Version:    0.6.0
 */
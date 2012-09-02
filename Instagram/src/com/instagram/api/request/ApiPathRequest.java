package com.instagram.api.request;

import com.instagram.api.RequestParams;

public class ApiPathRequest
{
  public static final String TAG = "ApiPathRequest";
  private final String mPath;

  public ApiPathRequest(String paramString)
  {
    this.mPath = paramString;
  }

  public void constructParams(RequestParams paramRequestParams)
  {
  }

  public void perform()
  {
    new Thread(new ApiPathRequest.1(this)).start();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.request.ApiPathRequest
 * JD-Core Version:    0.6.0
 */
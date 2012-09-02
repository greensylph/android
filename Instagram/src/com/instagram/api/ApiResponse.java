package com.instagram.api;

import ch.boye.httpclientandroidlib.StatusLine;
import java.util.Collection;
import java.util.HashMap;
import org.codehaus.jackson.JsonNode;

public abstract class ApiResponse<T>
{
  public static String NETWORK_ERROR_MESSAGE = "An unknown network error has occurred.";
  public static String UNKNOWN_ERROR_MESSAGE = "An unknown error has occurred.";
  private boolean mFailedToLoad;
  protected StatusLine mStatusLine;

  public ApiResponseStatus getApiResponseStatus()
  {
    ApiResponseStatus localApiResponseStatus;
    if (isOk())
      localApiResponseStatus = ApiResponseStatus.ApiResponseStatusOk;
    while (true)
    {
      return localApiResponseStatus;
      Integer localInteger = getResponseCode();
      if (localInteger != null)
      {
        if (localInteger.intValue() == 404)
        {
          localApiResponseStatus = ApiResponseStatus.ApiResponseStatusObjectNotFound;
          continue;
        }
        localApiResponseStatus = ApiResponseStatus.ApiResponseStatusError;
        continue;
      }
      if (this.mFailedToLoad)
      {
        localApiResponseStatus = ApiResponseStatus.ApiResponseStatusError;
        continue;
      }
      localApiResponseStatus = ApiResponseStatus.ApiResponseStatusLoading;
    }
  }

  public abstract String getErrorMessage();

  public abstract String getErrorTitle();

  public Integer getResponseCode()
  {
    if (getStatusLine() != null);
    for (Integer localInteger = Integer.valueOf(getStatusLine().getStatusCode()); ; localInteger = null)
      return localInteger;
  }

  public abstract JsonNode getRootNode();

  public abstract String getStatus();

  public StatusLine getStatusLine()
  {
    return this.mStatusLine;
  }

  public abstract String getStatusMessage();

  public abstract T getSuccessObject();

  public abstract Collection<HashMap<String, String>> getSystemMessages();

  public abstract boolean hasRootValue(String paramString);

  public abstract boolean isNetworkRequest();

  public abstract boolean isOk();

  public abstract T readRootValue(String paramString, Class<T> paramClass);

  public abstract void setErrorMessage(String paramString);

  public void setErrorStatusIfFailedToLoad()
  {
    if (getApiResponseStatus() == ApiResponseStatus.ApiResponseStatusLoading);
    for (boolean bool = true; ; bool = false)
    {
      this.mFailedToLoad = bool;
      return;
    }
  }

  public abstract void setIsNetworkResponse(boolean paramBoolean);

  public void setStatusLine(StatusLine paramStatusLine)
  {
    this.mStatusLine = paramStatusLine;
  }

  public abstract void setSuccessObject(T paramT);
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.ApiResponse
 * JD-Core Version:    0.6.0
 */
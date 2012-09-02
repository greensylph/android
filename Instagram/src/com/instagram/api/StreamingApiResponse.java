package com.instagram.api;

import android.content.Context;
import java.util.Collection;
import java.util.HashMap;
import org.codehaus.jackson.JsonNode;

public class StreamingApiResponse<T> extends ApiResponse<T>
{
  protected Context mContext;
  private String mErrorMessage;
  private String mErrorTitle;
  private boolean mNetworkRequest;
  private String mStatus;
  private String mStatusMessage;
  T mSuccessObject;
  private Collection<HashMap<String, String>> mSystemMessages;

  protected StreamingApiResponse(Context paramContext)
  {
    this.mContext = paramContext;
  }

  public StreamingApiResponse(String paramString)
  {
    this.mErrorMessage = paramString;
  }

  public static <T> StreamingApiResponse<T> createWithError(String paramString)
  {
    if (paramString == null)
      paramString = NETWORK_ERROR_MESSAGE;
    return new StreamingApiResponse(paramString);
  }

  public Context getContext()
  {
    return this.mContext;
  }

  public String getErrorMessage()
  {
    return this.mErrorMessage;
  }

  public String getErrorTitle()
  {
    return this.mErrorTitle;
  }

  public JsonNode getRootNode()
  {
    return null;
  }

  public String getStatus()
  {
    return this.mStatus;
  }

  public String getStatusMessage()
  {
    return this.mStatusMessage;
  }

  public T getSuccessObject()
  {
    return this.mSuccessObject;
  }

  public Collection<HashMap<String, String>> getSystemMessages()
  {
    return this.mSystemMessages;
  }

  public boolean hasRootValue(String paramString)
  {
    return false;
  }

  public boolean isNetworkRequest()
  {
    return this.mNetworkRequest;
  }

  public boolean isOk()
  {
    if ((this.mErrorMessage == null) && (getStatus().equalsIgnoreCase("ok")));
    for (int i = 1; ; i = 0)
      return i;
  }

  public T readRootValue(String paramString, Class<T> paramClass)
  {
    return null;
  }

  public void setErrorMessage(String paramString)
  {
    this.mErrorMessage = paramString;
  }

  public void setErrorTitle(String paramString)
  {
    this.mErrorTitle = paramString;
  }

  public void setIsNetworkResponse(boolean paramBoolean)
  {
    this.mNetworkRequest = paramBoolean;
  }

  public void setStatus(String paramString)
  {
    this.mStatus = paramString;
  }

  public void setStatusMessage(String paramString)
  {
    this.mStatusMessage = paramString;
  }

  public void setSuccessObject(T paramT)
  {
    this.mSuccessObject = paramT;
  }

  public void setSystemMessages(Collection<HashMap<String, String>> paramCollection)
  {
    this.mSystemMessages = paramCollection;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.StreamingApiResponse
 * JD-Core Version:    0.6.0
 */
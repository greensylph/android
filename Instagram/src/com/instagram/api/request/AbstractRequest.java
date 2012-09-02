package com.instagram.api.request;

import android.content.Context;
import android.support.v4.app.LoaderManager;
import ch.boye.httpclientandroidlib.client.methods.HttpUriRequest;
import com.instagram.api.AbstractApiCallbacks;
import com.instagram.api.ApiHttpClient;
import com.instagram.api.ApiRequestLoaderCallbacks;
import com.instagram.api.ApiResponse;
import com.instagram.api.BaseApiLoaderCallbacks;
import com.instagram.api.RequestParams;

public abstract class AbstractRequest<T>
{
  protected final AbstractApiCallbacks<T> mApiCallbacks;
  protected Context mContext;
  private final int mLoaderId;
  private final LoaderManager mLoaderManager;
  private final RequestParams mParams = new RequestParams();
  private HttpUriRequest mRequest;

  public AbstractRequest(Context paramContext, LoaderManager paramLoaderManager, int paramInt, AbstractApiCallbacks<T> paramAbstractApiCallbacks)
  {
    this.mContext = paramContext.getApplicationContext();
    this.mLoaderManager = paramLoaderManager;
    this.mLoaderId = paramInt;
    this.mApiCallbacks = paramAbstractApiCallbacks;
    register();
  }

  protected abstract HttpUriRequest buildRequest(ApiHttpClient paramApiHttpClient, String paramString, RequestParams paramRequestParams);

  protected ApiRequestLoaderCallbacks<T> constructLoaderCallbacks()
  {
    return new ApiRequestLoaderCallbacks(this.mContext, this, this.mApiCallbacks);
  }

  public Context getContext()
  {
    return this.mContext;
  }

  public int getLoaderId()
  {
    return this.mLoaderId;
  }

  public LoaderManager getLoaderManager()
  {
    return this.mLoaderManager;
  }

  protected RequestParams getParams()
  {
    return this.mParams;
  }

  protected abstract String getPath();

  public HttpUriRequest getRequest()
  {
    if (this.mRequest == null)
      this.mRequest = buildRequest(ApiHttpClient.getInstance(this.mContext), ApiUrlHelper.expandPath(getPath(), isSecure()), getParams());
    return this.mRequest;
  }

  public void handleErrorInBackground(ApiResponse<T> paramApiResponse)
  {
  }

  protected boolean isSecure()
  {
    return false;
  }

  public void perform()
  {
    this.mRequest = null;
    this.mLoaderManager.restartLoader(this.mLoaderId, null, constructLoaderCallbacks());
  }

  public void preProcessInBackground()
    throws AbstractRequest.PreProcessException
  {
  }

  public abstract T processInBackground(ApiResponse<T> paramApiResponse);

  protected void register()
  {
    this.mLoaderManager.initLoader(this.mLoaderId, null, new BaseApiLoaderCallbacks(this.mApiCallbacks, this.mContext, this));
  }

  public boolean shouldShowAlertForRequest(ApiResponse<T> paramApiResponse)
  {
    return true;
  }

  public static class PreProcessException extends Exception
  {
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.request.AbstractRequest
 * JD-Core Version:    0.6.0
 */
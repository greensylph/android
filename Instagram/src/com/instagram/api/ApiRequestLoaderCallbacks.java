package com.instagram.api;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import com.instagram.api.request.AbstractRequest;

public class ApiRequestLoaderCallbacks<T> extends BaseApiLoaderCallbacks<T>
{
  private final AbstractRequest<T> mApiRequest;

  public ApiRequestLoaderCallbacks(Context paramContext, AbstractRequest<T> paramAbstractRequest, AbstractApiCallbacks<T> paramAbstractApiCallbacks)
  {
    super(paramAbstractApiCallbacks, paramContext, paramAbstractRequest);
    this.mApiRequest = paramAbstractRequest;
  }

  protected void onApiResponseObjectCreated(ApiResponse<T> paramApiResponse)
  {
  }

  public Loader<ApiResponse<T>> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    if (getApiCallbacks() != null)
      getApiCallbacks().onRequestStart();
    return new ApiRequestLoaderCallbacks.1(this, this.mContext);
  }

  public void onLoadFinished(Loader<ApiResponse<T>> paramLoader, ApiResponse<T> paramApiResponse)
  {
    super.onLoadFinished(paramLoader, paramApiResponse);
    this.mApiRequest.getLoaderManager().destroyLoader(this.mApiRequest.getLoaderId());
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.ApiRequestLoaderCallbacks
 * JD-Core Version:    0.6.0
 */
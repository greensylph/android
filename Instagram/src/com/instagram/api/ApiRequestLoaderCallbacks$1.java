package com.instagram.api;

import android.content.Context;
import ch.boye.httpclientandroidlib.HttpResponse;
import com.instagram.android.loader.ImmediateAsyncTaskLoaderAsyncTask;
import com.instagram.api.request.AbstractRequest;
import com.instagram.api.request.AbstractRequest.PreProcessException;

class ApiRequestLoaderCallbacks$1 extends ImmediateAsyncTaskLoaderAsyncTask<ApiResponse<T>>
{
  public void deliverResult(ApiResponse<T> paramApiResponse)
  {
    super.deliverResult(paramApiResponse);
  }

  public ApiResponse<T> loadInBackground()
  {
    try
    {
      ApiRequestLoaderCallbacks.access$000(this.this$0).preProcessInBackground();
      HttpResponse localHttpResponse = ApiHttpClient.getInstance(this.this$0.mContext).sendRequest(ApiRequestLoaderCallbacks.access$000(this.this$0).getRequest());
      localObjectMappedApiResponse = ObjectMappedApiResponse.parseResponse(getContext(), localHttpResponse);
      if ((localObjectMappedApiResponse.isOk()) && (localObjectMappedApiResponse.getErrorMessage() == null))
      {
        localObjectMappedApiResponse.setSuccessObject(ApiRequestLoaderCallbacks.access$000(this.this$0).processInBackground(localObjectMappedApiResponse));
        localObjectMappedApiResponse.setIsNetworkResponse(true);
        this.this$0.onApiResponseObjectCreated(localObjectMappedApiResponse);
        return localObjectMappedApiResponse;
      }
    }
    catch (AbstractRequest.PreProcessException localPreProcessException)
    {
      while (true)
      {
        ObjectMappedApiResponse localObjectMappedApiResponse;
        ObjectMappedApiResponse.createWithError(localPreProcessException.getMessage());
        continue;
        ApiRequestLoaderCallbacks.access$000(this.this$0).handleErrorInBackground(localObjectMappedApiResponse);
      }
    }
  }

  protected void onStartLoading()
  {
    super.onStartLoading();
  }

  protected void onStopLoading()
  {
    super.onStopLoading();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.ApiRequestLoaderCallbacks.1
 * JD-Core Version:    0.6.0
 */
package com.instagram.api.request;

import android.content.Context;
import android.support.v4.app.LoaderManager;
import com.instagram.api.AbstractApiCallbacks;
import com.instagram.api.ApiRequestLoaderCallbacks;
import com.instagram.api.ApiResponse;
import com.instagram.api.StreamingApiRequestLoaderCallbacks;
import com.instagram.api.StreamingApiResponse;
import java.io.File;
import java.io.IOException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public abstract class AbstractStreamingRequest<T> extends AbstractRequest<T>
{
  public AbstractStreamingRequest(Context paramContext, LoaderManager paramLoaderManager, int paramInt, AbstractApiCallbacks<T> paramAbstractApiCallbacks)
  {
    super(paramContext, paramLoaderManager, paramInt, paramAbstractApiCallbacks);
  }

  public File cacheResponseFile()
  {
    return null;
  }

  protected ApiRequestLoaderCallbacks<T> constructLoaderCallbacks()
  {
    return new StreamingApiRequestLoaderCallbacks(this.mContext, this, this.mApiCallbacks);
  }

  public void handleErrorInBackground(StreamingApiResponse<T> paramStreamingApiResponse)
  {
  }

  public void onResponseParsed(StreamingApiResponse<T> paramStreamingApiResponse)
  {
  }

  public T processInBackground(ApiResponse<T> paramApiResponse)
  {
    return null;
  }

  public abstract void processResponseField(String paramString, JsonParser paramJsonParser, StreamingApiResponse<T> paramStreamingApiResponse)
    throws JsonParseException, IOException;
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.request.AbstractStreamingRequest
 * JD-Core Version:    0.6.0
 */
package com.instagram.api.request;

import android.content.Context;
import android.support.v4.app.LoaderManager;
import ch.boye.httpclientandroidlib.client.methods.HttpUriRequest;
import com.instagram.android.model.Media;
import com.instagram.api.AbstractStreamingApiCallbacks;
import com.instagram.api.ApiHttpClient;
import com.instagram.api.RequestParams;
import com.instagram.api.StreamingApiResponse;
import java.io.IOException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public class FetchAllCommentsRequest extends AbstractStreamingRequest<Object>
{
  private Media mMedia;

  public FetchAllCommentsRequest(Context paramContext, LoaderManager paramLoaderManager, int paramInt, AbstractStreamingApiCallbacks paramAbstractStreamingApiCallbacks)
  {
    super(paramContext, paramLoaderManager, paramInt, paramAbstractStreamingApiCallbacks);
  }

  protected HttpUriRequest buildRequest(ApiHttpClient paramApiHttpClient, String paramString, RequestParams paramRequestParams)
  {
    return paramApiHttpClient.getRequest(paramString);
  }

  protected String getPath()
  {
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = this.mMedia.getId();
    return String.format("media/%s/comments/", arrayOfObject);
  }

  public void handleErrorInBackground(StreamingApiResponse<Object> paramStreamingApiResponse)
  {
    this.mMedia.commentsRequestFailed();
  }

  public void onResponseParsed(StreamingApiResponse<Object> paramStreamingApiResponse)
  {
    this.mMedia.commentsRequestFinished();
  }

  public void perform(Media paramMedia)
  {
    this.mMedia = paramMedia;
    this.mMedia.commentsRequestStarted();
    perform();
  }

  public void processResponseField(String paramString, JsonParser paramJsonParser, StreamingApiResponse<Object> paramStreamingApiResponse)
    throws JsonParseException, IOException
  {
    if (this.mMedia.canParseAsCommentField(paramString))
      this.mMedia.parseAsCommentField(paramJsonParser);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.request.FetchAllCommentsRequest
 * JD-Core Version:    0.6.0
 */
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
import org.codehaus.jackson.JsonToken;

public class PermalinkRequest extends AbstractStreamingRequest<String>
{
  private Media mMedia;

  public PermalinkRequest(Media paramMedia, Context paramContext, LoaderManager paramLoaderManager, AbstractStreamingApiCallbacks<String> paramAbstractStreamingApiCallbacks)
  {
    super(paramContext, paramLoaderManager, 0, paramAbstractStreamingApiCallbacks);
    this.mMedia = paramMedia;
  }

  protected HttpUriRequest buildRequest(ApiHttpClient paramApiHttpClient, String paramString, RequestParams paramRequestParams)
  {
    return paramApiHttpClient.getRequest(paramString);
  }

  protected final String getPath()
  {
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = this.mMedia.getId();
    return String.format("media/%s/permalink/", arrayOfObject);
  }

  public void processResponseField(String paramString, JsonParser paramJsonParser, StreamingApiResponse<String> paramStreamingApiResponse)
    throws JsonParseException, IOException
  {
    if ("permalink".equals(paramString))
    {
      paramJsonParser.nextToken();
      if (paramJsonParser.getCurrentToken() != JsonToken.VALUE_NULL)
      {
        String str = paramJsonParser.getText();
        if (str != null)
          paramStreamingApiResponse.setSuccessObject(str);
      }
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.request.PermalinkRequest
 * JD-Core Version:    0.6.0
 */
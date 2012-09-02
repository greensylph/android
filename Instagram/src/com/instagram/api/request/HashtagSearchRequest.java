package com.instagram.api.request;

import android.content.Context;
import android.support.v4.app.LoaderManager;
import ch.boye.httpclientandroidlib.client.methods.HttpUriRequest;
import com.instagram.android.model.Hashtag;
import com.instagram.api.AbstractStreamingApiCallbacks;
import com.instagram.api.ApiHttpClient;
import com.instagram.api.RequestParams;
import com.instagram.api.StreamingApiResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class HashtagSearchRequest extends AbstractStreamingRequest<ArrayList<Hashtag>>
{
  private String mSearchString;

  public HashtagSearchRequest(Context paramContext, LoaderManager paramLoaderManager, int paramInt, AbstractStreamingApiCallbacks<ArrayList<Hashtag>> paramAbstractStreamingApiCallbacks)
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
    arrayOfObject[0] = URLEncoder.encode(this.mSearchString);
    return String.format("tags/search/?q=%s", arrayOfObject);
  }

  public void perform(String paramString)
  {
    this.mSearchString = paramString;
    perform();
  }

  public void processResponseField(String paramString, JsonParser paramJsonParser, StreamingApiResponse<ArrayList<Hashtag>> paramStreamingApiResponse)
    throws JsonParseException, IOException
  {
    if ("results".equals(paramString))
    {
      paramJsonParser.nextToken();
      ArrayList localArrayList = new ArrayList();
      while (paramJsonParser.nextToken() != JsonToken.END_ARRAY)
      {
        Hashtag localHashtag = Hashtag.fromJsonParser(paramJsonParser);
        if (localHashtag == null)
          break;
        localArrayList.add(localHashtag);
      }
      paramStreamingApiResponse.setSuccessObject(localArrayList);
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.request.HashtagSearchRequest
 * JD-Core Version:    0.6.0
 */
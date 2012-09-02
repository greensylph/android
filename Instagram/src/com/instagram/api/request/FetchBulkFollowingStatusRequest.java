package com.instagram.api.request;

import android.content.Context;
import android.support.v4.app.LoaderManager;
import ch.boye.httpclientandroidlib.client.methods.HttpUriRequest;
import com.instagram.android.model.User;
import com.instagram.android.service.UserStore;
import com.instagram.api.AbstractStreamingApiCallbacks;
import com.instagram.api.ApiHttpClient;
import com.instagram.api.RequestParams;
import com.instagram.api.StreamingApiResponse;
import java.io.IOException;
import java.util.Iterator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class FetchBulkFollowingStatusRequest extends AbstractStreamingRequest<Object>
{
  public FetchBulkFollowingStatusRequest(Context paramContext, LoaderManager paramLoaderManager, int paramInt, AbstractStreamingApiCallbacks<Object> paramAbstractStreamingApiCallbacks)
  {
    super(paramContext, paramLoaderManager, paramInt, paramAbstractStreamingApiCallbacks);
  }

  protected HttpUriRequest buildRequest(ApiHttpClient paramApiHttpClient, String paramString, RequestParams paramRequestParams)
  {
    return paramApiHttpClient.postRequest(paramString, paramRequestParams);
  }

  protected String getPath()
  {
    return "friendships/show_many/";
  }

  public void performForUsers(Iterable<User> paramIterable)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    Iterator localIterator = paramIterable.iterator();
    while (localIterator.hasNext())
    {
      localStringBuilder.append(((User)localIterator.next()).getId());
      localStringBuilder.append(",");
    }
    if (localStringBuilder.length() > 0)
      localStringBuilder.deleteCharAt(-1 + localStringBuilder.length());
    getParams().put("user_ids", localStringBuilder.toString());
    perform();
  }

  public void processResponseField(String paramString, JsonParser paramJsonParser, StreamingApiResponse<Object> paramStreamingApiResponse)
    throws JsonParseException, IOException
  {
    if ("friendship_statuses".equals(paramString))
    {
      paramJsonParser.nextToken();
      while (paramJsonParser.nextToken() != JsonToken.END_OBJECT)
      {
        String str = paramJsonParser.getCurrentName();
        if (str == null)
          continue;
        paramJsonParser.nextToken();
        ((User)UserStore.getInstance(getContext()).get(str)).updateFollowStatus(paramJsonParser, getContext());
      }
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.request.FetchBulkFollowingStatusRequest
 * JD-Core Version:    0.6.0
 */
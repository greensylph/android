package com.instagram.api.request;

import android.content.Context;
import android.support.v4.app.LoaderManager;
import ch.boye.httpclientandroidlib.client.methods.HttpUriRequest;
import com.instagram.android.model.User;
import com.instagram.api.AbstractStreamingApiCallbacks;
import com.instagram.api.ApiHttpClient;
import com.instagram.api.RequestParams;
import com.instagram.api.StreamingApiResponse;
import java.io.IOException;
import java.util.ArrayList;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public abstract class UserListRequest extends AbstractStreamingRequest<ArrayList<User>>
{
  public UserListRequest(Context paramContext, LoaderManager paramLoaderManager, int paramInt, AbstractStreamingApiCallbacks<ArrayList<User>> paramAbstractStreamingApiCallbacks)
  {
    super(paramContext, paramLoaderManager, paramInt, paramAbstractStreamingApiCallbacks);
  }

  protected HttpUriRequest buildRequest(ApiHttpClient paramApiHttpClient, String paramString, RequestParams paramRequestParams)
  {
    return paramApiHttpClient.postRequest(paramString, paramRequestParams);
  }

  public void perform(String paramString)
  {
    getParams().put("query", paramString);
    super.perform();
  }

  public void processResponseField(String paramString, JsonParser paramJsonParser, StreamingApiResponse<ArrayList<User>> paramStreamingApiResponse)
    throws JsonParseException, IOException
  {
    if ("users".equals(paramString))
    {
      paramJsonParser.nextToken();
      ArrayList localArrayList = new ArrayList();
      while (paramJsonParser.nextToken() != JsonToken.END_ARRAY)
      {
        User localUser = User.fromJsonParser(paramStreamingApiResponse.getContext(), paramJsonParser);
        if (localUser == null)
          break;
        localArrayList.add(localUser);
      }
      paramStreamingApiResponse.setSuccessObject(localArrayList);
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.request.UserListRequest
 * JD-Core Version:    0.6.0
 */
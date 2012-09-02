package com.instagram.api.request;

import android.content.Context;
import android.support.v4.app.LoaderManager;
import ch.boye.httpclientandroidlib.client.methods.HttpUriRequest;
import com.instagram.android.model.SuggestedUser;
import com.instagram.api.AbstractStreamingApiCallbacks;
import com.instagram.api.ApiHttpClient;
import com.instagram.api.RequestParams;
import com.instagram.api.StreamingApiResponse;
import java.io.IOException;
import java.util.ArrayList;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class SuggestedUserListRequest extends AbstractStreamingRequest<ArrayList<SuggestedUser>>
{
  private boolean mIsDuringSignup;

  public SuggestedUserListRequest(Context paramContext, LoaderManager paramLoaderManager, int paramInt, boolean paramBoolean, AbstractStreamingApiCallbacks<ArrayList<SuggestedUser>> paramAbstractStreamingApiCallbacks)
  {
    super(paramContext, paramLoaderManager, paramInt, paramAbstractStreamingApiCallbacks);
    this.mIsDuringSignup = paramBoolean;
  }

  protected HttpUriRequest buildRequest(ApiHttpClient paramApiHttpClient, String paramString, RequestParams paramRequestParams)
  {
    return paramApiHttpClient.postRequest(paramString, paramRequestParams);
  }

  protected String getPath()
  {
    if (this.mIsDuringSignup);
    for (String str = "friendships/suggested/?in_signup=true"; ; str = "friendships/suggested/")
      return str;
  }

  public void processResponseField(String paramString, JsonParser paramJsonParser, StreamingApiResponse<ArrayList<SuggestedUser>> paramStreamingApiResponse)
    throws JsonParseException, IOException
  {
    if ("groups".equals(paramString))
    {
      paramJsonParser.nextToken();
      ArrayList localArrayList = new ArrayList();
      while (paramJsonParser.nextToken() != JsonToken.END_ARRAY)
      {
        SuggestedUser localSuggestedUser = SuggestedUser.fromJsonParser(this.mContext, paramJsonParser);
        if (localSuggestedUser == null)
          break;
        localArrayList.add(localSuggestedUser);
      }
      paramStreamingApiResponse.setSuccessObject(localArrayList);
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.request.SuggestedUserListRequest
 * JD-Core Version:    0.6.0
 */
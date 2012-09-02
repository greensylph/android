package com.instagram.api.request;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import ch.boye.httpclientandroidlib.client.methods.HttpUriRequest;
import com.instagram.android.model.Media;
import com.instagram.api.AbstractStreamingApiCallbacks;
import com.instagram.api.ApiHttpClient;
import com.instagram.api.RequestParams;
import com.instagram.api.StreamingApiResponse;
import java.io.IOException;
import java.util.ArrayList;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class FetchPopularRequest extends AbstractStreamingRequest<ArrayList<Media>>
{
  public FetchPopularRequest(Fragment paramFragment, int paramInt, AbstractStreamingApiCallbacks<ArrayList<Media>> paramAbstractStreamingApiCallbacks)
  {
    super(paramFragment.getActivity(), paramFragment.getLoaderManager(), paramInt, paramAbstractStreamingApiCallbacks);
  }

  public FetchPopularRequest(FragmentActivity paramFragmentActivity, int paramInt, AbstractStreamingApiCallbacks<ArrayList<Media>> paramAbstractStreamingApiCallbacks)
  {
    super(paramFragmentActivity, paramFragmentActivity.getSupportLoaderManager(), paramInt, paramAbstractStreamingApiCallbacks);
  }

  protected HttpUriRequest buildRequest(ApiHttpClient paramApiHttpClient, String paramString, RequestParams paramRequestParams)
  {
    return paramApiHttpClient.getRequest(paramString);
  }

  protected String getPath()
  {
    return "feed/popular/";
  }

  public void processResponseField(String paramString, JsonParser paramJsonParser, StreamingApiResponse<ArrayList<Media>> paramStreamingApiResponse)
    throws JsonParseException, IOException
  {
    if ("items".equals(paramString))
    {
      paramJsonParser.nextToken();
      ArrayList localArrayList = new ArrayList();
      while (paramJsonParser.nextToken() != JsonToken.END_ARRAY)
      {
        Media localMedia = Media.fromJsonParser(paramJsonParser);
        if (localMedia == null)
          break;
        localArrayList.add(localMedia);
      }
      paramStreamingApiResponse.setSuccessObject(localArrayList);
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.request.FetchPopularRequest
 * JD-Core Version:    0.6.0
 */
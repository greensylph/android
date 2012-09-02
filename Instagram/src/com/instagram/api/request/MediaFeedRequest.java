package com.instagram.api.request;

import ch.boye.httpclientandroidlib.client.methods.HttpUriRequest;
import com.instagram.android.fragment.CommentThreadFragment;
import com.instagram.android.fragment.FeedFragment;
import com.instagram.android.model.Media;
import com.instagram.android.model.MediaFeedResponse;
import com.instagram.api.AbstractStreamingApiCallbacks;
import com.instagram.api.ApiHttpClient;
import com.instagram.api.ApiResponse;
import com.instagram.api.RequestParams;
import com.instagram.api.StreamingApiResponse;
import java.io.IOException;
import java.util.ArrayList;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public abstract class MediaFeedRequest extends AbstractStreamingRequest<MediaFeedResponse>
{
  private FeedFragment mFeedFragment;
  private boolean mLoadMore = false;

  public MediaFeedRequest(CommentThreadFragment paramCommentThreadFragment, int paramInt, AbstractStreamingApiCallbacks<MediaFeedResponse> paramAbstractStreamingApiCallbacks)
  {
    super(paramCommentThreadFragment.getActivity(), paramCommentThreadFragment.getLoaderManager(), paramInt, paramAbstractStreamingApiCallbacks);
  }

  public MediaFeedRequest(FeedFragment paramFeedFragment, int paramInt, AbstractStreamingApiCallbacks<MediaFeedResponse> paramAbstractStreamingApiCallbacks)
  {
    super(paramFeedFragment.getActivity(), paramFeedFragment.getLoaderManager(), paramInt, paramAbstractStreamingApiCallbacks);
    this.mFeedFragment = paramFeedFragment;
  }

  protected HttpUriRequest buildRequest(ApiHttpClient paramApiHttpClient, String paramString, RequestParams paramRequestParams)
  {
    return paramApiHttpClient.getRequest(paramString);
  }

  protected abstract String getBaseFeedPath();

  public String getLocationString()
  {
    return "";
  }

  public String getMaxIdString()
  {
    Object[] arrayOfObject;
    if ((this.mLoadMore) && (this.mFeedFragment != null) && (this.mFeedFragment.getMaxId() != null))
    {
      arrayOfObject = new Object[1];
      arrayOfObject[0] = this.mFeedFragment.getMaxId();
    }
    for (String str = String.format("?max_id=%s", arrayOfObject); ; str = "")
      return str;
  }

  protected final String getPath()
  {
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = getBaseFeedPath();
    arrayOfObject[1] = getMaxIdString();
    arrayOfObject[2] = getLocationString();
    return String.format("%s%s%s", arrayOfObject);
  }

  public void onResponseParsed(StreamingApiResponse<MediaFeedResponse> paramStreamingApiResponse)
  {
    MediaFeedResponse localMediaFeedResponse = (MediaFeedResponse)paramStreamingApiResponse.getSuccessObject();
    if ((localMediaFeedResponse != null) && (localMediaFeedResponse.getNextMaxId() == null))
    {
      ArrayList localArrayList = localMediaFeedResponse.getItems();
      if ((localArrayList != null) && (localArrayList.size() > 0))
        localMediaFeedResponse.setNextMaxId(((Media)localArrayList.get(-1 + localArrayList.size())).getId());
    }
  }

  public void performWithNewPage()
  {
    this.mLoadMore = true;
    perform();
  }

  public void processResponseField(String paramString, JsonParser paramJsonParser, StreamingApiResponse<MediaFeedResponse> paramStreamingApiResponse)
    throws JsonParseException, IOException
  {
    MediaFeedResponse localMediaFeedResponse = (MediaFeedResponse)paramStreamingApiResponse.getSuccessObject();
    if ("items".equals(paramString))
    {
      paramJsonParser.nextToken();
      if (localMediaFeedResponse == null)
      {
        localMediaFeedResponse = new MediaFeedResponse();
        paramStreamingApiResponse.setSuccessObject(localMediaFeedResponse);
      }
      ArrayList localArrayList = new ArrayList();
      while (paramJsonParser.nextToken() != JsonToken.END_ARRAY)
      {
        Media localMedia = Media.fromJsonParser(paramJsonParser);
        if (localMedia == null)
          break;
        localArrayList.add(localMedia);
      }
      localMediaFeedResponse.setItems(localArrayList);
    }
    while (true)
    {
      return;
      if ("more_available".equals(paramString))
      {
        paramJsonParser.nextToken();
        if (localMediaFeedResponse == null)
        {
          localMediaFeedResponse = new MediaFeedResponse();
          paramStreamingApiResponse.setSuccessObject(localMediaFeedResponse);
        }
        localMediaFeedResponse.setIsMoreAvailable(paramJsonParser.getBooleanValue());
        continue;
      }
      if (!"next_max_id".equals(paramString))
        continue;
      paramJsonParser.nextToken();
      if (localMediaFeedResponse == null)
      {
        localMediaFeedResponse = new MediaFeedResponse();
        paramStreamingApiResponse.setSuccessObject(localMediaFeedResponse);
      }
      localMediaFeedResponse.setNextMaxId(paramJsonParser.getText());
    }
  }

  public boolean shouldShowAlertForRequest(ApiResponse<MediaFeedResponse> paramApiResponse)
  {
    return false;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.request.MediaFeedRequest
 * JD-Core Version:    0.6.0
 */
package com.instagram.api.request;

import com.instagram.android.fragment.FeedFragment;
import com.instagram.android.model.MediaFeedResponse;
import com.instagram.android.model.User;
import com.instagram.api.AbstractStreamingApiCallbacks;

public class UserMediaFeedRequest extends MediaFeedRequest
{
  private User mUser;

  public UserMediaFeedRequest(FeedFragment paramFeedFragment, int paramInt, AbstractStreamingApiCallbacks<MediaFeedResponse> paramAbstractStreamingApiCallbacks, User paramUser)
  {
    super(paramFeedFragment, paramInt, paramAbstractStreamingApiCallbacks);
    this.mUser = paramUser;
  }

  protected String getBaseFeedPath()
  {
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = this.mUser.getId();
    return String.format("feed/user/%s/", arrayOfObject);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.request.UserMediaFeedRequest
 * JD-Core Version:    0.6.0
 */
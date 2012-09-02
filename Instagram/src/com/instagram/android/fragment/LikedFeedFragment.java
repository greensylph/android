package com.instagram.android.fragment;

import com.instagram.android.adapter.FeedAdapter.ViewMode;
import com.instagram.android.model.MediaFeedResponse;
import com.instagram.api.AbstractStreamingApiCallbacks;
import com.instagram.api.request.MediaFeedRequest;

public class LikedFeedFragment extends FeedFragment
{
  public ActionBarConfigurer getActionBarConfigurer()
  {
    return new FeedFragment.StandardFeedActionBar(this);
  }

  protected FeedAdapter.ViewMode getDefaultFeedViewMode()
  {
    return FeedAdapter.ViewMode.GRID;
  }

  protected MediaFeedRequest makeRequest(AbstractStreamingApiCallbacks<MediaFeedResponse> paramAbstractStreamingApiCallbacks)
  {
    return new LikedFeedFragment.1(this, this, 2131492868, paramAbstractStreamingApiCallbacks);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.LikedFeedFragment
 * JD-Core Version:    0.6.0
 */
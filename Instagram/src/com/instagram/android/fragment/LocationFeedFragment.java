package com.instagram.android.fragment;

import android.os.Bundle;
import com.instagram.android.adapter.FeedAdapter.ViewMode;
import com.instagram.android.adapter.LocationFeedAdapter;
import com.instagram.android.model.Media;
import com.instagram.android.model.MediaFeedResponse;
import com.instagram.android.service.MediaStore;
import com.instagram.api.AbstractStreamingApiCallbacks;
import com.instagram.api.request.MediaFeedRequest;

public class LocationFeedFragment extends FeedFragment
{
  public static final String ARGUMENT_LOCATION_MEDIA_ID = "com.instagram.android.fragment.LocationFeedFragment.ARGUMENT_LOCATION_MEDIA_ID";
  protected LocationFeedAdapter mAdapter;
  private Media mMedia;

  public ActionBarConfigurer getActionBarConfigurer()
  {
    return new LocationFeedFragment.2(this);
  }

  protected LocationFeedAdapter getAdapter()
  {
    if (this.mAdapter == null)
      this.mAdapter = new LocationFeedAdapter(getActivity(), this, getDefaultFeedViewMode(), getGridViewPadding(), this);
    return this.mAdapter;
  }

  protected FeedAdapter.ViewMode getDefaultFeedViewMode()
  {
    return FeedAdapter.ViewMode.GRID;
  }

  protected MediaFeedRequest makeRequest(AbstractStreamingApiCallbacks<MediaFeedResponse> paramAbstractStreamingApiCallbacks)
  {
    return new LocationFeedFragment.1(this, this, 2131492868, paramAbstractStreamingApiCallbacks);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mMedia = MediaStore.getInstance(getActivity()).get(getArguments().get("com.instagram.android.fragment.LocationFeedFragment.ARGUMENT_LOCATION_MEDIA_ID"));
    getAdapter().setHeaderObject(this.mMedia.getVenue());
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.LocationFeedFragment
 * JD-Core Version:    0.6.0
 */
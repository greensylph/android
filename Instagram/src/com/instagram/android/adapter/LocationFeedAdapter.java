package com.instagram.android.adapter;

import android.content.Context;
import android.view.View;
import com.instagram.android.adapter.row.LocationRowAdapter;
import com.instagram.android.adapter.row.LocationRowAdapter.Holder;
import com.instagram.android.fragment.FeedFragment;
import com.instagram.android.location.Venue;

public class LocationFeedAdapter extends SimpleHeaderFeedAdapter<Venue>
{
  public LocationFeedAdapter(Context paramContext, FeedAdapter.ListenerDelegate paramListenerDelegate, FeedAdapter.ViewMode paramViewMode, FeedAdapter.GridViewPadding paramGridViewPadding, FeedFragment paramFeedFragment)
  {
    super(paramContext, paramListenerDelegate, paramViewMode, paramGridViewPadding, paramFeedFragment);
  }

  protected void bindHeaderView(Context paramContext, View paramView, int paramInt)
  {
    LocationRowAdapter.bindView((LocationRowAdapter.Holder)paramView.getTag(), (Venue)getItem(paramInt), this.mFeedFragment.getActivity(), this);
  }

  protected View createHeaderView(Context paramContext)
  {
    return LocationRowAdapter.newView(paramContext);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.adapter.LocationFeedAdapter
 * JD-Core Version:    0.6.0
 */
package com.instagram.android.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.AbsListView.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ListView;
import com.instagram.android.adapter.FeedAdapter;
import com.instagram.android.adapter.FeedAdapter.GridViewPadding;
import com.instagram.android.adapter.FeedAdapter.ViewMode;
import com.instagram.android.model.MediaFeedResponse;
import com.instagram.api.AbstractStreamingApiCallbacks;
import com.instagram.api.request.MediaFeedRequest;

public class PopularAltFragment extends FeedFragment
{
  private int mOldOrientation;

  protected void addFooterViews(ListView paramListView)
  {
    FrameLayout localFrameLayout = new FrameLayout(getActivity());
    localFrameLayout.setLayoutParams(new AbsListView.LayoutParams(-1, getResources().getDimensionPixelSize(2131427329)));
    paramListView.addFooterView(localFrameLayout);
  }

  public ActionBarConfigurer getActionBarConfigurer()
  {
    return new PopularAltFragment.2(this);
  }

  protected FeedAdapter.ViewMode getDefaultFeedViewMode()
  {
    return FeedAdapter.ViewMode.GRID;
  }

  protected FeedAdapter.GridViewPadding getGridViewPadding()
  {
    return FeedAdapter.GridViewPadding.LOOSE;
  }

  protected MediaFeedRequest makeRequest(AbstractStreamingApiCallbacks<MediaFeedResponse> paramAbstractStreamingApiCallbacks)
  {
    return new PopularAltFragment.1(this, this, 0, paramAbstractStreamingApiCallbacks);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    getAdapter().setShouldPaginate(false);
  }

  public void onStart()
  {
    this.mOldOrientation = getActivity().getRequestedOrientation();
    getActivity().setRequestedOrientation(1);
    super.onStart();
  }

  public void onStop()
  {
    getActivity().setRequestedOrientation(this.mOldOrientation);
    super.onStop();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.PopularAltFragment
 * JD-Core Version:    0.6.0
 */
package com.instagram.android.adapter;

import android.content.Context;
import android.view.View;
import com.instagram.android.adapter.row.HashtagFeedHeaderRowAdapter;
import com.instagram.android.adapter.row.HashtagFeedHeaderRowAdapter.Holder;
import com.instagram.android.fragment.FeedFragment;
import com.instagram.android.model.Hashtag;

public class HashtagFeedAdapter extends SimpleHeaderFeedAdapter<Hashtag>
{
  public HashtagFeedAdapter(Context paramContext, FeedAdapter.ListenerDelegate paramListenerDelegate, FeedAdapter.ViewMode paramViewMode, FeedAdapter.GridViewPadding paramGridViewPadding, FeedFragment paramFeedFragment)
  {
    super(paramContext, paramListenerDelegate, paramViewMode, paramGridViewPadding, paramFeedFragment);
  }

  protected void bindHeaderView(Context paramContext, View paramView, int paramInt)
  {
    HashtagFeedHeaderRowAdapter.bindView((HashtagFeedHeaderRowAdapter.Holder)paramView.getTag(), (Hashtag)getItem(paramInt), paramContext, this);
  }

  protected View createHeaderView(Context paramContext)
  {
    return HashtagFeedHeaderRowAdapter.newView(paramContext);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.adapter.HashtagFeedAdapter
 * JD-Core Version:    0.6.0
 */
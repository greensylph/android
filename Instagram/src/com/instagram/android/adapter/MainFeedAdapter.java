package com.instagram.android.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import com.instagram.android.adapter.row.PendingMediaRowAdapter;
import com.instagram.android.adapter.row.PendingMediaRowAdapter.Holder;
import com.instagram.android.fragment.FeedFragment;
import com.instagram.android.media.PendingMedia;
import java.util.ArrayList;
import java.util.List;

public class MainFeedAdapter extends FeedAdapter
{
  private static final int NUM_VIEW_TYPES = 1;
  private static final String TAG = "MainFeedAdapter";
  private static final int VIEW_TYPE_PENDING_MEDIA;
  protected List<PendingMedia> mPendingMedia = null;

  public MainFeedAdapter(Context paramContext, FeedAdapter.ListenerDelegate paramListenerDelegate, FeedAdapter.ViewMode paramViewMode, FeedAdapter.GridViewPadding paramGridViewPadding, FeedFragment paramFeedFragment)
  {
    super(paramContext, paramListenerDelegate, paramViewMode, paramGridViewPadding, paramFeedFragment);
  }

  protected void bindView(Context paramContext, View paramView, int paramInt)
  {
    if (paramInt >= getChildCount())
      super.bindView(paramContext, paramView, paramInt);
    while (true)
    {
      return;
      int i = getItemViewType(paramInt);
      if (adjustChildViewType(0) != i)
        break;
      PendingMediaRowAdapter.bindView(paramContext, this, (PendingMediaRowAdapter.Holder)paramView.getTag(), (PendingMedia)getItem(paramInt));
    }
    throw new UnsupportedOperationException("View type not handled");
  }

  protected View createView(Context paramContext, int paramInt, ViewGroup paramViewGroup)
  {
    if (paramInt >= getChildCount());
    for (View localView = super.createView(paramContext, paramInt, paramViewGroup); ; localView = PendingMediaRowAdapter.newView(paramContext))
    {
      return localView;
      int i = getItemViewType(paramInt);
      if (adjustChildViewType(0) != i)
        break;
    }
    throw new UnsupportedOperationException("View type not handled");
  }

  public int getChildCount()
  {
    if (this.mPendingMedia == null);
    for (int i = 0; ; i = this.mPendingMedia.size())
      return i;
  }

  public Object getItem(int paramInt)
  {
    if (getChildCount() > paramInt);
    for (Object localObject = this.mPendingMedia.get(paramInt); ; localObject = super.getItem(paramInt))
      return localObject;
  }

  public int getItemViewType(int paramInt)
  {
    if (paramInt >= getChildCount());
    for (int i = super.getItemViewType(paramInt); ; i = adjustChildViewType(0))
    {
      return i;
      if (!(getItem(paramInt) instanceof PendingMedia))
        break;
    }
    throw new UnsupportedOperationException("Unhandled instance type");
  }

  public int getViewTypeCount()
  {
    return 1 + super.getViewTypeCount();
  }

  protected boolean isValidItemTypeForStickyHeader(int paramInt)
  {
    int i = 0;
    if (getItemViewType(paramInt + getChildCount()) != adjustChildViewType(0))
      i = 1;
    return i;
  }

  public void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt1 >= getChildCount())
      super.onScroll(paramAbsListView, adjustedItemPosition(paramInt1), paramInt2, paramInt3);
    while (true)
    {
      return;
      onHeaderScroll(paramAbsListView, adjustedItemPosition(paramInt1), paramInt2, paramInt3);
    }
  }

  public void setPendingMedia(ArrayList<PendingMedia> paramArrayList)
  {
    this.mPendingMedia = paramArrayList;
    notifyDataSetChanged();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.adapter.MainFeedAdapter
 * JD-Core Version:    0.6.0
 */
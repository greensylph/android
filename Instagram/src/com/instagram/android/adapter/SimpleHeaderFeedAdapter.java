package com.instagram.android.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import com.instagram.android.fragment.FeedFragment;

public abstract class SimpleHeaderFeedAdapter<T> extends FeedAdapter
{
  private static final int NUM_VIEW_TYPES = 1;
  private static final String TAG = "UserDetailFeedAdapter";
  private static final int VIEW_TYPE_HEADER_OBJECT;
  protected T headerObject = null;

  public SimpleHeaderFeedAdapter(Context paramContext, FeedAdapter.ListenerDelegate paramListenerDelegate, FeedAdapter.ViewMode paramViewMode, FeedAdapter.GridViewPadding paramGridViewPadding, FeedFragment paramFeedFragment)
  {
    super(paramContext, paramListenerDelegate, paramViewMode, paramGridViewPadding, paramFeedFragment);
  }

  protected abstract void bindHeaderView(Context paramContext, View paramView, int paramInt);

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
      bindHeaderView(paramContext, paramView, paramInt);
    }
    throw new UnsupportedOperationException("View type not handled");
  }

  protected abstract View createHeaderView(Context paramContext);

  protected View createView(Context paramContext, int paramInt, ViewGroup paramViewGroup)
  {
    if (paramInt >= getChildCount());
    for (View localView = super.createView(paramContext, paramInt, paramViewGroup); ; localView = createHeaderView(paramContext))
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
    if (this.headerObject == null);
    for (int i = 0; ; i = 1)
      return i;
  }

  public Object getItem(int paramInt)
  {
    if (getChildCount() > paramInt);
    for (Object localObject = this.headerObject; ; localObject = super.getItem(paramInt))
      return localObject;
  }

  public int getItemViewType(int paramInt)
  {
    if (paramInt >= getChildCount());
    for (int i = super.getItemViewType(paramInt); ; i = adjustChildViewType(0))
      return i;
  }

  public int getViewTypeCount()
  {
    return 1 + super.getViewTypeCount();
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

  public void onScrollStateChanged(AbsListView paramAbsListView, int paramInt)
  {
  }

  public void setHeaderObject(T paramT)
  {
    this.headerObject = paramT;
    notifyDataSetChanged();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.adapter.SimpleHeaderFeedAdapter
 * JD-Core Version:    0.6.0
 */
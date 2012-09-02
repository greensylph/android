package com.instagram.android.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

public abstract class NoResultsEnhancedAdapter<T> extends EnhancedArrayAdapter<T>
{
  private static final int ITEM_VIEW_TYPE_DEFAULT = 0;
  private static final int ITEM_VIEW_TYPE_NO_RESULTS = 1;
  public static final int TYPE_COUNT = 2;
  private boolean mIsLoaded = false;

  public NoResultsEnhancedAdapter(Context paramContext)
  {
    super(paramContext);
  }

  public int getCount()
  {
    int i;
    if (this.mObjects.size() > 0)
      i = this.mObjects.size();
    while (true)
    {
      return i;
      if (this.mIsLoaded)
      {
        i = 1;
        continue;
      }
      i = 0;
    }
  }

  public T getItem(int paramInt)
  {
    if (paramInt < this.mObjects.size());
    for (Object localObject = this.mObjects.get(paramInt); ; localObject = null)
      return localObject;
  }

  public int getItemViewType(int paramInt)
  {
    if ((this.mIsLoaded) && (this.mObjects.size() == 0));
    for (int i = 1; ; i = 0)
      return i;
  }

  protected abstract View getNoResultsView(View paramView, ViewGroup paramViewGroup);

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    if (getItemViewType(paramInt) == 1);
    for (View localView = getNoResultsView(paramView, paramViewGroup); ; localView = super.getView(paramInt, paramView, paramViewGroup))
      return localView;
  }

  public int getViewTypeCount()
  {
    return 2;
  }

  public boolean isEmpty()
  {
    if (getCount() == 0);
    for (int i = 1; ; i = 0)
      return i;
  }

  public boolean isEnabled(int paramInt)
  {
    if (getItemViewType(paramInt) == 1);
    for (boolean bool = false; ; bool = super.isEnabled(paramInt))
      return bool;
  }

  public void setItems(ArrayList<T> paramArrayList)
  {
    this.mObjects.clear();
    this.mObjects.addAll(paramArrayList);
    this.mIsLoaded = true;
    notifyDataSetChanged();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.adapter.NoResultsEnhancedAdapter
 * JD-Core Version:    0.6.0
 */
package com.instagram.android.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import java.util.ArrayList;
import java.util.List;

public abstract class EnhancedArrayAdapter<T> extends BaseAdapter
  implements ListAdapter
{
  protected Context mContext;
  protected List<T> mObjects = new ArrayList();

  public EnhancedArrayAdapter(Context paramContext)
  {
    this.mContext = paramContext;
  }

  public boolean areAllItemsEnabled()
  {
    return false;
  }

  protected abstract void bindView(View paramView, Context paramContext, int paramInt);

  public Context getContext()
  {
    return this.mContext;
  }

  public int getCount()
  {
    return this.mObjects.size();
  }

  public T getItem(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt < getCount()));
    for (Object localObject = this.mObjects.get(paramInt); ; localObject = null)
      return localObject;
  }

  public long getItemId(int paramInt)
  {
    return 0L;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    if (paramView == null);
    for (View localView = newView(this.mContext, paramInt, paramViewGroup); ; localView = paramView)
    {
      bindView(localView, this.mContext, paramInt);
      return localView;
    }
  }

  public boolean hasStableIds()
  {
    return true;
  }

  public boolean isEmpty()
  {
    return this.mObjects.isEmpty();
  }

  protected abstract View newView(Context paramContext, int paramInt, ViewGroup paramViewGroup);
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.adapter.EnhancedArrayAdapter
 * JD-Core Version:    0.6.0
 */
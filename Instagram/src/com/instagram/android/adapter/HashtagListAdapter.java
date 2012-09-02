package com.instagram.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.instagram.android.adapter.row.HashtagRowAdapter;
import com.instagram.android.adapter.row.HashtagRowAdapter.Holder;
import com.instagram.android.model.Hashtag;

public class HashtagListAdapter extends NoResultsEnhancedAdapter
{
  public HashtagListAdapter(Context paramContext)
  {
    super(paramContext);
  }

  protected void bindView(View paramView, Context paramContext, int paramInt)
  {
    HashtagRowAdapter.bindView((HashtagRowAdapter.Holder)paramView.getTag(), (Hashtag)getItem(paramInt), paramContext);
  }

  protected View getNoResultsView(View paramView, ViewGroup paramViewGroup)
  {
    View localView = LayoutInflater.from(getContext()).inflate(2130903112, null);
    ((TextView)localView.findViewById(2131493056)).setText(2131231060);
    return localView;
  }

  protected View newView(Context paramContext, int paramInt, ViewGroup paramViewGroup)
  {
    return HashtagRowAdapter.newView(paramContext);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.adapter.HashtagListAdapter
 * JD-Core Version:    0.6.0
 */
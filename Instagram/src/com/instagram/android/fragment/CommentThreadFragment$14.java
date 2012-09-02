package com.instagram.android.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

class CommentThreadFragment$14
  implements ActionBarConfigurer
{
  public View customTitleView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup)
  {
    View localView1 = paramLayoutInflater.inflate(2130903042, paramViewGroup, false);
    View localView2 = localView1.findViewById(2131492885);
    localView2.setOnClickListener(new CommentThreadFragment.14.1(this));
    if (CommentThreadFragment.access$900(this.this$0));
    for (int i = 0; ; i = 8)
    {
      localView2.setVisibility(i);
      ((TextView)localView1.findViewById(2131492876)).setText(getTitle());
      localView1.findViewById(2131492876).setVisibility(0);
      return localView1;
    }
  }

  public String getTitle()
  {
    return this.this$0.getString(2131231015);
  }

  public boolean isLoading()
  {
    return false;
  }

  public boolean showBackButton()
  {
    return true;
  }

  public boolean showRefreshButton()
  {
    return false;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.CommentThreadFragment.14
 * JD-Core Version:    0.6.0
 */
package com.instagram.android.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.instagram.android.model.User;
import com.instagram.util.StringUtil;

class SelfFragment$1 extends FeedFragment.StandardFeedActionBar
{
  public View customTitleView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup)
  {
    View localView = paramLayoutInflater.inflate(2130903049, paramViewGroup, false);
    localView.findViewById(2131492884).setVisibility(0);
    localView.findViewById(2131492884).setOnClickListener(new SelfFragment.1.1(this));
    localView.findViewById(2131492885).setOnClickListener(new SelfFragment.1.2(this));
    ((TextView)localView.findViewById(2131492876)).setText(getTitle());
    localView.findViewById(2131492876).setVisibility(0);
    TextView localTextView = (TextView)localView.findViewById(2131492883);
    String str = getSubtitle();
    if (!StringUtil.isNullOrEmpty(str))
    {
      localTextView.setText(str);
      localTextView.setVisibility(0);
    }
    while (true)
    {
      return localView;
      localTextView.setVisibility(8);
    }
  }

  public String getSubtitle()
  {
    if (this.this$0.mUser != null);
    for (String str = this.this$0.mUser.getFullName(); ; str = null)
      return str;
  }

  public String getTitle()
  {
    if (this.this$0.mUser != null);
    for (String str = this.this$0.mUser.getUsername(); ; str = null)
      return str;
  }

  public boolean showRefreshButton()
  {
    return false;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.SelfFragment.1
 * JD-Core Version:    0.6.0
 */
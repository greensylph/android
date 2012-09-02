package com.instagram.android.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;

class PopularAltFragment$2 extends FeedFragment.StandardFeedActionBar
{
  public View customTitleView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup)
  {
    ImageView localImageView;
    if ((this.this$0.getArguments() != null) && (this.this$0.getArguments().getBoolean("branding")))
    {
      localImageView = new ImageView(this.this$0.getActivity());
      localImageView.setImageDrawable(this.this$0.getActivity().getResources().getDrawable(2130837506));
      FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(-2, -2, 19);
      localLayoutParams.setMargins((int)this.this$0.getResources().getDimension(2131427341), 0, 0, 0);
      localImageView.setLayoutParams(localLayoutParams);
    }
    while (true)
    {
      return localImageView;
      localImageView = null;
    }
  }

  public String getTitle()
  {
    return this.this$0.getString(2131230870);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.PopularAltFragment.2
 * JD-Core Version:    0.6.0
 */
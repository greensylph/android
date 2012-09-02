package com.instagram.android.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.instagram.android.service.AppContext;
import com.instagram.android.widget.EnhancedWebView;

class NewsFragment$6
  implements ActionBarConfigurer
{
  public View customTitleView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup)
  {
    return null;
  }

  public String getTitle()
  {
    return AppContext.getContext().getString(2131230875);
  }

  public boolean isLoading()
  {
    return NewsFragment.access$000(this.this$0).isLoading();
  }

  public boolean showBackButton()
  {
    return false;
  }

  public boolean showRefreshButton()
  {
    return true;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.NewsFragment.6
 * JD-Core Version:    0.6.0
 */
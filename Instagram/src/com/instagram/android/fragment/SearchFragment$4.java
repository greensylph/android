package com.instagram.android.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.instagram.android.service.AppContext;

class SearchFragment$4
  implements ActionBarConfigurer
{
  public View customTitleView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup)
  {
    return null;
  }

  public String getTitle()
  {
    return AppContext.getContext().getString(2131230965);
  }

  public boolean isLoading()
  {
    return this.this$0.mIsLoading;
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
 * Qualified Name:     com.instagram.android.fragment.SearchFragment.4
 * JD-Core Version:    0.6.0
 */
package com.instagram.android.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

class SimpleWebViewFragment$2
  implements ActionBarConfigurer
{
  public View customTitleView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup)
  {
    return null;
  }

  public String getTitle()
  {
    return this.this$0.getArguments().getString("com.instagram.android.fragment.SimpleWebViewFragment.ARGUMENT_TITLE");
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
 * Qualified Name:     com.instagram.android.fragment.SimpleWebViewFragment.2
 * JD-Core Version:    0.6.0
 */
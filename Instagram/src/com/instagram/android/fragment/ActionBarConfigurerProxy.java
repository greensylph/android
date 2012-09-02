package com.instagram.android.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ActionBarConfigurerProxy
  implements ActionBarConfigurer
{
  protected ActionBarConfigurer mProxy;

  public ActionBarConfigurerProxy(ActionBarConfigurer paramActionBarConfigurer)
  {
    this.mProxy = paramActionBarConfigurer;
  }

  public View customTitleView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup)
  {
    return this.mProxy.customTitleView(paramLayoutInflater, paramViewGroup);
  }

  public String getTitle()
  {
    return this.mProxy.getTitle();
  }

  public boolean isLoading()
  {
    return this.mProxy.isLoading();
  }

  public boolean showBackButton()
  {
    return this.mProxy.showBackButton();
  }

  public boolean showRefreshButton()
  {
    return this.mProxy.showRefreshButton();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.ActionBarConfigurerProxy
 * JD-Core Version:    0.6.0
 */
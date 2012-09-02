package com.instagram.android.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract interface ActionBarConfigurer
{
  public abstract View customTitleView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup);

  public abstract String getTitle();

  public abstract boolean isLoading();

  public abstract boolean showBackButton();

  public abstract boolean showRefreshButton();

  public static abstract interface ActionBarConfigurerFactory
  {
    public abstract ActionBarConfigurer getActionBarConfigurer();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.ActionBarConfigurer
 * JD-Core Version:    0.6.0
 */
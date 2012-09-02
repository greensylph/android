package com.instagram.android.fragment;

public abstract interface RefreshListener
{
  public static final int REFRESHING = 1;
  public static final int STOPPED;

  public abstract int getRefreshState();

  public abstract void onRefreshStart();

  public abstract void onRefreshStop();
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.RefreshListener
 * JD-Core Version:    0.6.0
 */
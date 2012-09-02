package com.instagram.android.activity;

import android.view.KeyEvent;

public abstract interface ActivityKeyEventDelegate
{
  public abstract boolean onBackPressed();

  public abstract boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent);

  public abstract boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent);
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.activity.ActivityKeyEventDelegate
 * JD-Core Version:    0.6.0
 */
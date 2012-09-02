package com.instagram.android.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

public class RefreshButtonDark extends RefreshButton
{
  public RefreshButtonDark(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  protected Drawable getBackgroundDrawable()
  {
    return getContext().getResources().getDrawable(2130837514);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.widget.RefreshButtonDark
 * JD-Core Version:    0.6.0
 */
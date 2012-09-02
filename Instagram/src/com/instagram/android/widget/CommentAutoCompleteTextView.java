package com.instagram.android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;

public class CommentAutoCompleteTextView extends IgAutoCompleteTextView
{
  private BackButtonListener mBackButtonListener;
  private OnLayoutChangeListener mSimpleChangedLayoutListener;

  public CommentAutoCompleteTextView(Context paramContext)
  {
    super(paramContext);
  }

  public CommentAutoCompleteTextView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public CommentAutoCompleteTextView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  public boolean onKeyPreIme(int paramInt, KeyEvent paramKeyEvent)
  {
    int i = 1;
    if ((paramInt == 4) && (paramKeyEvent.getAction() == i))
      if (this.mBackButtonListener != null)
        this.mBackButtonListener.onBack();
    while (true)
    {
      return i;
      i = 0;
      continue;
      boolean bool = super.dispatchKeyEvent(paramKeyEvent);
    }
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((paramBoolean) && (this.mSimpleChangedLayoutListener != null))
      this.mSimpleChangedLayoutListener.onLayoutChange(this);
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
  }

  public void setBackButtonListener(BackButtonListener paramBackButtonListener)
  {
    this.mBackButtonListener = paramBackButtonListener;
  }

  public void setSimpleChangedLayoutListener(OnLayoutChangeListener paramOnLayoutChangeListener)
  {
    this.mSimpleChangedLayoutListener = paramOnLayoutChangeListener;
  }

  public static abstract interface BackButtonListener
  {
    public abstract void onBack();
  }

  public static abstract interface OnLayoutChangeListener
  {
    public abstract void onLayoutChange(View paramView);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.widget.CommentAutoCompleteTextView
 * JD-Core Version:    0.6.0
 */
package com.instagram.android.widget;

import android.text.TextPaint;
import android.text.style.ClickableSpan;

public abstract class ClickableNameSpan extends ClickableSpan
{
  protected boolean mIsBold;

  public ClickableNameSpan()
  {
    this(true);
  }

  public ClickableNameSpan(boolean paramBoolean)
  {
    this.mIsBold = paramBoolean;
  }

  public void updateDrawState(TextPaint paramTextPaint)
  {
    paramTextPaint.setFakeBoldText(this.mIsBold);
    paramTextPaint.setColor(paramTextPaint.linkColor);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.widget.ClickableNameSpan
 * JD-Core Version:    0.6.0
 */
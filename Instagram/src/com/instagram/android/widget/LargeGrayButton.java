package com.instagram.android.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.widget.Button;

public class LargeGrayButton extends Button
{
  public LargeGrayButton(Context paramContext)
  {
    super(paramContext);
    init();
  }

  public LargeGrayButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init();
  }

  public LargeGrayButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init();
  }

  public void init()
  {
    Resources localResources = getContext().getResources();
    setBackgroundDrawable(localResources.getDrawable(2130837572));
    setShadowLayer(1.0F, 0.0F, 2.0F, localResources.getColor(2131165188));
    setTextColor(localResources.getColor(2131165196));
  }

  public void setEnabled(boolean paramBoolean)
  {
    super.setEnabled(paramBoolean);
    Resources localResources = getContext().getResources();
    if (paramBoolean)
      setTextColor(localResources.getColor(2131165196));
    while (true)
    {
      return;
      setTextColor(localResources.getColor(2131165189));
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.widget.LargeGrayButton
 * JD-Core Version:    0.6.0
 */
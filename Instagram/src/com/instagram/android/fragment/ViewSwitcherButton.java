package com.instagram.android.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.widget.Button;
import com.instagram.android.service.AppContext;

public class ViewSwitcherButton extends Button
{
  public ViewSwitcherButton(Context paramContext)
  {
    super(paramContext);
  }

  public ViewSwitcherButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public ViewSwitcherButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  public void setSelected(boolean paramBoolean)
  {
    super.setSelected(paramBoolean);
    if (isSelected())
      setTextColor(AppContext.getContext().getResources().getColor(2131165196));
    while (true)
    {
      return;
      setTextColor(AppContext.getContext().getResources().getColor(2131165195));
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.ViewSwitcherButton
 * JD-Core Version:    0.6.0
 */
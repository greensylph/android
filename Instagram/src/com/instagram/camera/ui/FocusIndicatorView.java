package com.instagram.camera.ui;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;

public class FocusIndicatorView extends View
  implements FocusIndicator
{
  public FocusIndicatorView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  private void setDrawable(int paramInt)
  {
    setBackgroundDrawable(getResources().getDrawable(paramInt));
  }

  public void clear()
  {
    setBackgroundDrawable(null);
  }

  public void showFail()
  {
    setDrawable(2130837696);
  }

  public void showStart()
  {
    setDrawable(2130837698);
  }

  public void showSuccess()
  {
    setDrawable(2130837697);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.camera.ui.FocusIndicatorView
 * JD-Core Version:    0.6.0
 */
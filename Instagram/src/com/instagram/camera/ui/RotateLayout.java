package com.instagram.camera.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class RotateLayout extends ViewGroup
  implements Rotatable
{
  private static final String TAG = "RotateLayout";
  private View mChild;
  private int mOrientation;

  public RotateLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setBackgroundResource(17170445);
  }

  @TargetApi(11)
  private void setTranslation(int paramInt1, int paramInt2)
  {
    switch (this.mOrientation)
    {
    default:
    case 0:
    case 90:
    case 180:
    case 270:
    }
    while (true)
    {
      this.mChild.setRotation(-this.mOrientation);
      return;
      this.mChild.setTranslationX(0.0F);
      this.mChild.setTranslationY(0.0F);
      continue;
      this.mChild.setTranslationX(0.0F);
      this.mChild.setTranslationY(paramInt2);
      continue;
      this.mChild.setTranslationX(paramInt1);
      this.mChild.setTranslationY(paramInt2);
      continue;
      this.mChild.setTranslationX(paramInt1);
      this.mChild.setTranslationY(0.0F);
    }
  }

  @TargetApi(11)
  protected void onFinishInflate()
  {
    this.mChild = getChildAt(0);
    if (Build.VERSION.SDK_INT >= 11)
    {
      this.mChild.setPivotX(0.0F);
      this.mChild.setPivotY(0.0F);
    }
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = paramInt3 - paramInt1;
    int j = paramInt4 - paramInt2;
    switch (this.mOrientation)
    {
    default:
    case 0:
    case 180:
    case 90:
    case 270:
    }
    while (true)
    {
      return;
      this.mChild.layout(0, 0, i, j);
      continue;
      this.mChild.layout(0, 0, j, i);
    }
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = 0;
    int j = 0;
    switch (this.mOrientation)
    {
    default:
    case 0:
    case 180:
    case 90:
    case 270:
    }
    while (true)
    {
      setMeasuredDimension(i, j);
      if (Build.VERSION.SDK_INT >= 11)
        setTranslation(i, j);
      return;
      measureChild(this.mChild, paramInt1, paramInt2);
      i = this.mChild.getMeasuredWidth();
      j = this.mChild.getMeasuredHeight();
      continue;
      measureChild(this.mChild, paramInt2, paramInt1);
      i = this.mChild.getMeasuredHeight();
      j = this.mChild.getMeasuredWidth();
    }
  }

  public void setOrientation(int paramInt)
  {
    int i = paramInt % 360;
    if (this.mOrientation == i);
    while (true)
    {
      return;
      this.mOrientation = i;
      requestLayout();
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.camera.ui.RotateLayout
 * JD-Core Version:    0.6.0
 */
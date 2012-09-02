package com.instagram.camera;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.RelativeLayout;

public class SquarePreviewFrameLayout extends RelativeLayout
{
  private double mAspectRatio;

  public SquarePreviewFrameLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    int k = getPaddingLeft() + getPaddingRight();
    int m = getPaddingTop() + getPaddingBottom();
    int n = i - k;
    int i1 = j - m;
    if (n > i1 * this.mAspectRatio)
      n = (int)(0.5D + i1 * this.mAspectRatio);
    while (true)
    {
      int i2 = n + k;
      int i3 = i1 + m;
      super.onMeasure(View.MeasureSpec.makeMeasureSpec(i2, 1073741824), View.MeasureSpec.makeMeasureSpec(i3, 1073741824));
      setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
      return;
      i1 = (int)(0.5D + n / this.mAspectRatio);
    }
  }

  public void setAspectRatio(double paramDouble)
  {
    if (paramDouble <= 0.0D)
      throw new IllegalArgumentException();
    if (this.mAspectRatio != paramDouble)
    {
      this.mAspectRatio = paramDouble;
      requestLayout();
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.camera.SquarePreviewFrameLayout
 * JD-Core Version:    0.6.0
 */
package com.instagram.android.widget;

import android.content.Context;
import android.util.AttributeSet;
import com.instagram.android.imagecache.IgImageView;

public class ConstrainedImageView extends IgImageView
{
  public ConstrainedImageView(Context paramContext)
  {
    super(paramContext);
  }

  public ConstrainedImageView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.widget.ConstrainedImageView
 * JD-Core Version:    0.6.0
 */
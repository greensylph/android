package com.instagram.android.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.View.MeasureSpec;
import com.instagram.android.imagecache.IgProgressImageView;

public class ConstrainedProgressImageView extends IgProgressImageView
{
  public static final int PROGRESS_BAR_HEIGHT = 10;

  public ConstrainedProgressImageView(Context paramContext)
  {
    super(paramContext);
  }

  public ConstrainedProgressImageView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    int i = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824);
    int j = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824);
    if (getChildCount() > 0)
    {
      getChildAt(0).measure(i, j);
      getChildAt(1).measure(i, (int)TypedValue.applyDimension(1, 10.0F, getResources().getDisplayMetrics()));
      getChildAt(2).measure(i, j);
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.widget.ConstrainedProgressImageView
 * JD-Core Version:    0.6.0
 */
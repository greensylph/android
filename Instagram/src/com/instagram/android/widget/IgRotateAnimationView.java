package com.instagram.android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class IgRotateAnimationView extends ImageView
{
  public IgRotateAnimationView(Context paramContext)
  {
    super(paramContext);
  }

  public IgRotateAnimationView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public IgRotateAnimationView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    Animation localAnimation = AnimationUtils.loadAnimation(getContext(), 2130968585);
    localAnimation.reset();
    startAnimation(localAnimation);
  }

  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    clearAnimation();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.widget.IgRotateAnimationView
 * JD-Core Version:    0.6.0
 */
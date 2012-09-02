package com.instagram.android.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.CompoundButton;
import com.instagram.android.R.styleable;

public class ActionBarHighlightButton extends CompoundButton
{
  private static final int FADE_DURATION = 666;
  private static final String TAG = "ActionBarHighlightButton";
  private Drawable mBackgroundDrawable;
  private Drawable mButtonDrawable;
  private AlphaDrawableAnimation mHighlightAnimation;

  public ActionBarHighlightButton(Context paramContext)
  {
    super(paramContext);
    init();
  }

  public ActionBarHighlightButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mButtonDrawable = getContext().obtainStyledAttributes(paramAttributeSet, R.styleable.ActionBarHighlightButton).getDrawable(0);
    setButtonDrawable(this.mButtonDrawable);
    init();
  }

  private int calculateStartAlpha(float paramFloat)
  {
    return (int)(255.0F * Math.max(Math.min(paramFloat, 1.0F), 0.0F));
  }

  private void init()
  {
    this.mBackgroundDrawable = getResources().getDrawable(2130837539).mutate();
    this.mBackgroundDrawable.setAlpha(0);
    setBackgroundDrawable(this.mBackgroundDrawable);
    setClickable(true);
  }

  private void resetAnimation(float paramFloat)
  {
    int i = calculateStartAlpha(paramFloat);
    int j = (int)(i * 666 / 255.0F);
    this.mBackgroundDrawable.setAlpha(i);
    if (this.mHighlightAnimation != null)
      this.mHighlightAnimation.cancel();
    this.mHighlightAnimation = new AlphaDrawableAnimation(this.mBackgroundDrawable, i, 0);
    this.mHighlightAnimation.setInterpolator(new DecelerateInterpolator());
    this.mHighlightAnimation.setDuration(j);
    this.mHighlightAnimation.setFillAfter(true);
  }

  public Drawable getButtonDrawable()
  {
    return this.mButtonDrawable;
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    int i = 0;
    switch (paramMotionEvent.getAction())
    {
    default:
      if ((!super.onTouchEvent(paramMotionEvent)) && (i == 0))
        break;
    case 0:
    case 1:
    }
    for (int j = 1; ; j = 0)
    {
      return j;
      resetAnimation(paramMotionEvent.getPressure());
      i = 1;
      break;
      if (this.mHighlightAnimation != null)
        startAnimation(this.mHighlightAnimation);
      i = 1;
      break;
    }
  }

  private static class AlphaDrawableAnimation extends Animation
  {
    private int mCurrentAlpha = -1;
    private Drawable mDrawable;
    private int mFromAlpha;
    private int mToAlpha;

    public AlphaDrawableAnimation(Drawable paramDrawable, int paramInt1, int paramInt2)
    {
      this.mDrawable = paramDrawable;
      this.mFromAlpha = paramInt1;
      this.mToAlpha = paramInt2;
    }

    protected void applyTransformation(float paramFloat, Transformation paramTransformation)
    {
      int i = (int)(this.mFromAlpha + paramFloat * (this.mToAlpha - this.mFromAlpha));
      if (i != this.mCurrentAlpha)
      {
        this.mDrawable.setAlpha(i);
        this.mDrawable.invalidateSelf();
        this.mCurrentAlpha = i;
      }
    }

    public boolean willChangeBounds()
    {
      return false;
    }

    public boolean willChangeTransformationMatrix()
    {
      return false;
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.widget.ActionBarHighlightButton
 * JD-Core Version:    0.6.0
 */
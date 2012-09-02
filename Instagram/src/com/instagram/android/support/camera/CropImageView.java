package com.instagram.android.support.camera;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import java.util.ArrayList;
import java.util.Iterator;

class CropImageView extends ImageViewTouchBase
{
  ArrayList<HighlightView> mHighlightViews = new ArrayList();
  float mLastX;
  float mLastY;
  int mMotionEdge;
  HighlightView mMotionHighlightView = null;

  public CropImageView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  private void centerBasedOnHighlightView(HighlightView paramHighlightView)
  {
    Rect localRect = paramHighlightView.mDrawRect;
    float f1 = localRect.width();
    float f2 = localRect.height();
    float f3 = getWidth();
    float f4 = getHeight();
    float f5 = Math.max(1.0F, Math.min(0.6F * (f3 / f1), 0.6F * (f4 / f2)) * getScale());
    if (Math.abs(f5 - getScale()) / f5 > 0.1D)
    {
      float[] arrayOfFloat = new float[2];
      arrayOfFloat[0] = paramHighlightView.mCropRect.centerX();
      arrayOfFloat[1] = paramHighlightView.mCropRect.centerY();
      getImageMatrix().mapPoints(arrayOfFloat);
      zoomTo(f5, arrayOfFloat[0], arrayOfFloat[1], 300.0F);
    }
    ensureVisible(paramHighlightView);
  }

  private void ensureVisible(HighlightView paramHighlightView)
  {
    Rect localRect = paramHighlightView.mDrawRect;
    int i = Math.max(0, getLeft() - localRect.left);
    int j = Math.min(0, getRight() - localRect.right);
    int k = Math.max(0, getTop() - localRect.top);
    int m = Math.min(0, getBottom() - localRect.bottom);
    int n;
    if (i != 0)
    {
      n = i;
      if (k == 0)
        break label108;
    }
    label108: for (int i1 = k; ; i1 = m)
    {
      if ((n != 0) || (i1 != 0))
        panBy(n, i1);
      return;
      n = j;
      break;
    }
  }

  private void recomputeFocus(MotionEvent paramMotionEvent)
  {
    for (int i = 0; i < this.mHighlightViews.size(); i++)
    {
      HighlightView localHighlightView2 = (HighlightView)this.mHighlightViews.get(i);
      localHighlightView2.setFocus(false);
      localHighlightView2.invalidate();
    }
    for (int j = 0; ; j++)
    {
      if (j < this.mHighlightViews.size())
      {
        HighlightView localHighlightView1 = (HighlightView)this.mHighlightViews.get(j);
        if (localHighlightView1.getHit(paramMotionEvent.getX(), paramMotionEvent.getY()) == 1)
          continue;
        if (!localHighlightView1.hasFocus())
        {
          localHighlightView1.setFocus(true);
          localHighlightView1.invalidate();
        }
      }
      invalidate();
      return;
    }
  }

  public void add(HighlightView paramHighlightView)
  {
    this.mHighlightViews.add(paramHighlightView);
    invalidate();
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    for (int i = 0; i < this.mHighlightViews.size(); i++)
      ((HighlightView)this.mHighlightViews.get(i)).draw(paramCanvas);
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    if (this.mBitmapDisplayed.getBitmap() != null)
    {
      Iterator localIterator = this.mHighlightViews.iterator();
      while (localIterator.hasNext())
      {
        HighlightView localHighlightView = (HighlightView)localIterator.next();
        localHighlightView.mMatrix.set(getImageMatrix());
        localHighlightView.invalidate();
        if (!localHighlightView.mIsFocused)
          continue;
        centerBasedOnHighlightView(localHighlightView);
      }
    }
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    CropImage localCropImage = (CropImage)getContext();
    int i;
    if (localCropImage.mSaving)
    {
      i = 0;
      return i;
    }
    switch (paramMotionEvent.getAction())
    {
    default:
      label48: switch (paramMotionEvent.getAction())
      {
      default:
      case 1:
      case 2:
      }
    case 0:
    case 1:
    case 2:
    }
    while (true)
    {
      i = 1;
      break;
      if (localCropImage.mWaitingToPick)
      {
        recomputeFocus(paramMotionEvent);
        break label48;
      }
      for (int m = 0; m < this.mHighlightViews.size(); m++)
      {
        HighlightView localHighlightView2 = (HighlightView)this.mHighlightViews.get(m);
        int n = localHighlightView2.getHit(paramMotionEvent.getX(), paramMotionEvent.getY());
        if (n == 1)
          continue;
        this.mMotionEdge = n;
        this.mMotionHighlightView = localHighlightView2;
        this.mLastX = paramMotionEvent.getX();
        this.mLastY = paramMotionEvent.getY();
        HighlightView localHighlightView3 = this.mMotionHighlightView;
        if (n == 32);
        for (HighlightView.ModifyMode localModifyMode = HighlightView.ModifyMode.Move; ; localModifyMode = HighlightView.ModifyMode.Grow)
        {
          localHighlightView3.setMode(localModifyMode);
          break;
        }
      }
      if (localCropImage.mWaitingToPick)
        for (int j = 0; ; j++)
        {
          if (j >= this.mHighlightViews.size())
            break label366;
          HighlightView localHighlightView1 = (HighlightView)this.mHighlightViews.get(j);
          if (!localHighlightView1.hasFocus())
            continue;
          localCropImage.mCrop = localHighlightView1;
          int k = 0;
          if (k < this.mHighlightViews.size())
          {
            if (k == j);
            while (true)
            {
              k++;
              break;
              ((HighlightView)this.mHighlightViews.get(k)).setHidden(true);
            }
          }
          centerBasedOnHighlightView(localHighlightView1);
          ((CropImage)getContext()).mWaitingToPick = false;
          i = 1;
          break;
        }
      if (this.mMotionHighlightView != null)
      {
        centerBasedOnHighlightView(this.mMotionHighlightView);
        this.mMotionHighlightView.setMode(HighlightView.ModifyMode.None);
      }
      label366: this.mMotionHighlightView = null;
      break label48;
      if (localCropImage.mWaitingToPick)
      {
        recomputeFocus(paramMotionEvent);
        break label48;
      }
      if (this.mMotionHighlightView == null)
        break label48;
      this.mMotionHighlightView.handleMotion(this.mMotionEdge, paramMotionEvent.getX() - this.mLastX, paramMotionEvent.getY() - this.mLastY);
      this.mLastX = paramMotionEvent.getX();
      this.mLastY = paramMotionEvent.getY();
      ensureVisible(this.mMotionHighlightView);
      break label48;
      center(true, true);
      continue;
      if (getScale() != 1.0F)
        continue;
      center(true, true);
    }
  }

  protected void postTranslate(float paramFloat1, float paramFloat2)
  {
    super.postTranslate(paramFloat1, paramFloat2);
    for (int i = 0; i < this.mHighlightViews.size(); i++)
    {
      HighlightView localHighlightView = (HighlightView)this.mHighlightViews.get(i);
      localHighlightView.mMatrix.postTranslate(paramFloat1, paramFloat2);
      localHighlightView.invalidate();
    }
  }

  protected void zoomIn()
  {
    super.zoomIn();
    Iterator localIterator = this.mHighlightViews.iterator();
    while (localIterator.hasNext())
    {
      HighlightView localHighlightView = (HighlightView)localIterator.next();
      localHighlightView.mMatrix.set(getImageMatrix());
      localHighlightView.invalidate();
    }
  }

  protected void zoomOut()
  {
    super.zoomOut();
    Iterator localIterator = this.mHighlightViews.iterator();
    while (localIterator.hasNext())
    {
      HighlightView localHighlightView = (HighlightView)localIterator.next();
      localHighlightView.mMatrix.set(getImageMatrix());
      localHighlightView.invalidate();
    }
  }

  protected void zoomTo(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    super.zoomTo(paramFloat1, paramFloat2, paramFloat3);
    Iterator localIterator = this.mHighlightViews.iterator();
    while (localIterator.hasNext())
    {
      HighlightView localHighlightView = (HighlightView)localIterator.next();
      localHighlightView.mMatrix.set(getImageMatrix());
      localHighlightView.invalidate();
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.support.camera.CropImageView
 * JD-Core Version:    0.6.0
 */
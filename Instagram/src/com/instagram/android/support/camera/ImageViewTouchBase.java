package com.instagram.android.support.camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

abstract class ImageViewTouchBase extends ImageView
{
  static final float SCALE_RATE = 1.25F;
  private static final String TAG = "ImageViewTouchBase";
  protected Matrix mBaseMatrix = new Matrix();
  protected final RotateBitmap mBitmapDisplayed = new RotateBitmap(null);
  private final Matrix mDisplayMatrix = new Matrix();
  protected Handler mHandler = new Handler();
  private final float[] mMatrixValues = new float[9];
  float mMaxZoom;
  private Runnable mOnLayoutRunnable = null;
  private Recycler mRecycler;
  protected Matrix mSuppMatrix = new Matrix();
  int mThisHeight = -1;
  int mThisWidth = -1;

  public ImageViewTouchBase(Context paramContext)
  {
    super(paramContext);
    init();
  }

  public ImageViewTouchBase(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init();
  }

  private void getProperBaseMatrix(RotateBitmap paramRotateBitmap, Matrix paramMatrix)
  {
    float f1 = getWidth();
    float f2 = getHeight();
    float f3 = paramRotateBitmap.getWidth();
    float f4 = paramRotateBitmap.getHeight();
    paramMatrix.reset();
    float f5 = Math.min(Math.min(f1 / f3, 3.0F), Math.min(f2 / f4, 3.0F));
    paramMatrix.postConcat(paramRotateBitmap.getRotateMatrix());
    paramMatrix.postScale(f5, f5);
    paramMatrix.postTranslate((f1 - f3 * f5) / 2.0F, (f2 - f4 * f5) / 2.0F);
  }

  private void init()
  {
    setScaleType(ImageView.ScaleType.MATRIX);
  }

  private void setImageBitmap(Bitmap paramBitmap, int paramInt)
  {
    super.setImageBitmap(paramBitmap);
    Drawable localDrawable = getDrawable();
    if (localDrawable != null)
      localDrawable.setDither(true);
    Bitmap localBitmap = this.mBitmapDisplayed.getBitmap();
    this.mBitmapDisplayed.setBitmap(paramBitmap);
    this.mBitmapDisplayed.setRotation(paramInt);
    if ((localBitmap != null) && (localBitmap != paramBitmap) && (this.mRecycler != null))
      this.mRecycler.recycle(localBitmap);
  }

  protected void center(boolean paramBoolean1, boolean paramBoolean2)
  {
    if (this.mBitmapDisplayed.getBitmap() == null)
      return;
    Matrix localMatrix = getImageViewMatrix();
    RectF localRectF = new RectF(0.0F, 0.0F, this.mBitmapDisplayed.getBitmap().getWidth(), this.mBitmapDisplayed.getBitmap().getHeight());
    localMatrix.mapRect(localRectF);
    float f1 = localRectF.height();
    float f2 = localRectF.width();
    float f3 = 0.0F;
    float f4 = 0.0F;
    int j;
    label111: int i;
    if (paramBoolean2)
    {
      j = getHeight();
      if (f1 < j)
        f4 = (j - f1) / 2.0F - localRectF.top;
    }
    else if (paramBoolean1)
    {
      i = getWidth();
      if (f2 >= i)
        break label214;
      f3 = (i - f2) / 2.0F - localRectF.left;
    }
    while (true)
    {
      postTranslate(f3, f4);
      setImageMatrix(getImageViewMatrix());
      break;
      if (localRectF.top > 0.0F)
      {
        f4 = -localRectF.top;
        break label111;
      }
      if (localRectF.bottom >= j)
        break label111;
      f4 = getHeight() - localRectF.bottom;
      break label111;
      label214: if (localRectF.left > 0.0F)
      {
        f3 = -localRectF.left;
        continue;
      }
      if (localRectF.right >= i)
        continue;
      f3 = i - localRectF.right;
    }
  }

  public void clear()
  {
    setImageBitmapResetBase(null, true);
  }

  protected Matrix getImageViewMatrix()
  {
    this.mDisplayMatrix.set(this.mBaseMatrix);
    this.mDisplayMatrix.postConcat(this.mSuppMatrix);
    return this.mDisplayMatrix;
  }

  protected float getScale()
  {
    return getScale(this.mSuppMatrix);
  }

  protected float getScale(Matrix paramMatrix)
  {
    return getValue(paramMatrix, 0);
  }

  protected float getValue(Matrix paramMatrix, int paramInt)
  {
    paramMatrix.getValues(this.mMatrixValues);
    return this.mMatrixValues[paramInt];
  }

  protected float maxZoom()
  {
    float f;
    if (this.mBitmapDisplayed.getBitmap() == null)
      f = 1.0F;
    while (true)
    {
      return f;
      f = 4.0F * Math.max(this.mBitmapDisplayed.getWidth() / this.mThisWidth, this.mBitmapDisplayed.getHeight() / this.mThisHeight);
    }
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if ((paramInt == 4) && (paramKeyEvent.getRepeatCount() == 0))
      paramKeyEvent.startTracking();
    for (boolean bool = true; ; bool = super.onKeyDown(paramInt, paramKeyEvent))
      return bool;
  }

  public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent)
  {
    if ((paramInt == 4) && (paramKeyEvent.isTracking()) && (!paramKeyEvent.isCanceled()) && (getScale() > 1.0F))
      zoomTo(1.0F);
    for (boolean bool = true; ; bool = super.onKeyUp(paramInt, paramKeyEvent))
      return bool;
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    this.mThisWidth = (paramInt3 - paramInt1);
    this.mThisHeight = (paramInt4 - paramInt2);
    Runnable localRunnable = this.mOnLayoutRunnable;
    if (localRunnable != null)
    {
      this.mOnLayoutRunnable = null;
      localRunnable.run();
    }
    if (this.mBitmapDisplayed.getBitmap() != null)
    {
      getProperBaseMatrix(this.mBitmapDisplayed, this.mBaseMatrix);
      setImageMatrix(getImageViewMatrix());
    }
  }

  protected void panBy(float paramFloat1, float paramFloat2)
  {
    postTranslate(paramFloat1, paramFloat2);
    setImageMatrix(getImageViewMatrix());
  }

  protected void postTranslate(float paramFloat1, float paramFloat2)
  {
    this.mSuppMatrix.postTranslate(paramFloat1, paramFloat2);
  }

  public void setImageBitmap(Bitmap paramBitmap)
  {
    setImageBitmap(paramBitmap, 0);
  }

  public void setImageBitmapResetBase(Bitmap paramBitmap, boolean paramBoolean)
  {
    setImageRotateBitmapResetBase(new RotateBitmap(paramBitmap), paramBoolean);
  }

  public void setImageRotateBitmapResetBase(RotateBitmap paramRotateBitmap, boolean paramBoolean)
  {
    if (getWidth() <= 0)
    {
      this.mOnLayoutRunnable = new Runnable(paramRotateBitmap, paramBoolean)
      {
        public void run()
        {
          ImageViewTouchBase.this.setImageRotateBitmapResetBase(this.val$bitmap, this.val$resetSupp);
        }
      };
      return;
    }
    if (paramRotateBitmap.getBitmap() != null)
    {
      getProperBaseMatrix(paramRotateBitmap, this.mBaseMatrix);
      setImageBitmap(paramRotateBitmap.getBitmap(), paramRotateBitmap.getRotation());
    }
    while (true)
    {
      if (paramBoolean)
        this.mSuppMatrix.reset();
      setImageMatrix(getImageViewMatrix());
      this.mMaxZoom = maxZoom();
      break;
      this.mBaseMatrix.reset();
      setImageBitmap(null);
    }
  }

  public void setRecycler(Recycler paramRecycler)
  {
    this.mRecycler = paramRecycler;
  }

  protected void zoomIn()
  {
    zoomIn(1.25F);
  }

  protected void zoomIn(float paramFloat)
  {
    if (getScale() >= this.mMaxZoom);
    while (true)
    {
      return;
      if (this.mBitmapDisplayed.getBitmap() != null)
      {
        float f1 = getWidth() / 2.0F;
        float f2 = getHeight() / 2.0F;
        this.mSuppMatrix.postScale(paramFloat, paramFloat, f1, f2);
        setImageMatrix(getImageViewMatrix());
        continue;
      }
    }
  }

  protected void zoomOut()
  {
    zoomOut(1.25F);
  }

  protected void zoomOut(float paramFloat)
  {
    if (this.mBitmapDisplayed.getBitmap() == null)
      return;
    float f1 = getWidth() / 2.0F;
    float f2 = getHeight() / 2.0F;
    Matrix localMatrix = new Matrix(this.mSuppMatrix);
    localMatrix.postScale(1.0F / paramFloat, 1.0F / paramFloat, f1, f2);
    if (getScale(localMatrix) < 1.0F)
      this.mSuppMatrix.setScale(1.0F, 1.0F, f1, f2);
    while (true)
    {
      setImageMatrix(getImageViewMatrix());
      center(true, true);
      break;
      this.mSuppMatrix.postScale(1.0F / paramFloat, 1.0F / paramFloat, f1, f2);
    }
  }

  protected void zoomTo(float paramFloat)
  {
    zoomTo(paramFloat, getWidth() / 2.0F, getHeight() / 2.0F);
  }

  protected void zoomTo(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (paramFloat1 > this.mMaxZoom)
      paramFloat1 = this.mMaxZoom;
    float f = paramFloat1 / getScale();
    this.mSuppMatrix.postScale(f, f, paramFloat2, paramFloat3);
    setImageMatrix(getImageViewMatrix());
    center(true, true);
  }

  protected void zoomTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    float f1 = (paramFloat1 - getScale()) / paramFloat4;
    float f2 = getScale();
    long l = System.currentTimeMillis();
    this.mHandler.post(new Runnable(paramFloat4, l, f2, f1, paramFloat2, paramFloat3)
    {
      public void run()
      {
        long l = System.currentTimeMillis();
        float f1 = Math.min(this.val$durationMs, (float)(l - this.val$startTime));
        float f2 = this.val$oldScale + f1 * this.val$incrementPerMs;
        ImageViewTouchBase.this.zoomTo(f2, this.val$centerX, this.val$centerY);
        if (f1 < this.val$durationMs)
          ImageViewTouchBase.this.mHandler.post(this);
      }
    });
  }

  protected void zoomToPoint(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    float f1 = getWidth() / 2.0F;
    float f2 = getHeight() / 2.0F;
    panBy(f1 - paramFloat2, f2 - paramFloat3);
    zoomTo(paramFloat1, f1, f2);
  }

  public static abstract interface Recycler
  {
    public abstract void recycle(Bitmap paramBitmap);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.support.camera.ImageViewTouchBase
 * JD-Core Version:    0.6.0
 */
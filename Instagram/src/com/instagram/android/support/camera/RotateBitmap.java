package com.instagram.android.support.camera;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class RotateBitmap
{
  public static final String TAG = "RotateBitmap";
  private Bitmap mBitmap;
  private int mRotation;

  public RotateBitmap(Bitmap paramBitmap)
  {
    this.mBitmap = paramBitmap;
    this.mRotation = 0;
  }

  public RotateBitmap(Bitmap paramBitmap, int paramInt)
  {
    this.mBitmap = paramBitmap;
    this.mRotation = (paramInt % 360);
  }

  public Bitmap getBitmap()
  {
    return this.mBitmap;
  }

  public int getHeight()
  {
    if (isOrientationChanged());
    for (int i = this.mBitmap.getWidth(); ; i = this.mBitmap.getHeight())
      return i;
  }

  public Matrix getRotateMatrix()
  {
    Matrix localMatrix = new Matrix();
    if (this.mRotation != 0)
    {
      int i = this.mBitmap.getWidth() / 2;
      int j = this.mBitmap.getHeight() / 2;
      localMatrix.preTranslate(-i, -j);
      localMatrix.postRotate(this.mRotation);
      localMatrix.postTranslate(getWidth() / 2, getHeight() / 2);
    }
    return localMatrix;
  }

  public int getRotation()
  {
    return this.mRotation;
  }

  public int getWidth()
  {
    if (isOrientationChanged());
    for (int i = this.mBitmap.getHeight(); ; i = this.mBitmap.getWidth())
      return i;
  }

  public boolean isOrientationChanged()
  {
    if (this.mRotation / 90 % 2 != 0);
    for (int i = 1; ; i = 0)
      return i;
  }

  public void recycle()
  {
    if (this.mBitmap != null)
    {
      this.mBitmap.recycle();
      this.mBitmap = null;
    }
  }

  public void setBitmap(Bitmap paramBitmap)
  {
    this.mBitmap = paramBitmap;
  }

  public void setRotation(int paramInt)
  {
    this.mRotation = paramInt;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.support.camera.RotateBitmap
 * JD-Core Version:    0.6.0
 */
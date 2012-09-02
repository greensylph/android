package com.instagram.android.imagecache;

import android.graphics.Bitmap;

public class CompressedBackedBitmap
{
  private int compressedImageSize;
  private Bitmap mBitmap;

  public CompressedBackedBitmap(Bitmap paramBitmap, int paramInt)
  {
    this.mBitmap = paramBitmap;
    this.compressedImageSize = paramInt;
  }

  public Bitmap getBitmap()
  {
    return this.mBitmap;
  }

  public int getCompressedImageSize()
  {
    return this.compressedImageSize;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.imagecache.CompressedBackedBitmap
 * JD-Core Version:    0.6.0
 */
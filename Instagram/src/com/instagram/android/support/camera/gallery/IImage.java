package com.instagram.android.support.camera.gallery;

import android.graphics.Bitmap;
import android.net.Uri;
import java.io.InputStream;

public abstract interface IImage
{
  public static final int MINI_THUMB_MAX_NUM_PIXELS = 16384;
  public static final int MINI_THUMB_TARGET_SIZE = 96;
  public static final boolean NO_NATIVE = false;
  public static final boolean NO_ROTATE = false;
  public static final boolean ROTATE_AS_NEEDED = true;
  public static final int THUMBNAIL_MAX_NUM_PIXELS = 196608;
  public static final int THUMBNAIL_TARGET_SIZE = 320;
  public static final int UNCONSTRAINED = -1;
  public static final boolean USE_NATIVE = true;

  public abstract Bitmap fullSizeBitmap(int paramInt1, int paramInt2);

  public abstract Bitmap fullSizeBitmap(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2);

  public abstract InputStream fullSizeImageData();

  public abstract Uri fullSizeImageUri();

  public abstract IImageList getContainer();

  public abstract String getDataPath();

  public abstract long getDateTaken();

  public abstract int getDegreesRotated();

  public abstract int getHeight();

  public abstract String getMimeType();

  public abstract String getTitle();

  public abstract int getWidth();

  public abstract boolean isDrm();

  public abstract boolean isReadonly();

  public abstract Bitmap miniThumbBitmap();

  public abstract boolean rotateImageBy(int paramInt);

  public abstract Bitmap thumbBitmap(boolean paramBoolean);
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.support.camera.gallery.IImage
 * JD-Core Version:    0.6.0
 */
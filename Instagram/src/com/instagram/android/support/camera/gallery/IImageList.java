package com.instagram.android.support.camera.gallery;

import android.net.Uri;
import java.util.HashMap;

public abstract interface IImageList
{
  public abstract void close();

  public abstract HashMap<String, String> getBucketIds();

  public abstract int getCount();

  public abstract IImage getImageAt(int paramInt);

  public abstract IImage getImageForUri(Uri paramUri);

  public abstract int getImageIndex(IImage paramIImage);

  public abstract boolean isEmpty();

  public abstract boolean removeImage(IImage paramIImage);

  public abstract boolean removeImageAt(int paramInt);
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.support.camera.gallery.IImageList
 * JD-Core Version:    0.6.0
 */
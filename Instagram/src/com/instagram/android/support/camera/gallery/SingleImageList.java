package com.instagram.android.support.camera.gallery;

import android.content.ContentResolver;
import android.net.Uri;
import java.util.HashMap;

public class SingleImageList
  implements IImageList
{
  private static final String TAG = "BaseImageList";
  private IImage mSingleImage;
  private Uri mUri;

  public SingleImageList(ContentResolver paramContentResolver, Uri paramUri)
  {
    this.mUri = paramUri;
    this.mSingleImage = new UriImage(this, paramContentResolver, paramUri);
  }

  public void close()
  {
    this.mSingleImage = null;
    this.mUri = null;
  }

  public HashMap<String, String> getBucketIds()
  {
    throw new UnsupportedOperationException();
  }

  public int getCount()
  {
    return 1;
  }

  public IImage getImageAt(int paramInt)
  {
    if (paramInt == 0);
    for (IImage localIImage = this.mSingleImage; ; localIImage = null)
      return localIImage;
  }

  public IImage getImageForUri(Uri paramUri)
  {
    if (paramUri.equals(this.mUri));
    for (IImage localIImage = this.mSingleImage; ; localIImage = null)
      return localIImage;
  }

  public int getImageIndex(IImage paramIImage)
  {
    if (paramIImage == this.mSingleImage);
    for (int i = 0; ; i = -1)
      return i;
  }

  public boolean isEmpty()
  {
    return false;
  }

  public boolean removeImage(IImage paramIImage)
  {
    return false;
  }

  public boolean removeImageAt(int paramInt)
  {
    return false;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.support.camera.gallery.SingleImageList
 * JD-Core Version:    0.6.0
 */
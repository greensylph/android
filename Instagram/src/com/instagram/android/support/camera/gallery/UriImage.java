package com.instagram.android.support.camera.gallery;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import com.instagram.android.support.camera.BitmapManager;
import com.instagram.android.support.camera.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

class UriImage
  implements IImage
{
  private static final String TAG = "UriImage";
  private final IImageList mContainer;
  private final ContentResolver mContentResolver;
  private final Uri mUri;

  UriImage(IImageList paramIImageList, ContentResolver paramContentResolver, Uri paramUri)
  {
    this.mContainer = paramIImageList;
    this.mContentResolver = paramContentResolver;
    this.mUri = paramUri;
  }

  private InputStream getInputStream()
  {
    Object localObject;
    try
    {
      if (this.mUri.getScheme().equals("file"))
      {
        localObject = new FileInputStream(this.mUri.getPath());
      }
      else
      {
        InputStream localInputStream = this.mContentResolver.openInputStream(this.mUri);
        localObject = localInputStream;
      }
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      localObject = null;
    }
    return (InputStream)localObject;
  }

  private ParcelFileDescriptor getPFD()
  {
    Object localObject;
    try
    {
      if (this.mUri.getScheme().equals("file"))
      {
        localObject = ParcelFileDescriptor.open(new File(this.mUri.getPath()), 268435456);
      }
      else
      {
        ParcelFileDescriptor localParcelFileDescriptor = this.mContentResolver.openFileDescriptor(this.mUri, "r");
        localObject = localParcelFileDescriptor;
      }
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      localObject = null;
    }
    return (ParcelFileDescriptor)localObject;
  }

  private BitmapFactory.Options snifBitmapOptions()
  {
    ParcelFileDescriptor localParcelFileDescriptor = getPFD();
    BitmapFactory.Options localOptions;
    if (localParcelFileDescriptor == null)
      localOptions = null;
    while (true)
    {
      return localOptions;
      try
      {
        localOptions = new BitmapFactory.Options();
        localOptions.inJustDecodeBounds = true;
        BitmapManager.instance().decodeFileDescriptor(localParcelFileDescriptor.getFileDescriptor(), localOptions);
        Util.closeSilently(localParcelFileDescriptor);
      }
      finally
      {
        Util.closeSilently(localParcelFileDescriptor);
      }
    }
  }

  public Bitmap fullSizeBitmap(int paramInt1, int paramInt2)
  {
    return fullSizeBitmap(paramInt1, paramInt2, true, false);
  }

  public Bitmap fullSizeBitmap(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    return fullSizeBitmap(paramInt1, paramInt2, paramBoolean, false);
  }

  public Bitmap fullSizeBitmap(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
  {
    try
    {
      Bitmap localBitmap2 = Util.makeBitmap(paramInt1, paramInt2, getPFD(), paramBoolean2);
      localBitmap1 = localBitmap2;
      return localBitmap1;
    }
    catch (Exception localException)
    {
      while (true)
      {
        Log.e("UriImage", "got exception decoding bitmap ", localException);
        Bitmap localBitmap1 = null;
      }
    }
  }

  public InputStream fullSizeImageData()
  {
    return getInputStream();
  }

  public long fullSizeImageId()
  {
    return 0L;
  }

  public Uri fullSizeImageUri()
  {
    return this.mUri;
  }

  public IImageList getContainer()
  {
    return this.mContainer;
  }

  public String getDataPath()
  {
    return this.mUri.getPath();
  }

  public long getDateTaken()
  {
    return 0L;
  }

  public int getDegreesRotated()
  {
    return 0;
  }

  public String getDisplayName()
  {
    return getTitle();
  }

  public int getHeight()
  {
    BitmapFactory.Options localOptions = snifBitmapOptions();
    if (localOptions != null);
    for (int i = localOptions.outHeight; ; i = 0)
      return i;
  }

  public String getMimeType()
  {
    BitmapFactory.Options localOptions = snifBitmapOptions();
    if ((localOptions != null) && (localOptions.outMimeType != null));
    for (String str = localOptions.outMimeType; ; str = "")
      return str;
  }

  public String getTitle()
  {
    return this.mUri.toString();
  }

  public int getWidth()
  {
    BitmapFactory.Options localOptions = snifBitmapOptions();
    if (localOptions != null);
    for (int i = localOptions.outWidth; ; i = 0)
      return i;
  }

  public boolean isDrm()
  {
    return false;
  }

  public boolean isReadonly()
  {
    return true;
  }

  public Bitmap miniThumbBitmap()
  {
    return thumbBitmap(true);
  }

  public boolean rotateImageBy(int paramInt)
  {
    return false;
  }

  public Bitmap thumbBitmap(boolean paramBoolean)
  {
    return fullSizeBitmap(320, 196608, paramBoolean);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.support.camera.gallery.UriImage
 * JD-Core Version:    0.6.0
 */
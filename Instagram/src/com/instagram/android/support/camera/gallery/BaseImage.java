package com.instagram.android.support.camera.gallery;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore.Images.Thumbnails;
import android.util.Log;
import com.instagram.android.support.camera.BitmapManager;
import com.instagram.android.support.camera.Util;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public abstract class BaseImage
  implements IImage
{
  private static final String TAG = "BaseImage";
  private static final int UNKNOWN_LENGTH = -1;
  protected BaseImageList mContainer;
  protected ContentResolver mContentResolver;
  protected String mDataPath;
  private final long mDateTaken;
  private final String mDisplayName;
  private int mHeight = -1;
  protected long mId;
  protected final int mIndex;
  protected String mMimeType;
  protected long mMiniThumbMagic;
  private String mTitle;
  protected Uri mUri;
  private int mWidth = -1;

  protected BaseImage(BaseImageList paramBaseImageList, ContentResolver paramContentResolver, long paramLong1, int paramInt, Uri paramUri, String paramString1, long paramLong2, String paramString2, long paramLong3, String paramString3, String paramString4)
  {
    this.mContainer = paramBaseImageList;
    this.mContentResolver = paramContentResolver;
    this.mId = paramLong1;
    this.mIndex = paramInt;
    this.mUri = paramUri;
    this.mDataPath = paramString1;
    this.mMiniThumbMagic = paramLong2;
    this.mMimeType = paramString2;
    this.mDateTaken = paramLong3;
    this.mTitle = paramString3;
    this.mDisplayName = paramString4;
  }

  private void setupDimension()
  {
    ParcelFileDescriptor localParcelFileDescriptor = null;
    try
    {
      localParcelFileDescriptor = this.mContentResolver.openFileDescriptor(this.mUri, "r");
      BitmapFactory.Options localOptions = new BitmapFactory.Options();
      localOptions.inJustDecodeBounds = true;
      BitmapManager.instance().decodeFileDescriptor(localParcelFileDescriptor.getFileDescriptor(), localOptions);
      this.mWidth = localOptions.outWidth;
      this.mHeight = localOptions.outHeight;
      return;
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      while (true)
      {
        this.mWidth = 0;
        this.mHeight = 0;
        Util.closeSilently(localParcelFileDescriptor);
      }
    }
    finally
    {
      Util.closeSilently(localParcelFileDescriptor);
    }
    throw localObject;
  }

  public boolean equals(Object paramObject)
  {
    if ((paramObject == null) || (!(paramObject instanceof Image)));
    for (boolean bool = false; ; bool = this.mUri.equals(((Image)paramObject).mUri))
      return bool;
  }

  public Bitmap fullSizeBitmap(int paramInt1, int paramInt2)
  {
    return fullSizeBitmap(paramInt1, paramInt2, true, false);
  }

  public Bitmap fullSizeBitmap(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
  {
    Uri localUri = this.mContainer.contentUri(this.mId);
    if (localUri == null);
    for (Bitmap localBitmap = null; ; localBitmap = Util.rotate(localBitmap, getDegreesRotated()))
      do
      {
        return localBitmap;
        localBitmap = Util.makeBitmap(paramInt1, paramInt2, localUri, this.mContentResolver, paramBoolean2);
      }
      while ((localBitmap == null) || (!paramBoolean1));
  }

  public InputStream fullSizeImageData()
  {
    try
    {
      InputStream localInputStream2 = this.mContentResolver.openInputStream(this.mUri);
      localInputStream1 = localInputStream2;
      return localInputStream1;
    }
    catch (IOException localIOException)
    {
      while (true)
        InputStream localInputStream1 = null;
    }
  }

  public long fullSizeImageId()
  {
    return this.mId;
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
    return this.mDataPath;
  }

  public long getDateTaken()
  {
    return this.mDateTaken;
  }

  public int getDegreesRotated()
  {
    return 0;
  }

  public String getDisplayName()
  {
    return this.mDisplayName;
  }

  public int getHeight()
  {
    if (this.mHeight == -1)
      setupDimension();
    return this.mHeight;
  }

  public String getMimeType()
  {
    return this.mMimeType;
  }

  public String getTitle()
  {
    return this.mTitle;
  }

  public int getWidth()
  {
    if (this.mWidth == -1)
      setupDimension();
    return this.mWidth;
  }

  public int hashCode()
  {
    return this.mUri.hashCode();
  }

  public Bitmap miniThumbBitmap()
  {
    Object localObject = null;
    try
    {
      long l = this.mId;
      Bitmap localBitmap1 = MediaStore.Images.Thumbnails.getThumbnail(this.mContentResolver, l, 3, null);
      Bitmap localBitmap2 = localBitmap1;
      if (localBitmap2 != null)
        localBitmap2 = Util.rotate(localBitmap2, getDegreesRotated());
      localObject = localBitmap2;
      return localObject;
    }
    catch (Throwable localThrowable)
    {
      while (true)
        Log.e("BaseImage", "miniThumbBitmap got exception", localThrowable);
    }
  }

  protected void onRemove()
  {
  }

  public String toString()
  {
    return this.mUri.toString();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.support.camera.gallery.BaseImage
 * JD-Core Version:    0.6.0
 */
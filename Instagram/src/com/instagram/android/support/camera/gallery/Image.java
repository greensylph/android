package com.instagram.android.support.camera.gallery;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore.Images.Thumbnails;
import android.util.Log;
import com.instagram.android.support.camera.Util;
import java.io.IOException;

public class Image extends BaseImage
  implements IImage
{
  private static final String TAG = "BaseImage";
  private static final String[] THUMB_PROJECTION;
  private ExifInterface mExif;
  private int mRotation;

  static
  {
    String[] arrayOfString = new String[1];
    arrayOfString[0] = "_id";
    THUMB_PROJECTION = arrayOfString;
  }

  public Image(BaseImageList paramBaseImageList, ContentResolver paramContentResolver, long paramLong1, int paramInt1, Uri paramUri, String paramString1, long paramLong2, String paramString2, long paramLong3, String paramString3, String paramString4, int paramInt2)
  {
    super(paramBaseImageList, paramContentResolver, paramLong1, paramInt1, paramUri, paramString1, paramLong2, paramString2, paramLong3, paramString3, paramString4);
    this.mRotation = paramInt2;
  }

  private void loadExifData()
  {
    try
    {
      this.mExif = new ExifInterface(this.mDataPath);
      return;
    }
    catch (IOException localIOException)
    {
      while (true)
        Log.e("BaseImage", "cannot read exif", localIOException);
    }
  }

  private void saveExifData()
    throws IOException
  {
    if (this.mExif != null)
      this.mExif.saveAttributes();
  }

  private void setExifRotation(int paramInt)
  {
    try
    {
      i = paramInt % 360;
      if (i < 0)
      {
        i += 360;
        break label91;
        while (true)
        {
          replaceExifTag("Orientation", Integer.toString(j));
          saveExifData();
          return;
          j = 1;
          continue;
          j = 6;
          continue;
          j = 3;
          continue;
          j = 8;
        }
      }
    }
    catch (Exception localException)
    {
      int i;
      while (true)
        Log.e("BaseImage", "unable to save exif data with new orientation " + fullSizeImageUri(), localException);
      label91: int j = 1;
      switch (i)
      {
      default:
      case 0:
      case 90:
      case 180:
      case 270:
      }
    }
  }

  public int getDegreesRotated()
  {
    return this.mRotation;
  }

  public boolean isDrm()
  {
    return false;
  }

  public boolean isReadonly()
  {
    String str = getMimeType();
    if ((!"image/jpeg".equals(str)) && (!"image/png".equals(str)));
    for (int i = 1; ; i = 0)
      return i;
  }

  public void replaceExifTag(String paramString1, String paramString2)
  {
    if (this.mExif == null)
      loadExifData();
    this.mExif.setAttribute(paramString1, paramString2);
  }

  public boolean rotateImageBy(int paramInt)
  {
    int i = (paramInt + getDegreesRotated()) % 360;
    setExifRotation(i);
    setDegreesRotated(i);
    return true;
  }

  protected void setDegreesRotated(int paramInt)
  {
    if (this.mRotation == paramInt);
    while (true)
    {
      return;
      this.mRotation = paramInt;
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("orientation", Integer.valueOf(this.mRotation));
      this.mContentResolver.update(this.mUri, localContentValues, null, null);
    }
  }

  public Bitmap thumbBitmap(boolean paramBoolean)
  {
    BitmapFactory.Options localOptions = new BitmapFactory.Options();
    localOptions.inDither = false;
    localOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
    Bitmap localBitmap = MediaStore.Images.Thumbnails.getThumbnail(this.mContentResolver, this.mId, 1, localOptions);
    if ((localBitmap != null) && (paramBoolean))
      localBitmap = Util.rotate(localBitmap, getDegreesRotated());
    return localBitmap;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.support.camera.gallery.Image
 * JD-Core Version:    0.6.0
 */
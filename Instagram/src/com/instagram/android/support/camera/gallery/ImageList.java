package com.instagram.android.support.camera.gallery;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.net.Uri.Builder;
import android.provider.MediaStore.Images.Media;
import java.util.HashMap;

public class ImageList extends BaseImageList
  implements IImageList
{
  private static final String[] ACCEPTABLE_IMAGE_TYPES;
  static final String[] IMAGE_PROJECTION;
  private static final int INDEX_DATA_PATH = 1;
  private static final int INDEX_DATE_MODIFIED = 7;
  private static final int INDEX_DATE_TAKEN = 2;
  private static final int INDEX_ID = 0;
  private static final int INDEX_MIME_TYPE = 6;
  private static final int INDEX_MINI_THUMB_MAGIC = 3;
  private static final int INDEX_ORIENTATION = 4;
  private static final int INDEX_TITLE = 5;
  private static final String TAG = "ImageList";
  private static final String WHERE_CLAUSE = "(mime_type in (?, ?, ?))";
  private static final String WHERE_CLAUSE_WITH_BUCKET_ID = "(mime_type in (?, ?, ?)) AND bucket_id = ?";

  static
  {
    String[] arrayOfString1 = new String[3];
    arrayOfString1[0] = "image/jpeg";
    arrayOfString1[1] = "image/png";
    arrayOfString1[2] = "image/gif";
    ACCEPTABLE_IMAGE_TYPES = arrayOfString1;
    String[] arrayOfString2 = new String[8];
    arrayOfString2[0] = "_id";
    arrayOfString2[1] = "_data";
    arrayOfString2[2] = "datetaken";
    arrayOfString2[3] = "mini_thumb_magic";
    arrayOfString2[4] = "orientation";
    arrayOfString2[5] = "title";
    arrayOfString2[6] = "mime_type";
    arrayOfString2[7] = "date_modified";
    IMAGE_PROJECTION = arrayOfString2;
  }

  public ImageList(ContentResolver paramContentResolver, Uri paramUri, int paramInt, String paramString)
  {
    super(paramContentResolver, paramUri, paramInt, paramString);
  }

  public ImageList(ContentResolver paramContentResolver, Uri paramUri1, Uri paramUri2, int paramInt, String paramString)
  {
    super(paramContentResolver, paramUri1, paramInt, paramString);
    this.mThumbUri = paramUri2;
  }

  protected Cursor createCursor()
  {
    return MediaStore.Images.Media.query(this.mContentResolver, this.mBaseUri, IMAGE_PROJECTION, whereClause(), whereClauseArgs(), sortOrder());
  }

  public HashMap<String, String> getBucketIds()
  {
    Uri localUri = this.mBaseUri.buildUpon().appendQueryParameter("distinct", "true").build();
    ContentResolver localContentResolver = this.mContentResolver;
    String[] arrayOfString = new String[2];
    arrayOfString[0] = "bucket_display_name";
    arrayOfString[1] = "bucket_id";
    Cursor localCursor = MediaStore.Images.Media.query(localContentResolver, localUri, arrayOfString, whereClause(), whereClauseArgs(), null);
    HashMap localHashMap;
    try
    {
      localHashMap = new HashMap();
      if (localCursor.moveToNext())
        localHashMap.put(localCursor.getString(1), localCursor.getString(0));
    }
    finally
    {
      localCursor.close();
    }
    return localHashMap;
  }

  protected long getImageId(Cursor paramCursor)
  {
    return paramCursor.getLong(0);
  }

  protected BaseImage loadImageFromCursor(Cursor paramCursor)
  {
    long l1 = paramCursor.getLong(0);
    String str1 = paramCursor.getString(1);
    long l2 = paramCursor.getLong(2);
    if (l2 == 0L)
      l2 = 1000L * paramCursor.getLong(7);
    long l3 = paramCursor.getLong(3);
    int i = paramCursor.getInt(4);
    String str2 = paramCursor.getString(5);
    String str3 = paramCursor.getString(6);
    if ((str2 == null) || (str2.length() == 0))
      str2 = str1;
    String str4 = str2;
    return new Image(this, this.mContentResolver, l1, paramCursor.getPosition(), contentUri(l1), str1, l3, str3, l2, str2, str4, i);
  }

  protected String whereClause()
  {
    if (this.mBucketId == null);
    for (String str = "(mime_type in (?, ?, ?))"; ; str = "(mime_type in (?, ?, ?)) AND bucket_id = ?")
      return str;
  }

  protected String[] whereClauseArgs()
  {
    String[] arrayOfString;
    if (this.mBucketId != null)
    {
      int i = ACCEPTABLE_IMAGE_TYPES.length;
      arrayOfString = new String[i + 1];
      System.arraycopy(ACCEPTABLE_IMAGE_TYPES, 0, arrayOfString, 0, i);
      arrayOfString[i] = this.mBucketId;
    }
    while (true)
    {
      return arrayOfString;
      arrayOfString = ACCEPTABLE_IMAGE_TYPES;
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.support.camera.gallery.ImageList
 * JD-Core Version:    0.6.0
 */
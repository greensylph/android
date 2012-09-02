package com.instagram.android.support.camera.gallery;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.instagram.android.support.camera.Util;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class BaseImageList
  implements IImageList
{
  private static final int CACHE_CAPACITY = 512;
  private static final String TAG = "BaseImageList";
  private static final Pattern sPathWithId = Pattern.compile("(.*)/\\d+");
  protected Uri mBaseUri;
  protected String mBucketId;
  private final LruCache<Integer, BaseImage> mCache = new LruCache(512);
  protected ContentResolver mContentResolver;
  protected Cursor mCursor;
  protected boolean mCursorDeactivated = false;
  protected int mSort;
  protected Uri mThumbUri;

  public BaseImageList(ContentResolver paramContentResolver, Uri paramUri, int paramInt, String paramString)
  {
    this.mSort = paramInt;
    this.mBaseUri = paramUri;
    this.mBucketId = paramString;
    this.mContentResolver = paramContentResolver;
    this.mCursor = createCursor();
    if (this.mCursor == null)
      Log.w("BaseImageList", "createCursor returns null.");
    this.mCache.clear();
  }

  private Cursor getCursor()
  {
    monitorenter;
    Cursor localCursor;
    try
    {
      if (this.mCursor == null)
      {
        localCursor = null;
        monitorexit;
      }
      else
      {
        if (this.mCursorDeactivated)
        {
          this.mCursor.requery();
          this.mCursorDeactivated = false;
        }
        localCursor = this.mCursor;
        monitorexit;
      }
    }
    finally
    {
      localObject = finally;
      monitorexit;
      throw localObject;
    }
    return localCursor;
  }

  private static String getPathWithoutId(Uri paramUri)
  {
    String str = paramUri.getPath();
    Matcher localMatcher = sPathWithId.matcher(str);
    if (localMatcher.matches())
      str = localMatcher.group(1);
    return str;
  }

  private boolean isChildImageUri(Uri paramUri)
  {
    Uri localUri = this.mBaseUri;
    if ((Util.equals(localUri.getScheme(), paramUri.getScheme())) && (Util.equals(localUri.getHost(), paramUri.getHost())) && (Util.equals(localUri.getAuthority(), paramUri.getAuthority())) && (Util.equals(localUri.getPath(), getPathWithoutId(paramUri))));
    for (int i = 1; ; i = 0)
      return i;
  }

  public void close()
  {
    try
    {
      invalidateCursor();
      this.mContentResolver = null;
      if (this.mCursor != null)
      {
        this.mCursor.close();
        this.mCursor = null;
      }
      return;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      while (true)
        Log.e("BaseImageList", "Caught exception while deactivating cursor.", localIllegalStateException);
    }
  }

  public Uri contentUri(long paramLong)
  {
    try
    {
      if (ContentUris.parseId(this.mBaseUri) != paramLong)
        Log.e("BaseImageList", "id mismatch");
      localUri = this.mBaseUri;
      return localUri;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      while (true)
        Uri localUri = ContentUris.withAppendedId(this.mBaseUri, paramLong);
    }
  }

  protected abstract Cursor createCursor();

  public int getCount()
  {
    Cursor localCursor = getCursor();
    int i;
    if (localCursor == null)
      i = 0;
    while (true)
    {
      return i;
      monitorenter;
      try
      {
        i = localCursor.getCount();
        monitorexit;
        continue;
      }
      finally
      {
        localObject = finally;
        monitorexit;
      }
    }
    throw localObject;
  }

  public IImage getImageAt(int paramInt)
  {
    Object localObject1 = null;
    BaseImage localBaseImage = (BaseImage)this.mCache.get(Integer.valueOf(paramInt));
    Cursor localCursor;
    if (localBaseImage == null)
    {
      localCursor = getCursor();
      if (localCursor == null)
        return localObject1;
      monitorenter;
    }
    while (true)
    {
      try
      {
        if (!localCursor.moveToPosition(paramInt))
          break label84;
        localBaseImage = loadImageFromCursor(localCursor);
        this.mCache.put(Integer.valueOf(paramInt), localBaseImage);
        monitorexit;
      }
      finally
      {
        localObject2 = finally;
        monitorexit;
        throw localObject2;
      }
      localObject1 = localBaseImage;
      break;
      label84: localBaseImage = null;
    }
  }

  // ERROR //
  public IImage getImageForUri(Uri paramUri)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: aload_0
    //   3: aload_1
    //   4: invokespecial 194	com/instagram/android/support/camera/gallery/BaseImageList:isChildImageUri	(Landroid/net/Uri;)Z
    //   7: ifne +5 -> 12
    //   10: aload_2
    //   11: areturn
    //   12: aload_1
    //   13: invokestatic 150	android/content/ContentUris:parseId	(Landroid/net/Uri;)J
    //   16: lstore 5
    //   18: aload_0
    //   19: invokespecial 162	com/instagram/android/support/camera/gallery/BaseImageList:getCursor	()Landroid/database/Cursor;
    //   22: astore 7
    //   24: aload 7
    //   26: ifnull -16 -> 10
    //   29: aload_0
    //   30: monitorenter
    //   31: aload 7
    //   33: bipush 255
    //   35: invokeinterface 182 2 0
    //   40: pop
    //   41: iconst_0
    //   42: istore 10
    //   44: aload 7
    //   46: invokeinterface 197 1 0
    //   51: ifeq +104 -> 155
    //   54: aload_0
    //   55: aload 7
    //   57: invokevirtual 201	com/instagram/android/support/camera/gallery/BaseImageList:getImageId	(Landroid/database/Cursor;)J
    //   60: lload 5
    //   62: lcmp
    //   63: ifne +86 -> 149
    //   66: aload_0
    //   67: getfield 52	com/instagram/android/support/camera/gallery/BaseImageList:mCache	Lcom/instagram/android/support/camera/gallery/LruCache;
    //   70: iload 10
    //   72: invokestatic 172	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   75: invokevirtual 176	com/instagram/android/support/camera/gallery/LruCache:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   78: checkcast 178	com/instagram/android/support/camera/gallery/BaseImage
    //   81: astore_2
    //   82: aload_2
    //   83: ifnonnull +24 -> 107
    //   86: aload_0
    //   87: aload 7
    //   89: invokevirtual 186	com/instagram/android/support/camera/gallery/BaseImageList:loadImageFromCursor	(Landroid/database/Cursor;)Lcom/instagram/android/support/camera/gallery/BaseImage;
    //   92: astore_2
    //   93: aload_0
    //   94: getfield 52	com/instagram/android/support/camera/gallery/BaseImageList:mCache	Lcom/instagram/android/support/camera/gallery/LruCache;
    //   97: iload 10
    //   99: invokestatic 172	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   102: aload_2
    //   103: invokevirtual 190	com/instagram/android/support/camera/gallery/LruCache:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   106: pop
    //   107: aload_0
    //   108: monitorexit
    //   109: goto -99 -> 10
    //   112: astore 8
    //   114: aload_0
    //   115: monitorexit
    //   116: aload 8
    //   118: athrow
    //   119: astore_3
    //   120: ldc 13
    //   122: new 203	java/lang/StringBuilder
    //   125: dup
    //   126: invokespecial 204	java/lang/StringBuilder:<init>	()V
    //   129: ldc 206
    //   131: invokevirtual 210	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   134: aload_1
    //   135: invokevirtual 213	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   138: invokevirtual 216	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   141: aload_3
    //   142: invokestatic 219	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   145: pop
    //   146: goto -136 -> 10
    //   149: iinc 10 1
    //   152: goto -108 -> 44
    //   155: aload_0
    //   156: monitorexit
    //   157: goto -147 -> 10
    //
    // Exception table:
    //   from	to	target	type
    //   31	116	112	finally
    //   155	157	112	finally
    //   12	18	119	java/lang/NumberFormatException
  }

  protected abstract long getImageId(Cursor paramCursor);

  public int getImageIndex(IImage paramIImage)
  {
    return ((BaseImage)paramIImage).mIndex;
  }

  protected void invalidateCache()
  {
    this.mCache.clear();
  }

  protected void invalidateCursor()
  {
    if (this.mCursor == null);
    while (true)
    {
      return;
      this.mCursor.deactivate();
      this.mCursorDeactivated = true;
    }
  }

  public boolean isEmpty()
  {
    if (getCount() == 0);
    for (int i = 1; ; i = 0)
      return i;
  }

  protected abstract BaseImage loadImageFromCursor(Cursor paramCursor);

  public boolean removeImage(IImage paramIImage)
  {
    if (this.mContentResolver.delete(paramIImage.fullSizeImageUri(), null, null) > 0)
    {
      ((BaseImage)paramIImage).onRemove();
      invalidateCursor();
      invalidateCache();
    }
    for (int i = 1; ; i = 0)
      return i;
  }

  public boolean removeImageAt(int paramInt)
  {
    return removeImage(getImageAt(paramInt));
  }

  protected String sortOrder()
  {
    if (this.mSort == 1);
    for (String str = " ASC"; ; str = " DESC")
      return "case ifnull(datetaken,0) when 0 then date_modified*1000 else datetaken end" + str + ", _id" + str;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.support.camera.gallery.BaseImageList
 * JD-Core Version:    0.6.0
 */
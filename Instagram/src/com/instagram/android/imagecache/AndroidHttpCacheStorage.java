package com.instagram.android.imagecache;

import android.content.Context;
import ch.boye.httpclientandroidlib.client.cache.HttpCacheEntry;
import ch.boye.httpclientandroidlib.client.cache.HttpCacheEntrySerializer;
import ch.boye.httpclientandroidlib.client.cache.HttpCacheStorage;
import ch.boye.httpclientandroidlib.client.cache.HttpCacheUpdateCallback;
import ch.boye.httpclientandroidlib.client.cache.HttpCacheUpdateException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class AndroidHttpCacheStorage
  implements HttpCacheStorage
{
  public static final String LOG_TAG = "AndroidHttpCacheStorage";
  public static final int MAX_SIZE = 31457280;
  private final DiskLruCache mDiskLruCache;
  private final HttpCacheEntrySerializer mSerializer;

  public AndroidHttpCacheStorage(Context paramContext, HttpCacheEntrySerializer paramHttpCacheEntrySerializer)
  {
    this.mSerializer = paramHttpCacheEntrySerializer;
    try
    {
      this.mDiskLruCache = DiskLruCache.open(getCacheStorage(paramContext), 0, 1, 31457280L);
      return;
    }
    catch (IOException localIOException)
    {
    }
    throw new RuntimeException("Unable to open file cache");
  }

  private File getCacheStorage(Context paramContext)
    throws IOException
  {
    File localFile = paramContext.getCacheDir();
    if (localFile == null)
      localFile = getExternalStorage(paramContext);
    if (localFile == null)
      throw new IOException("Unable to open storage");
    return localFile;
  }

  private File getExternalStorage(Context paramContext)
  {
    return paramContext.getExternalCacheDir();
  }

  private String getFilenameForKey(String paramString)
  {
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.toHexString(paramString.hashCode());
    return String.format("%s", arrayOfObject);
  }

  private HttpCacheEntry reconstituteEntry(InputStream paramInputStream)
    throws IOException
  {
    return this.mSerializer.readFrom(new BufferedInputStream(paramInputStream));
  }

  public HttpCacheEntry getEntry(String paramString)
    throws IOException
  {
    DiskLruCache.Snapshot localSnapshot = null;
    try
    {
      localSnapshot = this.mDiskLruCache.get(getFilenameForKey(paramString));
      HttpCacheEntry localHttpCacheEntry1;
      if (localSnapshot != null)
      {
        HttpCacheEntry localHttpCacheEntry2 = reconstituteEntry(localSnapshot.getInputStream(0));
        localHttpCacheEntry1 = localHttpCacheEntry2;
      }
      do
      {
        return localHttpCacheEntry1;
        localHttpCacheEntry1 = null;
      }
      while (localSnapshot == null);
      localSnapshot.close();
    }
    finally
    {
      if (localSnapshot != null)
        localSnapshot.close();
    }
  }

  // ERROR //
  public void putEntry(String paramString, HttpCacheEntry paramHttpCacheEntry)
    throws IOException
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_3
    //   2: aconst_null
    //   3: astore 4
    //   5: aload_0
    //   6: getfield 40	com/instagram/android/imagecache/AndroidHttpCacheStorage:mDiskLruCache	Lcom/instagram/android/imagecache/DiskLruCache;
    //   9: aload_0
    //   10: aload_1
    //   11: invokespecial 98	com/instagram/android/imagecache/AndroidHttpCacheStorage:getFilenameForKey	(Ljava/lang/String;)Ljava/lang/String;
    //   14: invokevirtual 119	com/instagram/android/imagecache/DiskLruCache:edit	(Ljava/lang/String;)Lcom/instagram/android/imagecache/DiskLruCache$Editor;
    //   17: astore 4
    //   19: new 121	java/io/BufferedOutputStream
    //   22: dup
    //   23: aload 4
    //   25: iconst_0
    //   26: invokevirtual 127	com/instagram/android/imagecache/DiskLruCache$Editor:newOutputStream	(I)Ljava/io/OutputStream;
    //   29: invokespecial 130	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   32: astore 6
    //   34: aload_0
    //   35: getfield 26	com/instagram/android/imagecache/AndroidHttpCacheStorage:mSerializer	Lch/boye/httpclientandroidlib/client/cache/HttpCacheEntrySerializer;
    //   38: aload_2
    //   39: aload 6
    //   41: invokeinterface 134 3 0
    //   46: aload 6
    //   48: ifnull +8 -> 56
    //   51: aload 6
    //   53: invokevirtual 137	java/io/OutputStream:close	()V
    //   56: aload 4
    //   58: ifnull +8 -> 66
    //   61: aload 4
    //   63: invokevirtual 140	com/instagram/android/imagecache/DiskLruCache$Editor:commit	()V
    //   66: return
    //   67: astore 5
    //   69: aload_3
    //   70: ifnull +7 -> 77
    //   73: aload_3
    //   74: invokevirtual 137	java/io/OutputStream:close	()V
    //   77: aload 4
    //   79: ifnull +8 -> 87
    //   82: aload 4
    //   84: invokevirtual 140	com/instagram/android/imagecache/DiskLruCache$Editor:commit	()V
    //   87: aload 5
    //   89: athrow
    //   90: astore 5
    //   92: aload 6
    //   94: astore_3
    //   95: goto -26 -> 69
    //
    // Exception table:
    //   from	to	target	type
    //   5	34	67	finally
    //   34	46	90	finally
  }

  public void removeEntry(String paramString)
    throws IOException
  {
  }

  public void updateEntry(String paramString, HttpCacheUpdateCallback paramHttpCacheUpdateCallback)
    throws IOException, HttpCacheUpdateException
  {
    DiskLruCache.Snapshot localSnapshot = null;
    try
    {
      localSnapshot = this.mDiskLruCache.get(paramString);
      if (localSnapshot == null);
      HttpCacheEntry localHttpCacheEntry;
      for (Object localObject2 = null; ; localObject2 = localHttpCacheEntry)
      {
        putEntry(paramString, paramHttpCacheUpdateCallback.update((HttpCacheEntry)localObject2));
        return;
        localHttpCacheEntry = reconstituteEntry(localSnapshot.getInputStream(0));
      }
    }
    finally
    {
      if (localSnapshot != null)
        localSnapshot.close();
    }
    throw localObject1;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.imagecache.AndroidHttpCacheStorage
 * JD-Core Version:    0.6.0
 */
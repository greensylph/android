package com.instagram.android.imagecache;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

public class CompressedBackedLruCache<T> extends LruCache<T, CompressedBackedBitmap>
{
  private final int mMaxCount;

  public CompressedBackedLruCache(int paramInt1, int paramInt2, int paramInt3)
  {
    super(paramInt1, paramInt3);
    this.mMaxCount = paramInt2;
  }

  private void trimToCount(int paramInt)
  {
    int i = 0;
    while (true)
    {
      monitorenter;
      if (i == 0);
      try
      {
        if ((this.map.size() <= paramInt) || ((i >= this.mMinTrimCount) && (this.map.size() <= paramInt)) || (this.map.isEmpty()))
          return;
        Map.Entry localEntry = (Map.Entry)this.map.entrySet().iterator().next();
        Object localObject2 = localEntry.getKey();
        CompressedBackedBitmap localCompressedBackedBitmap = (CompressedBackedBitmap)localEntry.getValue();
        this.map.remove(localObject2);
        monitorexit;
        entryRemoved(true, localObject2, localCompressedBackedBitmap, null);
        i++;
        continue;
      }
      finally
      {
        monitorexit;
      }
    }
    throw localObject1;
  }

  public CompressedBackedBitmap put(T paramT, CompressedBackedBitmap paramCompressedBackedBitmap)
  {
    CompressedBackedBitmap localCompressedBackedBitmap = (CompressedBackedBitmap)super.put(paramT, paramCompressedBackedBitmap);
    trimToCount(this.mMaxCount);
    return localCompressedBackedBitmap;
  }

  protected int sizeOf(T paramT, CompressedBackedBitmap paramCompressedBackedBitmap)
  {
    return paramCompressedBackedBitmap.getCompressedImageSize();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.imagecache.CompressedBackedLruCache
 * JD-Core Version:    0.6.0
 */
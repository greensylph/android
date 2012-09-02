package com.instagram.android.imagecache;

import java.util.LinkedHashMap;
import java.util.Map;

public class LruCache<K, V>
{
  private int createCount;
  private int hitCount;
  protected final int mMinTrimCount;
  protected final LinkedHashMap<K, V> map;
  private int maxSize;
  private int missCount;
  private int putCount;
  protected int size;

  public LruCache(int paramInt1, int paramInt2)
  {
    this.mMinTrimCount = paramInt2;
    if (paramInt1 <= 0)
      throw new IllegalArgumentException("maxSize <= 0");
    this.maxSize = paramInt1;
    this.map = new LinkedHashMap(0, 0.75F, true);
  }

  private int safeSizeOf(K paramK, V paramV)
  {
    int i = sizeOf(paramK, paramV);
    if (i < 0)
      throw new IllegalStateException("Negative size: " + paramK + "=" + paramV);
    return i;
  }

  // ERROR //
  private void trimToSize(int paramInt)
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore_2
    //   2: aload_0
    //   3: monitorenter
    //   4: aload_0
    //   5: getfield 70	com/instagram/android/imagecache/LruCache:size	I
    //   8: iflt +20 -> 28
    //   11: aload_0
    //   12: getfield 40	com/instagram/android/imagecache/LruCache:map	Ljava/util/LinkedHashMap;
    //   15: invokevirtual 74	java/util/LinkedHashMap:isEmpty	()Z
    //   18: ifeq +48 -> 66
    //   21: aload_0
    //   22: getfield 70	com/instagram/android/imagecache/LruCache:size	I
    //   25: ifeq +41 -> 66
    //   28: new 47	java/lang/IllegalStateException
    //   31: dup
    //   32: new 49	java/lang/StringBuilder
    //   35: dup
    //   36: invokespecial 50	java/lang/StringBuilder:<init>	()V
    //   39: aload_0
    //   40: invokevirtual 78	java/lang/Object:getClass	()Ljava/lang/Class;
    //   43: invokevirtual 83	java/lang/Class:getName	()Ljava/lang/String;
    //   46: invokevirtual 56	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   49: ldc 85
    //   51: invokevirtual 56	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   54: invokevirtual 65	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   57: invokespecial 66	java/lang/IllegalStateException:<init>	(Ljava/lang/String;)V
    //   60: athrow
    //   61: astore_3
    //   62: aload_0
    //   63: monitorexit
    //   64: aload_3
    //   65: athrow
    //   66: iload_2
    //   67: ifne +11 -> 78
    //   70: aload_0
    //   71: getfield 70	com/instagram/android/imagecache/LruCache:size	I
    //   74: iload_1
    //   75: if_icmple +29 -> 104
    //   78: iload_2
    //   79: aload_0
    //   80: getfield 23	com/instagram/android/imagecache/LruCache:mMinTrimCount	I
    //   83: if_icmplt +11 -> 94
    //   86: aload_0
    //   87: getfield 70	com/instagram/android/imagecache/LruCache:size	I
    //   90: iload_1
    //   91: if_icmple +13 -> 104
    //   94: aload_0
    //   95: getfield 40	com/instagram/android/imagecache/LruCache:map	Ljava/util/LinkedHashMap;
    //   98: invokevirtual 74	java/util/LinkedHashMap:isEmpty	()Z
    //   101: ifeq +6 -> 107
    //   104: aload_0
    //   105: monitorexit
    //   106: return
    //   107: aload_0
    //   108: getfield 40	com/instagram/android/imagecache/LruCache:map	Ljava/util/LinkedHashMap;
    //   111: invokevirtual 89	java/util/LinkedHashMap:entrySet	()Ljava/util/Set;
    //   114: invokeinterface 95 1 0
    //   119: invokeinterface 101 1 0
    //   124: checkcast 103	java/util/Map$Entry
    //   127: astore 4
    //   129: aload 4
    //   131: invokeinterface 106 1 0
    //   136: astore 5
    //   138: aload 4
    //   140: invokeinterface 109 1 0
    //   145: astore 6
    //   147: aload_0
    //   148: getfield 40	com/instagram/android/imagecache/LruCache:map	Ljava/util/LinkedHashMap;
    //   151: aload 5
    //   153: invokevirtual 113	java/util/LinkedHashMap:remove	(Ljava/lang/Object;)Ljava/lang/Object;
    //   156: pop
    //   157: aload_0
    //   158: aload_0
    //   159: getfield 70	com/instagram/android/imagecache/LruCache:size	I
    //   162: aload_0
    //   163: aload 5
    //   165: aload 6
    //   167: invokespecial 115	com/instagram/android/imagecache/LruCache:safeSizeOf	(Ljava/lang/Object;Ljava/lang/Object;)I
    //   170: isub
    //   171: putfield 70	com/instagram/android/imagecache/LruCache:size	I
    //   174: iinc 2 1
    //   177: aload_0
    //   178: monitorexit
    //   179: aload_0
    //   180: iconst_1
    //   181: aload 5
    //   183: aload 6
    //   185: aconst_null
    //   186: invokevirtual 119	com/instagram/android/imagecache/LruCache:entryRemoved	(ZLjava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V
    //   189: goto -187 -> 2
    //
    // Exception table:
    //   from	to	target	type
    //   4	64	61	finally
    //   70	179	61	finally
  }

  protected V create(K paramK)
  {
    return null;
  }

  public final int createCount()
  {
    monitorenter;
    try
    {
      int i = this.createCount;
      monitorexit;
      return i;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  protected void entryRemoved(boolean paramBoolean, K paramK, V paramV1, V paramV2)
  {
  }

  public final void evictAll()
  {
    trimToSize(-1);
  }

  public final V get(K paramK)
  {
    if (paramK == null)
      throw new NullPointerException("key == null");
    monitorenter;
    Object localObject3;
    try
    {
      Object localObject2 = this.map.get(paramK);
      if (localObject2 != null)
      {
        this.hitCount = (1 + this.hitCount);
        monitorexit;
        localObject3 = localObject2;
        break label180;
      }
      this.missCount = (1 + this.missCount);
      monitorexit;
      localObject3 = create(paramK);
      if (localObject3 == null)
        localObject3 = null;
    }
    finally
    {
      monitorexit;
    }
    monitorenter;
    try
    {
      this.createCount = (1 + this.createCount);
      Object localObject5 = this.map.put(paramK, localObject3);
      if (localObject5 != null)
        this.map.put(paramK, localObject5);
      while (true)
      {
        monitorexit;
        if (localObject5 == null)
          break;
        entryRemoved(false, paramK, localObject3, localObject5);
        localObject3 = localObject5;
        break label180;
        this.size += safeSizeOf(paramK, localObject3);
      }
    }
    finally
    {
      monitorexit;
    }
    trimToSize(this.maxSize);
    label180: return localObject3;
  }

  public final int hitCount()
  {
    monitorenter;
    try
    {
      int i = this.hitCount;
      monitorexit;
      return i;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public final int maxSize()
  {
    monitorenter;
    try
    {
      int i = this.maxSize;
      monitorexit;
      return i;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public final int missCount()
  {
    monitorenter;
    try
    {
      int i = this.missCount;
      monitorexit;
      return i;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public V put(K paramK, V paramV)
  {
    if ((paramK == null) || (paramV == null))
      throw new NullPointerException("key == null || value == null");
    monitorenter;
    try
    {
      this.putCount = (1 + this.putCount);
      this.size += safeSizeOf(paramK, paramV);
      Object localObject2 = this.map.put(paramK, paramV);
      if (localObject2 != null)
        this.size -= safeSizeOf(paramK, localObject2);
      monitorexit;
      if (localObject2 != null)
        entryRemoved(false, paramK, localObject2, paramV);
      trimToSize(this.maxSize);
      return localObject2;
    }
    finally
    {
      monitorexit;
    }
    throw localObject1;
  }

  public final int putCount()
  {
    monitorenter;
    try
    {
      int i = this.putCount;
      monitorexit;
      return i;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public final V remove(K paramK)
  {
    if (paramK == null)
      throw new NullPointerException("key == null");
    monitorenter;
    try
    {
      Object localObject2 = this.map.remove(paramK);
      if (localObject2 != null)
        this.size -= safeSizeOf(paramK, localObject2);
      monitorexit;
      if (localObject2 != null)
        entryRemoved(false, paramK, localObject2, null);
      return localObject2;
    }
    finally
    {
      monitorexit;
    }
    throw localObject1;
  }

  public final int size()
  {
    monitorenter;
    try
    {
      int i = this.size;
      monitorexit;
      return i;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  protected int sizeOf(K paramK, V paramV)
  {
    return 1;
  }

  public final Map<K, V> snapshot()
  {
    monitorenter;
    try
    {
      LinkedHashMap localLinkedHashMap = new LinkedHashMap(this.map);
      monitorexit;
      return localLinkedHashMap;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public final String toString()
  {
    int i = 0;
    monitorenter;
    try
    {
      int j = this.hitCount + this.missCount;
      if (j != 0)
        i = 100 * this.hitCount / j;
      Object[] arrayOfObject = new Object[4];
      arrayOfObject[0] = Integer.valueOf(this.maxSize);
      arrayOfObject[1] = Integer.valueOf(this.hitCount);
      arrayOfObject[2] = Integer.valueOf(this.missCount);
      arrayOfObject[3] = Integer.valueOf(i);
      String str = String.format("LruCache[maxSize=%d,hits=%d,misses=%d,hitRate=%d%%]", arrayOfObject);
      monitorexit;
      return str;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.imagecache.LruCache
 * JD-Core Version:    0.6.0
 */
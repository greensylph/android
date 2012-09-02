package com.instagram.android.support.camera.gallery;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class LruCache<K, V>
{
  private final HashMap<K, V> mLruMap;
  private ReferenceQueue<V> mQueue = new ReferenceQueue();
  private final HashMap<K, Entry<K, V>> mWeakMap = new HashMap();

  public LruCache(int paramInt)
  {
    this.mLruMap = new LinkedHashMap(16, 0.75F, true, paramInt)
    {
      protected boolean removeEldestEntry(Map.Entry<K, V> paramEntry)
      {
        if (size() > this.val$capacity);
        for (int i = 1; ; i = 0)
          return i;
      }
    };
  }

  private void cleanUpWeakMap()
  {
    for (Entry localEntry = (Entry)this.mQueue.poll(); localEntry != null; localEntry = (Entry)this.mQueue.poll())
      this.mWeakMap.remove(localEntry.mKey);
  }

  public void clear()
  {
    monitorenter;
    try
    {
      this.mLruMap.clear();
      this.mWeakMap.clear();
      this.mQueue = new ReferenceQueue();
      monitorexit;
      return;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public V get(K paramK)
  {
    monitorenter;
    while (true)
    {
      Object localObject5;
      try
      {
        cleanUpWeakMap();
        Object localObject2 = this.mLruMap.get(paramK);
        localObject3 = localObject2;
        if (localObject3 != null)
          return localObject3;
        Entry localEntry = (Entry)this.mWeakMap.get(paramK);
        if (localEntry != null)
          continue;
        localObject5 = null;
        break label71;
        Object localObject4 = localEntry.get();
        localObject5 = localObject4;
      }
      finally
      {
        monitorexit;
      }
      label71: Object localObject3 = localObject5;
    }
  }

  public V put(K paramK, V paramV)
  {
    monitorenter;
    try
    {
      cleanUpWeakMap();
      this.mLruMap.put(paramK, paramV);
      Entry localEntry = (Entry)this.mWeakMap.put(paramK, new Entry(paramK, paramV, this.mQueue));
      if (localEntry == null);
      Object localObject2;
      for (Object localObject3 = null; ; localObject3 = localObject2)
      {
        return localObject3;
        localObject2 = localEntry.get();
      }
    }
    finally
    {
      monitorexit;
    }
    throw localObject1;
  }

  private static class Entry<K, V> extends WeakReference<V>
  {
    K mKey;

    public Entry(K paramK, V paramV, ReferenceQueue<V> paramReferenceQueue)
    {
      super(paramReferenceQueue);
      this.mKey = paramK;
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.support.camera.gallery.LruCache
 * JD-Core Version:    0.6.0
 */
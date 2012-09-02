package com.instagram.android.imagecache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import ch.boye.httpclientandroidlib.HttpEntity;
import ch.boye.httpclientandroidlib.impl.client.DefaultHttpClient;
import ch.boye.httpclientandroidlib.impl.client.cache.DefaultHttpCacheEntrySerializer;
import ch.boye.httpclientandroidlib.impl.conn.tsccm.ThreadSafeClientConnManager;
import ch.boye.httpclientandroidlib.params.HttpParams;
import ch.boye.httpclientandroidlib.util.ByteArrayBuffer;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class IgBitmapCache
{
  private static final String LOG_TAG = "IgBitmapCache";
  public static final int MAX_ENTRIES = 500;
  public static final int MAX_SIZE = 31457280;
  private static final int MAX_TASKS = 4;
  public static final int MIN_TRIM_COUNT = 60;
  static final BitmapFactory.Options options = new BitmapFactory.Options();
  private static IgBitmapCache sInstance;
  private HashMap<String, LoadBitmapTask> mAllTasks = new HashMap();
  private DefaultHttpClient mHttpClient;
  private CompressedBackedLruCache<String> mLoadedBitmaps = new CompressedBackedLruCache(31457280, 500, 60);
  private HashSet<LoadBitmapTask> mRunningTasks = new HashSet();
  private AndroidHttpCacheStorage mStorage;
  private LinkedList<LoadBitmapTask> mTaskQueue = new LinkedList();

  static
  {
    options.inPurgeable = true;
    options.inInputShareable = false;
  }

  public IgBitmapCache(Context paramContext)
  {
    ThreadSafeClientConnManager localThreadSafeClientConnManager = new ThreadSafeClientConnManager();
    localThreadSafeClientConnManager.setDefaultMaxPerRoute(10);
    localThreadSafeClientConnManager.setMaxTotal(10);
    this.mStorage = new AndroidHttpCacheStorage(paramContext, new DefaultHttpCacheEntrySerializer());
    this.mHttpClient = new DefaultHttpClient(localThreadSafeClientConnManager);
    this.mHttpClient.getParams().setIntParameter("http.socket.timeout", 30000);
    this.mHttpClient.getParams().setIntParameter("http.connection.timeout", 30000);
    this.mHttpClient.getParams().setBooleanParameter("http.tcp.nodelay", true);
  }

  private static void createCache(Context paramContext)
  {
    monitorenter;
    try
    {
      IgBitmapCache localIgBitmapCache = sInstance;
      if (localIgBitmapCache != null);
      while (true)
      {
        return;
        sInstance = new IgBitmapCache(paramContext);
      }
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public static IgBitmapCache getInstance(Context paramContext)
  {
    if (sInstance == null)
      createCache(paramContext);
    return sInstance;
  }

  private void queueTask(boolean paramBoolean, LoadBitmapTask paramLoadBitmapTask)
  {
    if (paramBoolean)
      this.mTaskQueue.addFirst(paramLoadBitmapTask);
    while (true)
    {
      return;
      this.mTaskQueue.addLast(paramLoadBitmapTask);
    }
  }

  public void loadBitmap(String paramString, BitmapCallback paramBitmapCallback, boolean paramBoolean1, boolean paramBoolean2)
  {
    CompressedBackedBitmap localCompressedBackedBitmap = (CompressedBackedBitmap)this.mLoadedBitmaps.get(paramString);
    if (localCompressedBackedBitmap == null)
      if (this.mAllTasks.containsKey(paramString))
      {
        LoadBitmapTask localLoadBitmapTask2 = (LoadBitmapTask)this.mAllTasks.get(paramString);
        if (paramBitmapCallback != null)
        {
          localLoadBitmapTask2.addCallback(paramBitmapCallback);
          if (paramBoolean1)
            localLoadBitmapTask2.setReportProgress(paramBoolean1);
        }
        if (!this.mRunningTasks.contains(localLoadBitmapTask2))
        {
          this.mTaskQueue.remove(localLoadBitmapTask2);
          queueTask(paramBoolean2, localLoadBitmapTask2);
        }
        updateTasks();
      }
    while (true)
    {
      return;
      LoadBitmapTask localLoadBitmapTask1 = new LoadBitmapTask(paramString, paramBoolean1);
      if (paramBitmapCallback != null)
        localLoadBitmapTask1.addCallback(paramBitmapCallback);
      this.mAllTasks.put(paramString, localLoadBitmapTask1);
      queueTask(paramBoolean2, localLoadBitmapTask1);
      break;
      if (paramBitmapCallback == null)
        continue;
      paramBitmapCallback.setBitmap(paramString, localCompressedBackedBitmap.getBitmap());
    }
  }

  public void preLoadBitmaps(String[] paramArrayOfString)
  {
    int i = paramArrayOfString.length;
    for (int j = 0; j < i; j++)
      loadBitmap(paramArrayOfString[j], null, false, false);
  }

  public void updateTasks()
  {
    while ((this.mRunningTasks.size() < 4) && (!this.mTaskQueue.isEmpty()))
    {
      LoadBitmapTask localLoadBitmapTask = (LoadBitmapTask)this.mTaskQueue.removeFirst();
      this.mRunningTasks.add(localLoadBitmapTask);
      localLoadBitmapTask.executeOnExecutor(IgAsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }
  }

  public void warm(String paramString)
  {
    CompressedBackedBitmap localCompressedBackedBitmap = (CompressedBackedBitmap)this.mLoadedBitmaps.get(paramString);
    if (localCompressedBackedBitmap != null)
      localCompressedBackedBitmap.getBitmap().getPixel(0, 0);
  }

  public static abstract interface BitmapCallback
  {
    public abstract void bindToTask(IgBitmapCache.LoadBitmapTask paramLoadBitmapTask);

    public abstract void reportError();

    public abstract void reportProgress(String paramString, int paramInt);

    public abstract void setBitmap(String paramString, Bitmap paramBitmap);
  }

  public class LoadBitmapTask extends IgAsyncTask<Void, Integer, Bitmap>
  {
    private List<IgBitmapCache.BitmapCallback> mBitmapCallbacks = new ArrayList();
    private int mProgress = 0;
    private boolean mReportProgress;
    private String mUrl;

    public LoadBitmapTask(String paramBoolean, boolean arg3)
    {
      this.mUrl = paramBoolean;
      boolean bool;
      this.mReportProgress = bool;
    }

    private void reportProgressToCallbacks(Integer paramInteger)
    {
      Iterator localIterator = this.mBitmapCallbacks.iterator();
      while (localIterator.hasNext())
      {
        IgBitmapCache.BitmapCallback localBitmapCallback = (IgBitmapCache.BitmapCallback)localIterator.next();
        if (localBitmapCallback == null)
          continue;
        localBitmapCallback.reportProgress(this.mUrl, paramInteger.intValue());
      }
    }

    public void addCallback(IgBitmapCache.BitmapCallback paramBitmapCallback)
    {
      paramBitmapCallback.bindToTask(this);
      paramBitmapCallback.reportProgress(this.mUrl, this.mProgress);
      this.mBitmapCallbacks.add(paramBitmapCallback);
    }

    // ERROR //
    protected Bitmap doInBackground(Void[] paramArrayOfVoid)
    {
      // Byte code:
      //   0: aconst_null
      //   1: astore_2
      //   2: aload_0
      //   3: getfield 23	com/instagram/android/imagecache/IgBitmapCache$LoadBitmapTask:this$0	Lcom/instagram/android/imagecache/IgBitmapCache;
      //   6: invokestatic 85	com/instagram/android/imagecache/IgBitmapCache:access$000	(Lcom/instagram/android/imagecache/IgBitmapCache;)Lcom/instagram/android/imagecache/AndroidHttpCacheStorage;
      //   9: aload_0
      //   10: getfield 35	com/instagram/android/imagecache/IgBitmapCache$LoadBitmapTask:mUrl	Ljava/lang/String;
      //   13: invokevirtual 91	com/instagram/android/imagecache/AndroidHttpCacheStorage:getEntry	(Ljava/lang/String;)Lch/boye/httpclientandroidlib/client/cache/HttpCacheEntry;
      //   16: astore 9
      //   18: aload 9
      //   20: ifnull +72 -> 92
      //   23: aload_0
      //   24: aload 9
      //   26: invokevirtual 97	ch/boye/httpclientandroidlib/client/cache/HttpCacheEntry:getResource	()Lch/boye/httpclientandroidlib/client/cache/Resource;
      //   29: invokeinterface 103 1 0
      //   34: invokevirtual 107	com/instagram/android/imagecache/IgBitmapCache$LoadBitmapTask:readBytes	(Ljava/io/InputStream;)[B
      //   37: astore 12
      //   39: aload 12
      //   41: iconst_0
      //   42: aload 12
      //   44: arraylength
      //   45: getstatic 111	com/instagram/android/imagecache/IgBitmapCache:options	Landroid/graphics/BitmapFactory$Options;
      //   48: invokestatic 117	android/graphics/BitmapFactory:decodeByteArray	([BIILandroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
      //   51: astore 8
      //   53: aload 8
      //   55: ifnull +167 -> 222
      //   58: aload_0
      //   59: getfield 23	com/instagram/android/imagecache/IgBitmapCache$LoadBitmapTask:this$0	Lcom/instagram/android/imagecache/IgBitmapCache;
      //   62: invokestatic 121	com/instagram/android/imagecache/IgBitmapCache:access$200	(Lcom/instagram/android/imagecache/IgBitmapCache;)Lcom/instagram/android/imagecache/CompressedBackedLruCache;
      //   65: aload_0
      //   66: getfield 35	com/instagram/android/imagecache/IgBitmapCache$LoadBitmapTask:mUrl	Ljava/lang/String;
      //   69: new 123	com/instagram/android/imagecache/CompressedBackedBitmap
      //   72: dup
      //   73: aload 8
      //   75: aload 12
      //   77: arraylength
      //   78: invokespecial 126	com/instagram/android/imagecache/CompressedBackedBitmap:<init>	(Landroid/graphics/Bitmap;I)V
      //   81: invokevirtual 132	com/instagram/android/imagecache/CompressedBackedLruCache:put	(Ljava/lang/Object;Lcom/instagram/android/imagecache/CompressedBackedBitmap;)Lcom/instagram/android/imagecache/CompressedBackedBitmap;
      //   84: pop
      //   85: aload_2
      //   86: invokestatic 138	ch/boye/httpclientandroidlib/util/EntityUtils:consume	(Lch/boye/httpclientandroidlib/HttpEntity;)V
      //   89: aload 8
      //   91: areturn
      //   92: new 140	java/util/Date
      //   95: dup
      //   96: invokespecial 141	java/util/Date:<init>	()V
      //   99: astore 10
      //   101: aload_0
      //   102: getfield 23	com/instagram/android/imagecache/IgBitmapCache$LoadBitmapTask:this$0	Lcom/instagram/android/imagecache/IgBitmapCache;
      //   105: invokestatic 145	com/instagram/android/imagecache/IgBitmapCache:access$100	(Lcom/instagram/android/imagecache/IgBitmapCache;)Lch/boye/httpclientandroidlib/impl/client/DefaultHttpClient;
      //   108: new 147	ch/boye/httpclientandroidlib/client/methods/HttpGet
      //   111: dup
      //   112: aload_0
      //   113: getfield 35	com/instagram/android/imagecache/IgBitmapCache$LoadBitmapTask:mUrl	Ljava/lang/String;
      //   116: invokespecial 150	ch/boye/httpclientandroidlib/client/methods/HttpGet:<init>	(Ljava/lang/String;)V
      //   119: invokevirtual 156	ch/boye/httpclientandroidlib/impl/client/DefaultHttpClient:execute	(Lch/boye/httpclientandroidlib/client/methods/HttpUriRequest;)Lch/boye/httpclientandroidlib/HttpResponse;
      //   122: astore 11
      //   124: aload 11
      //   126: invokeinterface 162 1 0
      //   131: astore_2
      //   132: aload_0
      //   133: aload_2
      //   134: invokevirtual 166	com/instagram/android/imagecache/IgBitmapCache$LoadBitmapTask:toByteArray	(Lch/boye/httpclientandroidlib/HttpEntity;)[B
      //   137: astore 12
      //   139: new 140	java/util/Date
      //   142: dup
      //   143: invokespecial 141	java/util/Date:<init>	()V
      //   146: astore 13
      //   148: aload_0
      //   149: getfield 23	com/instagram/android/imagecache/IgBitmapCache$LoadBitmapTask:this$0	Lcom/instagram/android/imagecache/IgBitmapCache;
      //   152: invokestatic 85	com/instagram/android/imagecache/IgBitmapCache:access$000	(Lcom/instagram/android/imagecache/IgBitmapCache;)Lcom/instagram/android/imagecache/AndroidHttpCacheStorage;
      //   155: aload_0
      //   156: getfield 35	com/instagram/android/imagecache/IgBitmapCache$LoadBitmapTask:mUrl	Ljava/lang/String;
      //   159: new 93	ch/boye/httpclientandroidlib/client/cache/HttpCacheEntry
      //   162: dup
      //   163: aload 10
      //   165: aload 13
      //   167: aload 11
      //   169: invokeinterface 170 1 0
      //   174: aload 11
      //   176: invokeinterface 174 1 0
      //   181: new 176	ch/boye/httpclientandroidlib/impl/client/cache/HeapResource
      //   184: dup
      //   185: aload 12
      //   187: invokespecial 179	ch/boye/httpclientandroidlib/impl/client/cache/HeapResource:<init>	([B)V
      //   190: invokespecial 182	ch/boye/httpclientandroidlib/client/cache/HttpCacheEntry:<init>	(Ljava/util/Date;Ljava/util/Date;Lch/boye/httpclientandroidlib/StatusLine;[Lch/boye/httpclientandroidlib/Header;Lch/boye/httpclientandroidlib/client/cache/Resource;)V
      //   193: invokevirtual 186	com/instagram/android/imagecache/AndroidHttpCacheStorage:putEntry	(Ljava/lang/String;Lch/boye/httpclientandroidlib/client/cache/HttpCacheEntry;)V
      //   196: goto -157 -> 39
      //   199: astore 5
      //   201: ldc 188
      //   203: aload 5
      //   205: invokevirtual 192	java/io/IOException:toString	()Ljava/lang/String;
      //   208: invokestatic 198	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
      //   211: pop
      //   212: aload_2
      //   213: invokestatic 138	ch/boye/httpclientandroidlib/util/EntityUtils:consume	(Lch/boye/httpclientandroidlib/HttpEntity;)V
      //   216: aconst_null
      //   217: astore 8
      //   219: goto -130 -> 89
      //   222: aload_2
      //   223: invokestatic 138	ch/boye/httpclientandroidlib/util/EntityUtils:consume	(Lch/boye/httpclientandroidlib/HttpEntity;)V
      //   226: goto -10 -> 216
      //   229: astore 14
      //   231: goto -15 -> 216
      //   234: astore_3
      //   235: aload_2
      //   236: invokestatic 138	ch/boye/httpclientandroidlib/util/EntityUtils:consume	(Lch/boye/httpclientandroidlib/HttpEntity;)V
      //   239: aload_3
      //   240: athrow
      //   241: astore 16
      //   243: goto -154 -> 89
      //   246: astore 7
      //   248: goto -32 -> 216
      //   251: astore 4
      //   253: goto -14 -> 239
      //
      // Exception table:
      //   from	to	target	type
      //   2	85	199	java/io/IOException
      //   92	196	199	java/io/IOException
      //   222	226	229	java/io/IOException
      //   2	85	234	finally
      //   92	196	234	finally
      //   201	212	234	finally
      //   85	89	241	java/io/IOException
      //   212	216	246	java/io/IOException
      //   235	239	251	java/io/IOException
    }

    public String getUrl()
    {
      return this.mUrl;
    }

    protected void onPostExecute(Bitmap paramBitmap)
    {
      Iterator localIterator = this.mBitmapCallbacks.iterator();
      while (localIterator.hasNext())
      {
        IgBitmapCache.BitmapCallback localBitmapCallback = (IgBitmapCache.BitmapCallback)localIterator.next();
        if (localBitmapCallback == null)
          continue;
        if (paramBitmap != null)
        {
          localBitmapCallback.setBitmap(this.mUrl, paramBitmap);
          continue;
        }
        localBitmapCallback.reportError();
      }
      IgBitmapCache.this.mAllTasks.remove(this.mUrl);
      IgBitmapCache.this.mRunningTasks.remove(this);
      IgBitmapCache.this.updateTasks();
      super.onPostExecute(paramBitmap);
    }

    protected void onPreExecute()
    {
      reportProgressToCallbacks(Integer.valueOf(0));
      super.onPreExecute();
    }

    protected void onProgressUpdate(Integer[] paramArrayOfInteger)
    {
      this.mProgress = paramArrayOfInteger[0].intValue();
      reportProgressToCallbacks(Integer.valueOf(this.mProgress));
      super.onProgressUpdate(paramArrayOfInteger);
    }

    public byte[] readBytes(InputStream paramInputStream)
      throws IOException
    {
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      byte[] arrayOfByte = new byte[4096];
      while (true)
      {
        int i = paramInputStream.read(arrayOfByte);
        if (i == -1)
          break;
        localByteArrayOutputStream.write(arrayOfByte, 0, i);
      }
      return localByteArrayOutputStream.toByteArray();
    }

    public void removeCallback(IgBitmapCache.BitmapCallback paramBitmapCallback)
    {
      this.mBitmapCallbacks.remove(paramBitmapCallback);
    }

    public void setReportProgress(boolean paramBoolean)
    {
      this.mReportProgress = paramBoolean;
    }

    public byte[] toByteArray(HttpEntity paramHttpEntity)
      throws IOException
    {
      int i = 4096;
      if (paramHttpEntity == null)
        throw new IllegalArgumentException("HTTP entity may not be null");
      InputStream localInputStream = paramHttpEntity.getContent();
      Object localObject2;
      if (localInputStream == null)
      {
        localObject2 = null;
        return localObject2;
      }
      try
      {
        if (paramHttpEntity.getContentLength() > 2147483647L)
          throw new IllegalArgumentException("HTTP entity too large to be buffered in memory");
      }
      finally
      {
        localInputStream.close();
      }
      int j = (int)paramHttpEntity.getContentLength();
      if (j < 0);
      for (int k = i; ; k = j)
      {
        ByteArrayBuffer localByteArrayBuffer = new ByteArrayBuffer(k);
        if (j < 0);
        while (true)
        {
          byte[] arrayOfByte1 = new byte[i];
          int m = 0;
          while (true)
          {
            int n = localInputStream.read(arrayOfByte1);
            if (n == -1)
              break;
            localByteArrayBuffer.append(arrayOfByte1, 0, n);
            if ((!this.mReportProgress) || (j <= 0))
              continue;
            int i1 = (int)(100.0F * (localByteArrayBuffer.length() / j));
            if (i1 / 10 <= m)
              continue;
            Integer[] arrayOfInteger = new Integer[1];
            arrayOfInteger[0] = Integer.valueOf(i1);
            publishProgress(arrayOfInteger);
            m = i1 / 10;
          }
          i = j / 10;
        }
        byte[] arrayOfByte2 = localByteArrayBuffer.toByteArray();
        localObject2 = arrayOfByte2;
        localInputStream.close();
        break;
      }
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.imagecache.IgBitmapCache
 * JD-Core Version:    0.6.0
 */
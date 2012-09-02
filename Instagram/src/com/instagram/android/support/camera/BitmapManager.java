package com.instagram.android.support.camera;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.util.Log;
import java.io.FileDescriptor;
import java.util.WeakHashMap;

public class BitmapManager
{
  private static final String TAG = "BitmapManager";
  private static BitmapManager sManager = null;
  private final WeakHashMap<Thread, ThreadStatus> mThreadStatus = new WeakHashMap();

  private ThreadStatus getOrCreateThreadStatus(Thread paramThread)
  {
    monitorenter;
    try
    {
      ThreadStatus localThreadStatus = (ThreadStatus)this.mThreadStatus.get(paramThread);
      if (localThreadStatus == null)
      {
        localThreadStatus = new ThreadStatus(null);
        this.mThreadStatus.put(paramThread, localThreadStatus);
      }
      monitorexit;
      return localThreadStatus;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public static BitmapManager instance()
  {
    monitorenter;
    try
    {
      if (sManager == null)
        sManager = new BitmapManager();
      BitmapManager localBitmapManager = sManager;
      monitorexit;
      return localBitmapManager;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  private void setDecodingOptions(Thread paramThread, BitmapFactory.Options paramOptions)
  {
    monitorenter;
    try
    {
      getOrCreateThreadStatus(paramThread).mOptions = paramOptions;
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

  public void allowThreadDecoding(Thread paramThread)
  {
    monitorenter;
    try
    {
      getOrCreateThreadStatus(paramThread).mState = State.ALLOW;
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

  public boolean canThreadDecoding(Thread paramThread)
  {
    int i = 1;
    monitorenter;
    try
    {
      ThreadStatus localThreadStatus = (ThreadStatus)this.mThreadStatus.get(paramThread);
      if (localThreadStatus == null);
      State localState1;
      State localState2;
      do
      {
        return i;
        localState1 = localThreadStatus.mState;
        localState2 = State.CANCEL;
      }
      while (localState1 != localState2);
      while (true)
        i = 0;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  // ERROR //
  public void cancelThreadDecoding(Thread paramThread, android.content.ContentResolver paramContentResolver)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: aload_1
    //   4: invokespecial 53	com/instagram/android/support/camera/BitmapManager:getOrCreateThreadStatus	(Ljava/lang/Thread;)Lcom/instagram/android/support/camera/BitmapManager$ThreadStatus;
    //   7: astore 4
    //   9: aload 4
    //   11: getstatic 71	com/instagram/android/support/camera/BitmapManager$State:CANCEL	Lcom/instagram/android/support/camera/BitmapManager$State;
    //   14: putfield 66	com/instagram/android/support/camera/BitmapManager$ThreadStatus:mState	Lcom/instagram/android/support/camera/BitmapManager$State;
    //   17: aload 4
    //   19: getfield 57	com/instagram/android/support/camera/BitmapManager$ThreadStatus:mOptions	Landroid/graphics/BitmapFactory$Options;
    //   22: ifnull +11 -> 33
    //   25: aload 4
    //   27: getfield 57	com/instagram/android/support/camera/BitmapManager$ThreadStatus:mOptions	Landroid/graphics/BitmapFactory$Options;
    //   30: invokevirtual 80	android/graphics/BitmapFactory$Options:requestCancelDecode	()V
    //   33: aload_0
    //   34: invokevirtual 83	java/lang/Object:notifyAll	()V
    //   37: aload 4
    //   39: monitorenter
    //   40: aload 4
    //   42: getfield 87	com/instagram/android/support/camera/BitmapManager$ThreadStatus:mThumbRequesting	Z
    //   45: ifeq +43 -> 88
    //   48: aload_2
    //   49: aload_1
    //   50: invokevirtual 93	java/lang/Thread:getId	()J
    //   53: invokestatic 99	android/provider/MediaStore$Images$Thumbnails:cancelThumbnailRequest	(Landroid/content/ContentResolver;J)V
    //   56: aload_2
    //   57: aload_1
    //   58: invokevirtual 93	java/lang/Thread:getId	()J
    //   61: invokestatic 102	android/provider/MediaStore$Video$Thumbnails:cancelThumbnailRequest	(Landroid/content/ContentResolver;J)V
    //   64: aload 4
    //   66: ldc2_w 103
    //   69: invokevirtual 108	java/lang/Object:wait	(J)V
    //   72: goto -32 -> 40
    //   75: astore 6
    //   77: aload 4
    //   79: monitorexit
    //   80: aload 6
    //   82: athrow
    //   83: astore 5
    //   85: aload_0
    //   86: monitorexit
    //   87: return
    //   88: aload 4
    //   90: monitorexit
    //   91: goto -6 -> 85
    //   94: astore_3
    //   95: aload_0
    //   96: monitorexit
    //   97: aload_3
    //   98: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   40	80	75	finally
    //   88	91	75	finally
    //   37	40	83	java/lang/InterruptedException
    //   80	83	83	java/lang/InterruptedException
    //   2	37	94	finally
    //   37	40	94	finally
    //   80	83	94	finally
  }

  public Bitmap decodeFileDescriptor(FileDescriptor paramFileDescriptor, BitmapFactory.Options paramOptions)
  {
    Bitmap localBitmap = null;
    if (paramOptions.mCancel);
    while (true)
    {
      return localBitmap;
      Thread localThread = Thread.currentThread();
      if (!canThreadDecoding(localThread))
      {
        Log.d("BitmapManager", "Thread " + localThread + " is not allowed to decode.");
        continue;
      }
      setDecodingOptions(localThread, paramOptions);
      localBitmap = BitmapFactory.decodeFileDescriptor(paramFileDescriptor, null, paramOptions);
      removeDecodingOptions(localThread);
    }
  }

  // ERROR //
  public Bitmap getThumbnail(android.content.ContentResolver paramContentResolver, long paramLong, int paramInt, BitmapFactory.Options paramOptions, boolean paramBoolean)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 7
    //   3: invokestatic 117	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   6: astore 8
    //   8: aload_0
    //   9: aload 8
    //   11: invokespecial 53	com/instagram/android/support/camera/BitmapManager:getOrCreateThreadStatus	(Ljava/lang/Thread;)Lcom/instagram/android/support/camera/BitmapManager$ThreadStatus;
    //   14: astore 9
    //   16: aload_0
    //   17: aload 8
    //   19: invokevirtual 119	com/instagram/android/support/camera/BitmapManager:canThreadDecoding	(Ljava/lang/Thread;)Z
    //   22: ifne +37 -> 59
    //   25: ldc 16
    //   27: new 121	java/lang/StringBuilder
    //   30: dup
    //   31: invokespecial 122	java/lang/StringBuilder:<init>	()V
    //   34: ldc 124
    //   36: invokevirtual 128	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   39: aload 8
    //   41: invokevirtual 131	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   44: ldc 133
    //   46: invokevirtual 128	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   49: invokevirtual 137	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   52: invokestatic 143	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   55: pop
    //   56: aload 7
    //   58: areturn
    //   59: aload 9
    //   61: monitorenter
    //   62: aload 9
    //   64: iconst_1
    //   65: putfield 87	com/instagram/android/support/camera/BitmapManager$ThreadStatus:mThumbRequesting	Z
    //   68: aload 9
    //   70: monitorexit
    //   71: iload 6
    //   73: ifeq +79 -> 152
    //   76: aload_1
    //   77: aload 8
    //   79: invokevirtual 93	java/lang/Thread:getId	()J
    //   82: iload 4
    //   84: aconst_null
    //   85: invokestatic 158	android/provider/MediaStore$Video$Thumbnails:getThumbnail	(Landroid/content/ContentResolver;JILandroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
    //   88: astore 15
    //   90: aload 15
    //   92: astore 7
    //   94: aload 9
    //   96: monitorenter
    //   97: aload 9
    //   99: iconst_0
    //   100: putfield 87	com/instagram/android/support/camera/BitmapManager$ThreadStatus:mThumbRequesting	Z
    //   103: aload 9
    //   105: invokevirtual 83	java/lang/Object:notifyAll	()V
    //   108: aload 9
    //   110: monitorexit
    //   111: goto -55 -> 56
    //   114: astore 16
    //   116: aload 9
    //   118: monitorexit
    //   119: aload 16
    //   121: athrow
    //   122: astore 12
    //   124: aload 9
    //   126: monitorexit
    //   127: aload 12
    //   129: athrow
    //   130: astore 10
    //   132: aload 9
    //   134: monitorenter
    //   135: aload 9
    //   137: iconst_0
    //   138: putfield 87	com/instagram/android/support/camera/BitmapManager$ThreadStatus:mThumbRequesting	Z
    //   141: aload 9
    //   143: invokevirtual 83	java/lang/Object:notifyAll	()V
    //   146: aload 9
    //   148: monitorexit
    //   149: aload 10
    //   151: athrow
    //   152: aload_1
    //   153: aload 8
    //   155: invokevirtual 93	java/lang/Thread:getId	()J
    //   158: iload 4
    //   160: aconst_null
    //   161: invokestatic 159	android/provider/MediaStore$Images$Thumbnails:getThumbnail	(Landroid/content/ContentResolver;JILandroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
    //   164: astore 13
    //   166: aload 13
    //   168: astore 7
    //   170: aload 9
    //   172: monitorenter
    //   173: aload 9
    //   175: iconst_0
    //   176: putfield 87	com/instagram/android/support/camera/BitmapManager$ThreadStatus:mThumbRequesting	Z
    //   179: aload 9
    //   181: invokevirtual 83	java/lang/Object:notifyAll	()V
    //   184: aload 9
    //   186: monitorexit
    //   187: goto -131 -> 56
    //   190: astore 14
    //   192: aload 9
    //   194: monitorexit
    //   195: aload 14
    //   197: athrow
    //   198: astore 11
    //   200: aload 9
    //   202: monitorexit
    //   203: aload 11
    //   205: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   97	119	114	finally
    //   62	71	122	finally
    //   124	127	122	finally
    //   59	62	130	finally
    //   76	90	130	finally
    //   127	130	130	finally
    //   152	166	130	finally
    //   173	195	190	finally
    //   135	149	198	finally
    //   200	203	198	finally
  }

  void removeDecodingOptions(Thread paramThread)
  {
    monitorenter;
    try
    {
      ((ThreadStatus)this.mThreadStatus.get(paramThread)).mOptions = null;
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

  private static enum State
  {
    static
    {
      ALLOW = new State("ALLOW", 1);
      State[] arrayOfState = new State[2];
      arrayOfState[0] = CANCEL;
      arrayOfState[1] = ALLOW;
      $VALUES = arrayOfState;
    }
  }

  private static class ThreadStatus
  {
    public BitmapFactory.Options mOptions;
    public BitmapManager.State mState = BitmapManager.State.ALLOW;
    public boolean mThumbRequesting;

    public String toString()
    {
      String str;
      if (this.mState == BitmapManager.State.CANCEL)
        str = "Cancel";
      while (true)
      {
        return "thread state = " + str + ", options = " + this.mOptions;
        if (this.mState == BitmapManager.State.ALLOW)
        {
          str = "Allow";
          continue;
        }
        str = "?";
      }
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.support.camera.BitmapManager
 * JD-Core Version:    0.6.0
 */
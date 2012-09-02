package com.instagram.camera;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

public class SoundPlayer
  implements Runnable
{
  private static final String TAG = "SoundPlayer";
  private AssetFileDescriptor mAfd;
  private int mAudioStreamType;
  private boolean mExit;
  private int mPlayCount = 0;
  private MediaPlayer mPlayer;
  private Thread mThread;

  public SoundPlayer(AssetFileDescriptor paramAssetFileDescriptor)
  {
    this.mAfd = paramAssetFileDescriptor;
    this.mAudioStreamType = 3;
  }

  public SoundPlayer(AssetFileDescriptor paramAssetFileDescriptor, boolean paramBoolean)
  {
    this.mAfd = paramAssetFileDescriptor;
    if (paramBoolean);
    for (this.mAudioStreamType = 7; ; this.mAudioStreamType = 3)
      return;
  }

  public void play()
  {
    if (this.mThread == null)
    {
      this.mThread = new Thread(this);
      this.mThread.start();
    }
    monitorenter;
    try
    {
      this.mPlayCount = (1 + this.mPlayCount);
      notifyAll();
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

  // ERROR //
  public void release()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 36	com/instagram/camera/SoundPlayer:mThread	Ljava/lang/Thread;
    //   4: ifnull +28 -> 32
    //   7: aload_0
    //   8: monitorenter
    //   9: aload_0
    //   10: iconst_1
    //   11: putfield 54	com/instagram/camera/SoundPlayer:mExit	Z
    //   14: aload_0
    //   15: invokevirtual 47	java/lang/Object:notifyAll	()V
    //   18: aload_0
    //   19: monitorexit
    //   20: aload_0
    //   21: getfield 36	com/instagram/camera/SoundPlayer:mThread	Ljava/lang/Thread;
    //   24: invokevirtual 57	java/lang/Thread:join	()V
    //   27: aload_0
    //   28: aconst_null
    //   29: putfield 36	com/instagram/camera/SoundPlayer:mThread	Ljava/lang/Thread;
    //   32: aload_0
    //   33: getfield 30	com/instagram/camera/SoundPlayer:mAfd	Landroid/content/res/AssetFileDescriptor;
    //   36: ifnull +15 -> 51
    //   39: aload_0
    //   40: getfield 30	com/instagram/camera/SoundPlayer:mAfd	Landroid/content/res/AssetFileDescriptor;
    //   43: invokevirtual 62	android/content/res/AssetFileDescriptor:close	()V
    //   46: aload_0
    //   47: aconst_null
    //   48: putfield 30	com/instagram/camera/SoundPlayer:mAfd	Landroid/content/res/AssetFileDescriptor;
    //   51: aload_0
    //   52: getfield 64	com/instagram/camera/SoundPlayer:mPlayer	Landroid/media/MediaPlayer;
    //   55: ifnull +15 -> 70
    //   58: aload_0
    //   59: getfield 64	com/instagram/camera/SoundPlayer:mPlayer	Landroid/media/MediaPlayer;
    //   62: invokevirtual 68	android/media/MediaPlayer:release	()V
    //   65: aload_0
    //   66: aconst_null
    //   67: putfield 64	com/instagram/camera/SoundPlayer:mPlayer	Landroid/media/MediaPlayer;
    //   70: return
    //   71: astore_2
    //   72: aload_0
    //   73: monitorexit
    //   74: aload_2
    //   75: athrow
    //   76: astore_1
    //   77: goto -31 -> 46
    //   80: astore_3
    //   81: goto -54 -> 27
    //
    // Exception table:
    //   from	to	target	type
    //   9	20	71	finally
    //   72	74	71	finally
    //   39	46	76	java/io/IOException
    //   20	27	80	java/lang/InterruptedException
  }

  // ERROR //
  public void run()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 64	com/instagram/camera/SoundPlayer:mPlayer	Landroid/media/MediaPlayer;
    //   4: ifnonnull +70 -> 74
    //   7: new 66	android/media/MediaPlayer
    //   10: dup
    //   11: invokespecial 72	android/media/MediaPlayer:<init>	()V
    //   14: astore_3
    //   15: aload_3
    //   16: aload_0
    //   17: getfield 32	com/instagram/camera/SoundPlayer:mAudioStreamType	I
    //   20: invokevirtual 76	android/media/MediaPlayer:setAudioStreamType	(I)V
    //   23: aload_3
    //   24: aload_0
    //   25: getfield 30	com/instagram/camera/SoundPlayer:mAfd	Landroid/content/res/AssetFileDescriptor;
    //   28: invokevirtual 80	android/content/res/AssetFileDescriptor:getFileDescriptor	()Ljava/io/FileDescriptor;
    //   31: aload_0
    //   32: getfield 30	com/instagram/camera/SoundPlayer:mAfd	Landroid/content/res/AssetFileDescriptor;
    //   35: invokevirtual 84	android/content/res/AssetFileDescriptor:getStartOffset	()J
    //   38: aload_0
    //   39: getfield 30	com/instagram/camera/SoundPlayer:mAfd	Landroid/content/res/AssetFileDescriptor;
    //   42: invokevirtual 87	android/content/res/AssetFileDescriptor:getLength	()J
    //   45: invokevirtual 91	android/media/MediaPlayer:setDataSource	(Ljava/io/FileDescriptor;JJ)V
    //   48: aload_3
    //   49: iconst_0
    //   50: invokevirtual 95	android/media/MediaPlayer:setLooping	(Z)V
    //   53: aload_3
    //   54: invokevirtual 98	android/media/MediaPlayer:prepare	()V
    //   57: aload_0
    //   58: aload_3
    //   59: putfield 64	com/instagram/camera/SoundPlayer:mPlayer	Landroid/media/MediaPlayer;
    //   62: aload_0
    //   63: getfield 30	com/instagram/camera/SoundPlayer:mAfd	Landroid/content/res/AssetFileDescriptor;
    //   66: invokevirtual 62	android/content/res/AssetFileDescriptor:close	()V
    //   69: aload_0
    //   70: aconst_null
    //   71: putfield 30	com/instagram/camera/SoundPlayer:mAfd	Landroid/content/res/AssetFileDescriptor;
    //   74: aload_0
    //   75: monitorenter
    //   76: aload_0
    //   77: getfield 54	com/instagram/camera/SoundPlayer:mExit	Z
    //   80: ifeq +6 -> 86
    //   83: aload_0
    //   84: monitorexit
    //   85: return
    //   86: aload_0
    //   87: getfield 28	com/instagram/camera/SoundPlayer:mPlayCount	I
    //   90: ifgt +30 -> 120
    //   93: aload_0
    //   94: invokevirtual 101	java/lang/Object:wait	()V
    //   97: goto -21 -> 76
    //   100: astore 4
    //   102: aload_0
    //   103: monitorexit
    //   104: aload 4
    //   106: athrow
    //   107: astore_1
    //   108: ldc 10
    //   110: ldc 103
    //   112: aload_1
    //   113: invokestatic 109	com/instagram/android/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   116: pop
    //   117: goto -117 -> 0
    //   120: aload_0
    //   121: bipush 255
    //   123: aload_0
    //   124: getfield 28	com/instagram/camera/SoundPlayer:mPlayCount	I
    //   127: iadd
    //   128: putfield 28	com/instagram/camera/SoundPlayer:mPlayCount	I
    //   131: aload_0
    //   132: monitorexit
    //   133: aload_0
    //   134: getfield 64	com/instagram/camera/SoundPlayer:mPlayer	Landroid/media/MediaPlayer;
    //   137: invokevirtual 110	android/media/MediaPlayer:start	()V
    //   140: goto -140 -> 0
    //
    // Exception table:
    //   from	to	target	type
    //   76	104	100	finally
    //   120	133	100	finally
    //   0	76	107	java/lang/Exception
    //   104	107	107	java/lang/Exception
    //   133	140	107	java/lang/Exception
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.camera.SoundPlayer
 * JD-Core Version:    0.6.0
 */
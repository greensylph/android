package com.instagram.android.media;

import android.content.Context;
import android.graphics.Bitmap;
import com.instagram.util.ViewUtil;
import java.io.File;

public class PendingMediaFactory
{
  private static final String TAG = "PendingMediaFactory";
  private static final int THUMBNAIL_SIZE = 55;

  private static File getPendingMediaTempFile(Context paramContext)
  {
    return new File(paramContext.getCacheDir(), "pending_media_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
  }

  public static PendingMedia make(Context paramContext, Bitmap paramBitmap)
  {
    File localFile = getPendingMediaTempFile(paramContext);
    saveImageToUpload(localFile, paramBitmap);
    paramBitmap.recycle();
    int i = (int)ViewUtil.dpToPx(paramContext, 55);
    return PendingMedia.create(localFile.getPath(), i);
  }

  // ERROR //
  private static void saveImageToUpload(File paramFile, Bitmap paramBitmap)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: new 89	java/io/FileOutputStream
    //   5: dup
    //   6: aload_0
    //   7: invokespecial 92	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   10: astore_3
    //   11: aload_1
    //   12: getstatic 98	android/graphics/Bitmap$CompressFormat:JPEG	Landroid/graphics/Bitmap$CompressFormat;
    //   15: bipush 95
    //   17: aload_3
    //   18: invokevirtual 102	android/graphics/Bitmap:compress	(Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
    //   21: pop
    //   22: aload_3
    //   23: invokevirtual 105	java/io/FileOutputStream:flush	()V
    //   26: aload_3
    //   27: invokevirtual 108	java/io/FileOutputStream:close	()V
    //   30: aload_3
    //   31: ifnull +7 -> 38
    //   34: aload_3
    //   35: invokevirtual 108	java/io/FileOutputStream:close	()V
    //   38: return
    //   39: astore 13
    //   41: goto -3 -> 38
    //   44: astore 4
    //   46: ldc 8
    //   48: ldc 110
    //   50: aload 4
    //   52: invokestatic 116	com/instagram/android/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   55: pop
    //   56: aload_2
    //   57: ifnull -19 -> 38
    //   60: aload_2
    //   61: invokevirtual 108	java/io/FileOutputStream:close	()V
    //   64: goto -26 -> 38
    //   67: astore 8
    //   69: goto -31 -> 38
    //   72: astore 9
    //   74: ldc 8
    //   76: ldc 118
    //   78: aload 9
    //   80: invokestatic 116	com/instagram/android/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   83: pop
    //   84: aload_2
    //   85: ifnull -47 -> 38
    //   88: aload_2
    //   89: invokevirtual 108	java/io/FileOutputStream:close	()V
    //   92: goto -54 -> 38
    //   95: astore 11
    //   97: goto -59 -> 38
    //   100: astore 5
    //   102: aload_2
    //   103: ifnull +7 -> 110
    //   106: aload_2
    //   107: invokevirtual 108	java/io/FileOutputStream:close	()V
    //   110: aload 5
    //   112: athrow
    //   113: astore 6
    //   115: goto -5 -> 110
    //   118: astore 5
    //   120: aload_3
    //   121: astore_2
    //   122: goto -20 -> 102
    //   125: astore 9
    //   127: aload_3
    //   128: astore_2
    //   129: goto -55 -> 74
    //   132: astore 4
    //   134: aload_3
    //   135: astore_2
    //   136: goto -90 -> 46
    //
    // Exception table:
    //   from	to	target	type
    //   34	38	39	java/io/IOException
    //   2	11	44	java/io/FileNotFoundException
    //   60	64	67	java/io/IOException
    //   2	11	72	java/io/IOException
    //   88	92	95	java/io/IOException
    //   2	11	100	finally
    //   46	56	100	finally
    //   74	84	100	finally
    //   106	110	113	java/io/IOException
    //   11	30	118	finally
    //   11	30	125	java/io/IOException
    //   11	30	132	java/io/FileNotFoundException
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.media.PendingMediaFactory
 * JD-Core Version:    0.6.0
 */
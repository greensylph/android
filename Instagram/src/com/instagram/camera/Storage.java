package com.instagram.camera;

import android.os.Environment;
import android.os.StatFs;
import com.instagram.android.Log;
import java.io.File;

public class Storage
{
  public static final String BUCKET_ID;
  private static final int BUFSIZE = 4096;
  public static final String DCIM = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString();
  public static final String DIRECTORY = DCIM + "/Camera";
  public static final long LOW_STORAGE_THRESHOLD = 50000000L;
  public static final long PICTURE_SIZE = 1500000L;
  public static final long PREPARING = -2L;
  private static final String TAG = "CameraStorage";
  public static final long UNAVAILABLE = -1L;
  public static final long UNKNOWN_SIZE = -3L;

  static
  {
    BUCKET_ID = String.valueOf(DIRECTORY.toLowerCase().hashCode());
  }

  // ERROR //
  public static android.net.Uri addImage(android.content.ContentResolver paramContentResolver, String paramString, long paramLong, android.location.Location paramLocation, int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokestatic 87	com/instagram/camera/Storage:getImagePath	(Ljava/lang/String;)Ljava/lang/String;
    //   4: astore 9
    //   6: aconst_null
    //   7: astore 10
    //   9: new 89	java/io/FileOutputStream
    //   12: dup
    //   13: aload 9
    //   15: invokespecial 92	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
    //   18: astore 11
    //   20: aload 11
    //   22: aload 6
    //   24: invokevirtual 96	java/io/FileOutputStream:write	([B)V
    //   27: aload 11
    //   29: invokevirtual 99	java/io/FileOutputStream:close	()V
    //   32: new 101	android/content/ContentValues
    //   35: dup
    //   36: bipush 9
    //   38: invokespecial 104	android/content/ContentValues:<init>	(I)V
    //   41: astore 19
    //   43: aload 19
    //   45: ldc 106
    //   47: aload_1
    //   48: invokevirtual 110	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   51: aload 19
    //   53: ldc 112
    //   55: new 51	java/lang/StringBuilder
    //   58: dup
    //   59: invokespecial 54	java/lang/StringBuilder:<init>	()V
    //   62: aload_1
    //   63: invokevirtual 58	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   66: ldc 114
    //   68: invokevirtual 58	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   71: invokevirtual 61	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   74: invokevirtual 110	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   77: aload 19
    //   79: ldc 116
    //   81: lload_2
    //   82: invokestatic 121	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   85: invokevirtual 124	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   88: aload 19
    //   90: ldc 126
    //   92: ldc 128
    //   94: invokevirtual 110	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   97: aload 19
    //   99: ldc 130
    //   101: iload 5
    //   103: invokestatic 135	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   106: invokevirtual 138	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   109: aload 19
    //   111: ldc 140
    //   113: aload 9
    //   115: invokevirtual 110	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   118: aload 19
    //   120: ldc 142
    //   122: aload 6
    //   124: arraylength
    //   125: invokestatic 135	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   128: invokevirtual 138	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Integer;)V
    //   131: aload 4
    //   133: ifnull +33 -> 166
    //   136: aload 19
    //   138: ldc 144
    //   140: aload 4
    //   142: invokevirtual 150	android/location/Location:getLatitude	()D
    //   145: invokestatic 155	java/lang/Double:valueOf	(D)Ljava/lang/Double;
    //   148: invokevirtual 158	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Double;)V
    //   151: aload 19
    //   153: ldc 160
    //   155: aload 4
    //   157: invokevirtual 163	android/location/Location:getLongitude	()D
    //   160: invokestatic 155	java/lang/Double:valueOf	(D)Ljava/lang/Double;
    //   163: invokevirtual 158	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Double;)V
    //   166: aload_0
    //   167: getstatic 169	android/provider/MediaStore$Images$Media:EXTERNAL_CONTENT_URI	Landroid/net/Uri;
    //   170: aload 19
    //   172: invokevirtual 175	android/content/ContentResolver:insert	(Landroid/net/Uri;Landroid/content/ContentValues;)Landroid/net/Uri;
    //   175: astore 16
    //   177: aload 16
    //   179: ifnonnull +55 -> 234
    //   182: ldc 24
    //   184: ldc 177
    //   186: invokestatic 183	com/instagram/android/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   189: pop
    //   190: aconst_null
    //   191: astore 16
    //   193: aload 16
    //   195: areturn
    //   196: astore 12
    //   198: ldc 24
    //   200: ldc 185
    //   202: aload 12
    //   204: invokestatic 188	com/instagram/android/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   207: pop
    //   208: aconst_null
    //   209: astore 16
    //   211: aload 10
    //   213: invokevirtual 99	java/io/FileOutputStream:close	()V
    //   216: goto -23 -> 193
    //   219: astore 17
    //   221: goto -28 -> 193
    //   224: astore 13
    //   226: aload 10
    //   228: invokevirtual 99	java/io/FileOutputStream:close	()V
    //   231: aload 13
    //   233: athrow
    //   234: goto -41 -> 193
    //   237: astore 18
    //   239: goto -207 -> 32
    //   242: astore 14
    //   244: goto -13 -> 231
    //   247: astore 13
    //   249: aload 11
    //   251: astore 10
    //   253: goto -27 -> 226
    //   256: astore 12
    //   258: aload 11
    //   260: astore 10
    //   262: goto -64 -> 198
    //
    // Exception table:
    //   from	to	target	type
    //   9	20	196	java/lang/Exception
    //   211	216	219	java/lang/Exception
    //   9	20	224	finally
    //   198	208	224	finally
    //   27	32	237	java/lang/Exception
    //   226	231	242	java/lang/Exception
    //   20	27	247	finally
    //   20	27	256	java/lang/Exception
  }

  public static void ensureOSXCompatible()
  {
    File localFile = new File(DCIM, "100ANDRO");
    if ((!localFile.exists()) && (!localFile.mkdirs()))
      Log.e("CameraStorage", "Failed to create " + localFile.getPath());
  }

  public static long getAvailableSpace()
  {
    long l1 = -1L;
    String str = Environment.getExternalStorageState();
    Log.d("CameraStorage", "External storage state=" + str);
    if ("checking".equals(str))
      l1 = -2L;
    while (true)
    {
      return l1;
      if (!"mounted".equals(str))
        continue;
      File localFile = new File(DIRECTORY);
      localFile.mkdirs();
      if ((!localFile.isDirectory()) || (!localFile.canWrite()))
        continue;
      try
      {
        StatFs localStatFs = new StatFs(DIRECTORY);
        long l2 = localStatFs.getAvailableBlocks();
        int i = localStatFs.getBlockSize();
        l1 = l2 * i;
      }
      catch (Exception localException)
      {
        Log.i("CameraStorage", "Fail to access external storage", localException);
        l1 = -3L;
      }
    }
  }

  public static String getImagePath(String paramString)
  {
    return DIRECTORY + '/' + paramString + ".jpg";
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.camera.Storage
 * JD-Core Version:    0.6.0
 */
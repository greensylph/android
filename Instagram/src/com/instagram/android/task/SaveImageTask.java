package com.instagram.android.task;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import com.instagram.android.Log;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SaveImageTask extends AsyncTask<Bitmap, Void, Void>
{
  private static final String TAG = "SaveImageTask";
  private final ContentResolver mResolver;

  public SaveImageTask(ContentResolver paramContentResolver)
  {
    this.mResolver = paramContentResolver;
  }

  private File getInstagramMediaFile()
  {
    File localFile1 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Instagram");
    if ((!localFile1.exists()) && (!localFile1.mkdirs()))
      Log.d("Instagram", "failed to create directory");
    String str;
    for (File localFile2 = null; ; localFile2 = new File(localFile1.getPath() + File.separator + "IMG_" + str + ".jpg"))
    {
      return localFile2;
      str = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    }
  }

  // ERROR //
  protected Void doInBackground(Bitmap[] paramArrayOfBitmap)
  {
    // Byte code:
    //   0: ldc 9
    //   2: ldc 102
    //   4: invokestatic 51	com/instagram/android/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   7: pop
    //   8: aload_1
    //   9: iconst_0
    //   10: aaload
    //   11: astore_3
    //   12: aconst_null
    //   13: astore 4
    //   15: aload_0
    //   16: invokespecial 104	com/instagram/android/task/SaveImageTask:getInstagramMediaFile	()Ljava/io/File;
    //   19: astore 5
    //   21: aload 5
    //   23: ifnonnull +13 -> 36
    //   26: ldc 33
    //   28: ldc 106
    //   30: invokestatic 51	com/instagram/android/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   33: pop
    //   34: aconst_null
    //   35: areturn
    //   36: new 108	java/io/FileOutputStream
    //   39: dup
    //   40: aload 5
    //   42: invokespecial 111	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   45: astore 6
    //   47: aload_3
    //   48: getstatic 117	android/graphics/Bitmap$CompressFormat:JPEG	Landroid/graphics/Bitmap$CompressFormat;
    //   51: bipush 95
    //   53: aload 6
    //   55: invokevirtual 123	android/graphics/Bitmap:compress	(Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
    //   58: pop
    //   59: aload_3
    //   60: invokevirtual 126	android/graphics/Bitmap:recycle	()V
    //   63: aload 6
    //   65: invokevirtual 129	java/io/FileOutputStream:flush	()V
    //   68: aload 6
    //   70: invokevirtual 132	java/io/FileOutputStream:close	()V
    //   73: aload 6
    //   75: ifnull +8 -> 83
    //   78: aload 6
    //   80: invokevirtual 132	java/io/FileOutputStream:close	()V
    //   83: aload 5
    //   85: invokevirtual 135	java/io/File:getName	()Ljava/lang/String;
    //   88: astore 16
    //   90: aload 16
    //   92: iconst_0
    //   93: bipush 253
    //   95: aload 16
    //   97: invokevirtual 141	java/lang/String:length	()I
    //   100: iadd
    //   101: invokestatic 147	android/text/TextUtils:substring	(Ljava/lang/CharSequence;II)Ljava/lang/String;
    //   104: astore 17
    //   106: new 149	android/content/ContentValues
    //   109: dup
    //   110: invokespecial 150	android/content/ContentValues:<init>	()V
    //   113: astore 18
    //   115: aload 18
    //   117: ldc 152
    //   119: aload 17
    //   121: invokevirtual 156	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   124: aload 18
    //   126: ldc 158
    //   128: aload 16
    //   130: invokevirtual 156	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   133: aload 18
    //   135: ldc 160
    //   137: invokestatic 166	java/lang/System:currentTimeMillis	()J
    //   140: invokestatic 172	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   143: invokevirtual 175	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   146: aload 18
    //   148: ldc 177
    //   150: ldc 179
    //   152: invokevirtual 156	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   155: aload 18
    //   157: ldc 181
    //   159: aload 5
    //   161: invokevirtual 72	java/io/File:getPath	()Ljava/lang/String;
    //   164: invokevirtual 156	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/String;)V
    //   167: aload_0
    //   168: getfield 18	com/instagram/android/task/SaveImageTask:mResolver	Landroid/content/ContentResolver;
    //   171: getstatic 187	android/provider/MediaStore$Images$Media:EXTERNAL_CONTENT_URI	Landroid/net/Uri;
    //   174: aload 18
    //   176: invokevirtual 193	android/content/ContentResolver:insert	(Landroid/net/Uri;Landroid/content/ContentValues;)Landroid/net/Uri;
    //   179: astore 21
    //   181: aload 18
    //   183: invokevirtual 196	android/content/ContentValues:clear	()V
    //   186: aload 18
    //   188: ldc 198
    //   190: new 22	java/io/File
    //   193: dup
    //   194: aload 5
    //   196: invokevirtual 72	java/io/File:getPath	()Ljava/lang/String;
    //   199: invokespecial 87	java/io/File:<init>	(Ljava/lang/String;)V
    //   202: invokevirtual 200	java/io/File:length	()J
    //   205: invokestatic 172	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   208: invokevirtual 175	android/content/ContentValues:put	(Ljava/lang/String;Ljava/lang/Long;)V
    //   211: aload_0
    //   212: getfield 18	com/instagram/android/task/SaveImageTask:mResolver	Landroid/content/ContentResolver;
    //   215: aload 21
    //   217: aload 18
    //   219: aconst_null
    //   220: aconst_null
    //   221: invokevirtual 204	android/content/ContentResolver:update	(Landroid/net/Uri;Landroid/content/ContentValues;Ljava/lang/String;[Ljava/lang/String;)I
    //   224: pop
    //   225: goto -191 -> 34
    //   228: astore 7
    //   230: ldc 9
    //   232: ldc 206
    //   234: aload 7
    //   236: invokestatic 210	com/instagram/android/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   239: pop
    //   240: aload 4
    //   242: ifnull -208 -> 34
    //   245: aload 4
    //   247: invokevirtual 132	java/io/FileOutputStream:close	()V
    //   250: goto -216 -> 34
    //   253: astore 11
    //   255: goto -221 -> 34
    //   258: astore 12
    //   260: ldc 9
    //   262: ldc 212
    //   264: aload 12
    //   266: invokestatic 210	com/instagram/android/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   269: pop
    //   270: aload 4
    //   272: ifnull -238 -> 34
    //   275: aload 4
    //   277: invokevirtual 132	java/io/FileOutputStream:close	()V
    //   280: goto -246 -> 34
    //   283: astore 14
    //   285: goto -251 -> 34
    //   288: astore 8
    //   290: aload 4
    //   292: ifnull +8 -> 300
    //   295: aload 4
    //   297: invokevirtual 132	java/io/FileOutputStream:close	()V
    //   300: aload 8
    //   302: athrow
    //   303: astore 19
    //   305: ldc 9
    //   307: ldc 214
    //   309: invokestatic 216	com/instagram/android/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   312: pop
    //   313: goto -88 -> 225
    //   316: astore 23
    //   318: goto -235 -> 83
    //   321: astore 9
    //   323: goto -23 -> 300
    //   326: astore 8
    //   328: aload 6
    //   330: astore 4
    //   332: goto -42 -> 290
    //   335: astore 12
    //   337: aload 6
    //   339: astore 4
    //   341: goto -81 -> 260
    //   344: astore 7
    //   346: aload 6
    //   348: astore 4
    //   350: goto -120 -> 230
    //
    // Exception table:
    //   from	to	target	type
    //   36	47	228	java/io/FileNotFoundException
    //   245	250	253	java/io/IOException
    //   36	47	258	java/io/IOException
    //   275	280	283	java/io/IOException
    //   36	47	288	finally
    //   230	240	288	finally
    //   260	270	288	finally
    //   167	225	303	java/lang/Exception
    //   78	83	316	java/io/IOException
    //   295	300	321	java/io/IOException
    //   47	73	326	finally
    //   47	73	335	java/io/IOException
    //   47	73	344	java/io/FileNotFoundException
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.task.SaveImageTask
 * JD-Core Version:    0.6.0
 */
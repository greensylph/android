package com.instagram.android.support.camera;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import com.instagram.android.support.camera.gallery.IImage;
import java.io.Closeable;
import java.io.FileDescriptor;
import java.io.IOException;

public class Util
{
  public static final int DIRECTION_DOWN = 3;
  public static final int DIRECTION_LEFT = 0;
  public static final int DIRECTION_RIGHT = 1;
  public static final int DIRECTION_UP = 2;
  public static final boolean NO_RECYCLE_INPUT = false;
  public static final boolean RECYCLE_INPUT = true;
  private static final String TAG = "Util";
  private static View.OnClickListener sNullOnClickListener;

  public static void Assert(boolean paramBoolean)
  {
    if (!paramBoolean)
      throw new AssertionError();
  }

  public static void closeSilently(ParcelFileDescriptor paramParcelFileDescriptor)
  {
    if (paramParcelFileDescriptor == null);
    while (true)
    {
      return;
      try
      {
        paramParcelFileDescriptor.close();
      }
      catch (Throwable localThrowable)
      {
      }
    }
  }

  public static void closeSilently(Closeable paramCloseable)
  {
    if (paramCloseable == null);
    while (true)
    {
      return;
      try
      {
        paramCloseable.close();
      }
      catch (Throwable localThrowable)
      {
      }
    }
  }

  private static int computeInitialSampleSize(BitmapFactory.Options paramOptions, int paramInt1, int paramInt2)
  {
    double d1 = paramOptions.outWidth;
    double d2 = paramOptions.outHeight;
    int i;
    int j;
    if (paramInt2 == -1)
    {
      i = 1;
      if (paramInt1 != -1)
        break label62;
      j = 128;
      label33: if (j >= i)
        break label86;
    }
    while (true)
    {
      return i;
      i = (int)Math.ceil(Math.sqrt(d1 * d2 / paramInt2));
      break;
      label62: j = (int)Math.min(Math.floor(d1 / paramInt1), Math.floor(d2 / paramInt1));
      break label33;
      label86: if ((paramInt2 == -1) && (paramInt1 == -1))
      {
        i = 1;
        continue;
      }
      if (paramInt1 == -1)
        continue;
      i = j;
    }
  }

  public static int computeSampleSize(BitmapFactory.Options paramOptions, int paramInt1, int paramInt2)
  {
    int i = computeInitialSampleSize(paramOptions, paramInt1, paramInt2);
    if (i <= 8)
    {
      j = 1;
      while (j < i)
        j <<= 1;
    }
    int j = 8 * ((i + 7) / 8);
    return j;
  }

  public static BitmapFactory.Options createNativeAllocOptions()
  {
    BitmapFactory.Options localOptions = new BitmapFactory.Options();
    localOptions.inPurgeable = true;
    localOptions.inInputShareable = true;
    return localOptions;
  }

  public static Intent createSetAsIntent(IImage paramIImage)
  {
    Uri localUri = paramIImage.fullSizeImageUri();
    Intent localIntent = new Intent("android.intent.action.ATTACH_DATA");
    localIntent.setDataAndType(localUri, paramIImage.getMimeType());
    localIntent.putExtra("mimeType", paramIImage.getMimeType());
    return localIntent;
  }

  public static boolean equals(String paramString1, String paramString2)
  {
    if ((paramString1 == paramString2) || (paramString1.equals(paramString2)));
    for (int i = 1; ; i = 0)
      return i;
  }

  public static View.OnClickListener getNullOnClickListener()
  {
    monitorenter;
    try
    {
      if (sNullOnClickListener == null)
        sNullOnClickListener = new View.OnClickListener()
        {
          public void onClick(View paramView)
          {
          }
        };
      View.OnClickListener localOnClickListener = sNullOnClickListener;
      monitorexit;
      return localOnClickListener;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public static <T> int indexOf(T[] paramArrayOfT, T paramT)
  {
    int i = 0;
    if (i < paramArrayOfT.length)
      if (!paramArrayOfT[i].equals(paramT));
    while (true)
    {
      return i;
      i++;
      break;
      i = -1;
    }
  }

  public static Bitmap makeBitmap(int paramInt1, int paramInt2, Uri paramUri, ContentResolver paramContentResolver, ParcelFileDescriptor paramParcelFileDescriptor, BitmapFactory.Options paramOptions)
  {
    Object localObject1 = null;
    if (paramParcelFileDescriptor == null);
    try
    {
      ParcelFileDescriptor localParcelFileDescriptor = makeInputStream(paramUri, paramContentResolver);
      paramParcelFileDescriptor = localParcelFileDescriptor;
      if (paramParcelFileDescriptor == null);
      while (true)
      {
        return localObject1;
        if (paramOptions == null)
          paramOptions = new BitmapFactory.Options();
        FileDescriptor localFileDescriptor = paramParcelFileDescriptor.getFileDescriptor();
        paramOptions.inJustDecodeBounds = true;
        BitmapManager.instance().decodeFileDescriptor(localFileDescriptor, paramOptions);
        if ((!paramOptions.mCancel) && (paramOptions.outWidth != -1))
        {
          int i = paramOptions.outHeight;
          if (i != -1);
        }
        else
        {
          closeSilently(paramParcelFileDescriptor);
          continue;
        }
        paramOptions.inSampleSize = computeSampleSize(paramOptions, paramInt1, paramInt2);
        paramOptions.inJustDecodeBounds = false;
        paramOptions.inDither = false;
        paramOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap localBitmap = BitmapManager.instance().decodeFileDescriptor(localFileDescriptor, paramOptions);
        localObject1 = localBitmap;
        closeSilently(paramParcelFileDescriptor);
      }
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
      while (true)
      {
        Log.e("Util", "Got oom exception ", localOutOfMemoryError);
        closeSilently(paramParcelFileDescriptor);
      }
    }
    finally
    {
      closeSilently(paramParcelFileDescriptor);
    }
    throw localObject2;
  }

  // ERROR //
  public static Bitmap makeBitmap(int paramInt1, int paramInt2, Uri paramUri, ContentResolver paramContentResolver, boolean paramBoolean)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 5
    //   3: aload_3
    //   4: aload_2
    //   5: ldc 191
    //   7: invokevirtual 197	android/content/ContentResolver:openFileDescriptor	(Landroid/net/Uri;Ljava/lang/String;)Landroid/os/ParcelFileDescriptor;
    //   10: astore 5
    //   12: aconst_null
    //   13: astore 9
    //   15: iload 4
    //   17: ifeq +8 -> 25
    //   20: invokestatic 199	com/instagram/android/support/camera/Util:createNativeAllocOptions	()Landroid/graphics/BitmapFactory$Options;
    //   23: astore 9
    //   25: iload_0
    //   26: iload_1
    //   27: aload_2
    //   28: aload_3
    //   29: aload 5
    //   31: aload 9
    //   33: invokestatic 201	com/instagram/android/support/camera/Util:makeBitmap	(IILandroid/net/Uri;Landroid/content/ContentResolver;Landroid/os/ParcelFileDescriptor;Landroid/graphics/BitmapFactory$Options;)Landroid/graphics/Bitmap;
    //   36: astore 10
    //   38: aload 10
    //   40: astore 8
    //   42: aload 5
    //   44: invokestatic 141	com/instagram/android/support/camera/Util:closeSilently	(Landroid/os/ParcelFileDescriptor;)V
    //   47: aload 8
    //   49: areturn
    //   50: astore 7
    //   52: aconst_null
    //   53: astore 8
    //   55: aload 5
    //   57: invokestatic 141	com/instagram/android/support/camera/Util:closeSilently	(Landroid/os/ParcelFileDescriptor;)V
    //   60: goto -13 -> 47
    //   63: astore 6
    //   65: aload 5
    //   67: invokestatic 141	com/instagram/android/support/camera/Util:closeSilently	(Landroid/os/ParcelFileDescriptor;)V
    //   70: aload 6
    //   72: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   3	38	50	java/io/IOException
    //   3	38	63	finally
  }

  public static Bitmap makeBitmap(int paramInt1, int paramInt2, ParcelFileDescriptor paramParcelFileDescriptor, boolean paramBoolean)
  {
    BitmapFactory.Options localOptions = null;
    if (paramBoolean)
      localOptions = createNativeAllocOptions();
    return makeBitmap(paramInt1, paramInt2, null, null, paramParcelFileDescriptor, localOptions);
  }

  private static ParcelFileDescriptor makeInputStream(Uri paramUri, ContentResolver paramContentResolver)
  {
    try
    {
      ParcelFileDescriptor localParcelFileDescriptor2 = paramContentResolver.openFileDescriptor(paramUri, "r");
      localParcelFileDescriptor1 = localParcelFileDescriptor2;
      return localParcelFileDescriptor1;
    }
    catch (IOException localIOException)
    {
      while (true)
        ParcelFileDescriptor localParcelFileDescriptor1 = null;
    }
  }

  public static Bitmap rotate(Bitmap paramBitmap, int paramInt)
  {
    Matrix localMatrix;
    if ((paramInt != 0) && (paramBitmap != null))
    {
      localMatrix = new Matrix();
      localMatrix.setRotate(paramInt, paramBitmap.getWidth() / 2.0F, paramBitmap.getHeight() / 2.0F);
    }
    try
    {
      int i = paramBitmap.getWidth();
      int j = paramBitmap.getHeight();
      Bitmap localBitmap = Bitmap.createBitmap(paramBitmap, 0, 0, i, j, localMatrix, true);
      if (paramBitmap != localBitmap)
      {
        paramBitmap.recycle();
        paramBitmap = localBitmap;
      }
      label75: return paramBitmap;
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
      break label75;
    }
  }

  public static void startBackgroundJob(MonitoredActivity paramMonitoredActivity, String paramString1, String paramString2, Runnable paramRunnable, Handler paramHandler)
  {
    new Thread(new BackgroundJob(paramMonitoredActivity, paramRunnable, ProgressDialog.show(paramMonitoredActivity, paramString1, paramString2, true, false), paramHandler)).start();
  }

  public static Bitmap transform(Matrix paramMatrix, Bitmap paramBitmap, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
  {
    int i = paramBitmap.getWidth() - paramInt1;
    int j = paramBitmap.getHeight() - paramInt2;
    Bitmap localBitmap2;
    if ((!paramBoolean1) && ((i < 0) || (j < 0)))
    {
      localBitmap2 = Bitmap.createBitmap(paramInt1, paramInt2, Bitmap.Config.ARGB_8888);
      Canvas localCanvas = new Canvas(localBitmap2);
      int n = Math.max(0, i / 2);
      int i1 = Math.max(0, j / 2);
      Rect localRect1 = new Rect(n, i1, n + Math.min(paramInt1, paramBitmap.getWidth()), i1 + Math.min(paramInt2, paramBitmap.getHeight()));
      int i2 = (paramInt1 - localRect1.width()) / 2;
      int i3 = (paramInt2 - localRect1.height()) / 2;
      Rect localRect2 = new Rect(i2, i3, paramInt1 - i2, paramInt2 - i3);
      localCanvas.drawBitmap(paramBitmap, localRect1, localRect2, null);
      if (paramBoolean2)
        paramBitmap.recycle();
      return localBitmap2;
    }
    float f1 = paramBitmap.getWidth();
    float f2 = paramBitmap.getHeight();
    if (f1 / f2 > paramInt1 / paramInt2)
    {
      float f4 = paramInt2 / f2;
      if ((f4 < 0.9F) || (f4 > 1.0F))
      {
        paramMatrix.setScale(f4, f4);
        label232: if (paramMatrix == null)
          break label382;
      }
    }
    label382: for (Bitmap localBitmap1 = Bitmap.createBitmap(paramBitmap, 0, 0, paramBitmap.getWidth(), paramBitmap.getHeight(), paramMatrix, true); ; localBitmap1 = paramBitmap)
    {
      if ((paramBoolean2) && (localBitmap1 != paramBitmap))
        paramBitmap.recycle();
      int k = Math.max(0, localBitmap1.getWidth() - paramInt1);
      int m = Math.max(0, localBitmap1.getHeight() - paramInt2);
      localBitmap2 = Bitmap.createBitmap(localBitmap1, k / 2, m / 2, paramInt1, paramInt2);
      if ((localBitmap2 == localBitmap1) || ((!paramBoolean2) && (localBitmap1 == paramBitmap)))
        break;
      localBitmap1.recycle();
      break;
      paramMatrix = null;
      break label232;
      float f3 = paramInt1 / f1;
      if ((f3 < 0.9F) || (f3 > 1.0F))
      {
        paramMatrix.setScale(f3, f3);
        break label232;
      }
      paramMatrix = null;
      break label232;
    }
  }

  private static class BackgroundJob extends MonitoredActivity.LifeCycleAdapter
    implements Runnable
  {
    private final MonitoredActivity mActivity;
    private final Runnable mCleanupRunner = new Util.BackgroundJob.1(this);
    private final ProgressDialog mDialog;
    private final Handler mHandler;
    private final Runnable mJob;

    public BackgroundJob(MonitoredActivity paramMonitoredActivity, Runnable paramRunnable, ProgressDialog paramProgressDialog, Handler paramHandler)
    {
      this.mActivity = paramMonitoredActivity;
      this.mDialog = paramProgressDialog;
      this.mJob = paramRunnable;
      this.mActivity.addLifeCycleListener(this);
      this.mHandler = paramHandler;
    }

    public void onActivityDestroyed(MonitoredActivity paramMonitoredActivity)
    {
      this.mCleanupRunner.run();
      this.mHandler.removeCallbacks(this.mCleanupRunner);
    }

    public void onActivityStarted(MonitoredActivity paramMonitoredActivity)
    {
      this.mDialog.show();
    }

    public void onActivityStopped(MonitoredActivity paramMonitoredActivity)
    {
      this.mDialog.hide();
    }

    public void run()
    {
      try
      {
        this.mJob.run();
        return;
      }
      finally
      {
        this.mHandler.post(this.mCleanupRunner);
      }
      throw localObject;
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.support.camera.Util
 * JD-Core Version:    0.6.0
 */
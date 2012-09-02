package com.instagram.android.gl;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import com.instagram.android.Log;
import com.instagram.android.service.AppContext;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class NativeBridge
{
  public static int RENDER_SIZE_UNSPECIFIED = 0;
  private static final String TAG = "NativeBridge";
  private static ImageProcessorDelegate sImageProcessorDelegate;
  private static int sMasterTextureHeight;
  private static int sMasterTextureWidth = 0;

  static
  {
    sMasterTextureHeight = 0;
    sImageProcessorDelegate = null;
  }

  public static native ByteBuffer applyUnsharpMask(String paramString);

  public static int createTexture(String paramString)
  {
    Object localObject = null;
    Context localContext = AppContext.getContext();
    Log.d("NativeBridge", "Trying to decode/load texture: " + paramString);
    try
    {
      if (paramString.startsWith("/"))
      {
        File localFile = new File(paramString);
        InputStream localInputStream1 = localContext.getContentResolver().openInputStream(Uri.fromFile(localFile));
        localObject = localInputStream1;
        if (!paramString.endsWith(".pkm"))
          break label142;
        i = GLHelper.makeETC1Texture(localObject);
        if (localObject == null);
      }
    }
    catch (IOException localIOException1)
    {
      try
      {
        while (true)
        {
          localObject.close();
          return i;
          InputStream localInputStream2 = localContext.getAssets().open("filters/" + paramString);
          localObject = localInputStream2;
          continue;
          localIOException1 = localIOException1;
          Log.e("NativeBridge", "Failed to open input stream", localIOException1);
        }
        label142: Bitmap localBitmap = BitmapFactory.decodeStream(localObject);
        int i = GLHelper.makeBitmapTexture(localBitmap);
        localBitmap.recycle();
      }
      catch (IOException localIOException2)
      {
        while (true)
          Log.e("NativeBridge", "Failed to close input stream", localIOException2);
      }
    }
  }

  public static boolean getBordersEnabled()
  {
    if (sImageProcessorDelegate == null)
      Log.e("NativeBridge", "Image processor delegate was null");
    for (boolean bool = false; ; bool = sImageProcessorDelegate.getBordersEnabled())
      return bool;
  }

  public static int getCurrentFilter()
  {
    if (sImageProcessorDelegate == null)
      Log.e("NativeBridge", "Image processor delegate was null");
    for (int i = 0; ; i = sImageProcessorDelegate.getCurrentFilter())
      return i;
  }

  public static native NativeFilter[] getFilters();

  public static native String getInstagramString(String paramString);

  public static boolean getIsLowEndDevice()
  {
    if ((Build.MODEL.indexOf("GT-S5360") != -1) || (Build.MODEL.indexOf("GT-S5830M") != -1) || (Build.MODEL.indexOf("GT-S5830i") != -1) || (Build.MODEL.indexOf("GT-S5830C") != -1));
    for (int i = 1; ; i = 0)
      return i;
  }

  public static boolean getLuxEnabled()
  {
    if (sImageProcessorDelegate == null)
      Log.e("NativeBridge", "Image processor delegate was null");
    for (boolean bool = false; ; bool = sImageProcessorDelegate.getLuxEnabled())
      return bool;
  }

  public static boolean getLuxSupported()
  {
    if (!getIsLowEndDevice());
    for (int i = 1; ; i = 0)
      return i;
  }

  public static boolean getMirrorMasterTexture()
  {
    if (sImageProcessorDelegate == null)
      Log.e("NativeBridge", "Image processor delegate was null");
    for (boolean bool = false; ; bool = sImageProcessorDelegate.getMirrorMasterTexture())
      return bool;
  }

  public static boolean getTiltShiftEnabled()
  {
    return sImageProcessorDelegate.getTiltShiftEnabled();
  }

  public static int getTiltShiftMode()
  {
    return sImageProcessorDelegate.getTiltShiftMode();
  }

  public static float getTiltShiftOriginX()
  {
    return sImageProcessorDelegate.getTiltShiftOriginX();
  }

  public static float getTiltShiftOriginY()
  {
    return sImageProcessorDelegate.getTiltShiftOriginY();
  }

  public static float getTiltShiftRadius()
  {
    return sImageProcessorDelegate.getTiltShiftRadius();
  }

  public static boolean getTiltShiftSupported()
  {
    if (!getIsLowEndDevice());
    for (int i = 1; ; i = 0)
      return i;
  }

  public static float getTiltShiftTheta()
  {
    return sImageProcessorDelegate.getTiltShiftTheta();
  }

  public static int loadMasterTexture()
  {
    int i = 0;
    if (sImageProcessorDelegate == null)
      Log.e("NativeBridge", "Image processor delegate was null");
    while (true)
    {
      return i;
      sImageProcessorDelegate.onStartLoadMasterTexture();
      Bitmap localBitmap = sImageProcessorDelegate.getMasterTextureBitmap();
      if (localBitmap == null)
      {
        Log.d("NativeBridge", "getMasterTextureBitmap() returned null from image processor delegate");
        continue;
      }
      sMasterTextureWidth = localBitmap.getWidth();
      sMasterTextureHeight = localBitmap.getHeight();
      i = GLHelper.makeBitmapTexture(localBitmap);
      setMasterTextureWidthHeight(localBitmap.getWidth(), localBitmap.getHeight());
      localBitmap.recycle();
      sImageProcessorDelegate.onFinishLoadMasterTexture();
    }
  }

  public static native void mirrorMasterTexture();

  public static Bitmap renderToBitmap(int paramInt1, int paramInt2)
  {
    int i = RENDER_SIZE_UNSPECIFIED;
    if (paramInt1 == i)
      paramInt1 = sMasterTextureWidth;
    int j = RENDER_SIZE_UNSPECIFIED;
    if (paramInt2 == j)
      paramInt2 = sMasterTextureHeight;
    ByteBuffer localByteBuffer = renderToByteBuffer(sMasterTextureWidth, sMasterTextureHeight);
    Bitmap.Config localConfig = Bitmap.Config.ARGB_8888;
    Bitmap localBitmap1 = Bitmap.createBitmap(sMasterTextureWidth, sMasterTextureHeight, localConfig);
    localBitmap1.copyPixelsFromBuffer(localByteBuffer);
    Object localObject = localBitmap1;
    int k = localBitmap1.getWidth();
    for (int m = localBitmap1.getHeight(); k / 2 >= paramInt1; m = ((Bitmap)localObject).getHeight())
    {
      Log.d("NativeBridge", "Halving bitmap: " + k + " -> " + k / 2);
      Bitmap localBitmap3 = Bitmap.createBitmap(k / 2, m / 2, localConfig);
      Canvas localCanvas2 = new Canvas(localBitmap3);
      Paint localPaint2 = new Paint(2);
      Matrix localMatrix2 = new Matrix();
      localMatrix2.postScale(0.5F, 0.5F);
      localCanvas2.drawBitmap((Bitmap)localObject, localMatrix2, localPaint2);
      ((Bitmap)localObject).recycle();
      localObject = localBitmap3;
      k = ((Bitmap)localObject).getWidth();
    }
    if (k != paramInt1)
    {
      Log.d("NativeBridge", "Final resize");
      Bitmap localBitmap2 = Bitmap.createBitmap(paramInt1, paramInt2, localConfig);
      Canvas localCanvas1 = new Canvas(localBitmap2);
      Paint localPaint1 = new Paint(2);
      Matrix localMatrix1 = new Matrix();
      localMatrix1.postScale(paramInt1 / k, paramInt2 / m);
      localCanvas1.drawBitmap((Bitmap)localObject, localMatrix1, localPaint1);
      ((Bitmap)localObject).recycle();
      localObject = localBitmap2;
      localBitmap2.getWidth();
      localBitmap2.getHeight();
    }
    return (Bitmap)localObject;
  }

  public static native ByteBuffer renderToByteBuffer(int paramInt1, int paramInt2);

  public static native void rotateMasterTexture();

  public static native void setBordersEnabled(boolean paramBoolean);

  public static native void setLuxEnabled(boolean paramBoolean);

  public static void setMasterTextureDelegate(ImageProcessorDelegate paramImageProcessorDelegate)
  {
    sImageProcessorDelegate = paramImageProcessorDelegate;
  }

  public static native void setMasterTextureWidthHeight(int paramInt1, int paramInt2);

  public static native void setTiltShiftEnabled(boolean paramBoolean);

  public static native void setTiltShiftMode(int paramInt);

  public static native void setTiltShiftOrigin(float paramFloat1, float paramFloat2);

  public static native void setTiltShiftRadius(float paramFloat);

  public static native void setTiltShiftTheta(float paramFloat);

  public static native void tiltShiftFadeInMaskHighlight();

  public static native void tiltShiftFadeOutMaskHighlight();

  public static native void useFilter(int paramInt);

  public static abstract interface ImageProcessorDelegate
  {
    public abstract boolean getBordersEnabled();

    public abstract int getCurrentFilter();

    public abstract boolean getLuxEnabled();

    public abstract Bitmap getMasterTextureBitmap();

    public abstract boolean getMirrorMasterTexture();

    public abstract boolean getTiltShiftEnabled();

    public abstract int getTiltShiftMode();

    public abstract float getTiltShiftOriginX();

    public abstract float getTiltShiftOriginY();

    public abstract float getTiltShiftRadius();

    public abstract float getTiltShiftTheta();

    public abstract void onFinishLoadMasterTexture();

    public abstract void onStartLoadMasterTexture();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.gl.NativeBridge
 * JD-Core Version:    0.6.0
 */
package com.instagram.util;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import com.instagram.android.Log;
import com.instagram.android.support.camera.ImageManager;
import com.instagram.android.support.camera.gallery.IImage;
import com.instagram.android.support.camera.gallery.IImageList;

public class BitmapHelper
{
  public static final String TAG = "BitmapHelper";

  private static int getSupportedMax(int paramInt)
  {
    int i = 1936;
    if (paramInt >= 2048)
      i = 2048;
    while (true)
    {
      return i;
      if (paramInt >= i)
        continue;
      if (paramInt >= 1536)
      {
        i = 1536;
        continue;
      }
      if (paramInt >= 1024)
      {
        i = 1024;
        continue;
      }
      if (paramInt >= 720)
      {
        i = 720;
        continue;
      }
      if (paramInt >= 640)
      {
        i = 640;
        continue;
      }
      i = 480;
    }
  }

  public static Bitmap largestSquareBitmap(ContentResolver paramContentResolver, Uri paramUri)
  {
    IImage localIImage = ImageManager.makeImageList(paramContentResolver, paramUri, 1).getImageForUri(paramUri);
    int i = (int)(0.37D * Runtime.getRuntime().maxMemory()) / 4;
    Log.d("BitmapHelper", "Max number of pixels: " + i);
    System.gc();
    Bitmap localBitmap1 = localIImage.fullSizeBitmap(-1, i, true, true);
    Object[] arrayOfObject1 = new Object[2];
    arrayOfObject1[0] = Integer.valueOf(localBitmap1.getWidth());
    arrayOfObject1[1] = Integer.valueOf(localBitmap1.getHeight());
    Log.d("BitmapHelper", String.format("Full size bitmap: %dx%d", arrayOfObject1));
    int j = Math.min(localBitmap1.getWidth(), localBitmap1.getHeight());
    float f = Math.min(1.0F, getSupportedMax(j) / j);
    Matrix localMatrix = new Matrix();
    localMatrix.postScale(f, f);
    Rect localRect1 = new Rect(0, 0, j, j);
    RectF localRectF = new RectF(0.0F, 0.0F, localRect1.width(), localRect1.height());
    localMatrix.mapRect(localRectF);
    Object[] arrayOfObject2 = new Object[2];
    arrayOfObject2[0] = Float.valueOf(localRectF.width());
    arrayOfObject2[1] = Float.valueOf(localRectF.height());
    Log.d("BitmapHelper", String.format("Dest rect: %fx%f", arrayOfObject2));
    int k = (int)localRectF.width();
    System.gc();
    Bitmap localBitmap2 = Bitmap.createBitmap(k, k, Bitmap.Config.ARGB_8888);
    Rect localRect2 = new Rect(0, 0, k, k);
    new Canvas(localBitmap2).drawBitmap(localBitmap1, localRect1, localRect2, null);
    localBitmap1.recycle();
    return localBitmap2;
  }

  public static Bitmap squarifyIfNeeded(Bitmap paramBitmap)
  {
    int i = paramBitmap.getWidth();
    int j = paramBitmap.getHeight();
    if (i == j)
      return paramBitmap;
    int k = Math.min(i, j);
    int n;
    if (i > j)
      n = (i - j) / 2;
    int m;
    for (Rect localRect1 = new Rect(n, 0, k + n, k); ; localRect1 = new Rect(0, m, k, k + m))
    {
      Rect localRect2 = new Rect(0, 0, k, k);
      Bitmap localBitmap = Bitmap.createBitmap(k, k, paramBitmap.getConfig());
      new Canvas(localBitmap).drawBitmap(paramBitmap, localRect1, localRect2, null);
      paramBitmap = localBitmap;
      break;
      m = (j - i) / 2;
    }
  }

  public static class ImageTooSmallException extends Exception
  {
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.util.BitmapHelper
 * JD-Core Version:    0.6.0
 */
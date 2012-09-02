package com.instagram.android.gl;

import android.graphics.Bitmap;
import android.opengl.ETC1Util;
import android.opengl.ETC1Util.ETC1Texture;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import com.instagram.android.Log;
import java.io.IOException;
import java.io.InputStream;

public class GLHelper
{
  public static void checkGlError(String paramString)
  {
    int i = GLES20.glGetError();
    if (i != 0)
    {
      Log.e("Shader", paramString + ": glError " + i);
      throw new RuntimeException(paramString + ": glError " + i);
    }
  }

  public static int makeBitmapTexture(Bitmap paramBitmap)
  {
    int i;
    if (paramBitmap == null)
      i = 0;
    while (true)
    {
      return i;
      int[] arrayOfInt = new int[1];
      GLES20.glGenTextures(1, arrayOfInt, 0);
      i = arrayOfInt[0];
      GLES20.glBindTexture(3553, i);
      try
      {
        GLUtils.texImage2D(3553, 0, paramBitmap, 0);
        GLES20.glTexParameterf(3553, 10241, 9729.0F);
        GLES20.glTexParameterf(3553, 10240, 9729.0F);
        GLES20.glTexParameteri(3553, 10242, 33071);
        GLES20.glTexParameteri(3553, 10243, 33071);
        checkGlError("makeBitmapTexture");
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        Log.d("GLHelper", "Failed to load GL texture", localIllegalArgumentException);
        i = 0;
      }
    }
  }

  public static int makeETC1Texture(InputStream paramInputStream)
  {
    int[] arrayOfInt = new int[1];
    GLES20.glGenTextures(1, arrayOfInt, 0);
    int i = arrayOfInt[0];
    GLES20.glBindTexture(3553, i);
    try
    {
      ETC1Util.ETC1Texture localETC1Texture = ETC1Util.createTexture(paramInputStream);
      ETC1Util.loadTexture(3553, 0, 0, 6407, 33635, localETC1Texture);
      GLES20.glTexParameterf(3553, 10241, 9729.0F);
      GLES20.glTexParameterf(3553, 10240, 9729.0F);
      GLES20.glTexParameteri(3553, 10242, 33071);
      GLES20.glTexParameteri(3553, 10243, 33071);
      return i;
    }
    catch (IOException localIOException)
    {
    }
    throw new RuntimeException("!!!!!>>>> " + localIOException.getStackTrace());
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.gl.GLHelper
 * JD-Core Version:    0.6.0
 */
package com.instagram.util;

import android.content.Context;
import java.io.File;

public class FileUtil
{
  public static File generateTempFile(Context paramContext)
  {
    return new File(paramContext.getCacheDir(), "tmp_photo_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.util.FileUtil
 * JD-Core Version:    0.6.0
 */
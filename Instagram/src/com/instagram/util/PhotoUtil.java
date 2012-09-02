package com.instagram.util;

import android.content.Context;
import android.content.res.Resources;
import android.widget.Toast;

public abstract class PhotoUtil
{
  protected static void showFileNotFoundToast(Context paramContext)
  {
    Toast.makeText(paramContext, paramContext.getResources().getString(2131231008), 1).show();
  }

  protected static void showPhotoTooSmallToast(Context paramContext)
  {
    Toast.makeText(paramContext, paramContext.getResources().getString(2131231007), 1).show();
  }

  protected static void showUnknownErrorToast(Context paramContext)
  {
    Toast.makeText(paramContext, paramContext.getResources().getString(2131231009), 1).show();
  }

  public static class PhotoTooSmallException extends Exception
  {
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.util.PhotoUtil
 * JD-Core Version:    0.6.0
 */
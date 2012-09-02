package com.instagram.util;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.support.v4.app.Fragment;
import java.io.File;

public class GalleryUtil extends PhotoUtil
{
  private static final String TAG = "GalleryUtil";

  private static Intent constructGalleryIntent(File paramFile)
  {
    Intent localIntent = new Intent("android.intent.action.GET_CONTENT", null);
    localIntent.setType("image/*");
    if (paramFile != null)
    {
      localIntent.putExtra("output", Uri.fromFile(paramFile));
      localIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.name());
    }
    return localIntent;
  }

  private static Uri getImageUriFromResult(Intent paramIntent, File paramFile)
  {
    Uri localUri = paramIntent.getData();
    if (((paramFile != null) && (localUri == null)) || (localUri.toString().length() == 0))
      localUri = Uri.fromFile(paramFile);
    return localUri;
  }

  public static Uri handleActivityResult(Intent paramIntent, File paramFile)
  {
    return getImageUriFromResult(paramIntent, paramFile);
  }

  public static void handleActivityResult(Activity paramActivity, Intent paramIntent, File paramFile, int paramInt)
  {
    paramActivity.startActivityForResult(CropUtil.constructCropIntent(paramActivity, handleActivityResult(paramIntent, paramFile)), paramInt);
  }

  public static void handleActivityResult(Fragment paramFragment, Intent paramIntent, File paramFile, int paramInt)
  {
    Uri localUri = handleActivityResult(paramIntent, paramFile);
    paramFragment.startActivityForResult(CropUtil.constructCropIntent(paramFragment.getActivity(), localUri), paramInt);
  }

  public static void show(Activity paramActivity, int paramInt, File paramFile)
  {
    paramActivity.startActivityForResult(Intent.createChooser(constructGalleryIntent(paramFile), paramActivity.getResources().getString(2131231006)), paramInt);
  }

  public static void show(Fragment paramFragment, int paramInt, File paramFile)
  {
    paramFragment.startActivityForResult(Intent.createChooser(constructGalleryIntent(paramFile), paramFragment.getResources().getString(2131231006)), paramInt);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.util.GalleryUtil
 * JD-Core Version:    0.6.0
 */
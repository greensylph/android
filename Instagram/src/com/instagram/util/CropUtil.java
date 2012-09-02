package com.instagram.util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import com.instagram.android.support.camera.CropImage;

public class CropUtil extends PhotoUtil
{
  private static boolean sRestrictNextCrop = false;

  public static Intent constructCropIntent(Activity paramActivity, Uri paramUri)
  {
    Intent localIntent = new Intent(paramActivity, CropImage.class);
    localIntent.setType("image/*");
    Uri localUri = Uri.fromFile(FileUtil.generateTempFile(paramActivity));
    localIntent.setData(paramUri);
    localIntent.putExtra("scale", false);
    localIntent.putExtra("scaleUpIfNeeded", false);
    localIntent.putExtra("largestDimension", 2048);
    if (takeRestrictNextCrop())
      localIntent.putExtra("smallestDimension", 200);
    localIntent.putExtra("aspectX", 1);
    localIntent.putExtra("aspectY", 1);
    localIntent.putExtra("noFaceDetection", true);
    localIntent.putExtra("output", localUri);
    localIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
    CameraUsageReportingUtil.didOpenCropScreen();
    return localIntent;
  }

  public static void setRestrictNextCrop()
  {
    sRestrictNextCrop = true;
  }

  public static void show(Activity paramActivity, int paramInt, Uri paramUri)
  {
    paramActivity.startActivityForResult(constructCropIntent(paramActivity, paramUri), paramInt);
  }

  static boolean takeRestrictNextCrop()
  {
    boolean bool = sRestrictNextCrop;
    sRestrictNextCrop = false;
    return bool;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.util.CropUtil
 * JD-Core Version:    0.6.0
 */
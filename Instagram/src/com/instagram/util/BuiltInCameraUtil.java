package com.instagram.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore.Images.Media;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import com.instagram.android.Log;
import com.instagram.camera.CameraUtil;
import com.instagram.camera.Storage;
import java.io.File;
import java.io.IOException;

public class BuiltInCameraUtil extends PhotoUtil
{
  public static final String TAG = "BuiltInCameraUtil";

  private static Intent constructIntent(Activity paramActivity, Intent paramIntent, File paramFile)
  {
    Uri localUri = Uri.fromFile(paramFile);
    if (localUri == null)
      localUri = paramIntent.getData();
    return CropUtil.constructCropIntent(paramActivity, localUri);
  }

  public static File getPhotoOutputMediaFile()
  {
    return new File(Storage.getImagePath(CameraUtil.createJpegName(System.currentTimeMillis())));
  }

  public static void handleActivityResult(Activity paramActivity, Intent paramIntent, File paramFile, int paramInt)
  {
    paramActivity.startActivityForResult(constructIntent(paramActivity, paramIntent, paramFile), paramInt);
  }

  public static void handleActivityResult(Fragment paramFragment, Intent paramIntent, File paramFile, int paramInt)
  {
    paramFragment.startActivityForResult(constructIntent(paramFragment.getActivity(), paramIntent, paramFile), paramInt);
  }

  private static Intent prepareIntent(ContentResolver paramContentResolver, File paramFile)
  {
    try
    {
      paramFile.getParentFile().mkdirs();
      paramFile.createNewFile();
      Runtime.getRuntime().exec("chmod 0666" + paramFile.getPath());
      String str1 = paramFile.getName();
      String str2 = TextUtils.substring(str1, 0, -3 + str1.length());
      localContentValues = new ContentValues();
      localContentValues.put("title", str2);
      localContentValues.put("_display_name", str1);
      localContentValues.put("datetaken", Long.valueOf(System.currentTimeMillis()));
      localContentValues.put("mime_type", "image/jpeg");
      localContentValues.put("_data", paramFile.getPath());
    }
    catch (IOException localIOException)
    {
      try
      {
        ContentValues localContentValues;
        paramContentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, localContentValues);
        Intent localIntent = new Intent("android.media.action.IMAGE_CAPTURE");
        localIntent.putExtra("output", Uri.fromFile(paramFile));
        return localIntent;
        localIOException = localIOException;
        Log.d("BuiltInCameraUtil", "Error while trying to create photo file", localIOException);
      }
      catch (Exception localException)
      {
        while (true)
          Log.e("BuiltInCameraUtil", "Unable to insert media into media store");
      }
    }
  }

  public static void show(Activity paramActivity, int paramInt, File paramFile)
  {
    paramActivity.startActivityForResult(prepareIntent(paramActivity.getContentResolver(), paramFile), paramInt);
  }

  public static void show(Fragment paramFragment, int paramInt, File paramFile)
  {
    paramFragment.startActivityForResult(prepareIntent(paramFragment.getActivity().getContentResolver(), paramFile), paramInt);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.util.BuiltInCameraUtil
 * JD-Core Version:    0.6.0
 */
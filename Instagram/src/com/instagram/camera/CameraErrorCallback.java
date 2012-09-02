package com.instagram.camera;

import android.hardware.Camera;
import android.hardware.Camera.ErrorCallback;
import com.instagram.android.Log;

public class CameraErrorCallback
  implements Camera.ErrorCallback
{
  private static final String TAG = "CameraErrorCallback";

  public void onError(int paramInt, Camera paramCamera)
  {
    Log.e("CameraErrorCallback", "Got camera error callback. error=" + paramInt);
    if (paramInt == 100)
      throw new RuntimeException("Media server died.");
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.camera.CameraErrorCallback
 * JD-Core Version:    0.6.0
 */
package com.instagram.camera;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import com.instagram.android.Log;
import java.io.IOException;
import java.util.List;

public class CameraPreview extends SurfaceView
  implements SurfaceHolder.Callback
{
  private static final String TAG = "CameraSurfaceView";
  private Camera mCamera;
  private final SurfaceHolder mHolder;

  public CameraPreview(Context paramContext, Camera paramCamera)
  {
    super(paramContext);
    setCamera(paramCamera);
    this.mHolder = getHolder();
    this.mHolder.addCallback(this);
    this.mHolder.setType(3);
  }

  public void setCamera(Camera paramCamera)
  {
    this.mCamera = paramCamera;
    if (this.mCamera != null)
      this.mCamera.setDisplayOrientation(90);
  }

  public void surfaceChanged(SurfaceHolder paramSurfaceHolder, int paramInt1, int paramInt2, int paramInt3)
  {
    if (this.mCamera == null);
    while (true)
    {
      return;
      if (this.mHolder.getSurface() == null)
      {
        Log.d("CameraSurfaceView", "preview surface does not exist");
        continue;
      }
      try
      {
        this.mCamera.stopPreview();
        Camera.Parameters localParameters = this.mCamera.getParameters();
        if (localParameters.getSupportedFocusModes().contains("continuous-picture"))
        {
          Log.d("CameraSurfaceView", "Params: setting continuous-picture");
          localParameters.setFocusMode("continuous-picture");
        }
        if (localParameters.getSupportedWhiteBalance().contains("auto"))
        {
          Log.d("CameraSurfaceView", "Params: setting white balance");
          localParameters.setWhiteBalance("auto");
        }
        localParameters.setPreviewSize(960, 720);
        localParameters.setPictureSize(2592, 1944);
        this.mCamera.setParameters(localParameters);
        try
        {
          this.mCamera.setPreviewDisplay(this.mHolder);
          this.mCamera.startPreview();
        }
        catch (Exception localException2)
        {
          Log.d("CameraSurfaceView", "Error starting camera preview: " + localException2.getMessage());
        }
      }
      catch (Exception localException1)
      {
        while (true)
          Log.d("CameraSurfaceView", "ignore: tried to stop a non-existent preview");
      }
    }
  }

  public void surfaceCreated(SurfaceHolder paramSurfaceHolder)
  {
    if (this.mCamera == null);
    while (true)
    {
      return;
      try
      {
        this.mCamera.setPreviewDisplay(paramSurfaceHolder);
        this.mCamera.startPreview();
      }
      catch (IOException localIOException)
      {
        Log.d("CameraSurfaceView", "Error setting camera preview: " + localIOException.getMessage());
      }
    }
  }

  public void surfaceDestroyed(SurfaceHolder paramSurfaceHolder)
  {
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.camera.CameraPreview
 * JD-Core Version:    0.6.0
 */
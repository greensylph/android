package com.instagram.camera;

import android.annotation.TargetApi;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import com.instagram.android.Log;
import java.io.IOException;

@TargetApi(9)
public class CameraHolder
{
  private static final int RELEASE_CAMERA = 1;
  private static final String TAG = "CameraHolder";
  private static CameraHolder sHolder;
  private int mBackCameraId = -1;
  private Camera mCameraDevice;
  private int mCameraId = -1;
  private int mFrontCameraId = -1;
  private final Handler mHandler;
  private Camera.CameraInfo[] mInfo;
  private long mKeepBeforeTime = 0L;
  private int mNumberOfCameras;
  private Camera.Parameters mParameters;
  private int mUsers = 0;

  private CameraHolder()
  {
    HandlerThread localHandlerThread = new HandlerThread("CameraHolder");
    localHandlerThread.start();
    this.mHandler = new MyHandler(localHandlerThread.getLooper());
    this.mNumberOfCameras = Camera.getNumberOfCameras();
    this.mInfo = new Camera.CameraInfo[this.mNumberOfCameras];
    for (int i = 0; i < this.mNumberOfCameras; i++)
    {
      this.mInfo[i] = new Camera.CameraInfo();
      Camera.getCameraInfo(i, this.mInfo[i]);
      if ((this.mBackCameraId == -1) && (this.mInfo[i].facing == 0))
        this.mBackCameraId = i;
      if ((this.mFrontCameraId != -1) || (this.mInfo[i].facing != 1))
        continue;
      this.mFrontCameraId = i;
    }
  }

  public static CameraHolder instance()
  {
    monitorenter;
    try
    {
      if (sHolder == null)
        sHolder = new CameraHolder();
      CameraHolder localCameraHolder = sHolder;
      monitorexit;
      return localCameraHolder;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  private void releaseCamera()
  {
    boolean bool1 = true;
    monitorenter;
    try
    {
      boolean bool2;
      if (this.mUsers == 0)
      {
        bool2 = bool1;
        CameraUtil.Assert(bool2);
        if (this.mCameraDevice == null)
          break label67;
        label24: CameraUtil.Assert(bool1);
        long l = System.currentTimeMillis();
        if (l >= this.mKeepBeforeTime)
          break label72;
        this.mHandler.sendEmptyMessageDelayed(1, this.mKeepBeforeTime - l);
      }
      while (true)
      {
        return;
        bool2 = false;
        break;
        label67: bool1 = false;
        break label24;
        label72: this.mCameraDevice.release();
        this.mCameraDevice = null;
        this.mParameters = null;
        this.mCameraId = -1;
      }
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public int getBackCameraId()
  {
    return this.mBackCameraId;
  }

  public Camera.CameraInfo[] getCameraInfo()
  {
    return this.mInfo;
  }

  public int getFrontCameraId()
  {
    return this.mFrontCameraId;
  }

  public int getNumberOfCameras()
  {
    return this.mNumberOfCameras;
  }

  public void keep()
  {
    int i = 1;
    monitorenter;
    try
    {
      if ((this.mUsers == i) || (this.mUsers == 0))
      {
        CameraUtil.Assert(i);
        this.mKeepBeforeTime = (3000L + System.currentTimeMillis());
        return;
      }
      int j = 0;
    }
    finally
    {
      monitorexit;
    }
  }

  public Camera open(int paramInt)
    throws CameraHardwareException
  {
    boolean bool = true;
    monitorenter;
    while (true)
    {
      try
      {
        if (this.mUsers != 0)
          continue;
        CameraUtil.Assert(bool);
        if ((this.mCameraDevice == null) || (this.mCameraId == paramInt))
          continue;
        this.mCameraDevice.release();
        this.mCameraDevice = null;
        this.mCameraId = -1;
        Camera localCamera1 = this.mCameraDevice;
        if (localCamera1 == null)
          try
          {
            Log.v("CameraHolder", "open camera " + paramInt);
            this.mCameraDevice = Camera.open(paramInt);
            this.mCameraId = paramInt;
            this.mParameters = this.mCameraDevice.getParameters();
            this.mUsers = (1 + this.mUsers);
            this.mHandler.removeMessages(1);
            this.mKeepBeforeTime = 0L;
            Camera localCamera2 = this.mCameraDevice;
            monitorexit;
            return localCamera2;
            bool = false;
          }
          catch (RuntimeException localRuntimeException)
          {
            Log.e("CameraHolder", "fail to connect Camera", localRuntimeException);
            throw new CameraHardwareException(localRuntimeException);
          }
      }
      finally
      {
        monitorexit;
      }
      try
      {
        this.mCameraDevice.reconnect();
        this.mCameraDevice.setParameters(this.mParameters);
      }
      catch (IOException localIOException)
      {
        Log.e("CameraHolder", "reconnect failed.");
      }
    }
    throw new CameraHardwareException(localIOException);
  }

  public void release()
  {
    int i = 1;
    monitorenter;
    try
    {
      if (this.mUsers == i)
      {
        CameraUtil.Assert(i);
        this.mUsers = (-1 + this.mUsers);
        this.mCameraDevice.stopPreview();
        releaseCamera();
        return;
      }
      int j = 0;
    }
    finally
    {
      monitorexit;
    }
  }

  public Camera tryOpen(int paramInt)
  {
    Object localObject1 = null;
    monitorenter;
    try
    {
      if (this.mUsers == 0)
      {
        Camera localCamera = open(paramInt);
        localObject1 = localCamera;
      }
      return localObject1;
    }
    catch (CameraHardwareException localCameraHardwareException)
    {
      while (!"eng".equals(Build.TYPE));
      throw new RuntimeException(localCameraHardwareException);
    }
    finally
    {
      monitorexit;
    }
    throw localObject2;
  }

  private class MyHandler extends Handler
  {
    MyHandler(Looper arg2)
    {
      super();
    }

    public void handleMessage(Message paramMessage)
    {
      switch (paramMessage.what)
      {
      default:
      case 1:
      }
      while (true)
      {
        return;
        synchronized (CameraHolder.this)
        {
          if (CameraHolder.this.mUsers == 0)
            CameraHolder.this.releaseCamera();
        }
      }
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.camera.CameraHolder
 * JD-Core Version:    0.6.0
 */
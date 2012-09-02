package com.instagram.android.gl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.EGLConfigChooser;
import android.opengl.GLSurfaceView.EGLContextFactory;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View.BaseSavedState;
import com.instagram.android.Log;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;

public class FilterGLSurfaceView extends GLSurfaceView
{
  private static final boolean DEBUG = false;
  static final float GRADIENT_MAX_RADIUS = 0.5F;
  static final float GRADIENT_MIN_RADIUS = 0.05F;
  private static final int INVALID_POINTER_ID = -1;
  private static String TAG = "FilterGLSurfaceView";
  boolean mActionMoved = true;
  private int mActivePointerId = -1;
  private FilterController mFilterController;
  int mLastMovedPointerId = -1;
  private float mLastX;
  private float mLastY;
  final NativeRenderer mRenderer = new NativeRenderer();
  private IgScaleGestureDetector mScaleDetector;
  private float mScaleFactor = 1.0F;
  private float mStartPanOriginX;
  private float mStartPanOriginY;
  private float mStartPanPointX;
  private float mStartPanPointY;
  private float mStartRadius;
  private float mStartTheta;
  private float mTiltShiftOriginX = 0.5F;
  private float mTiltShiftOriginY = 0.5F;
  private float mTiltShiftRadius = 0.125F;
  private float mTiltShiftTheta = 0.0F;

  public FilterGLSurfaceView(Context paramContext)
  {
    super(paramContext);
    init(paramContext);
  }

  public FilterGLSurfaceView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(paramContext);
  }

  private void beginMoveState()
  {
    NativeBridge.tiltShiftFadeInMaskHighlight();
    this.mActionMoved = true;
  }

  private static void checkEglError(String paramString, EGL10 paramEGL10)
  {
    while (true)
    {
      int i = paramEGL10.eglGetError();
      if (i == 12288)
        break;
      String str = TAG;
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = paramString;
      arrayOfObject[1] = Integer.valueOf(i);
      Log.e(str, String.format("%s: EGL error: 0x%x", arrayOfObject));
    }
  }

  private float getGlCoordinate(float paramFloat1, float paramFloat2)
  {
    return paramFloat1 / paramFloat2;
  }

  private void init(Context paramContext)
  {
    setZOrderOnTop(true);
    getHolder().setFormat(-3);
    setEGLContextClientVersion(2);
    setEGLContextFactory(new ContextFactory(null));
    setEGLConfigChooser(new ConfigChooser(8, 8, 8, 0, 0, 0));
    setRenderer(this.mRenderer);
    setRenderMode(0);
    this.mScaleDetector = new IgScaleGestureDetector(paramContext, new ScaleListener(null));
  }

  private void setTiltShiftOrigin(float paramFloat1, float paramFloat2)
  {
    this.mTiltShiftOriginX = paramFloat1;
    this.mTiltShiftOriginY = paramFloat2;
    this.mFilterController.setTiltShiftOrigin(paramFloat1, paramFloat2);
  }

  private void setTiltShiftRadius(float paramFloat)
  {
    this.mTiltShiftRadius = paramFloat;
    this.mFilterController.setTiltShiftRadius(paramFloat);
  }

  private void setTiltShiftTheta(float paramFloat)
  {
    this.mTiltShiftTheta = paramFloat;
    this.mFilterController.setTiltShiftTheta(paramFloat);
  }

  public NativeRenderer getRenderer()
  {
    return this.mRenderer;
  }

  public float getTiltShiftOriginX()
  {
    return this.mTiltShiftOriginX;
  }

  public float getTiltShiftOriginY()
  {
    return this.mTiltShiftOriginY;
  }

  public float getTiltShiftRadius()
  {
    return this.mTiltShiftRadius;
  }

  public float getTiltShiftTheta()
  {
    return this.mTiltShiftTheta;
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
  }

  protected void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if (!(paramParcelable instanceof SavedState))
      super.onRestoreInstanceState(paramParcelable);
    while (true)
    {
      return;
      SavedState localSavedState = (SavedState)paramParcelable;
      super.onRestoreInstanceState(localSavedState.getSuperState());
      this.mTiltShiftOriginX = localSavedState.mTiltShiftOriginX;
      this.mTiltShiftOriginY = localSavedState.mTiltShiftOriginY;
      this.mTiltShiftTheta = localSavedState.mTiltShiftTheta;
      this.mTiltShiftRadius = localSavedState.mTiltShiftRadius;
    }
  }

  protected Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = new SavedState(super.onSaveInstanceState());
    SavedState.access$002(localSavedState, this.mTiltShiftOriginX);
    SavedState.access$102(localSavedState, this.mTiltShiftOriginY);
    SavedState.access$202(localSavedState, this.mTiltShiftTheta);
    SavedState.access$302(localSavedState, this.mTiltShiftRadius);
    return localSavedState;
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    int m;
    if (!NativeBridge.getTiltShiftEnabled())
    {
      m = 0;
      return m;
    }
    this.mScaleDetector.onTouchEvent(paramMotionEvent);
    int i = paramMotionEvent.getAction();
    switch (i & 0xFF)
    {
    case 4:
    case 5:
    default:
    case 0:
    case 2:
    case 1:
    case 3:
    case 6:
    }
    while (true)
    {
      m = 1;
      break;
      this.mStartPanOriginX = this.mTiltShiftOriginX;
      this.mStartPanOriginY = this.mTiltShiftOriginY;
      this.mStartTheta = this.mTiltShiftTheta;
      this.mStartRadius = this.mTiltShiftRadius;
      this.mStartPanPointX = paramMotionEvent.getX();
      this.mStartPanPointY = paramMotionEvent.getY();
      this.mLastX = paramMotionEvent.getX();
      this.mLastY = paramMotionEvent.getY();
      this.mActivePointerId = paramMotionEvent.getPointerId(0);
      if (paramMotionEvent.getPointerCount() != 1)
        continue;
      this.mActionMoved = false;
      continue;
      if (this.mActivePointerId == -1)
        continue;
      int i1 = paramMotionEvent.findPointerIndex(this.mActivePointerId);
      float f3 = paramMotionEvent.getX(i1);
      float f4 = paramMotionEvent.getY(i1);
      if (this.mLastMovedPointerId != this.mActivePointerId)
      {
        this.mLastMovedPointerId = this.mActivePointerId;
        continue;
      }
      if ((this.mScaleDetector.isInProgress()) || ((f3 == this.mLastX) && (f4 == this.mLastY)))
        continue;
      this.mLastX = f3;
      this.mLastY = f4;
      float f5 = getGlCoordinate(f3 - this.mStartPanPointX, getWidth());
      float f6 = getGlCoordinate(f4 - this.mStartPanPointY, getHeight());
      if ((f5 >= -0.01D) && (f5 <= 0.01D) && (f6 >= -0.01D) && (f6 <= 0.01D))
        continue;
      beginMoveState();
      setTiltShiftOrigin(Math.max(Math.min(f5 + this.mStartPanOriginX, 1.0F), 0.0F), Math.max(Math.min(-f6 + this.mStartPanOriginY, 1.0F), 0.0F));
      continue;
      this.mActivePointerId = -1;
      if (!this.mScaleDetector.isInProgress())
      {
        if ((this.mActionMoved) || (paramMotionEvent.getPointerCount() != 1))
          break label524;
        float f1 = getGlCoordinate(this.mLastX, getWidth());
        float f2 = 1.0F - getGlCoordinate(this.mLastY, getHeight());
        Log.d("actionMoved", "setTiltShiftOrigin " + f1 + " " + f2);
        setTiltShiftOrigin(f1, f2);
        NativeBridge.tiltShiftFadeInMaskHighlight();
        getHandler().postDelayed(new Runnable()
        {
          public void run()
          {
            NativeBridge.tiltShiftFadeOutMaskHighlight();
          }
        }
        , 500L);
      }
      while (true)
      {
        this.mActionMoved = true;
        break;
        label524: NativeBridge.tiltShiftFadeOutMaskHighlight();
      }
      this.mActivePointerId = -1;
      NativeBridge.tiltShiftFadeOutMaskHighlight();
      continue;
      int j = (0xFF00 & i) >> 8;
      int k = paramMotionEvent.getPointerId(j);
      Log.d("pan", "ACTION_POINTER_UP called");
      if (k == this.mActivePointerId)
      {
        if (j == 0);
        for (int n = 1; ; n = 0)
        {
          if (!this.mScaleDetector.isInProgress())
          {
            this.mStartPanOriginX = this.mTiltShiftOriginX;
            this.mStartPanOriginY = this.mTiltShiftOriginY;
            this.mStartTheta = this.mTiltShiftRadius;
            this.mStartRadius = this.mTiltShiftRadius;
            this.mStartPanPointX = paramMotionEvent.getX(n);
            this.mStartPanPointY = paramMotionEvent.getY(n);
            this.mLastX = paramMotionEvent.getX(n);
            this.mLastY = paramMotionEvent.getY(n);
            this.mActionMoved = true;
          }
          this.mActivePointerId = paramMotionEvent.getPointerId(n);
          break;
        }
      }
      if (this.mScaleDetector.isInProgress())
        continue;
      this.mStartPanOriginX = this.mTiltShiftOriginX;
      this.mStartPanOriginY = this.mTiltShiftOriginY;
      this.mStartTheta = this.mTiltShiftTheta;
      this.mStartRadius = this.mTiltShiftRadius;
      this.mStartPanPointX = paramMotionEvent.getX(this.mActivePointerId);
      this.mStartPanPointY = paramMotionEvent.getY(this.mActivePointerId);
      this.mLastX = paramMotionEvent.getX(this.mActivePointerId);
      this.mLastY = paramMotionEvent.getY(this.mActivePointerId);
      this.mActionMoved = true;
    }
  }

  public void setFilterController(FilterController paramFilterController)
  {
    this.mFilterController = paramFilterController;
  }

  private static class ConfigChooser
    implements GLSurfaceView.EGLConfigChooser
  {
    private static int EGL_OPENGL_ES2_BIT = 4;
    private static int[] s_configAttribs2;
    protected int mAlphaSize;
    protected int mBlueSize;
    protected int mDepthSize;
    protected int mGreenSize;
    protected int mRedSize;
    protected int mStencilSize;
    private int[] mValue = new int[1];

    static
    {
      int[] arrayOfInt = new int[9];
      arrayOfInt[0] = 12324;
      arrayOfInt[1] = 4;
      arrayOfInt[2] = 12323;
      arrayOfInt[3] = 4;
      arrayOfInt[4] = 12322;
      arrayOfInt[5] = 4;
      arrayOfInt[6] = 12352;
      arrayOfInt[7] = EGL_OPENGL_ES2_BIT;
      arrayOfInt[8] = 12344;
      s_configAttribs2 = arrayOfInt;
    }

    public ConfigChooser(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
    {
      this.mRedSize = paramInt1;
      this.mGreenSize = paramInt2;
      this.mBlueSize = paramInt3;
      this.mAlphaSize = paramInt4;
      this.mDepthSize = paramInt5;
      this.mStencilSize = paramInt6;
    }

    private int findConfigAttrib(EGL10 paramEGL10, EGLDisplay paramEGLDisplay, EGLConfig paramEGLConfig, int paramInt1, int paramInt2)
    {
      if (paramEGL10.eglGetConfigAttrib(paramEGLDisplay, paramEGLConfig, paramInt1, this.mValue))
        paramInt2 = this.mValue[0];
      return paramInt2;
    }

    private void printConfig(EGL10 paramEGL10, EGLDisplay paramEGLDisplay, EGLConfig paramEGLConfig)
    {
      int[] arrayOfInt1 = new int[33];
      arrayOfInt1[0] = 12320;
      arrayOfInt1[1] = 12321;
      arrayOfInt1[2] = 12322;
      arrayOfInt1[3] = 12323;
      arrayOfInt1[4] = 12324;
      arrayOfInt1[5] = 12325;
      arrayOfInt1[6] = 12326;
      arrayOfInt1[7] = 12327;
      arrayOfInt1[8] = 12328;
      arrayOfInt1[9] = 12329;
      arrayOfInt1[10] = 12330;
      arrayOfInt1[11] = 12331;
      arrayOfInt1[12] = 12332;
      arrayOfInt1[13] = 12333;
      arrayOfInt1[14] = 12334;
      arrayOfInt1[15] = 12335;
      arrayOfInt1[16] = 12336;
      arrayOfInt1[17] = 12337;
      arrayOfInt1[18] = 12338;
      arrayOfInt1[19] = 12339;
      arrayOfInt1[20] = 12340;
      arrayOfInt1[21] = 12343;
      arrayOfInt1[22] = 12342;
      arrayOfInt1[23] = 12341;
      arrayOfInt1[24] = 12345;
      arrayOfInt1[25] = 12346;
      arrayOfInt1[26] = 12347;
      arrayOfInt1[27] = 12348;
      arrayOfInt1[28] = 12349;
      arrayOfInt1[29] = 12350;
      arrayOfInt1[30] = 12351;
      arrayOfInt1[31] = 12352;
      arrayOfInt1[32] = 12354;
      String[] arrayOfString = new String[33];
      arrayOfString[0] = "EGL_BUFFER_SIZE";
      arrayOfString[1] = "EGL_ALPHA_SIZE";
      arrayOfString[2] = "EGL_BLUE_SIZE";
      arrayOfString[3] = "EGL_GREEN_SIZE";
      arrayOfString[4] = "EGL_RED_SIZE";
      arrayOfString[5] = "EGL_DEPTH_SIZE";
      arrayOfString[6] = "EGL_STENCIL_SIZE";
      arrayOfString[7] = "EGL_CONFIG_CAVEAT";
      arrayOfString[8] = "EGL_CONFIG_ID";
      arrayOfString[9] = "EGL_LEVEL";
      arrayOfString[10] = "EGL_MAX_PBUFFER_HEIGHT";
      arrayOfString[11] = "EGL_MAX_PBUFFER_PIXELS";
      arrayOfString[12] = "EGL_MAX_PBUFFER_WIDTH";
      arrayOfString[13] = "EGL_NATIVE_RENDERABLE";
      arrayOfString[14] = "EGL_NATIVE_VISUAL_ID";
      arrayOfString[15] = "EGL_NATIVE_VISUAL_TYPE";
      arrayOfString[16] = "EGL_PRESERVED_RESOURCES";
      arrayOfString[17] = "EGL_SAMPLES";
      arrayOfString[18] = "EGL_SAMPLE_BUFFERS";
      arrayOfString[19] = "EGL_SURFACE_TYPE";
      arrayOfString[20] = "EGL_TRANSPARENT_TYPE";
      arrayOfString[21] = "EGL_TRANSPARENT_RED_VALUE";
      arrayOfString[22] = "EGL_TRANSPARENT_GREEN_VALUE";
      arrayOfString[23] = "EGL_TRANSPARENT_BLUE_VALUE";
      arrayOfString[24] = "EGL_BIND_TO_TEXTURE_RGB";
      arrayOfString[25] = "EGL_BIND_TO_TEXTURE_RGBA";
      arrayOfString[26] = "EGL_MIN_SWAP_INTERVAL";
      arrayOfString[27] = "EGL_MAX_SWAP_INTERVAL";
      arrayOfString[28] = "EGL_LUMINANCE_SIZE";
      arrayOfString[29] = "EGL_ALPHA_MASK_SIZE";
      arrayOfString[30] = "EGL_COLOR_BUFFER_TYPE";
      arrayOfString[31] = "EGL_RENDERABLE_TYPE";
      arrayOfString[32] = "EGL_CONFORMANT";
      int[] arrayOfInt2 = new int[1];
      int i = 0;
      if (i < arrayOfInt1.length)
      {
        int j = arrayOfInt1[i];
        String str1 = arrayOfString[i];
        if (paramEGL10.eglGetConfigAttrib(paramEGLDisplay, paramEGLConfig, j, arrayOfInt2))
        {
          String str2 = FilterGLSurfaceView.TAG;
          Object[] arrayOfObject = new Object[2];
          arrayOfObject[0] = str1;
          arrayOfObject[1] = Integer.valueOf(arrayOfInt2[0]);
          Log.w(str2, String.format("  %s: %d\n", arrayOfObject));
        }
        while (true)
        {
          i++;
          break;
          while (paramEGL10.eglGetError() != 12288);
        }
      }
    }

    private void printConfigs(EGL10 paramEGL10, EGLDisplay paramEGLDisplay, EGLConfig[] paramArrayOfEGLConfig)
    {
      int i = paramArrayOfEGLConfig.length;
      String str1 = FilterGLSurfaceView.TAG;
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = Integer.valueOf(i);
      Log.w(str1, String.format("%d configurations", arrayOfObject1));
      for (int j = 0; j < i; j++)
      {
        String str2 = FilterGLSurfaceView.TAG;
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = Integer.valueOf(j);
        Log.w(str2, String.format("Configuration %d:\n", arrayOfObject2));
        printConfig(paramEGL10, paramEGLDisplay, paramArrayOfEGLConfig[j]);
      }
    }

    public EGLConfig chooseConfig(EGL10 paramEGL10, EGLDisplay paramEGLDisplay)
    {
      int[] arrayOfInt = new int[1];
      paramEGL10.eglChooseConfig(paramEGLDisplay, s_configAttribs2, null, 0, arrayOfInt);
      int i = arrayOfInt[0];
      if (i <= 0)
        throw new IllegalArgumentException("No configs match configSpec");
      EGLConfig[] arrayOfEGLConfig = new EGLConfig[i];
      paramEGL10.eglChooseConfig(paramEGLDisplay, s_configAttribs2, arrayOfEGLConfig, i, arrayOfInt);
      return chooseConfig(paramEGL10, paramEGLDisplay, arrayOfEGLConfig);
    }

    public EGLConfig chooseConfig(EGL10 paramEGL10, EGLDisplay paramEGLDisplay, EGLConfig[] paramArrayOfEGLConfig)
    {
      int i = paramArrayOfEGLConfig.length;
      int j = 0;
      EGLConfig localEGLConfig;
      if (j < i)
      {
        localEGLConfig = paramArrayOfEGLConfig[j];
        int k = findConfigAttrib(paramEGL10, paramEGLDisplay, localEGLConfig, 12325, 0);
        int m = findConfigAttrib(paramEGL10, paramEGLDisplay, localEGLConfig, 12326, 0);
        if ((k < this.mDepthSize) || (m < this.mStencilSize));
        int n;
        int i1;
        int i2;
        int i3;
        do
        {
          j++;
          break;
          n = findConfigAttrib(paramEGL10, paramEGLDisplay, localEGLConfig, 12324, 0);
          i1 = findConfigAttrib(paramEGL10, paramEGLDisplay, localEGLConfig, 12323, 0);
          i2 = findConfigAttrib(paramEGL10, paramEGLDisplay, localEGLConfig, 12322, 0);
          i3 = findConfigAttrib(paramEGL10, paramEGLDisplay, localEGLConfig, 12321, 0);
        }
        while ((n != this.mRedSize) || (i1 != this.mGreenSize) || (i2 != this.mBlueSize) || (i3 < this.mAlphaSize));
      }
      while (true)
      {
        return localEGLConfig;
        localEGLConfig = null;
      }
    }
  }

  private static class ContextFactory
    implements GLSurfaceView.EGLContextFactory
  {
    private static int EGL_CONTEXT_CLIENT_VERSION = 12440;

    public EGLContext createContext(EGL10 paramEGL10, EGLDisplay paramEGLDisplay, EGLConfig paramEGLConfig)
    {
      Log.w(FilterGLSurfaceView.TAG, "creating OpenGL ES 2.0 context");
      FilterGLSurfaceView.access$800("Before eglCreateContext", paramEGL10);
      int[] arrayOfInt = new int[3];
      arrayOfInt[0] = EGL_CONTEXT_CLIENT_VERSION;
      arrayOfInt[1] = 2;
      arrayOfInt[2] = 12344;
      EGLContext localEGLContext = paramEGL10.eglCreateContext(paramEGLDisplay, paramEGLConfig, EGL10.EGL_NO_CONTEXT, arrayOfInt);
      FilterGLSurfaceView.access$800("After eglCreateContext", paramEGL10);
      return localEGLContext;
    }

    public void destroyContext(EGL10 paramEGL10, EGLDisplay paramEGLDisplay, EGLContext paramEGLContext)
    {
      paramEGL10.eglDestroyContext(paramEGLDisplay, paramEGLContext);
    }
  }

  static class SavedState extends View.BaseSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new FilterGLSurfaceView.SavedState.1();
    private float mTiltShiftOriginX;
    private float mTiltShiftOriginY;
    private float mTiltShiftRadius;
    private float mTiltShiftTheta;

    private SavedState(Parcel paramParcel)
    {
      super();
      this.mTiltShiftOriginX = paramParcel.readFloat();
      this.mTiltShiftOriginY = paramParcel.readFloat();
      this.mTiltShiftTheta = paramParcel.readFloat();
      this.mTiltShiftRadius = paramParcel.readFloat();
    }

    SavedState(Parcelable paramParcelable)
    {
      super();
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      paramParcel.writeFloat(this.mTiltShiftOriginX);
      paramParcel.writeFloat(this.mTiltShiftOriginY);
      paramParcel.writeFloat(this.mTiltShiftTheta);
      paramParcel.writeFloat(this.mTiltShiftRadius);
    }
  }

  private class ScaleListener
    implements IgScaleGestureDetector.OnScaleGestureListener
  {
    float mStartScalePanPointX;
    float mStartScalePanPointY;

    private ScaleListener()
    {
    }

    public boolean onScale(IgScaleGestureDetector paramIgScaleGestureDetector)
    {
      FilterGLSurfaceView.this.beginMoveState();
      float f1 = Math.max(Math.min(FilterGLSurfaceView.this.mStartRadius * paramIgScaleGestureDetector.getScaleFactor(), 0.5F), 0.05F);
      if (FilterGLSurfaceView.this.mStartRadius != f1)
      {
        FilterGLSurfaceView.this.setTiltShiftRadius(f1);
        FilterGLSurfaceView.access$1002(FilterGLSurfaceView.this, f1);
      }
      float f2 = paramIgScaleGestureDetector.getFocusX();
      float f3 = paramIgScaleGestureDetector.getFocusY();
      float f4 = FilterGLSurfaceView.this.getGlCoordinate(f2 - this.mStartScalePanPointX, FilterGLSurfaceView.this.getWidth());
      float f5 = FilterGLSurfaceView.this.getGlCoordinate(f3 - this.mStartScalePanPointY, FilterGLSurfaceView.this.getHeight());
      float f6 = Math.max(Math.min(f4 + FilterGLSurfaceView.this.mStartPanOriginX, 1.0F), 0.0F);
      float f7 = Math.max(Math.min(-f5 + FilterGLSurfaceView.this.mStartPanOriginY, 1.0F), 0.0F);
      FilterGLSurfaceView.this.mFilterController.setTiltShiftOrigin(f6, f7);
      double d1 = Math.atan2(paramIgScaleGestureDetector.getPreviousSpanY(), paramIgScaleGestureDetector.getPreviousSpanX());
      double d2 = Math.atan2(paramIgScaleGestureDetector.getCurrentSpanY(), paramIgScaleGestureDetector.getCurrentSpanX());
      if (d1 != d2)
      {
        float f8 = FilterGLSurfaceView.this.mStartTheta + (float)d2 - (float)d1;
        FilterGLSurfaceView.this.setTiltShiftTheta(f8);
        FilterGLSurfaceView.access$1602(FilterGLSurfaceView.this, f8);
      }
      return true;
    }

    public boolean onScaleBegin(IgScaleGestureDetector paramIgScaleGestureDetector)
    {
      FilterGLSurfaceView.access$1302(FilterGLSurfaceView.this, FilterGLSurfaceView.this.mTiltShiftOriginX);
      FilterGLSurfaceView.access$1402(FilterGLSurfaceView.this, FilterGLSurfaceView.this.mTiltShiftOriginY);
      this.mStartScalePanPointX = paramIgScaleGestureDetector.getFocusX();
      this.mStartScalePanPointY = paramIgScaleGestureDetector.getFocusY();
      FilterGLSurfaceView.access$1602(FilterGLSurfaceView.this, FilterGLSurfaceView.this.mTiltShiftTheta);
      FilterGLSurfaceView.this.beginMoveState();
      return true;
    }

    public void onScaleEnd(IgScaleGestureDetector paramIgScaleGestureDetector)
    {
      FilterGLSurfaceView.this.mActionMoved = true;
      FilterGLSurfaceView.this.mScaleDetector.hardReset();
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.gl.FilterGLSurfaceView
 * JD-Core Version:    0.6.0
 */
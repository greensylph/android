package com.instagram.android.gl;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

public class IgScaleGestureDetector
{
  private static final float PRESSURE_THRESHOLD = 0.67F;
  private static final String TAG = "ScaleGestureDetector";
  private boolean mActive0MostRecent;
  private int mActiveId0;
  private int mActiveId1;
  private float mBottomSlopEdge;
  private final Context mContext;
  private MotionEvent mCurrEvent;
  private float mCurrFingerDiffX;
  private float mCurrFingerDiffY;
  private float mCurrLen;
  private float mCurrPressure;
  private final float mEdgeSlop;
  private float mFocusX;
  private float mFocusY;
  private boolean mGestureInProgress;
  private boolean mInvalidGesture;
  private final OnScaleGestureListener mListener;
  private MotionEvent mPrevEvent;
  private float mPrevFingerDiffX;
  private float mPrevFingerDiffY;
  private float mPrevLen;
  private float mPrevPressure;
  private float mRightSlopEdge;
  private float mScaleFactor;
  private boolean mSloppyGesture;
  private long mTimeDelta;

  public IgScaleGestureDetector(Context paramContext, OnScaleGestureListener paramOnScaleGestureListener)
  {
    ViewConfiguration localViewConfiguration = ViewConfiguration.get(paramContext);
    this.mContext = paramContext;
    this.mListener = paramOnScaleGestureListener;
    this.mEdgeSlop = localViewConfiguration.getScaledEdgeSlop();
  }

  private int findNewActiveIndex(MotionEvent paramMotionEvent, int paramInt1, int paramInt2)
  {
    int i = paramMotionEvent.getPointerCount();
    int j = paramMotionEvent.findPointerIndex(paramInt1);
    int k = -1;
    for (int m = 0; ; m++)
    {
      if (m < i)
      {
        if ((m == paramInt2) || (m == j))
          continue;
        float f1 = this.mEdgeSlop;
        float f2 = this.mRightSlopEdge;
        float f3 = this.mBottomSlopEdge;
        float f4 = getRawX(paramMotionEvent, m);
        float f5 = getRawY(paramMotionEvent, m);
        if ((f4 < f1) || (f5 < f1) || (f4 > f2) || (f5 > f3))
          continue;
        k = m;
      }
      return k;
    }
  }

  private static float getRawX(MotionEvent paramMotionEvent, int paramInt)
  {
    float f;
    if (paramInt < 0)
      f = 1.4E-45F;
    while (true)
    {
      return f;
      if (paramInt == 0)
      {
        f = paramMotionEvent.getRawX();
        continue;
      }
      f = paramMotionEvent.getRawX() - paramMotionEvent.getX() + paramMotionEvent.getX(paramInt);
    }
  }

  private static float getRawY(MotionEvent paramMotionEvent, int paramInt)
  {
    float f;
    if (paramInt < 0)
      f = 1.4E-45F;
    while (true)
    {
      return f;
      if (paramInt == 0)
      {
        f = paramMotionEvent.getRawY();
        continue;
      }
      f = paramMotionEvent.getRawY() - paramMotionEvent.getY() + paramMotionEvent.getY(paramInt);
    }
  }

  private void reset()
  {
    if (this.mPrevEvent != null)
    {
      this.mPrevEvent.recycle();
      this.mPrevEvent = null;
    }
    if (this.mCurrEvent != null)
    {
      this.mCurrEvent.recycle();
      this.mCurrEvent = null;
    }
    this.mSloppyGesture = false;
    this.mGestureInProgress = false;
    this.mActiveId0 = -1;
    this.mActiveId1 = -1;
    this.mInvalidGesture = false;
  }

  private void setContext(MotionEvent paramMotionEvent)
  {
    if (this.mCurrEvent != null)
      this.mCurrEvent.recycle();
    this.mCurrEvent = MotionEvent.obtain(paramMotionEvent);
    this.mCurrLen = -1.0F;
    this.mPrevLen = -1.0F;
    this.mScaleFactor = -1.0F;
    MotionEvent localMotionEvent = this.mPrevEvent;
    int i = localMotionEvent.findPointerIndex(this.mActiveId0);
    int j = localMotionEvent.findPointerIndex(this.mActiveId1);
    int k = paramMotionEvent.findPointerIndex(this.mActiveId0);
    int m = paramMotionEvent.findPointerIndex(this.mActiveId1);
    if ((i < 0) || (j < 0) || (k < 0) || (m < 0))
    {
      this.mInvalidGesture = true;
      Log.e("ScaleGestureDetector", "Invalid MotionEvent stream detected.", new Throwable());
      if (this.mGestureInProgress)
        this.mListener.onScaleEnd(this);
    }
    while (true)
    {
      return;
      float f1 = localMotionEvent.getX(i);
      float f2 = localMotionEvent.getY(i);
      float f3 = localMotionEvent.getX(j);
      float f4 = localMotionEvent.getY(j);
      float f5 = paramMotionEvent.getX(k);
      float f6 = paramMotionEvent.getY(k);
      float f7 = paramMotionEvent.getX(m);
      float f8 = paramMotionEvent.getY(m);
      float f9 = f3 - f1;
      float f10 = f4 - f2;
      float f11 = f7 - f5;
      float f12 = f8 - f6;
      this.mPrevFingerDiffX = f9;
      this.mPrevFingerDiffY = f10;
      this.mCurrFingerDiffX = f11;
      this.mCurrFingerDiffY = f12;
      this.mFocusX = (f5 + 0.5F * f11);
      this.mFocusY = (f6 + 0.5F * f12);
      this.mTimeDelta = (paramMotionEvent.getEventTime() - localMotionEvent.getEventTime());
      this.mCurrPressure = (paramMotionEvent.getPressure(k) + paramMotionEvent.getPressure(m));
      this.mPrevPressure = (localMotionEvent.getPressure(i) + localMotionEvent.getPressure(j));
    }
  }

  public float getCurrentSpan()
  {
    if (this.mCurrLen == -1.0F)
    {
      float f1 = this.mCurrFingerDiffX;
      float f2 = this.mCurrFingerDiffY;
      this.mCurrLen = FloatMath.sqrt(f1 * f1 + f2 * f2);
    }
    return this.mCurrLen;
  }

  public float getCurrentSpanX()
  {
    return this.mCurrFingerDiffX;
  }

  public float getCurrentSpanY()
  {
    return this.mCurrFingerDiffY;
  }

  public long getEventTime()
  {
    return this.mCurrEvent.getEventTime();
  }

  public float getFocusX()
  {
    return this.mFocusX;
  }

  public float getFocusY()
  {
    return this.mFocusY;
  }

  public float getPreviousSpan()
  {
    if (this.mPrevLen == -1.0F)
    {
      float f1 = this.mPrevFingerDiffX;
      float f2 = this.mPrevFingerDiffY;
      this.mPrevLen = FloatMath.sqrt(f1 * f1 + f2 * f2);
    }
    return this.mPrevLen;
  }

  public float getPreviousSpanX()
  {
    return this.mPrevFingerDiffX;
  }

  public float getPreviousSpanY()
  {
    return this.mPrevFingerDiffY;
  }

  public float getScaleFactor()
  {
    if (this.mScaleFactor == -1.0F)
      this.mScaleFactor = (getCurrentSpan() / getPreviousSpan());
    return this.mScaleFactor;
  }

  public long getTimeDelta()
  {
    return this.mTimeDelta;
  }

  public void hardReset()
  {
    reset();
    this.mPrevFingerDiffX = 0.0F;
    this.mPrevFingerDiffY = 0.0F;
    this.mCurrFingerDiffX = 0.0F;
    this.mCurrFingerDiffY = 0.0F;
    this.mCurrLen = -1.0F;
    this.mPrevLen = -1.0F;
    this.mScaleFactor = -1.0F;
  }

  public boolean isInProgress()
  {
    return this.mGestureInProgress;
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    int i = paramMotionEvent.getActionMasked();
    if (i == 0)
      reset();
    int j = 1;
    if (this.mInvalidGesture)
      j = 0;
    while (true)
    {
      int k = j;
      return k;
      if (!this.mGestureInProgress)
        switch (i)
        {
        case 3:
        case 4:
        default:
          break;
        case 0:
          this.mActiveId0 = paramMotionEvent.getPointerId(0);
          this.mActive0MostRecent = true;
          break;
        case 1:
          reset();
          break;
        case 5:
          DisplayMetrics localDisplayMetrics = this.mContext.getResources().getDisplayMetrics();
          this.mRightSlopEdge = (localDisplayMetrics.widthPixels - this.mEdgeSlop);
          this.mBottomSlopEdge = (localDisplayMetrics.heightPixels - this.mEdgeSlop);
          if (this.mPrevEvent != null)
            this.mPrevEvent.recycle();
          this.mPrevEvent = MotionEvent.obtain(paramMotionEvent);
          this.mTimeDelta = 0L;
          int i24 = paramMotionEvent.getActionIndex();
          int i25 = paramMotionEvent.findPointerIndex(this.mActiveId0);
          this.mActiveId1 = paramMotionEvent.getPointerId(i24);
          int i26;
          int i27;
          if ((i25 < 0) || (i25 == i24))
          {
            if (i25 == i24)
            {
              i26 = -1;
              i25 = findNewActiveIndex(paramMotionEvent, i26, i25);
              this.mActiveId0 = paramMotionEvent.getPointerId(i25);
            }
          }
          else
          {
            this.mActive0MostRecent = false;
            setContext(paramMotionEvent);
            float f8 = this.mEdgeSlop;
            float f9 = this.mRightSlopEdge;
            float f10 = this.mBottomSlopEdge;
            float f11 = getRawX(paramMotionEvent, i25);
            float f12 = getRawY(paramMotionEvent, i25);
            float f13 = getRawX(paramMotionEvent, i24);
            float f14 = getRawY(paramMotionEvent, i24);
            if ((f11 >= f8) && (f12 >= f8) && (f11 <= f9) && (f12 <= f10))
              break label415;
            i27 = 1;
            if ((f13 >= f8) && (f14 >= f8) && (f13 <= f9) && (f14 <= f10))
              break label421;
          }
          for (int i28 = 1; ; i28 = 0)
          {
            if ((i27 == 0) || (i28 == 0))
              break label427;
            this.mFocusX = -1.0F;
            this.mFocusY = -1.0F;
            this.mSloppyGesture = true;
            break;
            i26 = this.mActiveId1;
            break label225;
            i27 = 0;
            break label341;
          }
          if (i27 != 0)
          {
            this.mFocusX = paramMotionEvent.getX(i24);
            this.mFocusY = paramMotionEvent.getY(i24);
            this.mSloppyGesture = true;
            continue;
          }
          if (i28 != 0)
          {
            this.mFocusX = paramMotionEvent.getX(i25);
            this.mFocusY = paramMotionEvent.getY(i25);
            this.mSloppyGesture = true;
            continue;
          }
          this.mSloppyGesture = false;
          this.mGestureInProgress = this.mListener.onScaleBegin(this);
          break;
        case 2:
          if (!this.mSloppyGesture)
            continue;
          float f1 = this.mEdgeSlop;
          float f2 = this.mRightSlopEdge;
          float f3 = this.mBottomSlopEdge;
          int i18 = paramMotionEvent.findPointerIndex(this.mActiveId0);
          int i19 = paramMotionEvent.findPointerIndex(this.mActiveId1);
          float f4 = getRawX(paramMotionEvent, i18);
          float f5 = getRawY(paramMotionEvent, i18);
          float f6 = getRawX(paramMotionEvent, i19);
          float f7 = getRawY(paramMotionEvent, i19);
          int i20;
          if ((f4 < f1) || (f5 < f1) || (f4 > f2) || (f5 > f3))
          {
            i20 = 1;
            if ((f6 >= f1) && (f7 >= f1) && (f6 <= f2) && (f7 <= f3))
              break label801;
          }
          for (int i21 = 1; ; i21 = 0)
          {
            if (i20 != 0)
            {
              int i23 = findNewActiveIndex(paramMotionEvent, this.mActiveId1, i18);
              if (i23 >= 0)
              {
                i18 = i23;
                this.mActiveId0 = paramMotionEvent.getPointerId(i23);
                getRawX(paramMotionEvent, i23);
                getRawY(paramMotionEvent, i23);
                i20 = 0;
              }
            }
            if (i21 != 0)
            {
              int i22 = findNewActiveIndex(paramMotionEvent, this.mActiveId0, i19);
              if (i22 >= 0)
              {
                i19 = i22;
                this.mActiveId1 = paramMotionEvent.getPointerId(i22);
                getRawX(paramMotionEvent, i22);
                getRawY(paramMotionEvent, i22);
                i21 = 0;
              }
            }
            if ((i20 == 0) || (i21 == 0))
              break label807;
            this.mFocusX = -1.0F;
            this.mFocusY = -1.0F;
            break;
            i20 = 0;
            break label627;
          }
          if (i20 != 0)
          {
            this.mFocusX = paramMotionEvent.getX(i19);
            this.mFocusY = paramMotionEvent.getY(i19);
            continue;
          }
          if (i21 != 0)
          {
            this.mFocusX = paramMotionEvent.getX(i18);
            this.mFocusY = paramMotionEvent.getY(i18);
            continue;
          }
          this.mSloppyGesture = false;
          this.mGestureInProgress = this.mListener.onScaleBegin(this);
          break;
        case 6:
          label225: label627: if (!this.mSloppyGesture)
            continue;
          label341: int i11 = paramMotionEvent.getPointerCount();
          label415: label421: label427: int i12 = paramMotionEvent.getActionIndex();
          label801: label807: int i13 = paramMotionEvent.getPointerId(i12);
          if (i11 > 2)
          {
            if (i13 == this.mActiveId0)
            {
              int i17 = findNewActiveIndex(paramMotionEvent, this.mActiveId1, i12);
              if (i17 < 0)
                continue;
              this.mActiveId0 = paramMotionEvent.getPointerId(i17);
              continue;
            }
            if (i13 != this.mActiveId1)
              continue;
            int i16 = findNewActiveIndex(paramMotionEvent, this.mActiveId0, i12);
            if (i16 < 0)
              continue;
            this.mActiveId1 = paramMotionEvent.getPointerId(i16);
            continue;
          }
          if (i13 == this.mActiveId0);
          int i15;
          for (int i14 = this.mActiveId1; ; i14 = this.mActiveId0)
          {
            i15 = paramMotionEvent.findPointerIndex(i14);
            if (i15 >= 0)
              break label1086;
            this.mInvalidGesture = true;
            Log.e("ScaleGestureDetector", "Invalid MotionEvent stream detected.", new Throwable());
            if (this.mGestureInProgress)
            {
              Log.d("IgScaleGestureDetector", "onScaleEnd");
              this.mListener.onScaleEnd(this);
            }
            k = 0;
            break;
          }
          label1086: this.mActiveId0 = paramMotionEvent.getPointerId(i15);
          this.mActive0MostRecent = true;
          this.mActiveId1 = -1;
          this.mFocusX = paramMotionEvent.getX(i15);
          this.mFocusY = paramMotionEvent.getY(i15);
          break;
        }
      switch (i)
      {
      case 4:
      default:
        break;
      case 1:
        reset();
        break;
      case 5:
        this.mListener.onScaleEnd(this);
        int i7 = this.mActiveId0;
        int i8 = this.mActiveId1;
        reset();
        this.mPrevEvent = MotionEvent.obtain(paramMotionEvent);
        int i9;
        if (this.mActive0MostRecent)
        {
          this.mActiveId0 = i7;
          this.mActiveId1 = paramMotionEvent.getPointerId(paramMotionEvent.getActionIndex());
          this.mActive0MostRecent = false;
          i9 = paramMotionEvent.findPointerIndex(this.mActiveId0);
          if ((i9 < 0) || (this.mActiveId0 == this.mActiveId1))
            if (this.mActiveId0 != this.mActiveId1)
              break label1329;
        }
        for (int i10 = -1; ; i10 = this.mActiveId1)
        {
          this.mActiveId0 = paramMotionEvent.getPointerId(findNewActiveIndex(paramMotionEvent, i10, i9));
          setContext(paramMotionEvent);
          this.mGestureInProgress = this.mListener.onScaleBegin(this);
          break;
          i7 = i8;
          break label1219;
        }
      case 6:
        int m = paramMotionEvent.getPointerCount();
        int n = paramMotionEvent.getActionIndex();
        int i1 = paramMotionEvent.getPointerId(n);
        int i2 = 0;
        if (m > 2)
          if (i1 == this.mActiveId0)
          {
            int i6 = findNewActiveIndex(paramMotionEvent, this.mActiveId1, n);
            if (i6 >= 0)
            {
              this.mListener.onScaleEnd(this);
              this.mActiveId0 = paramMotionEvent.getPointerId(i6);
              this.mActive0MostRecent = true;
              this.mPrevEvent = MotionEvent.obtain(paramMotionEvent);
              setContext(paramMotionEvent);
              this.mGestureInProgress = this.mListener.onScaleBegin(this);
              this.mPrevEvent.recycle();
              this.mPrevEvent = MotionEvent.obtain(paramMotionEvent);
              setContext(paramMotionEvent);
              if (i2 == 0)
                continue;
              setContext(paramMotionEvent);
              if (i1 != this.mActiveId0)
                break label1647;
            }
          }
        for (int i3 = this.mActiveId1; ; i3 = this.mActiveId0)
        {
          int i4 = paramMotionEvent.findPointerIndex(i3);
          this.mFocusX = paramMotionEvent.getX(i4);
          this.mFocusY = paramMotionEvent.getY(i4);
          this.mListener.onScaleEnd(this);
          reset();
          this.mActiveId0 = i3;
          this.mActive0MostRecent = true;
          break;
          i2 = 1;
          break label1446;
          if (i1 != this.mActiveId1)
            break label1446;
          int i5 = findNewActiveIndex(paramMotionEvent, this.mActiveId0, n);
          if (i5 >= 0)
          {
            this.mListener.onScaleEnd(this);
            this.mActiveId1 = paramMotionEvent.getPointerId(i5);
            this.mActive0MostRecent = false;
            this.mPrevEvent = MotionEvent.obtain(paramMotionEvent);
            setContext(paramMotionEvent);
            this.mGestureInProgress = this.mListener.onScaleBegin(this);
            break label1446;
          }
          i2 = 1;
          break label1446;
          i2 = 1;
          break label1466;
        }
      case 3:
        this.mListener.onScaleEnd(this);
        reset();
        break;
      case 2:
        label1219: label1647: setContext(paramMotionEvent);
        label1329: label1466: if ((this.mCurrPressure / this.mPrevPressure <= 0.67F) || (!this.mListener.onScale(this)))
          continue;
        label1446: this.mPrevEvent.recycle();
        this.mPrevEvent = MotionEvent.obtain(paramMotionEvent);
      }
    }
  }

  public static abstract interface OnScaleGestureListener
  {
    public abstract boolean onScale(IgScaleGestureDetector paramIgScaleGestureDetector);

    public abstract boolean onScaleBegin(IgScaleGestureDetector paramIgScaleGestureDetector);

    public abstract void onScaleEnd(IgScaleGestureDetector paramIgScaleGestureDetector);
  }

  public static class SimpleOnScaleGestureListener
    implements IgScaleGestureDetector.OnScaleGestureListener
  {
    public boolean onScale(IgScaleGestureDetector paramIgScaleGestureDetector)
    {
      return false;
    }

    public boolean onScaleBegin(IgScaleGestureDetector paramIgScaleGestureDetector)
    {
      return true;
    }

    public void onScaleEnd(IgScaleGestureDetector paramIgScaleGestureDetector)
    {
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.gl.IgScaleGestureDetector
 * JD-Core Version:    0.6.0
 */
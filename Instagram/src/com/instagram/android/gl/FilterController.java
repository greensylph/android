package com.instagram.android.gl;

import android.graphics.Bitmap;
import android.os.Handler;

public abstract class FilterController
{
  private void queueEventOnFilterView(Runnable paramRunnable)
  {
    getFilterView().queueEvent(paramRunnable);
  }

  public abstract FilterGLSurfaceView getFilterView();

  public void mirrorMasterTexture()
  {
    queueEventOnFilterView(new Runnable()
    {
      public void run()
      {
        NativeBridge.mirrorMasterTexture();
        FilterController.this.getFilterView().requestRender();
      }
    });
  }

  public void renderToBitmap(Handler paramHandler, RenderCallbacks paramRenderCallbacks, int paramInt)
  {
    queueEventOnFilterView(new Runnable(paramInt, paramHandler, paramRenderCallbacks)
    {
      public void run()
      {
        Bitmap localBitmap = NativeBridge.renderToBitmap(this.val$dimen, this.val$dimen);
        this.val$handler.post(new FilterController.7.1(this, localBitmap));
      }
    });
  }

  public void rotateMasterTexture()
  {
    queueEventOnFilterView(new Runnable()
    {
      public void run()
      {
        NativeBridge.rotateMasterTexture();
        FilterController.this.getFilterView().requestRender();
      }
    });
  }

  public void setBordersEnabled(boolean paramBoolean)
  {
    queueEventOnFilterView(new Runnable(paramBoolean)
    {
      public void run()
      {
        NativeBridge.setBordersEnabled(this.val$enabled);
        FilterController.this.getFilterView().requestRender();
      }
    });
  }

  public void setLuxEnabled(boolean paramBoolean)
  {
    queueEventOnFilterView(new Runnable(paramBoolean)
    {
      public void run()
      {
        NativeBridge.setLuxEnabled(this.val$enabled);
        FilterController.this.getFilterView().requestRender();
      }
    });
  }

  public void setTiltShiftEnabled(boolean paramBoolean)
  {
    queueEventOnFilterView(new Runnable(paramBoolean)
    {
      public void run()
      {
        NativeBridge.setTiltShiftEnabled(this.val$enabled);
        FilterController.this.getFilterView().requestRender();
      }
    });
  }

  public void setTiltShiftMode(int paramInt)
  {
    NativeBridge.setTiltShiftMode(paramInt);
  }

  public void setTiltShiftOrigin(float paramFloat1, float paramFloat2)
  {
    NativeBridge.setTiltShiftOrigin(paramFloat1, paramFloat2);
  }

  public void setTiltShiftRadius(float paramFloat)
  {
    NativeBridge.setTiltShiftRadius(paramFloat);
  }

  public void setTiltShiftTheta(float paramFloat)
  {
    NativeBridge.setTiltShiftTheta(paramFloat);
  }

  public void useFilter(int paramInt)
  {
    queueEventOnFilterView(new Runnable(paramInt)
    {
      public void run()
      {
        NativeBridge.useFilter(this.val$filterId);
        FilterController.this.getFilterView().requestRender();
      }
    });
  }

  public static abstract interface RenderCallbacks
  {
    public abstract void renderFinished(Bitmap paramBitmap);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.gl.FilterController
 * JD-Core Version:    0.6.0
 */
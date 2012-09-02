package com.instagram.camera;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class ShutterButton extends ImageView
{
  private OnShutterButtonListener mListener;
  private boolean mOldPressed;

  public ShutterButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  private void callShutterButtonFocus(boolean paramBoolean)
  {
    if (this.mListener != null)
      this.mListener.onShutterButtonFocus(paramBoolean);
  }

  protected void drawableStateChanged()
  {
    super.drawableStateChanged();
    boolean bool = isPressed();
    if (bool != this.mOldPressed)
    {
      if (bool)
        break label41;
      post(new Runnable(bool)
      {
        public void run()
        {
          ShutterButton.this.callShutterButtonFocus(this.val$pressed);
        }
      });
    }
    while (true)
    {
      this.mOldPressed = bool;
      return;
      label41: callShutterButtonFocus(bool);
    }
  }

  public boolean performClick()
  {
    boolean bool = super.performClick();
    if (this.mListener != null)
      this.mListener.onShutterButtonClick();
    return bool;
  }

  public void setOnShutterButtonListener(OnShutterButtonListener paramOnShutterButtonListener)
  {
    this.mListener = paramOnShutterButtonListener;
  }

  public static abstract interface OnShutterButtonListener
  {
    public abstract void onShutterButtonClick();

    public abstract void onShutterButtonFocus(boolean paramBoolean);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.camera.ShutterButton
 * JD-Core Version:    0.6.0
 */
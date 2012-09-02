package com.instagram.android.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.instagram.android.Log;

public class ActionBarCameraFlashButton extends ActionBarHighlightButton
{
  private static final String TAG = "ActionBarCameraFlashButton";
  private FlashMode mCurrentMode = FlashMode.OFF;
  private Drawable mFlashAutoDrawable;
  private Drawable mFlashOffDrawable;
  private Drawable mFlashOnDrawable;
  private FlashButtonOnClickListener mOnClickListener = null;

  public ActionBarCameraFlashButton(Context paramContext)
  {
    super(paramContext);
    init();
  }

  public ActionBarCameraFlashButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init();
  }

  private Drawable getDrawable(FlashMode paramFlashMode)
  {
    Drawable localDrawable;
    switch (3.$SwitchMap$com$instagram$android$widget$ActionBarCameraFlashButton$FlashMode[paramFlashMode.ordinal()])
    {
    default:
      localDrawable = this.mFlashOffDrawable;
    case 3:
    case 2:
    }
    while (true)
    {
      return localDrawable;
      localDrawable = this.mFlashOnDrawable;
      continue;
      localDrawable = this.mFlashAutoDrawable;
    }
  }

  private void init()
  {
    Resources localResources = getContext().getResources();
    this.mFlashOffDrawable = getButtonDrawable();
    this.mFlashOnDrawable = localResources.getDrawable(2130837528);
    this.mFlashAutoDrawable = localResources.getDrawable(2130837526);
    setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
    {
      public void onCheckedChanged(CompoundButton paramCompoundButton, boolean paramBoolean)
      {
        Log.d("ActionBarCameraFlashButton", "Test: " + paramBoolean);
      }
    });
    super.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        switch (ActionBarCameraFlashButton.3.$SwitchMap$com$instagram$android$widget$ActionBarCameraFlashButton$FlashMode[ActionBarCameraFlashButton.this.mCurrentMode.ordinal()])
        {
        default:
          ActionBarCameraFlashButton.this.showMode(ActionBarCameraFlashButton.FlashMode.OFF);
        case 1:
        case 2:
        }
        while (true)
        {
          if (ActionBarCameraFlashButton.this.mOnClickListener != null)
            ActionBarCameraFlashButton.this.mOnClickListener.onClick(paramView, ActionBarCameraFlashButton.this.mCurrentMode);
          return;
          ActionBarCameraFlashButton.this.showMode(ActionBarCameraFlashButton.FlashMode.AUTO);
          continue;
          ActionBarCameraFlashButton.this.showMode(ActionBarCameraFlashButton.FlashMode.ON);
        }
      }
    });
  }

  @Deprecated
  public void setOnClickListener(View.OnClickListener paramOnClickListener)
  {
    throw new RuntimeException("Use FlashButtonOnClickListener");
  }

  public void setOnClickListener(FlashButtonOnClickListener paramFlashButtonOnClickListener)
  {
    this.mOnClickListener = paramFlashButtonOnClickListener;
  }

  public void showMode(FlashMode paramFlashMode)
  {
    if (paramFlashMode == this.mCurrentMode);
    while (true)
    {
      return;
      setButtonDrawable(getDrawable(paramFlashMode));
      this.mCurrentMode = paramFlashMode;
    }
  }

  public static abstract interface FlashButtonOnClickListener
  {
    public abstract void onClick(View paramView, ActionBarCameraFlashButton.FlashMode paramFlashMode);
  }

  public static enum FlashMode
  {
    static
    {
      OFF = new FlashMode("OFF", 1);
      AUTO = new FlashMode("AUTO", 2);
      FlashMode[] arrayOfFlashMode = new FlashMode[3];
      arrayOfFlashMode[0] = ON;
      arrayOfFlashMode[1] = OFF;
      arrayOfFlashMode[2] = AUTO;
      $VALUES = arrayOfFlashMode;
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.widget.ActionBarCameraFlashButton
 * JD-Core Version:    0.6.0
 */
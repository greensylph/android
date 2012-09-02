package com.instagram.android.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

public class RefreshButton extends ViewSwitcher
{
  public RefreshButton(Context paramContext)
  {
    super(paramContext);
    init(paramContext);
  }

  public RefreshButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(paramContext);
  }

  protected Drawable getBackgroundDrawable()
  {
    return getContext().getResources().getDrawable(2130837556);
  }

  protected void init(Context paramContext)
  {
    ImageView localImageView1 = new ImageView(paramContext);
    Drawable localDrawable = paramContext.getResources().getDrawable(2130837540);
    localImageView1.setImageDrawable(localDrawable);
    localImageView1.setBackgroundDrawable(getBackgroundDrawable());
    addView(localImageView1);
    FrameLayout localFrameLayout = new FrameLayout(paramContext);
    localFrameLayout.setBackgroundDrawable(getBackgroundDrawable());
    ImageView localImageView2 = new ImageView(getContext());
    localImageView2.setImageDrawable(paramContext.getResources().getDrawable(2130837542));
    Animation localAnimation = AnimationUtils.loadAnimation(paramContext, 2130968585);
    localAnimation.reset();
    localImageView2.startAnimation(localAnimation);
    localFrameLayout.addView(localImageView2, new FrameLayout.LayoutParams(-2, -2, 17));
    addView(localFrameLayout, localDrawable.getIntrinsicWidth(), localDrawable.getIntrinsicHeight());
  }

  public boolean isClickable()
  {
    if (getDisplayedChild() == 0);
    for (int i = 1; ; i = 0)
      return i;
  }

  public boolean isEnabled()
  {
    return true;
  }

  protected void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if (!paramParcelable.getClass().equals(SavedState.class))
      super.onRestoreInstanceState(paramParcelable);
    while (true)
    {
      return;
      SavedState localSavedState = (SavedState)paramParcelable;
      super.onRestoreInstanceState(localSavedState.getSuperState());
      setDisplayedChild(localSavedState.displayChild);
    }
  }

  protected Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = new SavedState(super.onSaveInstanceState());
    localSavedState.displayChild = getDisplayedChild();
    return localSavedState;
  }

  public void setOnClickListener(View.OnClickListener paramOnClickListener)
  {
    getChildAt(0).setOnClickListener(paramOnClickListener);
  }

  private static class SavedState extends View.BaseSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new RefreshButton.SavedState.1();
    int displayChild;

    public SavedState(Parcel paramParcel)
    {
      super();
      this.displayChild = paramParcel.readInt();
    }

    public SavedState(Parcelable paramParcelable)
    {
      super();
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      paramParcel.writeInt(this.displayChild);
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.widget.RefreshButton
 * JD-Core Version:    0.6.0
 */
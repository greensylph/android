package com.instagram.android.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.util.AttributeSet;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.instagram.android.activity.MainTabActivity;

public class CustomToastView extends LinearLayout
{
  public static final String BROADCAST_TOAST = "com.instagram.android.activity.MainTabActivity.BROADCAST_TOAST";
  public static final int DELAY = 7000;
  public static final String EXTRA_BROADCAST_TOAST_COMMENTS = "com.instagram.android.activity.MainTabActivity.EXTRA_BROADCAST_TOAST_COMMENTS";
  public static final String EXTRA_BROADCAST_TOAST_LIKES = "com.instagram.android.activity.MainTabActivity.EXTRA_BROADCAST_TOAST_LIKES";
  public static final String EXTRA_BROADCAST_TOAST_RELATIONSHIPS = "com.instagram.android.activity.MainTabActivity.EXTRA_BROADCAST_TOAST_RELATIONSHIPS";
  private Runnable mFadeRunnable = new CustomToastView.1(this);
  private boolean mFadingOut;
  private Handler mHandler = new Handler();
  private BroadcastReceiver receiver = new CustomToastView.2(this);

  public CustomToastView(Context paramContext)
  {
    super(paramContext);
  }

  public CustomToastView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public CustomToastView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  private void fadeOut()
  {
    if ((getVisibility() == 8) || (this.mFadingOut));
    while (true)
    {
      return;
      this.mFadingOut = true;
      clearAnimation();
      AlphaAnimation localAlphaAnimation = new AlphaAnimation(1.0F, 0.0F);
      localAlphaAnimation.setDuration(500L);
      localAlphaAnimation.setAnimationListener(new CustomToastView.3(this));
      startAnimation(localAlphaAnimation);
    }
  }

  protected void onAttachedToWindow()
  {
    LocalBroadcastManager.getInstance(getContext()).registerReceiver(this.receiver, new IntentFilter("com.instagram.android.activity.MainTabActivity.BROADCAST_TOAST"));
    LocalBroadcastManager.getInstance(getContext()).registerReceiver(this.receiver, new IntentFilter("com.instagram.android.fragment.NewsFragment.BROADCAST_INBOX_SHOWN"));
    super.onAttachedToWindow();
  }

  protected void onDetachedFromWindow()
  {
    LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(this.receiver);
    this.mHandler.removeCallbacks(this.mFadeRunnable);
    super.onDetachedFromWindow();
  }

  public void show(int paramInt1, int paramInt2, int paramInt3)
  {
    if (!MainTabActivity.getShouldShowToast());
    while (true)
    {
      return;
      TextView localTextView1 = (TextView)findViewById(2131493101);
      TextView localTextView2 = (TextView)findViewById(2131493103);
      TextView localTextView3 = (TextView)findViewById(2131493105);
      localTextView1.setText(String.valueOf(paramInt1));
      localTextView2.setText(String.valueOf(paramInt2));
      localTextView3.setText(String.valueOf(paramInt3));
      AlphaAnimation localAlphaAnimation = new AlphaAnimation(0.0F, 0.0F);
      localAlphaAnimation.setFillAfter(true);
      localAlphaAnimation.setDuration(0L);
      localAlphaAnimation.setAnimationListener(new CustomToastView.4(this));
      startAnimation(localAlphaAnimation);
      this.mHandler.postDelayed(this.mFadeRunnable, 7000L);
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.widget.CustomToastView
 * JD-Core Version:    0.6.0
 */
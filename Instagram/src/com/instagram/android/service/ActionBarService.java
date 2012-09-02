package com.instagram.android.service;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.instagram.android.fragment.ActionBarConfigurer;
import com.instagram.android.widget.RefreshButton;

public class ActionBarService
{
  public static final String ACTION_BAR_BACK_CLICK = "com.instagram.android.service.action_bar_back_clicked";
  public static final String ACTION_BAR_CLICK = "com.instagram.android.service.action_bar_clicked";
  public static final String ACTION_BAR_REFRESH_CLICK = "com.instagram.android.service.action_bar_refresh_clicked";
  public static final String ACTION_BAR_SERVICE = "com.instagram.android.service.actionBarService";
  public static final String ACTION_BAR_UPDATED = "com.instagram.android.service.action_bar_updated";
  private ActionBarConfigurer mConfigurer;
  private Context mContext;
  private RefreshButton mRefreshButton;
  private View mView;

  public ActionBarService(Context paramContext)
  {
    this.mContext = paramContext;
  }

  private void actionBarChanged()
  {
    LocalBroadcastManager.getInstance(this.mContext).sendBroadcastSync(new Intent("com.instagram.android.service.action_bar_updated"));
  }

  public static ActionBarService getInstance(Context paramContext)
  {
    ActionBarService localActionBarService = (ActionBarService)paramContext.getApplicationContext().getSystemService("com.instagram.android.service.actionBarService");
    if (localActionBarService == null)
      throw new IllegalStateException("ActionBarService not available");
    return localActionBarService;
  }

  private void updateRefreshButton()
  {
    if (this.mRefreshButton != null)
    {
      if (!this.mConfigurer.isLoading())
        break label28;
      this.mRefreshButton.setDisplayedChild(1);
    }
    while (true)
    {
      return;
      label28: this.mRefreshButton.setDisplayedChild(0);
    }
  }

  public void attach(ActionBarConfigurer paramActionBarConfigurer)
  {
    this.mConfigurer = paramActionBarConfigurer;
    actionBarChanged();
  }

  public void configureActionBar(View paramView)
  {
    this.mView = paramView;
    paramView.setOnClickListener(new ActionBarService.1(this, paramView));
    View localView1 = paramView.findViewById(2131492875);
    int i;
    View localView2;
    int j;
    label90: ViewGroup localViewGroup;
    View localView3;
    if (this.mConfigurer.showBackButton())
    {
      i = 0;
      localView1.setVisibility(i);
      paramView.findViewById(2131492875).setOnClickListener(new ActionBarService.2(this, paramView));
      localView2 = paramView.findViewById(2131492878);
      if (localView2 != null)
      {
        if (!this.mConfigurer.showRefreshButton())
          break label203;
        j = 0;
        localView2.setVisibility(j);
        localView2.setOnClickListener(new ActionBarService.3(this, paramView));
      }
      localViewGroup = (ViewGroup)paramView.findViewById(2131492877);
      localViewGroup.removeAllViews();
      localView3 = this.mConfigurer.customTitleView(LayoutInflater.from(paramView.getContext()), localViewGroup);
      if (localView3 != null)
        break label210;
      ((TextView)paramView.findViewById(2131492876)).setText(this.mConfigurer.getTitle());
      paramView.findViewById(2131492876).setVisibility(0);
    }
    while (true)
    {
      this.mRefreshButton = ((RefreshButton)localView2);
      updateRefreshButton();
      return;
      i = 8;
      break;
      label203: j = 8;
      break label90;
      label210: paramView.findViewById(2131492876).setVisibility(8);
      localViewGroup.addView(localView3);
    }
  }

  public void forceUpdate()
  {
    if (this.mView != null)
      configureActionBar(this.mView);
  }

  public void setIsLoading(boolean paramBoolean)
  {
    RefreshButton localRefreshButton;
    if (this.mRefreshButton != null)
    {
      localRefreshButton = this.mRefreshButton;
      if (!paramBoolean)
        break label24;
    }
    label24: for (int i = 1; ; i = 0)
    {
      localRefreshButton.setDisplayedChild(i);
      return;
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.service.ActionBarService
 * JD-Core Version:    0.6.0
 */
package com.instagram.android.activity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import com.instagram.android.fragment.ActionBarConfigurer.ActionBarConfigurerFactory;
import com.instagram.android.model.Comment;
import com.instagram.android.service.ActionBarService;
import com.instagram.android.service.ClickManager;

public abstract class BaseFragmentActivity extends FragmentActivity
{
  public static final String EXTRA_PREVIOUS_SCREEN_NAME = "com.instagram.android.activity.ActivityInTab.EXTRA_PREVIOUS_SCREEN_NAME";
  private BroadcastReceiver mActionBarService = new BaseFragmentActivity.2(this);
  private BroadcastReceiver mBackButtonReceiver = new BaseFragmentActivity.1(this);
  private Handler mHandler = new Handler();
  private BroadcastReceiver mHashLinkReceiver = new BaseFragmentActivity.4(this);
  private BroadcastReceiver mUserLinkReceiver = new BaseFragmentActivity.3(this);

  private String getPreviousScreenName()
  {
    String str = null;
    if ((getIntent() != null) && (getIntent().getExtras() != null))
      str = getIntent().getExtras().getString("com.instagram.android.activity.ActivityInTab.EXTRA_PREVIOUS_SCREEN_NAME");
    return str;
  }

  public void configureActionBar()
  {
    Fragment localFragment = getSupportFragmentManager().findFragmentById(2131492901);
    if (localFragment != null)
    {
      BaseFragmentActivity.6 local6 = new BaseFragmentActivity.6(this, ((ActionBarConfigurer.ActionBarConfigurerFactory)localFragment).getActionBarConfigurer());
      ActionBarService.getInstance(this).attach(local6);
    }
  }

  protected abstract void initializeStartingFragment();

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903053);
    initializeStartingFragment();
    getSupportFragmentManager().addOnBackStackChangedListener(new BaseFragmentActivity.5(this));
  }

  protected void onPause()
  {
    super.onPause();
    LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mBackButtonReceiver);
    LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mActionBarService);
    LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mUserLinkReceiver);
    LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mHashLinkReceiver);
    ClickManager.getInstance().setFragmentManager(null);
    ClickManager.getInstance().setCurrentActivity(null);
    ClickManager.getInstance().setHandler(null);
  }

  protected void onResume()
  {
    super.onResume();
    LocalBroadcastManager.getInstance(this).registerReceiver(this.mBackButtonReceiver, new IntentFilter("com.instagram.android.service.action_bar_back_clicked"));
    LocalBroadcastManager.getInstance(this).registerReceiver(this.mActionBarService, new IntentFilter("com.instagram.android.service.action_bar_updated"));
    LocalBroadcastManager.getInstance(this).registerReceiver(this.mUserLinkReceiver, new IntentFilter(Comment.BROADCAST_USER_NAME_LINK_CLICKED));
    LocalBroadcastManager.getInstance(this).registerReceiver(this.mHashLinkReceiver, new IntentFilter(Comment.BROADCAST_HASH_TAG_CLICKED));
    configureActionBar();
    ActionBarService.getInstance(this).configureActionBar(findViewById(2131492874));
    ClickManager.getInstance().setFragmentManager(getSupportFragmentManager());
    ClickManager.getInstance().setCurrentActivity(this);
    ClickManager.getInstance().setHandler(this.mHandler);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.activity.BaseFragmentActivity
 * JD-Core Version:    0.6.0
 */
package com.instagram.android.activity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import com.instagram.android.fragment.ActionBarConfigurer.ActionBarConfigurerFactory;
import com.instagram.android.fragment.LandingPageFragment;
import com.instagram.android.service.ActionBarService;
import com.instagram.android.service.ClickManager;

public class SignedOutFragmentLayout extends FragmentActivity
{
  public static final String ARGUMENT_BRANDING = "branding";
  public static boolean overrideBack = false;
  private BroadcastReceiver mBackButtonReceiver = new SignedOutFragmentLayout.1(this);
  private Handler mHandler = new Handler();

  public void configureActionBar()
  {
    Fragment localFragment = getSupportFragmentManager().findFragmentById(2131492901);
    if (localFragment != null)
      ActionBarService.getInstance(this).attach(((ActionBarConfigurer.ActionBarConfigurerFactory)localFragment).getActionBarConfigurer());
  }

  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
  }

  public void onAttachFragment(Fragment paramFragment)
  {
    super.onAttachFragment(paramFragment);
  }

  public void onBackPressed()
  {
    if (overrideBack);
    while (true)
    {
      return;
      super.onBackPressed();
    }
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903056);
    if (getSupportFragmentManager().findFragmentById(2131492901) == null)
    {
      Bundle localBundle = new Bundle();
      localBundle.putBoolean("branding", true);
      LandingPageFragment localLandingPageFragment = new LandingPageFragment();
      localLandingPageFragment.setArguments(localBundle);
      FragmentTransaction localFragmentTransaction = getSupportFragmentManager().beginTransaction();
      localFragmentTransaction.replace(2131492901, localLandingPageFragment);
      localFragmentTransaction.commit();
    }
    getSupportFragmentManager().addOnBackStackChangedListener(new SignedOutFragmentLayout.2(this));
  }

  protected void onPause()
  {
    super.onPause();
    LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mBackButtonReceiver);
    ClickManager.getInstance().setFragmentManager(null);
    ClickManager.getInstance().setCurrentActivity(null);
    ClickManager.getInstance().setHandler(null);
  }

  protected void onResume()
  {
    super.onResume();
    configureActionBar();
    LocalBroadcastManager.getInstance(this).registerReceiver(this.mBackButtonReceiver, new IntentFilter("com.instagram.android.service.action_bar_back_clicked"));
    ClickManager.getInstance().setFragmentManager(getSupportFragmentManager());
    ClickManager.getInstance().setCurrentActivity(this);
    ClickManager.getInstance().setHandler(this.mHandler);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.activity.SignedOutFragmentLayout
 * JD-Core Version:    0.6.0
 */
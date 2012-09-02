package com.instagram.android.activity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import com.instagram.android.fragment.FeedFragment;
import com.instagram.android.fragment.HashtagFeedFragment;
import com.instagram.android.fragment.MainFeedFragment;
import com.instagram.android.fragment.NewsFragment;
import com.instagram.android.fragment.PopularAltFragment;
import com.instagram.android.fragment.SelfFragment;
import com.instagram.android.fragment.UserDetailFragment;
import com.instagram.util.FragmentUtil;

public class ActivityInTab extends BaseFragmentActivity
{
  public static final String EXTRA_STARTING_FRAGMENT = "com.instagram.extra.EXTRA_STARTING_FRAGMENT";
  public static final String NEW_FRAGMENT_ARGUMENT_ID = "id";
  public static final String NEW_FRAGMENT_SCREEN_ARGUMENT = "screen";
  public static final String STARTING_ARGUMENT_SHORT_URL = "shortUrl";
  public static final String STARTING_ARGUMENT_USERID = "userid";
  public static final int STARTING_FRAGMENT_FEED = 0;
  public static final int STARTING_FRAGMENT_NEWS = 3;
  public static final int STARTING_FRAGMENT_POPULAR = 1;
  public static final int STARTING_FRAGMENT_PROFILE = 4;
  public static final int STARTING_FRAGMENT_SHARE = 2;
  private static boolean sPopToRootFlag = false;
  private static Bundle sStartingArgumentsBundle = null;
  private BroadcastReceiver mCurrentTabPressedReceiver = new ActivityInTab.1(this);
  private String popToNamedTransaction = null;

  private Fragment getStartingFragment(int paramInt)
  {
    Object localObject;
    switch (paramInt)
    {
    case 2:
    default:
      localObject = null;
    case 1:
    case 3:
    case 4:
    case 0:
    }
    while (true)
    {
      return localObject;
      localObject = new PopularAltFragment();
      continue;
      localObject = new NewsFragment();
      continue;
      localObject = new SelfFragment();
      Bundle localBundle = new Bundle();
      localBundle.putBoolean("com.instagram.android.fragment.UserDetailFragment.EXTRA_CURRENT_USER", true);
      ((Fragment)localObject).setArguments(localBundle);
      continue;
      localObject = new MainFeedFragment();
    }
  }

  public static void handleStartingArgumentsBundle(FragmentManager paramFragmentManager, Bundle paramBundle)
  {
    Bundle localBundle = new Bundle();
    Object localObject;
    if (paramBundle.getString("shortUrl") != null)
    {
      localObject = new FeedFragment();
      localBundle.putString("com.instagram.android.fragment.ARGUMENTS_KEY_SHORT_URL", paramBundle.getString("shortUrl"));
      localBundle.putBoolean("noBackStack", true);
    }
    while (true)
    {
      FragmentUtil.navigateTo(paramFragmentManager, (Fragment)localObject, localBundle);
      return;
      if (paramBundle.get("screen").equals("media"))
      {
        localObject = new FeedFragment();
        localBundle.putString("com.instagram.android.fragment.ARGUMENTS_KEY_EXTRA_MEDIA_ID", paramBundle.getString("id"));
        localBundle.putBoolean("com.instagram.android.fragment.ARGUMENTS_KEY_LOAD_FROM_NETWORK", true);
        continue;
      }
      if (paramBundle.get("screen").equals("user"))
      {
        localObject = new UserDetailFragment();
        localBundle.putString("com.instagram.android.fragment.UserDetailFragment.EXTRA_USER_NAME", paramBundle.getString("id"));
        continue;
      }
      if (paramBundle.get("screen").equals("userid"))
      {
        localObject = new UserDetailFragment();
        localBundle.putString("com.instagram.android.fragment.UserDetailFragment.EXTRA_USER_ID", paramBundle.getString("id"));
        continue;
      }
      if (!paramBundle.get("screen").equals("hashtag"))
        break;
      localObject = new HashtagFeedFragment();
      localBundle.putString("com.instagram.android.fragment.HashtagFeedFragment.ARGUMENT_TAG_NAME", paramBundle.getString("id"));
    }
    throw new RuntimeException("Received unknown starting bundle type");
  }

  public static void setStartingArgumentsAndPopToRootFlag(Bundle paramBundle, boolean paramBoolean)
  {
    sPopToRootFlag = paramBoolean;
    sStartingArgumentsBundle = paramBundle;
  }

  protected String getTabName()
  {
    return getIntent().getExtras().getString("com.instagram.android.activity.EXTRA_TAB_TAG_NAME");
  }

  protected void initializeStartingFragment()
  {
    int i = getIntent().getExtras().getInt("com.instagram.extra.EXTRA_STARTING_FRAGMENT");
    if (getSupportFragmentManager().findFragmentById(2131492901) == null)
    {
      FragmentTransaction localFragmentTransaction = getSupportFragmentManager().beginTransaction();
      localFragmentTransaction.replace(2131492901, getStartingFragment(i));
      localFragmentTransaction.commit();
    }
  }

  public void onBackPressed()
  {
    if (getSupportFragmentManager().getBackStackEntryCount() == 0)
    {
      Intent localIntent = new Intent("com.instagram.android.activity.BROADCAST_BACK_PUSHED");
      localIntent.putExtra("com.instagram.android.activity.EXTRA_TAB_TAG_NAME", getTabName());
      LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(localIntent);
    }
    while (true)
    {
      return;
      super.onBackPressed();
    }
  }

  protected void onPause()
  {
    LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mCurrentTabPressedReceiver);
    super.onPause();
  }

  protected void onPostResume()
  {
    super.onPostResume();
    if (takePopToRoot())
      popToRoot();
    LocalBroadcastManager.getInstance(this).registerReceiver(this.mCurrentTabPressedReceiver, new IntentFilter("com.instagram.android.activity.BROADCAST_POP_TO_ROOT"));
    Bundle localBundle = takeStartingArgumentsBundle();
    if (localBundle != null)
      handleStartingArgumentsBundle(getSupportFragmentManager(), localBundle);
  }

  public void popToRoot()
  {
    getSupportFragmentManager().popBackStackImmediate(0, 1);
  }

  public void setPopToNamedTransaction(String paramString)
  {
    this.popToNamedTransaction = paramString;
  }

  protected boolean takePopToRoot()
  {
    int i = 0;
    if (sPopToRootFlag)
    {
      sPopToRootFlag = false;
      i = 1;
    }
    return i;
  }

  protected Bundle takeStartingArgumentsBundle()
  {
    Bundle localBundle = sStartingArgumentsBundle;
    sStartingArgumentsBundle = null;
    return localBundle;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.activity.ActivityInTab
 * JD-Core Version:    0.6.0
 */
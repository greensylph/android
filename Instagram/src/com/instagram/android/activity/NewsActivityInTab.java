package com.instagram.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.ListView;
import com.instagram.android.Log;

public class NewsActivityInTab extends ActivityInTab
{
  public static final String EXTRA_ACTION_VEW_SHORT_URL = "com.instagram.android.activity.NewsActivityInTab.EXTRA_ACTION_VIEW_SHORT_URL";
  public static final String EXTRA_NEWS_LAUNCH_BUNDLE = "com.instagram.android.activity.NewsActivityInTab.EXTRA_NEWS_LAUNCH_BUNDLE";
  private static final String LOG_TAG = "NewsActivityInTab";
  private static boolean loadInboxFlag = false;

  public static void setLoadInboxFlag()
  {
    loadInboxFlag = true;
  }

  public static boolean takeLoadInboxFlag()
  {
    int i = 0;
    if (loadInboxFlag)
    {
      loadInboxFlag = false;
      i = 1;
    }
    return i;
  }

  protected void onPostResume()
  {
    super.onPostResume();
    if (takeLoadInboxFlag())
    {
      Log.d("NewsActivityInTab", "Resuming, but the news should reload due to push notification");
      LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent("com.instagram.android.receiver.C2DMReceiver.NOTIFICATION_RECEIVED_BROADCAST_PROXY"));
    }
  }

  public void popToRoot()
  {
    super.popToRoot();
    if (getIntent().getExtras().getString("com.instagram.android.activity.EXTRA_TAB_TAG_NAME").equals("news"));
    while (true)
    {
      return;
      ((ListFragment)(ListFragment)getSupportFragmentManager().findFragmentById(2131492901)).getListView().setSelection(0);
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.activity.NewsActivityInTab
 * JD-Core Version:    0.6.0
 */
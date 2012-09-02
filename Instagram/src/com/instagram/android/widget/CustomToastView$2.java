package com.instagram.android.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

class CustomToastView$2 extends BroadcastReceiver
{
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    if (paramIntent.getAction().equals("com.instagram.android.activity.MainTabActivity.BROADCAST_TOAST"))
      this.this$0.show(paramIntent.getExtras().getInt("com.instagram.android.activity.MainTabActivity.EXTRA_BROADCAST_TOAST_COMMENTS"), paramIntent.getExtras().getInt("com.instagram.android.activity.MainTabActivity.EXTRA_BROADCAST_TOAST_LIKES"), paramIntent.getExtras().getInt("com.instagram.android.activity.MainTabActivity.EXTRA_BROADCAST_TOAST_RELATIONSHIPS"));
    while (true)
    {
      return;
      if (paramIntent.getAction().equals("com.instagram.android.fragment.NewsFragment.BROADCAST_INBOX_SHOWN"))
      {
        CustomToastView.access$000(this.this$0);
        continue;
      }
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.widget.CustomToastView.2
 * JD-Core Version:    0.6.0
 */
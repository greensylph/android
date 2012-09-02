package com.instagram.android.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.instagram.android.service.ActionBarService;

class BaseFragmentActivity$2 extends BroadcastReceiver
{
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    ActionBarService.getInstance(paramContext).configureActionBar(this.this$0.findViewById(2131492874));
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.activity.BaseFragmentActivity.2
 * JD-Core Version:    0.6.0
 */
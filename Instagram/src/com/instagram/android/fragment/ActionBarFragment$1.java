package com.instagram.android.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.instagram.android.service.ActionBarService;

class ActionBarFragment$1 extends BroadcastReceiver
{
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    ActionBarService.getInstance(paramContext).configureActionBar(this.this$0.getView().findViewById(2131492874));
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.ActionBarFragment.1
 * JD-Core Version:    0.6.0
 */
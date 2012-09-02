package com.instagram.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import com.instagram.android.model.User;
import com.instagram.android.service.AutoCompleteHashtagService;
import com.instagram.android.service.AutoCompleteUserService;

class InstagramApplication$1 extends BroadcastReceiver
{
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    if (paramIntent.getBooleanExtra("loggedin", false))
    {
      this.this$0.startService(new Intent(paramContext, AutoCompleteUserService.class));
      this.this$0.startService(new Intent(paramContext, AutoCompleteHashtagService.class));
      LocalBroadcastManager.getInstance(paramContext).registerReceiver(InstagramApplication.access$000(this.this$0), new IntentFilter(User.getUserFollowUpdateBroadcastId(paramIntent.getExtras().getString("userid"))));
    }
    while (true)
    {
      return;
      LocalBroadcastManager.getInstance(paramContext).unregisterReceiver(InstagramApplication.access$000(this.this$0));
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.InstagramApplication.1
 * JD-Core Version:    0.6.0
 */
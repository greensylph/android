package com.instagram.android.service;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.View.OnClickListener;

class ActionBarService$3
  implements View.OnClickListener
{
  public void onClick(View paramView)
  {
    LocalBroadcastManager.getInstance(this.val$view.getContext()).sendBroadcastSync(new Intent("com.instagram.android.service.action_bar_refresh_clicked"));
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.service.ActionBarService.3
 * JD-Core Version:    0.6.0
 */
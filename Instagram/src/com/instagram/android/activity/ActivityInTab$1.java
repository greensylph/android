package com.instagram.android.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import com.instagram.android.fragment.ScrollToTopFragment.ScrollToTopFragment;
import com.instagram.android.fragment.UserDetailFragment;

class ActivityInTab$1 extends BroadcastReceiver
{
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    if (paramIntent.getExtras().getString("com.instagram.android.activity.EXTRA_TAB_TAG_NAME").equals(this.this$0.getTabName()))
    {
      if (this.this$0.getSupportFragmentManager().getBackStackEntryCount() <= 0)
        break label43;
      this.this$0.popToRoot();
    }
    while (true)
    {
      return;
      label43: Fragment localFragment = this.this$0.getSupportFragmentManager().findFragmentById(2131492901);
      if ((localFragment instanceof ScrollToTopFragment))
        ((ScrollToTopFragment)localFragment).scrollToTop();
      if (!(localFragment instanceof UserDetailFragment))
        continue;
      ((UserDetailFragment)localFragment).executeRequest();
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.activity.ActivityInTab.1
 * JD-Core Version:    0.6.0
 */
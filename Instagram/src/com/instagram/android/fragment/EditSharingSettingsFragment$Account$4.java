package com.instagram.android.fragment;

import android.content.Context;
import com.instagram.tumblr.TumblrAccount;

 enum EditSharingSettingsFragment$Account$4
{
  public String getName(Context paramContext)
  {
    return null;
  }

  public int getResId()
  {
    return 2131492946;
  }

  public boolean isConnected(Context paramContext)
  {
    return TumblrAccount.isConfigured(paramContext);
  }

  public void unlink(Context paramContext)
  {
    TumblrAccount.remove(paramContext);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.EditSharingSettingsFragment.Account.4
 * JD-Core Version:    0.6.0
 */
package com.instagram.android.fragment;

import android.content.Context;
import com.instagram.twitter.TwitterAccount;

 enum EditSharingSettingsFragment$Account$2
{
  public String getName(Context paramContext)
  {
    return null;
  }

  public int getResId()
  {
    return 2131492944;
  }

  public boolean isConnected(Context paramContext)
  {
    return TwitterAccount.isConfigured(paramContext);
  }

  public void unlink(Context paramContext)
  {
    TwitterAccount.remove(paramContext, true);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.EditSharingSettingsFragment.Account.2
 * JD-Core Version:    0.6.0
 */
package com.instagram.android.fragment;

import android.content.Context;
import com.facebook.android.Facebook;
import com.instagram.facebook.FacebookAccount;

 enum EditSharingSettingsFragment$Account$1
{
  public String getName(Context paramContext)
  {
    return null;
  }

  public int getResId()
  {
    return 2131492940;
  }

  public boolean isConnected(Context paramContext)
  {
    return FacebookAccount.getFacebook().isSessionValid();
  }

  public void unlink(Context paramContext)
  {
    FacebookAccount.deleteCredentials(true);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.EditSharingSettingsFragment.Account.1
 * JD-Core Version:    0.6.0
 */
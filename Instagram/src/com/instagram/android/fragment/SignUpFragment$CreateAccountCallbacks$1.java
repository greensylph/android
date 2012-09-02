package com.instagram.android.fragment;

import android.os.Bundle;
import com.instagram.android.receiver.C2DMReceiver;
import com.instagram.facebook.FacebookAccount;
import com.instagram.twitter.TwitterAccount;
import com.instagram.util.FragmentUtil;

class SignUpFragment$CreateAccountCallbacks$1
  implements Runnable
{
  public void run()
  {
    Bundle localBundle = new Bundle();
    localBundle.putBoolean("com.instagram.android.fragment.FindFriendsFragment.ARGUMENT_IS_SIGN_UP_FLOW", true);
    localBundle.putBoolean("noBackStack", true);
    FragmentUtil.navigateTo(this.this$1.this$0.getFragmentManager(), new FindFriendsFragment(), localBundle);
    C2DMReceiver.refreshAppC2DMRegistrationState(this.this$1.this$0.getActivity());
    if (FacebookAccount.hasQueuedStoreTokenRequest())
      FacebookAccount.performStoreTokenRequest();
    if (TwitterAccount.hasQueuedStoreTokenRequest())
      TwitterAccount.performStoreTokenRequest();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.SignUpFragment.CreateAccountCallbacks.1
 * JD-Core Version:    0.6.0
 */
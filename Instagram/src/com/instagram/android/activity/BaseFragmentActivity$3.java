package com.instagram.android.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.instagram.android.fragment.UserDetailFragment;
import com.instagram.android.model.Comment;
import com.instagram.util.FragmentUtil;

class BaseFragmentActivity$3 extends BroadcastReceiver
{
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    UserDetailFragment localUserDetailFragment = new UserDetailFragment();
    Bundle localBundle = new Bundle();
    localBundle.putString("com.instagram.android.fragment.UserDetailFragment.EXTRA_USER_NAME", paramIntent.getExtras().getString(Comment.BROADCAST_EXTRA_USER_NAME));
    FragmentUtil.navigateTo(this.this$0.getSupportFragmentManager(), localUserDetailFragment, localBundle);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.activity.BaseFragmentActivity.3
 * JD-Core Version:    0.6.0
 */
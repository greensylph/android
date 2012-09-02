package com.instagram.android.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.instagram.android.fragment.HashtagFeedFragment;
import com.instagram.android.model.Comment;

class BaseFragmentActivity$4 extends BroadcastReceiver
{
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    HashtagFeedFragment.startFragment(this.this$0, paramIntent.getExtras().getString(Comment.BROADCAST_HASH_TAG_NAME), this.this$0.getSupportFragmentManager());
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.activity.BaseFragmentActivity.4
 * JD-Core Version:    0.6.0
 */
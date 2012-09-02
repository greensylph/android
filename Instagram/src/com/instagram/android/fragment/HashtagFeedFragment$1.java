package com.instagram.android.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.instagram.android.adapter.SimpleHeaderFeedAdapter;

class HashtagFeedFragment$1 extends BroadcastReceiver
{
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    this.this$0.getAdapter().notifyDataSetChanged();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.HashtagFeedFragment.1
 * JD-Core Version:    0.6.0
 */
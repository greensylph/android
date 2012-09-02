package com.instagram.android.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.instagram.android.adapter.CommentThreadAdapter;

class CommentThreadFragment$1 extends BroadcastReceiver
{
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    if (CommentThreadFragment.access$000(this.this$0) != null)
      CommentThreadFragment.access$100(this.this$0).setMedia(CommentThreadFragment.access$000(this.this$0));
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.CommentThreadFragment.1
 * JD-Core Version:    0.6.0
 */
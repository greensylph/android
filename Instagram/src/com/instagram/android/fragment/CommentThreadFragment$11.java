package com.instagram.android.fragment;

import android.os.Bundle;
import com.instagram.android.listener.UserLinkClickListener;
import com.instagram.android.model.User;
import com.instagram.util.FragmentUtil;

class CommentThreadFragment$11
  implements UserLinkClickListener
{
  public void onClick(User paramUser)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("com.instagram.android.fragment.UserDetailFragment.EXTRA_USER_ID", paramUser.getId());
    FragmentUtil.navigateTo(this.this$0.getFragmentManager(), new UserDetailFragment(), localBundle);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.CommentThreadFragment.11
 * JD-Core Version:    0.6.0
 */
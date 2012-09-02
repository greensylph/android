package com.instagram.android.service;

import com.instagram.android.Log;
import com.instagram.android.listener.UserLinkClickListener;
import com.instagram.android.model.User;

class ClickManager$1
  implements UserLinkClickListener
{
  public void onClick(User paramUser)
  {
    if (ClickManager.access$000(this.this$0) != null)
      ClickManager.access$000(this.this$0).onClick(paramUser);
    while (true)
    {
      return;
      Log.d("ClickManager", "Link clicked without any listener");
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.service.ClickManager.1
 * JD-Core Version:    0.6.0
 */
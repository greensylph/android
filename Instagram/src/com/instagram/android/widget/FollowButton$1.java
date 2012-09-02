package com.instagram.android.widget;

import android.support.v4.app.LoaderManager;
import android.view.View;
import android.view.View.OnClickListener;
import com.instagram.android.model.User;

class FollowButton$1
  implements View.OnClickListener
{
  public void onClick(View paramView)
  {
    this.this$0.setEnabled(false);
    this.val$user.toggleFollowStatus(this.this$0.getContext(), this.val$loaderManager);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.widget.FollowButton.1
 * JD-Core Version:    0.6.0
 */
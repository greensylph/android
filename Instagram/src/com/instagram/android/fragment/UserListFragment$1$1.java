package com.instagram.android.fragment;

import android.app.Dialog;
import android.view.View;
import android.view.View.OnClickListener;
import com.instagram.android.adapter.UserListAdapter;
import com.instagram.android.widget.IgDialogBuilder;
import java.util.List;

class UserListFragment$1$1
  implements View.OnClickListener
{
  public void onClick(View paramView)
  {
    UserListFragment.1.1.1 local1 = new UserListFragment.1.1.1(this);
    if (UserListFragment.access$200(this.this$1.this$0).getUsers().size() > 50)
      new IgDialogBuilder(this.this$1.this$0.getActivity()).setMessage(2131231016).setPositiveButton(2131230978, local1).setCancelable(true).setNegativeButton(2131230921, new UserListFragment.1.1.2(this)).create().show();
    while (true)
    {
      return;
      UserListFragment.access$400(this.this$1.this$0, this.val$view);
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.UserListFragment.1.1
 * JD-Core Version:    0.6.0
 */
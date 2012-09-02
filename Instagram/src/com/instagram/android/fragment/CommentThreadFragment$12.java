package com.instagram.android.fragment;

import android.app.Dialog;
import com.instagram.android.model.Comment;
import com.instagram.android.widget.IgDialogBuilder;

class CommentThreadFragment$12
  implements Runnable
{
  public void run()
  {
    if (this.this$0.getActivity() != null)
      new IgDialogBuilder(this.this$0.getActivity()).setMessage(this.val$optionalErrorMessageFromServer).setPositiveButton(2131231027, new CommentThreadFragment.12.2(this)).setNegativeButton(2131230986, new CommentThreadFragment.12.1(this)).create().show();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.CommentThreadFragment.12
 * JD-Core Version:    0.6.0
 */
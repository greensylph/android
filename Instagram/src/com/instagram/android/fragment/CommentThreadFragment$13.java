package com.instagram.android.fragment;

import android.app.Dialog;
import com.instagram.android.widget.IgDialogBuilder;

class CommentThreadFragment$13
  implements Runnable
{
  public void run()
  {
    if (this.this$0.getActivity() != null)
    {
      IgDialogBuilder localIgDialogBuilder = new IgDialogBuilder(this.this$0.getActivity());
      CommentThreadFragment localCommentThreadFragment = this.this$0;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = this.this$0.getString(2131230975);
      localIgDialogBuilder.setMessage(localCommentThreadFragment.getString(2131230974, arrayOfObject)).setPositiveButton(2131230955, new CommentThreadFragment.13.1(this)).create().show();
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.CommentThreadFragment.13
 * JD-Core Version:    0.6.0
 */
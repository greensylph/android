package com.instagram.android.fragment;

import com.instagram.android.adapter.CommentThreadAdapter;

class CommentThreadFragment$5
  implements Runnable
{
  public void run()
  {
    if ((this.this$0.getView() != null) && (this.this$0.getListView() != null))
      this.this$0.setSelection(-1 + CommentThreadFragment.access$100(this.this$0).getCount());
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.CommentThreadFragment.5
 * JD-Core Version:    0.6.0
 */
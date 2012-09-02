package com.instagram.android.fragment;

import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

class CommentThreadFragment$6
  implements TextView.OnEditorActionListener
{
  public boolean onEditorAction(TextView paramTextView, int paramInt, KeyEvent paramKeyEvent)
  {
    if ((paramInt == 4) && (CommentThreadFragment.access$300(this.this$0)))
      CommentThreadFragment.access$600(this.this$0, paramTextView);
    return true;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.CommentThreadFragment.6
 * JD-Core Version:    0.6.0
 */
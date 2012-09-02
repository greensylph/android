package com.instagram.android.fragment;

import android.database.DataSetObserver;
import com.instagram.android.service.ActionBarService;

class CommentThreadFragment$3 extends DataSetObserver
{
  public void onChanged()
  {
    ActionBarService.getInstance(this.this$0.getActivity()).forceUpdate();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.CommentThreadFragment.3
 * JD-Core Version:    0.6.0
 */
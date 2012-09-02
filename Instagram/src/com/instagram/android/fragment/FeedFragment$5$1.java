package com.instagram.android.fragment;

import com.instagram.android.adapter.FeedAdapter;

class FeedFragment$5$1
  implements Runnable
{
  public void run()
  {
    if (this.this$1.this$0.getView() != null)
    {
      this.this$1.this$0.getAdapter().setStickyScrollingEnabled(true);
      this.this$1.this$0.getAdapter().onScroll(this.this$1.this$0.getListView(), 0, 0, 0);
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.FeedFragment.5.1
 * JD-Core Version:    0.6.0
 */
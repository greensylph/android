package com.instagram.android.fragment;

import android.view.View;
import android.view.animation.AnimationUtils;
import com.instagram.android.service.AppContext;

class SearchTagsFragment$2
  implements Runnable
{
  public void run()
  {
    if (this.this$0.mIsLoading)
      if (this.this$0.getView() != null)
      {
        View localView2 = this.this$0.getView().findViewById(2131492983);
        localView2.startAnimation(AnimationUtils.loadAnimation(AppContext.getContext(), 2130968585));
        localView2.setVisibility(0);
      }
    while (true)
    {
      return;
      if (this.this$0.getView() != null)
      {
        View localView1 = this.this$0.getView().findViewById(2131492983);
        localView1.setVisibility(8);
        localView1.clearAnimation();
        continue;
      }
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.SearchTagsFragment.2
 * JD-Core Version:    0.6.0
 */
package com.instagram.android.fragment;

import android.view.View;
import android.view.animation.AnimationUtils;
import com.instagram.android.model.Media;
import com.instagram.android.service.AppContext;

class CommentThreadFragment$16
  implements Runnable
{
  public void run()
  {
    if ((CommentThreadFragment.access$400(this.this$0)) || ((CommentThreadFragment.access$000(this.this$0) != null) && (CommentThreadFragment.access$000(this.this$0).isLoadingMoreComments())))
      if (this.this$0.getView() != null)
      {
        View localView1 = this.this$0.getView().findViewById(2131492983);
        localView1.startAnimation(AnimationUtils.loadAnimation(AppContext.getContext(), 2130968585));
        localView1.setVisibility(0);
      }
    while (true)
    {
      return;
      if (this.this$0.getView() != null)
      {
        View localView2 = this.this$0.getView().findViewById(2131492983);
        localView2.setVisibility(8);
        localView2.clearAnimation();
        continue;
      }
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.CommentThreadFragment.16
 * JD-Core Version:    0.6.0
 */
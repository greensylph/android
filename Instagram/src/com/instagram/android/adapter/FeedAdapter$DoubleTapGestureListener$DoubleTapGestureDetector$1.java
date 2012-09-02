package com.instagram.android.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ListView;
import com.instagram.android.fragment.FeedFragment;
import com.instagram.android.model.Media;
import com.instagram.android.service.AppContext;

class FeedAdapter$DoubleTapGestureListener$DoubleTapGestureDetector$1
  implements Runnable
{
  public void run()
  {
    ListView localListView;
    if (this.this$2.this$1.this$0.mFeedFragment.getView() != null)
    {
      localListView = this.this$2.this$1.this$0.mFeedFragment.getListView();
      if (localListView == null);
    }
    for (int i = 0; ; i++)
    {
      if (i < localListView.getCount())
      {
        View localView1 = localListView.getChildAt(i);
        if (localView1 == null)
          continue;
        View localView2 = localView1.findViewById(2131493016);
        if ((localView2 == null) || (localView2.getTag(2131492864) == null))
          continue;
        int j = ((Integer)localView2.getTag(2131492864)).intValue();
        Media localMedia = (Media)this.this$2.this$1.this$0.getItem(j);
        if ((localMedia == null) || (!this.val$touchedMedia.getId().equals(localMedia.getId())))
          continue;
        ImageView localImageView = (ImageView)localView1.findViewById(2131493017);
        if (localImageView != null)
        {
          Animation localAnimation = FeedAdapter.access$500(this.this$2.this$1.this$0);
          localImageView.setImageDrawable(AppContext.getContext().getResources().getDrawable(2130837665));
          localImageView.clearAnimation();
          localImageView.startAnimation(localAnimation);
        }
      }
      return;
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.adapter.FeedAdapter.DoubleTapGestureListener.DoubleTapGestureDetector.1
 * JD-Core Version:    0.6.0
 */
package com.instagram.android.imagecache;

import android.view.View;
import android.widget.AbsListView.RecyclerListener;

class SampleActivity$1
  implements AbsListView.RecyclerListener
{
  public void onMovedToScrapHeap(View paramView)
  {
    ((IgProgressImageView)paramView.findViewById(R.id.row_image_igimageview)).removeLoadCallback();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.imagecache.SampleActivity.1
 * JD-Core Version:    0.6.0
 */
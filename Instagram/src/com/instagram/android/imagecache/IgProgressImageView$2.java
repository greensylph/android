package com.instagram.android.imagecache;

import android.widget.ProgressBar;

class IgProgressImageView$2
  implements IgImageView.OnProgressListener
{
  public void onProgress(int paramInt)
  {
    if (this.this$0.getDisplayedChild() != 1)
      this.this$0.setDisplayedChild(1);
    IgProgressImageView.access$100(this.this$0).setProgress(paramInt);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.imagecache.IgProgressImageView.2
 * JD-Core Version:    0.6.0
 */
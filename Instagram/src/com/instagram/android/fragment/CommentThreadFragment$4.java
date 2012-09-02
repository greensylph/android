package com.instagram.android.fragment;

import com.instagram.android.adapter.CommentThreadAdapter;
import com.instagram.android.model.Media;
import com.instagram.android.model.MediaFeedResponse;
import com.instagram.api.AbstractStreamingApiCallbacks;
import java.util.ArrayList;

class CommentThreadFragment$4 extends AbstractStreamingApiCallbacks<MediaFeedResponse>
{
  public void onRequestFinished()
  {
    CommentThreadFragment.access$402(this.this$0, false);
    CommentThreadFragment.access$500(this.this$0);
    super.onRequestFinished();
  }

  public void onRequestStart()
  {
    CommentThreadFragment.access$402(this.this$0, true);
    CommentThreadFragment.access$500(this.this$0);
    super.onRequestStart();
  }

  protected void onSuccess(MediaFeedResponse paramMediaFeedResponse)
  {
    CommentThreadFragment.access$002(this.this$0, (Media)paramMediaFeedResponse.getItems().get(0));
    CommentThreadFragment.access$100(this.this$0).setMedia(CommentThreadFragment.access$000(this.this$0));
    CommentThreadFragment.access$200(this.this$0);
    CommentThreadFragment.access$300(this.this$0);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.CommentThreadFragment.4
 * JD-Core Version:    0.6.0
 */
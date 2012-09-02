package com.instagram.android.fragment;

import android.os.Bundle;
import com.instagram.api.AbstractStreamingApiCallbacks;
import com.instagram.api.request.MediaFeedRequest;

class CommentThreadFragment$17 extends MediaFeedRequest
{
  protected String getBaseFeedPath()
  {
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = this.this$0.getArguments().getString("com.instagram.android.fragment.CommentThreadFragment.INTENT_EXTRA_MEDIA_ID");
    return String.format("media/%s/info/", arrayOfObject);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.CommentThreadFragment.17
 * JD-Core Version:    0.6.0
 */
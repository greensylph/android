package com.instagram.android.fragment;

import android.net.Uri;
import android.os.Bundle;
import com.instagram.api.AbstractStreamingApiCallbacks;
import com.instagram.api.request.MediaFeedRequest;

class HashtagFeedFragment$2 extends MediaFeedRequest
{
  protected String getBaseFeedPath()
  {
    String str = this.this$0.getArguments().getString("com.instagram.android.fragment.HashtagFeedFragment.ARGUMENT_TAG_NAME");
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Uri.encode(str);
    return String.format("feed/tag/%s/", arrayOfObject);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.HashtagFeedFragment.2
 * JD-Core Version:    0.6.0
 */
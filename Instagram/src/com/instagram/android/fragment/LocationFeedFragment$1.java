package com.instagram.android.fragment;

import android.net.Uri;
import com.instagram.android.location.Venue;
import com.instagram.android.model.Media;
import com.instagram.api.AbstractStreamingApiCallbacks;
import com.instagram.api.request.MediaFeedRequest;

class LocationFeedFragment$1 extends MediaFeedRequest
{
  protected String getBaseFeedPath()
  {
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Uri.encode(LocationFeedFragment.access$000(this.this$0).getVenue().id);
    return String.format("feed/location/%s/", arrayOfObject);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.LocationFeedFragment.1
 * JD-Core Version:    0.6.0
 */
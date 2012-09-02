package com.instagram.android.activity;

import com.instagram.android.location.Venue;
import com.instagram.android.widget.RefreshButton;
import com.instagram.foursquare.NearbyVenuesManager.FetchListener;
import java.util.List;

class NearbyVenuesActivity$3
  implements NearbyVenuesManager.FetchListener
{
  public void onFailure()
  {
    NearbyVenuesActivity.access$200(this.this$0).setDisplayedChild(0);
  }

  public void onSuccess(List<Venue> paramList)
  {
    NearbyVenuesActivity.access$200(this.this$0).setDisplayedChild(0);
    NearbyVenuesActivity.access$300(this.this$0, paramList);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.activity.NearbyVenuesActivity.3
 * JD-Core Version:    0.6.0
 */
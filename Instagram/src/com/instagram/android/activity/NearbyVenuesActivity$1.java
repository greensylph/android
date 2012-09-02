package com.instagram.android.activity;

import android.location.Location;
import com.instagram.android.location.BestLocationListener;
import java.util.Observable;
import java.util.Observer;

class NearbyVenuesActivity$1
  implements Observer
{
  public void update(Observable paramObservable, Object paramObject)
  {
    Location localLocation = (Location)paramObject;
    if (((BestLocationListener)paramObservable).isAccurateEnough(localLocation))
      NearbyVenuesActivity.access$000(this.this$0, localLocation);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.activity.NearbyVenuesActivity.1
 * JD-Core Version:    0.6.0
 */
package com.instagram.android.activity;

import android.location.Location;
import android.view.View;
import android.view.View.OnClickListener;
import com.instagram.android.InstagramApplication;

class NearbyVenuesActivity$5
  implements View.OnClickListener
{
  public void onClick(View paramView)
  {
    InstagramApplication localInstagramApplication = (InstagramApplication)this.this$0.getApplication();
    Location localLocation = localInstagramApplication.getLastKnownLocation();
    if ((localLocation != null) && (localInstagramApplication.isLocationValid(localLocation)))
      NearbyVenuesActivity.access$400(this.this$0, localLocation, this.val$searchString);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.activity.NearbyVenuesActivity.5
 * JD-Core Version:    0.6.0
 */
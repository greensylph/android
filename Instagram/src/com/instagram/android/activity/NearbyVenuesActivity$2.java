package com.instagram.android.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.instagram.android.adapter.NearbyVenuesAdapter;
import com.instagram.android.location.Venue;
import com.instagram.foursquare.NearbyVenuesManager;

class NearbyVenuesActivity$2
  implements AdapterView.OnItemClickListener
{
  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    if (NearbyVenuesActivity.access$100(this.this$0).isCustomRow(1, paramInt))
    {
      String str = NearbyVenuesActivity.access$100(this.this$0).getSearchString();
      NearbyVenuesActivity.show(this.this$0, 0, false, str);
    }
    while (true)
    {
      return;
      if (NearbyVenuesActivity.access$100(this.this$0).getCount() > 0)
      {
        Venue localVenue = NearbyVenuesActivity.access$100(this.this$0).getItem(paramInt);
        if (localVenue == null)
          continue;
        NearbyVenuesManager.setSelectedVenue(localVenue);
        Intent localIntent = new Intent();
        this.this$0.setResult(-1, localIntent);
        this.this$0.finish();
        continue;
      }
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.activity.NearbyVenuesActivity.2
 * JD-Core Version:    0.6.0
 */
package com.instagram.android.service;

import android.content.Context;
import com.instagram.android.Log;
import com.instagram.android.location.Venue;
import java.util.HashMap;

public class VenueStore extends HashMap<String, Venue>
{
  public static final String TAG = "VenueStore";
  public static final String VENUE_STORE_SERVICE = "com.instagram.android.service.venue_store";

  public static VenueStore getInstance(Context paramContext)
  {
    VenueStore localVenueStore = (VenueStore)paramContext.getSystemService("com.instagram.android.service.venue_store");
    if (localVenueStore == null)
      localVenueStore = (VenueStore)paramContext.getApplicationContext().getSystemService("com.instagram.android.service.venue_store");
    if (localVenueStore == null)
      throw new IllegalStateException("Venue store not available");
    return localVenueStore;
  }

  public Venue put(String paramString, Venue paramVenue)
  {
    if (paramString == null)
      Log.d("VenueStore", "Key was null!");
    return (Venue)super.put(paramString, paramVenue);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.service.VenueStore
 * JD-Core Version:    0.6.0
 */
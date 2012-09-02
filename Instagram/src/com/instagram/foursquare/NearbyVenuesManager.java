package com.instagram.foursquare;

import android.location.Location;
import com.instagram.android.Log;
import com.instagram.android.location.Venue;
import java.util.List;
import java.util.Observable;

public class NearbyVenuesManager extends Observable
{
  private static final String TAG = "NearbyVenuesManager";
  private static Venue sSelectedVenue;
  private static List<Venue> sVenues = null;

  public static void clearCache()
  {
    sVenues = null;
  }

  public static void fetchNearby(Location paramLocation, String paramString, boolean paramBoolean, FetchListener paramFetchListener)
  {
    Log.d("NearbyVenuesManager", "Perfoming fetchNearby");
    Foursquare.venuesNearby(paramLocation, paramString, new Foursquare.RequestListener(paramBoolean, paramFetchListener)
    {
      private void handleFailure()
      {
        if (this.val$listener != null)
        {
          Log.e("NearbyVenuesManager", "Failed to fetchNearby venues");
          this.val$listener.onFailure();
        }
      }

      private void handleSuccess(List<Venue> paramList, boolean paramBoolean)
      {
        Log.e("NearbyVenuesManager", "Successfully fetched venues");
        if ((paramBoolean) && (NearbyVenuesManager.access$000(paramList)))
          NearbyVenuesManager.access$102(paramList);
        if (this.val$listener != null)
          this.val$listener.onSuccess(paramList);
      }

      public void onFailure()
      {
        handleFailure();
      }

      public void onSuccess(List<Venue> paramList)
      {
        if (NearbyVenuesManager.access$000(paramList))
          handleSuccess(paramList, this.val$cache);
        while (true)
        {
          return;
          handleFailure();
        }
      }
    });
  }

  public static List<Venue> getCache()
  {
    return sVenues;
  }

  public static boolean hasCache()
  {
    if (sVenues != null);
    for (int i = 1; ; i = 0)
      return i;
  }

  private static boolean isValidVenueList(List<Venue> paramList)
  {
    if ((paramList != null) && (paramList.size() > 0));
    for (int i = 1; ; i = 0)
      return i;
  }

  public static void prefetch(Location paramLocation)
  {
    Log.d("NearbyVenuesManager", "Doing prefetch");
    fetchNearby(paramLocation, null, true, null);
  }

  public static void setSelectedVenue(Venue paramVenue)
  {
    sSelectedVenue = paramVenue;
  }

  public static Venue takeSelectedVenue()
  {
    Venue localVenue = sSelectedVenue;
    sSelectedVenue = null;
    return localVenue;
  }

  public static abstract interface FetchListener
  {
    public abstract void onFailure();

    public abstract void onSuccess(List<Venue> paramList);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.foursquare.NearbyVenuesManager
 * JD-Core Version:    0.6.0
 */
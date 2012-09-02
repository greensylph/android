package com.instagram.android.location;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

public class BestLocationListener extends Observable
  implements LocationListener
{
  private static final boolean DEBUG = false;
  public static final long LOCATION_UPDATE_MAX_DELTA_THRESHOLD = 300000L;
  public static final long LOCATION_UPDATE_MIN_DISTANCE = 0L;
  public static final long LOCATION_UPDATE_MIN_TIME = 0L;
  public static final float REQUESTED_FIRST_SEARCH_ACCURACY_IN_METERS = 100.0F;
  public static final int REQUESTED_FIRST_SEARCH_MAX_DELTA_THRESHOLD = 300000;
  public static final long SLOW_LOCATION_UPDATE_MIN_DISTANCE = 50L;
  public static final long SLOW_LOCATION_UPDATE_MIN_TIME = 300000L;
  private static final String TAG = "BestLocationListener";
  private Location mLastLocation;

  public void clearLastKnownLocation()
  {
    monitorenter;
    try
    {
      this.mLastLocation = null;
      monitorexit;
      return;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public Location getLastKnownLocation()
  {
    monitorenter;
    try
    {
      Location localLocation = this.mLastLocation;
      monitorexit;
      return localLocation;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public boolean isAccurateEnough(Location paramLocation)
  {
    if ((paramLocation != null) && (paramLocation.hasAccuracy()) && (paramLocation.getAccuracy() <= 100.0F) && (new Date().getTime() - paramLocation.getTime() < 300000L));
    for (int i = 1; ; i = 0)
      return i;
  }

  public boolean isLocationValid(Location paramLocation)
  {
    monitorenter;
    try
    {
      long l1 = new Date().getTime();
      long l2 = paramLocation.getTime();
      if (l1 - l2 <= 300000L)
      {
        i = 1;
        return i;
      }
      int i = 0;
    }
    finally
    {
      monitorexit;
    }
  }

  public void onBestLocationChanged(Location paramLocation)
  {
    monitorenter;
    try
    {
      this.mLastLocation = paramLocation;
      setChanged();
      notifyObservers(paramLocation);
      monitorexit;
      return;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public void onLocationChanged(Location paramLocation)
  {
    updateLocation(paramLocation);
  }

  public void onProviderDisabled(String paramString)
  {
  }

  public void onProviderEnabled(String paramString)
  {
  }

  public void onStatusChanged(String paramString, int paramInt, Bundle paramBundle)
  {
  }

  public void register(LocationManager paramLocationManager, boolean paramBoolean)
  {
    long l1 = 300000L;
    long l2 = 50L;
    if (paramBoolean)
    {
      l1 = 0L;
      l2 = 0L;
    }
    Iterator localIterator = paramLocationManager.getProviders(true).iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if (paramLocationManager.isProviderEnabled(str))
        updateLocation(paramLocationManager.getLastKnownLocation(str));
      if ((!paramBoolean) && ("gps".equals(str)))
        continue;
      paramLocationManager.requestLocationUpdates(str, l1, (float)l2, this);
    }
  }

  public void unregister(LocationManager paramLocationManager)
  {
    paramLocationManager.removeUpdates(this);
  }

  public void updateLastKnownLocation(LocationManager paramLocationManager)
  {
    monitorenter;
    try
    {
      List localList = paramLocationManager.getProviders(true);
      int i = 0;
      int j = localList.size();
      while (i < j)
      {
        String str = (String)localList.get(i);
        if (paramLocationManager.isProviderEnabled(str))
          updateLocation(paramLocationManager.getLastKnownLocation(str));
        i++;
      }
      monitorexit;
      return;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public void updateLocation(Location paramLocation)
  {
    if ((paramLocation != null) && (this.mLastLocation == null))
      onBestLocationChanged(paramLocation);
    while (true)
    {
      return;
      if (paramLocation != null)
      {
        long l1 = new Date().getTime();
        long l2 = l1 - paramLocation.getTime();
        long l3 = l1 - this.mLastLocation.getTime();
        int i;
        label63: int j;
        label75: label83: int k;
        if (l2 <= 300000L)
        {
          i = 1;
          if (l3 > 300000L)
            break label160;
          j = 1;
          if (l2 > l3)
            break label166;
          if ((!paramLocation.hasAccuracy()) && (!this.mLastLocation.hasAccuracy()))
            break label169;
          k = 1;
          label103: m = 0;
          if (k != 0)
            if ((!paramLocation.hasAccuracy()) || (this.mLastLocation.hasAccuracy()))
              break label175;
        }
        for (int m = 1; ; m = 0)
        {
          if ((k == 0) || (m == 0) || (i == 0))
            break label225;
          onBestLocationChanged(paramLocation);
          break;
          i = 0;
          break label63;
          label160: j = 0;
          break label75;
          label166: break label83;
          label169: k = 0;
          break label103;
          label175: if ((paramLocation.hasAccuracy()) || (!this.mLastLocation.hasAccuracy()))
            break label198;
        }
        label198: if (paramLocation.getAccuracy() <= this.mLastLocation.getAccuracy());
        for (m = 1; ; m = 0)
          break;
        label225: if ((i == 0) || (j != 0))
          continue;
        onBestLocationChanged(paramLocation);
        continue;
      }
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.location.BestLocationListener
 * JD-Core Version:    0.6.0
 */
package com.instagram.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import com.instagram.android.InstagramApplication;
import com.instagram.android.adapter.NearbyVenuesAdapter;
import com.instagram.android.location.Venue;
import com.instagram.android.widget.RefreshButton;
import com.instagram.foursquare.NearbyVenuesManager;
import java.util.List;
import java.util.Observer;

public class NearbyVenuesActivity extends FragmentActivity
{
  private static final String INTENT_EXTRA_SEARCH_STRING = "searchString";
  public static final String INTENT_EXTRA_USE_CACHED_VENUES = "useCachedVenues";
  private static final int REQUEST_SEARCH_VENUES;
  public final String TAG = "NearbyVenuesActivity";
  private NearbyVenuesAdapter mAdapter;
  private Observer mNearbyVenuesLocationObserver = new NearbyVenuesActivity.1(this);
  private AdapterView.OnItemClickListener mOnClickListener = new NearbyVenuesActivity.2(this);
  private RefreshButton mRefreshButton;
  private boolean mUsingCache;

  private void fetchVenues(Location paramLocation, String paramString)
  {
    int i = 1;
    this.mRefreshButton.setDisplayedChild(i);
    if (paramString == null);
    while (true)
    {
      NearbyVenuesManager.fetchNearby(paramLocation, paramString, i, new NearbyVenuesActivity.3(this));
      return;
      int j = 0;
    }
  }

  private String getIntentExtraSearchString()
  {
    Intent localIntent = getIntent();
    if (localIntent.hasExtra("searchString"));
    for (String str = localIntent.getStringExtra("searchString"); ; str = null)
      return str;
  }

  private void locationUpdated(Location paramLocation)
  {
    stopUpdatingLocation();
    this.mAdapter.setLocation(paramLocation);
    if (!this.mUsingCache)
      fetchVenues(paramLocation, getIntentExtraSearchString());
  }

  private void setNearbyVenues(List<Venue> paramList)
  {
    this.mAdapter.setNearbyVenues(paramList);
    this.mAdapter.notifyDataSetChanged();
  }

  private boolean shouldUseCache()
  {
    int i = 0;
    if ((getIntent().hasExtra("useCachedVenues")) && (getIntent().getBooleanExtra("useCachedVenues", false)))
      i = 1;
    return i;
  }

  public static void show(Activity paramActivity, int paramInt, boolean paramBoolean, String paramString)
  {
    Intent localIntent = new Intent(paramActivity, NearbyVenuesActivity.class);
    localIntent.putExtra("useCachedVenues", paramBoolean);
    localIntent.putExtra("searchString", paramString);
    paramActivity.startActivityForResult(localIntent, paramInt);
  }

  private void startUpdatingLocation()
  {
    ((InstagramApplication)getApplication()).requestLocationUpdates(this.mNearbyVenuesLocationObserver);
  }

  private void stopUpdatingLocation()
  {
    ((InstagramApplication)getApplication()).removeLocationUpdates(this.mNearbyVenuesLocationObserver);
  }

  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if (paramInt2 != -1);
    while (true)
    {
      return;
      switch (paramInt1)
      {
      default:
        break;
      case 0:
        setResult(-1);
        finish();
      }
    }
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903055);
    ListView localListView = (ListView)findViewById(16908298);
    localListView.setOnItemClickListener(this.mOnClickListener);
    localListView.addFooterView(getLayoutInflater().inflate(2130903114, null));
    this.mAdapter = new NearbyVenuesAdapter(this);
    localListView.setAdapter(this.mAdapter);
    if (getIntentExtraSearchString() != null)
      this.mAdapter.disableSearch();
    String str1 = getIntentExtraSearchString();
    if (str1 != null)
    {
      String str2 = "“" + str1 + "”";
      ((TextView)findViewById(2131492876)).setText(str2);
    }
    findViewById(2131492875).setOnClickListener(new NearbyVenuesActivity.4(this));
    this.mRefreshButton = ((RefreshButton)findViewById(2131492878));
    this.mRefreshButton.setOnClickListener(new NearbyVenuesActivity.5(this, str1));
  }

  protected void onStart()
  {
    super.onStart();
    InstagramApplication localInstagramApplication = (InstagramApplication)getApplication();
    Location localLocation = localInstagramApplication.getLastKnownLocation();
    boolean bool;
    if ((shouldUseCache()) && (NearbyVenuesManager.hasCache()))
    {
      bool = true;
      this.mUsingCache = bool;
      if ((localLocation == null) || (!localInstagramApplication.isLocationValid(localLocation)))
        break label77;
      this.mAdapter.setLocation(localLocation);
      label57: if (!this.mUsingCache)
        break label84;
      setNearbyVenues(NearbyVenuesManager.getCache());
    }
    while (true)
    {
      return;
      bool = false;
      break;
      label77: startUpdatingLocation();
      break label57;
      label84: if (localLocation == null)
        continue;
      fetchVenues(localLocation, getIntentExtraSearchString());
    }
  }

  protected void onStop()
  {
    super.onStop();
    stopUpdatingLocation();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.activity.NearbyVenuesActivity
 * JD-Core Version:    0.6.0
 */
package com.instagram.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.instagram.android.InstagramApplication;
import com.instagram.android.Log;
import com.instagram.android.Preferences;
import com.instagram.android.adapter.FilterHashTagsAndNamesAdapter;
import com.instagram.android.location.BestLocationListener;
import com.instagram.android.location.Venue;
import com.instagram.android.media.PendingMedia;
import com.instagram.android.service.PendingMediaService;
import com.instagram.android.widget.IgAutoCompleteTextView;
import com.instagram.facebook.FacebookAccount;
import com.instagram.facebook.FacebookConstants;
import com.instagram.foursquare.FoursquareAccount;
import com.instagram.foursquare.NearbyVenuesManager;
import com.instagram.tumblr.TumblrAccount;
import com.instagram.twitter.TwitterAccount;
import com.instagram.util.CameraUsageReportingUtil;
import java.util.Observable;
import java.util.Observer;

public class MetadataActivity extends Activity
{
  private static final int HANDLER_MESSAGE_LOCATION_TIMED_OUT = 0;
  public static final String INTENT_EXTRA_PENDING_MEDIA_KEY = "com.instagram.android.MetadataActivity.INTENT_EXTRA_PENDING_MEDIA_ID";
  public static final int LOCATION_TIMEOUT_MILLIS = 10000;
  private static final int MENU_FACEBOOK = 2;
  private static final int MENU_FOURSQUARE = 3;
  private static final int MENU_TUMBLR = 4;
  private static final int MENU_TWITTER = 1;
  private static final int NO_MENU = 0;
  private static final int REQUEST_FOURSQUARE_AUTH = 2;
  private static final int REQUEST_NEARBY_VENUES = 4;
  private static final int REQUEST_TUMBLR_AUTH = 3;
  private static final int REQUEST_TWITTER_AUTH = 1;
  public static final String TAG = "MetadataActivity";
  private IgAutoCompleteTextView mCaptionText;
  private FacebookAccount mFacebookAccount;
  private CompoundButton mFacebookToggle;
  private CompoundButton mFoursquareToggle;
  private CompoundButton mGeotagCheckBox;
  private TextView mGeotagLocationHint;
  private TextView mGeotagTextView;
  Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramMessage)
    {
      switch (paramMessage.what)
      {
      default:
      case 0:
      }
      while (true)
      {
        return;
        String str = MetadataActivity.this.getString(2131231052);
        Toast.makeText(MetadataActivity.this.getApplicationContext(), str, 0).show();
        MetadataActivity.this.mGeotagCheckBox.setChecked(false);
      }
    }
  };
  private Location mLocation;
  private View mLocationButton;
  private PendingMedia mMedia;
  Observer mNearbyVenuesLocationObserver = new Observer()
  {
    public void update(Observable paramObservable, Object paramObject)
    {
      Location localLocation = (Location)paramObject;
      if (((BestLocationListener)paramObservable).isAccurateEnough(localLocation))
      {
        MetadataActivity.this.setLocation(localLocation);
        NearbyVenuesManager.prefetch(localLocation);
      }
    }
  };
  private String mPendingMediaKey;
  private CompoundButton mTumblrToggle;
  private CompoundButton mTwitterToggle;
  private Venue mVenue;
  private int mVisibleContextMenu = 0;

  private void configureButtonToggles()
  {
    this.mTwitterToggle = ((CompoundButton)findViewById(2131492997));
    this.mTwitterToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
    {
      public void onCheckedChanged(CompoundButton paramCompoundButton, boolean paramBoolean)
      {
        if (!paramBoolean)
          MetadataActivity.this.mMedia.setIsTwitterEnabled(false);
        while (true)
        {
          return;
          if (TwitterAccount.isConfigured(MetadataActivity.this))
          {
            MetadataActivity.this.mMedia.setIsTwitterEnabled(true);
            continue;
          }
          paramCompoundButton.setChecked(false);
          TwitterAuthActivity.show(MetadataActivity.this, 1);
        }
      }
    });
    registerForContextMenu(this.mTwitterToggle);
    this.mFacebookToggle = ((CompoundButton)findViewById(2131492999));
    this.mFacebookToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
    {
      public void onCheckedChanged(CompoundButton paramCompoundButton, boolean paramBoolean)
      {
        if (!paramBoolean)
          MetadataActivity.this.mMedia.setIsFacebookEnabled(false);
        while (true)
        {
          return;
          Facebook localFacebook = FacebookAccount.getFacebook();
          if (localFacebook.isSessionValid())
          {
            MetadataActivity.this.mMedia.setIsFacebookEnabled(true);
            continue;
          }
          paramCompoundButton.setChecked(false);
          localFacebook.authorize(MetadataActivity.this, FacebookConstants.FACEBOOK_PERMISSIONS, new MetadataActivity.FacebookAuthListener(MetadataActivity.this, localFacebook, null));
        }
      }
    });
    registerForContextMenu(this.mFacebookToggle);
    this.mFoursquareToggle = ((CompoundButton)findViewById(2131493001));
    this.mFoursquareToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
    {
      public void onCheckedChanged(CompoundButton paramCompoundButton, boolean paramBoolean)
      {
        if (!paramBoolean)
          MetadataActivity.this.mMedia.setIsFoursquareEnabled(false);
        while (true)
        {
          return;
          if (FoursquareAccount.get(MetadataActivity.this) != null)
          {
            MetadataActivity.this.mMedia.setIsFoursquareEnabled(true);
            continue;
          }
          paramCompoundButton.setChecked(false);
          FoursquareAuthActivity.show(MetadataActivity.this, 2);
        }
      }
    });
    registerForContextMenu(this.mFoursquareToggle);
    this.mTumblrToggle = ((CompoundButton)findViewById(2131493003));
    this.mTumblrToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
    {
      public void onCheckedChanged(CompoundButton paramCompoundButton, boolean paramBoolean)
      {
        if (!paramBoolean)
          MetadataActivity.this.mMedia.setIsTumblrEnabled(false);
        while (true)
        {
          return;
          if (TumblrAccount.get(MetadataActivity.this) != null)
          {
            MetadataActivity.this.mMedia.setIsTumblrEnabled(true);
            continue;
          }
          paramCompoundButton.setChecked(false);
          TumblrAuthActivity.show(MetadataActivity.this, 3);
        }
      }
    });
  }

  private void configureRows()
  {
    findViewById(2131492875).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        MetadataActivity.this.onBackPressed();
      }
    });
    findViewById(2131492998).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        MetadataActivity.this.mFacebookToggle.toggle();
      }
    });
    findViewById(2131493000).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        if (MetadataActivity.this.mFoursquareToggle.isEnabled())
          MetadataActivity.this.mFoursquareToggle.toggle();
      }
    });
    findViewById(2131493002).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        MetadataActivity.this.mTumblrToggle.toggle();
      }
    });
    findViewById(2131492996).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        MetadataActivity.this.mTwitterToggle.toggle();
      }
    });
  }

  private PendingMedia getPendingMedia()
  {
    if (this.mPendingMediaKey == null)
      this.mPendingMediaKey = getIntent().getStringExtra("com.instagram.android.MetadataActivity.INTENT_EXTRA_PENDING_MEDIA_ID");
    return PendingMediaService.getMedia(this.mPendingMediaKey);
  }

  private void handleResultForFoursquareRequest(int paramInt, Intent paramIntent)
  {
    if (paramInt == -1);
    for (int i = 1; ; i = 0)
    {
      if (i != 0)
      {
        if (hasVenue())
          this.mFoursquareToggle.setChecked(true);
        Toast.makeText(this, "Foursquare account connected!", 0).show();
      }
      return;
    }
  }

  private void handleResultForNearbyLocationsRequest(int paramInt, Intent paramIntent)
  {
    if (paramInt == -1)
      setVenue(NearbyVenuesManager.takeSelectedVenue());
  }

  private void handleResultForTumblrRequest(int paramInt, Intent paramIntent)
  {
    if (paramInt == -1);
    for (int i = 1; ; i = 0)
    {
      if (i != 0)
      {
        this.mTumblrToggle.setChecked(true);
        Toast.makeText(this, "Tumblr account connected!", 0).show();
      }
      return;
    }
  }

  private void handleResultForTwitterRequest(int paramInt, Intent paramIntent)
  {
    if (paramInt == -1);
    for (int i = 1; ; i = 0)
    {
      if (i != 0)
      {
        this.mTwitterToggle.setChecked(true);
        Toast.makeText(this, "Twitter account connected!", 0).show();
      }
      return;
    }
  }

  private boolean hasLocation()
  {
    if (this.mLocation != null);
    for (int i = 1; ; i = 0)
      return i;
  }

  private boolean hasVenue()
  {
    if (this.mVenue != null);
    for (int i = 1; ; i = 0)
      return i;
  }

  private void hideKeyboard()
  {
    ((InputMethodManager)getSystemService("input_method")).hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
  }

  private void requestLocation()
  {
    this.mHandler.sendEmptyMessageDelayed(0, 10000L);
    InstagramApplication localInstagramApplication = (InstagramApplication)getApplication();
    Location localLocation = localInstagramApplication.getLastKnownLocation();
    if ((localLocation != null) && (localInstagramApplication.isLocationValid(localLocation)))
      setLocation(localLocation);
    while (true)
    {
      return;
      this.mGeotagTextView.setText(2131230856);
      localInstagramApplication.requestLocationUpdates(this.mNearbyVenuesLocationObserver);
    }
  }

  private void setLocation(Location paramLocation)
  {
    this.mLocation = paramLocation;
    stopUpdatingLocation();
    if (this.mLocation == null)
    {
      this.mGeotagTextView.setText(2131230853);
      this.mGeotagLocationHint.setVisibility(8);
      this.mLocationButton.setVisibility(8);
      findViewById(2131492990).setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          CompoundButton localCompoundButton = MetadataActivity.this.mGeotagCheckBox;
          if (!MetadataActivity.this.mGeotagCheckBox.isChecked());
          for (boolean bool = true; ; bool = false)
          {
            localCompoundButton.setChecked(bool);
            return;
          }
        }
      });
      this.mMedia.clearGeotag();
      this.mFoursquareToggle.setChecked(false);
    }
    while (true)
    {
      return;
      this.mGeotagTextView.setText(2131230854);
      this.mGeotagLocationHint.setVisibility(0);
      this.mLocationButton.setVisibility(0);
      findViewById(2131492990).setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          MetadataActivity.this.onClickLocationButton(paramView);
        }
      });
      this.mMedia.setLatitude(paramLocation.getLatitude());
      this.mMedia.setLongitude(paramLocation.getLongitude());
    }
  }

  private void setVenue(Venue paramVenue)
  {
    this.mVenue = paramVenue;
    this.mMedia.setVenue(paramVenue);
    if (this.mVenue == null)
    {
      this.mFoursquareToggle.setEnabled(false);
      if (this.mLocation != null)
        this.mGeotagLocationHint.setVisibility(0);
    }
    while (true)
    {
      return;
      this.mGeotagLocationHint.setVisibility(8);
      continue;
      this.mGeotagTextView.setText(paramVenue.name);
      this.mFoursquareToggle.setEnabled(true);
      this.mGeotagLocationHint.setVisibility(8);
    }
  }

  private void stopUpdatingLocation()
  {
    ((InstagramApplication)getApplication()).removeLocationUpdates(this.mNearbyVenuesLocationObserver);
    this.mHandler.removeMessages(0);
  }

  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    switch (paramInt1)
    {
    default:
      FacebookAccount.getFacebook().authorizeCallback(paramInt1, paramInt2, paramIntent);
    case 1:
    case 2:
    case 3:
    case 4:
    }
    while (true)
    {
      return;
      handleResultForTwitterRequest(paramInt2, paramIntent);
      continue;
      handleResultForFoursquareRequest(paramInt2, paramIntent);
      continue;
      handleResultForTumblrRequest(paramInt2, paramIntent);
      continue;
      handleResultForNearbyLocationsRequest(paramInt2, paramIntent);
    }
  }

  public void onClickLocationButton(View paramView)
  {
    NearbyVenuesActivity.show(this, 4, true, null);
  }

  public void onClickUploadButton(View paramView)
  {
    this.mMedia.setCaption(this.mCaptionText.getText().toString());
    PendingMediaService.configure(this, this.mMedia);
    MainTabActivity.setNewMediaPosted();
    setResult(-1);
    finish();
  }

  public void onContentChanged()
  {
  }

  public boolean onContextItemSelected(MenuItem paramMenuItem)
  {
    switch (this.mVisibleContextMenu)
    {
    default:
    case 1:
    case 2:
    case 3:
    case 4:
    }
    while (true)
    {
      this.mVisibleContextMenu = 0;
      return false;
      TwitterAccount.remove(this, true);
      this.mTwitterToggle.setChecked(false);
      continue;
      FacebookAccount.deleteCredentials(true);
      this.mFacebookToggle.setChecked(false);
      continue;
      FoursquareAccount.delete(this);
      this.mFoursquareToggle.setChecked(false);
      continue;
      TumblrAccount.remove(this);
      this.mTumblrToggle.setChecked(false);
    }
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle != null)
      this.mPendingMediaKey = paramBundle.getString("com.instagram.android.MetadataActivity.INTENT_EXTRA_PENDING_MEDIA_ID");
    this.mMedia = getPendingMedia();
    if (this.mMedia == null)
    {
      Log.e("MetadataActivity", "Pending media was null when starting MetadataActivity");
      finish();
    }
    while (true)
    {
      return;
      NearbyVenuesManager.clearCache();
      setContentView(2130903095);
      this.mCaptionText = ((IgAutoCompleteTextView)findViewById(2131492989));
      this.mCaptionText.setOnKeyListener(new View.OnKeyListener()
      {
        public boolean onKey(View paramView, int paramInt, KeyEvent paramKeyEvent)
        {
          if ((paramKeyEvent.getAction() == 1) && (paramKeyEvent.getKeyCode() == 66))
            MetadataActivity.this.hideKeyboard();
          return false;
        }
      });
      this.mCaptionText.setAdapter(new FilterHashTagsAndNamesAdapter(this));
      configureButtonToggles();
      registerForContextMenu(this.mTumblrToggle);
      this.mLocationButton = findViewById(2131492994);
      this.mGeotagTextView = ((TextView)findViewById(2131492992));
      this.mGeotagCheckBox = ((CompoundButton)findViewById(2131492991));
      this.mGeotagLocationHint = ((TextView)findViewById(2131492993));
      configureRows();
      setVenue(this.mVenue);
      setLocation(this.mLocation);
      this.mGeotagCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
      {
        public void onCheckedChanged(CompoundButton paramCompoundButton, boolean paramBoolean)
        {
          if (paramBoolean)
            if (!MetadataActivity.this.hasLocation())
              MetadataActivity.this.requestLocation();
          while (true)
          {
            Preferences.getInstance(MetadataActivity.this).setHasGeotagEnabled(paramBoolean);
            return;
            MetadataActivity.this.setLocation(null);
            MetadataActivity.this.setVenue(null);
          }
        }
      });
      this.mGeotagCheckBox.setChecked(Preferences.getInstance(this).getHasGeotagEnabled());
      if (!this.mGeotagCheckBox.isChecked())
        this.mLocationButton.setVisibility(4);
      CameraUsageReportingUtil.didOpenPostMetadata();
    }
  }

  public void onCreateContextMenu(ContextMenu paramContextMenu, View paramView, ContextMenu.ContextMenuInfo paramContextMenuInfo)
  {
    if (paramView == this.mTwitterToggle)
      this.mVisibleContextMenu = 1;
    while (true)
    {
      return;
      if (paramView == this.mFacebookToggle)
      {
        this.mVisibleContextMenu = 2;
        continue;
      }
      if (paramView == this.mFoursquareToggle)
      {
        this.mVisibleContextMenu = 3;
        continue;
      }
      if (paramView == this.mTumblrToggle)
      {
        this.mVisibleContextMenu = 4;
        continue;
      }
      this.mVisibleContextMenu = 0;
    }
  }

  protected void onPause()
  {
    super.onPause();
  }

  protected void onResume()
  {
    super.onResume();
  }

  protected void onSaveInstanceState(Bundle paramBundle)
  {
    paramBundle.putString("com.instagram.android.MetadataActivity.INTENT_EXTRA_PENDING_MEDIA_ID", this.mPendingMediaKey);
    super.onSaveInstanceState(paramBundle);
  }

  protected void onStop()
  {
    super.onStop();
  }

  private final class FacebookAuthListener
    implements Facebook.DialogListener
  {
    private final Facebook mFacebook;

    private FacebookAuthListener(Facebook arg2)
    {
      Object localObject;
      this.mFacebook = localObject;
    }

    public void onCancel()
    {
      Log.d("MetadataActivity", "Facebook onCancel");
    }

    public void onComplete(Bundle paramBundle)
    {
      MetadataActivity.this.mFacebookToggle.setChecked(true);
      FacebookAccount.saveCredentials();
    }

    public void onError(DialogError paramDialogError)
    {
      Log.d("MetadataActivity", "Facebook onError");
    }

    public void onFacebookError(FacebookError paramFacebookError)
    {
      Log.d("MetadataActivity", "Facebook onFacebookError");
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.activity.MetadataActivity
 * JD-Core Version:    0.6.0
 */
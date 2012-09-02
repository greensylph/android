package com.instagram.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.instagram.android.Log;
import com.instagram.foursquare.FoursquareAccount;
import com.instagram.foursquare.FoursquareConstants;

public class FoursquareAuthActivity extends Activity
{
  private static final String sFragment = "#access_token=";
  private final String TAG = getClass().getSimpleName();

  private boolean containsAccessTokenFragment(String paramString)
  {
    if (paramString.indexOf("#access_token=") > -1);
    for (int i = 1; ; i = 0)
      return i;
  }

  private String getAccessTokenFragment(String paramString)
  {
    Object localObject = null;
    if (paramString == null);
    while (true)
    {
      return localObject;
      try
      {
        String str = paramString.substring(paramString.indexOf("#access_token=") + "#access_token=".length(), paramString.length());
        localObject = str;
      }
      catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
      {
        Log.e(this.TAG, "Unable to pull access_token from URL: " + paramString);
      }
    }
  }

  private void handleAccessTokenFragment(String paramString)
  {
    Intent localIntent = new Intent();
    if (paramString == null)
      setResult(0, localIntent);
    while (true)
    {
      finish();
      return;
      new FoursquareAccount(paramString).store(this);
      setResult(-1, localIntent);
    }
  }

  public static void show(Activity paramActivity, int paramInt)
  {
    paramActivity.startActivityForResult(new Intent(paramActivity, FoursquareAuthActivity.class), paramInt);
  }

  public static void show(Fragment paramFragment, int paramInt)
  {
    paramFragment.startActivityForResult(new Intent(paramFragment.getActivity(), FoursquareAuthActivity.class), paramInt);
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903065);
    WebView localWebView = (WebView)findViewById(2131492931);
    localWebView.getSettings().setJavaScriptEnabled(true);
    localWebView.setWebViewClient(new FoursquareAuthActivity.1(this));
    localWebView.loadUrl("https://foursquare.com/oauth2/authenticate?client_id=" + FoursquareConstants.getConsumerKey() + "&response_type=token" + "&redirect_uri=" + "https://instagram.com/foursquare/oauth_callback/");
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.activity.FoursquareAuthActivity
 * JD-Core Version:    0.6.0
 */
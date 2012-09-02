package com.instagram.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import com.instagram.android.Log;
import com.instagram.android.fragment.ProgressDialogFragment;
import com.instagram.android.loader.XAuthRequestLoader;
import com.instagram.android.loader.XAuthResponse;
import com.instagram.android.service.TumblrService;
import com.instagram.tumblr.TumblrAccount;
import com.instagram.tumblr.TumblrConstants;

public class TumblrAuthActivity extends XAuthActivity
{
  private static final String TAG = "TumblrAuthActivity";
  private final View.OnClickListener mDoneButtonOnClickListener = new View.OnClickListener()
  {
    public void onClick(View paramView)
    {
      TumblrAuthActivity.this.showProgressDialog();
    }
  };

  private void dismissProgressDialog()
  {
    DialogFragment localDialogFragment = (DialogFragment)getSupportFragmentManager().findFragmentByTag("progressDialog");
    this.mHandler.post(new Runnable(localDialogFragment)
    {
      public void run()
      {
        if (this.val$fragment != null)
          this.val$fragment.dismiss();
      }
    });
  }

  private String getPassword()
  {
    String str = null;
    EditText localEditText = (EditText)findViewById(2131492905);
    if (localEditText != null)
      str = localEditText.getText().toString();
    return str;
  }

  private String getUsername()
  {
    String str = null;
    EditText localEditText = (EditText)findViewById(2131492904);
    if (localEditText != null)
      str = localEditText.getText().toString();
    return str;
  }

  public static void show(Activity paramActivity, int paramInt)
  {
    paramActivity.startActivityForResult(new Intent(paramActivity, TumblrAuthActivity.class), paramInt);
  }

  public static void show(Fragment paramFragment, int paramInt)
  {
    paramFragment.startActivityForResult(new Intent(paramFragment.getActivity(), TumblrAuthActivity.class), paramInt);
  }

  private void showProgressDialog()
  {
    ProgressDialogFragment.newInstance().show(getSupportFragmentManager(), "progressDialog");
    getSupportLoaderManager().restartLoader(0, null, new XAuthLoaderCallbacks(null));
  }

  protected String getServiceName()
  {
    return getResources().getString(2131230860);
  }

  protected void setupContentView()
  {
    Bundle localBundle = new Bundle();
    localBundle.putBoolean("deliverOnly", true);
    getSupportLoaderManager().initLoader(0, localBundle, new XAuthLoaderCallbacks(null));
    setContentView(2130903057);
    findViewById(2131492909).setOnClickListener(this.mDoneButtonOnClickListener);
    ((EditText)findViewById(2131492904)).setHint(getServiceName() + " " + getResources().getString(2131230846));
  }

  private class XAuthLoaderCallbacks
    implements LoaderManager.LoaderCallbacks<XAuthResponse>
  {
    private XAuthLoaderCallbacks()
    {
    }

    public Loader<XAuthResponse> onCreateLoader(int paramInt, Bundle paramBundle)
    {
      XAuthRequestLoader localXAuthRequestLoader = new XAuthRequestLoader(TumblrAuthActivity.this.getApplicationContext());
      if ((paramBundle != null) && (paramBundle.getBoolean("deliverOnly")));
      for (boolean bool = true; ; bool = false)
      {
        localXAuthRequestLoader.setDeliverOnly(bool);
        localXAuthRequestLoader.setXAuthURL("https://www.tumblr.com/oauth/access_token");
        localXAuthRequestLoader.setUsernamePassword(TumblrAuthActivity.this.getUsername(), TumblrAuthActivity.this.getPassword());
        localXAuthRequestLoader.setConsumeKeySecret(TumblrConstants.getConsumerKey(), TumblrConstants.getConsumerSecret());
        return localXAuthRequestLoader;
      }
    }

    public void onLoadFinished(Loader<XAuthResponse> paramLoader, XAuthResponse paramXAuthResponse)
    {
      TumblrAuthActivity.this.getSupportLoaderManager().destroyLoader(paramLoader.getId());
      TumblrAuthActivity.this.dismissProgressDialog();
      if (paramXAuthResponse.success())
      {
        Log.d("TumblrAuthActivity", "Success! Token: " + paramXAuthResponse.getToken() + ", Secret: " + paramXAuthResponse.getSecret());
        if (((CheckBox)TumblrAuthActivity.this.findViewById(2131492908)).isChecked())
          TumblrService.followInstagramBlog(TumblrAuthActivity.this.getApplicationContext());
        TumblrAccount.store(TumblrAuthActivity.this.getApplicationContext(), paramXAuthResponse.getToken(), paramXAuthResponse.getSecret());
        TumblrAuthActivity.this.setResult(-1);
        TumblrAuthActivity.this.finish();
      }
      while (true)
      {
        return;
        TumblrAuthActivity.this.showAlertDialog(TumblrAuthActivity.this.getResources().getString(2131231041));
      }
    }

    public void onLoaderReset(Loader<XAuthResponse> paramLoader)
    {
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.activity.TumblrAuthActivity
 * JD-Core Version:    0.6.0
 */
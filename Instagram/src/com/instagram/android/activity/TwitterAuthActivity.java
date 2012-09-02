package com.instagram.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.instagram.android.widget.IgProgressDialog;
import com.instagram.twitter.TwitterXAuth;

public class TwitterAuthActivity extends XAuthActivity
{
  private final String TAG = getClass().getSimpleName();

  private void connectTwitter(String paramString1, String paramString2)
  {
    TwitterXAuth.asyncConnect(this, paramString1, paramString2, new TwitterAuthActivity.1(this, IgProgressDialog.show(this, "", getString(2131230889), true)));
  }

  private String getPassword()
  {
    return ((EditText)findViewById(2131492905)).getText().toString();
  }

  private String getUsername()
  {
    return ((EditText)findViewById(2131492904)).getText().toString();
  }

  private void hideKeyboard()
  {
    ((InputMethodManager)getSystemService("input_method")).hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
  }

  public static void show(Activity paramActivity, int paramInt)
  {
    paramActivity.startActivityForResult(new Intent(paramActivity, TwitterAuthActivity.class), paramInt);
  }

  public static void show(Fragment paramFragment, int paramInt)
  {
    paramFragment.startActivityForResult(new Intent(paramFragment.getActivity(), TwitterAuthActivity.class), paramInt);
  }

  protected String getServiceName()
  {
    return getResources().getString(2131230845);
  }

  protected void onStop()
  {
    hideKeyboard();
    super.onStop();
  }

  protected void setupContentView()
  {
    setContentView(2130903130);
    findViewById(2131492909).setOnClickListener(new TwitterAuthActivity.2(this));
    ((EditText)findViewById(2131492904)).setHint(getServiceName() + " " + getResources().getString(2131230846));
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.activity.TwitterAuthActivity
 * JD-Core Version:    0.6.0
 */
package com.instagram.android.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

public abstract class XAuthActivity extends FragmentActivity
{
  protected Handler mHandler = new Handler();

  protected abstract String getServiceName();

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setupContentView();
    ((TextView)findViewById(2131492876)).setText(getServiceName());
    findViewById(2131492875).setOnClickListener(new XAuthActivity.1(this));
    findViewById(2131492896).setVisibility(8);
  }

  protected abstract void setupContentView();

  protected void showAlertDialog(String paramString)
  {
    this.mHandler.post(new XAuthActivity.2(this, paramString));
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.activity.XAuthActivity
 * JD-Core Version:    0.6.0
 */
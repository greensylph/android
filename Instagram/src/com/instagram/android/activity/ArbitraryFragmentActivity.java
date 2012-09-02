package com.instagram.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class ArbitraryFragmentActivity extends BaseFragmentActivity
{
  public static final String EXTRAS_BUNDLE = "com.instagram.android.activity.ArbitraryFragmentActivity.EXTRAS_BUNDLE";
  public static final String EXTRAS_FRAGMENT_CLASS_NAME = "com.instagram.android.activity.ArbitraryFragmentActivity.EXTRAS_FRAGMENT_CLASS_NAME";

  protected void initializeStartingFragment()
  {
    String str;
    if (getSupportFragmentManager().findFragmentById(2131492901) == null)
      str = getIntent().getExtras().getString("com.instagram.android.activity.ArbitraryFragmentActivity.EXTRAS_FRAGMENT_CLASS_NAME");
    try
    {
      Fragment localFragment = (Fragment)Class.forName(str).newInstance();
      localFragment.setArguments(getIntent().getExtras().getBundle("com.instagram.android.activity.ArbitraryFragmentActivity.EXTRAS_BUNDLE"));
      FragmentTransaction localFragmentTransaction = getSupportFragmentManager().beginTransaction();
      localFragmentTransaction.replace(2131492901, localFragment);
      localFragmentTransaction.commit();
      return;
    }
    catch (Exception localException)
    {
    }
    throw new RuntimeException("No fragment by the name of " + str + " found");
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.activity.ArbitraryFragmentActivity
 * JD-Core Version:    0.6.0
 */
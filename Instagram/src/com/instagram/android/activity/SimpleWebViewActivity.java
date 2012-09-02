package com.instagram.android.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.instagram.android.fragment.SimpleWebViewFragment;

public class SimpleWebViewActivity extends BaseFragmentActivity
{
  public static void show(Activity paramActivity, String paramString1, boolean paramBoolean, String paramString2)
  {
    Intent localIntent = new Intent(paramActivity, SimpleWebViewActivity.class);
    localIntent.putExtra("com.instagram.android.fragment.SimpleWebViewFragment.ARGUMENT_URL", paramString1);
    localIntent.putExtra("com.instagram.android.fragment.SimpleWebViewFragment.ARGUMENT_LOAD_SAME_HOST", paramBoolean);
    localIntent.putExtra("com.instagram.android.fragment.SimpleWebViewFragment.ARGUMENT_TITLE", paramString2);
    paramActivity.startActivity(localIntent);
  }

  protected void initializeStartingFragment()
  {
    if (getSupportFragmentManager().findFragmentById(2131492901) == null)
    {
      SimpleWebViewFragment localSimpleWebViewFragment = new SimpleWebViewFragment();
      localSimpleWebViewFragment.setArguments(getIntent().getExtras());
      FragmentTransaction localFragmentTransaction = getSupportFragmentManager().beginTransaction();
      localFragmentTransaction.replace(2131492901, localSimpleWebViewFragment);
      localFragmentTransaction.commit();
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.activity.SimpleWebViewActivity
 * JD-Core Version:    0.6.0
 */
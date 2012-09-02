package com.instagram.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.instagram.android.activity.ArbitraryFragmentActivity;

public class FragmentUtil
{
  public static final String ARGUMENTS_KEY_NO_BACK_STACK = "noBackStack";

  public static void detachAndAdd(FragmentManager paramFragmentManager, Fragment paramFragment1, Fragment paramFragment2, Bundle paramBundle)
  {
    FragmentTransaction localFragmentTransaction = paramFragmentManager.beginTransaction();
    paramFragment2.setArguments(paramBundle);
    localFragmentTransaction.detach(paramFragment1);
    localFragmentTransaction.add(2131492901, paramFragment2);
    localFragmentTransaction.commit();
    paramFragmentManager.executePendingTransactions();
  }

  public static void navigateTo(FragmentManager paramFragmentManager, Fragment paramFragment, Bundle paramBundle)
  {
    FragmentTransaction localFragmentTransaction = paramFragmentManager.beginTransaction();
    if ((paramBundle != null) && (paramBundle.getBoolean("noBackStack")))
      paramBundle.remove("noBackStack");
    while (true)
    {
      paramFragment.setArguments(paramBundle);
      localFragmentTransaction.replace(2131492901, paramFragment);
      localFragmentTransaction.commit();
      paramFragmentManager.executePendingTransactions();
      return;
      localFragmentTransaction.addToBackStack(null);
    }
  }

  public static void navigateTo(FragmentManager paramFragmentManager, Fragment paramFragment, String paramString, Bundle paramBundle)
  {
    FragmentTransaction localFragmentTransaction = paramFragmentManager.beginTransaction();
    paramFragment.setArguments(paramBundle);
    localFragmentTransaction.replace(2131492901, paramFragment);
    localFragmentTransaction.addToBackStack(paramString);
    localFragmentTransaction.commit();
    paramFragmentManager.executePendingTransactions();
  }

  public static void navigateToInNewActivity(Activity paramActivity, Fragment paramFragment, Bundle paramBundle, String paramString)
  {
    Intent localIntent = new Intent(paramActivity, ArbitraryFragmentActivity.class);
    localIntent.putExtra("com.instagram.android.activity.ActivityInTab.EXTRA_PREVIOUS_SCREEN_NAME", paramString);
    localIntent.putExtra("com.instagram.android.activity.ArbitraryFragmentActivity.EXTRAS_FRAGMENT_CLASS_NAME", paramFragment.getClass().getName());
    localIntent.putExtra("com.instagram.android.activity.ArbitraryFragmentActivity.EXTRAS_BUNDLE", paramBundle);
    paramActivity.startActivity(localIntent);
  }

  public static void removeAndAdd(FragmentManager paramFragmentManager, Fragment paramFragment1, Fragment paramFragment2, Bundle paramBundle)
  {
    FragmentTransaction localFragmentTransaction = paramFragmentManager.beginTransaction();
    paramFragment2.setArguments(paramBundle);
    localFragmentTransaction.remove(paramFragment1);
    localFragmentTransaction.add(2131492901, paramFragment2);
    localFragmentTransaction.commit();
    paramFragmentManager.executePendingTransactions();
  }

  public static void replaceFragment(FragmentManager paramFragmentManager, Fragment paramFragment, Bundle paramBundle)
  {
    FragmentTransaction localFragmentTransaction = paramFragmentManager.beginTransaction();
    paramFragment.setArguments(paramBundle);
    localFragmentTransaction.replace(2131492901, paramFragment);
    localFragmentTransaction.commit();
    paramFragmentManager.executePendingTransactions();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.util.FragmentUtil
 * JD-Core Version:    0.6.0
 */
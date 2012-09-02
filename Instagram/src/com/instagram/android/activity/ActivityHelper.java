package com.instagram.android.activity;

import android.app.Activity;
import android.content.Intent;

public class ActivityHelper
{
  static void redirectToSignedOutState(Activity paramActivity)
  {
    Intent localIntent = new Intent(paramActivity, SignedOutFragmentLayout.class);
    localIntent.setFlags(67108864);
    paramActivity.startActivity(localIntent);
    paramActivity.finish();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.activity.ActivityHelper
 * JD-Core Version:    0.6.0
 */
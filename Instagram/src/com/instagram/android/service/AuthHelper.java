package com.instagram.android.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;
import com.instagram.android.Preferences;
import com.instagram.android.model.User;

public class AuthHelper
{
  public static final String AUTH_HELPER_SERVICE = "com.instagram.androidservice,AuthHelper";
  public static final String BROADCAST_USER_CHANGED = "com.instagram.android.service.BROADCAST_USER_CHANGED";
  public static final String BROADCAST_USER_CHANGED_EXTRA_LOGGED_IN = "loggedin";
  public static final String BROADCAST_USER_CHANGED_EXTRA_USER_ID = "userid";
  public static final String BROADCAST_WEBVIEW_CHANGED_USER = "com.instagram.androidBROADCAST_WEBVIEW_CHANGED_USER";
  private Context mContext;
  private User mCurrentUser;
  private BroadcastReceiver updateUserReceiver = new AuthHelper.1(this);

  public AuthHelper(Context paramContext)
  {
    this.mContext = paramContext;
    LocalBroadcastManager.getInstance(paramContext).registerReceiver(this.updateUserReceiver, new IntentFilter("com.instagram.androidBROADCAST_WEBVIEW_CHANGED_USER"));
  }

  private void broadcastUserChange(String paramString, boolean paramBoolean)
  {
    Intent localIntent = new Intent("com.instagram.android.service.BROADCAST_USER_CHANGED");
    localIntent.putExtra("userid", paramString);
    localIntent.putExtra("loggedin", paramBoolean);
    LocalBroadcastManager.getInstance(AppContext.getContext()).sendBroadcast(localIntent);
  }

  public static AuthHelper getInstance()
  {
    Context localContext = AppContext.getContext();
    AuthHelper localAuthHelper = (AuthHelper)localContext.getSystemService("com.instagram.androidservice,AuthHelper");
    if (localAuthHelper == null)
      localAuthHelper = (AuthHelper)localContext.getApplicationContext().getSystemService("com.instagram.androidservice,AuthHelper");
    if (localAuthHelper == null)
      throw new IllegalStateException("AuthHelper not available");
    return localAuthHelper;
  }

  private void onUserNeedsFetch()
  {
    Toast.makeText(this.mContext, "onUserNeedsFetch() not implemented", 0);
  }

  public void clearCurrentUser()
  {
    if (this.mCurrentUser != null)
      broadcastUserChange(this.mCurrentUser.getId(), false);
    this.mCurrentUser = null;
  }

  public User getCurrentUser()
  {
    if (this.mCurrentUser == null);
    User localUser;
    try
    {
      String str = Preferences.getInstance(this.mContext).getCurrentUserData();
      if (str == null)
      {
        localUser = null;
      }
      else
      {
        this.mCurrentUser = User.getUserFromSerializedData(str);
        UserStore.getInstance(this.mContext).put(this.mCurrentUser.getId(), this.mCurrentUser);
        broadcastUserChange(this.mCurrentUser.getId(), true);
        localUser = this.mCurrentUser;
      }
    }
    catch (Exception localException)
    {
      throw new RuntimeException("Unable to retrieve current user");
    }
    return localUser;
  }

  public void saveCurrentUser(User paramUser)
  {
    try
    {
      this.mCurrentUser = paramUser;
      Preferences.getInstance(this.mContext).setCurrentUserData(paramUser.getUserSerialized());
      broadcastUserChange(paramUser.getId(), true);
      return;
    }
    catch (Exception localException)
    {
    }
    throw new RuntimeException("Unable to write current user");
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.service.AuthHelper
 * JD-Core Version:    0.6.0
 */
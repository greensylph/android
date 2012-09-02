package com.instagram.api.request;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import com.instagram.android.Preferences;
import com.instagram.android.imagecache.IgAsyncTask;
import com.instagram.android.service.AppContext;
import com.instagram.android.service.AuthHelper;
import com.instagram.android.service.AutoCompleteUserService;
import com.instagram.api.ApiHttpClient;

public class LogoutTask extends IgAsyncTask<Void, Void, Void>
{
  public static void executeClientLogout()
  {
    Context localContext = AppContext.getContext();
    Preferences.getInstance(localContext).clearLogin();
    AuthHelper.getInstance().clearCurrentUser();
    LocalBroadcastManager.getInstance(localContext).sendBroadcast(new Intent("com.instagram.android.activity.BROADCAST_LOGOUT"));
    AutoCompleteUserService.clear();
    CookieSyncManager.createInstance(localContext);
    CookieManager.getInstance().removeAllCookie();
  }

  protected Void doInBackground(Void[] paramArrayOfVoid)
  {
    ApiHttpClient.getInstance(AppContext.getContext()).post(ApiUrlHelper.expandPath("accounts/logout/"));
    return null;
  }

  protected void onPostExecute(Void paramVoid)
  {
    executeClientLogout();
    super.onPostExecute(paramVoid);
  }

  protected void onPreExecute()
  {
    super.onPreExecute();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.request.LogoutTask
 * JD-Core Version:    0.6.0
 */
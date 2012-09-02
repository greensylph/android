package com.instagram.android.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import ch.boye.httpclientandroidlib.HttpResponse;
import ch.boye.httpclientandroidlib.StatusLine;
import ch.boye.httpclientandroidlib.util.EntityUtils;
import com.instagram.android.Log;
import com.instagram.android.Preferences;
import com.instagram.api.ApiHttpClient;
import com.instagram.api.RequestParams;
import com.instagram.api.request.ApiUrlHelper;
import java.io.IOException;
import java.util.Date;

public class PushRegistrationService extends IntentService
{
  public static final String DEVICE_TOKEN = "com.instagram.android.service.PushRegistrationService.DEVICE_TOKEN";
  private static final String LOG_TAG = "PushRegistrationService";

  public PushRegistrationService()
  {
    super(PushRegistrationService.class.toString());
  }

  protected void onHandleIntent(Intent paramIntent)
  {
    try
    {
      RequestParams localRequestParams = new RequestParams();
      localRequestParams.put("device_token", paramIntent.getExtras().getString("com.instagram.android.service.PushRegistrationService.DEVICE_TOKEN"));
      localRequestParams.put("device_type", "android");
      HttpResponse localHttpResponse = ApiHttpClient.getInstance(this).post(ApiUrlHelper.expandPath("push/register/"), localRequestParams);
      if (localHttpResponse != null)
      {
        EntityUtils.consume(localHttpResponse.getEntity());
        if ((localHttpResponse.getStatusLine() != null) && (localHttpResponse.getStatusLine().getStatusCode() == 200))
          Preferences.getInstance(this).setPushRegistrationDate(new Date().getTime());
      }
      else
      {
        Log.d("PushRegistrationService", "Error registering device");
      }
    }
    catch (IOException localIOException)
    {
      Log.d("PushRegistrationService", "Error registering device");
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.service.PushRegistrationService
 * JD-Core Version:    0.6.0
 */
package com.facebook.android;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

class Facebook$TokenRefreshServiceConnection$1 extends Handler
{
  public void handleMessage(Message paramMessage)
  {
    String str1 = paramMessage.getData().getString("access_token");
    long l = 1000L * paramMessage.getData().getLong("expires_in");
    Bundle localBundle = (Bundle)paramMessage.getData().clone();
    localBundle.putLong("expires_in", l);
    if (str1 != null)
    {
      this.this$1.this$0.setAccessToken(str1);
      this.this$1.this$0.setAccessExpires(l);
      if (this.this$1.serviceListener != null)
        this.this$1.serviceListener.onComplete(localBundle);
    }
    String str2;
    while (true)
    {
      this.this$1.applicationsContext.unbindService(this.this$1);
      return;
      if (this.this$1.serviceListener == null)
        continue;
      str2 = paramMessage.getData().getString("error");
      if (!paramMessage.getData().containsKey("error_code"))
        break;
      int i = paramMessage.getData().getInt("error_code");
      this.this$1.serviceListener.onFacebookError(new FacebookError(str2, null, i));
    }
    Facebook.ServiceListener localServiceListener = this.this$1.serviceListener;
    if (str2 != null);
    while (true)
    {
      localServiceListener.onError(new Error(str2));
      break;
      str2 = "Unknown service error";
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.facebook.android.Facebook.TokenRefreshServiceConnection.1
 * JD-Core Version:    0.6.0
 */
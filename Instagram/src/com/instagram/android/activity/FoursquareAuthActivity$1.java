package com.instagram.android.activity;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

class FoursquareAuthActivity$1 extends WebViewClient
{
  public void onPageStarted(WebView paramWebView, String paramString, Bitmap paramBitmap)
  {
    if (FoursquareAuthActivity.access$000(this.this$0, paramString))
      FoursquareAuthActivity.access$200(this.this$0, FoursquareAuthActivity.access$100(this.this$0, paramString));
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.activity.FoursquareAuthActivity.1
 * JD-Core Version:    0.6.0
 */
package com.instagram.android.fragment;

import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

class SimpleWebViewFragment$1 extends WebViewClient
{
  public boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString)
  {
    if ((this.val$finalHost != null) && (this.val$finalHost.equalsIgnoreCase(Uri.parse(paramString).getHost())))
      paramWebView.loadUrl(paramString);
    for (boolean bool = true; ; bool = super.shouldOverrideUrlLoading(paramWebView, paramString))
      return bool;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.SimpleWebViewFragment.1
 * JD-Core Version:    0.6.0
 */
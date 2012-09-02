package com.instagram.android.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.webkit.WebView;
import com.instagram.android.Log;
import com.instagram.android.widget.EnhancedWebView;
import com.instagram.api.request.ApiUrlHelper;
import java.util.Map;

class NewsFragment$2 extends BroadcastReceiver
{
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    Log.d("NewsFragment", "Received push.");
    if (NewsFragment.access$000(this.this$0) == null)
      Log.d("NewsFragment", "Currently initializing web view, omitting");
    while (true)
    {
      return;
      if ((NewsFragment.access$000(this.this$0).equals(NewsFragment.access$300(this.this$0).get(NewsFragment.MODE_INBOX))) && (NewsFragment.access$000(this.this$0).isShown()))
      {
        Log.d("NewsFragment", "Currently visible when push received, omitting");
        continue;
      }
      WebView localWebView = (WebView)NewsFragment.access$300(this.this$0).get(NewsFragment.MODE_INBOX);
      if (localWebView == null)
        continue;
      Log.d("NewsFragment", "Currently have a receiver for the inbox fragment, running reload");
      localWebView.loadUrl(ApiUrlHelper.expandPath(NewsFragment.access$200(this.this$0, NewsFragment.MODE_INBOX.intValue())));
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.NewsFragment.2
 * JD-Core Version:    0.6.0
 */
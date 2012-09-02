package com.instagram.android.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.instagram.android.widget.EnhancedWebView;
import com.instagram.api.request.ApiUrlHelper;
import java.util.Map;

class NewsFragment$1 extends BroadcastReceiver
{
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    if (paramIntent.getAction().equals("com.instagram.android.service.action_bar_refresh_clicked"))
      if (NewsFragment.access$000(this.this$0) != null)
        NewsFragment.access$000(this.this$0).loadUrl(ApiUrlHelper.expandPath(NewsFragment.access$200(this.this$0, NewsFragment.access$100(this.this$0))));
    while (true)
    {
      return;
      if ((paramIntent.getAction().equals("com.instagram.android.fragment.NewsFragment.BROADCAST_SWITCH_TO_INBOX")) && (NewsFragment.access$000(this.this$0) != null) && (NewsFragment.access$000(this.this$0) != NewsFragment.access$300(this.this$0).get(NewsFragment.MODE_INBOX)))
      {
        NewsFragment.access$400(this.this$0, NewsFragment.MODE_INBOX.intValue());
        continue;
      }
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.NewsFragment.1
 * JD-Core Version:    0.6.0
 */
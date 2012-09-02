package com.instagram.android.fragment;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.instagram.android.activity.SimpleWebViewActivity;
import com.instagram.android.model.SimpleMenuItem;
import com.instagram.api.request.WebUrlHelper;

class AboutFragment$2
  implements AdapterView.OnItemClickListener
{
  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    switch (((SimpleMenuItem)paramAdapterView.getItemAtPosition(paramInt)).getStringResId())
    {
    default:
    case 2131231031:
    case 2131231032:
    case 2131231033:
    case 2131231034:
    case 2131231035:
    case 2131231036:
    }
    while (true)
    {
      return;
      this.this$0.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(WebUrlHelper.addAndroidVersionToUrl("http://help.instagram.com/", this.this$0.getActivity()))));
      continue;
      this.this$0.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://www.instagram.com/")));
      continue;
      this.this$0.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://blog.instagram.com/")));
      continue;
      SimpleWebViewActivity.show(this.this$0.getActivity(), WebUrlHelper.expandPath("/legal/terms/", false), true, this.this$0.getString(2131231034));
      continue;
      SimpleWebViewActivity.show(this.this$0.getActivity(), WebUrlHelper.expandPath("/legal/libraries/", false), true, this.this$0.getString(2131231035));
      continue;
      SimpleWebViewActivity.show(this.this$0.getActivity(), WebUrlHelper.expandPath("/legal/privacy/", false), true, this.this$0.getString(2131231036));
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.AboutFragment.2
 * JD-Core Version:    0.6.0
 */
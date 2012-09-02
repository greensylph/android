package com.instagram.android.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class SimpleWebViewFragment extends Fragment
  implements ActionBarConfigurer.ActionBarConfigurerFactory
{
  public static final String ARGUMENT_LOAD_SAME_HOST = "com.instagram.android.fragment.SimpleWebViewFragment.ARGUMENT_LOAD_SAME_HOST";
  public static final String ARGUMENT_TITLE = "com.instagram.android.fragment.SimpleWebViewFragment.ARGUMENT_TITLE";
  public static final String ARGUMENT_URL = "com.instagram.android.fragment.SimpleWebViewFragment.ARGUMENT_URL";
  private WebView mWebView;

  public ActionBarConfigurer getActionBarConfigurer()
  {
    return new SimpleWebViewFragment.2(this);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    String str1 = getArguments().getString("com.instagram.android.fragment.SimpleWebViewFragment.ARGUMENT_URL");
    String str2 = null;
    if (getArguments().getBoolean("com.instagram.android.fragment.SimpleWebViewFragment.ARGUMENT_LOAD_SAME_HOST"))
      str2 = Uri.parse(str1).getHost();
    this.mWebView = new WebView(getActivity());
    this.mWebView.getSettings().setJavaScriptEnabled(true);
    String str3 = str2;
    this.mWebView.setWebViewClient(new SimpleWebViewFragment.1(this, str3));
    this.mWebView.loadUrl(str1);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return this.mWebView;
  }

  public void onDestroy()
  {
    if (this.mWebView != null)
    {
      this.mWebView.destroy();
      this.mWebView = null;
    }
    super.onDestroy();
  }

  public void onPause()
  {
    super.onPause();
  }

  public void onResume()
  {
    super.onResume();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.SimpleWebViewFragment
 * JD-Core Version:    0.6.0
 */
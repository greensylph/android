package com.instagram.android.fragment;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import com.instagram.android.activity.NewsActivityInTab;
import com.instagram.android.fragment.ScrollToTopFragment.ScrollToTopFragment;
import com.instagram.android.service.ActionBarService;
import com.instagram.android.widget.EnhancedWebView;
import com.instagram.api.request.ApiUrlHelper;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NewsFragment extends Fragment
  implements ActionBarConfigurer.ActionBarConfigurerFactory, ScrollToTopFragment
{
  public static final String BROADCAST_SWITCH_TO_INBOX = "com.instagram.android.fragment.NewsFragment.BROADCAST_SWITCH_TO_INBOX";
  public static final String LOG_TAG = "NewsFragment";
  public static final Integer MODE_INBOX;
  public static final Integer MODE_NEWS = Integer.valueOf(0);
  private static final String STATE_CURRENT_MODE = "com.instagram.android.fragment.NewsFragment.STATE_CURRENT_MODE";
  private static boolean switchToInboxOnResume;
  private EnhancedWebView mCurrentWebView;
  private Button mInboxButton;
  private int mMode = MODE_INBOX.intValue();
  private Button mNewsButton;
  private Map<Integer, EnhancedWebView> mWebViewMap = new HashMap();
  private BroadcastReceiver pushReceiver = new NewsFragment.2(this);
  private BroadcastReceiver resumedReceiver = new NewsFragment.1(this);

  static
  {
    MODE_INBOX = Integer.valueOf(1);
    switchToInboxOnResume = false;
  }

  private EnhancedWebView createWebView(Integer paramInteger)
  {
    EnhancedWebView localEnhancedWebView = new EnhancedWebView(this, paramInteger);
    localEnhancedWebView.getSettings().setJavaScriptEnabled(true);
    localEnhancedWebView.setOnLoadingChangeListener(new NewsFragment.3(this));
    return localEnhancedWebView;
  }

  private String getWebViewUrl(int paramInt)
  {
    if (paramInt == MODE_INBOX.intValue());
    for (String str = "news/inbox/"; ; str = "news/")
      return str;
  }

  private EnhancedWebView initializeWebView(int paramInt, Bundle paramBundle)
  {
    EnhancedWebView localEnhancedWebView = (EnhancedWebView)this.mWebViewMap.get(Integer.valueOf(paramInt));
    if (paramBundle != null)
      localEnhancedWebView.restoreState(paramBundle);
    if (localEnhancedWebView.getUrl() == null)
      localEnhancedWebView.loadUrl(ApiUrlHelper.expandPath(getWebViewUrl(paramInt)));
    return localEnhancedWebView;
  }

  public static void setSwitchToInboxFlag()
  {
    switchToInboxOnResume = true;
  }

  private void switchToMode(int paramInt)
  {
    switchToMode(paramInt, false);
  }

  private void switchToMode(int paramInt, boolean paramBoolean)
  {
    this.mMode = paramInt;
    if (this.mCurrentWebView.equals(this.mWebViewMap.get(Integer.valueOf(paramInt))))
      if (paramBoolean)
        this.mCurrentWebView.loadUrl(ApiUrlHelper.expandPath(getWebViewUrl(this.mMode)));
    while (true)
    {
      return;
      EnhancedWebView localEnhancedWebView = this.mCurrentWebView;
      ViewGroup localViewGroup = (ViewGroup)localEnhancedWebView.getParent();
      this.mCurrentWebView = ((EnhancedWebView)this.mWebViewMap.get(Integer.valueOf(paramInt)));
      localViewGroup.removeView(localEnhancedWebView);
      localViewGroup.addView(this.mCurrentWebView);
      initializeWebView(paramInt, null);
      updateViewState(paramInt);
      ActionBarService.getInstance(getActivity()).forceUpdate();
    }
  }

  private void updateViewState(int paramInt)
  {
    boolean bool1 = true;
    Button localButton1 = this.mNewsButton;
    boolean bool2;
    Button localButton2;
    if (paramInt == MODE_NEWS.intValue())
    {
      bool2 = bool1;
      localButton1.setSelected(bool2);
      localButton2 = this.mInboxButton;
      if (paramInt != MODE_INBOX.intValue())
        break label55;
    }
    while (true)
    {
      localButton2.setSelected(bool1);
      return;
      bool2 = false;
      break;
      label55: bool1 = false;
    }
  }

  public ActionBarConfigurer getActionBarConfigurer()
  {
    return new NewsFragment.6(this);
  }

  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mWebViewMap.put(MODE_NEWS, createWebView(MODE_NEWS));
    this.mWebViewMap.put(MODE_INBOX, createWebView(MODE_INBOX));
    ActionBarService.getInstance(getActivity()).attach(getActionBarConfigurer());
    LocalBroadcastManager.getInstance(getActivity()).registerReceiver(this.pushReceiver, new IntentFilter("com.instagram.android.receiver.C2DMReceiver.NOTIFICATION_RECEIVED_BROADCAST_PROXY"));
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(2130903073, null);
    ViewGroup localViewGroup = (ViewGroup)localView.findViewById(2131492953);
    if (paramBundle != null)
      this.mMode = paramBundle.getInt("com.instagram.android.fragment.NewsFragment.STATE_CURRENT_MODE", MODE_INBOX.intValue());
    if (this.mCurrentWebView == null)
    {
      NewsActivityInTab.takeLoadInboxFlag();
      this.mCurrentWebView = initializeWebView(this.mMode, paramBundle);
    }
    localViewGroup.addView(this.mCurrentWebView);
    this.mNewsButton = ((Button)localView.findViewById(2131492951));
    this.mNewsButton.setOnClickListener(new NewsFragment.4(this));
    this.mInboxButton = ((Button)localView.findViewById(2131492952));
    this.mInboxButton.setOnClickListener(new NewsFragment.5(this));
    updateViewState(this.mMode);
    return localView;
  }

  public void onDestroy()
  {
    LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(this.pushReceiver);
    Iterator localIterator = this.mWebViewMap.values().iterator();
    while (localIterator.hasNext())
    {
      EnhancedWebView localEnhancedWebView = (EnhancedWebView)localIterator.next();
      if (localEnhancedWebView == null)
        continue;
      localEnhancedWebView.destroy();
    }
    this.mWebViewMap.clear();
    this.mCurrentWebView = null;
    super.onDestroy();
  }

  public void onDestroyView()
  {
    EnhancedWebView localEnhancedWebView = this.mCurrentWebView;
    ((ViewGroup)localEnhancedWebView.getParent()).removeView(localEnhancedWebView);
    localEnhancedWebView.setPictureListener(null);
    super.onDestroyView();
  }

  public void onPause()
  {
    super.onPause();
    LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(this.resumedReceiver);
  }

  public void onResume()
  {
    super.onResume();
    ((NewsActivityInTab)getActivity()).configureActionBar();
    LocalBroadcastManager.getInstance(getActivity()).registerReceiver(this.resumedReceiver, new IntentFilter("com.instagram.android.service.action_bar_refresh_clicked"));
    LocalBroadcastManager.getInstance(getActivity()).registerReceiver(this.resumedReceiver, new IntentFilter("com.instagram.android.fragment.NewsFragment.BROADCAST_SWITCH_TO_INBOX"));
    if (switchToInboxOnResume)
    {
      switchToInboxOnResume = false;
      if ((this.mCurrentWebView != null) && (this.mCurrentWebView != this.mWebViewMap.get(MODE_INBOX)))
        switchToMode(MODE_INBOX.intValue());
    }
  }

  public void onSaveInstanceState(Bundle paramBundle)
  {
    Iterator localIterator = this.mWebViewMap.values().iterator();
    while (localIterator.hasNext())
      ((EnhancedWebView)localIterator.next()).saveState(paramBundle);
    paramBundle.putInt("com.instagram.android.fragment.NewsFragment.STATE_CURRENT_MODE", this.mMode);
    super.onSaveInstanceState(paramBundle);
  }

  public void onStart()
  {
    super.onStart();
  }

  public void onStop()
  {
    super.onStop();
  }

  public void scrollToTop()
  {
    if (this.mCurrentWebView != null)
      this.mCurrentWebView.scrollToTop();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.NewsFragment
 * JD-Core Version:    0.6.0
 */
package com.instagram.android.widget;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import com.instagram.android.Log;
import com.instagram.android.activity.NewsActivityInTab;
import com.instagram.android.fragment.FeedFragment;
import com.instagram.android.fragment.NewsFragment;
import com.instagram.android.fragment.UserDetailFragment;
import com.instagram.android.service.CustomObjectMapper;
import com.instagram.util.FragmentUtil;
import java.util.HashSet;
import org.codehaus.jackson.JsonNode;

public class EnhancedWebView extends WebView
{
  public static final String BROADCAST_INBOX_SHOWN = "com.instagram.android.fragment.NewsFragment.BROADCAST_INBOX_SHOWN";
  public static final String LOG_TAG = "EnhancedWebView";
  private final Fragment mFragment;
  private boolean mIsLoading = false;
  private final HashSet<String> mMediaIdsSeen = new HashSet();
  private OnLoadingChangeListener mOnLoadingChangeListener;
  private final Integer mType;

  public EnhancedWebView(Fragment paramFragment, Integer paramInteger)
  {
    super(paramFragment.getActivity());
    this.mFragment = paramFragment;
    this.mType = paramInteger;
    setWebViewClient(new CustomWebViewClient(null));
  }

  private void sendViewVisibleBroadcast()
  {
    if (this.mType.equals(NewsFragment.MODE_INBOX))
      LocalBroadcastManager.getInstance(getContext()).sendBroadcast(new Intent("com.instagram.android.fragment.NewsFragment.BROADCAST_INBOX_SHOWN"));
  }

  public boolean isLoading()
  {
    return this.mIsLoading;
  }

  protected void onVisibilityChanged(View paramView, int paramInt)
  {
    if (isShown())
      sendViewVisibleBroadcast();
    super.onVisibilityChanged(paramView, paramInt);
  }

  protected void onWindowVisibilityChanged(int paramInt)
  {
    if (isShown())
      sendViewVisibleBroadcast();
    super.onWindowVisibilityChanged(paramInt);
  }

  public void scrollToTop()
  {
    loadUrl("javascript:window.scrollTo(0, 0)");
  }

  public void setOnLoadingChangeListener(OnLoadingChangeListener paramOnLoadingChangeListener)
  {
    this.mOnLoadingChangeListener = paramOnLoadingChangeListener;
  }

  private class CustomWebViewClient extends WebViewClient
  {
    private CustomWebViewClient()
    {
    }

    public void onPageFinished(WebView paramWebView, String paramString)
    {
      Log.d("EnhancedWebView", "Finished: " + paramString);
      EnhancedWebView.this.scrollToTop();
      EnhancedWebView.this.mMediaIdsSeen.clear();
      EnhancedWebView.access$202(EnhancedWebView.this, false);
      if (EnhancedWebView.this.mOnLoadingChangeListener != null)
        EnhancedWebView.this.mOnLoadingChangeListener.onLoadingChange(EnhancedWebView.this, EnhancedWebView.this.mIsLoading);
    }

    public void onPageStarted(WebView paramWebView, String paramString, Bitmap paramBitmap)
    {
      Log.d("EnhancedWebView", "Loading: " + paramString);
      if (EnhancedWebView.this.mType.equals(NewsFragment.MODE_INBOX))
        NewsActivityInTab.takeLoadInboxFlag();
      EnhancedWebView.access$202(EnhancedWebView.this, true);
      if (EnhancedWebView.this.mOnLoadingChangeListener != null)
        EnhancedWebView.this.mOnLoadingChangeListener.onLoadingChange(EnhancedWebView.this, EnhancedWebView.this.mIsLoading);
      super.onPageStarted(paramWebView, paramString, paramBitmap);
    }

    public void onReceivedError(WebView paramWebView, int paramInt, String paramString1, String paramString2)
    {
      if (paramInt != 503);
      try
      {
        paramWebView.stopLoading();
      }
      catch (Exception localException1)
      {
        try
        {
          while (true)
          {
            paramWebView.clearView();
            label15: paramWebView.loadData(Uri.encode("<html>" + "" + "</html>"), "text/html", "UTF-8");
            Toast.makeText(EnhancedWebView.this.getContext(), 2131230882, 1).show();
            super.onReceivedError(paramWebView, paramInt, paramString1, paramString2);
            return;
            localException1 = localException1;
          }
        }
        catch (Exception localException2)
        {
          break label15;
        }
      }
    }

    public boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString)
    {
      Uri localUri = Uri.parse(paramString);
      if (localUri.getScheme().equals("instagram"))
      {
        if (!localUri.getHost().equals("media"))
          break label291;
        if ((EnhancedWebView.this.mFragment != null) && (EnhancedWebView.this.mFragment.isResumed()))
        {
          Bundle localBundle2 = new Bundle();
          String str = localUri.getQueryParameter("id");
          localBundle2.putString("com.instagram.android.fragment.ARGUMENTS_KEY_EXTRA_MEDIA_ID", str);
          if (!EnhancedWebView.this.mMediaIdsSeen.contains(str))
          {
            localBundle2.putBoolean("com.instagram.android.fragment.ARGUMENTS_KEY_LOAD_FROM_NETWORK", true);
            EnhancedWebView.this.mMediaIdsSeen.add(str);
          }
          FragmentUtil.navigateTo(EnhancedWebView.this.mFragment.getFragmentManager(), new FeedFragment(), localBundle2);
        }
        if (localUri.getHost().equals("toast"))
        {
          Log.d("EnhancedWebView", "Received toast: " + paramString);
          if (EnhancedWebView.this.isShown())
            break label407;
        }
      }
      while (true)
      {
        try
        {
          JsonNode localJsonNode = CustomObjectMapper.getInstance(EnhancedWebView.this.getContext()).readTree(localUri.getQueryParameter("counts"));
          Intent localIntent = new Intent("com.instagram.android.activity.MainTabActivity.BROADCAST_TOAST");
          localIntent.putExtra("com.instagram.android.activity.MainTabActivity.EXTRA_BROADCAST_TOAST_LIKES", localJsonNode.get("likes").asInt());
          localIntent.putExtra("com.instagram.android.activity.MainTabActivity.EXTRA_BROADCAST_TOAST_COMMENTS", localJsonNode.get("comments").asInt());
          localIntent.putExtra("com.instagram.android.activity.MainTabActivity.EXTRA_BROADCAST_TOAST_RELATIONSHIPS", localJsonNode.get("relationships").asInt());
          LocalBroadcastManager.getInstance(EnhancedWebView.this.getContext()).sendBroadcast(localIntent);
          return true;
          label291: if ((!localUri.getHost().equals("user")) || (EnhancedWebView.this.mFragment == null) || (!EnhancedWebView.this.mFragment.isResumed()))
            break;
          Bundle localBundle1 = new Bundle();
          localBundle1.putString("com.instagram.android.fragment.UserDetailFragment.EXTRA_USER_NAME", localUri.getQueryParameter("username"));
          FragmentUtil.navigateTo(EnhancedWebView.this.mFragment.getFragmentManager(), new UserDetailFragment(), localBundle1);
        }
        catch (Exception localException)
        {
          Log.w("EnhancedWebView", "Error parsing json from url: " + paramString);
          continue;
        }
        label407: Log.d("EnhancedWebView", "Webview is visible, not showing toast");
      }
    }
  }

  public static abstract interface OnLoadingChangeListener
  {
    public abstract void onLoadingChange(EnhancedWebView paramEnhancedWebView, boolean paramBoolean);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.widget.EnhancedWebView
 * JD-Core Version:    0.6.0
 */
package com.instagram.android.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import com.instagram.android.Preferences;
import java.util.HashMap;

public class NavBarFragment extends Fragment
{
  TabHost mTabHost;
  TabManager mTabManager;

  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    FragmentManager.enableDebugLogging(true);
    if (this.mTabHost == null)
    {
      this.mTabHost = ((TabHost)getView().findViewById(16908306));
      this.mTabHost.setup();
    }
    if (this.mTabManager == null)
    {
      this.mTabManager = new TabManager(this, this.mTabHost, 2131492901);
      this.mTabManager.addTab(this.mTabHost.newTabSpec("feed").setIndicator("Feed"), FeedFragment.class, null);
      this.mTabManager.addTab(this.mTabHost.newTabSpec("popular").setIndicator("Popular"), PopularAltFragment.class, null);
      this.mTabManager.addTab(this.mTabHost.newTabSpec("news").setIndicator("News"), NewsFragment.class, null);
      Bundle localBundle = new Bundle();
      localBundle.putString("com.instagram.android.fragment.UserDetailFragment.EXTRA_USER_ID", Preferences.getInstance(getActivity()).getUserId());
      this.mTabManager.addTab(this.mTabHost.newTabSpec("profile").setIndicator("Profile"), UserDetailFragment.class, localBundle);
    }
    if (paramBundle != null)
      this.mTabHost.setCurrentTab(paramBundle.getInt("tabState"));
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903089, paramViewGroup, false);
  }

  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putInt("tabState", this.mTabHost.getCurrentTab());
  }

  public static class TabManager
    implements TabHost.OnTabChangeListener
  {
    private final Fragment mActivity;
    private final int mContainerId;
    TabInfo mLastTab;
    private final TabHost mTabHost;
    private final HashMap<String, TabInfo> mTabs = new HashMap();

    public TabManager(Fragment paramFragment, TabHost paramTabHost, int paramInt)
    {
      this.mActivity = paramFragment;
      this.mTabHost = paramTabHost;
      this.mContainerId = paramInt;
      this.mTabHost.setOnTabChangedListener(this);
    }

    public void addTab(TabHost.TabSpec paramTabSpec, Class<?> paramClass, Bundle paramBundle)
    {
      paramTabSpec.setContent(new DummyTabFactory(this.mActivity.getActivity()));
      String str = paramTabSpec.getTag();
      TabInfo localTabInfo = new TabInfo(str, paramClass, paramBundle);
      TabInfo.access$002(localTabInfo, this.mActivity.getFragmentManager().findFragmentByTag(str));
      if ((localTabInfo.fragment != null) && (!localTabInfo.fragment.isDetached()))
      {
        FragmentTransaction localFragmentTransaction = this.mActivity.getFragmentManager().beginTransaction();
        localFragmentTransaction.detach(localTabInfo.fragment);
        localFragmentTransaction.commit();
      }
      this.mTabs.put(str, localTabInfo);
      this.mTabHost.addTab(paramTabSpec);
    }

    public void onTabChanged(String paramString)
    {
      TabInfo localTabInfo = (TabInfo)this.mTabs.get(paramString);
      this.mActivity.getFragmentManager().popBackStack();
      FragmentTransaction localFragmentTransaction;
      if (this.mLastTab != localTabInfo)
      {
        localFragmentTransaction = this.mActivity.getFragmentManager().beginTransaction();
        if ((this.mLastTab != null) && (this.mLastTab.fragment != null))
          localFragmentTransaction.detach(this.mLastTab.fragment);
        if (localTabInfo != null)
        {
          if (localTabInfo.fragment != null)
            break label146;
          TabInfo.access$002(localTabInfo, Fragment.instantiate(this.mActivity.getActivity(), localTabInfo.clss.getName(), localTabInfo.args));
          localFragmentTransaction.add(this.mContainerId, localTabInfo.fragment, localTabInfo.tag);
        }
      }
      while (true)
      {
        this.mLastTab = localTabInfo;
        localFragmentTransaction.commit();
        this.mActivity.getFragmentManager().executePendingTransactions();
        return;
        label146: localFragmentTransaction.attach(localTabInfo.fragment);
      }
    }

    static class DummyTabFactory
      implements TabHost.TabContentFactory
    {
      private final Context mContext;

      public DummyTabFactory(Context paramContext)
      {
        this.mContext = paramContext;
      }

      public View createTabContent(String paramString)
      {
        View localView = new View(this.mContext);
        localView.setMinimumWidth(0);
        localView.setMinimumHeight(0);
        return localView;
      }
    }

    static final class TabInfo
    {
      private final Bundle args;
      private final Class<?> clss;
      private Fragment fragment;
      private final String tag;

      TabInfo(String paramString, Class<?> paramClass, Bundle paramBundle)
      {
        this.tag = paramString;
        this.clss = paramClass;
        this.args = paramBundle;
      }
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.NavBarFragment
 * JD-Core Version:    0.6.0
 */
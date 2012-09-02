package com.instagram.android.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.instagram.android.adapter.SuggestedUserAdapter;
import com.instagram.android.model.SuggestedUser;
import com.instagram.android.model.User;
import com.instagram.android.service.ActionBarService;
import com.instagram.android.service.AppContext;
import com.instagram.api.AbstractStreamingApiCallbacks;
import com.instagram.api.ApiResponse;
import com.instagram.api.request.FetchBulkFollowingStatusRequest;
import com.instagram.api.request.SuggestedUserListRequest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SuggestedUserFragment extends ListFragment
  implements ActionBarConfigurer.ActionBarConfigurerFactory
{
  private SuggestedUserAdapter mAdapter;
  private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramContext, Intent paramIntent)
    {
      String str = paramIntent.getAction();
      if (str.equals("com.instagram.android.service.action_bar_clicked"))
        SuggestedUserFragment.this.scrollToTop();
      while (true)
      {
        return;
        if ((str.equals("com.instagram.android.service.action_bar_refresh_clicked")) && (!SuggestedUserFragment.this.mIsLoading) && (SuggestedUserFragment.this.mInitialNetworkRequestFinished))
        {
          SuggestedUserFragment.this.fetchData(true);
          continue;
        }
      }
    }
  };
  private boolean mInitialNetworkRequestFinished;
  private boolean mIsLoading;
  private ReceivedStatusUpdateHandler mRefreshStatusHandler = new ReceivedStatusUpdateHandler(null);
  private BroadcastReceiver receiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramContext, Intent paramIntent)
    {
      SuggestedUserFragment.this.mRefreshStatusHandler.removeMessages(0);
      SuggestedUserFragment.this.mRefreshStatusHandler.sendEmptyMessageDelayed(0, 300L);
    }
  };

  private void fetchData(boolean paramBoolean)
  {
    if (((!this.mInitialNetworkRequestFinished) || (paramBoolean)) && (!this.mIsLoading))
      new SuggestedUserListRequest(getActivity(), getLoaderManager(), 0, isSignUpFlow(), new AbstractStreamingApiCallbacks()
      {
        protected void onRequestFail(ApiResponse<ArrayList<SuggestedUser>> paramApiResponse)
        {
          SuggestedUserFragment.access$402(SuggestedUserFragment.this, true);
          Toast.makeText(SuggestedUserFragment.this.getActivity(), 2131230882, 0).show();
          super.onRequestFail(paramApiResponse);
        }

        public void onRequestFinished()
        {
          SuggestedUserFragment.access$502(SuggestedUserFragment.this, false);
          SuggestedUserFragment.access$402(SuggestedUserFragment.this, true);
          ActionBarService.getInstance(SuggestedUserFragment.this.getActivity()).setIsLoading(false);
          SuggestedUserFragment.this.updateProgressBarState();
        }

        public void onRequestStart()
        {
          SuggestedUserFragment.access$502(SuggestedUserFragment.this, true);
          ActionBarService.getInstance(SuggestedUserFragment.this.getActivity()).setIsLoading(true);
          SuggestedUserFragment.this.updateProgressBarState();
        }

        protected void onSuccess(ArrayList<SuggestedUser> paramArrayList)
        {
          if (paramArrayList == null)
            paramArrayList = new ArrayList();
          SuggestedUserFragment.this.mAdapter.setSuggestedUsers(paramArrayList);
          ArrayList localArrayList = new ArrayList(paramArrayList.size());
          Iterator localIterator = paramArrayList.iterator();
          while (localIterator.hasNext())
            localArrayList.add(((SuggestedUser)localIterator.next()).getUser());
          if (localArrayList.size() > 0)
            SuggestedUserFragment.this.fetchFollowStatusInBulk(localArrayList);
        }
      }).perform();
  }

  private boolean isSignUpFlow()
  {
    if ((getArguments() != null) && (getArguments().getBoolean("com.instagram.android.fragment.FindFriendsFragment.ARGUMENT_IS_SIGN_UP_FLOW")));
    for (int i = 1; ; i = 0)
      return i;
  }

  public void fetchFollowStatusInBulk(Iterable<User> paramIterable)
  {
    new FetchBulkFollowingStatusRequest(getActivity(), getLoaderManager(), 1, null).performForUsers(paramIterable);
  }

  public ActionBarConfigurer getActionBarConfigurer()
  {
    return new ActionBarConfigurer()
    {
      public View customTitleView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup)
      {
        View localView = null;
        if (SuggestedUserFragment.this.isSignUpFlow())
        {
          localView = paramLayoutInflater.inflate(2130903051, paramViewGroup, false);
          ((Button)localView.findViewById(2131492898)).setOnClickListener(new SuggestedUserFragment.4.1(this));
          ((TextView)localView.findViewById(2131492876)).setText(getTitle());
        }
        return localView;
      }

      public String getTitle()
      {
        return SuggestedUserFragment.this.getString(2131230928);
      }

      public boolean isLoading()
      {
        return SuggestedUserFragment.this.mIsLoading;
      }

      public boolean showBackButton()
      {
        if (SuggestedUserFragment.this.getFragmentManager().getBackStackEntryCount() > 0);
        for (int i = 1; ; i = 0)
          return i;
      }

      public boolean showRefreshButton()
      {
        return true;
      }
    };
  }

  protected SuggestedUserAdapter getAdapter()
  {
    FragmentActivity localFragmentActivity;
    LoaderManager localLoaderManager;
    FragmentManager localFragmentManager;
    if (this.mAdapter == null)
    {
      localFragmentActivity = getActivity();
      localLoaderManager = getLoaderManager();
      localFragmentManager = getFragmentManager();
      if (isSignUpFlow())
        break label53;
    }
    label53: for (boolean bool = true; ; bool = false)
    {
      this.mAdapter = new SuggestedUserAdapter(localFragmentActivity, localLoaderManager, localFragmentManager, bool);
      return this.mAdapter;
    }
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setListAdapter(getAdapter());
    getAdapter().registerDataSetObserver(new DataSetObserver()
    {
      public void onChanged()
      {
        LocalBroadcastManager.getInstance(SuggestedUserFragment.this.getActivity()).unregisterReceiver(SuggestedUserFragment.this.receiver);
        Iterator localIterator = SuggestedUserFragment.this.getAdapter().getSuggestedUsers().iterator();
        while (localIterator.hasNext())
        {
          SuggestedUser localSuggestedUser = (SuggestedUser)localIterator.next();
          LocalBroadcastManager.getInstance(SuggestedUserFragment.this.getActivity()).registerReceiver(SuggestedUserFragment.this.receiver, new IntentFilter(User.getUserFollowUpdateBroadcastId(((SuggestedUser)localSuggestedUser).getUser().getId())));
        }
      }

      public void onInvalidated()
      {
      }
    });
    fetchData(false);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903087, paramViewGroup, false);
  }

  public void onDestroy()
  {
    LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(this.receiver);
    super.onDestroy();
  }

  public void onDestroyView()
  {
    super.onDestroyView();
  }

  public void onPause()
  {
    super.onPause();
    LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(this.mBroadcastReceiver);
  }

  public void onResume()
  {
    super.onResume();
    LocalBroadcastManager localLocalBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
    localLocalBroadcastManager.registerReceiver(this.mBroadcastReceiver, new IntentFilter("com.instagram.android.service.action_bar_clicked"));
    localLocalBroadcastManager.registerReceiver(this.mBroadcastReceiver, new IntentFilter("com.instagram.android.service.action_bar_refresh_clicked"));
  }

  public void scrollToTop()
  {
    getListView().smoothScrollToPosition(0);
  }

  protected void updateProgressBarState()
  {
    if (this.mIsLoading)
      if (getView() != null)
      {
        View localView2 = getView().findViewById(2131492983);
        localView2.startAnimation(AnimationUtils.loadAnimation(AppContext.getContext(), 2130968585));
        localView2.setVisibility(0);
      }
    while (true)
    {
      return;
      if (getView() != null)
      {
        View localView1 = getView().findViewById(2131492983);
        localView1.setVisibility(8);
        localView1.clearAnimation();
        continue;
      }
    }
  }

  private class ReceivedStatusUpdateHandler extends Handler
  {
    private ReceivedStatusUpdateHandler()
    {
    }

    public void handleMessage(Message paramMessage)
    {
      SuggestedUserFragment.this.mAdapter.notifyDataSetChanged();
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.SuggestedUserFragment
 * JD-Core Version:    0.6.0
 */
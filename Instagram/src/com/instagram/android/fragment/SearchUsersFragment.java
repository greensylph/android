package com.instagram.android.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.LoaderManager;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import com.instagram.android.Preferences;
import com.instagram.android.adapter.UserListAdapter;
import com.instagram.android.model.User;
import com.instagram.android.service.ActionBarService;
import com.instagram.android.service.AppContext;
import com.instagram.api.AbstractStreamingApiCallbacks;
import com.instagram.api.request.FetchBulkFollowingStatusRequest;
import com.instagram.api.request.UserListRequest;
import com.instagram.util.FragmentUtil;
import java.util.ArrayList;

public class SearchUsersFragment extends SearchFragment
{
  protected UserListAdapter mAdapter;
  private FetchBulkFollowingStatusRequest mFetchBulkFollowingStatusRequest;
  private Handler mHandler = new Handler();
  private final AdapterView.OnItemClickListener mUserItemClickListener = new AdapterView.OnItemClickListener()
  {
    public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
    {
      User localUser = (User)paramAdapterView.getItemAtPosition(paramInt);
      Preferences.getInstance(AppContext.getContext()).saveRecentUser(localUser);
      Bundle localBundle = new Bundle();
      localBundle.putString("com.instagram.android.fragment.UserDetailFragment.EXTRA_USER_ID", localUser.getId());
      UserDetailFragment localUserDetailFragment = new UserDetailFragment();
      FragmentUtil.navigateTo(SearchUsersFragment.this.getFragmentManager(), localUserDetailFragment, localBundle);
    }
  };
  private final AbstractStreamingApiCallbacks<ArrayList<User>> mUserListRequestCallbacks = new AbstractStreamingApiCallbacks()
  {
    public void onRequestFinished()
    {
      SearchUsersFragment.this.mIsLoading = false;
      ActionBarService.getInstance(AppContext.getContext()).setIsLoading(false);
      SearchUsersFragment.this.updateProgressBarState();
    }

    public void onRequestStart()
    {
      SearchUsersFragment.this.mIsLoading = true;
      ActionBarService.getInstance(AppContext.getContext()).setIsLoading(true);
      SearchUsersFragment.this.updateProgressBarState();
    }

    protected void onSuccess(ArrayList<User> paramArrayList)
    {
      SearchUsersFragment.this.mAdapter.setItems(paramArrayList);
      SearchUsersFragment.this.setSelection(0);
      SearchUsersFragment.this.getFetchBulkFollowingStatusRequest().performForUsers(paramArrayList);
    }
  };
  private UserListSearchRequest mUserListSearchRequest;

  private FetchBulkFollowingStatusRequest getFetchBulkFollowingStatusRequest()
  {
    if (this.mFetchBulkFollowingStatusRequest == null)
      this.mFetchBulkFollowingStatusRequest = new FetchBulkFollowingStatusRequest(AppContext.getContext(), getCompositeLoaderManager(), 1, null);
    return this.mFetchBulkFollowingStatusRequest;
  }

  private void updateProgressBarState()
  {
    this.mHandler.post(new Runnable()
    {
      public void run()
      {
        if (SearchUsersFragment.this.mIsLoading)
          if (SearchUsersFragment.this.getView() != null)
          {
            View localView2 = SearchUsersFragment.this.getView().findViewById(2131492983);
            localView2.startAnimation(AnimationUtils.loadAnimation(AppContext.getContext(), 2130968585));
            localView2.setVisibility(0);
          }
        while (true)
        {
          return;
          if (SearchUsersFragment.this.getView() != null)
          {
            View localView1 = SearchUsersFragment.this.getView().findViewById(2131492983);
            localView1.setVisibility(8);
            localView1.clearAnimation();
            continue;
          }
        }
      }
    });
  }

  protected BaseAdapter getAdapter()
  {
    if (this.mAdapter == null)
    {
      this.mAdapter = new UserListAdapter(AppContext.getContext());
      this.mAdapter.setShowFollowButtons(true);
      this.mAdapter.setLoaderManager(getCompositeLoaderManager());
      ArrayList localArrayList = Preferences.getInstance(AppContext.getContext()).getRecentUserSearches();
      if ((localArrayList != null) && (!localArrayList.isEmpty()))
      {
        this.mAdapter.setItems(localArrayList);
        getFetchBulkFollowingStatusRequest().performForUsers(localArrayList);
      }
    }
    return this.mAdapter;
  }

  protected int getHintResource()
  {
    return 2131231012;
  }

  protected AdapterView.OnItemClickListener getItemClickListener()
  {
    return this.mUserItemClickListener;
  }

  protected int getMode()
  {
    return 1;
  }

  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mUserListSearchRequest = new UserListSearchRequest(AppContext.getContext(), getCompositeLoaderManager(), 0, this.mUserListRequestCallbacks);
    getFetchBulkFollowingStatusRequest();
  }

  public void onDestroy()
  {
    if (this.mAdapter != null)
      this.mAdapter.onDestroy();
  }

  protected void performQuery(String paramString)
  {
    this.mUserListSearchRequest.perform(paramString);
  }

  private class UserListSearchRequest extends UserListRequest
  {
    public UserListSearchRequest(LoaderManager paramInt, int paramAbstractStreamingApiCallbacks, AbstractStreamingApiCallbacks<ArrayList<User>> arg4)
    {
      super(paramAbstractStreamingApiCallbacks, i, localAbstractStreamingApiCallbacks);
    }

    protected String getPath()
    {
      return "users/search/";
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.SearchUsersFragment
 * JD-Core Version:    0.6.0
 */
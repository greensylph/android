package com.instagram.android.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.instagram.android.Preferences;
import com.instagram.android.adapter.UserListAdapter;
import com.instagram.android.model.User;
import com.instagram.android.service.ActionBarService;
import com.instagram.android.service.AppContext;
import com.instagram.api.AbstractStreamingApiCallbacks;
import com.instagram.api.ApiResponse;
import com.instagram.api.RequestParams;
import com.instagram.api.request.FetchBulkFollowingStatusRequest;
import com.instagram.api.request.FollowAllRequest;
import com.instagram.api.request.UserListRequest;
import com.instagram.twitter.TwitterAccount;
import com.instagram.util.ContactHelper;
import com.instagram.util.FragmentUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public class UserListFragment extends ListFragment
  implements ActionBarConfigurer.ActionBarConfigurerFactory
{
  public static final String ARGUMENTS_ADDRESSBOOK = "com.instagram.android.fragment.UserListFragment.ARGUMENTS_ADDRESSBOOK";
  public static final String ARGUMENTS_CLICK_THROUGH = "com.instagram.android.fragment.UserListFragment.ARGUMENTS_CLICK_THROUGH";
  public static final String ARGUMENTS_FACEBOOK = "com.instagram.android.fragment.UserListFragment.ARGUMENTS_FACEBOOK";
  public static final String ARGUMENTS_FETCH_URL = "com.instagram.android.fragment.UserListFragment.ARGUMENTS_FETCH_URL";
  public static final String ARGUMENTS_FOLLOW_ALL_BUTTON = "com.instagram.android.fragment.UserListFragment.ARGUMENTS_FOLLOW_ALL_BUTTON";
  public static final String ARGUMENTS_FOLLOW_BUTTONS = "com.instagram.android.fragment.UserListFragment.ARGUMENTS_FOLLOW_BUTTONS";
  public static final String ARGUMENTS_SEARCH_STRING = "com.instagram.android.fragment.UserListFragment.ARGUMENTS_SEARCH_STRING";
  public static final String ARGUMENTS_SEARCH_URL = "com.instagram.android.fragment.UserListFragment.ARGUMENTS_SEARCH_URL";
  public static final String ARGUMENTS_TITLE = "com.instagram.android.fragment.UserListFragment.ARGUMENTS_TITLE";
  public static final String ARGUMENTS_TWITTER = "com.instagram.android.fragment.UserListFragment.ARGUMENTS_TWITTER";
  private static final int MAX_USERS_BEFORE_PROMPT = 50;
  private UserListAdapter mAdapter;
  private boolean mClickThrough = true;
  private Button mFollowAllButton;
  private Handler mHandler = new Handler();
  private boolean mIsLoading = false;
  private boolean mShowFollowAllButton;
  private boolean mShowFollowButtons;
  private String mTitle;
  private AbstractStreamingApiCallbacks<ArrayList<User>> userListCallbacks = new AbstractStreamingApiCallbacks()
  {
    public void onRequestFail(ApiResponse<ArrayList<User>> paramApiResponse)
    {
      Toast.makeText(UserListFragment.this.getActivity(), 2131231022, 1).show();
    }

    public void onRequestFinished()
    {
      UserListFragment.access$502(UserListFragment.this, false);
      ActionBarService.getInstance(UserListFragment.this.getActivity()).setIsLoading(false);
      UserListFragment.this.updateProgressBarState();
    }

    public void onRequestStart()
    {
      UserListFragment.access$502(UserListFragment.this, true);
      ActionBarService.getInstance(UserListFragment.this.getActivity()).setIsLoading(true);
      UserListFragment.this.updateProgressBarState();
    }

    protected void onSuccess(ArrayList<User> paramArrayList)
    {
      UserListFragment.this.mAdapter.setItems(paramArrayList);
      if (UserListFragment.this.mShowFollowButtons)
        UserListFragment.this.fetchFollowStatusInBulk(paramArrayList);
      if ((UserListFragment.this.mShowFollowAllButton) && (paramArrayList != null) && (paramArrayList.size() > 0))
        UserListFragment.this.mHandler.post(new UserListFragment.2.1(this));
      ActionBarService.getInstance(UserListFragment.this.getActivity()).forceUpdate();
    }
  };

  private void followAllFriends(View paramView)
  {
    paramView.setEnabled(false);
    new FollowAllRequest(getActivity(), getLoaderManager(), new FollowAllRequestCallbacks(null)).performForUsers(this.mAdapter.getUsers());
  }

  private void updateProgressBarState()
  {
    this.mHandler.post(new Runnable()
    {
      public void run()
      {
        if (UserListFragment.this.mIsLoading)
          if (UserListFragment.this.getView() != null)
          {
            View localView2 = UserListFragment.this.getView().findViewById(2131492983);
            localView2.startAnimation(AnimationUtils.loadAnimation(AppContext.getContext(), 2130968585));
            localView2.setVisibility(0);
          }
        while (true)
        {
          return;
          if (UserListFragment.this.getView() != null)
          {
            View localView1 = UserListFragment.this.getView().findViewById(2131492983);
            localView1.setVisibility(8);
            localView1.clearAnimation();
            continue;
          }
        }
      }
    });
  }

  public void fetchData()
  {
    if (getArguments().containsKey("com.instagram.android.fragment.UserListFragment.ARGUMENTS_FETCH_URL"))
    {
      String str3 = getArguments().getString("com.instagram.android.fragment.UserListFragment.ARGUMENTS_FETCH_URL");
      new UserListRequest(getActivity(), getLoaderManager(), 0, this.userListCallbacks, str3)
      {
        protected String getPath()
        {
          return this.val$fetchUrl;
        }
      }
      .perform();
    }
    while (true)
    {
      return;
      if ((getArguments().containsKey("com.instagram.android.fragment.UserListFragment.ARGUMENTS_SEARCH_URL")) && (getArguments().containsKey("com.instagram.android.fragment.UserListFragment.ARGUMENTS_SEARCH_STRING")))
      {
        String str1 = getArguments().getString("com.instagram.android.fragment.UserListFragment.ARGUMENTS_SEARCH_URL");
        String str2 = getArguments().getString("com.instagram.android.fragment.UserListFragment.ARGUMENTS_SEARCH_STRING");
        6 local6 = new UserListRequest(getActivity(), getLoaderManager(), 0, this.userListCallbacks, str1)
        {
          protected String getPath()
          {
            return this.val$searchUrl;
          }
        };
        local6.perform(str2);
        continue;
      }
      if (getArguments().containsKey("com.instagram.android.fragment.UserListFragment.ARGUMENTS_ADDRESSBOOK"))
      {
        new UserListRequest(getActivity(), getLoaderManager(), 0, this.userListCallbacks)
        {
          protected String getPath()
          {
            return "address_book/find/";
          }

          protected boolean isSecure()
          {
            return true;
          }

          public void preProcessInBackground()
          {
            getParams().put("contacts", ContactHelper.getJsonString(ContactHelper.getContacts(getContext())));
          }
        }
        .perform();
        continue;
      }
      if (getArguments().containsKey("com.instagram.android.fragment.UserListFragment.ARGUMENTS_FACEBOOK"))
      {
        new UserListRequest(getActivity(), getLoaderManager(), 0, this.userListCallbacks)
        {
          protected String getPath()
          {
            return "fb/find/";
          }

          public void perform()
          {
            getParams().put("fb_access_token", UserListFragment.this.getArguments().getString("com.instagram.android.fragment.UserListFragment.ARGUMENTS_FACEBOOK"));
            super.perform();
          }
        }
        .perform();
        continue;
      }
      if (!getArguments().containsKey("com.instagram.android.fragment.UserListFragment.ARGUMENTS_TWITTER"))
        continue;
      new UserListRequest(getActivity(), getLoaderManager(), 0, this.userListCallbacks)
      {
        protected String getPath()
        {
          return "twitter/find/";
        }

        public void perform()
        {
          Iterator localIterator = TwitterAccount.get(getContext()).getSharingInfo().entrySet().iterator();
          while (localIterator.hasNext())
          {
            Map.Entry localEntry = (Map.Entry)localIterator.next();
            getParams().put((String)localEntry.getKey(), (String)localEntry.getValue());
          }
          super.perform();
        }
      }
      .perform();
    }
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
        View localView;
        if ((UserListFragment.this.mShowFollowAllButton) && (UserListFragment.this.mAdapter != null) && (!UserListFragment.this.mAdapter.isEmpty()))
        {
          localView = paramLayoutInflater.inflate(2130903046, paramViewGroup, false);
          UserListFragment.access$302(UserListFragment.this, (Button)localView.findViewById(2131492895));
          UserListFragment.this.mFollowAllButton.setVisibility(8);
          UserListFragment.this.mFollowAllButton.setOnClickListener(new UserListFragment.1.1(this, localView));
          ((TextView)localView.findViewById(2131492876)).setText(getTitle());
        }
        while (true)
        {
          return localView;
          localView = null;
        }
      }

      public String getTitle()
      {
        return UserListFragment.this.mTitle;
      }

      public boolean isLoading()
      {
        return UserListFragment.this.mIsLoading;
      }

      public boolean showBackButton()
      {
        if (UserListFragment.this.getFragmentManager().getBackStackEntryCount() > 0);
        for (int i = 1; ; i = 0)
          return i;
      }

      public boolean showRefreshButton()
      {
        return false;
      }
    };
  }

  public void onCreate(Bundle paramBundle)
  {
    boolean bool1 = false;
    super.onCreate(paramBundle);
    boolean bool2;
    if ((Preferences.getInstance(getActivity()).isLoggedIn()) && (getArguments().getBoolean("com.instagram.android.fragment.UserListFragment.ARGUMENTS_FOLLOW_ALL_BUTTON", false)))
    {
      bool2 = true;
      this.mShowFollowAllButton = bool2;
      if ((Preferences.getInstance(getActivity()).isLoggedIn()) && (getArguments().getBoolean("com.instagram.android.fragment.UserListFragment.ARGUMENTS_FOLLOW_BUTTONS", false)))
        bool1 = true;
      this.mShowFollowButtons = bool1;
      this.mClickThrough = getArguments().getBoolean("com.instagram.android.fragment.UserListFragment.ARGUMENTS_CLICK_THROUGH", true);
      if (!getArguments().containsKey("com.instagram.android.fragment.UserListFragment.ARGUMENTS_TITLE"))
        break label174;
    }
    label174: for (String str = getArguments().getString("com.instagram.android.fragment.UserListFragment.ARGUMENTS_TITLE"); ; str = null)
    {
      this.mTitle = str;
      this.mAdapter = new UserListAdapter(getActivity().getApplicationContext());
      this.mAdapter.setShowFollowButtons(this.mShowFollowButtons);
      this.mAdapter.setLoaderManager(getLoaderManager());
      setListAdapter(this.mAdapter);
      fetchData();
      return;
      bool2 = false;
      break;
    }
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903087, paramViewGroup, false);
  }

  public void onDestroy()
  {
    this.mAdapter.onDestroy();
    super.onDestroy();
  }

  public void onPause()
  {
    super.onPause();
  }

  public void onResume()
  {
    super.onResume();
    if (this.mFollowAllButton != null)
      this.mFollowAllButton.setVisibility(0);
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    getListView().setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
      {
        if (UserListFragment.this.mClickThrough)
        {
          User localUser = (User)paramAdapterView.getItemAtPosition(paramInt);
          Bundle localBundle = new Bundle();
          localBundle.putString("com.instagram.android.fragment.UserDetailFragment.EXTRA_USER_ID", localUser.getId());
          FragmentUtil.navigateTo(UserListFragment.this.getFragmentManager(), new UserDetailFragment(), localBundle);
        }
      }
    });
  }

  private class FollowAllRequestCallbacks extends AbstractStreamingApiCallbacks<Object>
  {
    private FollowAllRequestCallbacks()
    {
    }

    protected void onRequestFail(ApiResponse<Object> paramApiResponse)
    {
      Toast.makeText(UserListFragment.this.getActivity(), 2131231022, 1).show();
    }

    public void onRequestFinished()
    {
      UserListFragment.access$502(UserListFragment.this, false);
      UserListFragment.this.updateProgressBarState();
    }

    public void onRequestStart()
    {
      UserListFragment.access$502(UserListFragment.this, true);
      UserListFragment.this.updateProgressBarState();
    }

    protected void onSuccess(Object paramObject)
    {
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.UserListFragment
 * JD-Core Version:    0.6.0
 */
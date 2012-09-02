package com.instagram.android.fragment;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.instagram.android.adapter.FeedAdapter.ViewMode;
import com.instagram.android.adapter.UserDetailFeedAdapter;
import com.instagram.android.model.MediaFeedResponse;
import com.instagram.android.model.User;
import com.instagram.android.service.ActionBarService;
import com.instagram.android.service.AuthHelper;
import com.instagram.android.service.UserStore;
import com.instagram.android.widget.IgDialogBuilder;
import com.instagram.api.AbstractStreamingApiCallbacks;
import com.instagram.api.ApiResponse;
import com.instagram.api.ApiResponseStatus;
import com.instagram.api.request.FlagHelper;
import com.instagram.api.request.FlagHelper.FlagType;
import com.instagram.api.request.FlagHelperBuilder;
import com.instagram.api.request.MediaFeedRequest;
import com.instagram.api.request.UserDetailRequest;
import com.instagram.api.request.UserMediaFeedRequest;
import com.instagram.util.StringUtil;

public class UserDetailFragment extends FeedFragment
{
  public static final String EXTRA_CURRENT_USER = "com.instagram.android.fragment.UserDetailFragment.EXTRA_CURRENT_USER";
  public static final String EXTRA_USER_ID = "com.instagram.android.fragment.UserDetailFragment.EXTRA_USER_ID";
  public static final String EXTRA_USER_NAME = "com.instagram.android.fragment.UserDetailFragment.EXTRA_USER_NAME";
  private static boolean sCurrentUserNeedsReload;
  private DialogInterface.OnClickListener itemClickListener = new DialogInterface.OnClickListener()
  {
    public void onClick(DialogInterface paramDialogInterface, int paramInt)
    {
      paramDialogInterface.dismiss();
      CharSequence[] arrayOfCharSequence = UserDetailFragment.this.getMenuOptions();
      if (arrayOfCharSequence[paramInt].equals(UserDetailFragment.this.getResources().getString(2131230962)))
        new FlagHelperBuilder().setFlagType(FlagHelper.FlagType.User).setContext(UserDetailFragment.this.getActivity()).setFragmentManager(UserDetailFragment.this.getFragmentManager()).setLoaderManager(UserDetailFragment.this.getLoaderManager()).setItemId(UserDetailFragment.this.getUser().getId()).createFlagHelper().showFlaggingOptions();
      while (true)
      {
        return;
        if (arrayOfCharSequence[paramInt].equals(UserDetailFragment.this.getResources().getString(2131230960)))
        {
          UserDetailFragment.this.showBlockDialog(2131230960);
          continue;
        }
        if (!arrayOfCharSequence[paramInt].equals(UserDetailFragment.this.getResources().getString(2131230961)))
          continue;
        UserDetailFragment.this.showBlockDialog(2131230961);
      }
    }
  };
  protected UserDetailFeedAdapter mAdapter;
  private boolean mCurrentUser;
  private ApiResponseStatus mLastApiResponseStatus;
  protected User mUser;
  private UserDetailRequest mUserDetailRequest;
  private String mUserId;
  private String mUserName;
  private Receiver receiver = new Receiver(null);

  private String blockOrUnblock()
  {
    if (getUser().isBlocking());
    for (String str = getResources().getString(2131230961); ; str = getResources().getString(2131230960))
      return str;
  }

  private void configureUser()
  {
    Bundle localBundle = getArguments();
    if ((localBundle.containsKey("com.instagram.android.fragment.UserDetailFragment.EXTRA_CURRENT_USER")) && (localBundle.getBoolean("com.instagram.android.fragment.UserDetailFragment.EXTRA_CURRENT_USER")))
      this.mCurrentUser = true;
    while (true)
    {
      return;
      if (localBundle.containsKey("com.instagram.android.fragment.UserDetailFragment.EXTRA_USER_ID"))
      {
        this.mUserId = localBundle.getString("com.instagram.android.fragment.UserDetailFragment.EXTRA_USER_ID");
        this.mUser = ((User)UserStore.getInstance(getActivity()).get(localBundle.getString("com.instagram.android.fragment.UserDetailFragment.EXTRA_USER_ID")));
        continue;
      }
      if (!localBundle.containsKey("com.instagram.android.fragment.UserDetailFragment.EXTRA_USER_NAME"))
        break;
      this.mUserName = localBundle.getString("com.instagram.android.fragment.UserDetailFragment.EXTRA_USER_NAME");
      this.mUser = UserStore.getInstance(getActivity()).findByUserName(localBundle.getString("com.instagram.android.fragment.UserDetailFragment.EXTRA_USER_NAME"));
    }
    throw new RuntimeException("User Detail Fragment started with user id or username");
  }

  private User getUser()
  {
    if (this.mCurrentUser);
    for (User localUser = AuthHelper.getInstance().getCurrentUser(); ; localUser = this.mUser)
      return localUser;
  }

  private String getUserId()
  {
    User localUser = getUser();
    if (localUser != null);
    for (String str = localUser.getId(); ; str = this.mUserId)
      return str;
  }

  private boolean isCurrentUser(User paramUser)
  {
    User localUser = AuthHelper.getInstance().getCurrentUser();
    if ((this.mCurrentUser) || (localUser.equals(paramUser)));
    for (int i = 1; ; i = 0)
      return i;
  }

  private void registerBroadcastReceivers(String paramString)
  {
    if (paramString != null)
    {
      LocalBroadcastManager.getInstance(getActivity()).registerReceiver(this.receiver, new IntentFilter(User.getUserBroadcastId(paramString)));
      LocalBroadcastManager.getInstance(getActivity()).registerReceiver(this.receiver, new IntentFilter(User.getUserFollowUpdateBroadcastId(paramString)));
    }
  }

  public static void setCurrentUserNeedsReload()
  {
    sCurrentUserNeedsReload = true;
  }

  private void showBlockDialog(int paramInt)
  {
    4 local4 = new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        paramDialogInterface.dismiss();
        if (paramInt == -1)
          UserDetailFragment.this.getUser().toggleBlockStatus(UserDetailFragment.this.getActivity(), UserDetailFragment.this.getLoaderManager());
      }
    };
    new IgDialogBuilder(getActivity()).setTitle(paramInt).setPositiveButton(2131230978, local4).setNegativeButton(2131230921, local4).create().show();
  }

  private void showUserOptionsDialog()
  {
    new IgDialogBuilder(getActivity()).setItems(getMenuOptions(), this.itemClickListener).setPositiveButton(2131230921, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        paramDialogInterface.cancel();
      }
    }).create().show();
  }

  private static boolean takeCurrentUserNeedsReload()
  {
    boolean bool = sCurrentUserNeedsReload;
    sCurrentUserNeedsReload = false;
    return bool;
  }

  public void executeRequest()
  {
    this.mUserDetailRequest = new UserDetailRequest(getActivity(), getLoaderManager(), 1, new UserDetailRequestCallbacks(null));
    User localUser = getUser();
    if (localUser != null)
    {
      getAdapter().setHeaderObject(localUser);
      this.mUserDetailRequest.performWithUserId(localUser.getId());
    }
    while (true)
    {
      constructAndPerformFeedRequest(true, new FeedFragment.FeedRequestCallbacks(this));
      return;
      if (getUserId() != null)
      {
        this.mUserDetailRequest.performWithUserId(getArguments().getString("com.instagram.android.fragment.UserDetailFragment.EXTRA_USER_ID"));
        continue;
      }
      if (this.mUserName == null)
        continue;
      this.mUserDetailRequest.performWithUserName(getArguments().getString("com.instagram.android.fragment.UserDetailFragment.EXTRA_USER_NAME"));
    }
  }

  public ActionBarConfigurer getActionBarConfigurer()
  {
    return new FeedFragment.StandardFeedActionBar()
    {
      public View customTitleView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup)
      {
        User localUser = UserDetailFragment.this.getUser();
        View localView;
        TextView localTextView;
        if ((AuthHelper.getInstance().getCurrentUser() != null) && (localUser != null))
        {
          localView = paramLayoutInflater.inflate(2130903049, paramViewGroup, false);
          localView.findViewById(2131492885).setOnClickListener(new UserDetailFragment.1.1(this));
          ((TextView)localView.findViewById(2131492876)).setText(getTitle());
          localView.findViewById(2131492876).setVisibility(0);
          ((TextView)localView.findViewById(2131492876)).setText(getTitle());
          localView.findViewById(2131492876).setVisibility(0);
          localTextView = (TextView)localView.findViewById(2131492883);
          String str = localUser.getFullName();
          if ((str != null) && (!StringUtil.isNullOrEmpty(str)) && (!str.equals(localUser.getUsername())))
          {
            localTextView.setText(str);
            localTextView.setVisibility(0);
          }
        }
        while (true)
        {
          return localView;
          localTextView.setVisibility(8);
          continue;
          localView = super.customTitleView(paramLayoutInflater, paramViewGroup);
        }
      }

      public String getTitle()
      {
        User localUser = UserDetailFragment.this.getUser();
        String str;
        if (localUser != null)
          str = localUser.getUsername();
        while (true)
        {
          return str;
          if (UserDetailFragment.this.mUserDetailRequest.getRequestedUsername() != null)
          {
            str = UserDetailFragment.this.mUserDetailRequest.getRequestedUsername();
            continue;
          }
          str = null;
        }
      }

      public boolean showRefreshButton()
      {
        return false;
      }
    };
  }

  protected UserDetailFeedAdapter getAdapter()
  {
    if (this.mAdapter == null)
      this.mAdapter = new UserDetailFeedAdapter(getActivity(), this, getDefaultFeedViewMode(), getGridViewPadding(), this);
    return this.mAdapter;
  }

  protected FeedAdapter.ViewMode getDefaultFeedViewMode()
  {
    return FeedAdapter.ViewMode.GRID;
  }

  public CharSequence[] getMenuOptions()
  {
    CharSequence[] arrayOfCharSequence = new CharSequence[2];
    arrayOfCharSequence[0] = blockOrUnblock();
    arrayOfCharSequence[1] = getResources().getString(2131230962);
    return arrayOfCharSequence;
  }

  protected MediaFeedRequest makeRequest(AbstractStreamingApiCallbacks<MediaFeedResponse> paramAbstractStreamingApiCallbacks)
  {
    User localUser = getUser();
    if (localUser != null);
    for (UserMediaFeedRequest localUserMediaFeedRequest = new UserMediaFeedRequest(this, localUser.getId().hashCode(), paramAbstractStreamingApiCallbacks, localUser); ; localUserMediaFeedRequest = null)
      return localUserMediaFeedRequest;
  }

  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
  }

  public void onCreate(Bundle paramBundle)
  {
    configureUser();
    registerBroadcastReceivers(getUserId());
    executeRequest();
    super.onCreate(paramBundle);
  }

  public void onDestroy()
  {
    LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(this.receiver);
    super.onDestroy();
  }

  public void onResume()
  {
    super.onResume();
    if (this.mLastApiResponseStatus == ApiResponseStatus.ApiResponseStatusError)
      executeRequest();
    while (true)
    {
      return;
      if ((takeCurrentUserNeedsReload()) && (isCurrentUser(getUser())))
      {
        executeRequest();
        continue;
      }
    }
  }

  private class Receiver extends BroadcastReceiver
  {
    private Receiver()
    {
    }

    public void onReceive(Context paramContext, Intent paramIntent)
    {
      UserDetailFragment.this.getAdapter().notifyDataSetChanged();
      if (UserDetailFragment.this.isResumed())
        ActionBarService.getInstance(paramContext).forceUpdate();
    }
  }

  private class UserDetailRequestCallbacks extends AbstractStreamingApiCallbacks<User>
  {
    private UserDetailRequestCallbacks()
    {
    }

    protected void onRequestFail(ApiResponse<User> paramApiResponse)
    {
      UserDetailFragment.access$202(UserDetailFragment.this, paramApiResponse.getApiResponseStatus());
      User localUser = UserDetailFragment.this.getUser();
      if (localUser != null)
        UserDetailFragment.this.getAdapter().setHeaderObject(localUser);
      while (true)
      {
        return;
        UserDetailFragment.this.getAdapter().setEmptyUser(UserDetailFragment.this.mLastApiResponseStatus);
      }
    }

    protected void onSuccess(User paramUser)
    {
      UserDetailFragment.this.mUser = paramUser;
      UserDetailFragment.access$202(UserDetailFragment.this, ApiResponseStatus.ApiResponseStatusOk);
      User localUser = UserDetailFragment.this.getUser();
      if ((localUser != null) && (UserDetailFragment.this.getAdapter().isEmpty()))
      {
        UserDetailFragment.this.registerBroadcastReceivers(UserDetailFragment.access$400(UserDetailFragment.this));
        UserDetailFragment.this.getAdapter().setHeaderObject(localUser);
        UserDetailFragment.this.constructAndPerformFeedRequest(true, new FeedFragment.FeedRequestCallbacks(UserDetailFragment.this));
      }
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.UserDetailFragment
 * JD-Core Version:    0.6.0
 */
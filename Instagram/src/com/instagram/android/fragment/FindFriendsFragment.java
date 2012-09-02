package com.instagram.android.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.instagram.android.Log;
import com.instagram.android.activity.SignedOutFragmentLayout;
import com.instagram.android.activity.TwitterAuthActivity;
import com.instagram.android.adapter.SimplePreferenceAdapter;
import com.instagram.android.model.GroupingHeader;
import com.instagram.android.model.SimpleMenuItem;
import com.instagram.android.widget.IgDialogBuilder;
import com.instagram.facebook.FacebookAccount;
import com.instagram.facebook.FacebookConstants;
import com.instagram.twitter.TwitterAccount;
import com.instagram.util.FragmentUtil;
import java.util.ArrayList;
import java.util.List;

public class FindFriendsFragment extends ListFragment
  implements ActionBarConfigurer.ActionBarConfigurerFactory
{
  public static final String ARGUMENT_IS_SIGN_UP_FLOW = "com.instagram.android.fragment.FindFriendsFragment.ARGUMENT_IS_SIGN_UP_FLOW";
  private static final String LOG_TAG = "com.instagram.android.fragment.FindFriendsFragment";
  private static final int REQUEST_TWITTER_AUTH = 1;
  private SimplePreferenceAdapter mAdapter;
  private FacebookAccount mFacebookAccount;
  private Handler mHandler = new Handler();

  private void confirmAddressDialog()
  {
    new IgDialogBuilder(getActivity()).setMessage(2131230931).setPositiveButton(2131230932, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        paramDialogInterface.dismiss();
        FindFriendsFragment.this.showContactList();
      }
    }).setNegativeButton(2131230921, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        paramDialogInterface.dismiss();
      }
    }).create().show();
  }

  private void handleResultForTwitterRequest()
  {
    this.mHandler.post(new Runnable()
    {
      public void run()
      {
        FindFriendsFragment.this.showTwitterList();
      }
    });
  }

  private boolean isSignUpFlow()
  {
    if ((getArguments() != null) && (getArguments().getBoolean("com.instagram.android.fragment.FindFriendsFragment.ARGUMENT_IS_SIGN_UP_FLOW")));
    for (int i = 1; ; i = 0)
      return i;
  }

  private void showContactList()
  {
    UserListFragment localUserListFragment = new UserListFragment();
    Bundle localBundle = new Bundle();
    localBundle.putBoolean("com.instagram.android.fragment.UserListFragment.ARGUMENTS_ADDRESSBOOK", true);
    localBundle.putBoolean("com.instagram.android.fragment.UserListFragment.ARGUMENTS_FOLLOW_ALL_BUTTON", true);
    localBundle.putBoolean("com.instagram.android.fragment.UserListFragment.ARGUMENTS_FOLLOW_BUTTONS", true);
    if (isSignUpFlow())
      localBundle.putBoolean("com.instagram.android.fragment.UserListFragment.ARGUMENTS_CLICK_THROUGH", false);
    FragmentUtil.navigateTo(getFragmentManager(), localUserListFragment, localBundle);
  }

  private void showFacebookList(String paramString)
  {
    UserListFragment localUserListFragment = new UserListFragment();
    Bundle localBundle = new Bundle();
    localBundle.putString("com.instagram.android.fragment.UserListFragment.ARGUMENTS_FACEBOOK", paramString);
    localBundle.putBoolean("com.instagram.android.fragment.UserListFragment.ARGUMENTS_FOLLOW_ALL_BUTTON", true);
    localBundle.putBoolean("com.instagram.android.fragment.UserListFragment.ARGUMENTS_FOLLOW_BUTTONS", true);
    if (isSignUpFlow())
      localBundle.putBoolean("com.instagram.android.fragment.UserListFragment.ARGUMENTS_CLICK_THROUGH", false);
    FragmentUtil.navigateTo(getFragmentManager(), localUserListFragment, localBundle);
  }

  private void showTwitterList()
  {
    UserListFragment localUserListFragment = new UserListFragment();
    Bundle localBundle = new Bundle();
    localBundle.putBoolean("com.instagram.android.fragment.UserListFragment.ARGUMENTS_TWITTER", true);
    localBundle.putBoolean("com.instagram.android.fragment.UserListFragment.ARGUMENTS_FOLLOW_ALL_BUTTON", true);
    localBundle.putBoolean("com.instagram.android.fragment.UserListFragment.ARGUMENTS_FOLLOW_BUTTONS", true);
    if (isSignUpFlow())
      localBundle.putBoolean("com.instagram.android.fragment.UserListFragment.ARGUMENTS_CLICK_THROUGH", false);
    FragmentUtil.navigateTo(getFragmentManager(), localUserListFragment, localBundle);
  }

  public ActionBarConfigurer getActionBarConfigurer()
  {
    return new ActionBarConfigurer()
    {
      public View customTitleView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup)
      {
        View localView = null;
        if (FindFriendsFragment.this.isSignUpFlow())
        {
          localView = paramLayoutInflater.inflate(2130903045, paramViewGroup, false);
          localView.findViewById(2131492894).setOnClickListener(new FindFriendsFragment.5.1(this));
          ((TextView)localView.findViewById(2131492876)).setText(getTitle());
        }
        return localView;
      }

      public String getTitle()
      {
        return FindFriendsFragment.this.getString(2131230923);
      }

      public boolean isLoading()
      {
        return false;
      }

      public boolean showBackButton()
      {
        if ((!FindFriendsFragment.this.isSignUpFlow()) && (FindFriendsFragment.this.getFragmentManager().getBackStackEntryCount() > 0));
        for (int i = 1; ; i = 0)
          return i;
      }

      public boolean showRefreshButton()
      {
        return false;
      }
    };
  }

  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    if (isSignUpFlow());
    try
    {
      ((SignedOutFragmentLayout)getActivity()).configureActionBar();
      label22: getListView().setOnItemClickListener(new AdapterView.OnItemClickListener()
      {
        public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
        {
          switch (((SimpleMenuItem)paramAdapterView.getItemAtPosition(paramInt)).getStringResId())
          {
          default:
          case 2131230924:
          case 2131230927:
          case 2131230928:
          case 2131230925:
          case 2131230926:
          }
          while (true)
          {
            return;
            FindFriendsFragment.this.confirmAddressDialog();
            continue;
            FragmentUtil.navigateTo(FindFriendsFragment.this.getFragmentManager(), new CompositeSearchFragment(), null);
            continue;
            FragmentUtil.navigateTo(FindFriendsFragment.this.getFragmentManager(), new SuggestedUserFragment(), null);
            continue;
            Facebook localFacebook = FacebookAccount.getFacebook();
            if (localFacebook.isSessionValid())
            {
              FindFriendsFragment.this.showFacebookList(localFacebook.getAccessToken());
              continue;
            }
            localFacebook.authorize(FindFriendsFragment.this, FacebookConstants.FACEBOOK_PERMISSIONS, new FindFriendsFragment.FacebookAuthListener(FindFriendsFragment.this, localFacebook, null));
            continue;
            if (TwitterAccount.isConfigured(FindFriendsFragment.this.getActivity()))
            {
              FindFriendsFragment.this.showTwitterList();
              continue;
            }
            TwitterAuthActivity.show(FindFriendsFragment.this, 1);
          }
        }
      });
      return;
    }
    catch (ClassCastException localClassCastException)
    {
      break label22;
    }
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (paramInt2 == -1)
      switch (paramInt1)
      {
      default:
        FacebookAccount.getFacebook().authorizeCallback(paramInt1, paramInt2, paramIntent);
      case 1:
      }
    while (true)
    {
      super.onActivityResult(paramInt1, paramInt2, paramIntent);
      return;
      handleResultForTwitterRequest();
    }
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(new SimpleMenuItem(2131230924));
    localArrayList.add(new SimpleMenuItem(2131230925));
    localArrayList.add(new SimpleMenuItem(2131230926));
    if (!isSignUpFlow())
    {
      localArrayList.add(new GroupingHeader(2131230928));
      localArrayList.add(new SimpleMenuItem(2131230928));
      localArrayList.add(new SimpleMenuItem(2131230927));
    }
    this.mAdapter = new SimplePreferenceAdapter(getActivity());
    this.mAdapter.addItems(localArrayList);
    setListAdapter(this.mAdapter);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(2130903087, paramViewGroup, false);
    localView.setBackgroundColor(getActivity().getResources().getColor(2131165210));
    return localView;
  }

  public void onPause()
  {
    if (isSignUpFlow())
      SignedOutFragmentLayout.overrideBack = false;
    super.onPause();
  }

  public void onResume()
  {
    if (isSignUpFlow())
      SignedOutFragmentLayout.overrideBack = true;
    super.onResume();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    getListView().setBackgroundColor(getActivity().getResources().getColor(2131165210));
  }

  private final class FacebookAuthListener
    implements Facebook.DialogListener
  {
    private final Facebook mFacebook;

    private FacebookAuthListener(Facebook arg2)
    {
      Object localObject;
      this.mFacebook = localObject;
    }

    public void onCancel()
    {
      Log.d("com.instagram.android.fragment.FindFriendsFragment", "Facebook onCancel");
    }

    public void onComplete(Bundle paramBundle)
    {
      FacebookAccount.saveCredentials();
      FindFriendsFragment.this.mHandler.post(new FindFriendsFragment.FacebookAuthListener.1(this));
    }

    public void onError(DialogError paramDialogError)
    {
      Log.d("com.instagram.android.fragment.FindFriendsFragment", "Facebook onError");
    }

    public void onFacebookError(FacebookError paramFacebookError)
    {
      Log.d("com.instagram.android.fragment.FindFriendsFragment", "Facebook onFacebookError");
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.FindFriendsFragment
 * JD-Core Version:    0.6.0
 */
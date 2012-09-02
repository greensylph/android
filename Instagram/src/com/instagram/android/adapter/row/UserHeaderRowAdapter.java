package com.instagram.android.adapter.row;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.instagram.android.Preferences;
import com.instagram.android.adapter.UserDetailFeedAdapter;
import com.instagram.android.fragment.UserListFragment;
import com.instagram.android.imagecache.IgImageView;
import com.instagram.android.model.User;
import com.instagram.android.model.User.FollowStatus;
import com.instagram.android.model.User.PrivacyStatus;
import com.instagram.android.service.AppContext;
import com.instagram.android.service.ClickManager;
import com.instagram.android.widget.FollowButton;
import com.instagram.android.widget.ViewSwitchWidgetHelper;
import com.instagram.api.request.UpdateAvatarHelper;
import com.instagram.util.FragmentUtil;
import com.instagram.util.StringUtil;
import com.instagram.util.ViewUtil;

public class UserHeaderRowAdapter
{
  public static void bindView(Holder paramHolder, User paramUser, Context paramContext, LoaderManager paramLoaderManager, FragmentManager paramFragmentManager, UserDetailFeedAdapter paramUserDetailFeedAdapter)
  {
    if (paramUser != null)
    {
      ViewSwitchWidgetHelper.bindViews(paramHolder.gridSwitchButton, paramHolder.listSwitchButton, paramUserDetailFeedAdapter);
      paramHolder.gridSwitchButtonGroup.setVisibility(0);
      FriendRequestHeaderRowAdapter.bindView((FriendRequestHeaderRowAdapter.Holder)paramHolder.friendRequestHeader.getTag(), paramUser, paramContext, paramLoaderManager);
      if (paramUser.getProfilePicUrl() != null)
      {
        paramHolder.imageView.setUrl(paramUser.getProfilePicUrl());
        if ((paramUser.isSelf(AppContext.getContext())) && (ClickManager.getInstance().getUpdateAvatarHelper() != null))
          paramHolder.imageView.setOnClickListener(new View.OnClickListener()
          {
            public void onClick(View paramView)
            {
              ClickManager.getInstance().getUpdateAvatarHelper().showDialog();
            }
          });
        paramHolder.photosCount.setText(ViewUtil.formatLargeNumber(paramUser.getMediaCount()));
        paramHolder.followersCount.setText(ViewUtil.formatLargeNumber(paramUser.getFollowerCount()));
        paramHolder.followersContainer.setOnClickListener(new View.OnClickListener(paramUser, paramContext, paramFragmentManager)
        {
          public void onClick(View paramView)
          {
            UserListFragment localUserListFragment = new UserListFragment();
            Bundle localBundle = new Bundle();
            Object[] arrayOfObject = new Object[1];
            arrayOfObject[0] = this.val$user.getId();
            localBundle.putString("com.instagram.android.fragment.UserListFragment.ARGUMENTS_FETCH_URL", String.format("friendships/%s/followers/", arrayOfObject));
            localBundle.putString("com.instagram.android.fragment.UserListFragment.ARGUMENTS_TITLE", this.val$context.getString(2131230918));
            localBundle.putBoolean("com.instagram.android.fragment.UserListFragment.ARGUMENTS_FOLLOW_BUTTONS", true);
            FragmentUtil.navigateTo(this.val$fragmentManager, localUserListFragment, localBundle);
          }
        });
        paramHolder.followingCount.setText(ViewUtil.formatLargeNumber(paramUser.getFollowingCount()));
        paramHolder.followingContainer.setOnClickListener(new View.OnClickListener(paramUser, paramContext, paramFragmentManager)
        {
          public void onClick(View paramView)
          {
            UserListFragment localUserListFragment = new UserListFragment();
            Bundle localBundle = new Bundle();
            Object[] arrayOfObject = new Object[1];
            arrayOfObject[0] = this.val$user.getId();
            localBundle.putString("com.instagram.android.fragment.UserListFragment.ARGUMENTS_FETCH_URL", String.format("friendships/%s/following/", arrayOfObject));
            localBundle.putString("com.instagram.android.fragment.UserListFragment.ARGUMENTS_TITLE", this.val$context.getString(2131230919));
            localBundle.putBoolean("com.instagram.android.fragment.UserListFragment.ARGUMENTS_FOLLOW_BUTTONS", true);
            FragmentUtil.navigateTo(this.val$fragmentManager, localUserListFragment, localBundle);
          }
        });
        if (!shouldShowFollowRow(paramUser, paramContext))
          break label344;
        paramHolder.followingViewGroup.setVisibility(0);
        paramHolder.followButton.update(paramUser, paramLoaderManager, true);
        paramHolder.followingText.setText(getFollowingText(paramContext, paramUser.getFollowStatus(), paramUser.getPrivacyStatus()));
        label215: if (StringUtil.isNullOrEmpty(paramUser.getBiography()))
          break label356;
        paramHolder.biography.setText(paramUser.getBiography());
        paramHolder.biography.setVisibility(0);
        label244: if (StringUtil.isNullOrEmpty(paramUser.getExternalUrl()))
          break label368;
        paramHolder.website.setText(paramUser.getExternalUrl());
        paramHolder.website.setVisibility(0);
        paramHolder.website.setOnClickListener(new View.OnClickListener(paramUser, paramContext)
        {
          public void onClick(View paramView)
          {
            Intent localIntent = new Intent("android.intent.action.VIEW");
            localIntent.setData(Uri.parse(this.val$user.getExternalUrl()));
            this.val$context.startActivity(localIntent);
          }
        });
        label289: if ((paramHolder.website.getVisibility() != 8) || (paramHolder.biography.getVisibility() != 8))
          break label380;
        paramHolder.userInfoViewGroup.setVisibility(8);
      }
      while (true)
      {
        return;
        paramHolder.imageView.setImageDrawable(AppContext.getContext().getResources().getDrawable(2130837731));
        break;
        label344: paramHolder.followingViewGroup.setVisibility(8);
        break label215;
        label356: paramHolder.biography.setVisibility(8);
        break label244;
        label368: paramHolder.website.setVisibility(8);
        break label289;
        label380: paramHolder.userInfoViewGroup.setVisibility(0);
      }
    }
    paramHolder.imageView.setImageDrawable(AppContext.getContext().getResources().getDrawable(2130837731));
    paramHolder.photosCount.setText("-");
    paramHolder.followersCount.setText("-");
    paramHolder.followingCount.setText("-");
    paramHolder.friendRequestHeader.setVisibility(8);
    paramHolder.followingViewGroup.setVisibility(8);
    paramHolder.website.setVisibility(8);
    if (paramUserDetailFeedAdapter.failedToFindUser())
      paramHolder.biography.setText(2131230893);
    while (true)
    {
      paramHolder.gridSwitchButtonGroup.setVisibility(8);
      break;
      if (paramUserDetailFeedAdapter.failedToFetchUser())
      {
        paramHolder.biography.setText(2131231022);
        continue;
      }
      paramHolder.biography.setText(2131230889);
    }
  }

  private static String getFollowingText(Context paramContext, User.FollowStatus paramFollowStatus, User.PrivacyStatus paramPrivacyStatus)
  {
    String str;
    switch (5.$SwitchMap$com$instagram$android$model$User$FollowStatus[paramFollowStatus.ordinal()])
    {
    default:
      str = "";
    case 1:
    case 2:
    case 3:
    case 4:
    case 5:
    case 6:
    case 7:
    }
    while (true)
    {
      return str;
      str = paramContext.getString(2131230889);
      continue;
      str = paramContext.getString(2131230890);
      continue;
      if (paramPrivacyStatus == User.PrivacyStatus.PrivacyStatusPrivate)
      {
        str = paramContext.getString(2131230891);
        continue;
      }
      str = paramContext.getString(2131230892);
      continue;
      str = paramContext.getString(2131230893);
    }
  }

  public static View newView(Context paramContext)
  {
    View localView = LayoutInflater.from(paramContext).inflate(2130903115, null);
    Holder localHolder = new Holder();
    localHolder.friendRequestHeader = localView.findViewById(2131493062);
    FriendRequestHeaderRowAdapter.createHolderForView(localHolder.friendRequestHeader);
    localHolder.imageView = ((IgImageView)localView.findViewById(2131493063));
    localHolder.photosCount = ((TextView)localView.findViewById(2131493065));
    localHolder.followersContainer = localView.findViewById(2131493066);
    localHolder.followersCount = ((TextView)localView.findViewById(2131493067));
    localHolder.followingContainer = localView.findViewById(2131493068);
    localHolder.followingCount = ((TextView)localView.findViewById(2131493069));
    localHolder.followingViewGroup = localView.findViewById(2131493070);
    localHolder.followingText = ((TextView)localView.findViewById(2131493071));
    localHolder.followButton = ((FollowButton)localView.findViewById(2131493072));
    localHolder.biography = ((TextView)localView.findViewById(2131493074));
    localHolder.userInfoViewGroup = ((ViewGroup)localView.findViewById(2131493073));
    localHolder.website = ((TextView)localView.findViewById(2131493075));
    localHolder.gridSwitchButtonGroup = ((ViewGroup)localView.findViewById(2131492972));
    localHolder.gridSwitchButton = ((Button)localView.findViewById(2131492973));
    localHolder.listSwitchButton = ((Button)localView.findViewById(2131492974));
    localView.setTag(localHolder);
    return localView;
  }

  private static boolean shouldShowFollowRow(User paramUser, Context paramContext)
  {
    if (((Preferences.getInstance(paramContext).isLoggedIn()) && (!paramUser.isSelf(paramContext))) || (paramUser.getFollowStatus() == User.FollowStatus.FollowStatusDoesNotExist));
    for (int i = 1; ; i = 0)
      return i;
  }

  public static class Holder
  {
    TextView biography;
    FollowButton followButton;
    View followersContainer;
    TextView followersCount;
    View followingContainer;
    TextView followingCount;
    TextView followingText;
    View followingViewGroup;
    View friendRequestHeader;
    Button gridSwitchButton;
    ViewGroup gridSwitchButtonGroup;
    IgImageView imageView;
    Button listSwitchButton;
    TextView photosCount;
    ViewGroup userInfoViewGroup;
    TextView website;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.adapter.row.UserHeaderRowAdapter
 * JD-Core Version:    0.6.0
 */
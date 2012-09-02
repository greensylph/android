package com.instagram.android.adapter.row;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.instagram.android.fragment.UserDetailFragment;
import com.instagram.android.imagecache.IgImageView;
import com.instagram.android.model.SuggestedUser;
import com.instagram.android.model.User;
import com.instagram.android.widget.FollowButton;
import com.instagram.util.FragmentUtil;
import com.instagram.util.StringUtil;
import java.util.List;

public class SuggestedUserRowAdapter
{
  public static void bindView(Holder paramHolder, SuggestedUser paramSuggestedUser, Context paramContext, BaseAdapter paramBaseAdapter, FragmentManager paramFragmentManager, boolean paramBoolean1, boolean paramBoolean2, LoaderManager paramLoaderManager)
  {
    paramHolder.userImageview.setUrl(paramSuggestedUser.getUser().getProfilePicUrl());
    String str = StringUtil.stripNewLines(paramSuggestedUser.getCaption());
    if (!StringUtil.isNullOrEmpty(str))
    {
      paramHolder.description.setText(str);
      paramHolder.description.setVisibility(0);
      paramHolder.username.setText(paramSuggestedUser.getUser().getUsername());
      if (paramSuggestedUser.getUser().getFullNameOrUsername().equals(paramSuggestedUser.getUser().getUsername()))
        break label173;
      paramHolder.fullname.setText(paramSuggestedUser.getUser().getFullNameOrUsername());
      paramHolder.fullname.setVisibility(0);
    }
    while (true)
    {
      int i = paramSuggestedUser.getThumbnails().size();
      for (int j = 0; (j < 4) && (j < i); j++)
        paramHolder.imageViews[j].setUrl((String)paramSuggestedUser.getThumbnails().get(j));
      paramHolder.description.setVisibility(8);
      break;
      label173: paramHolder.fullname.setVisibility(8);
    }
    if (paramBoolean1)
    {
      paramHolder.followButton.setVisibility(0);
      paramHolder.followButton.update(paramSuggestedUser.getUser(), paramLoaderManager, false);
    }
    while (paramBoolean2)
    {
      1 local1 = new View.OnClickListener(paramSuggestedUser, paramFragmentManager)
      {
        public void onClick(View paramView)
        {
          Bundle localBundle = new Bundle();
          localBundle.putString("com.instagram.android.fragment.UserDetailFragment.EXTRA_USER_ID", this.val$user.getUser().getId());
          FragmentUtil.navigateTo(this.val$fragmentManager, new UserDetailFragment(), localBundle);
        }
      };
      int k = paramSuggestedUser.getThumbnails().size();
      int m = 0;
      while (true)
        if ((m < 4) && (m < k))
        {
          paramHolder.imageViews[m].setOnClickListener(local1);
          m++;
          continue;
          paramHolder.followButton.setVisibility(8);
          break;
        }
      paramHolder.userImageview.setOnClickListener(local1);
      paramHolder.username.setOnClickListener(local1);
      paramHolder.fullname.setOnClickListener(local1);
    }
  }

  public static View newView(Context paramContext)
  {
    View localView = LayoutInflater.from(paramContext).inflate(2130903120, null);
    Holder localHolder = new Holder();
    localHolder.userImageview = ((IgImageView)localView.findViewById(2131493083));
    localHolder.description = ((TextView)localView.findViewById(2131493091));
    localHolder.username = ((TextView)localView.findViewById(2131493084));
    localHolder.fullname = ((TextView)localView.findViewById(2131493085));
    localHolder.followButton = ((FollowButton)localView.findViewById(2131493086));
    localHolder.imageViews[0] = ((IgImageView)localView.findViewById(2131493087));
    localHolder.imageViews[1] = ((IgImageView)localView.findViewById(2131493088));
    localHolder.imageViews[2] = ((IgImageView)localView.findViewById(2131493089));
    localHolder.imageViews[3] = ((IgImageView)localView.findViewById(2131493090));
    localHolder.userRow = ((ViewGroup)localView.findViewById(2131493082));
    localView.setTag(localHolder);
    return localView;
  }

  public static class Holder
  {
    TextView description;
    FollowButton followButton;
    TextView fullname;
    IgImageView[] imageViews = new IgImageView[4];
    IgImageView userImageview;
    ViewGroup userRow;
    TextView username;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.adapter.row.SuggestedUserRowAdapter
 * JD-Core Version:    0.6.0
 */
package com.instagram.android.adapter.row;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.instagram.android.imagecache.IgImageView;
import com.instagram.android.model.AutoCompleteUser;
import com.instagram.android.widget.FollowButton;

public class AutoCompleteUserRowAdapter
{
  public static void bindView(Holder paramHolder, AutoCompleteUser paramAutoCompleteUser)
  {
    paramHolder.userImageview.setVisibility(8);
    paramHolder.fullname.setText(paramAutoCompleteUser.getUsername());
    if (!TextUtils.isEmpty(paramAutoCompleteUser.getFullname()))
    {
      paramHolder.username.setVisibility(0);
      paramHolder.username.setText(paramAutoCompleteUser.getFullname());
    }
    while (true)
    {
      paramHolder.followButton.setVisibility(8);
      return;
      paramHolder.username.setVisibility(8);
    }
  }

  public static View newView(Context paramContext)
  {
    View localView = LayoutInflater.from(paramContext).inflate(2130903121, null);
    Holder localHolder = new Holder();
    localHolder.userImageview = ((IgImageView)localView.findViewById(2131493092));
    localHolder.fullname = ((TextView)localView.findViewById(2131493093));
    localHolder.username = ((TextView)localView.findViewById(2131493094));
    localHolder.followButton = ((FollowButton)localView.findViewById(2131493095));
    localView.setTag(localHolder);
    return localView;
  }

  public static class Holder
  {
    FollowButton followButton;
    TextView fullname;
    IgImageView userImageview;
    TextView username;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.adapter.row.AutoCompleteUserRowAdapter
 * JD-Core Version:    0.6.0
 */
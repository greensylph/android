package com.instagram.android.widget;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.LoaderManager;
import android.util.AttributeSet;
import android.widget.Button;
import com.instagram.android.model.User;
import com.instagram.android.model.User.FollowStatus;
import com.instagram.api.request.FetchFollowingStatus;

public class FollowButton extends Button
{
  private boolean mAlreadyFetched;
  private String mUserId;

  public FollowButton(Context paramContext)
  {
    this(paramContext, null, 2131623947);
  }

  public FollowButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 2131623947);
  }

  public FollowButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init();
  }

  private void updateShadowColor()
  {
    if (!isEnabled())
    {
      setShadowLayer(0.0F, 0.0F, 0.0F, getContext().getResources().getColor(2131165216));
      return;
    }
    Resources localResources;
    if (isSelected())
    {
      localResources = getContext().getResources();
      if (!isSelected())
        break label67;
    }
    label67: for (int i = 2131165222; ; i = 2131165223)
    {
      setShadowLayer(1.0F, 0.0F, 1.0F, localResources.getColor(i));
      break;
      break;
    }
  }

  public void init()
  {
    updateShadowColor();
  }

  public void update(User paramUser, LoaderManager paramLoaderManager, boolean paramBoolean)
  {
    if (paramUser == null);
    while (true)
    {
      return;
      if (paramUser.isSelf(getContext()))
      {
        setVisibility(8);
        continue;
      }
      if ((this.mUserId != null) && (paramUser != null) && (!paramUser.getId().equals(this.mUserId)))
        this.mAlreadyFetched = false;
      this.mUserId = paramUser.getId();
      if (this.mAlreadyFetched)
        break;
      this.mAlreadyFetched = true;
      setText("...");
      setEnabled(false);
      if (!paramBoolean)
        break;
      new FetchFollowingStatus(getContext(), paramLoaderManager, paramUser).perform();
    }
    setSelected(false);
    setEnabled(true);
    User.FollowStatus localFollowStatus = paramUser.getFollowStatus();
    setBackgroundResource(2130837732);
    if (localFollowStatus == User.FollowStatus.FollowStatusFollowing)
    {
      setText(2131230886);
      setSelected(true);
    }
    while (true)
    {
      setOnClickListener(new FollowButton.1(this, paramUser, paramLoaderManager));
      updateShadowColor();
      break;
      if (localFollowStatus == User.FollowStatus.FollowStatusRequested)
      {
        setText(2131230887);
        setBackgroundResource(2130837582);
        continue;
      }
      if (localFollowStatus != User.FollowStatus.FollowStatusNotFollowing)
        continue;
      setText(2131230888);
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.widget.FollowButton
 * JD-Core Version:    0.6.0
 */
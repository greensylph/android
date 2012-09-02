package com.instagram.android.adapter;

import android.content.Context;
import android.view.View;
import com.instagram.android.adapter.row.UserHeaderRowAdapter;
import com.instagram.android.adapter.row.UserHeaderRowAdapter.Holder;
import com.instagram.android.fragment.FeedFragment;
import com.instagram.android.model.User;
import com.instagram.api.ApiResponseStatus;

public class UserDetailFeedAdapter extends SimpleHeaderFeedAdapter<User>
{
  private ApiResponseStatus mResponseStatus;

  public UserDetailFeedAdapter(Context paramContext, FeedAdapter.ListenerDelegate paramListenerDelegate, FeedAdapter.ViewMode paramViewMode, FeedAdapter.GridViewPadding paramGridViewPadding, FeedFragment paramFeedFragment)
  {
    super(paramContext, paramListenerDelegate, paramViewMode, paramGridViewPadding, paramFeedFragment);
  }

  protected void bindHeaderView(Context paramContext, View paramView, int paramInt)
  {
    UserHeaderRowAdapter.bindView((UserHeaderRowAdapter.Holder)paramView.getTag(), (User)getItem(paramInt), paramContext, this.mLoadManager, this.mFragmentManager, this);
  }

  protected View createHeaderView(Context paramContext)
  {
    return UserHeaderRowAdapter.newView(paramContext);
  }

  public boolean failedToFetchUser()
  {
    if (this.mResponseStatus == ApiResponseStatus.ApiResponseStatusError);
    for (int i = 1; ; i = 0)
      return i;
  }

  public boolean failedToFindUser()
  {
    if (this.mResponseStatus == ApiResponseStatus.ApiResponseStatusObjectNotFound);
    for (int i = 1; ; i = 0)
      return i;
  }

  public int getChildCount()
  {
    return 1;
  }

  public void setEmptyUser(ApiResponseStatus paramApiResponseStatus)
  {
    this.mResponseStatus = paramApiResponseStatus;
    setHeaderObject(null);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.adapter.UserDetailFeedAdapter
 * JD-Core Version:    0.6.0
 */
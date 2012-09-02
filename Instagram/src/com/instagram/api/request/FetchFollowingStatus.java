package com.instagram.api.request;

import android.content.Context;
import android.support.v4.app.LoaderManager;
import ch.boye.httpclientandroidlib.client.methods.HttpUriRequest;
import com.instagram.android.model.User;
import com.instagram.android.model.User.FollowStatus;
import com.instagram.api.ApiHttpClient;
import com.instagram.api.ApiResponse;
import com.instagram.api.RequestParams;
import com.instagram.util.LoaderUtil;

public class FetchFollowingStatus extends AbstractRequest<Object>
{
  private User mUser;

  public FetchFollowingStatus(Context paramContext, LoaderManager paramLoaderManager, User paramUser)
  {
    super(paramContext, paramLoaderManager, LoaderUtil.getUniqueId(), null);
    this.mUser = paramUser;
    this.mUser.updateFollowStatus(User.FollowStatus.FollowStatusFetching, paramContext);
  }

  protected HttpUriRequest buildRequest(ApiHttpClient paramApiHttpClient, String paramString, RequestParams paramRequestParams)
  {
    return paramApiHttpClient.getRequest(paramString);
  }

  protected String getPath()
  {
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = this.mUser.getId();
    return String.format("friendships/show/%s/", arrayOfObject);
  }

  public void handleErrorInBackground(ApiResponse<Object> paramApiResponse)
  {
    this.mUser.revertFollowStatus(getContext());
  }

  public Object processInBackground(ApiResponse<Object> paramApiResponse)
  {
    this.mUser.updateFollowStatus(paramApiResponse.getRootNode(), getContext());
    return paramApiResponse;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.request.FetchFollowingStatus
 * JD-Core Version:    0.6.0
 */
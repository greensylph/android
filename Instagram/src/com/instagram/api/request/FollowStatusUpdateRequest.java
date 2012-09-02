package com.instagram.api.request;

import android.content.Context;
import android.support.v4.app.LoaderManager;
import ch.boye.httpclientandroidlib.client.methods.HttpUriRequest;
import com.instagram.android.model.User;
import com.instagram.android.model.User.UserAction;
import com.instagram.api.ApiHttpClient;
import com.instagram.api.ApiResponse;
import com.instagram.api.RequestParams;
import com.instagram.util.LoaderUtil;
import org.codehaus.jackson.JsonNode;

public class FollowStatusUpdateRequest extends AbstractRequest<Object>
{
  private User.UserAction mAction;
  private User mUser;

  public FollowStatusUpdateRequest(Context paramContext, LoaderManager paramLoaderManager, User.UserAction paramUserAction, User paramUser)
  {
    super(paramContext, paramLoaderManager, LoaderUtil.getUniqueId(), null);
    this.mAction = paramUserAction;
    this.mUser = paramUser;
  }

  private String getVerb(User.UserAction paramUserAction)
  {
    String str;
    switch (1.$SwitchMap$com$instagram$android$model$User$UserAction[paramUserAction.ordinal()])
    {
    default:
      str = null;
    case 1:
    case 2:
    case 3:
    case 4:
    case 5:
    }
    while (true)
    {
      return str;
      str = "create";
      continue;
      str = "destroy";
      continue;
      str = "ignore";
      continue;
      str = "approve";
    }
  }

  protected HttpUriRequest buildRequest(ApiHttpClient paramApiHttpClient, String paramString, RequestParams paramRequestParams)
  {
    return paramApiHttpClient.postRequest(paramString, paramRequestParams);
  }

  protected String getPath()
  {
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = getVerb(this.mAction);
    arrayOfObject[1] = this.mUser.getId();
    return String.format("friendships/%s/%s/", arrayOfObject);
  }

  public void handleErrorInBackground(ApiResponse<Object> paramApiResponse)
  {
    this.mUser.revertFollowStatus(getContext());
  }

  public Object processInBackground(ApiResponse<Object> paramApiResponse)
  {
    this.mUser.updateFollowStatus(paramApiResponse.getRootNode().get("friendship_status"), getContext());
    return null;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.request.FollowStatusUpdateRequest
 * JD-Core Version:    0.6.0
 */
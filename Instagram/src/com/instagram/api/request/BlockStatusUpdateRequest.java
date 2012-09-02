package com.instagram.api.request;

import android.content.Context;
import android.support.v4.app.LoaderManager;
import ch.boye.httpclientandroidlib.client.methods.HttpUriRequest;
import com.instagram.android.model.User;
import com.instagram.api.AbstractApiCallbacks;
import com.instagram.api.ApiHttpClient;
import com.instagram.api.ApiResponse;
import com.instagram.api.RequestParams;
import com.instagram.util.LoaderUtil;
import org.codehaus.jackson.JsonNode;

public class BlockStatusUpdateRequest extends AbstractRequest<Void>
{
  private User mUser;
  private String mVerb;

  public BlockStatusUpdateRequest(Context paramContext, LoaderManager paramLoaderManager, AbstractApiCallbacks<Void> paramAbstractApiCallbacks)
  {
    super(paramContext, paramLoaderManager, LoaderUtil.getUniqueId(), paramAbstractApiCallbacks);
  }

  protected HttpUriRequest buildRequest(ApiHttpClient paramApiHttpClient, String paramString, RequestParams paramRequestParams)
  {
    return paramApiHttpClient.postRequest(paramString, paramRequestParams);
  }

  protected String getPath()
  {
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = this.mVerb;
    arrayOfObject[1] = this.mUser.getId();
    return String.format("friendships/%s/%s/", arrayOfObject);
  }

  public void handleErrorInBackground(ApiResponse<Void> paramApiResponse)
  {
    this.mUser.revertBlockStatus(getContext());
    super.handleErrorInBackground(paramApiResponse);
  }

  public void perform(User paramUser, String paramString)
  {
    this.mUser = paramUser;
    this.mVerb = paramString;
    perform();
  }

  public Void processInBackground(ApiResponse<Void> paramApiResponse)
  {
    this.mUser.updateFollowStatus(paramApiResponse.getRootNode().get("friendship_status"), getContext());
    return null;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.request.BlockStatusUpdateRequest
 * JD-Core Version:    0.6.0
 */
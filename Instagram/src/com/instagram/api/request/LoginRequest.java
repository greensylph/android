package com.instagram.api.request;

import android.content.Context;
import android.support.v4.app.LoaderManager;
import ch.boye.httpclientandroidlib.client.methods.HttpUriRequest;
import com.instagram.android.model.User;
import com.instagram.api.AbstractApiCallbacks;
import com.instagram.api.ApiHttpClient;
import com.instagram.api.ApiResponse;
import com.instagram.api.RequestParams;
import com.instagram.util.JsonBuilder;
import com.instagram.util.RequestUtil;

public class LoginRequest extends AbstractRequest<User>
{
  public LoginRequest(Context paramContext, LoaderManager paramLoaderManager, AbstractApiCallbacks<User> paramAbstractApiCallbacks)
  {
    super(paramContext, paramLoaderManager, 2131492870, paramAbstractApiCallbacks);
  }

  protected HttpUriRequest buildRequest(ApiHttpClient paramApiHttpClient, String paramString, RequestParams paramRequestParams)
  {
    return paramApiHttpClient.postRequest(paramString, paramRequestParams);
  }

  protected String getPath()
  {
    return "accounts/login/";
  }

  protected boolean isSecure()
  {
    return true;
  }

  public void perform(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    String str = new JsonBuilder().put("username", paramString1).put("password", paramString2).put("device_id", paramString3).put("guid", paramString4).toString();
    RequestUtil.setSignedBody(getParams(), str);
    super.perform();
  }

  public User processInBackground(ApiResponse<User> paramApiResponse)
  {
    return (User)paramApiResponse.readRootValue("logged_in_user", User.class);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.request.LoginRequest
 * JD-Core Version:    0.6.0
 */
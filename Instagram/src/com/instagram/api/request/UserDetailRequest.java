package com.instagram.api.request;

import android.content.Context;
import android.support.v4.app.LoaderManager;
import ch.boye.httpclientandroidlib.client.methods.HttpUriRequest;
import com.instagram.android.Log;
import com.instagram.android.model.User;
import com.instagram.android.service.AuthHelper;
import com.instagram.android.service.UserStore;
import com.instagram.api.AbstractStreamingApiCallbacks;
import com.instagram.api.ApiHttpClient;
import com.instagram.api.RequestParams;
import com.instagram.api.StreamingApiResponse;
import java.io.IOException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;

public class UserDetailRequest extends AbstractStreamingRequest<User>
{
  public static final String LOG_TAG = "UserDetailRequest";
  private String mUserId;
  private String mUserName;

  public UserDetailRequest(Context paramContext, LoaderManager paramLoaderManager, int paramInt, AbstractStreamingApiCallbacks<User> paramAbstractStreamingApiCallbacks)
  {
    super(paramContext, paramLoaderManager, paramInt, paramAbstractStreamingApiCallbacks);
  }

  private boolean isCurrentUser(User paramUser)
  {
    if (this.mUserId != null);
    for (boolean bool = paramUser.getId().equals(this.mUserId); ; bool = paramUser.getUsername().equalsIgnoreCase(this.mUserName))
      return bool;
  }

  protected HttpUriRequest buildRequest(ApiHttpClient paramApiHttpClient, String paramString, RequestParams paramRequestParams)
  {
    return paramApiHttpClient.getRequest(paramString);
  }

  protected String getPath()
  {
    String str;
    if (this.mUserId != null)
    {
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = this.mUserId;
      str = String.format("users/%s/info/", arrayOfObject2);
    }
    while (true)
    {
      return str;
      if (this.mUserName != null)
      {
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = this.mUserName;
        str = String.format("users/%s/usernameinfo/", arrayOfObject1);
        continue;
      }
      Log.w("UserDetailRequest", "No path found");
      str = null;
    }
  }

  public String getRequestedUsername()
  {
    return this.mUserName;
  }

  public void performWithUserId(String paramString)
  {
    this.mUserId = paramString;
    this.mUserName = null;
    perform();
  }

  public void performWithUserName(String paramString)
  {
    this.mUserId = null;
    this.mUserName = paramString;
    perform();
  }

  public void processResponseField(String paramString, JsonParser paramJsonParser, StreamingApiResponse<User> paramStreamingApiResponse)
    throws JsonParseException, IOException
  {
    if ("user".equals(paramString))
    {
      paramJsonParser.nextToken();
      if (isCurrentUser(AuthHelper.getInstance().getCurrentUser()))
        UserStore.allowCurrentUserUpdate();
      paramStreamingApiResponse.setSuccessObject(User.fromJsonParser(paramStreamingApiResponse.getContext(), paramJsonParser));
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.request.UserDetailRequest
 * JD-Core Version:    0.6.0
 */
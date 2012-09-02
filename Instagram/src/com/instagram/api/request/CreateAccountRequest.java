package com.instagram.api.request;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.LoaderManager;
import ch.boye.httpclientandroidlib.client.methods.HttpUriRequest;
import com.instagram.android.Log;
import com.instagram.android.model.User;
import com.instagram.android.service.AppContext;
import com.instagram.api.AbstractApiCallbacks;
import com.instagram.api.ApiHttpClient;
import com.instagram.api.ApiResponse;
import com.instagram.api.RequestParams;
import com.instagram.util.RequestUtil;
import com.instagram.util.StringUtil;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;

public class CreateAccountRequest extends AbstractRequest<User>
{
  public static final String TAG = "CreateAccountRequest";

  public CreateAccountRequest(Context paramContext, LoaderManager paramLoaderManager, AbstractApiCallbacks<User> paramAbstractApiCallbacks)
  {
    super(paramContext, paramLoaderManager, 2131492869, paramAbstractApiCallbacks);
  }

  private String constructErrorMessage(ApiResponse<User> paramApiResponse)
  {
    JsonNode localJsonNode = paramApiResponse.getRootNode().get("errors");
    if (localJsonNode == null);
    StringBuilder localStringBuilder;
    for (String str = AppContext.getContext().getString(2131231009); ; str = localStringBuilder.toString())
    {
      return str;
      localStringBuilder = new StringBuilder();
      Iterator localIterator1 = localJsonNode.iterator();
      while (localIterator1.hasNext())
      {
        Iterator localIterator2 = ((JsonNode)localIterator1.next()).iterator();
        while (localIterator2.hasNext())
          localStringBuilder.append(((JsonNode)localIterator2.next()).asText()).append("\n");
      }
    }
  }

  protected HttpUriRequest buildRequest(ApiHttpClient paramApiHttpClient, String paramString, RequestParams paramRequestParams)
  {
    return paramApiHttpClient.postRequest(paramString, paramRequestParams);
  }

  protected String getPath()
  {
    return "accounts/create/";
  }

  protected boolean isSecure()
  {
    return true;
  }

  public void perform(Params paramParams)
  {
    RequestParams localRequestParams = getParams();
    RequestUtil.setSignedBody(localRequestParams, paramParams.toJsonString());
    if (paramParams.profilePic != null)
      localRequestParams.put("profile_pic", UpdateAvatarRequest.compressedInputStream(paramParams.profilePic), "profile_pic");
    super.perform();
  }

  public User processInBackground(ApiResponse<User> paramApiResponse)
  {
    User localUser = null;
    if (paramApiResponse.hasRootValue("created_user"))
      localUser = (User)paramApiResponse.readRootValue("created_user", User.class);
    while (true)
    {
      return localUser;
      paramApiResponse.setErrorMessage(constructErrorMessage(paramApiResponse));
    }
  }

  public static class Params
  {
    public String deviceId;
    public String email;
    public String guid;
    public String password;
    public String phone;
    public Bitmap profilePic;
    public String username;

    private String toJsonString()
    {
      StringWriter localStringWriter = new StringWriter();
      JsonFactory localJsonFactory = new JsonFactory();
      try
      {
        JsonGenerator localJsonGenerator = localJsonFactory.createJsonGenerator(localStringWriter);
        localJsonGenerator.writeStartObject();
        localJsonGenerator.writeStringField("email", StringUtil.get(this.email, ""));
        localJsonGenerator.writeStringField("username", StringUtil.get(this.username, ""));
        localJsonGenerator.writeStringField("password", StringUtil.get(this.password, ""));
        localJsonGenerator.writeStringField("phone", StringUtil.get(this.phone, ""));
        localJsonGenerator.writeStringField("device_id", StringUtil.get(this.deviceId, ""));
        localJsonGenerator.writeStringField("guid", StringUtil.get(this.guid, ""));
        localJsonGenerator.writeEndObject();
        localJsonGenerator.close();
        return localStringWriter.toString();
      }
      catch (IOException localIOException)
      {
        while (true)
          Log.d("CreateAccountRequest", "Failed to create params-json", localIOException);
      }
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.request.CreateAccountRequest
 * JD-Core Version:    0.6.0
 */
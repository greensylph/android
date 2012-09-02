package com.instagram.api.request;

import android.content.Context;
import android.support.v4.app.LoaderManager;
import ch.boye.httpclientandroidlib.client.methods.HttpUriRequest;
import com.instagram.api.AbstractApiCallbacks;
import com.instagram.api.ApiHttpClient;
import com.instagram.api.ApiResponse;
import com.instagram.api.RequestParams;
import com.instagram.util.JsonBuilder;
import com.instagram.util.LoaderUtil;
import com.instagram.util.RequestUtil;

public class FlagRequest extends AbstractRequest<Void>
{
  private String mId;
  private FlagHelper.FlagType mType;

  public FlagRequest(Context paramContext, LoaderManager paramLoaderManager, AbstractApiCallbacks<Void> paramAbstractApiCallbacks)
  {
    super(paramContext, paramLoaderManager, LoaderUtil.getUniqueId(), paramAbstractApiCallbacks);
  }

  protected HttpUriRequest buildRequest(ApiHttpClient paramApiHttpClient, String paramString, RequestParams paramRequestParams)
  {
    return paramApiHttpClient.postRequest(paramString, paramRequestParams);
  }

  protected String getPath()
  {
    String str;
    if (this.mType == FlagHelper.FlagType.Media)
    {
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = this.mId;
      str = String.format("media/%s/flag/", arrayOfObject2);
    }
    while (true)
    {
      return str;
      if (this.mType == FlagHelper.FlagType.User)
      {
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = this.mId;
        str = String.format("users/%s/flag/", arrayOfObject1);
        continue;
      }
      str = null;
    }
  }

  public void perform(FlagHelper.FlagType paramFlagType, String paramString1, FlagHelper.FlagReason paramFlagReason, String paramString2)
  {
    this.mType = paramFlagType;
    this.mId = paramString1;
    JsonBuilder localJsonBuilder = new JsonBuilder();
    localJsonBuilder.put("reason_id", Integer.toString(paramFlagReason.getReasonCode(paramFlagReason)));
    if (paramString2 != null)
      localJsonBuilder.put("details", paramString2);
    RequestUtil.setSignedBody(getParams(), localJsonBuilder.toString());
    perform();
  }

  public Void processInBackground(ApiResponse<Void> paramApiResponse)
  {
    return null;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.request.FlagRequest
 * JD-Core Version:    0.6.0
 */
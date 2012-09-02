package com.instagram.api.request;

import android.content.Context;
import android.support.v4.app.LoaderManager;
import ch.boye.httpclientandroidlib.client.methods.HttpUriRequest;
import com.instagram.api.AbstractApiCallbacks;
import com.instagram.api.ApiHttpClient;
import com.instagram.api.ApiResponse;
import com.instagram.api.RequestParams;
import com.instagram.util.LoaderUtil;

public class ChangePrivacyRequest extends AbstractRequest<Boolean>
{
  private boolean mMakePrivate;

  public ChangePrivacyRequest(Context paramContext, LoaderManager paramLoaderManager, AbstractApiCallbacks<Boolean> paramAbstractApiCallbacks, boolean paramBoolean)
  {
    super(paramContext, paramLoaderManager, LoaderUtil.getUniqueId(), paramAbstractApiCallbacks);
    this.mMakePrivate = paramBoolean;
  }

  protected HttpUriRequest buildRequest(ApiHttpClient paramApiHttpClient, String paramString, RequestParams paramRequestParams)
  {
    return paramApiHttpClient.postRequest(paramString, paramRequestParams);
  }

  protected String getPath()
  {
    if (this.mMakePrivate);
    for (String str = "accounts/set_private/"; ; str = "accounts/set_public/")
      return str;
  }

  public void perform()
  {
    super.perform();
  }

  public Boolean processInBackground(ApiResponse<Boolean> paramApiResponse)
  {
    if (this.mMakePrivate);
    for (Boolean localBoolean = new Boolean(true); ; localBoolean = new Boolean(false))
      return localBoolean;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.request.ChangePrivacyRequest
 * JD-Core Version:    0.6.0
 */
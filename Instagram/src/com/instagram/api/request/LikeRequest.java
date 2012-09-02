package com.instagram.api.request;

import android.content.Context;
import android.support.v4.app.LoaderManager;
import ch.boye.httpclientandroidlib.client.methods.HttpUriRequest;
import com.instagram.android.model.Media;
import com.instagram.api.ApiHttpClient;
import com.instagram.api.ApiResponse;
import com.instagram.api.RequestParams;
import com.instagram.util.JsonBuilder;
import com.instagram.util.LoaderUtil;
import com.instagram.util.RequestUtil;

public class LikeRequest extends AbstractRequest<Object>
{
  private boolean mLikeIntention;
  private Media mMedia;
  private boolean mWasDoubleTap;

  public LikeRequest(Context paramContext, LoaderManager paramLoaderManager, Media paramMedia, boolean paramBoolean1, boolean paramBoolean2)
  {
    super(paramContext, paramLoaderManager, LoaderUtil.getUniqueId(), null);
    this.mMedia = paramMedia;
    this.mLikeIntention = paramBoolean1;
    this.mWasDoubleTap = paramBoolean2;
  }

  protected HttpUriRequest buildRequest(ApiHttpClient paramApiHttpClient, String paramString, RequestParams paramRequestParams)
  {
    RequestUtil.setSignedBody(paramRequestParams, new JsonBuilder().put("media_id", this.mMedia.getId()).toString());
    return paramApiHttpClient.postRequest(paramString, paramRequestParams);
  }

  protected RequestParams getParams()
  {
    RequestParams localRequestParams = super.getParams();
    if (this.mWasDoubleTap)
      localRequestParams.put("source", "dtap");
    return localRequestParams;
  }

  protected String getPath()
  {
    Object[] arrayOfObject2;
    if (this.mLikeIntention)
    {
      arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = this.mMedia.getId();
    }
    Object[] arrayOfObject1;
    for (String str = String.format("media/%s/like/", arrayOfObject2); ; str = String.format("media/%s/unlike/", arrayOfObject1))
    {
      return str;
      arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = this.mMedia.getId();
    }
  }

  public void handleErrorInBackground(ApiResponse<Object> paramApiResponse)
  {
    Media localMedia = this.mMedia;
    if (!this.mMedia.isHasLiked());
    for (boolean bool = true; ; bool = false)
    {
      localMedia.updatedHasLiked(bool);
      return;
    }
  }

  public Object processInBackground(ApiResponse<Object> paramApiResponse)
  {
    return this.mMedia;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.request.LikeRequest
 * JD-Core Version:    0.6.0
 */
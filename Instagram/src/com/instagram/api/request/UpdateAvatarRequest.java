package com.instagram.api.request;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import ch.boye.httpclientandroidlib.HttpEntity;
import ch.boye.httpclientandroidlib.HttpResponse;
import ch.boye.httpclientandroidlib.client.HttpClient;
import ch.boye.httpclientandroidlib.client.methods.HttpGet;
import ch.boye.httpclientandroidlib.client.methods.HttpUriRequest;
import ch.boye.httpclientandroidlib.impl.client.DefaultHttpClient;
import ch.boye.httpclientandroidlib.util.EntityUtils;
import com.facebook.android.Facebook;
import com.instagram.android.model.User;
import com.instagram.android.service.AppContext;
import com.instagram.api.AbstractApiCallbacks;
import com.instagram.api.ApiHttpClient;
import com.instagram.api.ApiResponse;
import com.instagram.api.RequestParams;
import com.instagram.facebook.FacebookAccount;
import com.instagram.twitter.TwitterAccount;
import com.instagram.util.BitmapHelper;
import com.instagram.util.BitmapHelper.ImageTooSmallException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;

public class UpdateAvatarRequest extends AbstractRequest<User>
{
  public static final int TYPE_FACEBOOK = 0;
  public static final int TYPE_TWITTER = 1;
  public static final int TYPE_URI = 2;
  private boolean mRemove = false;
  private int mType;
  private Uri mUri;

  public UpdateAvatarRequest(Context paramContext, LoaderManager paramLoaderManager, AbstractApiCallbacks<User> paramAbstractApiCallbacks)
  {
    super(paramContext, paramLoaderManager, 2131492872, paramAbstractApiCallbacks);
  }

  public static ByteArrayInputStream compressedInputStream(Bitmap paramBitmap)
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    paramBitmap.compress(Bitmap.CompressFormat.JPEG, 90, localByteArrayOutputStream);
    return new ByteArrayInputStream(localByteArrayOutputStream.toByteArray());
  }

  public static Bitmap getBitmapToSend(int paramInt, Uri paramUri)
    throws IOException, BitmapHelper.ImageTooSmallException
  {
    Object localObject1 = null;
    HttpEntity localHttpEntity2;
    if (paramInt == 0)
      localHttpEntity2 = null;
    while (true)
    {
      try
      {
        localHttpEntity2 = new DefaultHttpClient().execute(new HttpGet("https://graph.facebook.com/me/picture?type=large&method=GET&access_token=" + FacebookAccount.getFacebook().getAccessToken())).getEntity();
        Bitmap localBitmap4 = BitmapFactory.decodeStream(localHttpEntity2.getContent());
        localObject1 = localBitmap4;
        if (localHttpEntity2 == null)
          continue;
        EntityUtils.consume(localHttpEntity2);
        if (localObject1 == null)
          continue;
        Bitmap localBitmap1 = Bitmap.createScaledBitmap((Bitmap)localObject1, (int)(150.0F * (((Bitmap)localObject1).getWidth() / ((Bitmap)localObject1).getHeight())), 150, true);
        ((Bitmap)localObject1).recycle();
        localObject1 = localBitmap1;
        Bitmap localBitmap2 = BitmapHelper.squarifyIfNeeded((Bitmap)localObject1);
        if (localObject1 == localBitmap2)
          continue;
        ((Bitmap)localObject1).recycle();
        localObject1 = localBitmap2;
        return localObject1;
      }
      finally
      {
        if (localHttpEntity2 == null)
          continue;
        EntityUtils.consume(localHttpEntity2);
      }
      HttpEntity localHttpEntity1;
      if (paramInt == 1)
        localHttpEntity1 = null;
      try
      {
        DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = URLEncoder.encode(TwitterAccount.get(AppContext.getContext()).getUsername());
        localHttpEntity1 = localDefaultHttpClient.execute(new HttpGet(String.format("https://api.twitter.com/1/users/profile_image?screen_name=%s&size=bigger", arrayOfObject))).getEntity();
        Bitmap localBitmap3 = BitmapFactory.decodeStream(localHttpEntity1.getContent());
        localObject1 = localBitmap3;
        if (localHttpEntity1 == null)
          continue;
        EntityUtils.consume(localHttpEntity1);
      }
      finally
      {
        if (localHttpEntity1 != null)
          EntityUtils.consume(localHttpEntity1);
      }
    }
  }

  protected HttpUriRequest buildRequest(ApiHttpClient paramApiHttpClient, String paramString, RequestParams paramRequestParams)
  {
    return paramApiHttpClient.postRequest(paramString, paramRequestParams);
  }

  protected String getPath()
  {
    if (this.mRemove);
    for (String str = "accounts/remove_profile_picture/"; ; str = "accounts/change_profile_picture/")
      return str;
  }

  public void performRemove()
  {
    this.mRemove = true;
    super.perform();
  }

  public void performWithType(int paramInt)
  {
    this.mType = paramInt;
    super.perform();
  }

  public void preProcessInBackground()
    throws AbstractRequest.PreProcessException
  {
    try
    {
      if (!this.mRemove)
      {
        ByteArrayInputStream localByteArrayInputStream = compressedInputStream(getBitmapToSend(this.mType, this.mUri));
        getParams().put("profile_pic", localByteArrayInputStream, "profile_pic");
      }
      super.preProcessInBackground();
      return;
    }
    catch (Exception localException)
    {
    }
    throw new AbstractRequest.PreProcessException();
  }

  public User processInBackground(ApiResponse<User> paramApiResponse)
  {
    User localUser = null;
    if (paramApiResponse.hasRootValue("user"))
      localUser = (User)paramApiResponse.readRootValue("user", User.class);
    return localUser;
  }

  public void setUri(Uri paramUri)
  {
    this.mUri = paramUri;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.request.UpdateAvatarRequest
 * JD-Core Version:    0.6.0
 */
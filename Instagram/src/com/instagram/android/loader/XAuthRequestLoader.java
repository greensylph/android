package com.instagram.android.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import java.util.Arrays;
import java.util.List;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class XAuthRequestLoader extends AsyncTaskLoader<XAuthResponse>
{
  private static final String TAG = "XAuthRequestLoader";
  private String mConsumerKey;
  private String mConsumerSecret;
  private boolean mDeliverOnly;
  private String mPassword;
  private XAuthResponse mResponse;
  private String mUsername;
  private String mXAuthURL;

  public XAuthRequestLoader(Context paramContext)
  {
    super(paramContext);
  }

  public void deliverResult(XAuthResponse paramXAuthResponse)
  {
    if ((isReset()) && (paramXAuthResponse != null));
    this.mResponse = paramXAuthResponse;
    if (isStarted())
      super.deliverResult(paramXAuthResponse);
    if (paramXAuthResponse != null);
  }

  public XAuthResponse loadInBackground()
  {
    DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
    HttpPost localHttpPost = new HttpPost(this.mXAuthURL);
    CommonsHttpOAuthConsumer localCommonsHttpOAuthConsumer = new CommonsHttpOAuthConsumer(this.mConsumerKey, this.mConsumerSecret);
    BasicNameValuePair[] arrayOfBasicNameValuePair = new BasicNameValuePair[3];
    arrayOfBasicNameValuePair[0] = new BasicNameValuePair("x_auth_username", this.mUsername);
    arrayOfBasicNameValuePair[1] = new BasicNameValuePair("x_auth_password", this.mPassword);
    arrayOfBasicNameValuePair[2] = new BasicNameValuePair("x_auth_mode", "client_auth");
    List localList = Arrays.asList(arrayOfBasicNameValuePair);
    XAuthResponse.Builder localBuilder = new XAuthResponse.Builder();
    try
    {
      localHttpPost.setEntity(new UrlEncodedFormEntity(localList, "UTF-8"));
      localCommonsHttpOAuthConsumer.sign(localHttpPost);
      XAuthResponse localXAuthResponse2 = XAuthResponse.parse(localDefaultHttpClient.execute(localHttpPost));
      localXAuthResponse1 = localXAuthResponse2;
      return localXAuthResponse1;
    }
    catch (Exception localException)
    {
      while (true)
      {
        localBuilder.setErrorMessage(localException.getMessage());
        XAuthResponse localXAuthResponse1 = localBuilder.create();
      }
    }
  }

  public void onCanceled(XAuthResponse paramXAuthResponse)
  {
    super.onCanceled(paramXAuthResponse);
  }

  protected void onReset()
  {
    super.onReset();
    onStopLoading();
    if (this.mResponse != null)
      this.mResponse = null;
  }

  protected void onStartLoading()
  {
    if ((this.mResponse == null) && (!this.mDeliverOnly))
      forceLoad();
    if (this.mResponse != null)
      deliverResult(this.mResponse);
  }

  protected void onStopLoading()
  {
    cancelLoad();
  }

  public void setConsumeKeySecret(String paramString1, String paramString2)
  {
    this.mConsumerKey = paramString1;
    this.mConsumerSecret = paramString2;
  }

  public void setDeliverOnly(boolean paramBoolean)
  {
    this.mDeliverOnly = paramBoolean;
  }

  public void setUsernamePassword(String paramString1, String paramString2)
  {
    this.mUsername = paramString1;
    this.mPassword = paramString2;
  }

  public void setXAuthURL(String paramString)
  {
    this.mXAuthURL = paramString;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.loader.XAuthRequestLoader
 * JD-Core Version:    0.6.0
 */
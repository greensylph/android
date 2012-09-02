package com.instagram.api;

import android.content.Context;
import ch.boye.httpclientandroidlib.HttpEntity;
import ch.boye.httpclientandroidlib.HttpResponse;
import ch.boye.httpclientandroidlib.HttpVersion;
import ch.boye.httpclientandroidlib.client.methods.HttpDelete;
import ch.boye.httpclientandroidlib.client.methods.HttpEntityEnclosingRequestBase;
import ch.boye.httpclientandroidlib.client.methods.HttpGet;
import ch.boye.httpclientandroidlib.client.methods.HttpPost;
import ch.boye.httpclientandroidlib.client.methods.HttpPut;
import ch.boye.httpclientandroidlib.client.methods.HttpUriRequest;
import ch.boye.httpclientandroidlib.conn.scheme.PlainSocketFactory;
import ch.boye.httpclientandroidlib.conn.scheme.Scheme;
import ch.boye.httpclientandroidlib.conn.scheme.SchemeRegistry;
import ch.boye.httpclientandroidlib.conn.ssl.SSLSocketFactory;
import ch.boye.httpclientandroidlib.impl.client.DefaultHttpClient;
import ch.boye.httpclientandroidlib.impl.conn.tsccm.ThreadSafeClientConnManager;
import ch.boye.httpclientandroidlib.params.BasicHttpParams;
import ch.boye.httpclientandroidlib.params.HttpConnectionParams;
import ch.boye.httpclientandroidlib.params.HttpProtocolParams;
import ch.boye.httpclientandroidlib.protocol.BasicHttpContext;
import ch.boye.httpclientandroidlib.protocol.HttpContext;
import ch.boye.httpclientandroidlib.protocol.SyncBasicHttpContext;
import com.instagram.android.InstagramApplication;
import com.instagram.android.Log;
import java.security.KeyStore;
import java.util.Locale;

public class ApiHttpClient
{
  private static final String ACCEPT_LANG_FOR_US_LOCALE = "en-US";
  private static final int DEFAULT_CONNECTION_TIMEOUT = 10000;
  public static final String HTTP_CLIENT_SERVICE = "com.instagram.api.ApiHttpClient";
  private static final String TAG = "ApiHttpClient";
  private static String USER_AGENT = InstagramApplication.getHttpUserAgent();
  private String mAcceptsLanguage;
  private final DefaultHttpClient mHttpClient;
  private final SyncBasicHttpContext mHttpContext;

  public ApiHttpClient(PersistentCookieStore paramPersistentCookieStore)
  {
    BasicHttpParams localBasicHttpParams = new BasicHttpParams();
    HttpConnectionParams.setTcpNoDelay(localBasicHttpParams, true);
    HttpConnectionParams.setConnectionTimeout(localBasicHttpParams, 10000);
    HttpProtocolParams.setVersion(localBasicHttpParams, HttpVersion.HTTP_1_1);
    HttpProtocolParams.setUserAgent(localBasicHttpParams, USER_AGENT);
    SchemeRegistry localSchemeRegistry = new SchemeRegistry();
    localSchemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
    localSchemeRegistry.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));
    ThreadSafeClientConnManager localThreadSafeClientConnManager = new ThreadSafeClientConnManager(localSchemeRegistry);
    localThreadSafeClientConnManager.setDefaultMaxPerRoute(20);
    this.mHttpContext = new SyncBasicHttpContext(new BasicHttpContext());
    this.mHttpClient = new DefaultHttpClient(localThreadSafeClientConnManager, localBasicHttpParams);
    this.mHttpClient.setCookieStore(paramPersistentCookieStore);
    ApiHttpClient.1 local1 = new ApiHttpClient.1(this);
    this.mHttpClient.addRequestInterceptor(local1);
    this.mHttpClient.addResponseInterceptor(new ApiHttpClient.2(this));
  }

  private HttpEntityEnclosingRequestBase addEntityToRequestBase(HttpEntityEnclosingRequestBase paramHttpEntityEnclosingRequestBase, HttpEntity paramHttpEntity)
  {
    if (paramHttpEntity != null)
      paramHttpEntityEnclosingRequestBase.setEntity(paramHttpEntity);
    return paramHttpEntityEnclosingRequestBase;
  }

  private static void addLocaleToHttpAcceptLanguage(StringBuilder paramStringBuilder, Locale paramLocale)
  {
    String str1 = convertObsoleteLanguageCodeToNew(paramLocale.getLanguage());
    if (str1 != null)
    {
      paramStringBuilder.append(str1);
      String str2 = paramLocale.getCountry();
      if (str2 != null)
      {
        paramStringBuilder.append("-");
        paramStringBuilder.append(str2);
      }
    }
  }

  private static String convertObsoleteLanguageCodeToNew(String paramString)
  {
    if (paramString == null)
      paramString = null;
    while (true)
    {
      return paramString;
      if ("iw".equals(paramString))
      {
        paramString = "he";
        continue;
      }
      if ("in".equals(paramString))
      {
        paramString = "id";
        continue;
      }
      if (!"ji".equals(paramString))
        continue;
      paramString = "yi";
    }
  }

  private String getCurrentAcceptLanguage()
  {
    Locale localLocale = Locale.getDefault();
    if (this.mAcceptsLanguage == null)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      addLocaleToHttpAcceptLanguage(localStringBuilder, localLocale);
      if (!Locale.US.equals(localLocale))
      {
        if (localStringBuilder.length() > 0)
          localStringBuilder.append(", ");
        localStringBuilder.append("en-US");
      }
      this.mAcceptsLanguage = localStringBuilder.toString();
    }
    return this.mAcceptsLanguage;
  }

  public static ApiHttpClient getInstance(Context paramContext)
  {
    ApiHttpClient localApiHttpClient = (ApiHttpClient)paramContext.getSystemService("com.instagram.api.ApiHttpClient");
    if (localApiHttpClient == null)
      localApiHttpClient = (ApiHttpClient)paramContext.getApplicationContext().getSystemService("com.instagram.api.ApiHttpClient");
    if (localApiHttpClient == null)
      throw new IllegalStateException("ApiHttpClient not available");
    return localApiHttpClient;
  }

  private KeyStore getTruststore()
  {
    try
    {
      KeyStore localKeyStore = KeyStore.getInstance(KeyStore.getDefaultType());
      localKeyStore.load(null, null);
      return localKeyStore;
    }
    catch (Exception localException)
    {
    }
    throw new RuntimeException("Unable to load keystore");
  }

  private String getUrlWithQueryString(String paramString, RequestParams paramRequestParams)
  {
    if (paramRequestParams != null)
    {
      String str = paramRequestParams.getParamString();
      paramString = paramString + "?" + str;
    }
    return paramString;
  }

  private HttpEntity paramsToEntity(RequestParams paramRequestParams)
  {
    HttpEntity localHttpEntity = null;
    if (paramRequestParams != null)
      localHttpEntity = paramRequestParams.getEntity();
    return localHttpEntity;
  }

  public HttpPost constructPostRequest(String paramString, HttpEntity paramHttpEntity)
  {
    return (HttpPost)addEntityToRequestBase(new HttpPost(paramString), paramHttpEntity);
  }

  public HttpPost constructPostRequest(String paramString, RequestParams paramRequestParams)
  {
    return constructPostRequest(paramString, paramsToEntity(paramRequestParams));
  }

  public void delete(String paramString)
  {
    HttpDelete localHttpDelete = new HttpDelete(paramString);
    sendRequest(this.mHttpClient, this.mHttpContext, localHttpDelete);
  }

  public HttpResponse get(String paramString)
  {
    return get(paramString, null);
  }

  public HttpResponse get(String paramString, RequestParams paramRequestParams)
  {
    return sendRequest(this.mHttpClient, this.mHttpContext, new HttpGet(getUrlWithQueryString(paramString, paramRequestParams)));
  }

  public HttpGet getRequest(String paramString)
  {
    return new HttpGet(paramString);
  }

  public HttpResponse post(String paramString)
  {
    return post(paramString, (HttpEntity)null);
  }

  public HttpResponse post(String paramString, HttpEntity paramHttpEntity)
  {
    return sendRequest(this.mHttpClient, this.mHttpContext, addEntityToRequestBase(new HttpPost(paramString), paramHttpEntity));
  }

  public HttpResponse post(String paramString, RequestParams paramRequestParams)
  {
    return post(paramString, paramsToEntity(paramRequestParams));
  }

  public HttpUriRequest postRequest(String paramString, RequestParams paramRequestParams)
  {
    return addEntityToRequestBase(new HttpPost(paramString), paramsToEntity(paramRequestParams));
  }

  public HttpResponse put(String paramString)
  {
    return put(paramString, (HttpEntity)null);
  }

  public HttpResponse put(String paramString, HttpEntity paramHttpEntity)
  {
    return sendRequest(this.mHttpClient, this.mHttpContext, addEntityToRequestBase(new HttpPut(paramString), paramHttpEntity));
  }

  public HttpResponse put(String paramString, RequestParams paramRequestParams)
  {
    return put(paramString, paramsToEntity(paramRequestParams));
  }

  public HttpResponse sendRequest(HttpUriRequest paramHttpUriRequest)
  {
    return sendRequest(this.mHttpClient, this.mHttpContext, paramHttpUriRequest);
  }

  public HttpResponse sendRequest(DefaultHttpClient paramDefaultHttpClient, HttpContext paramHttpContext, HttpUriRequest paramHttpUriRequest)
  {
    try
    {
      HttpResponse localHttpResponse2 = paramDefaultHttpClient.execute(paramHttpUriRequest, paramHttpContext);
      localHttpResponse1 = localHttpResponse2;
      return localHttpResponse1;
    }
    catch (Exception localException)
    {
      while (true)
      {
        paramHttpUriRequest.abort();
        Log.e("ApiHttpClient", "Send request failed", localException);
        HttpResponse localHttpResponse1 = null;
      }
    }
  }

  public void setCookieStore(PersistentCookieStore paramPersistentCookieStore)
  {
    this.mHttpClient.setCookieStore(paramPersistentCookieStore);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.ApiHttpClient
 * JD-Core Version:    0.6.0
 */
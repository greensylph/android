package com.loopj.android.http;

import android.content.Context;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.zip.GZIPInputStream;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.SyncBasicHttpContext;

public class AsyncHttpClient
{
  private static final int DEFAULT_MAX_CONNECTIONS = 10;
  private static final int DEFAULT_MAX_RETRIES = 5;
  private static final int DEFAULT_SOCKET_TIMEOUT = 10000;
  private static final String ENCODING = "UTF-8";
  private static final String ENCODING_GZIP = "gzip";
  private static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
  private static final String VERSION = "1.3.1";
  private static int maxConnections = 10;
  private static int socketTimeout = 10000;
  private DefaultHttpClient httpClient;
  private HttpContext httpContext;
  private Map<Context, List<WeakReference<Future>>> requestMap;
  private ThreadPoolExecutor threadPool;

  public AsyncHttpClient()
  {
    BasicHttpParams localBasicHttpParams = new BasicHttpParams();
    ConnManagerParams.setTimeout(localBasicHttpParams, socketTimeout);
    ConnManagerParams.setMaxConnectionsPerRoute(localBasicHttpParams, new ConnPerRouteBean(maxConnections));
    ConnManagerParams.setMaxTotalConnections(localBasicHttpParams, 10);
    HttpConnectionParams.setSoTimeout(localBasicHttpParams, socketTimeout);
    HttpConnectionParams.setTcpNoDelay(localBasicHttpParams, true);
    HttpProtocolParams.setVersion(localBasicHttpParams, HttpVersion.HTTP_1_1);
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = "1.3.1";
    HttpProtocolParams.setUserAgent(localBasicHttpParams, String.format("android-async-http/%s (http://loopj.com/android-async-http)", arrayOfObject));
    SchemeRegistry localSchemeRegistry = new SchemeRegistry();
    localSchemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
    localSchemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
    ThreadSafeClientConnManager localThreadSafeClientConnManager = new ThreadSafeClientConnManager(localBasicHttpParams, localSchemeRegistry);
    this.httpContext = new SyncBasicHttpContext(new BasicHttpContext());
    this.httpClient = new DefaultHttpClient(localThreadSafeClientConnManager, localBasicHttpParams);
    this.httpClient.addRequestInterceptor(new HttpRequestInterceptor()
    {
      public void process(HttpRequest paramHttpRequest, HttpContext paramHttpContext)
      {
        if (!paramHttpRequest.containsHeader("Accept-Encoding"))
          paramHttpRequest.addHeader("Accept-Encoding", "gzip");
      }
    });
    this.httpClient.addResponseInterceptor(new HttpResponseInterceptor()
    {
      public void process(HttpResponse paramHttpResponse, HttpContext paramHttpContext)
      {
        Header localHeader = paramHttpResponse.getEntity().getContentEncoding();
        HeaderElement[] arrayOfHeaderElement;
        int i;
        if (localHeader != null)
        {
          arrayOfHeaderElement = localHeader.getElements();
          i = arrayOfHeaderElement.length;
        }
        for (int j = 0; ; j++)
        {
          if (j < i)
          {
            if (!arrayOfHeaderElement[j].getName().equalsIgnoreCase("gzip"))
              continue;
            paramHttpResponse.setEntity(new AsyncHttpClient.InflatingEntity(paramHttpResponse.getEntity()));
          }
          return;
        }
      }
    });
    this.httpClient.setHttpRequestRetryHandler(new RetryHandler(5));
    this.threadPool = ((ThreadPoolExecutor)Executors.newCachedThreadPool());
    this.requestMap = new WeakHashMap();
  }

  private HttpEntityEnclosingRequestBase addEntityToRequestBase(HttpEntityEnclosingRequestBase paramHttpEntityEnclosingRequestBase, HttpEntity paramHttpEntity)
  {
    if (paramHttpEntity != null)
      paramHttpEntityEnclosingRequestBase.setEntity(paramHttpEntity);
    return paramHttpEntityEnclosingRequestBase;
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

  private void sendRequest(DefaultHttpClient paramDefaultHttpClient, HttpContext paramHttpContext, HttpUriRequest paramHttpUriRequest, String paramString, AsyncHttpResponseHandler paramAsyncHttpResponseHandler, Context paramContext)
  {
    if (paramString != null)
      paramHttpUriRequest.addHeader("Content-Type", paramString);
    Future localFuture = this.threadPool.submit(new AsyncHttpRequest(paramDefaultHttpClient, paramHttpContext, paramHttpUriRequest, paramAsyncHttpResponseHandler));
    if (paramContext != null)
    {
      Object localObject = (List)this.requestMap.get(paramContext);
      if (localObject == null)
      {
        localObject = new LinkedList();
        this.requestMap.put(paramContext, localObject);
      }
      ((List)localObject).add(new WeakReference(localFuture));
    }
  }

  public void cancelRequests(Context paramContext, boolean paramBoolean)
  {
    List localList = (List)this.requestMap.get(paramContext);
    if (localList != null)
    {
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext())
      {
        Future localFuture = (Future)((WeakReference)localIterator.next()).get();
        if (localFuture == null)
          continue;
        localFuture.cancel(paramBoolean);
      }
    }
    this.requestMap.remove(paramContext);
  }

  public void delete(Context paramContext, String paramString, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    HttpDelete localHttpDelete = new HttpDelete(paramString);
    sendRequest(this.httpClient, this.httpContext, localHttpDelete, null, paramAsyncHttpResponseHandler, paramContext);
  }

  public void delete(String paramString, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    delete(null, paramString, paramAsyncHttpResponseHandler);
  }

  public void get(Context paramContext, String paramString, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(paramContext, paramString, null, paramAsyncHttpResponseHandler);
  }

  public void get(Context paramContext, String paramString, RequestParams paramRequestParams, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    sendRequest(this.httpClient, this.httpContext, new HttpGet(getUrlWithQueryString(paramString, paramRequestParams)), null, paramAsyncHttpResponseHandler, paramContext);
  }

  public void get(String paramString, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(null, paramString, null, paramAsyncHttpResponseHandler);
  }

  public void get(String paramString, RequestParams paramRequestParams, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    get(null, paramString, paramRequestParams, paramAsyncHttpResponseHandler);
  }

  public HttpClient getHttpClient()
  {
    return this.httpClient;
  }

  public void post(Context paramContext, String paramString, RequestParams paramRequestParams, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    post(paramContext, paramString, paramsToEntity(paramRequestParams), null, paramAsyncHttpResponseHandler);
  }

  public void post(Context paramContext, String paramString1, HttpEntity paramHttpEntity, String paramString2, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    sendRequest(this.httpClient, this.httpContext, addEntityToRequestBase(new HttpPost(paramString1), paramHttpEntity), paramString2, paramAsyncHttpResponseHandler, paramContext);
  }

  public void post(String paramString, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    post(null, paramString, null, paramAsyncHttpResponseHandler);
  }

  public void post(String paramString, RequestParams paramRequestParams, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    post(null, paramString, paramRequestParams, paramAsyncHttpResponseHandler);
  }

  public void put(Context paramContext, String paramString, RequestParams paramRequestParams, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    put(paramContext, paramString, paramsToEntity(paramRequestParams), null, paramAsyncHttpResponseHandler);
  }

  public void put(Context paramContext, String paramString1, HttpEntity paramHttpEntity, String paramString2, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    sendRequest(this.httpClient, this.httpContext, addEntityToRequestBase(new HttpPut(paramString1), paramHttpEntity), paramString2, paramAsyncHttpResponseHandler, paramContext);
  }

  public void put(String paramString, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    put(null, paramString, null, paramAsyncHttpResponseHandler);
  }

  public void put(String paramString, RequestParams paramRequestParams, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    put(null, paramRequestParams, paramAsyncHttpResponseHandler);
  }

  public void setCookieStore(CookieStore paramCookieStore)
  {
    this.httpContext.setAttribute("http.cookie-store", paramCookieStore);
  }

  public void setSSLSocketFactory(SSLSocketFactory paramSSLSocketFactory)
  {
    this.httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", paramSSLSocketFactory, 443));
  }

  public void setThreadPool(ThreadPoolExecutor paramThreadPoolExecutor)
  {
    this.threadPool = paramThreadPoolExecutor;
  }

  public void setUserAgent(String paramString)
  {
    HttpProtocolParams.setUserAgent(this.httpClient.getParams(), paramString);
  }

  private static class InflatingEntity extends HttpEntityWrapper
  {
    public InflatingEntity(HttpEntity paramHttpEntity)
    {
      super();
    }

    public InputStream getContent()
      throws IOException
    {
      return new GZIPInputStream(this.wrappedEntity.getContent());
    }

    public long getContentLength()
    {
      return -1L;
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.loopj.android.http.AsyncHttpClient
 * JD-Core Version:    0.6.0
 */
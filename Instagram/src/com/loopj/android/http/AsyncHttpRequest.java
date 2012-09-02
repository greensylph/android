package com.loopj.android.http;

import java.io.IOException;
import java.net.ConnectException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.protocol.HttpContext;

class AsyncHttpRequest
  implements Runnable
{
  private AbstractHttpClient client;
  private HttpContext context;
  private int executionCount;
  private HttpUriRequest request;
  private AsyncHttpResponseHandler responseHandler;

  public AsyncHttpRequest(AbstractHttpClient paramAbstractHttpClient, HttpContext paramHttpContext, HttpUriRequest paramHttpUriRequest, AsyncHttpResponseHandler paramAsyncHttpResponseHandler)
  {
    this.client = paramAbstractHttpClient;
    this.context = paramHttpContext;
    this.request = paramHttpUriRequest;
    this.responseHandler = paramAsyncHttpResponseHandler;
  }

  private void makeRequest()
    throws IOException
  {
    HttpResponse localHttpResponse = this.client.execute(this.request, this.context);
    if (this.responseHandler != null)
      this.responseHandler.sendResponseMessage(localHttpResponse);
  }

  private void makeRequestWithRetries()
    throws ConnectException
  {
    boolean bool = true;
    Object localObject = null;
    HttpRequestRetryHandler localHttpRequestRetryHandler = this.client.getHttpRequestRetryHandler();
    IOException localIOException2;
    while (bool)
      try
      {
        makeRequest();
        return;
      }
      catch (IOException localIOException1)
      {
        int j = 1 + this.executionCount;
        this.executionCount = j;
        bool = localHttpRequestRetryHandler.retryRequest(localIOException1, j, this.context);
      }
      catch (NullPointerException localNullPointerException)
      {
        localIOException2 = new IOException("NPE in HttpClient" + localNullPointerException.getMessage());
        int i = 1 + this.executionCount;
        this.executionCount = i;
        bool = localHttpRequestRetryHandler.retryRequest(localIOException2, i, this.context);
      }
    ConnectException localConnectException = new ConnectException();
    localConnectException.initCause(localIOException2);
    throw localConnectException;
  }

  public void run()
  {
    try
    {
      if (this.responseHandler != null)
        this.responseHandler.sendStartMessage();
      makeRequestWithRetries();
      if (this.responseHandler != null)
        this.responseHandler.sendFinishMessage();
      return;
    }
    catch (IOException localIOException)
    {
      while (true)
      {
        if (this.responseHandler == null)
          continue;
        this.responseHandler.sendFinishMessage();
        this.responseHandler.sendFailureMessage(localIOException);
      }
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.loopj.android.http.AsyncHttpRequest
 * JD-Core Version:    0.6.0
 */
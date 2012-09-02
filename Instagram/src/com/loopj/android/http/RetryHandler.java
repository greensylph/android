package com.loopj.android.http;

import android.os.SystemClock;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashSet;
import javax.net.ssl.SSLHandshakeException;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.protocol.HttpContext;

class RetryHandler
  implements HttpRequestRetryHandler
{
  private static final int RETRY_SLEEP_TIME_MILLIS = 1500;
  private static HashSet<Class<?>> exceptionBlacklist;
  private static HashSet<Class<?>> exceptionWhitelist = new HashSet();
  private int maxRetries;

  static
  {
    exceptionBlacklist = new HashSet();
    exceptionWhitelist.add(NoHttpResponseException.class);
    exceptionWhitelist.add(UnknownHostException.class);
    exceptionWhitelist.add(SocketException.class);
    exceptionBlacklist.add(InterruptedIOException.class);
    exceptionBlacklist.add(SSLHandshakeException.class);
  }

  public RetryHandler(int paramInt)
  {
    this.maxRetries = paramInt;
  }

  public boolean retryRequest(IOException paramIOException, int paramInt, HttpContext paramHttpContext)
  {
    int i = 0;
    Boolean localBoolean = (Boolean)paramHttpContext.getAttribute("http.request_sent");
    int j;
    if ((localBoolean != null) && (localBoolean.booleanValue()))
    {
      j = 1;
      if (paramInt <= this.maxRetries)
        break label60;
      label40: if (i == 0)
        break label133;
      SystemClock.sleep(1500L);
    }
    while (true)
    {
      return i;
      j = 0;
      break;
      label60: if (exceptionBlacklist.contains(paramIOException.getClass()))
        break label40;
      if (exceptionWhitelist.contains(paramIOException.getClass()))
      {
        i = 1;
        break label40;
      }
      if (j == 0)
      {
        i = 1;
        break label40;
      }
      if (((HttpUriRequest)paramHttpContext.getAttribute("http.request")).getMethod().equals("POST"))
        break label40;
      i = 1;
      break label40;
      label133: paramIOException.printStackTrace();
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.loopj.android.http.RetryHandler
 * JD-Core Version:    0.6.0
 */
package com.instagram.api;

import ch.boye.httpclientandroidlib.HttpException;
import ch.boye.httpclientandroidlib.HttpRequest;
import ch.boye.httpclientandroidlib.HttpRequestInterceptor;
import ch.boye.httpclientandroidlib.protocol.HttpContext;
import java.io.IOException;

class ApiHttpClient$1
  implements HttpRequestInterceptor
{
  public void process(HttpRequest paramHttpRequest, HttpContext paramHttpContext)
    throws HttpException, IOException
  {
    if (!paramHttpRequest.containsHeader("Accept-Encoding"))
      paramHttpRequest.addHeader("Accept-Encoding", "gzip");
    if (!paramHttpRequest.containsHeader("Accept-Language"))
      paramHttpRequest.addHeader("Accept-Language", ApiHttpClient.access$000(this.this$0));
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.ApiHttpClient.1
 * JD-Core Version:    0.6.0
 */
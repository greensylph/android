package com.instagram.api;

import ch.boye.httpclientandroidlib.Header;
import ch.boye.httpclientandroidlib.HeaderElement;
import ch.boye.httpclientandroidlib.HttpEntity;
import ch.boye.httpclientandroidlib.HttpException;
import ch.boye.httpclientandroidlib.HttpResponse;
import ch.boye.httpclientandroidlib.HttpResponseInterceptor;
import ch.boye.httpclientandroidlib.client.entity.GzipDecompressingEntity;
import ch.boye.httpclientandroidlib.protocol.HttpContext;
import java.io.IOException;

class ApiHttpClient$2
  implements HttpResponseInterceptor
{
  public void process(HttpResponse paramHttpResponse, HttpContext paramHttpContext)
    throws HttpException, IOException
  {
    Header localHeader = paramHttpResponse.getEntity().getContentEncoding();
    HeaderElement[] arrayOfHeaderElement;
    if (localHeader != null)
      arrayOfHeaderElement = localHeader.getElements();
    for (int i = 0; ; i++)
    {
      if (i < arrayOfHeaderElement.length)
      {
        if (!arrayOfHeaderElement[i].getName().equalsIgnoreCase("gzip"))
          continue;
        paramHttpResponse.setEntity(new GzipDecompressingEntity(paramHttpResponse.getEntity()));
      }
      return;
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.ApiHttpClient.2
 * JD-Core Version:    0.6.0
 */
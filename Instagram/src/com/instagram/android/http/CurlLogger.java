package com.instagram.android.http;

import com.instagram.android.Log;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.RequestWrapper;
import org.apache.http.protocol.HttpContext;

public class CurlLogger
  implements HttpRequestInterceptor
{
  private static String toCurl(HttpUriRequest paramHttpUriRequest, boolean paramBoolean)
    throws IOException
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("curl ");
    Header[] arrayOfHeader = paramHttpUriRequest.getAllHeaders();
    int i = arrayOfHeader.length;
    int j = 0;
    if (j < i)
    {
      Header localHeader = arrayOfHeader[j];
      if ((!paramBoolean) && ((localHeader.getName().equals("Authorization")) || (localHeader.getName().equals("Cookie"))));
      while (true)
      {
        j++;
        break;
        localStringBuilder.append("--header \"");
        localStringBuilder.append(localHeader.toString().trim());
        localStringBuilder.append("\" ");
      }
    }
    URI localURI = paramHttpUriRequest.getURI();
    if ((paramHttpUriRequest instanceof RequestWrapper))
    {
      HttpRequest localHttpRequest = ((RequestWrapper)paramHttpUriRequest).getOriginal();
      if ((localHttpRequest instanceof HttpUriRequest))
        localURI = ((HttpUriRequest)localHttpRequest).getURI();
    }
    localStringBuilder.append("\"");
    localStringBuilder.append(localURI);
    localStringBuilder.append("\"");
    if ((paramHttpUriRequest instanceof HttpEntityEnclosingRequest))
    {
      HttpEntity localHttpEntity = ((HttpEntityEnclosingRequest)paramHttpUriRequest).getEntity();
      if ((localHttpEntity != null) && (localHttpEntity.isRepeatable()))
      {
        if (localHttpEntity.getContentLength() >= 1024L)
          break label274;
        ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
        localHttpEntity.writeTo(localByteArrayOutputStream);
        String str = localByteArrayOutputStream.toString();
        localStringBuilder.append(" --data-ascii \"").append(str).append("\"");
      }
    }
    while (true)
    {
      return localStringBuilder.toString();
      label274: localStringBuilder.append(" [TOO MUCH DATA TO INCLUDE]");
    }
  }

  public void process(HttpRequest paramHttpRequest, HttpContext paramHttpContext)
    throws HttpException, IOException
  {
    Log.d("CurlLogger", toCurl((HttpUriRequest)paramHttpRequest, true));
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.http.CurlLogger
 * JD-Core Version:    0.6.0
 */
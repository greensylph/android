package com.instagram.android.loader;

import android.text.TextUtils;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

public class XAuthResponse
{
  private final Params mParams;

  private XAuthResponse(Params paramParams)
  {
    this.mParams = paramParams;
  }

  static XAuthResponse parse(String paramString)
  {
    XAuthResponse localXAuthResponse;
    if (paramString == null)
      localXAuthResponse = null;
    while (true)
    {
      return localXAuthResponse;
      HashMap localHashMap = new HashMap(2);
      String[] arrayOfString1 = TextUtils.split(paramString, "&");
      Builder localBuilder = new Builder();
      try
      {
        int i = arrayOfString1.length;
        for (int j = 0; j < i; j++)
        {
          String[] arrayOfString2 = arrayOfString1[j].split("=", 2);
          localHashMap.put(arrayOfString2[0], arrayOfString2[1]);
        }
        localBuilder.setToken((String)localHashMap.get("oauth_token")).setSecret((String)localHashMap.get("oauth_token_secret"));
        localXAuthResponse = localBuilder.create();
      }
      catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
      {
        while (true)
          localBuilder.setErrorMessage(paramString);
      }
    }
  }

  static XAuthResponse parse(HttpResponse paramHttpResponse)
  {
    Object localObject = null;
    try
    {
      String str = EntityUtils.toString(paramHttpResponse.getEntity());
      localObject = str;
      return parse(localObject);
    }
    catch (IOException localIOException)
    {
      while (true)
        localIOException.printStackTrace();
    }
  }

  public String getErrorMessage()
  {
    return this.mParams.errorMessage;
  }

  public String getSecret()
  {
    return this.mParams.secret;
  }

  public String getToken()
  {
    return this.mParams.token;
  }

  public boolean success()
  {
    if (this.mParams.errorMessage == null);
    for (int i = 1; ; i = 0)
      return i;
  }

  public static class Builder
  {
    private final XAuthResponse.Params P = new XAuthResponse.Params(null);

    public XAuthResponse create()
    {
      return new XAuthResponse(this.P, null);
    }

    public Builder setErrorMessage(String paramString)
    {
      XAuthResponse.Params.access$302(this.P, paramString);
      return this;
    }

    public Builder setSecret(String paramString)
    {
      XAuthResponse.Params.access$202(this.P, paramString);
      return this;
    }

    public Builder setToken(String paramString)
    {
      XAuthResponse.Params.access$102(this.P, paramString);
      return this;
    }
  }

  private static class Params
  {
    private String errorMessage = null;
    private String secret = null;
    private String token = null;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.loader.XAuthResponse
 * JD-Core Version:    0.6.0
 */
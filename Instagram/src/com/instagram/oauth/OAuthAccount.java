package com.instagram.oauth;

public abstract class OAuthAccount
{
  private final String mSecret;
  private final String mToken;

  protected OAuthAccount(String paramString1, String paramString2)
  {
    this.mToken = paramString1;
    this.mSecret = paramString2;
  }

  public String getSecret()
  {
    return this.mSecret;
  }

  public String getToken()
  {
    return this.mToken;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.oauth.OAuthAccount
 * JD-Core Version:    0.6.0
 */
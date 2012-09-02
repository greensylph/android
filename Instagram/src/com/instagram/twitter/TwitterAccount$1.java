package com.instagram.twitter;

import com.instagram.api.RequestParams;
import com.instagram.api.request.ApiPathRequest;

class TwitterAccount$1 extends ApiPathRequest
{
  public void constructParams(RequestParams paramRequestParams)
  {
    paramRequestParams.put("twitter_access_token_key", this.val$account.getToken());
    paramRequestParams.put("twitter_access_token_secret", this.val$account.getSecret());
    paramRequestParams.put("twitter_username", this.val$account.getUsername());
    paramRequestParams.put("share_to_twitter", "1");
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.twitter.TwitterAccount.1
 * JD-Core Version:    0.6.0
 */
package com.instagram.facebook;

import com.instagram.api.RequestParams;
import com.instagram.api.request.ApiPathRequest;

class FacebookAccount$1 extends ApiPathRequest
{
  public void constructParams(RequestParams paramRequestParams)
  {
    paramRequestParams.put("fb_access_token", this.val$accessToken);
    paramRequestParams.put("share_to_facebook", "1");
    paramRequestParams.put("fb_has_publish_actions", "1");
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.facebook.FacebookAccount.1
 * JD-Core Version:    0.6.0
 */
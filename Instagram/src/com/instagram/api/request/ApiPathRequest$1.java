package com.instagram.api.request;

import ch.boye.httpclientandroidlib.HttpResponse;
import ch.boye.httpclientandroidlib.StatusLine;
import com.instagram.android.Log;
import com.instagram.android.service.AppContext;
import com.instagram.api.ApiHttpClient;
import com.instagram.api.RequestParams;

class ApiPathRequest$1
  implements Runnable
{
  public void run()
  {
    RequestParams localRequestParams = new RequestParams();
    this.this$0.constructParams(localRequestParams);
    ApiHttpClient localApiHttpClient = ApiHttpClient.getInstance(AppContext.getContext());
    String str = ApiUrlHelper.expandPath(ApiPathRequest.access$000(this.this$0));
    HttpResponse localHttpResponse = localApiHttpClient.post(str, localRequestParams);
    Object localObject = "unknown";
    if ((localHttpResponse != null) && (localHttpResponse.getStatusLine() != null))
      localObject = Integer.valueOf(localHttpResponse.getStatusLine().getStatusCode());
    Log.d("ApiPathRequest", "Response code for path: " + str + ", " + localObject);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.request.ApiPathRequest.1
 * JD-Core Version:    0.6.0
 */
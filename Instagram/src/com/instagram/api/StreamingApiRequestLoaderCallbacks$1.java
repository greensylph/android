package com.instagram.api;

import android.content.Context;
import ch.boye.httpclientandroidlib.HttpEntity;
import ch.boye.httpclientandroidlib.HttpResponse;
import com.instagram.android.loader.ImmediateAsyncTaskLoaderAsyncTask;
import com.instagram.api.request.AbstractRequest.PreProcessException;
import com.instagram.api.request.AbstractStreamingRequest;
import com.instagram.util.InputStreamWrapper;
import java.io.InputStream;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

class StreamingApiRequestLoaderCallbacks$1 extends ImmediateAsyncTaskLoaderAsyncTask<ApiResponse<T>>
{
  public void deliverResult(ApiResponse<T> paramApiResponse)
  {
    super.deliverResult(paramApiResponse);
  }

  public StreamingApiResponse<T> loadInBackground()
  {
    try
    {
      StreamingApiRequestLoaderCallbacks.access$000(this.this$0).preProcessInBackground();
      ApiHttpClient localApiHttpClient = ApiHttpClient.getInstance(this.this$0.mContext);
      localStreamingApiResponse = new StreamingApiResponse(this.this$0.mContext);
      HttpResponse localHttpResponse = null;
      try
      {
        localHttpResponse = localApiHttpClient.sendRequest(StreamingApiRequestLoaderCallbacks.access$000(this.this$0).getRequest());
        HttpEntity localHttpEntity = localHttpResponse.getEntity();
        JsonFactory localJsonFactory = new JsonFactory();
        localObject = localHttpEntity.getContent();
        if (StreamingApiRequestLoaderCallbacks.access$000(this.this$0).cacheResponseFile() != null)
          localObject = new InputStreamWrapper((InputStream)localObject, StreamingApiRequestLoaderCallbacks.access$000(this.this$0).cacheResponseFile());
        localJsonParser = localJsonFactory.createJsonParser((InputStream)localObject);
        while (true)
        {
          if (localJsonParser.nextToken() == JsonToken.END_OBJECT)
            break label331;
          str = localJsonParser.getCurrentName();
          if (!"status".equals(str))
            break;
          localJsonParser.nextToken();
          localStreamingApiResponse.setStatus(localJsonParser.getText());
        }
      }
      catch (Exception localException)
      {
        localStreamingApiResponse = StreamingApiResponse.createWithError("An unknown parse error has occurred.");
      }
      if (localStreamingApiResponse != null)
      {
        if (localHttpResponse != null)
          localStreamingApiResponse.setStatusLine(localHttpResponse.getStatusLine());
        if ((localStreamingApiResponse.isOk()) && (localStreamingApiResponse.getErrorMessage() == null))
          this.this$0.onApiResponseObjectCreated(localStreamingApiResponse);
      }
      else
      {
        localStreamingApiResponse.setIsNetworkResponse(true);
        return localStreamingApiResponse;
      }
    }
    catch (AbstractRequest.PreProcessException localPreProcessException)
    {
      while (true)
      {
        StreamingApiResponse localStreamingApiResponse;
        Object localObject;
        JsonParser localJsonParser;
        String str;
        StreamingApiResponse.createWithError(localPreProcessException.getMessage());
        continue;
        if ("message".equals(str))
        {
          localJsonParser.nextToken();
          localStreamingApiResponse.setErrorMessage(localJsonParser.getText());
          continue;
        }
        if ("_messages".equals(str))
        {
          localJsonParser.nextToken();
          localStreamingApiResponse.setSystemMessages(StreamingApiRequestLoaderCallbacks.access$100(this.this$0, localJsonParser));
          continue;
        }
        StreamingApiRequestLoaderCallbacks.access$000(this.this$0).processResponseField(str, localJsonParser, localStreamingApiResponse);
        continue;
        label331: localJsonParser.close();
        ((InputStream)localObject).close();
        StreamingApiRequestLoaderCallbacks.access$000(this.this$0).onResponseParsed(localStreamingApiResponse);
        continue;
        StreamingApiRequestLoaderCallbacks.access$000(this.this$0).handleErrorInBackground(localStreamingApiResponse);
      }
    }
  }

  protected void onStartLoading()
  {
    super.onStartLoading();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.StreamingApiRequestLoaderCallbacks.1
 * JD-Core Version:    0.6.0
 */
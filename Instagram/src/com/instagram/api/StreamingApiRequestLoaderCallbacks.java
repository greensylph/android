package com.instagram.api;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.Loader;
import com.instagram.api.request.AbstractStreamingRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class StreamingApiRequestLoaderCallbacks<T> extends ApiRequestLoaderCallbacks<T>
{
  public static final String PARSE_ERROR_MESSAGE = "An unknown parse error has occurred.";
  private final AbstractStreamingRequest<T> mApiRequest;

  public StreamingApiRequestLoaderCallbacks(Context paramContext, AbstractStreamingRequest<T> paramAbstractStreamingRequest, AbstractApiCallbacks<T> paramAbstractApiCallbacks)
  {
    super(paramContext, paramAbstractStreamingRequest, paramAbstractApiCallbacks);
    this.mApiRequest = paramAbstractStreamingRequest;
  }

  private Collection<HashMap<String, String>> parseMessageSystems(JsonParser paramJsonParser)
    throws IOException
  {
    ArrayList localArrayList = null;
    if (paramJsonParser.getCurrentToken() != JsonToken.VALUE_NULL)
      while (paramJsonParser.nextToken() != JsonToken.END_ARRAY)
      {
        String str1 = null;
        String str2 = null;
        while (paramJsonParser.nextToken() != JsonToken.END_OBJECT)
        {
          String str3 = paramJsonParser.getCurrentName();
          if ("key".equals(str3))
          {
            paramJsonParser.nextToken();
            str1 = paramJsonParser.getText();
            continue;
          }
          if (!"time".equals(str3))
            continue;
          paramJsonParser.nextToken();
          str2 = paramJsonParser.getText();
        }
        if ((str1 == null) || (str2 == null))
          continue;
        if (localArrayList == null)
          localArrayList = new ArrayList();
        HashMap localHashMap = new HashMap();
        localHashMap.put("key", str1);
        localHashMap.put("time", str2);
        localArrayList.add(localHashMap);
      }
    return localArrayList;
  }

  protected void onApiResponseObjectCreated(StreamingApiResponse<T> paramStreamingApiResponse)
  {
  }

  public Loader<ApiResponse<T>> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    if (getApiCallbacks() != null)
      getApiCallbacks().onRequestStart();
    return new StreamingApiRequestLoaderCallbacks.1(this, this.mContext);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.StreamingApiRequestLoaderCallbacks
 * JD-Core Version:    0.6.0
 */
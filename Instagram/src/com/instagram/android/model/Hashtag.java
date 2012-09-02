package com.instagram.android.model;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.LocalBroadcastManager;
import ch.boye.httpclientandroidlib.client.methods.HttpUriRequest;
import com.instagram.android.Log;
import com.instagram.android.service.AppContext;
import com.instagram.api.ApiHttpClient;
import com.instagram.api.ApiResponse;
import com.instagram.api.RequestParams;
import com.instagram.api.request.AbstractRequest;
import com.instagram.util.LoaderUtil;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class Hashtag
{
  public static String BROADCAST_HASHTAG_UPDATED = "com.instagram.android.model.Hashtag.BROADCAST_HASHTAG_UPDATED";
  public static final String TAG = "Hashtag";
  private static boolean sContentAdvisoryWarningsLoaded;
  private static final Map<String, Map<String, String>> sWarningTags = new HashMap();
  private int mMediaCount;
  private String mTagname;

  static
  {
    sContentAdvisoryWarningsLoaded = false;
  }

  private Hashtag()
  {
  }

  public Hashtag(String paramString)
  {
    this.mTagname = paramString;
  }

  public Hashtag(String paramString, LoaderManager paramLoaderManager)
  {
    this.mTagname = paramString;
    new HashtagRequest(AppContext.getContext(), paramLoaderManager, paramString).perform();
  }

  public static Hashtag fromJsonParser(JsonParser paramJsonParser)
    throws IOException
  {
    Hashtag localHashtag = null;
    while (paramJsonParser.nextToken() != JsonToken.END_OBJECT)
    {
      String str = paramJsonParser.getCurrentName();
      if ((str != null) && (localHashtag == null))
        localHashtag = new Hashtag();
      if ("media_count".equals(str))
      {
        paramJsonParser.nextToken();
        localHashtag.mMediaCount = paramJsonParser.getIntValue();
        continue;
      }
      if (!"name".equals(str))
        continue;
      paramJsonParser.nextToken();
      localHashtag.mTagname = paramJsonParser.getText();
    }
    return localHashtag;
  }

  public static String getContentAdvisoryWarning(String paramString)
  {
    String str1 = paramString.toLowerCase();
    Object[] arrayOfObject;
    if (sWarningTags.containsKey(str1))
    {
      Map localMap = (Map)sWarningTags.get(str1);
      arrayOfObject = new Object[2];
      arrayOfObject[0] = localMap.get("message");
      arrayOfObject[1] = localMap.get("url");
    }
    for (String str2 = String.format("%s\n%s", arrayOfObject); ; str2 = null)
      return str2;
  }

  public static String getHashtagBroadcastId(String paramString)
  {
    return BROADCAST_HASHTAG_UPDATED + "|" + paramString;
  }

  public static void loadContentAdvisoryWarnings()
  {
    monitorenter;
    try
    {
      boolean bool = sContentAdvisoryWarningsLoaded;
      if (bool);
      while (true)
      {
        return;
        sContentAdvisoryWarningsLoaded = true;
        new LoadContentAdvisoryWarningsTask(null).execute((Void[])null);
      }
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public boolean equals(Object paramObject)
  {
    int i = 1;
    if (this == paramObject);
    Hashtag localHashtag;
    do
    {
      while (true)
      {
        return i;
        if ((paramObject != null) && (getClass() == paramObject.getClass()))
          break;
        i = 0;
      }
      localHashtag = (Hashtag)paramObject;
      if (this.mTagname == null)
        break;
    }
    while (this.mTagname.equals(localHashtag.mTagname));
    while (true)
    {
      i = 0;
      break;
      if (localHashtag.mTagname == null)
        break;
    }
  }

  public int getMediaCount()
  {
    return this.mMediaCount;
  }

  public String getTagName()
  {
    return this.mTagname;
  }

  public int hashCode()
  {
    if (this.mTagname != null);
    for (int i = this.mTagname.hashCode(); ; i = 0)
      return i;
  }

  public void onSuccess(JsonNode paramJsonNode)
  {
    this.mMediaCount = paramJsonNode.get("media_count").asInt();
    JsonNode localJsonNode = paramJsonNode.get("name");
    if ((localJsonNode != null) && (localJsonNode.isNull()))
      this.mTagname = localJsonNode.asText();
    LocalBroadcastManager.getInstance(AppContext.getContext()).sendBroadcast(new Intent(getHashtagBroadcastId(this.mTagname)));
  }

  private class HashtagRequest extends AbstractRequest
  {
    private String mTagname;

    public HashtagRequest(Context paramLoaderManager, LoaderManager paramString, String arg4)
    {
      super(paramString, LoaderUtil.getUniqueId(), null);
      Object localObject;
      this.mTagname = localObject;
    }

    protected HttpUriRequest buildRequest(ApiHttpClient paramApiHttpClient, String paramString, RequestParams paramRequestParams)
    {
      return paramApiHttpClient.getRequest(paramString);
    }

    protected String getPath()
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = this.mTagname;
      return String.format("tags/%s/info", arrayOfObject);
    }

    public void handleErrorInBackground(ApiResponse paramApiResponse)
    {
      super.handleErrorInBackground(paramApiResponse);
    }

    public Object processInBackground(ApiResponse paramApiResponse)
    {
      Hashtag.this.onSuccess(paramApiResponse.getRootNode());
      return null;
    }
  }

  private static class LoadContentAdvisoryWarningsTask extends AsyncTask<Void, Void, Void>
  {
    private Context mContext = AppContext.getContext();

    private InputStream openJson()
    {
      try
      {
        InputStream localInputStream2 = this.mContext.getAssets().open("tagwarnings.json");
        localInputStream1 = localInputStream2;
        return localInputStream1;
      }
      catch (IOException localIOException)
      {
        while (true)
        {
          Log.e("Hashtag", "Unable to load tagwarnings json", localIOException);
          InputStream localInputStream1 = null;
        }
      }
    }

    private void processJson(InputStream paramInputStream)
      throws IOException
    {
      JsonParser localJsonParser = new JsonFactory().createJsonParser(paramInputStream);
      localJsonParser.nextToken();
      if (localJsonParser.getCurrentToken() != JsonToken.START_ARRAY)
      {
        Log.e("Hashtag", "Invalid json format for tag warnings");
        return;
      }
      while (true)
      {
        HashMap localHashMap;
        HashSet localHashSet;
        if (localJsonParser.nextToken() != JsonToken.END_ARRAY)
        {
          if (localJsonParser.getCurrentToken() != JsonToken.START_OBJECT)
          {
            Log.e("Hashtag", "Failed to parse root object for json tag warnings");
            break;
          }
          localHashMap = new HashMap(3);
          localHashSet = new HashSet();
        }
        while (true)
        {
          if (localJsonParser.nextToken() == JsonToken.END_OBJECT)
            break label259;
          String str2 = localJsonParser.getCurrentName();
          if ("message".equals(str2))
          {
            localJsonParser.nextToken();
            localHashMap.put("message", localJsonParser.getText());
            continue;
          }
          if ("url".equals(str2))
          {
            localJsonParser.nextToken();
            localHashMap.put("url", localJsonParser.getText());
            continue;
          }
          if ("category".equals(str2))
          {
            localJsonParser.nextToken();
            localHashMap.put("category", localJsonParser.getText());
            continue;
          }
          if (!"tags".equals(str2))
            continue;
          localJsonParser.nextToken();
          if (localJsonParser.getCurrentToken() != JsonToken.START_ARRAY)
          {
            Log.e("Hashtag", "Failed to parse tags for json tag warnings");
            break;
            break;
          }
          while (localJsonParser.nextToken() != JsonToken.END_ARRAY)
            localHashSet.add(localJsonParser.getText());
        }
        label259: Iterator localIterator = localHashSet.iterator();
        while (localIterator.hasNext())
        {
          String str1 = (String)localIterator.next();
          Hashtag.sWarningTags.put(str1.toLowerCase(), localHashMap);
        }
      }
    }

    protected Void doInBackground(Void[] paramArrayOfVoid)
    {
      InputStream localInputStream = openJson();
      if (localInputStream != null);
      try
      {
        processJson(localInputStream);
        return null;
      }
      catch (IOException localIOException)
      {
        while (true)
          Log.e("Hashtag", "Failed to process tag warnings json", localIOException);
      }
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.model.Hashtag
 * JD-Core Version:    0.6.0
 */
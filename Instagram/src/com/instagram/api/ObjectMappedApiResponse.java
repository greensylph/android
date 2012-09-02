package com.instagram.api;

import android.content.Context;
import ch.boye.httpclientandroidlib.HttpEntity;
import ch.boye.httpclientandroidlib.HttpResponse;
import ch.boye.httpclientandroidlib.StatusLine;
import ch.boye.httpclientandroidlib.util.EntityUtils;
import com.instagram.android.service.AppContext;
import com.instagram.android.service.CustomObjectMapper;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import org.codehaus.jackson.JsonNode;

public class ObjectMappedApiResponse<T> extends ApiResponse<T>
{
  private String mErrorMessage;
  private CustomObjectMapper mMapper;
  private boolean mNetworkRequest;
  JsonNode mRootNode;
  T mSuccessObject;

  protected ObjectMappedApiResponse(Context paramContext, String paramString)
    throws Exception
  {
    this.mMapper = CustomObjectMapper.getInstance(paramContext);
    this.mRootNode = ((JsonNode)this.mMapper.readValue(paramString, JsonNode.class));
  }

  public ObjectMappedApiResponse(String paramString)
  {
    this.mErrorMessage = paramString;
  }

  public static <T> ObjectMappedApiResponse<T> createWithError(String paramString)
  {
    if (paramString == null)
      paramString = NETWORK_ERROR_MESSAGE;
    return new ObjectMappedApiResponse(paramString);
  }

  public static <T> ObjectMappedApiResponse<T> parseResponse(Context paramContext, HttpResponse paramHttpResponse)
  {
    try
    {
      HttpEntity localHttpEntity = paramHttpResponse.getEntity();
      localObjectMappedApiResponse = new ObjectMappedApiResponse(paramContext, EntityUtils.toString(localHttpEntity));
      EntityUtils.consume(localHttpEntity);
      localObjectMappedApiResponse.setStatusLine(paramHttpResponse.getStatusLine());
      localObjectMappedApiResponse.setIsNetworkResponse(true);
      StatusLine localStatusLine = paramHttpResponse.getStatusLine();
      if ((!localObjectMappedApiResponse.isOk()) || (localStatusLine.getStatusCode() >= 300))
      {
        String str = localObjectMappedApiResponse.getStatusMessage();
        if (str == null)
          str = UNKNOWN_ERROR_MESSAGE;
        localObjectMappedApiResponse.setErrorMessage(str);
      }
      return localObjectMappedApiResponse;
    }
    catch (Exception localException)
    {
      while (true)
        ObjectMappedApiResponse localObjectMappedApiResponse = createWithError(NETWORK_ERROR_MESSAGE);
    }
  }

  private HashMap<String, String> parseSystemMessageNode(Iterator<Map.Entry<String, JsonNode>> paramIterator)
  {
    HashMap localHashMap = new HashMap();
    while (paramIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)paramIterator.next();
      localHashMap.put(localEntry.getKey(), ((JsonNode)localEntry.getValue()).asText());
    }
    return localHashMap;
  }

  private Collection<HashMap<String, String>> parseSystemMessages(Iterator<JsonNode> paramIterator)
  {
    ArrayList localArrayList = new ArrayList();
    while (paramIterator.hasNext())
      localArrayList.add(parseSystemMessageNode(((JsonNode)paramIterator.next()).getFields()));
    return localArrayList;
  }

  public String getErrorMessage()
  {
    return this.mErrorMessage;
  }

  public String getErrorTitle()
  {
    JsonNode localJsonNode;
    if (getRootNode() != null)
    {
      localJsonNode = getRootNode().get("error_title");
      if (localJsonNode == null);
    }
    for (String str = localJsonNode.asText(); ; str = AppContext.getContext().getString(2131230953))
      return str;
  }

  public CustomObjectMapper getMapper()
  {
    return this.mMapper;
  }

  public JsonNode getRootNode()
  {
    return this.mRootNode;
  }

  public String getStatus()
  {
    JsonNode localJsonNode = null;
    if (getRootNode() != null)
      localJsonNode = getRootNode().get("status");
    if (localJsonNode != null);
    for (String str = localJsonNode.asText(); ; str = null)
      return str;
  }

  public String getStatusMessage()
  {
    JsonNode localJsonNode = getRootNode().get("message");
    if (localJsonNode != null);
    for (String str = localJsonNode.asText(); ; str = null)
      return str;
  }

  public T getSuccessObject()
  {
    return this.mSuccessObject;
  }

  public Collection<HashMap<String, String>> getSystemMessages()
  {
    JsonNode localJsonNode;
    if (getRootNode() != null)
    {
      localJsonNode = getRootNode().get("_messages");
      if (localJsonNode == null);
    }
    for (Collection localCollection = parseSystemMessages(localJsonNode.getElements()); ; localCollection = null)
      return localCollection;
  }

  public boolean hasRootValue(String paramString)
  {
    return getRootNode().has(paramString);
  }

  public boolean isNetworkRequest()
  {
    return this.mNetworkRequest;
  }

  public boolean isOk()
  {
    if ((this.mErrorMessage == null) && (getStatus().equalsIgnoreCase("ok")));
    for (int i = 1; ; i = 0)
      return i;
  }

  public <T> ArrayList<T> readRootArrayList(String paramString, Class<T> paramClass)
  {
    JsonNode localJsonNode = getRootNode();
    return getMapper().readArrayList(localJsonNode.get(paramString), paramClass);
  }

  public T readRootValue(String paramString, Class<T> paramClass)
  {
    JsonNode localJsonNode = getRootNode();
    return getMapper().readValue(localJsonNode.get(paramString), paramClass);
  }

  public void setErrorMessage(String paramString)
  {
    this.mErrorMessage = paramString;
  }

  public void setIsNetworkResponse(boolean paramBoolean)
  {
    this.mNetworkRequest = paramBoolean;
  }

  public void setSuccessObject(T paramT)
  {
    this.mSuccessObject = paramT;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.ObjectMappedApiResponse
 * JD-Core Version:    0.6.0
 */
package com.instagram.util;

import com.instagram.android.Log;
import java.util.ArrayList;
import org.codehaus.jackson.JsonNode;
import org.json.JSONArray;
import org.json.JSONObject;

public class JSONUtil
{
  public static boolean exists(JsonNode paramJsonNode, String paramString)
  {
    if ((paramJsonNode.has(paramString)) && (!paramJsonNode.get(paramString).isNull()));
    for (int i = 1; ; i = 0)
      return i;
  }

  public static <T> ArrayList<T> optArray(JSONArray paramJSONArray, JsonToObject<T> paramJsonToObject)
  {
    ArrayList localArrayList = new ArrayList();
    if (paramJSONArray != null)
    {
      int i = paramJSONArray.length();
      int j = 0;
      if (j < i)
      {
        JSONObject localJSONObject = paramJSONArray.optJSONObject(j);
        if (localJSONObject != null)
        {
          Object localObject = paramJsonToObject.getObject(localJSONObject);
          if (localObject == null)
            break label67;
          localArrayList.add(localObject);
        }
        while (true)
        {
          j++;
          break;
          label67: Log.w("JSONUtil", "Error parsing object " + localJSONObject.toString());
        }
      }
    }
    return localArrayList;
  }

  public static Integer optInt(JSONObject paramJSONObject, String paramString)
  {
    if (paramJSONObject.isNull(paramString));
    for (Integer localInteger = null; ; localInteger = Integer.valueOf(paramJSONObject.optInt(paramString)))
      return localInteger;
  }

  public static String optJSONString(JSONObject paramJSONObject, String paramString)
  {
    return optJSONString(paramJSONObject, paramString, null);
  }

  public static String optJSONString(JSONObject paramJSONObject, String paramString1, String paramString2)
  {
    if (paramJSONObject.isNull(paramString1));
    while (true)
    {
      return paramString2;
      paramString2 = paramJSONObject.optString(paramString1, paramString2);
    }
  }

  public static Long optLong(JSONObject paramJSONObject, String paramString)
  {
    if (paramJSONObject.isNull(paramString));
    for (Long localLong = null; ; localLong = Long.valueOf(paramJSONObject.optLong(paramString)))
      return localLong;
  }

  public static String safeParsePK(JsonNode paramJsonNode)
  {
    if (paramJsonNode.get("id") != null);
    for (String str = paramJsonNode.get("id").asText(); ; str = paramJsonNode.get("pk").asText())
    {
      return str;
      if (paramJsonNode.get("pk") == null)
        break;
    }
    throw new RuntimeException("No primary key found for parsed object");
  }

  public static abstract interface JsonToObject<T>
  {
    public abstract T getObject(JSONObject paramJSONObject);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.util.JSONUtil
 * JD-Core Version:    0.6.0
 */
package com.loopj.android.http;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JsonHttpResponseHandler extends AsyncHttpResponseHandler
{
  protected void handleSuccessMessage(String paramString)
  {
    super.handleSuccessMessage(paramString);
    try
    {
      Object localObject = parseResponse(paramString);
      if ((localObject instanceof JSONObject))
        onSuccess((JSONObject)localObject);
      else if ((localObject instanceof JSONArray))
        onSuccess((JSONArray)localObject);
    }
    catch (JSONException localJSONException)
    {
      onFailure(localJSONException);
    }
  }

  public void onSuccess(JSONArray paramJSONArray)
  {
  }

  public void onSuccess(JSONObject paramJSONObject)
  {
  }

  protected Object parseResponse(String paramString)
    throws JSONException
  {
    return new JSONTokener(paramString).nextValue();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.loopj.android.http.JsonHttpResponseHandler
 * JD-Core Version:    0.6.0
 */
package com.instagram.foursquare;

import android.location.Location;
import com.instagram.android.Log;
import com.instagram.android.location.Venue;
import com.instagram.android.service.AppContext;
import com.instagram.android.service.CustomObjectMapper;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.JsonNode;

public class Foursquare
{
  private static final String TAG = "Foursquare";

  public static void venuesNearby(Location paramLocation, String paramString, RequestListener<List<Venue>> paramRequestListener)
  {
    RequestParams localRequestParams = FoursquareClient.makeParams();
    localRequestParams.put("v", "20111101");
    if (paramString != null)
      localRequestParams.put("query", paramString);
    localRequestParams.put("limit", "50");
    localRequestParams.put("intent", "checkin");
    localRequestParams.put("ll", paramLocation.getLatitude() + "," + paramLocation.getLongitude());
    FoursquareClient.get("venues/search", localRequestParams, new AsyncHttpResponseHandler(paramRequestListener)
    {
      public void onSuccess(String paramString)
      {
        CustomObjectMapper localCustomObjectMapper = CustomObjectMapper.getInstance(AppContext.getContext());
        Foursquare.1.1 local1 = new Foursquare.1.1(this);
        try
        {
          ArrayList localArrayList = (ArrayList)localCustomObjectMapper.readValue(((JsonNode)localCustomObjectMapper.readValue(paramString, JsonNode.class)).get("response").get("venues"), local1);
          this.val$listener.onSuccess(localArrayList);
          return;
        }
        catch (IOException localIOException)
        {
          while (true)
            Log.e("Foursquare", "Unable to parse locations", localIOException);
        }
      }
    });
  }

  public static abstract interface RequestListener<T>
  {
    public abstract void onFailure();

    public abstract void onSuccess(T paramT);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.foursquare.Foursquare
 * JD-Core Version:    0.6.0
 */
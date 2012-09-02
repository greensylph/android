package com.instagram.android.location;

import android.location.Location;
import com.instagram.android.Log;
import com.instagram.android.model.User;
import com.instagram.android.service.AppContext;
import com.instagram.android.service.AuthHelper;
import com.instagram.android.service.VenueStore;
import com.instagram.util.JSONUtil;
import com.instagram.util.StringUtil;
import java.io.IOException;
import org.apache.commons.lang3.StringEscapeUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class Venue
{
  public static final String EXTERNAL_SOURCE_FOURSQUARE = "foursquare";
  private static final String TAG = "Location";
  private static final VenueStore sVenueStore = VenueStore.getInstance(AppContext.getContext());
  public String address;
  public String externalId;
  public String externalSource;
  public String id;
  public Double latitude;
  public Double longitude;
  public boolean mIsCustomVenue;
  public String name;

  public static Venue fromCustomName(String paramString, Location paramLocation)
  {
    Venue localVenue = new Venue();
    localVenue.name = paramString;
    User localUser = AuthHelper.getInstance().getCurrentUser();
    localVenue.externalSource = ("user-" + localUser.getId());
    if (paramLocation == null)
      Log.d("Location", "No location when creating custom venue");
    while (true)
    {
      return localVenue;
      localVenue.latitude = Double.valueOf(paramLocation.getLatitude());
      localVenue.longitude = Double.valueOf(paramLocation.getLongitude());
    }
  }

  public static Venue fromFoursquareVenueNode(JsonNode paramJsonNode)
  {
    Venue localVenue = new Venue();
    JsonNode localJsonNode = paramJsonNode.get("location");
    localVenue.name = StringUtil.stripEmoji(StringEscapeUtils.unescapeXml(paramJsonNode.get("name").asText()));
    if (localJsonNode.get("address") != null)
      localVenue.address = localJsonNode.get("address").asText();
    localVenue.externalId = JSONUtil.safeParsePK(paramJsonNode);
    localVenue.externalSource = "foursquare";
    if (localJsonNode.get("lat") != null)
      localVenue.latitude = Double.valueOf(localJsonNode.get("lat").asDouble());
    if (localJsonNode.get("lng") != null)
      localVenue.longitude = Double.valueOf(localJsonNode.get("lng").asDouble());
    return localVenue;
  }

  public static Venue fromMediaLocationParser(JsonParser paramJsonParser)
    throws IOException
  {
    Object localObject;
    if (paramJsonParser.getCurrentToken() == JsonToken.VALUE_NULL)
      localObject = null;
    while (true)
    {
      return localObject;
      localObject = new Venue();
      Venue localVenue = null;
      while (paramJsonParser.nextToken() != JsonToken.END_OBJECT)
      {
        String str = paramJsonParser.getCurrentName();
        if (("id".equals(str)) || ("pk".equals(str)))
        {
          paramJsonParser.nextToken();
          ((Venue)localObject).id = paramJsonParser.getText();
          localVenue = (Venue)sVenueStore.get(((Venue)localObject).id);
          if (localVenue == null)
            continue;
          localVenue.copyMembers((Venue)localObject);
          localObject = localVenue;
          continue;
        }
        if ("name".equals(str))
        {
          paramJsonParser.nextToken();
          ((Venue)localObject).name = StringUtil.stripEmoji(StringEscapeUtils.unescapeXml(paramJsonParser.getText()));
          continue;
        }
        if ("lat".equals(str))
        {
          paramJsonParser.nextToken();
          ((Venue)localObject).latitude = Double.valueOf(paramJsonParser.getValueAsDouble());
          continue;
        }
        if ("lng".equals(str))
        {
          paramJsonParser.nextToken();
          ((Venue)localObject).longitude = Double.valueOf(paramJsonParser.getValueAsDouble());
          continue;
        }
        if ("address".equals(str))
        {
          paramJsonParser.nextToken();
          if (paramJsonParser.getCurrentToken() == JsonToken.VALUE_NULL)
            continue;
          ((Venue)localObject).address = paramJsonParser.getText();
          continue;
        }
        if ("external_source".equals(str))
        {
          paramJsonParser.nextToken();
          ((Venue)localObject).externalSource = paramJsonParser.getText();
          continue;
        }
        if ("external_id".equals(str))
        {
          paramJsonParser.nextToken();
          ((Venue)localObject).externalId = paramJsonParser.getText();
          continue;
        }
        if (!"foursquare_v2_id".equals(str))
          continue;
        paramJsonParser.nextToken();
        ((Venue)localObject).externalId = paramJsonParser.getText();
      }
      if (((Venue)localObject).id == null)
      {
        localObject = null;
        continue;
      }
      if (localVenue != null)
        continue;
      sVenueStore.put(((Venue)localObject).id, (Venue)localObject);
    }
  }

  public void copyMembers(Venue paramVenue)
  {
    if (paramVenue.id != null)
      this.id = paramVenue.id;
    if (paramVenue.name != null)
      this.name = paramVenue.name;
    if (paramVenue.address != null)
      this.address = paramVenue.address;
    if (paramVenue.externalId != null)
      this.externalId = paramVenue.externalId;
    if (paramVenue.externalSource != null)
      this.externalSource = paramVenue.externalSource;
    if (paramVenue.latitude != null)
      this.latitude = paramVenue.latitude;
    if (paramVenue.longitude != null)
      this.longitude = paramVenue.longitude;
  }

  public boolean getIsCustomVenue()
  {
    return this.mIsCustomVenue;
  }

  public void setIsCustomVenue(boolean paramBoolean)
  {
    this.mIsCustomVenue = paramBoolean;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.location.Venue
 * JD-Core Version:    0.6.0
 */
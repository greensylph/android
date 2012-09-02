package com.instagram.android.model;

import com.instagram.android.service.CustomObjectMapper;
import java.util.ArrayList;
import org.codehaus.jackson.JsonNode;

public class MediaFeedResponse
{
  private ArrayList<Media> items;
  private boolean moreAvailable;
  private String nextMaxId;

  public static MediaFeedResponse fromJsonNode(CustomObjectMapper paramCustomObjectMapper, JsonNode paramJsonNode)
  {
    MediaFeedResponse localMediaFeedResponse = new MediaFeedResponse();
    localMediaFeedResponse.items = paramCustomObjectMapper.readArrayList(paramJsonNode.get("items"), Media.class);
    boolean bool;
    if ((paramJsonNode.get("more_available") != null) && (paramJsonNode.get("more_available").getBooleanValue()))
    {
      bool = true;
      localMediaFeedResponse.moreAvailable = bool;
      if (localMediaFeedResponse.moreAvailable)
        if (paramJsonNode.get("next_max_id") == null)
          break label88;
    }
    for (localMediaFeedResponse.nextMaxId = paramJsonNode.get("next_max_id").asText(); ; localMediaFeedResponse.nextMaxId = ((Media)localMediaFeedResponse.items.get(-1 + localMediaFeedResponse.items.size())).getId())
      label88: 
      do
      {
        return localMediaFeedResponse;
        bool = false;
        break;
      }
      while ((localMediaFeedResponse.items == null) || (localMediaFeedResponse.items.size() <= 0));
  }

  public ArrayList<Media> getItems()
  {
    return this.items;
  }

  public String getNextMaxId()
  {
    return this.nextMaxId;
  }

  public boolean isMoreAvailable()
  {
    return this.moreAvailable;
  }

  public void setIsMoreAvailable(boolean paramBoolean)
  {
    this.moreAvailable = paramBoolean;
  }

  public void setItems(ArrayList<Media> paramArrayList)
  {
    this.items = paramArrayList;
  }

  public void setNextMaxId(String paramString)
  {
    this.nextMaxId = paramString;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.model.MediaFeedResponse
 * JD-Core Version:    0.6.0
 */
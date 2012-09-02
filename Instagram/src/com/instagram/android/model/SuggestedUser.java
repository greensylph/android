package com.instagram.android.model;

import android.content.Context;
import com.instagram.android.service.CustomObjectMapper;
import com.instagram.util.StringUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;

public class SuggestedUser
{
  private String mCaption;
  private List<String> mThumbnails = new ArrayList();
  private User mUser;

  public static SuggestedUser fromJsonNode(CustomObjectMapper paramCustomObjectMapper, JsonNode paramJsonNode)
  {
    SuggestedUser localSuggestedUser = new SuggestedUser();
    localSuggestedUser.mCaption = paramJsonNode.get("caption").asText();
    localSuggestedUser.mUser = ((User)paramCustomObjectMapper.readValue(paramJsonNode.get("user"), User.class));
    localSuggestedUser.mThumbnails = paramCustomObjectMapper.readArrayList(paramJsonNode.get("thumbnail_urls"), String.class);
    return localSuggestedUser;
  }

  public static SuggestedUser fromJsonParser(Context paramContext, JsonParser paramJsonParser)
    throws JsonParseException, IOException
  {
    SuggestedUser localSuggestedUser = null;
    while (paramJsonParser.nextToken() != JsonToken.END_OBJECT)
    {
      String str1 = paramJsonParser.getCurrentName();
      if ((str1 != null) && (localSuggestedUser == null))
        localSuggestedUser = new SuggestedUser();
      if ("caption".equals(str1))
      {
        paramJsonParser.nextToken();
        localSuggestedUser.mCaption = StringUtil.stripEmoji(paramJsonParser.getText());
        continue;
      }
      if ("user".equals(str1))
      {
        paramJsonParser.nextToken();
        localSuggestedUser.mUser = User.fromJsonParser(paramContext, paramJsonParser);
        continue;
      }
      if (!"thumbnail_urls".equals(str1))
        continue;
      paramJsonParser.nextToken();
      while (paramJsonParser.nextToken() != JsonToken.END_ARRAY)
      {
        String str2 = paramJsonParser.getText();
        localSuggestedUser.mThumbnails.add(str2);
      }
    }
    return localSuggestedUser;
  }

  public boolean equals(Object paramObject)
  {
    int i = 1;
    if (this == paramObject);
    SuggestedUser localSuggestedUser;
    do
    {
      while (true)
      {
        return i;
        if ((paramObject != null) && (getClass() == paramObject.getClass()))
          break;
        i = 0;
      }
      localSuggestedUser = (SuggestedUser)paramObject;
      if (this.mUser == null)
        break;
    }
    while (this.mUser.equals(localSuggestedUser.mUser));
    while (true)
    {
      i = 0;
      break;
      if (localSuggestedUser.mUser == null)
        break;
    }
  }

  public String getCaption()
  {
    return this.mCaption;
  }

  public List<String> getThumbnails()
  {
    return this.mThumbnails;
  }

  public User getUser()
  {
    return this.mUser;
  }

  public int hashCode()
  {
    if (this.mUser != null);
    for (int i = this.mUser.hashCode(); ; i = 0)
      return i;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.model.SuggestedUser
 * JD-Core Version:    0.6.0
 */
package com.instagram.android.service;

import android.content.Context;
import com.instagram.android.location.Venue;
import com.instagram.android.model.Comment;
import com.instagram.android.model.Media;
import com.instagram.android.model.MediaFeedResponse;
import com.instagram.android.model.SuggestedUser;
import com.instagram.android.model.User;
import java.io.IOException;
import java.util.ArrayList;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;
import org.codehaus.jackson.map.type.CollectionType;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.TypeReference;

public class CustomObjectMapper extends ObjectMapper
{
  public static final String CUSTOM_OBJECT_MAPPER = "com.instagram.android.service.customObjectMapper";

  public CustomObjectMapper(Context paramContext)
  {
    configure(DeserializationConfig.Feature.AUTO_DETECT_FIELDS, false);
    configure(DeserializationConfig.Feature.AUTO_DETECT_SETTERS, false);
    configure(DeserializationConfig.Feature.USE_ANNOTATIONS, false);
    configure(DeserializationConfig.Feature.AUTO_DETECT_CREATORS, false);
    configure(DeserializationConfig.Feature.USE_GETTERS_AS_SETTERS, false);
    configure(DeserializationConfig.Feature.CAN_OVERRIDE_ACCESS_MODIFIERS, false);
    configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    SimpleModule localSimpleModule = new SimpleModule("MediaModule", new Version(1, 0, 0, null));
    localSimpleModule.addValueInstantiator(User.class, new UserDelegateMapper(this, User.class, UserStore.getInstance(paramContext)));
    localSimpleModule.addValueInstantiator(Media.class, new MediaDelegateMapper(this, Media.class, MediaStore.getInstance(paramContext)));
    localSimpleModule.addValueInstantiator(Venue.class, new FoursquareVenueDelegateMapper(this, Venue.class));
    localSimpleModule.addValueInstantiator(Comment.class, new CommentDelegateMapper(this, Comment.class));
    localSimpleModule.addValueInstantiator(SuggestedUser.class, new SuggestedUserDelegateMapper(this, SuggestedUser.class));
    localSimpleModule.addValueInstantiator(MediaFeedResponse.class, new MediaFeedResponseMapper(this, MediaFeedResponse.class));
    registerModule(localSimpleModule);
  }

  public static CustomObjectMapper getInstance(Context paramContext)
  {
    CustomObjectMapper localCustomObjectMapper = (CustomObjectMapper)paramContext.getSystemService("com.instagram.android.service.customObjectMapper");
    if (localCustomObjectMapper == null)
      localCustomObjectMapper = (CustomObjectMapper)paramContext.getApplicationContext().getSystemService("com.instagram.android.service.customObjectMapper");
    if (localCustomObjectMapper == null)
      throw new IllegalStateException("CustomObjectMapper not available");
    return localCustomObjectMapper;
  }

  public <T> ArrayList<T> readArrayList(JsonNode paramJsonNode, Class<T> paramClass)
  {
    CollectionType localCollectionType = getTypeFactory().constructCollectionType(ArrayList.class, paramClass);
    try
    {
      ArrayList localArrayList = (ArrayList)super.readValue(paramJsonNode, localCollectionType);
      return localArrayList;
    }
    catch (Exception localException)
    {
    }
    throw new RuntimeException(localException);
  }

  public <T> T readValue(JsonNode paramJsonNode, Class<T> paramClass)
  {
    try
    {
      Object localObject = super.readValue(paramJsonNode, paramClass);
      return localObject;
    }
    catch (IOException localIOException)
    {
    }
    throw new RuntimeException(localIOException);
  }

  public <T> T readValue(JsonNode paramJsonNode, TypeReference paramTypeReference)
  {
    try
    {
      Object localObject = super.readValue(paramJsonNode, paramTypeReference);
      return localObject;
    }
    catch (IOException localIOException)
    {
    }
    throw new RuntimeException(localIOException);
  }

  private class CommentDelegateMapper extends DelegateValueMapper
  {
    public CommentDelegateMapper(CustomObjectMapper paramClass, Class arg3)
    {
      super(paramClass);
    }

    public Object createUsingDelegate(Object paramObject)
      throws IOException
    {
      JsonNode localJsonNode = (JsonNode)paramObject;
      return Comment.fromJsonNode(getMapper(), localJsonNode);
    }
  }

  private class FoursquareVenueDelegateMapper extends DelegateValueMapper
  {
    public FoursquareVenueDelegateMapper(CustomObjectMapper paramClass, Class arg3)
    {
      super(paramClass);
    }

    public Object createUsingDelegate(Object paramObject)
      throws IOException
    {
      return Venue.fromFoursquareVenueNode((JsonNode)paramObject);
    }
  }

  private class MediaDelegateMapper extends DelegateValueMapper
  {
    private MediaStore mMediaStore;

    public MediaDelegateMapper(CustomObjectMapper paramClass, Class paramMediaStore, MediaStore arg4)
    {
      super(paramClass);
      Object localObject;
      this.mMediaStore = localObject;
    }

    public Object createUsingDelegate(Object paramObject)
      throws IOException
    {
      JsonNode localJsonNode = (JsonNode)paramObject;
      String str = localJsonNode.get("id").asText();
      Media localMedia1 = this.mMediaStore.get(str);
      Media localMedia2 = Media.fromJsonNode(getMapper(), localJsonNode, localMedia1);
      if (localMedia1 == null)
        this.mMediaStore.put(localMedia2.getId(), localMedia2);
      while (true)
      {
        return localMedia2;
        localMedia2.broadcastUpdatedMedia();
      }
    }
  }

  private class MediaFeedResponseMapper extends DelegateValueMapper
  {
    public MediaFeedResponseMapper(CustomObjectMapper paramClass, Class arg3)
    {
      super(paramClass);
    }

    public Object createUsingDelegate(Object paramObject)
      throws IOException
    {
      JsonNode localJsonNode = (JsonNode)paramObject;
      return MediaFeedResponse.fromJsonNode(getMapper(), localJsonNode);
    }
  }

  private class SuggestedUserDelegateMapper extends DelegateValueMapper
  {
    public SuggestedUserDelegateMapper(CustomObjectMapper paramClass, Class arg3)
    {
      super(paramClass);
    }

    public Object createUsingDelegate(Object paramObject)
      throws IOException
    {
      JsonNode localJsonNode = (JsonNode)paramObject;
      return SuggestedUser.fromJsonNode(getMapper(), localJsonNode);
    }
  }

  private class UserDelegateMapper extends DelegateValueMapper
  {
    private UserStore mUserStore;

    public UserDelegateMapper(CustomObjectMapper paramClass, Class paramUserStore, UserStore arg4)
    {
      super(paramClass);
      Object localObject;
      this.mUserStore = localObject;
    }

    public Object createUsingDelegate(Object paramObject)
      throws IOException
    {
      JsonNode localJsonNode = (JsonNode)paramObject;
      String str = localJsonNode.get("pk").asText();
      User localUser1 = (User)this.mUserStore.get(str);
      User localUser2 = User.fromJsonNode(localJsonNode, localUser1);
      if (localUser1 == null)
        this.mUserStore.put(localUser2.getId(), localUser2);
      while (true)
      {
        return localUser2;
        localUser2.broadcastUpdate();
      }
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.service.CustomObjectMapper
 * JD-Core Version:    0.6.0
 */
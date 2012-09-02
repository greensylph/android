package com.instagram.android.model;

import java.io.IOException;
import java.util.ArrayList;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.module.SimpleModule;

public class AutoCompleteUser
{
  private static final int UserSearchArrayFullNameConcatIndex = 3;
  private static final int UserSearchArrayFullNameIndex = 2;
  private static final int UserSearchArrayPkIndex = 0;
  private static final int UserSearchArrayUsernameIndex = 1;
  private static ObjectMapper sUserObjectMapper;
  private String mFullNameConcat;
  private String mFullname;
  private String mId;
  private String mUsername;
  private ArrayList<String> values = new ArrayList();

  private AutoCompleteUser()
  {
  }

  public AutoCompleteUser(User paramUser)
  {
    this.mId = paramUser.getId();
    this.mUsername = paramUser.getUsername();
    this.mFullname = paramUser.getFullName();
    this.mFullNameConcat = stripWhiteSpace(paramUser.getFullName());
  }

  public static AutoCompleteUser fromJsonParser(JsonParser paramJsonParser)
    throws IOException
  {
    AutoCompleteUser localAutoCompleteUser = new AutoCompleteUser();
    paramJsonParser.nextToken();
    if (paramJsonParser.getCurrentToken() != JsonToken.END_ARRAY)
    {
      localAutoCompleteUser.mId = paramJsonParser.getText();
      paramJsonParser.nextToken();
      if (paramJsonParser.getCurrentToken() != JsonToken.END_ARRAY)
      {
        localAutoCompleteUser.mUsername = paramJsonParser.getText();
        paramJsonParser.nextToken();
        if (paramJsonParser.getCurrentToken() != JsonToken.END_ARRAY)
        {
          if (paramJsonParser.getCurrentToken() == JsonToken.VALUE_STRING)
            localAutoCompleteUser.mFullname = paramJsonParser.getText();
          paramJsonParser.nextToken();
          if (paramJsonParser.getCurrentToken() != JsonToken.END_ARRAY)
          {
            if (paramJsonParser.getCurrentToken() == JsonToken.VALUE_STRING)
              localAutoCompleteUser.mFullNameConcat = paramJsonParser.getText();
            while (paramJsonParser.getCurrentToken() != JsonToken.END_ARRAY)
              paramJsonParser.nextToken();
          }
        }
      }
    }
    return localAutoCompleteUser;
  }

  public static AutoCompleteUser getUserFromSerializedData(String paramString)
    throws IOException
  {
    return (AutoCompleteUser)getUserObjectMapper().readValue(paramString, AutoCompleteUser.class);
  }

  private static ObjectMapper getUserObjectMapper()
  {
    if (sUserObjectMapper == null)
    {
      SimpleModule localSimpleModule = new SimpleModule("UserModule", Version.unknownVersion());
      localSimpleModule.addSerializer(AutoCompleteUser.class, new AutoCompleteUser.LocalJson.Serializer());
      localSimpleModule.addDeserializer(AutoCompleteUser.class, new AutoCompleteUser.LocalJson.Deserializer());
      sUserObjectMapper = new ObjectMapper().withModule(localSimpleModule);
    }
    return sUserObjectMapper;
  }

  private String stripWhiteSpace(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder(paramString.length());
    for (int i = 0; i < localStringBuilder.length(); i++)
    {
      if (Character.isWhitespace(localStringBuilder.charAt(i)))
        continue;
      localStringBuilder.append(localStringBuilder);
    }
    return localStringBuilder.toString();
  }

  public boolean equals(Object paramObject)
  {
    int i = 1;
    if (this == paramObject);
    AutoCompleteUser localAutoCompleteUser;
    do
    {
      while (true)
      {
        return i;
        if ((paramObject != null) && (getClass() == paramObject.getClass()))
          break;
        i = 0;
      }
      localAutoCompleteUser = (AutoCompleteUser)paramObject;
      if (this.mId == null)
        break;
    }
    while (this.mId.equals(localAutoCompleteUser.mId));
    while (true)
    {
      i = 0;
      break;
      if (localAutoCompleteUser.mId == null)
        break;
    }
  }

  public String getFullNameConcat()
  {
    return this.mFullNameConcat;
  }

  public String getFullname()
  {
    return this.mFullname;
  }

  public String getId()
  {
    return this.mId;
  }

  public String getUserSerialized()
    throws IOException
  {
    return getUserObjectMapper().writeValueAsString(this);
  }

  public String getUsername()
  {
    return this.mUsername;
  }

  public int hashCode()
  {
    if (this.mId != null);
    for (int i = this.mId.hashCode(); ; i = 0)
      return i;
  }

  public static class LocalJson
  {
    private static final String JSON_KEY_FULLNAME = "fullname";
    private static final String JSON_KEY_FULLNAME_CONCAT = "concat";
    private static final String JSON_KEY_ID = "id";
    private static final String JSON_KEY_USERNAME = "username";

    public static class Deserializer extends JsonDeserializer<AutoCompleteUser>
    {
      public AutoCompleteUser deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
        throws IOException, JsonProcessingException
      {
        ((ObjectMapper)paramJsonParser.getCodec());
        JsonNode localJsonNode = (JsonNode)paramJsonParser.readValueAs(JsonNode.class);
        AutoCompleteUser localAutoCompleteUser = new AutoCompleteUser(null);
        AutoCompleteUser.access$102(localAutoCompleteUser, localJsonNode.get("id").asText());
        AutoCompleteUser.access$202(localAutoCompleteUser, localJsonNode.get("username").asText());
        if (localJsonNode.has("fullname"))
          AutoCompleteUser.access$302(localAutoCompleteUser, localJsonNode.get("fullname").asText());
        if (localJsonNode.has("concat"))
          AutoCompleteUser.access$402(localAutoCompleteUser, localJsonNode.get("concat").asText());
        return localAutoCompleteUser;
      }
    }

    public static class Serializer extends JsonSerializer<AutoCompleteUser>
    {
      public void serialize(AutoCompleteUser paramAutoCompleteUser, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
        throws IOException, JsonProcessingException
      {
        paramJsonGenerator.writeStartObject();
        paramJsonGenerator.writeStringField("id", paramAutoCompleteUser.getId());
        paramJsonGenerator.writeStringField("username", paramAutoCompleteUser.getUsername());
        if (paramAutoCompleteUser.getFullname() != null)
          paramJsonGenerator.writeStringField("fullname", paramAutoCompleteUser.getFullname());
        if (paramAutoCompleteUser.getFullNameConcat() != null)
          paramJsonGenerator.writeStringField("concat", paramAutoCompleteUser.getFullNameConcat());
        paramJsonGenerator.writeEndObject();
      }
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.model.AutoCompleteUser
 * JD-Core Version:    0.6.0
 */
package com.instagram.api;

import ch.boye.httpclientandroidlib.cookie.Cookie;
import ch.boye.httpclientandroidlib.impl.cookie.BasicClientCookie;
import java.io.IOException;
import java.util.Date;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

public class JsonSerializedCookie extends BasicClientCookie
{
  public JsonSerializedCookie(String paramString1, String paramString2)
  {
    super(paramString1, paramString2);
  }

  public static class LocalJson
  {
    private static final String JSON_KEY_COMMENT = "comment";
    private static final String JSON_KEY_COMMENT_URL = "comment_url";
    private static final String JSON_KEY_DOMAIN = "domain";
    private static final String JSON_KEY_EXPIRY_DATE = "expiry_date";
    private static final String JSON_KEY_NAME = "name";
    private static final String JSON_KEY_PATH = "path";
    private static final String JSON_KEY_PORTS = "ports";
    private static final String JSON_KEY_VALUE = "value";
    private static final String JSON_KEY_VERSION = "version";

    public static class Deserializer extends JsonDeserializer<Cookie>
    {
      public Cookie deserialize(JsonParser paramJsonParser, DeserializationContext paramDeserializationContext)
        throws IOException, JsonProcessingException
      {
        JsonNode localJsonNode = (JsonNode)paramJsonParser.readValueAs(JsonNode.class);
        JsonSerializedCookie localJsonSerializedCookie = new JsonSerializedCookie(localJsonNode.get("name").asText(), localJsonNode.get("value").asText());
        localJsonSerializedCookie.setComment(localJsonNode.get("comment").asText());
        localJsonSerializedCookie.setDomain(localJsonNode.get("domain").asText());
        localJsonSerializedCookie.setExpiryDate(new Date(localJsonNode.get("expiry_date").asLong()));
        localJsonSerializedCookie.setPath(localJsonNode.get("name").asText());
        localJsonSerializedCookie.setVersion(localJsonNode.get("version").asInt());
        return localJsonSerializedCookie;
      }
    }

    public static class Serializer extends JsonSerializer<Cookie>
    {
      public void serialize(Cookie paramCookie, JsonGenerator paramJsonGenerator, SerializerProvider paramSerializerProvider)
        throws IOException, JsonProcessingException
      {
        paramJsonGenerator.writeStartObject();
        paramJsonGenerator.writeStringField("comment", paramCookie.getComment());
        paramJsonGenerator.writeStringField("domain", paramCookie.getDomain());
        paramJsonGenerator.writeNumberField("expiry_date", paramCookie.getExpiryDate().getTime());
        paramJsonGenerator.writeStringField("name", paramCookie.getName());
        paramJsonGenerator.writeStringField("path", paramCookie.getPath());
        paramJsonGenerator.writeStringField("value", paramCookie.getValue());
        paramJsonGenerator.writeNumberField("version", paramCookie.getVersion());
        paramJsonGenerator.writeEndObject();
      }
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.JsonSerializedCookie
 * JD-Core Version:    0.6.0
 */
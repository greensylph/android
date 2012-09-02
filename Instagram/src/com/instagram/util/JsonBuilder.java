package com.instagram.util;

import java.io.IOException;
import java.io.StringWriter;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;

public class JsonBuilder
{
  JsonFactory mFactory = new JsonFactory();
  JsonGenerator mGenerator;
  StringWriter mWriter = new StringWriter();

  public JsonBuilder()
  {
    try
    {
      this.mGenerator = this.mFactory.createJsonGenerator(this.mWriter);
      this.mGenerator.writeStartObject();
      label48: return;
    }
    catch (IOException localIOException)
    {
      break label48;
    }
  }

  public JsonBuilder put(String paramString1, String paramString2)
  {
    try
    {
      this.mGenerator.writeStringField(paramString1, StringUtil.get(paramString2, ""));
      label14: return this;
    }
    catch (IOException localIOException)
    {
      break label14;
    }
  }

  public String toString()
  {
    try
    {
      this.mGenerator.writeEndObject();
      this.mGenerator.close();
      label14: return this.mWriter.toString();
    }
    catch (IOException localIOException)
    {
      break label14;
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.util.JsonBuilder
 * JD-Core Version:    0.6.0
 */
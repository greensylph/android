package com.instagram.android.service;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.deser.ValueInstantiator;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.JavaType;

public abstract class DelegateValueMapper extends ValueInstantiator
{
  protected Class mClazz;
  protected CustomObjectMapper mMapper;

  public DelegateValueMapper(Class paramClass, CustomObjectMapper paramCustomObjectMapper)
  {
    this.mClazz = paramClass;
    this.mMapper = paramCustomObjectMapper;
  }

  public boolean canCreateUsingDelegate()
  {
    return true;
  }

  public JavaType getDelegateType()
  {
    return TypeFactory.defaultInstance().constructType(JsonNode.class);
  }

  public CustomObjectMapper getMapper()
  {
    return this.mMapper;
  }

  public String getValueTypeDesc()
  {
    return this.mClazz.getName();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.service.DelegateValueMapper
 * JD-Core Version:    0.6.0
 */
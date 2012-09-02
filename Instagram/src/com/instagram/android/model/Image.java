package com.instagram.android.model;

import com.instagram.util.JSONUtil;
import org.json.JSONObject;

public class Image
{
  public static final int LOW_RESOLUTION = 6;
  public static final int STANDARD_RESOLUTION = 7;
  public static final int THUMBNAIL = 5;
  private int height;
  private int type;
  private String url;
  private int width;

  public static Image fromJSON(JSONObject paramJSONObject)
  {
    Image localImage;
    if (paramJSONObject == null)
      localImage = null;
    while (true)
    {
      return localImage;
      localImage = new Image();
      localImage.url = JSONUtil.optJSONString(paramJSONObject, "url");
      localImage.width = JSONUtil.optInt(paramJSONObject, "width").intValue();
      localImage.height = JSONUtil.optInt(paramJSONObject, "height").intValue();
      localImage.type = JSONUtil.optInt(paramJSONObject, "type").intValue();
    }
  }

  public boolean equals(Object paramObject)
  {
    int i = 1;
    if (this == paramObject);
    Image localImage;
    do
    {
      while (true)
      {
        return i;
        if ((paramObject instanceof Image))
          break;
        i = 0;
      }
      localImage = (Image)paramObject;
      if (this.url == null)
        break;
    }
    while (this.url.equals(localImage.url));
    while (true)
    {
      i = 0;
      break;
      if (localImage.url == null)
        break;
    }
  }

  public int getHeight()
  {
    return this.height;
  }

  public int getType()
  {
    return this.type;
  }

  public String getUrl()
  {
    return this.url;
  }

  public int getWidth()
  {
    return this.width;
  }

  public int hashCode()
  {
    if (this.url != null);
    for (int i = this.url.hashCode(); ; i = 0)
      return i;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.model.Image
 * JD-Core Version:    0.6.0
 */
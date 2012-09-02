package com.instagram.android.service;

import android.content.Context;
import com.instagram.android.model.Media;
import java.util.concurrent.ConcurrentHashMap;

public class MediaStore extends ConcurrentHashMap<String, Media>
{
  public static final String MEDIASTORE_SERVICE = "com.instagram.android.service.mediastore";

  public static MediaStore getInstance(Context paramContext)
  {
    MediaStore localMediaStore = (MediaStore)paramContext.getSystemService("com.instagram.android.service.mediastore");
    if (localMediaStore == null)
      localMediaStore = (MediaStore)paramContext.getApplicationContext().getSystemService("com.instagram.android.service.mediastore");
    if (localMediaStore == null)
      throw new IllegalStateException("MediaStore not available");
    return localMediaStore;
  }

  public Media get(Object paramObject)
  {
    if (paramObject == null);
    for (Media localMedia = null; ; localMedia = (Media)super.get(paramObject))
      return localMedia;
  }

  public Media update(String paramString, Media paramMedia)
  {
    Media localMedia = get(paramMedia.getId());
    if (localMedia != null)
      localMedia.updateFields(paramMedia);
    while (true)
    {
      return localMedia;
      put(paramMedia.getId(), paramMedia);
      localMedia = paramMedia;
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.service.MediaStore
 * JD-Core Version:    0.6.0
 */
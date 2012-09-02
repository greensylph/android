package com.instagram.android;

import com.instagram.android.imagecache.IgBitmapCache;
import com.instagram.android.service.AppContext;

class InstagramApplication$2
  implements Runnable
{
  public void run()
  {
    IgBitmapCache.getInstance(AppContext.getContext());
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.InstagramApplication.2
 * JD-Core Version:    0.6.0
 */
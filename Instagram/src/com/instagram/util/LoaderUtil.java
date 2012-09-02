package com.instagram.util;

public class LoaderUtil
{
  static int loaderId = 100000000;

  public static int getUniqueId()
  {
    int i = loaderId;
    loaderId = i + 1;
    return i;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.util.LoaderUtil
 * JD-Core Version:    0.6.0
 */
package com.instagram.android.gl;

public class NativeFilter
{
  private int mId;
  private String mName;
  private String mResInfix;

  public NativeFilter(int paramInt, String paramString1, String paramString2)
  {
    this.mId = paramInt;
    this.mName = paramString1;
    this.mResInfix = paramString2;
  }

  public int getId()
  {
    return this.mId;
  }

  public String getName()
  {
    return this.mName;
  }

  public String getResInfix()
  {
    return this.mResInfix;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.gl.NativeFilter
 * JD-Core Version:    0.6.0
 */
package com.instagram.android.model;

public class SimpleMenuItem
{
  boolean mDisclosure;
  CharSequence mProvidedString;
  int mStringResId;

  public SimpleMenuItem(int paramInt)
  {
    this.mStringResId = paramInt;
    this.mDisclosure = true;
  }

  public SimpleMenuItem(int paramInt, boolean paramBoolean)
  {
    this.mStringResId = paramInt;
    this.mDisclosure = paramBoolean;
  }

  public SimpleMenuItem(CharSequence paramCharSequence, boolean paramBoolean)
  {
    this.mProvidedString = paramCharSequence;
    this.mDisclosure = paramBoolean;
  }

  public CharSequence getProvidedString()
  {
    return this.mProvidedString;
  }

  public int getStringResId()
  {
    return this.mStringResId;
  }

  public boolean isDisclosure()
  {
    return this.mDisclosure;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.model.SimpleMenuItem
 * JD-Core Version:    0.6.0
 */
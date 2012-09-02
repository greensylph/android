package com.instagram.api;

public abstract class AbstractStreamingApiCallbacks<T> extends AbstractApiCallbacks<T>
{
  public void onRequestFinished()
  {
  }

  public void onRequestStart()
  {
  }

  protected abstract void onSuccess(T paramT);
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.AbstractStreamingApiCallbacks
 * JD-Core Version:    0.6.0
 */
package com.instagram.api;

public abstract class AbstractApiCallbacks<T>
{
  protected void onRequestFail(ApiResponse<T> paramApiResponse)
  {
  }

  public void onRequestFinished()
  {
  }

  public void onRequestStart()
  {
  }

  protected abstract void onSuccess(T paramT);
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.AbstractApiCallbacks
 * JD-Core Version:    0.6.0
 */
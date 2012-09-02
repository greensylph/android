package com.instagram.android.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public class AsyncTaskDataLoader<T> extends AsyncTaskLoader<T>
{
  public AsyncTaskDataLoader(Context paramContext)
  {
    super(paramContext);
  }

  public void deliverResult(T paramT)
  {
    if (isReset());
    while (true)
    {
      return;
      super.deliverResult(paramT);
    }
  }

  public T loadInBackground()
  {
    return null;
  }

  protected void onReset()
  {
    super.onReset();
    onStopLoading();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.loader.AsyncTaskDataLoader
 * JD-Core Version:    0.6.0
 */
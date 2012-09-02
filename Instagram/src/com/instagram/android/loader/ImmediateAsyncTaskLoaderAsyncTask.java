package com.instagram.android.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public abstract class ImmediateAsyncTaskLoaderAsyncTask<D> extends AsyncTaskLoader<D>
{
  public ImmediateAsyncTaskLoaderAsyncTask(Context paramContext)
  {
    super(paramContext);
  }

  public void deliverResult(D paramD)
  {
    super.deliverResult(paramD);
  }

  protected void onStartLoading()
  {
    forceLoad();
    super.onStartLoading();
  }

  protected void onStopLoading()
  {
    cancelLoad();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.loader.ImmediateAsyncTaskLoaderAsyncTask
 * JD-Core Version:    0.6.0
 */
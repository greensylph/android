package com.instagram.android.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import ch.boye.httpclientandroidlib.HttpResponse;
import ch.boye.httpclientandroidlib.StatusLine;
import com.instagram.android.Log;
import com.instagram.android.model.Media;
import com.instagram.api.ApiHttpClient;
import com.instagram.api.request.ApiUrlHelper;

public class MediaService extends IntentService
{
  private static final String INTENT_EXTRA_PENDING_MEDIA_ID = "com.instagram.android.MediaService.INTENT_EXTRA_PENDING_MEDIA_ID";
  private static final String INTENT_EXTRA_PERFORM_ACTION = "com.instagram.android.MediaService.INTENT_EXTRA_PERFORM_ACTION";
  private static final int PERFORM_ACTION_DELETE = 0;
  private static final String TAG = "MediaService";

  public MediaService()
  {
    super("MediaService");
  }

  public static void deleteMedia(Context paramContext, Media paramMedia)
  {
    paramMedia.setDeletedStatus(1);
    paramMedia.broadcastUpdatedMedia(paramContext, true);
    startService(paramContext, paramMedia, 0);
  }

  private void deleteMedia(Media paramMedia)
  {
    String str = ApiUrlHelper.expandPath("media/" + paramMedia.getId() + "/delete");
    AsyncHttpRequestTask localAsyncHttpRequestTask = new AsyncHttpRequestTask(new AsyncDeleteMediaHttpCallbacks(paramMedia));
    String[] arrayOfString = new String[1];
    arrayOfString[0] = str;
    localAsyncHttpRequestTask.execute(arrayOfString);
  }

  private int getAction(Intent paramIntent)
  {
    return paramIntent.getIntExtra("com.instagram.android.MediaService.INTENT_EXTRA_PERFORM_ACTION", -1);
  }

  private Media getMedia(Intent paramIntent)
  {
    String str = paramIntent.getStringExtra("com.instagram.android.MediaService.INTENT_EXTRA_PENDING_MEDIA_ID");
    if (str == null);
    for (Media localMedia = null; ; localMedia = MediaStore.getInstance(this).get(str))
      return localMedia;
  }

  public static void startService(Context paramContext, Media paramMedia, int paramInt)
  {
    Context localContext = paramContext.getApplicationContext();
    Intent localIntent = new Intent(localContext, MediaService.class);
    localIntent.putExtra("com.instagram.android.MediaService.INTENT_EXTRA_PERFORM_ACTION", paramInt);
    localIntent.putExtra("com.instagram.android.MediaService.INTENT_EXTRA_PENDING_MEDIA_ID", paramMedia.getId());
    localContext.startService(localIntent);
  }

  protected void onHandleIntent(Intent paramIntent)
  {
    int i = getAction(paramIntent);
    Media localMedia = getMedia(paramIntent);
    if (localMedia == null)
      Log.e("MediaService", "Media is null, cannot continue");
    while (true)
    {
      return;
      switch (i)
      {
      default:
        throw new UnsupportedOperationException("Action not handled or invalid: " + i);
      case 0:
      }
      deleteMedia(localMedia);
    }
  }

  private class AsyncDeleteMediaHttpCallbacks
    implements MediaService.AsyncHttpRequestCallbacks<Boolean>
  {
    private Media mMedia;

    public AsyncDeleteMediaHttpCallbacks(Media arg2)
    {
      Object localObject;
      this.mMedia = localObject;
    }

    public Boolean processResponseInBackground(HttpResponse paramHttpResponse)
    {
      return Boolean.valueOf(true);
    }

    public void requestFinished(Boolean paramBoolean)
    {
      if (((paramBoolean == null) || (!paramBoolean.booleanValue())) && (this.mMedia.getDeletedStatus() == 1))
      {
        this.mMedia.setDeletedStatus(0);
        this.mMedia.broadcastUpdatedMedia(MediaService.this.getBaseContext(), true);
      }
    }
  }

  static abstract interface AsyncHttpRequestCallbacks<T>
  {
    public abstract T processResponseInBackground(HttpResponse paramHttpResponse);

    public abstract void requestFinished(T paramT);
  }

  private class AsyncHttpRequestTask<T> extends AsyncTask<String, Void, T>
  {
    private MediaService.AsyncHttpRequestCallbacks<T> mCallbacks;

    public AsyncHttpRequestTask()
    {
      Object localObject;
      this.mCallbacks = localObject;
    }

    protected T doInBackground(String[] paramArrayOfString)
    {
      Object localObject = null;
      ApiHttpClient localApiHttpClient = ApiHttpClient.getInstance(MediaService.this);
      try
      {
        localHttpResponse = localApiHttpClient.get(paramArrayOfString[0]);
        if ((localHttpResponse == null) || (localHttpResponse.getStatusLine() == null) || (localHttpResponse.getStatusLine().getStatusCode() != 200))
        {
          Log.e("MediaService", "Bad HTTP response");
          return localObject;
        }
      }
      catch (Exception localException)
      {
        while (true)
        {
          HttpResponse localHttpResponse;
          continue;
          localObject = this.mCallbacks.processResponseInBackground(localHttpResponse);
        }
      }
    }

    protected void onPostExecute(T paramT)
    {
      this.mCallbacks.requestFinished(paramT);
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.service.MediaService
 * JD-Core Version:    0.6.0
 */
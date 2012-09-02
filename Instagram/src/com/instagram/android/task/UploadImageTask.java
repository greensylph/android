package com.instagram.android.task;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import com.instagram.android.Log;
import com.instagram.android.gl.NativeBridge;
import com.instagram.android.media.PendingMedia;
import com.instagram.android.media.PendingMediaFactory;
import com.instagram.android.service.PendingMediaService;

public class UploadImageTask extends AsyncTask<Bitmap, Void, PendingMedia>
{
  private static final String TAG = "UploadImageTask";
  private final Context mContext;
  private final int mMediaSource;

  public UploadImageTask(Context paramContext, int paramInt)
  {
    this.mContext = paramContext.getApplicationContext();
    this.mMediaSource = paramInt;
  }

  protected PendingMedia doInBackground(Bitmap[] paramArrayOfBitmap)
  {
    Log.d("UploadImageTask", "Creating pending media in background");
    Bitmap localBitmap = paramArrayOfBitmap[0];
    PendingMedia localPendingMedia = PendingMediaFactory.make(this.mContext, localBitmap);
    localPendingMedia.setFilterType(NativeBridge.getCurrentFilter());
    localPendingMedia.setSourceType(this.mMediaSource);
    PendingMediaService.upload(this.mContext, localPendingMedia);
    Log.d("UploadImageTask", "Finished creating pending media");
    return localPendingMedia;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.task.UploadImageTask
 * JD-Core Version:    0.6.0
 */
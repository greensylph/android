package com.instagram.android.fragment;

import android.content.ContentResolver;
import com.instagram.android.Log;
import com.instagram.android.task.SaveImageTask;

class FilterFragment$OffscreenRenderCallbacks$1 extends SaveImageTask
{
  protected void onPostExecute(Void paramVoid)
  {
    Log.d("FilterActivity", "Calling renderBitmapForUpload");
    FilterFragment.access$000(this.this$1.this$0);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.FilterFragment.OffscreenRenderCallbacks.1
 * JD-Core Version:    0.6.0
 */
package com.instagram.android.fragment;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import com.instagram.android.activity.MetadataActivity;
import com.instagram.android.media.PendingMedia;
import com.instagram.android.task.UploadImageTask;
import com.instagram.android.widget.IgProgressDialog;

class FilterFragment$OffscreenRenderCallbacks$2 extends UploadImageTask
{
  protected void onPostExecute(PendingMedia paramPendingMedia)
  {
    View localView = this.this$1.this$0.getView().findViewById(2131492893);
    if (localView != null)
      localView.setEnabled(true);
    if (FilterFragment.access$100(this.this$1.this$0) != null)
    {
      if (FilterFragment.access$100(this.this$1.this$0).isShowing())
        FilterFragment.access$100(this.this$1.this$0).dismiss();
      FilterFragment.access$102(this.this$1.this$0, null);
    }
    Intent localIntent = new Intent(this.this$1.this$0.getActivity(), MetadataActivity.class);
    localIntent.putExtra("com.instagram.android.MetadataActivity.INTENT_EXTRA_PENDING_MEDIA_ID", paramPendingMedia.getDeviceTimestamp());
    this.this$1.this$0.startActivityForResult(localIntent, 2);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.FilterFragment.OffscreenRenderCallbacks.2
 * JD-Core Version:    0.6.0
 */
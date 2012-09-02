package com.instagram.android.widget;

import android.content.Context;
import android.content.Intent;
import com.instagram.android.service.AppContext;

class MediaOptionsButton$PermalinkRequestCallbacks$1
  implements Runnable
{
  public void run()
  {
    if (!MediaOptionsButton.access$500(this.this$1.this$0).getWasCanceled())
    {
      MediaOptionsButton.access$500(this.this$1.this$0).dismiss();
      Intent localIntent = new Intent("android.intent.action.SEND");
      localIntent.setType("text/plain");
      localIntent.putExtra("android.intent.extra.TEXT", this.val$permalink);
      MediaOptionsButton.access$400(this.this$1.this$0).startActivity(Intent.createChooser(localIntent, AppContext.getContext().getString(2131230942)));
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.widget.MediaOptionsButton.PermalinkRequestCallbacks.1
 * JD-Core Version:    0.6.0
 */
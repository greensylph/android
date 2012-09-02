package com.instagram.android.widget;

import android.app.Dialog;

class MediaOptionsButton$PermalinkRequestCallbacks$2
  implements Runnable
{
  public void run()
  {
    MediaOptionsButton.access$500(this.this$1.this$0).dismiss();
    new IgDialogBuilder(MediaOptionsButton.access$400(this.this$1.this$0)).setMessage(2131231022).setPositiveButton(2131230955, new MediaOptionsButton.PermalinkRequestCallbacks.2.1(this)).create().show();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.widget.MediaOptionsButton.PermalinkRequestCallbacks.2
 * JD-Core Version:    0.6.0
 */
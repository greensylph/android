package com.instagram.android.widget;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import com.instagram.android.service.MediaService;

class MediaOptionsButton$MenuClickListener$2
  implements DialogInterface.OnClickListener
{
  public void onClick(DialogInterface paramDialogInterface, int paramInt)
  {
    MediaService.deleteMedia(this.this$1.this$0.getContext(), MediaOptionsButton.access$300(this.this$1.this$0));
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.widget.MediaOptionsButton.MenuClickListener.2
 * JD-Core Version:    0.6.0
 */
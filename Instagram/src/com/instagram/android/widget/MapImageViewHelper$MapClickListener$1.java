package com.instagram.android.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;

class MapImageViewHelper$MapClickListener$1
  implements DialogInterface.OnClickListener
{
  public void onClick(DialogInterface paramDialogInterface, int paramInt)
  {
    switch (paramInt)
    {
    default:
    case -1:
    case -2:
    }
    while (true)
    {
      return;
      Object[] arrayOfObject = new Object[4];
      arrayOfObject[0] = MapImageViewHelper.MapClickListener.access$000(this.this$0);
      arrayOfObject[1] = MapImageViewHelper.MapClickListener.access$100(this.this$0);
      arrayOfObject[2] = MapImageViewHelper.MapClickListener.access$200(this.this$0);
      arrayOfObject[3] = MapImageViewHelper.MapClickListener.access$300(this.this$0);
      Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse(String.format("geo:%s,%s?q=%s&z=%s", arrayOfObject)));
      MapImageViewHelper.MapClickListener.access$400(this.this$0).startActivity(localIntent);
      paramDialogInterface.dismiss();
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.widget.MapImageViewHelper.MapClickListener.1
 * JD-Core Version:    0.6.0
 */
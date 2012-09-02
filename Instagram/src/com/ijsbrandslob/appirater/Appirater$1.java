package com.ijsbrandslob.appirater;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;

class Appirater$1
  implements View.OnClickListener
{
  public void onClick(View paramView)
  {
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Appirater.access$000(this.this$0).getPackageName();
    Uri localUri = Uri.parse(String.format("market://details?id=%s", arrayOfObject));
    Intent localIntent = new Intent("android.intent.action.VIEW").setData(localUri);
    Appirater.access$000(this.this$0).startActivity(localIntent);
    Appirater.access$102(this.this$0, true);
    Appirater.access$200(this.this$0);
    this.val$rateDialog.dismiss();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.ijsbrandslob.appirater.Appirater.1
 * JD-Core Version:    0.6.0
 */
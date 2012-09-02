package com.instagram.android.widget;

import android.app.Dialog;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

class IgDialogBuilder$2
  implements AdapterView.OnItemClickListener
{
  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    if (this.val$listener != null)
      this.val$listener.onClick(IgDialogBuilder.access$000(this.this$0), paramInt);
    IgDialogBuilder.access$000(this.this$0).dismiss();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.widget.IgDialogBuilder.2
 * JD-Core Version:    0.6.0
 */
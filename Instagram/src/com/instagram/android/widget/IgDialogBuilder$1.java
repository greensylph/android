package com.instagram.android.widget;

import android.app.Dialog;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.view.View.OnClickListener;

class IgDialogBuilder$1
  implements View.OnClickListener
{
  public void onClick(View paramView)
  {
    if (this.val$onClickListener != null)
      this.val$onClickListener.onClick(IgDialogBuilder.access$000(this.this$0), this.val$buttonPositive);
    IgDialogBuilder.access$000(this.this$0).dismiss();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.widget.IgDialogBuilder.1
 * JD-Core Version:    0.6.0
 */
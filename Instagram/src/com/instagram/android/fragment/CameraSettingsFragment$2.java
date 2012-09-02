package com.instagram.android.fragment;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.CheckBox;

class CameraSettingsFragment$2
  implements DialogInterface.OnClickListener
{
  public void onClick(DialogInterface paramDialogInterface, int paramInt)
  {
    ((CheckBox)this.this$0.getView().findViewById(2131492937)).setChecked(false);
    paramDialogInterface.dismiss();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.CameraSettingsFragment.2
 * JD-Core Version:    0.6.0
 */
package com.instagram.android.fragment;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.ToggleButton;

class PhotoPrivacyFragment$1$1
  implements DialogInterface.OnClickListener
{
  public void onClick(DialogInterface paramDialogInterface, int paramInt)
  {
    paramDialogInterface.dismiss();
    ToggleButton localToggleButton = this.this$1.val$toggleButton;
    if (!this.val$willBePrivate);
    for (boolean bool = true; ; bool = false)
    {
      localToggleButton.setChecked(bool);
      return;
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.PhotoPrivacyFragment.1.1
 * JD-Core Version:    0.6.0
 */
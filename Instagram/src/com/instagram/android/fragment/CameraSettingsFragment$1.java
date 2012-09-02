package com.instagram.android.fragment;

import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.instagram.android.Preferences;

class CameraSettingsFragment$1
  implements CompoundButton.OnCheckedChangeListener
{
  public void onCheckedChanged(CompoundButton paramCompoundButton, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      Preferences.getInstance(this.this$0.getActivity()).setHasAdvancedCameraEnabled(true);
      CameraSettingsFragment.access$000(this.this$0);
    }
    while (true)
    {
      return;
      Preferences.getInstance(this.this$0.getActivity()).setHasAdvancedCameraEnabled(false);
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.CameraSettingsFragment.1
 * JD-Core Version:    0.6.0
 */
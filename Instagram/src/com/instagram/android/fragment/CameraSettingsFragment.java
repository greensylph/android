package com.instagram.android.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import com.instagram.android.Preferences;
import com.instagram.android.widget.IgDialogBuilder;

public class CameraSettingsFragment extends Fragment
  implements ActionBarConfigurer.ActionBarConfigurerFactory
{
  private void showWarningDialog()
  {
    new IgDialogBuilder(getActivity()).setMessage(2131231058).setPositiveButton(2131231003, new CameraSettingsFragment.3(this)).setNegativeButton(2131230921, new CameraSettingsFragment.2(this)).create().show();
  }

  public ActionBarConfigurer getActionBarConfigurer()
  {
    return new CameraSettingsFragment.4(this);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(2130903067, paramViewGroup, false);
    CheckBox localCheckBox = (CheckBox)localView.findViewById(2131492937);
    localCheckBox.setChecked(Preferences.getInstance(getActivity()).getHasAdvancedCameraEnabled());
    localCheckBox.setOnCheckedChangeListener(new CameraSettingsFragment.1(this));
    return localView;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.CameraSettingsFragment
 * JD-Core Version:    0.6.0
 */
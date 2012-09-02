package com.instagram.android.fragment;

import android.app.Dialog;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import com.instagram.android.widget.IgProgressDialog;

public class ProgressDialogFragment extends DialogFragment
{
  private static final String ARGUMENT_PROGRESS_MESSAGE = "com.instagram.android.fragment.ProgressDialogFragment.ARGUMENT_PROGRESS_MESSAGE";
  private final DialogInterface.OnKeyListener mBackButtonListener = new ProgressDialogFragment.1(this);

  public static ProgressDialogFragment newInstance()
  {
    return new ProgressDialogFragment();
  }

  protected String getProgressMessage()
  {
    return getString(2131230889);
  }

  public Dialog onCreateDialog(Bundle paramBundle)
  {
    IgProgressDialog localIgProgressDialog = new IgProgressDialog(getActivity());
    localIgProgressDialog.setMessage(getProgressMessage());
    localIgProgressDialog.setCancelable(false);
    localIgProgressDialog.setOnKeyListener(this.mBackButtonListener);
    return localIgProgressDialog;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.ProgressDialogFragment
 * JD-Core Version:    0.6.0
 */
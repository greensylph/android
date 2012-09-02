package com.instagram.android.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import com.instagram.android.widget.IgDialogBuilder;

public class AlertDialogFragment extends DialogFragment
{
  public static AlertDialogFragment newInstance(String paramString)
  {
    return newInstance(null, paramString);
  }

  public static AlertDialogFragment newInstance(String paramString1, String paramString2)
  {
    AlertDialogFragment localAlertDialogFragment = new AlertDialogFragment();
    Bundle localBundle = new Bundle();
    localBundle.putString("message", paramString2);
    localBundle.putString("title", paramString1);
    localAlertDialogFragment.setArguments(localBundle);
    return localAlertDialogFragment;
  }

  public Dialog onCreateDialog(Bundle paramBundle)
  {
    Bundle localBundle = getArguments();
    String str1 = localBundle.getString("message");
    String str2 = null;
    if (localBundle.containsKey("title"))
      str2 = localBundle.getString("title");
    IgDialogBuilder localIgDialogBuilder = new IgDialogBuilder(getActivity()).setMessage(str1);
    if (str2 != null)
      localIgDialogBuilder.setTitle(str2);
    return localIgDialogBuilder.setPositiveButton(2131230955, new AlertDialogFragment.1(this)).create();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.AlertDialogFragment
 * JD-Core Version:    0.6.0
 */
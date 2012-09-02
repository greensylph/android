package com.instagram.android.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.widget.TextView;

public class IgProgressDialog extends Dialog
{
  public IgProgressDialog(Context paramContext)
  {
    super(paramContext, 2131623942);
    setContentView(2130903097);
    setCancelable(false);
  }

  private void setMessage(CharSequence paramCharSequence)
  {
    TextView localTextView = (TextView)findViewById(2131492913);
    localTextView.setVisibility(0);
    localTextView.setText(paramCharSequence);
  }

  public static IgProgressDialog show(Context paramContext, CharSequence paramCharSequence1, CharSequence paramCharSequence2)
  {
    return show(paramContext, paramCharSequence1, paramCharSequence2, false);
  }

  public static IgProgressDialog show(Context paramContext, CharSequence paramCharSequence1, CharSequence paramCharSequence2, boolean paramBoolean)
  {
    return show(paramContext, paramCharSequence1, paramCharSequence2, paramBoolean, false, null);
  }

  public static IgProgressDialog show(Context paramContext, CharSequence paramCharSequence1, CharSequence paramCharSequence2, boolean paramBoolean1, boolean paramBoolean2)
  {
    return show(paramContext, paramCharSequence1, paramCharSequence2, paramBoolean1, paramBoolean2, null);
  }

  public static IgProgressDialog show(Context paramContext, CharSequence paramCharSequence1, CharSequence paramCharSequence2, boolean paramBoolean1, boolean paramBoolean2, DialogInterface.OnCancelListener paramOnCancelListener)
  {
    IgProgressDialog localIgProgressDialog = new IgProgressDialog(paramContext);
    localIgProgressDialog.setMessage(paramCharSequence2);
    localIgProgressDialog.setCancelable(paramBoolean2);
    localIgProgressDialog.setOnCancelListener(paramOnCancelListener);
    localIgProgressDialog.show();
    return localIgProgressDialog;
  }

  public void setMessage(String paramString)
  {
    TextView localTextView = (TextView)findViewById(2131492913);
    localTextView.setVisibility(0);
    localTextView.setText(paramString);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.widget.IgProgressDialog
 * JD-Core Version:    0.6.0
 */
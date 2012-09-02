package com.instagram.android.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.instagram.android.adapter.SimplePreferenceAdapter;

public class IgDialogBuilder
{
  private Context mContext;
  private final Dialog mD;

  public IgDialogBuilder(Context paramContext)
  {
    this.mContext = paramContext;
    this.mD = new Dialog(paramContext, 2131623942);
    this.mD.setContentView(2130903058);
    this.mD.findViewById(2131492981).setVisibility(8);
  }

  private void bindButton(int paramInt1, DialogInterface.OnClickListener paramOnClickListener, int paramInt2, int paramInt3)
  {
    View localView = this.mD.findViewById(paramInt2);
    ((Button)localView).setText(paramInt1);
    localView.setOnClickListener(new IgDialogBuilder.1(this, paramOnClickListener, paramInt3));
    localView.setVisibility(0);
  }

  public Dialog create()
  {
    return this.mD;
  }

  public IgDialogBuilder setCancelable(boolean paramBoolean)
  {
    this.mD.setCancelable(paramBoolean);
    return this;
  }

  public IgDialogBuilder setItems(CharSequence[] paramArrayOfCharSequence, DialogInterface.OnClickListener paramOnClickListener)
  {
    SimplePreferenceAdapter localSimplePreferenceAdapter = new SimplePreferenceAdapter(this.mContext);
    localSimplePreferenceAdapter.showDialogView(true);
    ListView localListView = (ListView)this.mD.findViewById(16908298);
    localListView.setOnItemClickListener(new IgDialogBuilder.2(this, paramOnClickListener));
    localSimplePreferenceAdapter.addDialogMenuItems(paramArrayOfCharSequence);
    localListView.setAdapter(localSimplePreferenceAdapter);
    this.mD.findViewById(2131492981).setVisibility(0);
    return this;
  }

  public IgDialogBuilder setMessage(int paramInt)
  {
    View localView = this.mD.findViewById(2131492913);
    ((TextView)localView).setText(paramInt);
    localView.setVisibility(0);
    return this;
  }

  public IgDialogBuilder setMessage(String paramString)
  {
    View localView = this.mD.findViewById(2131492913);
    ((TextView)localView).setText(paramString);
    localView.setVisibility(0);
    return this;
  }

  public IgDialogBuilder setNegativeButton(int paramInt, DialogInterface.OnClickListener paramOnClickListener)
  {
    bindButton(paramInt, paramOnClickListener, 2131492917, -2);
    return this;
  }

  public IgDialogBuilder setNeutralButton(int paramInt, DialogInterface.OnClickListener paramOnClickListener)
  {
    bindButton(paramInt, paramOnClickListener, 2131492916, -3);
    return this;
  }

  public IgDialogBuilder setPositiveButton(int paramInt, DialogInterface.OnClickListener paramOnClickListener)
  {
    bindButton(paramInt, paramOnClickListener, 2131492915, -1);
    return this;
  }

  public IgDialogBuilder setTitle(int paramInt)
  {
    View localView = this.mD.findViewById(2131492911);
    ((TextView)localView).setText(paramInt);
    localView.setVisibility(0);
    return this;
  }

  public IgDialogBuilder setTitle(String paramString)
  {
    View localView = this.mD.findViewById(2131492911);
    ((TextView)localView).setText(paramString);
    localView.setVisibility(0);
    return this;
  }

  public IgDialogBuilder setView(View paramView)
  {
    ViewGroup localViewGroup = (ViewGroup)this.mD.findViewById(2131492914);
    localViewGroup.addView(paramView);
    localViewGroup.setVisibility(0);
    return this;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.widget.IgDialogBuilder
 * JD-Core Version:    0.6.0
 */
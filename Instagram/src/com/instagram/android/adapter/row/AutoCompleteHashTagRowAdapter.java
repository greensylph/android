package com.instagram.android.adapter.row;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class AutoCompleteHashTagRowAdapter
{
  public static void bindView(Holder paramHolder, String paramString)
  {
    TextView localTextView = paramHolder.name;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramString;
    localTextView.setText(String.format("%s", arrayOfObject));
    paramHolder.count.setVisibility(8);
  }

  public static View newView(Context paramContext)
  {
    View localView = LayoutInflater.from(paramContext).inflate(2130903106, null);
    Holder localHolder = new Holder();
    Holder.access$002(localHolder, (TextView)localView.findViewById(2131493047));
    Holder.access$102(localHolder, (TextView)localView.findViewById(2131493048));
    localView.setTag(localHolder);
    return localView;
  }

  public static class Holder
  {
    private TextView count;
    private TextView name;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.adapter.row.AutoCompleteHashTagRowAdapter
 * JD-Core Version:    0.6.0
 */
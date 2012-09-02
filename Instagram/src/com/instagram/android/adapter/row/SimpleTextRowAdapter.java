package com.instagram.android.adapter.row;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.instagram.android.model.SimpleMenuItem;

public class SimpleTextRowAdapter
{
  public static void bindView(View paramView, Holder paramHolder, SimpleMenuItem paramSimpleMenuItem, boolean paramBoolean)
  {
    View localView;
    if (paramSimpleMenuItem.getProvidedString() != null)
    {
      paramHolder.textView.setText(paramSimpleMenuItem.getProvidedString());
      localView = paramHolder.disclosure;
      if (!paramSimpleMenuItem.isDisclosure())
        break label73;
    }
    label73: for (int i = 0; ; i = 8)
    {
      localView.setVisibility(i);
      if (paramBoolean)
      {
        paramView.setBackgroundDrawable(null);
        paramView.setPadding(0, 0, 0, 0);
      }
      return;
      paramHolder.textView.setText(paramSimpleMenuItem.getStringResId());
      break;
    }
  }

  public static View newBottomView(Context paramContext)
  {
    View localView1 = LayoutInflater.from(paramContext).inflate(2130903119, null);
    int i = paramContext.getResources().getDimensionPixelSize(2131427343);
    View localView2 = localView1.findViewById(2131493079);
    localView1.setPadding(i, 0, i, i);
    localView2.setBackgroundDrawable(paramContext.getResources().getDrawable(2130837702));
    newViewWithHolder(localView1);
    return localView1;
  }

  public static View newStandaloneView(Context paramContext)
  {
    return newTopView(paramContext, true);
  }

  public static View newTopView(Context paramContext)
  {
    return newTopView(paramContext, false);
  }

  private static View newTopView(Context paramContext, boolean paramBoolean)
  {
    View localView1 = LayoutInflater.from(paramContext).inflate(2130903119, null);
    int i = paramContext.getResources().getDimensionPixelSize(2131427343);
    View localView2 = localView1.findViewById(2131493079);
    localView1.setPadding(i, i, i, 0);
    if (paramBoolean)
      localView2.setBackgroundDrawable(paramContext.getResources().getDrawable(2130837709));
    while (true)
    {
      newViewWithHolder(localView1);
      return localView1;
      localView2.setBackgroundDrawable(paramContext.getResources().getDrawable(2130837712));
    }
  }

  public static View newView(Context paramContext)
  {
    View localView = LayoutInflater.from(paramContext).inflate(2130903119, null);
    newViewWithHolder(localView);
    return localView;
  }

  private static void newViewWithHolder(View paramView)
  {
    Holder localHolder = new Holder();
    localHolder.textView = ((TextView)paramView.findViewById(2131493080));
    localHolder.disclosure = paramView.findViewById(2131493081);
    paramView.setTag(localHolder);
  }

  public static class Holder
  {
    View disclosure;
    TextView textView;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.adapter.row.SimpleTextRowAdapter
 * JD-Core Version:    0.6.0
 */
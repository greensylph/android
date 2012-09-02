package com.instagram.android.adapter.row;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.instagram.android.widget.LoadMoreButton;
import com.instagram.android.widget.LoadMoreButton.LoadMoreInterface;

public class LoadMoreRowAdapter
{
  public static void bindView(Holder paramHolder, LoadMoreButton.LoadMoreInterface paramLoadMoreInterface)
  {
    paramHolder.button.bind(paramLoadMoreInterface);
  }

  public static View newView(Context paramContext, ViewGroup paramViewGroup)
  {
    View localView = LayoutInflater.from(paramContext).inflate(2130903109, paramViewGroup, false);
    Holder localHolder = new Holder();
    Holder.access$002(localHolder, (LoadMoreButton)localView.findViewById(2131493051));
    localView.setTag(localHolder);
    return localView;
  }

  public static class Holder
  {
    private LoadMoreButton button;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.adapter.row.LoadMoreRowAdapter
 * JD-Core Version:    0.6.0
 */
package com.instagram.android.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.instagram.android.adapter.row.SimpleHeaderRowAdapter;
import com.instagram.android.adapter.row.SimpleHeaderRowAdapter.Holder;
import com.instagram.android.adapter.row.SimpleTextRowAdapter;
import com.instagram.android.adapter.row.SimpleTextRowAdapter.Holder;
import com.instagram.android.model.GroupingHeader;
import com.instagram.android.model.SimpleMenuItem;
import java.util.Collection;
import java.util.List;

public class SimplePreferenceAdapter extends EnhancedArrayAdapter
{
  private static final int VIEW_TYPE_BOTTOM = 1;
  private static final int VIEW_TYPE_HEADER = 3;
  private static final int VIEW_TYPE_NORMAL = 2;
  private static final int VIEW_TYPE_STANDALONE = 4;
  private static final int VIEW_TYPE_TOP;
  private boolean mShowDialogView = false;

  public SimplePreferenceAdapter(Context paramContext)
  {
    super(paramContext);
  }

  public void addDialogMenuItems(CharSequence[] paramArrayOfCharSequence)
  {
    int i = paramArrayOfCharSequence.length;
    for (int j = 0; j < i; j++)
    {
      CharSequence localCharSequence = paramArrayOfCharSequence[j];
      this.mObjects.add(new SimpleMenuItem(localCharSequence, false));
    }
    notifyDataSetChanged();
  }

  public void addItems(Collection paramCollection)
  {
    this.mObjects.addAll(paramCollection);
    notifyDataSetChanged();
  }

  protected void bindView(View paramView, Context paramContext, int paramInt)
  {
    switch (getItemViewType(paramInt))
    {
    default:
      SimpleTextRowAdapter.bindView(paramView, (SimpleTextRowAdapter.Holder)paramView.getTag(), (SimpleMenuItem)getItem(paramInt), this.mShowDialogView);
    case 3:
    }
    while (true)
    {
      return;
      SimpleHeaderRowAdapter.bindView((SimpleHeaderRowAdapter.Holder)paramView.getTag(), (GroupingHeader)getItem(paramInt));
    }
  }

  public int getItemViewType(int paramInt)
  {
    int i;
    if (((paramInt != 0) && (!(getItem(paramInt - 1) instanceof GroupingHeader))) || (((getItem(paramInt + 1) instanceof GroupingHeader)) || ((paramInt == -1 + getCount()) && ((getItem(paramInt - 1) instanceof GroupingHeader)))))
      i = 4;
    while (true)
    {
      return i;
      if ((paramInt == 0) || ((getItem(paramInt - 1) instanceof GroupingHeader)))
      {
        i = 0;
        continue;
      }
      if ((paramInt == -1 + getCount()) || ((getItem(paramInt + 1) instanceof GroupingHeader)))
      {
        i = 1;
        continue;
      }
      if ((getItem(paramInt) instanceof GroupingHeader))
      {
        i = 3;
        continue;
      }
      i = 2;
    }
  }

  public int getViewTypeCount()
  {
    return 5;
  }

  protected View newView(Context paramContext, int paramInt, ViewGroup paramViewGroup)
  {
    View localView;
    switch (getItemViewType(paramInt))
    {
    case 2:
    default:
      localView = SimpleTextRowAdapter.newView(paramContext);
    case 4:
    case 0:
    case 1:
    case 3:
    }
    while (true)
    {
      return localView;
      localView = SimpleTextRowAdapter.newStandaloneView(paramContext);
      continue;
      localView = SimpleTextRowAdapter.newTopView(paramContext);
      continue;
      localView = SimpleTextRowAdapter.newBottomView(paramContext);
      continue;
      localView = SimpleHeaderRowAdapter.newView(paramContext);
    }
  }

  public void showDialogView(boolean paramBoolean)
  {
    this.mShowDialogView = paramBoolean;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.adapter.SimplePreferenceAdapter
 * JD-Core Version:    0.6.0
 */
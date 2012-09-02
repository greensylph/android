package com.instagram.android.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import com.instagram.android.adapter.filter.HashtagAndUsernameCompositeFilter;
import com.instagram.android.adapter.row.AutoCompleteHashTagRowAdapter;
import com.instagram.android.adapter.row.AutoCompleteHashTagRowAdapter.Holder;
import com.instagram.android.adapter.row.AutoCompleteUserRowAdapter;
import com.instagram.android.adapter.row.AutoCompleteUserRowAdapter.Holder;
import com.instagram.android.model.AutoCompleteUser;
import com.instagram.android.model.Media;
import java.util.ArrayList;

public class FilterHashTagsAndNamesAdapter extends EnhancedArrayAdapter
  implements Filterable
{
  private static final int TYPE_HASHTAG = 0;
  private static final int TYPE_USER = 1;
  private final Context mContext;
  private HashtagAndUsernameCompositeFilter mFilter;
  private Media mMedia;
  private ArrayList mValues;

  public FilterHashTagsAndNamesAdapter(Context paramContext)
  {
    super(paramContext);
    this.mContext = paramContext;
  }

  public FilterHashTagsAndNamesAdapter(Context paramContext, Media paramMedia)
  {
    super(paramContext);
    this.mContext = paramContext;
    this.mMedia = paramMedia;
  }

  protected void bindView(View paramView, Context paramContext, int paramInt)
  {
    switch (getItemViewType(paramInt))
    {
    default:
    case 0:
    case 1:
    }
    while (true)
    {
      return;
      AutoCompleteHashTagRowAdapter.bindView((AutoCompleteHashTagRowAdapter.Holder)paramView.getTag(), (String)this.mValues.get(paramInt));
      continue;
      AutoCompleteUserRowAdapter.bindView((AutoCompleteUserRowAdapter.Holder)paramView.getTag(), (AutoCompleteUser)this.mValues.get(paramInt));
    }
  }

  public int getCount()
  {
    if (this.mValues != null);
    for (int i = this.mValues.size(); ; i = 0)
      return i;
  }

  public Filter getFilter()
  {
    if (this.mFilter == null)
      if (this.mMedia == null)
        break label35;
    label35: for (this.mFilter = new HashtagAndUsernameCompositeFilter(this, this.mMedia); ; this.mFilter = new HashtagAndUsernameCompositeFilter(this))
      return this.mFilter;
  }

  public Object getItem(int paramInt)
  {
    int i = getItemViewType(paramInt);
    String str;
    if (i == 0)
      str = "#" + (String)this.mValues.get(paramInt);
    while (true)
    {
      return str;
      if (i == 1)
      {
        str = "@" + ((AutoCompleteUser)this.mValues.get(paramInt)).getUsername();
        continue;
      }
      str = "";
    }
  }

  public long getItemId(int paramInt)
  {
    return 0L;
  }

  public int getItemViewType(int paramInt)
  {
    if ((this.mValues.get(paramInt) instanceof String));
    for (int i = 0; ; i = 1)
      return i;
  }

  public int getViewTypeCount()
  {
    return 2;
  }

  protected View newView(Context paramContext, int paramInt, ViewGroup paramViewGroup)
  {
    View localView;
    switch (getItemViewType(paramInt))
    {
    default:
      localView = null;
    case 0:
    case 1:
    }
    while (true)
    {
      return localView;
      localView = AutoCompleteHashTagRowAdapter.newView(paramContext);
      continue;
      localView = AutoCompleteUserRowAdapter.newView(paramContext);
    }
  }

  public void setItems(ArrayList paramArrayList)
  {
    this.mValues = paramArrayList;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.adapter.FilterHashTagsAndNamesAdapter
 * JD-Core Version:    0.6.0
 */
package com.instagram.android.adapter.filter;

import android.widget.Filter;
import android.widget.Filter.FilterResults;
import com.instagram.android.adapter.FilterHashTagsAndNamesAdapter;
import com.instagram.android.model.Media;
import java.util.ArrayList;

public class HashtagAndUsernameCompositeFilter extends Filter
{
  private FilterHashTagsAndNamesAdapter mAdapter;
  private HashtagAutoCompleteFilter mHashtagAutoCompleteFilter;
  private UsernameAutoCompleteFilter mUsernameAutoCompleteFilter;

  public HashtagAndUsernameCompositeFilter(FilterHashTagsAndNamesAdapter paramFilterHashTagsAndNamesAdapter)
  {
    this.mAdapter = paramFilterHashTagsAndNamesAdapter;
    this.mHashtagAutoCompleteFilter = new HashtagAutoCompleteFilter(paramFilterHashTagsAndNamesAdapter);
    this.mUsernameAutoCompleteFilter = new UsernameAutoCompleteFilter();
  }

  public HashtagAndUsernameCompositeFilter(FilterHashTagsAndNamesAdapter paramFilterHashTagsAndNamesAdapter, Media paramMedia)
  {
    this.mAdapter = paramFilterHashTagsAndNamesAdapter;
    this.mHashtagAutoCompleteFilter = new HashtagAutoCompleteFilter(paramFilterHashTagsAndNamesAdapter);
    this.mUsernameAutoCompleteFilter = new UsernameAutoCompleteFilter(paramMedia);
  }

  protected Filter.FilterResults performFiltering(CharSequence paramCharSequence)
  {
    Filter.FilterResults localFilterResults;
    if ((paramCharSequence != null) && (paramCharSequence.length() > 0))
      if (paramCharSequence.charAt(0) == '#')
        localFilterResults = this.mHashtagAutoCompleteFilter.performFiltering(paramCharSequence.subSequence(1, paramCharSequence.length()));
    while (true)
    {
      return localFilterResults;
      if (paramCharSequence.charAt(0) == '@')
      {
        localFilterResults = this.mUsernameAutoCompleteFilter.performFiltering(paramCharSequence.subSequence(1, paramCharSequence.length()));
        continue;
      }
      localFilterResults = null;
    }
  }

  protected void publishResults(CharSequence paramCharSequence, Filter.FilterResults paramFilterResults)
  {
    if (paramFilterResults == null);
    while (true)
    {
      return;
      this.mAdapter.setItems((ArrayList)paramFilterResults.values);
      if (paramFilterResults.count > 0)
      {
        this.mAdapter.notifyDataSetChanged();
        continue;
      }
      this.mAdapter.notifyDataSetInvalidated();
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.adapter.filter.HashtagAndUsernameCompositeFilter
 * JD-Core Version:    0.6.0
 */
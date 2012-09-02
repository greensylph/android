package com.instagram.android.adapter.filter;

import android.widget.Filter;
import android.widget.Filter.FilterResults;
import com.instagram.android.adapter.FilterHashTagsAndNamesAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class HashtagAutoCompleteFilter extends Filter
{
  private static final HashMap<Character, HashSet<String>> sTags = new HashMap();
  private final FilterHashTagsAndNamesAdapter mAdapter;

  public HashtagAutoCompleteFilter(FilterHashTagsAndNamesAdapter paramFilterHashTagsAndNamesAdapter)
  {
    this.mAdapter = paramFilterHashTagsAndNamesAdapter;
  }

  public static void addTag(String paramString)
  {
    char c = Character.toLowerCase(paramString.charAt(0));
    if (!sTags.containsKey(Character.valueOf(c)))
      sTags.put(Character.valueOf(c), new HashSet());
    ((HashSet)sTags.get(Character.valueOf(c))).add(paramString);
  }

  public static void clearTags()
  {
    sTags.clear();
  }

  protected Filter.FilterResults performFiltering(CharSequence paramCharSequence)
  {
    Filter.FilterResults localFilterResults = new Filter.FilterResults();
    if ((paramCharSequence == null) || (paramCharSequence.length() == 0))
      localFilterResults.values = new ArrayList();
    ArrayList localArrayList;
    for (localFilterResults.count = 0; ; localFilterResults.count = localArrayList.size())
    {
      return localFilterResults;
      Character localCharacter = Character.valueOf(Character.toLowerCase(paramCharSequence.charAt(0)));
      localArrayList = new ArrayList();
      if (sTags.containsKey(localCharacter))
      {
        Iterator localIterator = ((HashSet)sTags.get(localCharacter)).iterator();
        while (localIterator.hasNext())
        {
          String str = (String)localIterator.next();
          if (!str.toLowerCase().startsWith(paramCharSequence.toString().toLowerCase()))
            continue;
          localArrayList.add(str);
        }
      }
      localFilterResults.values = localArrayList;
    }
  }

  protected void publishResults(CharSequence paramCharSequence, Filter.FilterResults paramFilterResults)
  {
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.adapter.filter.HashtagAutoCompleteFilter
 * JD-Core Version:    0.6.0
 */
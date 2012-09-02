package com.instagram.android.adapter.filter;

import android.widget.Filter;
import android.widget.Filter.FilterResults;
import com.instagram.android.model.AutoCompleteUser;
import com.instagram.android.model.Comment;
import com.instagram.android.model.Media;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class UsernameAutoCompleteFilter extends Filter
{
  private static HashMap<Character, HashSet<AutoCompleteUser>> sFullnames;
  private static HashMap<Character, HashSet<AutoCompleteUser>> sUsernames = new HashMap();
  private HashMap<Character, HashSet<AutoCompleteUser>> mMediaFullnames = new HashMap();
  private HashMap<Character, HashSet<AutoCompleteUser>> mMediaUsernames = new HashMap();

  static
  {
    sFullnames = new HashMap();
  }

  public UsernameAutoCompleteFilter()
  {
  }

  public UsernameAutoCompleteFilter(Media paramMedia)
  {
    Iterator localIterator = paramMedia.getActiveComments().iterator();
    while (localIterator.hasNext())
    {
      AutoCompleteUser localAutoCompleteUser = new AutoCompleteUser(((Comment)localIterator.next()).getUser());
      addToUsernameMap(localAutoCompleteUser, this.mMediaUsernames, false);
      addToUsernameMap(localAutoCompleteUser, this.mMediaFullnames, true);
    }
  }

  private void addToMatchingFullnameSetFromMap(CharSequence paramCharSequence, Set<AutoCompleteUser> paramSet, HashMap<Character, HashSet<AutoCompleteUser>> paramHashMap)
  {
    Character localCharacter = Character.valueOf(Character.toLowerCase(paramCharSequence.charAt(0)));
    if (paramHashMap.containsKey(localCharacter))
    {
      Iterator localIterator = ((HashSet)paramHashMap.get(localCharacter)).iterator();
      while (localIterator.hasNext())
      {
        AutoCompleteUser localAutoCompleteUser = (AutoCompleteUser)localIterator.next();
        if (!localAutoCompleteUser.getFullNameConcat().startsWith(paramCharSequence.toString().toLowerCase()))
          continue;
        paramSet.add(localAutoCompleteUser);
      }
    }
  }

  private void addToMatchingUsernameSetFromMap(CharSequence paramCharSequence, Set<AutoCompleteUser> paramSet, HashMap<Character, HashSet<AutoCompleteUser>> paramHashMap)
  {
    Character localCharacter = Character.valueOf(Character.toLowerCase(paramCharSequence.charAt(0)));
    if (paramHashMap.containsKey(localCharacter))
    {
      Iterator localIterator = ((HashSet)paramHashMap.get(localCharacter)).iterator();
      while (localIterator.hasNext())
      {
        AutoCompleteUser localAutoCompleteUser = (AutoCompleteUser)localIterator.next();
        if (!localAutoCompleteUser.getUsername().toLowerCase().startsWith(paramCharSequence.toString().toLowerCase()))
          continue;
        paramSet.add(localAutoCompleteUser);
      }
    }
  }

  private static void addToUsernameMap(AutoCompleteUser paramAutoCompleteUser, HashMap<Character, HashSet<AutoCompleteUser>> paramHashMap, boolean paramBoolean)
  {
    Character localCharacter = getFirstCharacter(paramAutoCompleteUser, paramBoolean);
    if (localCharacter != null)
    {
      if (!paramHashMap.containsKey(localCharacter))
        paramHashMap.put(localCharacter, new HashSet());
      ((HashSet)paramHashMap.get(localCharacter)).add(paramAutoCompleteUser);
    }
  }

  public static void addUser(AutoCompleteUser paramAutoCompleteUser)
  {
    addToUsernameMap(paramAutoCompleteUser, sUsernames, false);
    addToUsernameMap(paramAutoCompleteUser, sFullnames, true);
  }

  public static void clear()
  {
    sUsernames.clear();
    sFullnames.clear();
  }

  private Set<AutoCompleteUser> computeMatchingUserAutoCompleteSet(CharSequence paramCharSequence)
  {
    HashSet localHashSet = new HashSet();
    addToMatchingUsernameSetFromMap(paramCharSequence, localHashSet, sUsernames);
    addToMatchingUsernameSetFromMap(paramCharSequence, localHashSet, this.mMediaUsernames);
    addToMatchingFullnameSetFromMap(paramCharSequence, localHashSet, sFullnames);
    addToMatchingFullnameSetFromMap(paramCharSequence, localHashSet, this.mMediaFullnames);
    return localHashSet;
  }

  private static Character getFirstCharacter(AutoCompleteUser paramAutoCompleteUser, boolean paramBoolean)
  {
    Character localCharacter = null;
    if (paramBoolean)
      if ((paramAutoCompleteUser.getFullname() == null) || (paramAutoCompleteUser.getFullname().length() <= 0));
    for (localCharacter = Character.valueOf(Character.toLowerCase(paramAutoCompleteUser.getFullname().charAt(0))); ; localCharacter = Character.valueOf(Character.toLowerCase(paramAutoCompleteUser.getUsername().toLowerCase().charAt(0))))
      do
        return localCharacter;
      while ((paramAutoCompleteUser.getUsername() == null) || (paramAutoCompleteUser.getUsername().length() <= 0));
  }

  public static void removeUser(AutoCompleteUser paramAutoCompleteUser)
  {
    Character localCharacter1 = getFirstCharacter(paramAutoCompleteUser, false);
    if (localCharacter1 != null)
    {
      HashSet localHashSet2 = (HashSet)sUsernames.get(localCharacter1);
      if (localHashSet2 != null)
        localHashSet2.remove(paramAutoCompleteUser);
    }
    Character localCharacter2 = getFirstCharacter(paramAutoCompleteUser, true);
    if (localCharacter2 != null)
    {
      HashSet localHashSet1 = (HashSet)sFullnames.get(localCharacter2);
      if (localHashSet1 != null)
        localHashSet1.remove(paramAutoCompleteUser);
    }
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
      localArrayList = new ArrayList(computeMatchingUserAutoCompleteSet(paramCharSequence));
      localFilterResults.values = localArrayList;
    }
  }

  protected void publishResults(CharSequence paramCharSequence, Filter.FilterResults paramFilterResults)
  {
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.adapter.filter.UsernameAutoCompleteFilter
 * JD-Core Version:    0.6.0
 */
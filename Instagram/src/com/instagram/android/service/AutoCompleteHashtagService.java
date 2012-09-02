package com.instagram.android.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.instagram.android.adapter.filter.HashtagAutoCompleteFilter;
import com.instagram.util.StringUtil;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;

public class AutoCompleteHashtagService extends IntentService
{
  private static final String LOG_TAG = "AutoCompleteHashtagService";

  public AutoCompleteHashtagService()
  {
    super(AutoCompleteHashtagService.class.toString());
  }

  public static void addTag(String paramString)
  {
    getTagsPrefs().edit().putString(paramString, paramString).commit();
    HashtagAutoCompleteFilter.addTag(paramString);
  }

  public static void addTagsFromText(String paramString)
  {
    Matcher localMatcher = StringUtil.hashMatcher(paramString);
    SharedPreferences.Editor localEditor = getTagsPrefs().edit();
    while (localMatcher.find())
    {
      String str1 = localMatcher.group(1);
      String str2 = str1.substring(1, str1.length());
      localEditor.putString(str2, str2);
      HashtagAutoCompleteFilter.addTag(str2);
    }
    localEditor.commit();
  }

  public static void clear()
  {
    getTagsPrefs().edit().clear().commit();
    HashtagAutoCompleteFilter.clearTags();
  }

  private static SharedPreferences getTagsPrefs()
  {
    return AppContext.getContext().getSharedPreferences("AutoCompleteHashtagService", 0);
  }

  private void loadCachedSetFromDisk()
  {
    Iterator localIterator = getTagsPrefs().getAll().entrySet().iterator();
    while (localIterator.hasNext())
      HashtagAutoCompleteFilter.addTag((String)((Map.Entry)localIterator.next()).getValue());
  }

  protected void onHandleIntent(Intent paramIntent)
  {
    loadCachedSetFromDisk();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.service.AutoCompleteHashtagService
 * JD-Core Version:    0.6.0
 */
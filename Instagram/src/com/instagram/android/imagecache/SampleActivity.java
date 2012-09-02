package com.instagram.android.imagecache;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;

public class SampleActivity extends ListActivity
{
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    IgListAdapter localIgListAdapter = new IgListAdapter(this);
    setListAdapter(localIgListAdapter);
    String[] arrayOfString = new String[localIgListAdapter.getCount()];
    for (int i = 0; i < localIgListAdapter.getCount(); i++)
      arrayOfString[i] = localIgListAdapter.getUrlForRow(i);
    IgBitmapCache.getInstance(this).preLoadBitmaps(arrayOfString);
    getListView().setRecyclerListener(new SampleActivity.1(this));
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.imagecache.SampleActivity
 * JD-Core Version:    0.6.0
 */
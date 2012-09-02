package com.instagram.android.imagecache;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

public class GridViewActivity extends Activity
{
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.layout_gridview);
    GridView localGridView = (GridView)findViewById(R.id.gridview);
    IgListAdapter localIgListAdapter = new IgListAdapter(this);
    localIgListAdapter.setIsGrid(true);
    String[] arrayOfString = new String[localIgListAdapter.getCount()];
    for (int i = 0; i < localIgListAdapter.getCount(); i++)
      arrayOfString[i] = localIgListAdapter.getUrlForRow(i);
    IgBitmapCache.getInstance(this).preLoadBitmaps(arrayOfString);
    localGridView.setAdapter(localIgListAdapter);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.imagecache.GridViewActivity
 * JD-Core Version:    0.6.0
 */
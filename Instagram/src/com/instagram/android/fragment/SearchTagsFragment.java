package com.instagram.android.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import com.instagram.android.Preferences;
import com.instagram.android.adapter.HashtagListAdapter;
import com.instagram.android.model.Hashtag;
import com.instagram.android.service.AppContext;
import com.instagram.api.request.HashtagSearchRequest;
import java.util.ArrayList;
import java.util.Iterator;

public class SearchTagsFragment extends SearchFragment
{
  private HashtagListAdapter mAdapter;
  private Handler mHandler = new Handler();
  private HashtagSearchRequest mSearchTagsRequest;

  private void updateProgressBarState()
  {
    this.mHandler.post(new SearchTagsFragment.2(this));
  }

  protected BaseAdapter getAdapter()
  {
    if (this.mAdapter == null)
    {
      this.mAdapter = new HashtagListAdapter(AppContext.getContext());
      ArrayList localArrayList1 = Preferences.getInstance(AppContext.getContext()).getRecentHashtagSearches();
      if ((localArrayList1 != null) && (localArrayList1.size() > 0))
      {
        ArrayList localArrayList2 = new ArrayList(localArrayList1.size());
        Iterator localIterator = localArrayList1.iterator();
        while (localIterator.hasNext())
          localArrayList2.add(new Hashtag((String)localIterator.next()));
        this.mAdapter.setItems(localArrayList2);
      }
    }
    return this.mAdapter;
  }

  protected int getHintResource()
  {
    return 2131231013;
  }

  protected AdapterView.OnItemClickListener getItemClickListener()
  {
    return new SearchTagsFragment.3(this);
  }

  protected int getMode()
  {
    return 0;
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mSearchTagsRequest = new HashtagSearchRequest(AppContext.getContext(), getCompositeLoaderManager(), 0, new SearchTagsFragment.1(this));
  }

  protected void performQuery(String paramString)
  {
    this.mSearchTagsRequest.perform(paramString);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.SearchTagsFragment
 * JD-Core Version:    0.6.0
 */
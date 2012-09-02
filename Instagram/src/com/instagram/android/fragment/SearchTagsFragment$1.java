package com.instagram.android.fragment;

import com.instagram.android.adapter.HashtagListAdapter;
import com.instagram.android.model.Hashtag;
import com.instagram.android.service.ActionBarService;
import com.instagram.android.service.AppContext;
import com.instagram.api.AbstractStreamingApiCallbacks;
import java.util.ArrayList;

class SearchTagsFragment$1 extends AbstractStreamingApiCallbacks<ArrayList<Hashtag>>
{
  public void onRequestFinished()
  {
    this.this$0.mIsLoading = false;
    ActionBarService.getInstance(AppContext.getContext()).setIsLoading(false);
    SearchTagsFragment.access$100(this.this$0);
  }

  public void onRequestStart()
  {
    this.this$0.mIsLoading = true;
    ActionBarService.getInstance(AppContext.getContext()).setIsLoading(true);
    SearchTagsFragment.access$100(this.this$0);
  }

  protected void onSuccess(ArrayList<Hashtag> paramArrayList)
  {
    SearchTagsFragment.access$000(this.this$0).setItems(paramArrayList);
    this.this$0.setSelection(0);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.SearchTagsFragment.1
 * JD-Core Version:    0.6.0
 */
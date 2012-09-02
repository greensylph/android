package com.instagram.android.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.instagram.android.Preferences;
import com.instagram.android.model.Hashtag;
import com.instagram.android.service.AppContext;

class SearchTagsFragment$3
  implements AdapterView.OnItemClickListener
{
  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    Hashtag localHashtag = (Hashtag)paramAdapterView.getItemAtPosition(paramInt);
    Preferences.getInstance(AppContext.getContext()).saveRecentHashtag(localHashtag.getTagName());
    HashtagFeedFragment.startFragment(this.this$0.getActivity(), localHashtag.getTagName(), this.this$0.getFragmentManager());
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.SearchTagsFragment.3
 * JD-Core Version:    0.6.0
 */
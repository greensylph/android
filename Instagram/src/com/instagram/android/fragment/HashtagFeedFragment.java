package com.instagram.android.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import com.instagram.android.adapter.FeedAdapter.ViewMode;
import com.instagram.android.adapter.HashtagFeedAdapter;
import com.instagram.android.adapter.SimpleHeaderFeedAdapter;
import com.instagram.android.model.Hashtag;
import com.instagram.android.model.MediaFeedResponse;
import com.instagram.android.service.AutoCompleteHashtagService;
import com.instagram.android.widget.IgDialogBuilder;
import com.instagram.api.AbstractStreamingApiCallbacks;
import com.instagram.api.request.MediaFeedRequest;
import com.instagram.util.FragmentUtil;

public class HashtagFeedFragment extends FeedFragment
{
  public static final String ARGUMENT_TAG_NAME = "com.instagram.android.fragment.HashtagFeedFragment.ARGUMENT_TAG_NAME";
  private HashtagFeedAdapter mAdapter;
  private BroadcastReceiver updatedHashtag = new HashtagFeedFragment.1(this);

  private static void showContentAdvisory(Activity paramActivity, FragmentManager paramFragmentManager, String paramString1, String paramString2)
  {
    new IgDialogBuilder(paramActivity).setTitle(2131231073).setMessage(paramString1).setPositiveButton(2131231072, new HashtagFeedFragment.5(paramString2, paramFragmentManager)).setNegativeButton(2131230921, new HashtagFeedFragment.4()).create().show();
  }

  private static void showFeed(String paramString, FragmentManager paramFragmentManager)
  {
    HashtagFeedFragment localHashtagFeedFragment = new HashtagFeedFragment();
    Bundle localBundle = new Bundle();
    localBundle.putString("com.instagram.android.fragment.HashtagFeedFragment.ARGUMENT_TAG_NAME", paramString);
    FragmentUtil.navigateTo(paramFragmentManager, localHashtagFeedFragment, localBundle);
  }

  public static void startFragment(Activity paramActivity, String paramString, FragmentManager paramFragmentManager)
  {
    String str = Hashtag.getContentAdvisoryWarning(paramString);
    if (str == null)
      showFeed(paramString, paramFragmentManager);
    while (true)
    {
      return;
      showContentAdvisory(paramActivity, paramFragmentManager, str, paramString);
    }
  }

  public ActionBarConfigurer getActionBarConfigurer()
  {
    return new HashtagFeedFragment.3(this);
  }

  protected SimpleHeaderFeedAdapter getAdapter()
  {
    if (this.mAdapter == null)
      this.mAdapter = new HashtagFeedAdapter(getActivity(), this, getDefaultFeedViewMode(), getGridViewPadding(), this);
    return this.mAdapter;
  }

  protected FeedAdapter.ViewMode getDefaultFeedViewMode()
  {
    return FeedAdapter.ViewMode.GRID;
  }

  protected MediaFeedRequest makeRequest(AbstractStreamingApiCallbacks<MediaFeedResponse> paramAbstractStreamingApiCallbacks)
  {
    return new HashtagFeedFragment.2(this, this, 2131492868, paramAbstractStreamingApiCallbacks);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Hashtag localHashtag = new Hashtag(getArguments().getString("com.instagram.android.fragment.HashtagFeedFragment.ARGUMENT_TAG_NAME"), getLoaderManager());
    LocalBroadcastManager.getInstance(getActivity()).registerReceiver(this.updatedHashtag, new IntentFilter(Hashtag.getHashtagBroadcastId(localHashtag.getTagName())));
    this.mAdapter.setHeaderObject(localHashtag);
    AutoCompleteHashtagService.addTag(localHashtag.getTagName());
  }

  public void onDestroy()
  {
    LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(this.updatedHashtag);
    super.onDestroy();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.HashtagFeedFragment
 * JD-Core Version:    0.6.0
 */
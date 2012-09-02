package com.instagram.android.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.Toast;
import ch.boye.httpclientandroidlib.HttpEntity;
import ch.boye.httpclientandroidlib.HttpResponse;
import ch.boye.httpclientandroidlib.StatusLine;
import ch.boye.httpclientandroidlib.client.HttpClient;
import ch.boye.httpclientandroidlib.client.methods.HttpGet;
import ch.boye.httpclientandroidlib.impl.client.DefaultHttpClient;
import ch.boye.httpclientandroidlib.util.EntityUtils;
import com.instagram.android.Log;
import com.instagram.android.adapter.FeedAdapter;
import com.instagram.android.adapter.FeedAdapter.AdapterRefreshedListener;
import com.instagram.android.adapter.FeedAdapter.GridViewPadding;
import com.instagram.android.adapter.FeedAdapter.ListenerDelegate;
import com.instagram.android.adapter.FeedAdapter.MediaSetChangeListener;
import com.instagram.android.adapter.FeedAdapter.ViewMode;
import com.instagram.android.fragment.ScrollToTopFragment.ScrollToTopFragment;
import com.instagram.android.listener.CommentLinkClickListener;
import com.instagram.android.listener.DoubleTapMediaListener;
import com.instagram.android.listener.UserLinkClickListener;
import com.instagram.android.model.Media;
import com.instagram.android.model.MediaFeedResponse;
import com.instagram.android.model.User;
import com.instagram.android.service.ActionBarService;
import com.instagram.android.service.AppContext;
import com.instagram.android.service.ClickManager;
import com.instagram.android.service.CustomObjectMapper;
import com.instagram.android.service.MediaStore;
import com.instagram.android.widget.LoadMoreButton.LoadMoreInterface;
import com.instagram.api.AbstractStreamingApiCallbacks;
import com.instagram.api.ApiResponse;
import com.instagram.api.request.LikeRequest;
import com.instagram.api.request.MediaFeedRequest;
import com.instagram.util.FragmentUtil;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

public class FeedFragment extends ListFragment
  implements FeedAdapter.ListenerDelegate, ActionBarConfigurer.ActionBarConfigurerFactory, LoadMoreButton.LoadMoreInterface, ScrollToTopFragment
{
  public static final String ARGUMENTS_KEY_EXTRA_MEDIA_ID = "com.instagram.android.fragment.ARGUMENTS_KEY_EXTRA_MEDIA_ID";
  public static final String ARGUMENTS_KEY_LOAD_FROM_NETWORK = "com.instagram.android.fragment.ARGUMENTS_KEY_LOAD_FROM_NETWORK";
  public static final String ARGUMENTS_KEY_SHORT_URL = "com.instagram.android.fragment.ARGUMENTS_KEY_SHORT_URL";
  private static final String TAG = "FeedFragment";
  private static boolean sFlagScrollToTop;
  protected Long lastUpdatedFeedTime;
  protected FeedAdapter mAdapter;
  private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramContext, Intent paramIntent)
    {
      String str = paramIntent.getAction();
      if (str.equals("com.instagram.android.service.action_bar_clicked"))
        FeedFragment.this.scrollToTop();
      while (true)
      {
        return;
        if (str.equals("com.instagram.android.service.action_bar_refresh_clicked"))
        {
          if (FeedFragment.this.mIsLoading)
          {
            Log.d("FeedFragment", "Is loading already set, not performing request");
            continue;
          }
          FeedFragment.this.constructAndPerformFeedRequest(true, new FeedFragment.FeedRequestCallbacks(FeedFragment.this));
          continue;
        }
      }
    }
  };
  protected MediaFeedRequest mFeedRequest;
  private Handler mHandler = new Handler();
  protected boolean mInitialNetworkRequestFinished;
  private boolean mIsLoading = false;
  protected boolean mLoadingDefaultContent;
  private String mMaxId = null;
  private String mMediaId = null;
  private boolean mMoreAvailable = false;
  private BroadcastReceiver mediaUpdateReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramContext, Intent paramIntent)
    {
      if ((paramIntent != null) && (paramIntent.getExtras() != null))
      {
        FeedAdapter localFeedAdapter = FeedFragment.this.getAdapter();
        boolean bool1 = localFeedAdapter.hasMedia();
        localFeedAdapter.notifyDataSetChanged();
        boolean bool2 = localFeedAdapter.hasMedia();
        if ((bool1) && (!bool2) && (1 >= FeedFragment.this.getFragmentManager().getBackStackEntryCount()))
          FeedFragment.this.getFragmentManager().popBackStack();
      }
    }
  };

  public static void flagScrollToTop()
  {
    sFlagScrollToTop = true;
  }

  private void resolveShortUrl(String paramString)
  {
    CustomObjectMapper localCustomObjectMapper = CustomObjectMapper.getInstance(getActivity());
    String str = "http://instagram.com/api/v1/oembed/?url=" + paramString;
    8 local8 = new AsyncTask(localCustomObjectMapper)
    {
      protected String doInBackground(String[] paramArrayOfString)
      {
        String str1 = null;
        DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
        try
        {
          localHttpResponse = localDefaultHttpClient.execute(new HttpGet(paramArrayOfString[0]));
          if ((localHttpResponse == null) || (localHttpResponse.getStatusLine() == null) || (localHttpResponse.getStatusLine().getStatusCode() != 200))
          {
            Log.e("FeedFragment", "Bad HTTP response");
            return str1;
          }
        }
        catch (Exception localException)
        {
          while (true)
          {
            HttpResponse localHttpResponse;
            continue;
            HttpEntity localHttpEntity = localHttpResponse.getEntity();
            str1 = null;
            try
            {
              String str2 = EntityUtils.toString(localHttpEntity);
              str1 = ((JsonNode)this.val$objectMapper.readValue(str2, JsonNode.class)).get("media_id").asText();
              EntityUtils.consume(localHttpEntity);
            }
            catch (IOException localIOException)
            {
              localIOException.printStackTrace();
            }
          }
        }
      }

      protected void onPostExecute(String paramString)
      {
        FeedFragment.access$202(FeedFragment.this, paramString);
        FeedFragment.this.constructAndPerformFeedRequest(true, new FeedFragment.FeedRequestCallbacks(FeedFragment.this));
      }
    };
    String[] arrayOfString = new String[1];
    arrayOfString[0] = str;
    local8.execute(arrayOfString);
  }

  private boolean takeFlagScrollToTop()
  {
    boolean bool = sFlagScrollToTop;
    sFlagScrollToTop = false;
    return bool;
  }

  protected void addFooterViews(ListView paramListView)
  {
  }

  protected void addHeaderViews(ListView paramListView)
  {
  }

  protected void constructAndPerformFeedRequest(boolean paramBoolean, FeedRequestCallbacks paramFeedRequestCallbacks)
  {
    if (this.mIsLoading)
      Log.d("FeedFragment", "Is loading already set, not performing request");
    while (true)
    {
      return;
      this.lastUpdatedFeedTime = Long.valueOf(new Date().getTime());
      this.mFeedRequest = makeRequest(paramFeedRequestCallbacks);
      if (this.mFeedRequest == null)
        continue;
      paramFeedRequestCallbacks.setShouldClearOnAdd(paramBoolean);
      if (paramBoolean)
      {
        this.mFeedRequest.perform();
        continue;
      }
      this.mFeedRequest.performWithNewPage();
    }
  }

  public ActionBarConfigurer getActionBarConfigurer()
  {
    return new StandardFeedActionBar()
    {
      public View customTitleView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup)
      {
        return null;
      }
    };
  }

  protected FeedAdapter getAdapter()
  {
    if (this.mAdapter == null)
      this.mAdapter = new FeedAdapter(getActivity(), this, getDefaultFeedViewMode(), getGridViewPadding(), this);
    return this.mAdapter;
  }

  public CommentLinkClickListener getCommentLinkClickListener()
  {
    return new CommentLinkClickListener()
    {
      public void onClick(Media paramMedia)
      {
        CommentThreadFragment.show(FeedFragment.this.getFragmentManager(), paramMedia, false);
      }
    };
  }

  protected FeedAdapter.ViewMode getDefaultFeedViewMode()
  {
    return FeedAdapter.ViewMode.FEED;
  }

  public DoubleTapMediaListener getDoubleTapMediaListener()
  {
    return new DoubleTapMediaListener()
    {
      public void onDoubleTap(Media paramMedia)
      {
        if (!paramMedia.isHasLiked())
        {
          paramMedia.updatedHasLiked(true);
          new LikeRequest(FeedFragment.this.getActivity(), FeedFragment.this.getLoaderManager(), paramMedia, true, true).perform();
        }
      }
    };
  }

  protected FeedAdapter.GridViewPadding getGridViewPadding()
  {
    return FeedAdapter.GridViewPadding.LOOSE;
  }

  public String getMaxId()
  {
    return this.mMaxId;
  }

  public UserLinkClickListener getUserLinkClickListener()
  {
    return new UserLinkClickListener()
    {
      public void onClick(User paramUser)
      {
        Bundle localBundle = new Bundle();
        localBundle.putString("com.instagram.android.fragment.UserDetailFragment.EXTRA_USER_ID", paramUser.getId());
        FragmentUtil.navigateTo(FeedFragment.this.getFragmentManager(), new UserDetailFragment(), localBundle);
      }
    };
  }

  public boolean hasMoreItems()
  {
    return this.mMoreAvailable;
  }

  public boolean isLoadMoreVisible()
  {
    if ((this.mIsLoading) && (getAdapter().getFeedObjectsMediaCount() == 0));
    for (int i = 0; ; i = 1)
      return i;
  }

  public boolean isLoading()
  {
    return this.mIsLoading;
  }

  protected void loadDefaultContent()
  {
  }

  public void loadMore()
  {
    constructAndPerformFeedRequest(false, new FeedRequestCallbacks());
  }

  protected MediaFeedRequest makeRequest(AbstractStreamingApiCallbacks<MediaFeedResponse> paramAbstractStreamingApiCallbacks)
  {
    return new MediaFeedRequest(this, 0, paramAbstractStreamingApiCallbacks)
    {
      protected String getBaseFeedPath()
      {
        if (FeedFragment.this.mMediaId == null);
        Object[] arrayOfObject;
        for (String str = super.getPath(); ; str = String.format("media/%s/info/", arrayOfObject))
        {
          return str;
          arrayOfObject = new Object[1];
          arrayOfObject[0] = FeedFragment.access$200(FeedFragment.this);
        }
      }
    };
  }

  public void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    FeedAdapter localFeedAdapter = getAdapter();
    localFeedAdapter.setShouldPaginate(true);
    localFeedAdapter.setMediaSetChangeListener(new FeedAdapter.MediaSetChangeListener()
    {
      public void onMediaAdded(List<Media> paramList)
      {
        Iterator localIterator = paramList.iterator();
        while (localIterator.hasNext())
        {
          Media localMedia = (Media)localIterator.next();
          LocalBroadcastManager.getInstance(FeedFragment.this.getActivity()).registerReceiver(FeedFragment.this.mediaUpdateReceiver, new IntentFilter(Media.getMediaBroadcastString(localMedia.getId())));
        }
      }

      public void onMediaCleared()
      {
        LocalBroadcastManager.getInstance(FeedFragment.this.getActivity()).unregisterReceiver(FeedFragment.this.mediaUpdateReceiver);
      }
    });
    setListAdapter(localFeedAdapter);
    Media localMedia = null;
    boolean bool = false;
    String str = null;
    if (getArguments() != null)
    {
      this.mMediaId = getArguments().getString("com.instagram.android.fragment.ARGUMENTS_KEY_EXTRA_MEDIA_ID");
      str = getArguments().getString("com.instagram.android.fragment.ARGUMENTS_KEY_SHORT_URL");
      bool = getArguments().getBoolean("com.instagram.android.fragment.ARGUMENTS_KEY_LOAD_FROM_NETWORK");
      if ((this.mMediaId != null) || (str != null))
        localFeedAdapter.setShouldPaginate(false);
      if (this.mMediaId == null)
        break label132;
      if (!bool)
        localMedia = MediaStore.getInstance(getActivity()).get(this.mMediaId);
    }
    label132: label181: 
    while (true)
    {
      if (str != null);
      while (true)
      {
        return;
        if (str == null)
          break label181;
        resolveShortUrl(str);
        break;
        if ((localMedia == null) || (bool))
        {
          loadDefaultContent();
          constructAndPerformFeedRequest(true, new FeedRequestCallbacks());
          continue;
        }
        localFeedAdapter.addMedia(localMedia);
      }
    }
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(2130903087, paramViewGroup, false);
    ListView localListView = (ListView)localView.findViewById(16908298);
    addHeaderViews(localListView);
    addFooterViews(localListView);
    return localView;
  }

  public void onDestroy()
  {
    LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(this.mediaUpdateReceiver);
    super.onDestroy();
  }

  public void onDestroyView()
  {
    super.onDestroyView();
  }

  public void onDetach()
  {
    super.onDetach();
  }

  public void onPause()
  {
    super.onPause();
    LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(this.mBroadcastReceiver);
    getAdapter().setStickyScrollingEnabled(false);
    getAdapter().setAdapterRefreshedListener(null);
    ClickManager.getInstance().setUserLinkClickListener(null);
  }

  public void onResume()
  {
    super.onResume();
    if (takeFlagScrollToTop())
      scrollToTop();
    while (true)
    {
      LocalBroadcastManager localLocalBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
      localLocalBroadcastManager.registerReceiver(this.mBroadcastReceiver, new IntentFilter("com.instagram.android.service.action_bar_clicked"));
      localLocalBroadcastManager.registerReceiver(this.mBroadcastReceiver, new IntentFilter("com.instagram.android.service.action_bar_refresh_clicked"));
      getAdapter().setAdapterRefreshedListener(new FeedAdapter.AdapterRefreshedListener()
      {
        public void refreshed()
        {
          FeedFragment.this.getListView().post(new FeedFragment.3.1(this));
        }
      });
      ClickManager.getInstance().setUserLinkClickListener(getUserLinkClickListener());
      return;
      getAdapter().resetStickyHeader();
      getAdapter().setStickyScrollingEnabled(true);
      getAdapter().redrawStickyHeader(getListView());
    }
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    if (Build.VERSION.SDK_INT < 14)
      getListView().setScrollingCacheEnabled(false);
    getListView().setOnScrollListener(getAdapter());
    updateProgressBarState();
  }

  public void postDelayed(Runnable paramRunnable, long paramLong)
  {
    this.mHandler.postDelayed(paramRunnable, paramLong);
  }

  public void refreshStickyHeaderDelayed()
  {
    this.mHandler.postDelayed(new Runnable()
    {
      public void run()
      {
        if (FeedFragment.this.getView() != null)
        {
          FeedFragment.this.getAdapter().resetStickyHeader();
          FeedFragment.this.getAdapter().redrawStickyHeader(FeedFragment.this.getListView());
        }
      }
    }
    , 100L);
  }

  public void scrollToTop()
  {
    if ((getView() == null) || (getListView().getCount() == 0) || (getListView().getHeight() == 0));
    label150: 
    while (true)
    {
      return;
      getAdapter().setStickyScrollingEnabled(false);
      if ((getAdapter().getCount() > 1) && (getListView().getFirstVisiblePosition() != 0))
      {
        setSelection(1);
        getListView().smoothScrollToPosition(0);
        getListView().postDelayed(new Runnable()
        {
          public void run()
          {
            if (FeedFragment.this.getView() != null)
            {
              FeedFragment.this.getListView().smoothScrollBy(0, 0);
              FeedFragment.this.getListView().setSelection(0);
              FeedFragment.this.getListView().postDelayed(new FeedFragment.5.1(this), 100L);
            }
          }
        }
        , 100L);
        continue;
      }
      if (getListView().getFirstVisiblePosition() != 0)
      {
        setSelection(0);
        getListView().smoothScrollToPosition(0);
      }
      while (true)
      {
        if (getView() == null)
          break label150;
        getListView().postDelayed(new Runnable()
        {
          public void run()
          {
            if (FeedFragment.this.getView() != null)
            {
              FeedFragment.this.getAdapter().setStickyScrollingEnabled(true);
              FeedFragment.this.getAdapter().onScroll(FeedFragment.this.getListView(), 0, 0, 0);
            }
          }
        }
        , 100L);
        break;
        setSelection(0);
      }
    }
  }

  protected void updateProgressBarState()
  {
    if ((this.mIsLoading) && (getAdapter().isEmpty()) && (!this.mLoadingDefaultContent))
      if (getView() != null)
      {
        View localView2 = getView().findViewById(2131492983);
        localView2.startAnimation(AnimationUtils.loadAnimation(AppContext.getContext(), 2130968585));
        localView2.setVisibility(0);
      }
    while (true)
    {
      return;
      if (getView() != null)
      {
        View localView1 = getView().findViewById(2131492983);
        localView1.setVisibility(8);
        localView1.clearAnimation();
        continue;
      }
    }
  }

  protected class FeedRequestCallbacks extends AbstractStreamingApiCallbacks<MediaFeedResponse>
  {
    private boolean mClearOnAdd;

    protected FeedRequestCallbacks()
    {
    }

    private void setShouldClearOnAdd(boolean paramBoolean)
    {
      this.mClearOnAdd = paramBoolean;
    }

    protected void onRequestFail(ApiResponse<MediaFeedResponse> paramApiResponse)
    {
      FeedFragment.this.mInitialNetworkRequestFinished = true;
      Toast.makeText(FeedFragment.this.getActivity(), 2131230882, 0).show();
      FeedFragment.this.getAdapter().notifyDataSetChanged();
      FeedFragment.this.getAdapter().resetStickyHeader();
      super.onRequestFail(paramApiResponse);
    }

    public void onRequestFinished()
    {
      FeedFragment.access$002(FeedFragment.this, false);
      FeedFragment.this.mInitialNetworkRequestFinished = true;
      ActionBarService.getInstance(FeedFragment.this.getActivity()).setIsLoading(false);
      FeedFragment.this.getAdapter().resetStickyHeader();
      FeedFragment.this.updateProgressBarState();
    }

    public void onRequestStart()
    {
      FeedFragment.access$002(FeedFragment.this, true);
      ActionBarService.getInstance(FeedFragment.this.getActivity()).setIsLoading(true);
      FeedFragment.this.updateProgressBarState();
    }

    protected void onSuccess(MediaFeedResponse paramMediaFeedResponse)
    {
      if (this.mClearOnAdd)
        FeedFragment.this.getAdapter().clearMedia();
      this.mClearOnAdd = false;
      FeedFragment.access$402(FeedFragment.this, paramMediaFeedResponse.isMoreAvailable());
      FeedFragment.access$502(FeedFragment.this, paramMediaFeedResponse.getNextMaxId());
      FeedFragment.this.getAdapter().addMedia(paramMediaFeedResponse.getItems());
    }
  }

  protected class StandardFeedActionBar
    implements ActionBarConfigurer
  {
    protected StandardFeedActionBar()
    {
    }

    public View customTitleView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup)
    {
      return null;
    }

    public String getTitle()
    {
      return null;
    }

    public boolean isLoading()
    {
      return FeedFragment.this.mIsLoading;
    }

    public boolean showBackButton()
    {
      if (FeedFragment.this.getFragmentManager().getBackStackEntryCount() > 0);
      for (int i = 1; ; i = 0)
        return i;
    }

    public boolean showRefreshButton()
    {
      return true;
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.FeedFragment
 * JD-Core Version:    0.6.0
 */
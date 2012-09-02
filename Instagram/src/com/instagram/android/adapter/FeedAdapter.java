package com.instagram.android.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import com.instagram.android.InstagramApplication;
import com.instagram.android.Preferences;
import com.instagram.android.adapter.row.FeedImageRowAdapter;
import com.instagram.android.adapter.row.FeedImageRowAdapter.Builder;
import com.instagram.android.adapter.row.FeedImageRowAdapter.HeaderHolder;
import com.instagram.android.adapter.row.FeedImageRowAdapter.Holder;
import com.instagram.android.adapter.row.LoadMoreRowAdapter;
import com.instagram.android.adapter.row.LoadMoreRowAdapter.Holder;
import com.instagram.android.adapter.row.MediaSetRowAdapter;
import com.instagram.android.adapter.row.MediaSetRowAdapter.Holder;
import com.instagram.android.fragment.FeedFragment;
import com.instagram.android.imagecache.IgBitmapCache;
import com.instagram.android.listener.CommentLinkClickListener;
import com.instagram.android.listener.DoubleTapMediaListener;
import com.instagram.android.listener.UserLinkClickListener;
import com.instagram.android.model.Media;
import com.instagram.android.service.AppContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FeedAdapter extends BaseAdapter
  implements ListAdapter, AbsListView.OnScrollListener
{
  private static final int ITEM_TYPE_LOAD_MORE_ROW = 2;
  private static final int ITEM_TYPE_MEDIA = 0;
  private static final int ITEM_TYPE_POPULAR_ROW = 1;
  private static final int NUM_VIEW_TYPES = 3;
  public static final int READ_AHEAD_COUNT = 3;
  private static final String TAG = "FeedAdapter";
  private int lastQueuedDownPosition = -1;
  private int lastQueuedUpPosition = -1;
  private AdapterRefreshedListener mAdapterRefreshedListener;
  private Context mContext;
  private int mCurrentItemPositionOfStickyHeader;
  private Media mCurrentMediaOfStickyHeader;
  protected FeedFragment mFeedFragment;
  private FeedImageRowAdapter mFeedImageRowAdapter;
  private FeedObjects mFeedObjects = new FeedObjects(null);
  protected FragmentManager mFragmentManager;
  private GridViewPadding mGridViewPadding = GridViewPadding.LOOSE;
  protected LoaderManager mLoadManager;
  private boolean mLoggedIn = false;
  private MediaSetChangeListener mMediaSetChangeListener;
  private boolean mShouldPaginate;
  private boolean mStickyScrollingEnabled;
  private ViewMode mViewMode = ViewMode.FEED;
  private ViewTypeChangeListener mViewTypeChangeListener;
  private int previousItem = 0;

  public FeedAdapter(Context paramContext, ListenerDelegate paramListenerDelegate, ViewMode paramViewMode, GridViewPadding paramGridViewPadding, FeedFragment paramFeedFragment)
  {
    this.mContext = paramContext;
    this.mLoadManager = paramFeedFragment.getLoaderManager();
    this.mFeedFragment = paramFeedFragment;
    this.mLoggedIn = Preferences.getInstance(paramContext).isLoggedIn();
    this.mFragmentManager = paramFeedFragment.getFragmentManager();
    this.mViewMode = paramViewMode;
    this.mGridViewPadding = paramGridViewPadding;
    this.mStickyScrollingEnabled = true;
    getDoubleTapAnimation();
    this.mFeedImageRowAdapter = new FeedImageRowAdapter.Builder(paramContext).setActivity(paramFeedFragment.getActivity()).setFragmentManager(paramFeedFragment.getFragmentManager()).setLoaderManager(paramFeedFragment.getLoaderManager()).setImageViewOnClickListener(new DoubleTapGestureListener(paramListenerDelegate.getDoubleTapMediaListener())).create();
  }

  private Animation getDoubleTapAnimation()
  {
    Animation localAnimation = AnimationUtils.loadAnimation(AppContext.getContext(), 2130968580);
    localAnimation.reset();
    return localAnimation;
  }

  private ArrayList<Media> getMediaList(int paramInt)
  {
    return this.mFeedObjects.getMediaList(paramInt - getChildCount());
  }

  private View getStickyHeader()
  {
    View localView = null;
    if ((this.mFeedFragment != null) && (this.mFeedFragment.getView() != null))
      localView = this.mFeedFragment.getView().findViewById(2131492982);
    return localView;
  }

  @TargetApi(11)
  private void performStickyHeader(AbsListView paramAbsListView, int paramInt)
  {
    View localView1 = getStickyHeader();
    if ((localView1 == null) || (!InstagramApplication.getStickyHeaderSupported()))
      return;
    this.mCurrentItemPositionOfStickyHeader = paramInt;
    int i = paramInt + getChildCount();
    View localView2;
    View localView3;
    float f;
    if ((isValidItemTypeForStickyHeader(paramInt)) && (paramAbsListView.getChildCount() > 0))
    {
      Object localObject = getItem(i);
      if ((localObject != null) && ((localObject instanceof Media)) && (this.mFeedFragment != null))
      {
        localView2 = paramAbsListView.getChildAt(0);
        localView3 = localView2.findViewById(2131493028);
        if (localView3 != null)
        {
          Media localMedia = (Media)localObject;
          FeedImageRowAdapter.HeaderHolder localHeaderHolder = FeedImageRowAdapter.getHeaderHolder(localView1);
          localView1.setVisibility(0);
          int j = localView2.getHeight() + localView2.getTop();
          if (j >= localView1.getHeight())
            break label272;
          localView1.clearAnimation();
          f = 0 - (localView1.getHeight() - j);
          if (Build.VERSION.SDK_INT < 11)
            break label216;
          localView1.setY(f);
          label164: localView3.setVisibility(0);
          label170: if (this.mCurrentMediaOfStickyHeader != localMedia)
          {
            this.mFeedImageRowAdapter.bindProfileHeader(localHeaderHolder, localMedia);
            ((View)localView1.getParent()).postInvalidate();
            this.mCurrentMediaOfStickyHeader = localMedia;
          }
        }
      }
    }
    while (true)
    {
      setVisibilityOfProfileHeaderAtIndex(1, paramAbsListView, 0);
      break;
      label216: TranslateAnimation localTranslateAnimation = new TranslateAnimation(0, 0.0F, 0, 0.0F, 0, f, 0, f);
      localTranslateAnimation.setDuration(0L);
      localView1.clearAnimation();
      localTranslateAnimation.setFillAfter(true);
      localTranslateAnimation.setFillBefore(true);
      localTranslateAnimation.setFillEnabled(true);
      localView1.startAnimation(localTranslateAnimation);
      break label164;
      label272: if (Build.VERSION.SDK_INT >= 11)
        localView1.setY(0.0F);
      while (true)
      {
        if (localView2.getTop() == 0)
          break label314;
        localView3.setVisibility(4);
        localView1.setVisibility(0);
        break;
        localView1.clearAnimation();
      }
      label314: localView3.setVisibility(0);
      localView1.setVisibility(8);
      break label170;
      localView1.setVisibility(8);
      continue;
      if (localView1 == null)
        continue;
      localView1.setVisibility(8);
    }
  }

  private void preDecodeImage(Context paramContext, int paramInt)
  {
    Object localObject = null;
    if ((paramInt > -1) && (paramInt < getCount()))
      localObject = getItem(paramInt);
    if ((paramInt > -1) && (paramInt < getCount()) && (localObject != null) && ((localObject instanceof Media)))
      IgBitmapCache.getInstance(this.mContext).warm(((Media)(Media)localObject).getSizedUrl());
  }

  private void setVisibilityOfProfileHeaderAtIndex(int paramInt1, AbsListView paramAbsListView, int paramInt2)
  {
    if (paramAbsListView.getChildCount() > paramInt1)
    {
      View localView = paramAbsListView.getChildAt(paramInt1).findViewById(2131493028);
      if (localView != null)
        localView.setVisibility(paramInt2);
    }
  }

  private void testDecode(AbsListView paramAbsListView, int paramInt)
  {
    if (paramInt > this.previousItem)
    {
      this.lastQueuedUpPosition = -1;
      for (int j = 1; j < 3; j++)
        testIfShouldDecodeDown(paramAbsListView.getLastVisiblePosition(), j);
      this.lastQueuedDownPosition = (2 + paramAbsListView.getLastVisiblePosition());
    }
    while (true)
    {
      this.previousItem = paramInt;
      return;
      if (paramInt >= this.previousItem)
        continue;
      this.lastQueuedDownPosition = -1;
      for (int i = 1; i < 3; i++)
        testIfShouldDecodeUp(paramInt, i);
      this.lastQueuedUpPosition = (paramInt - 2);
    }
  }

  private void testIfShouldDecodeDown(int paramInt1, int paramInt2)
  {
    if ((this.lastQueuedDownPosition == -1) || (paramInt1 + paramInt2 > this.lastQueuedDownPosition))
      preDecodeImage(this.mContext, paramInt1 + paramInt2);
  }

  private void testIfShouldDecodeUp(int paramInt1, int paramInt2)
  {
    if ((this.lastQueuedUpPosition == -1) || (paramInt1 - paramInt2 < this.lastQueuedUpPosition))
      preDecodeImage(this.mContext, paramInt1 - paramInt2);
  }

  public void addMedia(Media paramMedia)
  {
    addMedia(paramMedia, false);
  }

  public void addMedia(Media paramMedia, boolean paramBoolean)
  {
    if (paramMedia == null);
    while (true)
    {
      return;
      ArrayList localArrayList = new ArrayList(1);
      localArrayList.add(paramMedia);
      addMedia(localArrayList, paramBoolean);
    }
  }

  public void addMedia(ArrayList<Media> paramArrayList)
  {
    addMedia(paramArrayList, false);
  }

  public void addMedia(ArrayList<Media> paramArrayList, boolean paramBoolean)
  {
    this.mFeedObjects.addMedia(paramArrayList, paramBoolean);
    notifyDataSetChanged();
  }

  protected int adjustChildViewType(int paramInt)
  {
    return paramInt + 3;
  }

  protected int adjustedItemPosition(int paramInt)
  {
    return paramInt - getChildCount();
  }

  public boolean areAllItemsEnabled()
  {
    return false;
  }

  protected void bindView(Context paramContext, View paramView, int paramInt)
  {
    boolean bool;
    switch (getItemViewType(paramInt))
    {
    default:
      throw new UnsupportedOperationException("View type unhandled");
    case 0:
      if (this.mLoggedIn)
        break;
      bool = true;
      this.mFeedImageRowAdapter.bindView((FeedImageRowAdapter.Holder)paramView.getTag(), (Media)getItem(paramInt), paramInt, bool);
    case 1:
    case 2:
    }
    while (true)
    {
      return;
      bool = false;
      break;
      MediaSetRowAdapter.bindView((MediaSetRowAdapter.Holder)paramView.getTag(), getMediaList(paramInt), paramContext);
      continue;
      LoadMoreRowAdapter.bindView((LoadMoreRowAdapter.Holder)paramView.getTag(), this.mFeedFragment);
    }
  }

  public void clearMedia()
  {
    this.mFeedObjects.clearMedia();
    notifyDataSetChanged();
  }

  protected View createView(Context paramContext, int paramInt, ViewGroup paramViewGroup)
  {
    View localView;
    switch (getItemViewType(paramInt))
    {
    default:
      throw new UnsupportedOperationException("Unhanlded view type");
    case 0:
      localView = this.mFeedImageRowAdapter.newView();
    case 1:
    case 2:
    }
    while (true)
    {
      return localView;
      localView = MediaSetRowAdapter.newView(paramContext, this.mGridViewPadding);
      continue;
      localView = LoadMoreRowAdapter.newView(paramContext, paramViewGroup);
    }
  }

  public int getChildCount()
  {
    return 0;
  }

  public int getCount()
  {
    int i = getChildCount() + this.mFeedObjects.getCount();
    if (this.mShouldPaginate)
      i++;
    return i;
  }

  public int getFeedObjectsMediaCount()
  {
    return this.mFeedObjects.getMediaCount();
  }

  public Object getItem(int paramInt)
  {
    int i = paramInt - getChildCount();
    if (i < this.mFeedObjects.getCount());
    for (Media localMedia = this.mFeedObjects.getMedia(i); ; localMedia = null)
      return localMedia;
  }

  public long getItemId(int paramInt)
  {
    return 0L;
  }

  public int getItemViewType(int paramInt)
  {
    int i;
    if (paramInt - getChildCount() == this.mFeedObjects.getCount())
      i = 2;
    while (true)
    {
      return i;
      if (this.mViewMode == ViewMode.FEED)
      {
        i = 0;
        continue;
      }
      if (this.mViewMode != ViewMode.GRID)
        break;
      i = 1;
    }
    throw new UnsupportedOperationException("View mode not handled (for item view type)");
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    if (paramView == null)
      paramView = createView(this.mContext, paramInt, paramViewGroup);
    bindView(this.mContext, paramView, paramInt);
    return paramView;
  }

  public ViewMode getViewMode()
  {
    return this.mViewMode;
  }

  public int getViewTypeCount()
  {
    return 3;
  }

  public boolean hasMedia()
  {
    return this.mFeedObjects.hasMedia();
  }

  public boolean hasStableIds()
  {
    return true;
  }

  public boolean isEmpty()
  {
    return this.mFeedObjects.isEmpty();
  }

  public boolean isEnabled(int paramInt)
  {
    return false;
  }

  protected boolean isValidItemTypeForStickyHeader(int paramInt)
  {
    if (getItemViewType(paramInt + getChildCount()) == 0);
    for (int i = 1; ; i = 0)
      return i;
  }

  public void notifyDataSetChanged()
  {
    this.mFeedObjects.filterMedia();
    resetStickyHeader();
    super.notifyDataSetChanged();
    this.mFeedFragment.refreshStickyHeaderDelayed();
  }

  public void notifyDataSetInvalidated()
  {
    this.mFeedObjects.filterMedia();
    resetStickyHeader();
    super.notifyDataSetInvalidated();
    this.mFeedFragment.refreshStickyHeaderDelayed();
  }

  public void onHeaderScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3)
  {
    setVisibilityOfProfileHeaderAtIndex(0, paramAbsListView, 0);
    setVisibilityOfProfileHeaderAtIndex(1, paramAbsListView, 0);
    View localView = getStickyHeader();
    if (localView != null)
      localView.setVisibility(8);
  }

  public void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3)
  {
    if ((this.mViewMode != ViewMode.FEED) || (!this.mStickyScrollingEnabled));
    while (true)
    {
      return;
      performStickyHeader(paramAbsListView, paramInt1);
    }
  }

  public void onScrollStateChanged(AbsListView paramAbsListView, int paramInt)
  {
    if ((paramInt == 0) && (this.mViewMode == ViewMode.FEED))
      testDecode(paramAbsListView, paramAbsListView.getFirstVisiblePosition());
  }

  public void redrawStickyHeader(AbsListView paramAbsListView)
  {
    performStickyHeader(paramAbsListView, this.mCurrentItemPositionOfStickyHeader);
  }

  public void resetStickyHeader()
  {
    this.mCurrentMediaOfStickyHeader = null;
    View localView = getStickyHeader();
    if (localView != null)
      localView.setVisibility(8);
  }

  public void setAdapterRefreshedListener(AdapterRefreshedListener paramAdapterRefreshedListener)
  {
    this.mAdapterRefreshedListener = paramAdapterRefreshedListener;
  }

  public void setMediaSetChangeListener(MediaSetChangeListener paramMediaSetChangeListener)
  {
    this.mMediaSetChangeListener = paramMediaSetChangeListener;
  }

  public void setShouldPaginate(boolean paramBoolean)
  {
    this.mShouldPaginate = paramBoolean;
  }

  public void setStickyScrollingEnabled(boolean paramBoolean)
  {
    this.mStickyScrollingEnabled = paramBoolean;
    if (!paramBoolean)
    {
      View localView = getStickyHeader();
      if (localView != null)
        localView.setVisibility(8);
    }
  }

  public void setViewTypeChangeListener(ViewTypeChangeListener paramViewTypeChangeListener)
  {
    this.mViewTypeChangeListener = paramViewTypeChangeListener;
  }

  public void switchMode()
  {
    ViewMode localViewMode1 = this.mViewMode;
    if (localViewMode1 == ViewMode.FEED);
    for (ViewMode localViewMode2 = ViewMode.GRID; ; localViewMode2 = ViewMode.FEED)
    {
      this.mViewMode = localViewMode2;
      if (localViewMode2 == ViewMode.GRID)
      {
        View localView = getStickyHeader();
        if (localView != null)
          localView.setVisibility(8);
      }
      this.mViewTypeChangeListener.onChange(localViewMode1, localViewMode2);
      this.mFeedObjects.preloadBitmaps();
      notifyDataSetChanged();
      return;
    }
  }

  public static abstract interface AdapterRefreshedListener
  {
    public abstract void refreshed();
  }

  private class DoubleTapGestureListener
    implements View.OnTouchListener
  {
    private GestureDetector mGestureDetector;
    private DoubleTapMediaListener mListener;
    private View touchedView;

    public DoubleTapGestureListener(DoubleTapMediaListener arg2)
    {
      Object localObject;
      this.mListener = localObject;
      this.mGestureDetector = new GestureDetector(new DoubleTapGestureDetector());
    }

    public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
    {
      this.touchedView = paramView;
      return this.mGestureDetector.onTouchEvent(paramMotionEvent);
    }

    class DoubleTapGestureDetector extends GestureDetector.SimpleOnGestureListener
    {
      DoubleTapGestureDetector()
      {
      }

      public boolean onDoubleTap(MotionEvent paramMotionEvent)
      {
        if (FeedAdapter.DoubleTapGestureListener.this.mListener != null)
        {
          int i = ((Integer)FeedAdapter.DoubleTapGestureListener.this.touchedView.getTag(2131492864)).intValue();
          Media localMedia = (Media)FeedAdapter.this.getItem(i);
          FeedAdapter.DoubleTapGestureListener.this.mListener.onDoubleTap(localMedia);
          Preferences.getInstance(FeedAdapter.this.mContext).setHasUsedDoubleTapToLike(true);
          FeedAdapter.this.mFeedFragment.postDelayed(new FeedAdapter.DoubleTapGestureListener.DoubleTapGestureDetector.1(this, localMedia), 100L);
        }
        return super.onDoubleTap(paramMotionEvent);
      }

      public boolean onDown(MotionEvent paramMotionEvent)
      {
        return true;
      }
    }
  }

  private class FeedObjects
  {
    private static final int NUM_MEDIA_PER_ROW = 4;
    private final ArrayList<Media> mFilteredMediaList = new ArrayList();
    private final ArrayList<Media> mMediaList = new ArrayList();
    private final Map<String, Media> mMediaMap = new HashMap();
    private boolean mMediaRecentlyCleared = false;

    private FeedObjects()
    {
    }

    private int getMediaCount()
    {
      return this.mFilteredMediaList.size();
    }

    private void preloadBitmapInteral(Media paramMedia)
    {
      if (FeedAdapter.this.getViewMode() == FeedAdapter.ViewMode.FEED)
      {
        IgBitmapCache localIgBitmapCache2 = IgBitmapCache.getInstance(FeedAdapter.this.mContext);
        String[] arrayOfString2 = new String[1];
        arrayOfString2[0] = paramMedia.getSizedUrl();
        localIgBitmapCache2.preLoadBitmaps(arrayOfString2);
      }
      while (true)
      {
        return;
        IgBitmapCache localIgBitmapCache1 = IgBitmapCache.getInstance(FeedAdapter.this.mContext);
        String[] arrayOfString1 = new String[1];
        arrayOfString1[0] = paramMedia.getThumbnailUrl();
        localIgBitmapCache1.preLoadBitmaps(arrayOfString1);
      }
    }

    private void preloadBitmaps()
    {
      Iterator localIterator = this.mMediaList.iterator();
      while (localIterator.hasNext())
        preloadBitmapInteral((Media)localIterator.next());
    }

    public void addMedia(ArrayList<Media> paramArrayList, boolean paramBoolean)
    {
      int i = 0;
      int j = 0;
      if (paramArrayList != null)
      {
        Iterator localIterator = paramArrayList.iterator();
        while (localIterator.hasNext())
        {
          Media localMedia = (Media)localIterator.next();
          String str = localMedia.getId();
          if ((Media)this.mMediaMap.get(str) != null)
            continue;
          this.mMediaMap.put(str, localMedia);
          if (paramBoolean)
          {
            ArrayList localArrayList = this.mMediaList;
            int k = j + 1;
            localArrayList.add(j, localMedia);
            j = k;
          }
          while (true)
          {
            preloadBitmapInteral(localMedia);
            i = 1;
            break;
            this.mMediaList.add(localMedia);
          }
        }
      }
      if ((i != 0) && (FeedAdapter.this.mMediaSetChangeListener != null))
        FeedAdapter.this.mMediaSetChangeListener.onMediaAdded(this.mMediaList);
      if ((this.mMediaRecentlyCleared) && (FeedAdapter.this.mAdapterRefreshedListener != null))
      {
        this.mMediaRecentlyCleared = false;
        FeedAdapter.this.mAdapterRefreshedListener.refreshed();
      }
    }

    public void clearMedia()
    {
      this.mMediaRecentlyCleared = true;
      this.mMediaMap.clear();
      this.mMediaList.clear();
      this.mFilteredMediaList.clear();
      if (FeedAdapter.this.mMediaSetChangeListener != null)
        FeedAdapter.this.mMediaSetChangeListener.onMediaCleared();
    }

    public void filterMedia()
    {
      this.mFilteredMediaList.clear();
      Iterator localIterator = this.mMediaList.iterator();
      while (localIterator.hasNext())
      {
        Media localMedia = (Media)localIterator.next();
        if (localMedia.getDeletedStatus() != 0)
          continue;
        this.mFilteredMediaList.add(localMedia);
      }
    }

    public int getCount()
    {
      if (FeedAdapter.this.mViewMode == FeedAdapter.ViewMode.FEED);
      for (int i = this.mFilteredMediaList.size(); ; i = (int)Math.ceil(this.mFilteredMediaList.size() / 4.0D))
      {
        return i;
        if (FeedAdapter.this.mViewMode != FeedAdapter.ViewMode.GRID)
          break;
      }
      throw new UnsupportedOperationException("View mode not handled");
    }

    public Media getMedia(int paramInt)
    {
      return (Media)this.mFilteredMediaList.get(paramInt);
    }

    public ArrayList<Media> getMediaList(int paramInt)
    {
      ArrayList localArrayList = new ArrayList();
      int i = paramInt * 4;
      for (int j = 0; j < 4; j++)
      {
        int k = i + j;
        if (k >= getMediaCount())
          continue;
        localArrayList.add(getMedia(k));
      }
      return localArrayList;
    }

    public boolean hasMedia()
    {
      if (this.mFilteredMediaList.size() > 0);
      for (int i = 1; ; i = 0)
        return i;
    }

    public boolean isEmpty()
    {
      if (getCount() == 0);
      for (int i = 1; ; i = 0)
        return i;
    }
  }

  public static enum GridViewPadding
  {
    static
    {
      LOOSE = new GridViewPadding("LOOSE", 1);
      GridViewPadding[] arrayOfGridViewPadding = new GridViewPadding[2];
      arrayOfGridViewPadding[0] = TIGHT;
      arrayOfGridViewPadding[1] = LOOSE;
      $VALUES = arrayOfGridViewPadding;
    }
  }

  public static abstract interface ListenerDelegate
  {
    public abstract CommentLinkClickListener getCommentLinkClickListener();

    public abstract DoubleTapMediaListener getDoubleTapMediaListener();

    public abstract UserLinkClickListener getUserLinkClickListener();
  }

  public static abstract interface MediaSetChangeListener
  {
    public abstract void onMediaAdded(List<Media> paramList);

    public abstract void onMediaCleared();
  }

  public static enum ViewMode
  {
    static
    {
      ViewMode[] arrayOfViewMode = new ViewMode[2];
      arrayOfViewMode[0] = FEED;
      arrayOfViewMode[1] = GRID;
      $VALUES = arrayOfViewMode;
    }
  }

  public static abstract interface ViewTypeChangeListener
  {
    public abstract void onChange(FeedAdapter.ViewMode paramViewMode1, FeedAdapter.ViewMode paramViewMode2);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.adapter.FeedAdapter
 * JD-Core Version:    0.6.0
 */
package com.instagram.android.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import com.ijsbrandslob.appirater.Appirater;
import com.instagram.android.Log;
import com.instagram.android.adapter.FeedAdapter;
import com.instagram.android.adapter.MainFeedAdapter;
import com.instagram.android.model.Media;
import com.instagram.android.model.MediaFeedResponse;
import com.instagram.android.service.AppContext;
import com.instagram.android.service.MediaStore;
import com.instagram.android.service.PendingMediaService;
import com.instagram.android.service.PendingMediaService.ServiceBinder;
import com.instagram.api.AbstractStreamingApiCallbacks;
import com.instagram.api.request.MediaFeedRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MainFeedFragment extends FeedFragment
{
  public static final String CACHED_FILENAME = "MainFeed.json";
  public static final int FIVE_MINUTES = 300000;
  private static final int HANDLER_MESSAGE_PLACE_NEW_MEDIA = 0;
  public static final int ITEMS_TO_LOAD_FROM_CACHE = 5;
  private static final String LOG_TAG = "MainFeedFragment";
  private static final String MESSAGE_DATA_NEW_MEDIA_ID = "new_media_id";
  private static final String MESSAGE_DATA_PENDING_MEDIA_KEY = "pending_media_key";
  public static final int PLACE_NEW_MEDIA_DELAY_MILLIS = 1000;
  public static final String TAG = "MainFeedFragment";
  private static boolean sLoadedDefaultContent;
  protected MainFeedAdapter mAdapter;
  private BroadcastReceiver mAppAliveReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramContext, Intent paramIntent)
    {
      if ((MainFeedFragment.this.lastUpdatedFeedTime != null) && (Math.abs(new Date().getTime() - MainFeedFragment.this.lastUpdatedFeedTime.longValue()) > 300000L))
        MainFeedFragment.this.constructAndPerformFeedRequest(true, new MainFeedFragment.1.1(this));
    }
  };
  private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramContext, Intent paramIntent)
    {
      String str1 = paramIntent.getAction();
      if (str1.equals("com.instagram.android.PendingMediaService.INTENT_ACTION_PENDING_MEDIA_CHANGED"))
        MainFeedFragment.this.syncPendingMedia();
      while (true)
      {
        return;
        if (!str1.equals("com.instagram.android.PendingMediaService.INTENT_ACTION_NEW_USER_MEDIA"))
          break;
        MainFeedFragment.this.getAdapter().notifyDataSetChanged();
        String str2 = paramIntent.getStringExtra("mediaId");
        String str3 = paramIntent.getStringExtra("pendingMediaKey");
        MainFeedFragment.this.placeNewMediaAfterDelay(str2, str3);
      }
      throw new UnsupportedOperationException("Unhandled intent action");
    }
  };
  private Map<String, String> mDelayedNewMedia = new HashMap();
  private Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramMessage)
    {
      switch (paramMessage.what)
      {
      default:
      case 0:
      }
      while (true)
      {
        return;
        String str1 = paramMessage.getData().getString("pending_media_key");
        String str2 = paramMessage.getData().getString("new_media_id");
        MainFeedFragment.this.placeNewMedia(str1, str2);
        new Appirater(MainFeedFragment.this.getActivity()).userDidSignificantEvent(true);
      }
    }
  };
  private PendingMediaService mPendingMediaService;
  private PendingMediaServiceConnection mPendingMediaServiceConnection;

  public static File getCachedHomeFeedFile(File paramFile)
  {
    return new File(paramFile, "MainFeed.json");
  }

  private void placeNewMedia(String paramString1, String paramString2)
  {
    PendingMediaService.removeMedia(paramString1);
    Media localMedia = MediaStore.getInstance(getActivity()).get(paramString2);
    getAdapter().resetStickyHeader();
    getAdapter().addMedia(localMedia, true);
    UserDetailFragment.setCurrentUserNeedsReload();
    if (this.mDelayedNewMedia.containsKey(paramString1))
      this.mDelayedNewMedia.remove(paramString1);
  }

  private void placeNewMediaAfterDelay(String paramString1, String paramString2)
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("new_media_id", paramString1);
    localBundle.putString("pending_media_key", paramString2);
    Message localMessage = this.mHandler.obtainMessage(0);
    localMessage.setData(localBundle);
    this.mHandler.sendMessageDelayed(localMessage, 1000L);
    this.mDelayedNewMedia.put(paramString2, paramString1);
  }

  // ERROR //
  private void readFromCache(File paramFile, long paramLong)
    throws IOException
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 4
    //   3: iconst_0
    //   4: istore 5
    //   6: new 193	org/codehaus/jackson/JsonFactory
    //   9: dup
    //   10: invokespecial 194	org/codehaus/jackson/JsonFactory:<init>	()V
    //   13: astore 6
    //   15: new 196	java/io/FileInputStream
    //   18: dup
    //   19: aload_1
    //   20: invokespecial 199	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   23: astore 7
    //   25: aload 6
    //   27: new 201	java/io/BufferedInputStream
    //   30: dup
    //   31: aload 7
    //   33: invokespecial 204	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   36: invokevirtual 208	org/codehaus/jackson/JsonFactory:createJsonParser	(Ljava/io/InputStream;)Lorg/codehaus/jackson/JsonParser;
    //   39: astore 9
    //   41: aload 9
    //   43: invokevirtual 214	org/codehaus/jackson/JsonParser:nextToken	()Lorg/codehaus/jackson/JsonToken;
    //   46: getstatic 220	org/codehaus/jackson/JsonToken:END_OBJECT	Lorg/codehaus/jackson/JsonToken;
    //   49: if_acmpeq +15 -> 64
    //   52: aload 9
    //   54: invokevirtual 223	org/codehaus/jackson/JsonParser:getCurrentToken	()Lorg/codehaus/jackson/JsonToken;
    //   57: astore 11
    //   59: aload 11
    //   61: ifnonnull +35 -> 96
    //   64: aload 7
    //   66: ifnull +8 -> 74
    //   69: aload 7
    //   71: invokevirtual 226	java/io/FileInputStream:close	()V
    //   74: iload 5
    //   76: ifne +19 -> 95
    //   79: aload_0
    //   80: getfield 79	com/instagram/android/fragment/MainFeedFragment:mHandler	Landroid/os/Handler;
    //   83: new 18	com/instagram/android/fragment/MainFeedFragment$7
    //   86: dup
    //   87: aload_0
    //   88: invokespecial 227	com/instagram/android/fragment/MainFeedFragment$7:<init>	(Lcom/instagram/android/fragment/MainFeedFragment;)V
    //   91: invokevirtual 231	android/os/Handler:post	(Ljava/lang/Runnable;)Z
    //   94: pop
    //   95: return
    //   96: ldc 233
    //   98: aload 9
    //   100: invokevirtual 237	org/codehaus/jackson/JsonParser:getCurrentName	()Ljava/lang/String;
    //   103: invokevirtual 242	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   106: ifeq -65 -> 41
    //   109: aload 9
    //   111: invokevirtual 214	org/codehaus/jackson/JsonParser:nextToken	()Lorg/codehaus/jackson/JsonToken;
    //   114: pop
    //   115: new 244	java/util/ArrayList
    //   118: dup
    //   119: invokespecial 245	java/util/ArrayList:<init>	()V
    //   122: astore 13
    //   124: aload 9
    //   126: invokevirtual 214	org/codehaus/jackson/JsonParser:nextToken	()Lorg/codehaus/jackson/JsonToken;
    //   129: getstatic 248	org/codehaus/jackson/JsonToken:END_ARRAY	Lorg/codehaus/jackson/JsonToken;
    //   132: if_acmpeq +54 -> 186
    //   135: aload 13
    //   137: invokevirtual 252	java/util/ArrayList:size	()I
    //   140: iconst_5
    //   141: if_icmpgt +45 -> 186
    //   144: aload 9
    //   146: invokestatic 258	com/instagram/android/model/Media:fromJsonParser	(Lorg/codehaus/jackson/JsonParser;)Lcom/instagram/android/model/Media;
    //   149: astore 17
    //   151: aload 17
    //   153: ifnull +33 -> 186
    //   156: aload 13
    //   158: aload 17
    //   160: invokevirtual 261	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   163: pop
    //   164: goto -40 -> 124
    //   167: astore 8
    //   169: aload 7
    //   171: astore 4
    //   173: aload 4
    //   175: ifnull +8 -> 183
    //   178: aload 4
    //   180: invokevirtual 226	java/io/FileInputStream:close	()V
    //   183: aload 8
    //   185: athrow
    //   186: aload 13
    //   188: invokevirtual 252	java/util/ArrayList:size	()I
    //   191: ifle -127 -> 64
    //   194: iconst_1
    //   195: istore 5
    //   197: invokestatic 267	java/lang/System:currentTimeMillis	()J
    //   200: lstore 14
    //   202: aload_0
    //   203: getfield 79	com/instagram/android/fragment/MainFeedFragment:mHandler	Landroid/os/Handler;
    //   206: new 16	com/instagram/android/fragment/MainFeedFragment$6
    //   209: dup
    //   210: aload_0
    //   211: lload 14
    //   213: lload_2
    //   214: aload 13
    //   216: invokespecial 270	com/instagram/android/fragment/MainFeedFragment$6:<init>	(Lcom/instagram/android/fragment/MainFeedFragment;JJLjava/util/ArrayList;)V
    //   219: lconst_0
    //   220: ldc2_w 271
    //   223: lload 14
    //   225: lload_2
    //   226: lsub
    //   227: lsub
    //   228: invokestatic 278	java/lang/Math:max	(JJ)J
    //   231: invokevirtual 282	android/os/Handler:postDelayed	(Ljava/lang/Runnable;J)Z
    //   234: pop
    //   235: goto -171 -> 64
    //   238: astore 8
    //   240: goto -67 -> 173
    //
    // Exception table:
    //   from	to	target	type
    //   25	59	167	finally
    //   96	164	167	finally
    //   186	235	167	finally
    //   6	25	238	finally
  }

  private void syncPendingMedia()
  {
    ArrayList localArrayList = PendingMediaService.getPendingMedia();
    ((MainFeedAdapter)getAdapter()).setPendingMedia(localArrayList);
  }

  public ActionBarConfigurer getActionBarConfigurer()
  {
    return new FeedFragment.StandardFeedActionBar()
    {
      public View customTitleView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup)
      {
        ImageView localImageView = new ImageView(MainFeedFragment.this.getActivity());
        localImageView.setImageDrawable(MainFeedFragment.this.getActivity().getResources().getDrawable(2130837506));
        FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(-2, -2, 19);
        localLayoutParams.setMargins((int)MainFeedFragment.this.getResources().getDimension(2131427341), 0, 0, 0);
        localImageView.setLayoutParams(localLayoutParams);
        return localImageView;
      }
    };
  }

  protected FeedAdapter getAdapter()
  {
    if (this.mAdapter == null)
      this.mAdapter = new MainFeedAdapter(getActivity(), this, getDefaultFeedViewMode(), getGridViewPadding(), this);
    return this.mAdapter;
  }

  protected void loadDefaultContent()
  {
    if (sLoadedDefaultContent);
    while (true)
    {
      return;
      sLoadedDefaultContent = true;
      this.mLoadingDefaultContent = true;
      new Thread(new Runnable(System.currentTimeMillis())
      {
        public void run()
        {
          File localFile = MainFeedFragment.getCachedHomeFeedFile(MainFeedFragment.this.getActivity().getCacheDir());
          if (localFile.exists());
          try
          {
            MainFeedFragment.this.readFromCache(localFile, this.val$epochWhenCalled);
            Log.e("MainFeedFragment", "Loaded from cached file.");
            return;
          }
          catch (IOException localIOException)
          {
            while (true)
              Log.e("MainFeedFragment", "Error reading from cached file.");
          }
        }
      }).start();
    }
  }

  protected MediaFeedRequest makeRequest(AbstractStreamingApiCallbacks<MediaFeedResponse> paramAbstractStreamingApiCallbacks)
  {
    return new MediaFeedRequest(this, 0, paramAbstractStreamingApiCallbacks)
    {
      private File mFile;

      public File cacheResponseFile()
      {
        if (this.mFile == null)
          this.mFile = new File(getContext().getCacheDir(), "MainFeed.json");
        return this.mFile;
      }

      protected String getBaseFeedPath()
      {
        return "feed/timeline/";
      }
    };
  }

  public void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    LocalBroadcastManager.getInstance(getActivity()).registerReceiver(this.mAppAliveReceiver, new IntentFilter("com.instagram.android.activity.BROADCAST_APP_ALIVE"));
  }

  public void onDetach()
  {
    super.onDetach();
    LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(this.mAppAliveReceiver);
  }

  public void onPause()
  {
    super.onPause();
    this.mHandler.removeMessages(0);
    if (this.mDelayedNewMedia.size() > 0)
    {
      HashMap localHashMap = new HashMap(this.mDelayedNewMedia);
      this.mDelayedNewMedia.clear();
      Iterator localIterator = localHashMap.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        placeNewMedia(str, (String)localHashMap.get(str));
      }
    }
    LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(this.mBroadcastReceiver);
  }

  public void onResume()
  {
    super.onResume();
    LocalBroadcastManager localLocalBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
    localLocalBroadcastManager.registerReceiver(this.mBroadcastReceiver, new IntentFilter("com.instagram.android.PendingMediaService.INTENT_ACTION_PENDING_MEDIA_CHANGED"));
    localLocalBroadcastManager.registerReceiver(this.mBroadcastReceiver, new IntentFilter("com.instagram.android.PendingMediaService.INTENT_ACTION_NEW_USER_MEDIA"));
  }

  public void onStart()
  {
    super.onStart();
    this.mPendingMediaServiceConnection = new PendingMediaServiceConnection(null);
    PendingMediaService.filterPartialPendingMedia();
    if (PendingMediaService.hasPendingMedia())
    {
      Context localContext = AppContext.getContext();
      localContext.bindService(new Intent(localContext, PendingMediaService.class), this.mPendingMediaServiceConnection, 1);
    }
  }

  public void onStop()
  {
    super.onStop();
    try
    {
      if (this.mPendingMediaService != null)
      {
        AppContext.getContext().unbindService(this.mPendingMediaServiceConnection);
        this.mPendingMediaServiceConnection = null;
      }
      return;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      while (true)
      {
        Log.e("MainFeedFragment", "Caught exception for unbindService, continuing...");
        this.mPendingMediaService = null;
      }
    }
  }

  private class PendingMediaServiceConnection
    implements ServiceConnection
  {
    private PendingMediaServiceConnection()
    {
    }

    public void onServiceConnected(ComponentName paramComponentName, IBinder paramIBinder)
    {
      PendingMediaService.ServiceBinder localServiceBinder = (PendingMediaService.ServiceBinder)paramIBinder;
      MainFeedFragment.access$502(MainFeedFragment.this, localServiceBinder.getService());
      MainFeedFragment.this.syncPendingMedia();
    }

    public void onServiceDisconnected(ComponentName paramComponentName)
    {
      MainFeedFragment.access$502(MainFeedFragment.this, null);
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.MainFeedFragment
 * JD-Core Version:    0.6.0
 */
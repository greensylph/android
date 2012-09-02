package com.instagram.android.activity;

import android.app.Dialog;
import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.Toast;
import com.instagram.android.InstagramAutoUpdater;
import com.instagram.android.Log;
import com.instagram.android.Preferences;
import com.instagram.android.fragment.FeedFragment;
import com.instagram.android.fragment.MainFeedFragment;
import com.instagram.android.fragment.NewsFragment;
import com.instagram.android.receiver.C2DMReceiver;
import com.instagram.android.service.AppContext;
import com.instagram.android.widget.CustomToastView;
import com.instagram.android.widget.IgDialogBuilder;
import com.instagram.android.widget.SimpleProxyView;
import com.instagram.util.BuiltInCameraUtil;
import com.instagram.util.CameraUsageReportingUtil;
import com.instagram.util.CropUtil;
import com.instagram.util.FileUtil;
import com.instagram.util.GalleryUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;

public class MainTabActivity extends TabActivity
{
  public static final String BROADCAST_APP_ALIVE = "com.instagram.android.activity.BROADCAST_APP_ALIVE";
  public static final String BROADCAST_BACK_PUSHED = "com.instagram.android.activity.BROADCAST_BACK_PUSHED";
  public static final String BROADCAST_LOGOUT = "com.instagram.android.activity.BROADCAST_LOGOUT";
  public static final String BROADCAST_POP_TO_ROOT = "com.instagram.android.activity.BROADCAST_POP_TO_ROOT";
  private static final String BUNDLE_BACK_TAB_LIST = "backTabList";
  private static final String BUNDLE_TEMP_PHOTO_FILE = "tempPhotoFile";
  public static final String EXTRA_RESUME_WITH_BUNDLE = "com.instagram.android.activity.MainTabActivity.EXTRA_RESUME_WITH_BUNDLE";
  public static final String EXTRA_TAB_TAG_NAME = "com.instagram.android.activity.EXTRA_TAB_TAG_NAME";
  public static final String NOTIFICATION_RECEIVED_BROADCAST_PROXY = "com.instagram.android.receiver.C2DMReceiver.NOTIFICATION_RECEIVED_BROADCAST_PROXY";
  private static final int REQUEST_CODE_BUILT_IN_CAMERA = 4;
  private static final int REQUEST_CODE_CAMERA_ACTIVITY = 1;
  private static final int REQUEST_CODE_CROP_PHOTO = 3;
  private static final int REQUEST_CODE_GALLERY = 2;
  public static final String TAG = "MainTabActivity";
  public static final String TAG_FEED = "feed";
  public static final String TAG_NEWS = "news";
  public static final String TAG_POPULAR = "popular";
  public static final String TAG_PROFILE = "profile";
  public static final String TAG_SHARE = "share";
  private static boolean mShouldShowToast = true;
  private static boolean sNewMediaPosted;
  private BroadcastReceiver broadcastReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramContext, Intent paramIntent)
    {
      if (paramIntent.getAction().equals("com.instagram.android.activity.BROADCAST_BACK_PUSHED"))
      {
        paramIntent.getExtras().getString("com.instagram.android.activity.EXTRA_TAB_TAG_NAME");
        if (!MainTabActivity.this.mBackTabList.isEmpty())
        {
          String str = (String)MainTabActivity.this.mBackTabList.removeFirst();
          MainTabActivity.access$102(MainTabActivity.this, true);
          MainTabActivity.this.getTabHost().setCurrentTabByTag(str);
          MainTabActivity.access$102(MainTabActivity.this, false);
        }
      }
      while (true)
      {
        return;
        MainTabActivity.this.finish();
        continue;
        if (!paramIntent.getAction().equals("com.instagram.android.activity.BROADCAST_LOGOUT"))
          continue;
        Intent localIntent = new Intent(MainTabActivity.this, SignedOutFragmentLayout.class);
        localIntent.setFlags(67108864);
        MainTabActivity.this.startActivity(localIntent);
        File localFile = MainFeedFragment.getCachedHomeFeedFile(MainTabActivity.this.getApplicationContext().getCacheDir());
        if (localFile.exists())
          localFile.delete();
        MainTabActivity.this.finish();
      }
    }
  };
  private InstagramAutoUpdater mAutoUpdater;
  private LinkedList<String> mBackTabList = new LinkedList();
  private boolean mIsShareIntent = false;
  private int mMediaSource;
  private String mOldTabTag;
  private File mTempPhotoFile = null;
  private BroadcastReceiver pushReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramContext, Intent paramIntent)
    {
      paramIntent.setAction("com.instagram.android.receiver.C2DMReceiver.NOTIFICATION_RECEIVED_BROADCAST_PROXY");
      Log.d("MainTabActivity", "Received NOTIFICATION_RECEIVED_BROADCAST, sending NOTIFICATION_RECEIVED_BROADCAST_PROXY");
      if (!LocalBroadcastManager.getInstance(paramContext).sendBroadcast(paramIntent))
        Log.w("MainTabActivity", "News Fragment did not receive NOTIFICATION_RECEIVED_BROADCAST_PROXY");
      while (true)
      {
        return;
        Log.d("MainTabActivity", "NOTIFICATION_RECEIVED_BROADCAST_PROXY received by somebody");
      }
    }
  };
  private boolean removingLink = false;

  private View addTab(int paramInt, TabTag paramTabTag)
  {
    Intent localIntent1 = new Intent(this, getTabClass(paramTabTag.getName()));
    localIntent1.putExtra("com.instagram.extra.EXTRA_STARTING_FRAGMENT", paramInt);
    localIntent1.putExtra("com.instagram.android.activity.EXTRA_TAB_TAG_NAME", paramTabTag.getName());
    Intent localIntent2 = getIntent();
    Bundle localBundle = localIntent2.getExtras();
    if (TabTag.NEWS.equals(paramTabTag))
    {
      if (!isStartFromPushNotification())
        break label121;
      localIntent1.putExtra("com.instagram.android.activity.NewsActivityInTab.EXTRA_NEWS_LAUNCH_BUNDLE", localBundle.getBundle("com.instagram.android.activity.NewsActivityInTab.EXTRA_NEWS_LAUNCH_BUNDLE"));
    }
    while (true)
    {
      View localView = createIndicatorView(paramTabTag);
      TabHost.TabSpec localTabSpec = getTabHost().newTabSpec(paramTabTag.getName()).setIndicator(localView).setContent(localIntent1);
      getTabHost().addTab(localTabSpec);
      return localView;
      label121: if (!isStartFromURL())
        continue;
      localIntent1.putExtra("com.instagram.android.activity.NewsActivityInTab.EXTRA_ACTION_VIEW_SHORT_URL", localIntent2.getDataString());
    }
  }

  private View createIndicatorView(TabTag paramTabTag)
  {
    SimpleProxyView localSimpleProxyView = (SimpleProxyView)LayoutInflater.from(this).inflate(paramTabTag.getLayoutId(), null);
    if (paramTabTag.equals(TabTag.SHARE))
    {
      localSimpleProxyView.setProxyToOnClickListener(false);
      localSimpleProxyView.addAdditionalOnClickListeners(new View.OnClickListener()
      {
        public void onClick(View paramView)
        {
          if ((!Preferences.getInstance(MainTabActivity.this.getApplicationContext()).getHasAdvancedCameraEnabled()) || (Build.VERSION.SDK_INT <= 8))
            MainTabActivity.this.showBuiltInCaptureOptionDialog();
          while (true)
          {
            return;
            CameraUsageReportingUtil.didOpenInstagramCamera();
            Intent localIntent = new Intent(MainTabActivity.this, MediaCaptureActivity.class);
            localIntent.setFlags(65536);
            MainTabActivity.this.startActivityForResult(localIntent, 1);
          }
        }
      });
    }
    while (true)
    {
      return localSimpleProxyView;
      localSimpleProxyView.addAdditionalOnClickListeners(new View.OnClickListener(paramTabTag)
      {
        public void onClick(View paramView)
        {
          if (MainTabActivity.this.getTabHost().getCurrentTabTag().equals(this.val$tag.getName()))
            MainTabActivity.this.popToRoot(this.val$tag.getName());
        }
      });
    }
  }

  public static boolean getShouldShowToast()
  {
    return mShouldShowToast;
  }

  private Class<? extends ActivityInTab> getTabClass(String paramString)
  {
    if (paramString.equals("news"));
    for (Object localObject = NewsActivityInTab.class; ; localObject = ActivityInTab.class)
      return localObject;
  }

  private void handleIntent(Intent paramIntent)
  {
    if (paramIntent == null);
    while (true)
    {
      return;
      C2DMReceiver.clearUnreadCount();
      if (paramIntent.getExtras() == null)
        continue;
      if (paramIntent.getExtras().get("com.instagram.android.activity.NewsActivityInTab.EXTRA_NEWS_LAUNCH_BUNDLE") != null)
      {
        NewsFragment.setSwitchToInboxFlag();
        NewsActivityInTab.setStartingArgumentsAndPopToRootFlag(paramIntent.getExtras().getBundle("com.instagram.android.activity.NewsActivityInTab.EXTRA_NEWS_LAUNCH_BUNDLE"), true);
        setTabStartupInternal("news");
        continue;
      }
      if (paramIntent.getExtras().get("com.instagram.android.activity.MainTabActivity.EXTRA_RESUME_WITH_BUNDLE") != null)
      {
        ActivityInTab.setStartingArgumentsAndPopToRootFlag(paramIntent.getExtras().getBundle("com.instagram.android.activity.MainTabActivity.EXTRA_RESUME_WITH_BUNDLE"), false);
        continue;
      }
      if (!isStartFromURL())
        continue;
      NewsFragment.setSwitchToInboxFlag();
      Bundle localBundle = new Bundle(1);
      localBundle.putString("shortUrl", paramIntent.getDataString());
      NewsActivityInTab.setStartingArgumentsAndPopToRootFlag(localBundle, true);
      setTabStartupInternal("news");
    }
  }

  private void handleReturnFromCamera()
  {
    FeedFragment.flagScrollToTop();
    setTabStartupInternal("feed");
    ActivityInTab.setStartingArgumentsAndPopToRootFlag(null, true);
  }

  private boolean isStartFromPushNotification()
  {
    return getIntent().hasExtra("com.instagram.android.activity.NewsActivityInTab.EXTRA_NEWS_LAUNCH_BUNDLE");
  }

  private boolean isStartFromURL()
  {
    return "android.intent.action.VIEW".equals(getIntent().getAction());
  }

  private void popToRoot(String paramString)
  {
    Intent localIntent = new Intent("com.instagram.android.activity.BROADCAST_POP_TO_ROOT");
    localIntent.putExtra("com.instagram.android.activity.EXTRA_TAB_TAG_NAME", paramString);
    LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(localIntent);
  }

  private void redirectToSignedOutState()
  {
    Intent localIntent = new Intent(this, SignedOutFragmentLayout.class);
    localIntent.setFlags(67108864);
    startActivity(localIntent);
    finish();
  }

  private void safeDeleteTempPhotoFile()
  {
    if ((this.mTempPhotoFile != null) && (this.mTempPhotoFile.isFile()))
    {
      Uri localUri = Uri.fromFile(this.mTempPhotoFile);
      this.mTempPhotoFile.delete();
      getContentResolver().notifyChange(localUri, null);
    }
  }

  public static void setNewMediaPosted()
  {
    sNewMediaPosted = true;
  }

  public static void setShouldShowToast(boolean paramBoolean)
  {
    mShouldShowToast = paramBoolean;
  }

  private void setTabStartupInternal(String paramString)
  {
    this.mOldTabTag = paramString;
    getTabHost().setCurrentTabByTag(paramString);
  }

  private void showBuiltInCaptureOptionDialog()
  {
    CharSequence[] arrayOfCharSequence = new CharSequence[2];
    arrayOfCharSequence[0] = getString(2131231004);
    arrayOfCharSequence[1] = getString(2131231005);
    CameraUsageReportingUtil.didOpenBuiltinSourcePicker();
    new IgDialogBuilder(this).setTitle(2131231006).setItems(arrayOfCharSequence, new DialogInterface.OnClickListener(arrayOfCharSequence)
    {
      private boolean optionIsCamera(int paramInt)
      {
        return this.val$options[paramInt].equals(MainTabActivity.this.getString(2131231004));
      }

      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        MainTabActivity localMainTabActivity = MainTabActivity.this;
        if (optionIsCamera(paramInt))
        {
          CameraUsageReportingUtil.didOpenBuiltinCamera();
          MainTabActivity.access$602(MainTabActivity.this, BuiltInCameraUtil.getPhotoOutputMediaFile());
          BuiltInCameraUtil.show(localMainTabActivity, 4, MainTabActivity.this.mTempPhotoFile);
        }
        while (true)
        {
          return;
          CameraUsageReportingUtil.didOpenBuiltinGallery(false);
          MainTabActivity.access$602(MainTabActivity.this, FileUtil.generateTempFile(AppContext.getContext()));
          GalleryUtil.show(localMainTabActivity, 2, MainTabActivity.this.mTempPhotoFile);
        }
      }
    }).create().show();
  }

  private boolean takeIsShareIntent()
  {
    boolean bool = this.mIsShareIntent;
    this.mIsShareIntent = false;
    return bool;
  }

  private static boolean takeNewMediaPosted()
  {
    boolean bool = sNewMediaPosted;
    sNewMediaPosted = false;
    return bool;
  }

  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (paramInt2 != -1)
      safeDeleteTempPhotoFile();
    while (true)
    {
      return;
      switch (paramInt1)
      {
      default:
        break;
      case 1:
        handleReturnFromCamera();
        break;
      case 4:
        CropUtil.setRestrictNextCrop();
        this.mMediaSource = 1;
        BuiltInCameraUtil.handleActivityResult(this, paramIntent, this.mTempPhotoFile, 3);
        break;
      case 2:
        CropUtil.setRestrictNextCrop();
        this.mMediaSource = 0;
        GalleryUtil.handleActivityResult(this, paramIntent, this.mTempPhotoFile, 3);
        break;
      case 3:
        if (this.mMediaSource == 0)
          safeDeleteTempPhotoFile();
        Intent localIntent = new Intent(this, MediaCaptureActivity.class);
        localIntent.setFlags(65536);
        localIntent.putExtra("mediaFilePath", paramIntent.getAction());
        localIntent.putExtra("mediaSource", this.mMediaSource);
        if (takeIsShareIntent())
          localIntent.putExtra("isShareIntent", true);
        startActivityForResult(localIntent, 1);
      }
    }
  }

  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
    if (getTabHost() != null)
    {
      if (paramConfiguration.orientation != 2)
        break label30;
      getTabWidget().setVisibility(8);
    }
    while (true)
    {
      return;
      label30: getTabWidget().setVisibility(0);
    }
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Log.d("MainTabActivity", "onCreate");
    boolean bool1 = Preferences.getInstance(this).isLoggedIn();
    Intent localIntent = getIntent();
    Bundle localBundle = localIntent.getExtras();
    boolean bool2;
    if (("android.intent.action.SEND".equals(localIntent.getAction())) && (localIntent.hasExtra("android.intent.extra.STREAM")))
    {
      bool2 = true;
      this.mIsShareIntent = bool2;
      if (this.mIsShareIntent)
      {
        if (!bool1)
          break label121;
        CameraUsageReportingUtil.didOpenExternalShareIntent();
        Uri localUri = (Uri)localBundle.getParcelable("android.intent.extra.STREAM");
        CropUtil.setRestrictNextCrop();
        CropUtil.show(this, 3, localUri);
      }
    }
    while (true)
    {
      if (bool1)
        break label135;
      redirectToSignedOutState();
      return;
      bool2 = false;
      break;
      label121: Toast.makeText(this, 2131231014, 1).show();
    }
    label135: setContentView(2130903081);
    ((CustomToastView)findViewById(2131493099)).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        if (!LocalBroadcastManager.getInstance(MainTabActivity.this.getApplicationContext()).sendBroadcast(new Intent("com.instagram.android.fragment.NewsFragment.BROADCAST_SWITCH_TO_INBOX")))
        {
          NewsFragment.setSwitchToInboxFlag();
          if (MainTabActivity.this.getTabHost().getCurrentTabTag().equals("news"))
            break label61;
          NewsActivityInTab.setStartingArgumentsAndPopToRootFlag(null, true);
          MainTabActivity.this.setTabStartupInternal("news");
        }
        while (true)
        {
          return;
          label61: MainTabActivity.this.popToRoot("news");
        }
      }
    });
    addTab(0, TabTag.FEED);
    addTab(1, TabTag.POPULAR);
    addTab(2, TabTag.SHARE).setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
    addTab(3, TabTag.NEWS);
    addTab(4, TabTag.PROFILE);
    setTabStartupInternal("news");
    if (isStartFromPushNotification());
    while (true)
    {
      handleIntent(getIntent());
      if (Preferences.getInstance(this).isPushExpired())
        C2DMReceiver.refreshAppC2DMRegistrationState(this);
      getTabHost().setOnTabChangedListener(new TabChangeListener(null));
      break;
      setTabStartupInternal("feed");
    }
  }

  protected void onNewIntent(Intent paramIntent)
  {
    Log.d("MainTabActivity", "On new intent");
    handleIntent(paramIntent);
    super.onNewIntent(paramIntent);
  }

  protected void onPause()
  {
    super.onPause();
    LocalBroadcastManager.getInstance(this).unregisterReceiver(this.broadcastReceiver);
  }

  protected void onRestoreInstanceState(Bundle paramBundle)
  {
    super.onRestoreInstanceState(paramBundle);
    this.mBackTabList = new LinkedList(paramBundle.getStringArrayList("backTabList"));
    String str = paramBundle.getString("tempPhotoFile");
    if (str != null)
      this.mTempPhotoFile = new File(str);
  }

  protected void onResume()
  {
    super.onResume();
    Log.d("MainTabActivity", "onResume");
    C2DMReceiver.clearUnreadCount();
    LocalBroadcastManager.getInstance(this).registerReceiver(this.broadcastReceiver, new IntentFilter("com.instagram.android.activity.BROADCAST_BACK_PUSHED"));
    LocalBroadcastManager.getInstance(this).registerReceiver(this.broadcastReceiver, new IntentFilter("com.instagram.android.activity.BROADCAST_LOGOUT"));
    LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent("com.instagram.android.activity.BROADCAST_APP_ALIVE"));
  }

  protected void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putStringArrayList("backTabList", new ArrayList(this.mBackTabList));
    if (this.mTempPhotoFile != null)
      paramBundle.putString("tempPhotoFile", this.mTempPhotoFile.toString());
  }

  protected void onStart()
  {
    super.onStart();
    LocalBroadcastManager.getInstance(this).registerReceiver(this.pushReceiver, new IntentFilter("com.instagram.android.receiver.C2DMReceiver.NOTIFICATION_RECEIVED_BROADCAST"));
    if (takeNewMediaPosted())
      handleReturnFromCamera();
  }

  protected void onStop()
  {
    LocalBroadcastManager.getInstance(this).unregisterReceiver(this.pushReceiver);
    super.onStop();
  }

  private class TabChangeListener
    implements TabHost.OnTabChangeListener
  {
    private TabChangeListener()
    {
    }

    public void onTabChanged(String paramString)
    {
      if (!MainTabActivity.this.removingLink)
      {
        MainTabActivity.this.mBackTabList.remove(paramString);
        MainTabActivity.this.mBackTabList.addFirst(MainTabActivity.this.mOldTabTag);
      }
      MainTabActivity.access$702(MainTabActivity.this, paramString);
    }
  }

  static enum TabTag
  {
    static
    {
      POPULAR = new TabTag("POPULAR", 3);
      PROFILE = new TabTag("PROFILE", 4);
      TabTag[] arrayOfTabTag = new TabTag[5];
      arrayOfTabTag[0] = FEED;
      arrayOfTabTag[1] = NEWS;
      arrayOfTabTag[2] = SHARE;
      arrayOfTabTag[3] = POPULAR;
      arrayOfTabTag[4] = PROFILE;
      $VALUES = arrayOfTabTag;
    }

    public int getLayoutId()
    {
      int i;
      switch (MainTabActivity.8.$SwitchMap$com$instagram$android$activity$MainTabActivity$TabTag[ordinal()])
      {
      default:
        i = 2130903128;
      case 1:
      case 2:
      case 3:
      case 4:
      }
      while (true)
      {
        return i;
        i = 2130903124;
        continue;
        i = 2130903125;
        continue;
        i = 2130903126;
        continue;
        i = 2130903127;
      }
    }

    public String getName()
    {
      String str;
      switch (MainTabActivity.8.$SwitchMap$com$instagram$android$activity$MainTabActivity$TabTag[ordinal()])
      {
      default:
        str = "profile";
      case 1:
      case 2:
      case 3:
      case 4:
      }
      while (true)
      {
        return str;
        str = "share";
        continue;
        str = "feed";
        continue;
        str = "news";
        continue;
        str = "popular";
      }
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.activity.MainTabActivity
 * JD-Core Version:    0.6.0
 */
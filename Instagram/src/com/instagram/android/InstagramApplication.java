package com.instagram.android;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.instagram.android.location.BestLocationListener;
import com.instagram.android.model.User;
import com.instagram.android.service.ActionBarService;
import com.instagram.android.service.AppContext;
import com.instagram.android.service.AuthHelper;
import com.instagram.android.service.AutoCompleteUserService.AutoCompleteUpdateReceiver;
import com.instagram.android.service.CustomObjectMapper;
import com.instagram.android.service.MediaStore;
import com.instagram.android.service.UserStore;
import com.instagram.android.service.VenueStore;
import com.instagram.api.ApiHttpClient;
import com.instagram.api.PersistentCookieStore;
import com.instagram.camera.CameraUtil;
import com.instagram.util.ApplicationUuidHelper;
import com.instagram.util.NoopUtil;
import com.instagram.util.ViewUtil;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Observer;

public class InstagramApplication extends Application
{
  private static final boolean DEBUG = false;
  public static final String PACKAGE_NAME = "com.instagram.android";
  private static final String TAG = "InstagramApplication";
  public static final int TASK_LIMIT = 3;
  private static boolean sFailedToLoadFileSystem;
  private static boolean sFailedToLoadLibrary = false;
  private static Boolean sStickyHeaderSupported;
  private final AutoCompleteUserService.AutoCompleteUpdateReceiver mAutoCompleteUpdateReceiver = new AutoCompleteUserService.AutoCompleteUpdateReceiver();
  private final BestLocationListener mBestLocationListener = new BestLocationListener();
  private BroadcastReceiver mChangedUserReceiver = new InstagramApplication.1(this);
  private Handler mHandler;
  private Map<String, Object> serviceMap;

  static
  {
    sFailedToLoadFileSystem = false;
    try
    {
      System.loadLibrary("jni");
      Log.d("InstagramApplication", "Loaded libraries");
      sStickyHeaderSupported = null;
      return;
    }
    catch (UnsatisfiedLinkError localUnsatisfiedLinkError)
    {
      while (true)
        sFailedToLoadLibrary = true;
    }
  }

  private void detectWebViewCrashingBug()
  {
    if (getCacheDir() == null)
      NoopUtil.report("failed_to_initialize_cache_dir");
  }

  public static void detectWriteableFileSystem()
  {
    try
    {
      ApplicationUuidHelper.generateOldStyleUuid(AppContext.getContext());
      return;
    }
    catch (RuntimeException localRuntimeException)
    {
      while (true)
        sFailedToLoadFileSystem = true;
    }
  }

  public static boolean getFailedToLoadFilesystem()
  {
    return sFailedToLoadFileSystem;
  }

  public static boolean getFailedToLoadLibrary()
  {
    return sFailedToLoadLibrary;
  }

  public static String getHttpUserAgent()
  {
    String str1 = "0.0.0 (Unknown)";
    try
    {
      Context localContext = AppContext.getContext();
      str1 = localContext.getPackageManager().getPackageInfo(localContext.getPackageName(), 0).versionName;
      label26: int i = 0;
      int j = 0;
      int k = 0;
      try
      {
        WindowManager localWindowManager = (WindowManager)AppContext.getContext().getSystemService("window");
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        localWindowManager.getDefaultDisplay().getMetrics(localDisplayMetrics);
        i = localDisplayMetrics.densityDpi;
        k = localDisplayMetrics.widthPixels;
        j = localDisplayMetrics.heightPixels;
        label86: Object[] arrayOfObject1 = new Object[3];
        arrayOfObject1[0] = Integer.valueOf(i);
        arrayOfObject1[1] = Integer.valueOf(k);
        arrayOfObject1[2] = Integer.valueOf(j);
        String str2 = String.format("%sdpi; %sx%s", arrayOfObject1);
        String str3 = Locale.getDefault().toString();
        Object localObject = "(unknown build)";
        try
        {
          String str5 = Build.MANUFACTURER;
          if (!Build.MANUFACTURER.equals(Build.BRAND))
          {
            Object[] arrayOfObject4 = new Object[2];
            arrayOfObject4[0] = Build.MANUFACTURER;
            arrayOfObject4[1] = Build.BRAND;
            str5 = String.format("%s/%s", arrayOfObject4);
          }
          Object[] arrayOfObject3 = new Object[8];
          arrayOfObject3[0] = Integer.valueOf(Build.VERSION.SDK_INT);
          arrayOfObject3[1] = Build.VERSION.RELEASE;
          arrayOfObject3[2] = str2;
          arrayOfObject3[3] = str5;
          arrayOfObject3[4] = Build.MODEL;
          arrayOfObject3[5] = Build.DEVICE;
          arrayOfObject3[6] = Build.HARDWARE;
          arrayOfObject3[7] = str3;
          String str6 = String.format("(%s/%s; %s; %s; %s; %s; %s; %s)", arrayOfObject3);
          localObject = str6;
          return "Instagram " + str1 + " Android " + (String)localObject;
        }
        catch (Exception localException2)
        {
          while (true)
            try
            {
              Object[] arrayOfObject2 = new Object[2];
              arrayOfObject2[0] = Integer.valueOf(Build.VERSION.SDK_INT);
              arrayOfObject2[1] = Build.VERSION.RELEASE;
              String str4 = String.format("(%s/%s)", arrayOfObject2);
              localObject = str4;
            }
            catch (Exception localException3)
            {
            }
        }
      }
      catch (Exception localException1)
      {
        break label86;
      }
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      break label26;
    }
  }

  public static boolean getStickyHeaderSupported()
  {
    if (sStickyHeaderSupported == null)
      if (ViewUtil.getScreenHeightPixels(AppContext.getContext()) <= 320)
        break label34;
    label34: for (boolean bool = true; ; bool = false)
    {
      sStickyHeaderSupported = Boolean.valueOf(bool);
      return sStickyHeaderSupported.booleanValue();
    }
  }

  private void scanAdvancedCameraWhiteList()
  {
    Log.d("InstagramApplication", "Device model: " + Build.MODEL);
    String[] arrayOfString = new String[20];
    arrayOfString[0] = "Galaxy Nexus";
    arrayOfString[1] = "Nexus S";
    arrayOfString[2] = "HTC_Amaze_4G";
    arrayOfString[3] = "DROID3";
    arrayOfString[4] = "DROID4";
    arrayOfString[5] = "DROIDX";
    arrayOfString[6] = "DROID BIONIC";
    arrayOfString[7] = "SPH-D700";
    arrayOfString[8] = "GT-I9100";
    arrayOfString[9] = "SGH-T959V";
    arrayOfString[10] = "SCH-I500";
    arrayOfString[11] = "SCH-I510";
    arrayOfString[12] = "ADR6425LVW";
    arrayOfString[13] = "MB865";
    arrayOfString[14] = "LT15a";
    arrayOfString[15] = "PC36100";
    arrayOfString[16] = "PG86100";
    arrayOfString[17] = "GT-N7000";
    arrayOfString[18] = "GT-P7510";
    arrayOfString[19] = "ThinkPad Tablet";
    boolean bool = false;
    if (Build.VERSION.SDK_INT >= 14)
      bool = true;
    label242: 
    while (true)
    {
      Preferences localPreferences = Preferences.getInstance(getApplicationContext());
      localPreferences.setHasAdvancedCameraEnabled(localPreferences.getHasAdvancedCameraEnabled(bool));
      return;
      int i = arrayOfString.length;
      for (int j = 0; ; j++)
      {
        if (j >= i)
          break label242;
        String str = arrayOfString[j];
        if (!Build.MODEL.equalsIgnoreCase(str))
          continue;
        bool = true;
        break;
      }
    }
  }

  public void clearLastKnownLocation()
  {
    this.mBestLocationListener.clearLastKnownLocation();
  }

  public Location getLastKnownLocation()
  {
    return this.mBestLocationListener.getLastKnownLocation();
  }

  public Object getSystemService(String paramString)
  {
    if (this.serviceMap.containsKey(paramString));
    for (Object localObject = this.serviceMap.get(paramString); ; localObject = super.getSystemService(paramString))
      return localObject;
  }

  public boolean isLocationValid(Location paramLocation)
  {
    return this.mBestLocationListener.isLocationValid(paramLocation);
  }

  public void onCreate()
  {
    super.onCreate();
    this.mHandler = new Handler();
    AppContext.setContext(getApplicationContext());
    this.serviceMap = new HashMap();
    this.serviceMap.put("com.instagram.androidservice,AuthHelper", new AuthHelper(this));
    this.serviceMap.put("com.instagram.android.service.mediastore", new MediaStore());
    this.serviceMap.put("com.instagram.android.service.userstore", new UserStore());
    this.serviceMap.put("com.instagram.android.service.venue_store", new VenueStore());
    this.serviceMap.put("com.instagram.android.service.customObjectMapper", new CustomObjectMapper(this));
    PersistentCookieStore localPersistentCookieStore = new PersistentCookieStore(this);
    this.serviceMap.put("com.instagram.api.PersistentCookieStore", localPersistentCookieStore);
    this.serviceMap.put("com.instagram.api.ApiHttpClient", new ApiHttpClient(localPersistentCookieStore));
    this.serviceMap.put("com.instagram.android.Preferences", new Preferences(this));
    this.serviceMap.put("com.instagram.android.service.actionBarService", new ActionBarService(this));
    detectWebViewCrashingBug();
    LocalBroadcastManager.getInstance(this).registerReceiver(this.mChangedUserReceiver, new IntentFilter("com.instagram.android.service.BROADCAST_USER_CHANGED"));
    CameraUtil.initialize(this);
    User.initialize();
    new Thread(new InstagramApplication.2(this)).start();
    scanAdvancedCameraWhiteList();
    this.mHandler.postDelayed(new InstagramApplication.3(this), 3000L);
  }

  public void onTerminate()
  {
    super.onTerminate();
    LocalBroadcastManager.getInstance(this).unregisterReceiver(this.mChangedUserReceiver);
    this.serviceMap.clear();
    AppContext.setContext(null);
  }

  public void removeLocationUpdates()
  {
    this.mBestLocationListener.unregister((LocationManager)getSystemService("location"));
  }

  public void removeLocationUpdates(Observer paramObserver)
  {
    this.mBestLocationListener.deleteObserver(paramObserver);
    removeLocationUpdates();
  }

  public BestLocationListener requestLocationUpdates(Observer paramObserver)
  {
    this.mBestLocationListener.addObserver(paramObserver);
    this.mBestLocationListener.register((LocationManager)getSystemService("location"), true);
    return this.mBestLocationListener;
  }

  public BestLocationListener requestLocationUpdates(boolean paramBoolean)
  {
    this.mBestLocationListener.register((LocationManager)getSystemService("location"), paramBoolean);
    return this.mBestLocationListener;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.InstagramApplication
 * JD-Core Version:    0.6.0
 */
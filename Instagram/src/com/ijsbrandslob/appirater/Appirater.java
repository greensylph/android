package com.ijsbrandslob.appirater;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.widget.Button;
import android.widget.TextView;
import ch.boye.httpclientandroidlib.HttpResponse;
import ch.boye.httpclientandroidlib.StatusLine;
import ch.boye.httpclientandroidlib.client.HttpClient;
import ch.boye.httpclientandroidlib.client.methods.HttpGet;
import ch.boye.httpclientandroidlib.impl.client.DefaultHttpClient;
import com.instagram.android.Log;
import java.util.Date;

public class Appirater
{
  private static final String APPIRATER_CURRENT_VERSION = "APPIRATER_CURRENT_VERSION";
  private static final String APPIRATER_DECLINED_TO_RATE = "APPIRATER_DECLINED_TO_RATE";
  private static final String APPIRATER_FIRST_USE_DATE = "APPIRATER_FIRST_USE_DATE";
  private static final String APPIRATER_RATED_CURRENT_VERSION = "APPIRATER_RATED_CURRENT_VERSION";
  private static final String APPIRATER_REMINDER_REQUEST_DATE = "APPIRATER_REMINDER_REQUEST_DATE";
  private static final String APPIRATER_SIG_EVENT_COUNT = "APPIRATER_SIG_EVENT_COUNT";
  private static final String APPIRATER_USE_COUNT = "APPIRATER_USE_COUNT";
  private static final int DAYS_UNTIL_PROMPT = 0;
  private static final boolean DEBUG = false;
  private static final String LOG_TAG = "Appirater";
  public static final int RANDOM_CONSTANT_VERSION_NUMBER = 2000;
  private static final int SIG_EVENTS_UNTIL_PROMPT = 4;
  private static final int TIME_BEFORE_REMINDING;
  private static final int USES_UNTIL_PROMPT;
  private Context mContext;
  private int mCurrentVersion;
  private boolean mDeclinedToRate;
  private Date mFirstUseDate;
  private boolean mRatedCurrentVersion;
  private Date mReminderRequestDate;
  private int mSignificantEventCount;
  private int mUseCount;

  public Appirater(Context paramContext)
  {
    this.mContext = paramContext;
    loadSettings();
  }

  private boolean connectedToNetwork()
  {
    try
    {
      int j = new DefaultHttpClient().execute(new HttpGet("http://www.google.com/")).getStatusLine().getStatusCode();
      if (j < 400)
      {
        i = 1;
        return i;
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        Log.d("Appirater", "Exception occurred: " + localException);
        int i = 0;
      }
    }
  }

  private int getVersion()
  {
    return 2000;
  }

  private void incrementAndRate(boolean paramBoolean)
  {
    incrementUseCount();
    if ((paramBoolean) && (ratingConditionsHaveBeenMet()) && (connectedToNetwork()))
      showRatingAlert();
  }

  private void incrementSignificantEventAndRate(boolean paramBoolean)
  {
    incrementSignificantEventCount();
    if ((paramBoolean) && (ratingConditionsHaveBeenMet()))
      showRatingAlert();
  }

  private void incrementSignificantEventCount()
  {
    int i = getVersion();
    if (this.mCurrentVersion == -1)
      this.mCurrentVersion = i;
    if (this.mCurrentVersion == i)
    {
      if (this.mFirstUseDate == null)
        this.mFirstUseDate = new Date();
      this.mSignificantEventCount = (1 + this.mSignificantEventCount);
    }
    while (true)
    {
      saveSettings();
      return;
      this.mCurrentVersion = i;
      this.mFirstUseDate = null;
      this.mUseCount = 0;
      this.mSignificantEventCount = 1;
      this.mRatedCurrentVersion = false;
      this.mDeclinedToRate = false;
      this.mReminderRequestDate = null;
    }
  }

  private void incrementUseCount()
  {
    int i = getVersion();
    if (this.mCurrentVersion == -1)
      this.mCurrentVersion = i;
    if (this.mCurrentVersion == i)
    {
      if (this.mFirstUseDate == null)
        this.mFirstUseDate = new Date();
      this.mUseCount = (1 + this.mUseCount);
    }
    while (true)
    {
      saveSettings();
      return;
      this.mCurrentVersion = i;
      this.mFirstUseDate = new Date();
      this.mUseCount = 1;
      this.mSignificantEventCount = 0;
      this.mRatedCurrentVersion = false;
      this.mDeclinedToRate = false;
      this.mReminderRequestDate = null;
    }
  }

  private void loadSettings()
  {
    this.mContext.getResources();
    SharedPreferences localSharedPreferences = this.mContext.getSharedPreferences(this.mContext.getPackageName(), 0);
    if (localSharedPreferences.contains("APPIRATER_FIRST_USE_DATE"))
    {
      long l1 = localSharedPreferences.getLong("APPIRATER_FIRST_USE_DATE", -1L);
      if (-1L != l1)
        this.mFirstUseDate = new Date(l1);
      long l2 = localSharedPreferences.getLong("APPIRATER_REMINDER_REQUEST_DATE", -1L);
      if (-1L != l2)
        this.mReminderRequestDate = new Date(l2);
      this.mUseCount = localSharedPreferences.getInt("APPIRATER_USE_COUNT", 0);
      this.mSignificantEventCount = localSharedPreferences.getInt("APPIRATER_SIG_EVENT_COUNT", 0);
      this.mCurrentVersion = localSharedPreferences.getInt("APPIRATER_CURRENT_VERSION", 0);
      this.mRatedCurrentVersion = localSharedPreferences.getBoolean("APPIRATER_RATED_CURRENT_VERSION", false);
      this.mDeclinedToRate = localSharedPreferences.getBoolean("APPIRATER_DECLINED_TO_RATE", false);
    }
  }

  private boolean ratingConditionsHaveBeenMet()
  {
    int i = 0;
    if (this.mUseCount < 0);
    while (true)
    {
      return i;
      if ((this.mSignificantEventCount < 4) || (this.mDeclinedToRate) || (this.mRatedCurrentVersion))
        continue;
      i = 1;
    }
  }

  private void saveSettings()
  {
    this.mContext.getResources();
    SharedPreferences.Editor localEditor = this.mContext.getSharedPreferences(this.mContext.getPackageName(), 0).edit();
    long l1 = -1L;
    if (this.mFirstUseDate != null)
      l1 = this.mFirstUseDate.getTime();
    localEditor.putLong("APPIRATER_FIRST_USE_DATE", l1);
    long l2 = -1L;
    if (this.mReminderRequestDate != null)
      l2 = this.mReminderRequestDate.getTime();
    localEditor.putLong("APPIRATER_REMINDER_REQUEST_DATE", l2);
    localEditor.putInt("APPIRATER_USE_COUNT", this.mUseCount);
    localEditor.putInt("APPIRATER_SIG_EVENT_COUNT", this.mSignificantEventCount);
    localEditor.putInt("APPIRATER_CURRENT_VERSION", this.mCurrentVersion);
    localEditor.putBoolean("APPIRATER_RATED_CURRENT_VERSION", this.mRatedCurrentVersion);
    localEditor.putBoolean("APPIRATER_DECLINED_TO_RATE", this.mDeclinedToRate);
    localEditor.commit();
  }

  private void showRatingAlert()
  {
    Dialog localDialog = new Dialog(this.mContext, 2131623942);
    Resources localResources = this.mContext.getResources();
    Object localObject = "unknown";
    try
    {
      CharSequence localCharSequence = this.mContext.getPackageManager().getApplicationLabel(this.mContext.getPackageManager().getApplicationInfo(this.mContext.getPackageName(), 0));
      localObject = localCharSequence;
      label58: localDialog.setContentView(2130903059);
      TextView localTextView1 = (TextView)localDialog.findViewById(2131492918);
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = localObject;
      localTextView1.setText(localResources.getString(2131230988, arrayOfObject1));
      TextView localTextView2 = (TextView)localDialog.findViewById(2131492919);
      String str1 = localResources.getString(2131230987);
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = localObject;
      localTextView2.setText(String.format(str1, arrayOfObject2));
      Button localButton1 = (Button)localDialog.findViewById(2131492920);
      String str2 = localResources.getString(2131230990);
      Object[] arrayOfObject3 = new Object[1];
      arrayOfObject3[0] = localObject;
      localButton1.setText(String.format(str2, arrayOfObject3));
      Button localButton2 = (Button)localDialog.findViewById(2131492921);
      Button localButton3 = (Button)localDialog.findViewById(2131492922);
      localButton1.setOnClickListener(new Appirater.1(this, localDialog));
      localButton2.setOnClickListener(new Appirater.2(this, localDialog));
      localButton3.setOnClickListener(new Appirater.3(this, localDialog));
      localDialog.show();
      return;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      break label58;
    }
  }

  public void appEnteredForeground(boolean paramBoolean)
  {
    incrementAndRate(paramBoolean);
  }

  public void appLaunched(boolean paramBoolean)
  {
    incrementAndRate(paramBoolean);
  }

  public void userDidSignificantEvent(boolean paramBoolean)
  {
    incrementSignificantEventAndRate(paramBoolean);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.ijsbrandslob.appirater.Appirater
 * JD-Core Version:    0.6.0
 */
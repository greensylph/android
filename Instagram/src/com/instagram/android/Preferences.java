package com.instagram.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.instagram.android.model.User;
import com.instagram.android.service.AppContext;
import com.instagram.android.service.UserStore;
import com.instagram.api.PersistentCookieStore;
import com.instagram.facebook.FacebookAccount;
import com.instagram.foursquare.FoursquareAccount;
import com.instagram.tumblr.TumblrAccount;
import com.instagram.twitter.TwitterAccount;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.CollectionType;
import org.codehaus.jackson.map.type.TypeFactory;

public class Preferences
{
  private static final boolean DEBUG = false;
  private static final String LOG_TAG = "Preferences";
  private static final int MAX_SAVED_TAGS = 15;
  private static final int MAX_SAVED_USERS = 5;
  public static final String PREFERENCES_SERVICE = "com.instagram.android.Preferences";
  private static final String PREFERENCE_ADVANCED_CAMERA_ENABLED = "advanced_camera_enabled";
  private static final String PREFERENCE_BORDERS_ENABLED = "borders_enabled";
  private static final String PREFERENCE_CURRENT_USER = "current";
  private static final String PREFERENCE_DOUBLE_TAP_TO_LIKE_HINT_IMPRESSIONS = "used_double_tap_hint_impressions";
  private static final String PREFERENCE_GEOTAG_ENABLED = "geotag_enabled";
  private static final String PREFERENCE_HAS_USED_DOUBLE_TAP_TO_LIKE = "used_double_tap";
  private static final String PREFERENCE_PUSH_REGISTRATION_DATE = "push_reg_date";
  private static final String PREFERENCE_RECENT_HASHTAG_SEARCHES = "recent_hashtag_searches";
  private static final String PREFERENCE_RECENT_USER_SEARCHES = "recent_user_searches";
  private static final String PREFERENCE_SYSTEM_MESSAGES = "system_message_";
  private static final String PREFERENCE_UNIQUE_ID = "unique_id";
  private static final String PREFERENCE_USER_ID = "id";
  private static final String PREFERENCE_USER_NAME = "user_name";
  private static final String TAG = "Preferences";
  private static final long TWO_DAYS = 172800000L;
  private static String sUniqueID = null;
  private Context mContext;
  private final ObjectMapper mObjectMapper;
  private SharedPreferences mPrefs;

  public Preferences(Context paramContext)
  {
    this.mContext = paramContext;
    this.mPrefs = PreferenceManager.getDefaultSharedPreferences(paramContext);
    this.mObjectMapper = new ObjectMapper();
  }

  public static Preferences getInstance(Context paramContext)
  {
    Preferences localPreferences = (Preferences)paramContext.getSystemService("com.instagram.android.Preferences");
    if (localPreferences == null)
      localPreferences = (Preferences)paramContext.getApplicationContext().getSystemService("com.instagram.android.Preferences");
    if (localPreferences == null)
      throw new IllegalStateException("ApiHttpClient not available");
    return localPreferences;
  }

  private String getSystemMessageKey(String paramString)
  {
    return "system_message_" + paramString;
  }

  private ArrayList<String> populateHashtagsFromData(ArrayList<String> paramArrayList, String paramString)
    throws IOException
  {
    CollectionType localCollectionType = this.mObjectMapper.getTypeFactory().constructCollectionType(ArrayList.class, String.class);
    return (ArrayList)this.mObjectMapper.readValue(paramString, localCollectionType);
  }

  public void clearLogin()
  {
    FacebookAccount.deleteCredentials(false);
    TwitterAccount.remove(AppContext.getContext(), false);
    FoursquareAccount.delete(AppContext.getContext());
    TumblrAccount.remove(AppContext.getContext());
    PersistentCookieStore.getInstance(this.mContext).clear();
    SharedPreferences.Editor localEditor = this.mPrefs.edit();
    clearUser(localEditor);
    localEditor.commit();
    boolean bool = getHasAdvancedCameraEnabled();
    this.mPrefs.edit().clear().commit();
    setHasAdvancedCameraEnabled(bool);
  }

  public void clearRecentUserSearches()
  {
    this.mPrefs.edit().remove("recent_user_searches").commit();
  }

  public void clearUser(SharedPreferences.Editor paramEditor)
  {
    paramEditor.remove("id").remove("user_name");
  }

  public String getCurrentUserData()
  {
    return this.mPrefs.getString("current", null);
  }

  public int getDoubleTappedToLikeHintImpressions()
  {
    return this.mPrefs.getInt("used_double_tap_hint_impressions", 0);
  }

  public boolean getHasAdvancedCameraEnabled()
  {
    return getHasAdvancedCameraEnabled(false);
  }

  public boolean getHasAdvancedCameraEnabled(boolean paramBoolean)
  {
    return this.mPrefs.getBoolean("advanced_camera_enabled", paramBoolean);
  }

  public boolean getHasBordersEnabled()
  {
    return this.mPrefs.getBoolean("borders_enabled", true);
  }

  public boolean getHasGeotagEnabled()
  {
    return this.mPrefs.getBoolean("geotag_enabled", false);
  }

  public boolean getHasUsedDoubleTapToLike()
  {
    return this.mPrefs.getBoolean("used_double_tap", false);
  }

  public ArrayList<String> getRecentHashtagSearches()
  {
    Object localObject = null;
    String str = this.mPrefs.getString("recent_hashtag_searches", null);
    if (str != null);
    try
    {
      ArrayList localArrayList = populateHashtagsFromData(null, str);
      localObject = localArrayList;
      return localObject;
    }
    catch (IOException localIOException)
    {
      while (true)
      {
        Log.d("Preferences", "Error reading from recent hash tags.  Clearing results");
        this.mPrefs.edit().remove("recent_hashtag_searches").commit();
      }
    }
  }

  public ArrayList<User> getRecentUserSearches()
  {
    Object localObject = null;
    String str = this.mPrefs.getString("recent_user_searches", null);
    UserStore localUserStore = UserStore.getInstance(AppContext.getContext());
    if (str != null);
    try
    {
      localArrayList = new ArrayList();
      while (true)
      {
        try
        {
          Iterator localIterator = User.getUserListFromSerializedData(str).iterator();
          if (!localIterator.hasNext())
            break;
          localUser = (User)localIterator.next();
          if ((User)localUserStore.get(localUser.getId()) != null)
            break label142;
          localUserStore.put(localUser.getId(), localUser);
          localArrayList.add(localUser);
          continue;
        }
        catch (IOException localIOException1)
        {
          localObject = localArrayList;
        }
        Log.d("Preferences", "Error reading from recent users. Clearing results");
        this.mPrefs.edit().remove("recent_user_searches").commit();
        return localObject;
        label142: User localUser = (User)localUserStore.get(localUser.getId());
      }
    }
    catch (IOException localIOException2)
    {
      while (true)
      {
        ArrayList localArrayList;
        continue;
        localObject = localArrayList;
      }
    }
  }

  public Float getSystemMessageTime(String paramString)
  {
    Float localFloat = null;
    String str = getSystemMessageKey(paramString);
    if (this.mPrefs.contains(str))
      localFloat = Float.valueOf(this.mPrefs.getFloat(str, -1.0F));
    return localFloat;
  }

  public String getUniqueId()
  {
    monitorenter;
    try
    {
      if (sUniqueID == null)
      {
        sUniqueID = this.mPrefs.getString("unique_id", null);
        if (sUniqueID == null)
        {
          sUniqueID = UUID.randomUUID().toString();
          SharedPreferences.Editor localEditor = this.mPrefs.edit();
          localEditor.putString("unique_id", sUniqueID);
          localEditor.commit();
        }
      }
      String str = sUniqueID;
      monitorexit;
      return str;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public String getUserId()
  {
    return this.mPrefs.getString("id", null);
  }

  public boolean isLoggedIn()
  {
    if (!TextUtils.isEmpty(getUserId()));
    for (int i = 1; ; i = 0)
      return i;
  }

  public boolean isPushExpired()
  {
    int i = 1;
    if (!this.mPrefs.contains("push_reg_date"));
    while (true)
    {
      return i;
      if (Math.abs(this.mPrefs.getLong("push_reg_date", 0L) - new Date().getTime()) > 172800000L)
        continue;
      i = 0;
    }
  }

  public void saveRecentHashtag(String paramString)
  {
    String str = this.mPrefs.getString("recent_hashtag_searches", null);
    ArrayList localArrayList;
    if (str != null)
      try
      {
        localArrayList = populateHashtagsFromData(null, str);
        while (localArrayList.size() > 15)
          localArrayList.remove(-1 + localArrayList.size());
      }
      catch (IOException localIOException)
      {
        Log.d("Preferences", "Error reading from recent hash tags.  Clearing results");
        this.mPrefs.edit().remove("recent_hashtag_searches").commit();
      }
    while (true)
    {
      return;
      localArrayList = new ArrayList(15);
      localArrayList.remove(paramString);
      localArrayList.add(0, paramString);
      this.mPrefs.edit().putString("recent_hashtag_searches", this.mObjectMapper.writeValueAsString(localArrayList)).commit();
    }
  }

  public void saveRecentUser(User paramUser)
  {
    String str = this.mPrefs.getString("recent_user_searches", null);
    ArrayList localArrayList;
    if (str != null)
      try
      {
        localArrayList = User.getUserListFromSerializedData(str);
        while (localArrayList.size() > 5)
          localArrayList.remove(-1 + localArrayList.size());
      }
      catch (IOException localIOException)
      {
        Log.d("Preferences", "Error reading from recent users. Clearing results");
        this.mPrefs.edit().remove("recent_user_searches").commit();
      }
    while (true)
    {
      return;
      localArrayList = new ArrayList(5);
      localArrayList.remove(paramUser);
      localArrayList.add(0, paramUser);
      this.mPrefs.edit().putString("recent_user_searches", User.getUserListSerialized(localArrayList)).commit();
    }
  }

  public void setCurrentUserData(String paramString)
  {
    this.mPrefs.edit().putString("current", paramString).commit();
  }

  public void setDoubleTappedToLikeHintImpressions(int paramInt)
  {
    this.mPrefs.edit().putInt("used_double_tap_hint_impressions", paramInt).commit();
  }

  public boolean setHasAdvancedCameraEnabled(boolean paramBoolean)
  {
    return this.mPrefs.edit().putBoolean("advanced_camera_enabled", paramBoolean).commit();
  }

  public void setHasBordersEnabled(boolean paramBoolean)
  {
    this.mPrefs.edit().putBoolean("borders_enabled", paramBoolean).commit();
  }

  public boolean setHasGeotagEnabled(boolean paramBoolean)
  {
    return this.mPrefs.edit().putBoolean("geotag_enabled", paramBoolean).commit();
  }

  public void setHasUsedDoubleTapToLike(boolean paramBoolean)
  {
    this.mPrefs.edit().putBoolean("used_double_tap", paramBoolean).commit();
  }

  public void setPushRegistrationDate(long paramLong)
  {
    this.mPrefs.edit().putLong("push_reg_date", paramLong);
  }

  public void setSystemMessageTime(String paramString, Float paramFloat)
  {
    this.mPrefs.edit().putFloat(getSystemMessageKey(paramString), paramFloat.floatValue()).commit();
  }

  public void storeLogin(User paramUser)
  {
    SharedPreferences.Editor localEditor = this.mPrefs.edit();
    storeUser(localEditor, paramUser);
    localEditor.commit();
  }

  public void storeUser(SharedPreferences.Editor paramEditor, User paramUser)
  {
    if ((TextUtils.isEmpty(paramUser.getId())) || (TextUtils.isEmpty(paramUser.getUsername())));
    while (true)
    {
      return;
      paramEditor.putString("id", paramUser.getId());
      paramEditor.putString("user_name", paramUser.getUsername());
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.Preferences
 * JD-Core Version:    0.6.0
 */
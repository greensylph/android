package com.instagram.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import ch.boye.httpclientandroidlib.client.CookieStore;
import ch.boye.httpclientandroidlib.cookie.Cookie;
import com.instagram.android.Log;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;

public class JsonBackedPersistentCookieStore
  implements CookieStore
{
  private static final String COOKIE_NAME_PREFIX = "cookie_";
  private static final String COOKIE_NAME_STORE = "names";
  private static final String COOKIE_PREFS = "CookiePrefsFile2";
  public static final String COOKIE_STORE_SERVICE = "com.instagram.api.PersistentCookieStore";
  public static final String LOG_TAG = "JsonBackedPersistentCookieStore";
  private static ObjectMapper mapper;
  private static SimpleModule module = new SimpleModule("JsonBackedPersistentCookieStore", Version.unknownVersion());
  private Context mContext;
  private final SharedPreferences mCookiePrefs;
  private final ConcurrentHashMap<String, Cookie> mCookies;

  static
  {
    module.addDeserializer(Cookie.class, new JsonSerializedCookie.LocalJson.Deserializer());
    module.addSerializer(Cookie.class, new JsonSerializedCookie.LocalJson.Serializer());
    mapper = new ObjectMapper().withModule(module);
  }

  public JsonBackedPersistentCookieStore(Context paramContext)
  {
    this.mContext = paramContext.getApplicationContext();
    this.mCookiePrefs = paramContext.getSharedPreferences("CookiePrefsFile2", 0);
    this.mCookies = new ConcurrentHashMap();
    CookieSyncManager.createInstance(paramContext);
    String str1 = this.mCookiePrefs.getString("names", null);
    if (str1 != null)
    {
      for (String str2 : TextUtils.split(str1, ","))
      {
        String str3 = this.mCookiePrefs.getString("cookie_" + str2, null);
        if (str3 == null)
          continue;
        Cookie localCookie = decodeCookie(str3);
        if (localCookie == null)
          continue;
        this.mCookies.put(str2, localCookie);
      }
      clearExpired(new Date());
    }
  }

  public static PersistentCookieStore getInstance(Context paramContext)
  {
    PersistentCookieStore localPersistentCookieStore = (PersistentCookieStore)paramContext.getApplicationContext().getSystemService("com.instagram.api.PersistentCookieStore");
    if (localPersistentCookieStore == null)
      throw new IllegalStateException("PersistentCookieStore not available");
    return localPersistentCookieStore;
  }

  public void addCookie(Cookie paramCookie)
  {
    String str1 = paramCookie.getName();
    this.mCookies.put(str1, paramCookie);
    SharedPreferences.Editor localEditor = this.mCookiePrefs.edit();
    localEditor.putString("names", TextUtils.join(",", this.mCookies.keySet()));
    localEditor.putString("cookie_" + str1, encodeCookie(paramCookie));
    localEditor.commit();
    CookieManager localCookieManager = CookieManager.getInstance();
    String str2 = paramCookie.getName() + "=" + paramCookie.getValue() + "; domain=" + paramCookie.getDomain();
    localCookieManager.setCookie(paramCookie.getDomain(), str2);
    CookieSyncManager.getInstance().sync();
  }

  protected String byteArrayToHexString(byte[] paramArrayOfByte)
  {
    StringBuilder localStringBuilder = new StringBuilder(2 * paramArrayOfByte.length);
    int i = paramArrayOfByte.length;
    for (int j = 0; j < i; j++)
    {
      int k = 0xFF & paramArrayOfByte[j];
      if (k < 16)
        localStringBuilder.append('0');
      localStringBuilder.append(Integer.toHexString(k));
    }
    return localStringBuilder.toString().toUpperCase();
  }

  public void clear()
  {
    this.mCookies.clear();
    SharedPreferences.Editor localEditor = this.mCookiePrefs.edit();
    Iterator localIterator = this.mCookies.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      localEditor.remove("cookie_" + str);
    }
    localEditor.remove("names");
    localEditor.commit();
    CookieManager.getInstance().removeAllCookie();
    CookieSyncManager.getInstance().sync();
  }

  public boolean clearExpired(Date paramDate)
  {
    int i = 0;
    SharedPreferences.Editor localEditor = this.mCookiePrefs.edit();
    Iterator localIterator = this.mCookies.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      String str = (String)localEntry.getKey();
      if (!((Cookie)localEntry.getValue()).isExpired(paramDate))
        continue;
      this.mCookies.remove(str);
      localEditor.remove("cookie_" + str);
      i = 1;
      CookieManager.getInstance().removeExpiredCookie();
    }
    if (i != 0)
    {
      localEditor.putString("names", TextUtils.join(",", this.mCookies.keySet()));
      CookieSyncManager.getInstance().sync();
    }
    localEditor.commit();
    CookieManager.getInstance().removeExpiredCookie();
    CookieSyncManager.getInstance().sync();
    return i;
  }

  protected Cookie decodeCookie(String paramString)
  {
    try
    {
      localCookie = (Cookie)mapper.readValue(paramString, Cookie.class);
      return localCookie;
    }
    catch (IOException localIOException)
    {
      while (true)
      {
        Log.w("JsonBackedPersistentCookieStore", "Error decoding cookie");
        Cookie localCookie = null;
      }
    }
  }

  protected String encodeCookie(Cookie paramCookie)
  {
    try
    {
      String str2 = mapper.writeValueAsString(paramCookie);
      str1 = str2;
      return str1;
    }
    catch (IOException localIOException)
    {
      while (true)
      {
        Log.w("JsonBackedPersistentCookieStore", "Error encoding cookie");
        String str1 = null;
      }
    }
  }

  public List<Cookie> getCookies()
  {
    return new ArrayList(this.mCookies.values());
  }

  protected byte[] hexStringToByteArray(String paramString)
  {
    int i = paramString.length();
    byte[] arrayOfByte = new byte[i / 2];
    for (int j = 0; j < i; j += 2)
      arrayOfByte[(j / 2)] = (byte)((Character.digit(paramString.charAt(j), 16) << 4) + Character.digit(paramString.charAt(j + 1), 16));
    return arrayOfByte;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.JsonBackedPersistentCookieStore
 * JD-Core Version:    0.6.0
 */
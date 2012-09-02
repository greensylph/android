package com.loopj.android.http;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;

public class PersistentCookieStore
  implements CookieStore
{
  private static final String COOKIE_NAME_PREFIX = "cookie_";
  private static final String COOKIE_NAME_STORE = "names";
  private static final String COOKIE_PREFS = "CookiePrefsFile";
  private SharedPreferences cookiePrefs;
  private ConcurrentHashMap<String, Cookie> cookies;

  public PersistentCookieStore(Context paramContext)
  {
    this.cookiePrefs = paramContext.getSharedPreferences("CookiePrefsFile", 0);
    this.cookies = new ConcurrentHashMap();
    String str1 = this.cookiePrefs.getString("names", null);
    if (str1 != null)
    {
      String[] arrayOfString = TextUtils.split(str1, ",");
      int j = arrayOfString.length;
      while (i < j)
      {
        String str2 = arrayOfString[i];
        String str3 = this.cookiePrefs.getString("cookie_" + str2, null);
        if (str3 != null)
        {
          Cookie localCookie = decodeCookie(str3);
          if (localCookie != null)
            this.cookies.put(str2, localCookie);
        }
        i++;
      }
      clearExpired(new Date());
    }
  }

  public void addCookie(Cookie paramCookie)
  {
    String str = paramCookie.getName();
    this.cookies.put(str, paramCookie);
    SharedPreferences.Editor localEditor = this.cookiePrefs.edit();
    localEditor.putString("names", TextUtils.join(",", this.cookies.keySet()));
    localEditor.putString("cookie_" + str, encodeCookie(new SerializableCookie(paramCookie)));
    localEditor.commit();
  }

  protected String byteArrayToHexString(byte[] paramArrayOfByte)
  {
    StringBuffer localStringBuffer = new StringBuffer(2 * paramArrayOfByte.length);
    for (int i = 0; i < paramArrayOfByte.length; i++)
    {
      int j = 0xFF & paramArrayOfByte[i];
      if (j < 16)
        localStringBuffer.append('0');
      localStringBuffer.append(Integer.toHexString(j));
    }
    return localStringBuffer.toString().toUpperCase();
  }

  public void clear()
  {
    this.cookies.clear();
    SharedPreferences.Editor localEditor = this.cookiePrefs.edit();
    Iterator localIterator = this.cookies.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      localEditor.remove("cookie_" + str);
    }
    localEditor.remove("names");
    localEditor.commit();
  }

  public boolean clearExpired(Date paramDate)
  {
    SharedPreferences.Editor localEditor = this.cookiePrefs.edit();
    Iterator localIterator = this.cookies.entrySet().iterator();
    int i = 0;
    if (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      String str = (String)localEntry.getKey();
      if (!((Cookie)localEntry.getValue()).isExpired(paramDate))
        break label160;
      this.cookies.remove(str);
      localEditor.remove("cookie_" + str);
    }
    label160: for (int j = 1; ; j = i)
    {
      i = j;
      break;
      if (i != 0)
        localEditor.putString("names", TextUtils.join(",", this.cookies.keySet()));
      localEditor.commit();
      return i;
    }
  }

  protected Cookie decodeCookie(String paramString)
  {
    ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(hexStringToByteArray(paramString));
    try
    {
      Cookie localCookie2 = ((SerializableCookie)new ObjectInputStream(localByteArrayInputStream).readObject()).getCookie();
      localCookie1 = localCookie2;
      return localCookie1;
    }
    catch (Exception localException)
    {
      while (true)
      {
        localException.printStackTrace();
        Cookie localCookie1 = null;
      }
    }
  }

  protected String encodeCookie(SerializableCookie paramSerializableCookie)
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    try
    {
      new ObjectOutputStream(localByteArrayOutputStream).writeObject(paramSerializableCookie);
      str = byteArrayToHexString(localByteArrayOutputStream.toByteArray());
      return str;
    }
    catch (Exception localException)
    {
      while (true)
        String str = null;
    }
  }

  public List<Cookie> getCookies()
  {
    return new ArrayList(this.cookies.values());
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
 * Qualified Name:     com.loopj.android.http.PersistentCookieStore
 * JD-Core Version:    0.6.0
 */
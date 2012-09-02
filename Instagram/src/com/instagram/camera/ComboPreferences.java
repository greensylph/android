package com.instagram.camera;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ComboPreferences
  implements SharedPreferences, SharedPreferences.OnSharedPreferenceChangeListener
{
  private static WeakHashMap<Context, ComboPreferences> sMap = new WeakHashMap();
  private CopyOnWriteArrayList<SharedPreferences.OnSharedPreferenceChangeListener> mListeners;
  private SharedPreferences mPrefGlobal;
  private SharedPreferences mPrefLocal;

  public ComboPreferences(Context paramContext)
  {
    this.mPrefGlobal = PreferenceManager.getDefaultSharedPreferences(paramContext);
    this.mPrefGlobal.registerOnSharedPreferenceChangeListener(this);
    synchronized (sMap)
    {
      sMap.put(paramContext, this);
      this.mListeners = new CopyOnWriteArrayList();
      return;
    }
  }

  public static ComboPreferences get(Context paramContext)
  {
    synchronized (sMap)
    {
      ComboPreferences localComboPreferences = (ComboPreferences)sMap.get(paramContext);
      return localComboPreferences;
    }
  }

  private static boolean isGlobal(String paramString)
  {
    return paramString.equals("pref_camera_id_key");
  }

  public boolean contains(String paramString)
  {
    int i = 1;
    if (this.mPrefLocal.contains(paramString));
    while (true)
    {
      return i;
      if (this.mPrefGlobal.contains(paramString))
        continue;
      i = 0;
    }
  }

  public SharedPreferences.Editor edit()
  {
    return new MyEditor();
  }

  public Map<String, ?> getAll()
  {
    throw new UnsupportedOperationException();
  }

  public boolean getBoolean(String paramString, boolean paramBoolean)
  {
    if ((isGlobal(paramString)) || (!this.mPrefLocal.contains(paramString)));
    for (boolean bool = this.mPrefGlobal.getBoolean(paramString, paramBoolean); ; bool = this.mPrefLocal.getBoolean(paramString, paramBoolean))
      return bool;
  }

  public float getFloat(String paramString, float paramFloat)
  {
    float f;
    if ((isGlobal(paramString)) || (!this.mPrefLocal.contains(paramString)))
      f = this.mPrefGlobal.getFloat(paramString, paramFloat);
    while (true)
    {
      return f;
      f = this.mPrefLocal.getFloat(paramString, paramFloat);
    }
  }

  public SharedPreferences getGlobal()
  {
    return this.mPrefGlobal;
  }

  public int getInt(String paramString, int paramInt)
  {
    if ((isGlobal(paramString)) || (!this.mPrefLocal.contains(paramString)));
    for (int i = this.mPrefGlobal.getInt(paramString, paramInt); ; i = this.mPrefLocal.getInt(paramString, paramInt))
      return i;
  }

  public SharedPreferences getLocal()
  {
    return this.mPrefLocal;
  }

  public long getLong(String paramString, long paramLong)
  {
    long l;
    if ((isGlobal(paramString)) || (!this.mPrefLocal.contains(paramString)))
      l = this.mPrefGlobal.getLong(paramString, paramLong);
    while (true)
    {
      return l;
      l = this.mPrefLocal.getLong(paramString, paramLong);
    }
  }

  public String getString(String paramString1, String paramString2)
  {
    if ((isGlobal(paramString1)) || (!this.mPrefLocal.contains(paramString1)));
    for (String str = this.mPrefGlobal.getString(paramString1, paramString2); ; str = this.mPrefLocal.getString(paramString1, paramString2))
      return str;
  }

  public Set<String> getStringSet(String paramString, Set<String> paramSet)
  {
    throw new UnsupportedOperationException();
  }

  public void onSharedPreferenceChanged(SharedPreferences paramSharedPreferences, String paramString)
  {
    Iterator localIterator = this.mListeners.iterator();
    while (localIterator.hasNext())
      ((SharedPreferences.OnSharedPreferenceChangeListener)localIterator.next()).onSharedPreferenceChanged(this, paramString);
  }

  public void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener paramOnSharedPreferenceChangeListener)
  {
    this.mListeners.add(paramOnSharedPreferenceChangeListener);
  }

  public void setLocalId(Context paramContext, int paramInt)
  {
    String str = paramContext.getPackageName() + "_preferences_" + paramInt;
    if (this.mPrefLocal != null)
      this.mPrefLocal.unregisterOnSharedPreferenceChangeListener(this);
    this.mPrefLocal = paramContext.getSharedPreferences(str, 0);
    this.mPrefLocal.registerOnSharedPreferenceChangeListener(this);
  }

  public void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener paramOnSharedPreferenceChangeListener)
  {
    this.mListeners.remove(paramOnSharedPreferenceChangeListener);
  }

  private class MyEditor
    implements SharedPreferences.Editor
  {
    private SharedPreferences.Editor mEditorGlobal = ComboPreferences.this.mPrefGlobal.edit();
    private SharedPreferences.Editor mEditorLocal = ComboPreferences.this.mPrefLocal.edit();

    MyEditor()
    {
    }

    @TargetApi(9)
    public void apply()
    {
      this.mEditorGlobal.apply();
      this.mEditorLocal.apply();
    }

    public SharedPreferences.Editor clear()
    {
      this.mEditorGlobal.clear();
      this.mEditorLocal.clear();
      return this;
    }

    public boolean commit()
    {
      boolean bool1 = this.mEditorGlobal.commit();
      boolean bool2 = this.mEditorLocal.commit();
      if ((bool1) && (bool2));
      for (int i = 1; ; i = 0)
        return i;
    }

    public SharedPreferences.Editor putBoolean(String paramString, boolean paramBoolean)
    {
      if (ComboPreferences.access$200(paramString))
        this.mEditorGlobal.putBoolean(paramString, paramBoolean);
      while (true)
      {
        return this;
        this.mEditorLocal.putBoolean(paramString, paramBoolean);
      }
    }

    public SharedPreferences.Editor putFloat(String paramString, float paramFloat)
    {
      if (ComboPreferences.access$200(paramString))
        this.mEditorGlobal.putFloat(paramString, paramFloat);
      while (true)
      {
        return this;
        this.mEditorLocal.putFloat(paramString, paramFloat);
      }
    }

    public SharedPreferences.Editor putInt(String paramString, int paramInt)
    {
      if (ComboPreferences.access$200(paramString))
        this.mEditorGlobal.putInt(paramString, paramInt);
      while (true)
      {
        return this;
        this.mEditorLocal.putInt(paramString, paramInt);
      }
    }

    public SharedPreferences.Editor putLong(String paramString, long paramLong)
    {
      if (ComboPreferences.access$200(paramString))
        this.mEditorGlobal.putLong(paramString, paramLong);
      while (true)
      {
        return this;
        this.mEditorLocal.putLong(paramString, paramLong);
      }
    }

    public SharedPreferences.Editor putString(String paramString1, String paramString2)
    {
      if (ComboPreferences.access$200(paramString1))
        this.mEditorGlobal.putString(paramString1, paramString2);
      while (true)
      {
        return this;
        this.mEditorLocal.putString(paramString1, paramString2);
      }
    }

    public SharedPreferences.Editor putStringSet(String paramString, Set<String> paramSet)
    {
      throw new UnsupportedOperationException();
    }

    public SharedPreferences.Editor remove(String paramString)
    {
      this.mEditorGlobal.remove(paramString);
      this.mEditorLocal.remove(paramString);
      return this;
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.camera.ComboPreferences
 * JD-Core Version:    0.6.0
 */
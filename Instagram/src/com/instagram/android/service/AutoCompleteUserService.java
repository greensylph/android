package com.instagram.android.service;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import com.instagram.android.Log;
import com.instagram.android.adapter.filter.UsernameAutoCompleteFilter;
import com.instagram.android.model.AutoCompleteUser;
import com.instagram.android.model.User;
import com.instagram.android.model.User.FollowStatus;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class AutoCompleteUserService extends IntentService
{
  private static final String EXPIRES = "EXPIRES_DATE";
  public static final String LOG_TAG = "AutoCompleteUserService";

  public AutoCompleteUserService()
  {
    super(AutoCompleteUserService.class.toString());
  }

  public static void addUser(AutoCompleteUser paramAutoCompleteUser)
  {
    try
    {
      getUserPrefs().edit().putString(paramAutoCompleteUser.getId(), paramAutoCompleteUser.getUserSerialized()).commit();
      UsernameAutoCompleteFilter.addUser(paramAutoCompleteUser);
      return;
    }
    catch (IOException localIOException)
    {
      while (true)
        Log.d("AutoCompleteUserService", "Unable to serialize user: " + paramAutoCompleteUser.getId());
    }
  }

  public static void addUsers(ArrayList<AutoCompleteUser> paramArrayList)
  {
    SharedPreferences.Editor localEditor = getUserPrefs().edit();
    Iterator localIterator = paramArrayList.iterator();
    while (localIterator.hasNext())
    {
      AutoCompleteUser localAutoCompleteUser = (AutoCompleteUser)localIterator.next();
      try
      {
        localEditor.putString(localAutoCompleteUser.getId(), localAutoCompleteUser.getUserSerialized());
        UsernameAutoCompleteFilter.addUser(localAutoCompleteUser);
      }
      catch (IOException localIOException)
      {
        Log.d("AutoCompleteUserService", "Unable to deserialize user: " + localAutoCompleteUser.getId());
      }
    }
    localEditor.commit();
  }

  public static void clear()
  {
    getUserPrefs().edit().clear().commit();
    UsernameAutoCompleteFilter.clear();
  }

  private static SharedPreferences getUserPrefs()
  {
    return AppContext.getContext().getSharedPreferences("autoCompleteUserStore", 0);
  }

  private boolean loadCachedSetFromDisk()
    throws IOException
  {
    SharedPreferences localSharedPreferences = getUserPrefs();
    if ((localSharedPreferences.contains("EXPIRES_DATE")) && (new Date().getTime() / 1000L < localSharedPreferences.getLong("EXPIRES_DATE", -1L)))
    {
      Iterator localIterator = localSharedPreferences.getAll().entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        if (localEntry.getKey().equals("EXPIRES_DATE"))
          continue;
        UsernameAutoCompleteFilter.addUser(AutoCompleteUser.getUserFromSerializedData((String)localEntry.getValue()));
      }
    }
    for (int i = 1; ; i = 0)
    {
      return i;
      clear();
    }
  }

  // ERROR //
  private void loadCachedSetFromNetwork()
  {
    // Byte code:
    //   0: aload_0
    //   1: invokestatic 181	com/instagram/api/ApiHttpClient:getInstance	(Landroid/content/Context;)Lcom/instagram/api/ApiHttpClient;
    //   4: ldc 183
    //   6: invokestatic 189	com/instagram/api/request/ApiUrlHelper:expandPath	(Ljava/lang/String;)Ljava/lang/String;
    //   9: invokevirtual 193	com/instagram/api/ApiHttpClient:get	(Ljava/lang/String;)Lch/boye/httpclientandroidlib/HttpResponse;
    //   12: astore_1
    //   13: aconst_null
    //   14: astore_2
    //   15: aconst_null
    //   16: astore_3
    //   17: new 82	java/util/ArrayList
    //   20: dup
    //   21: invokespecial 194	java/util/ArrayList:<init>	()V
    //   24: astore 4
    //   26: aload_1
    //   27: invokeinterface 200 1 0
    //   32: astore 14
    //   34: new 202	org/codehaus/jackson/JsonFactory
    //   37: dup
    //   38: invokespecial 203	org/codehaus/jackson/JsonFactory:<init>	()V
    //   41: astore 15
    //   43: aload 14
    //   45: invokeinterface 209 1 0
    //   50: astore_3
    //   51: aload 15
    //   53: aload_3
    //   54: invokevirtual 213	org/codehaus/jackson/JsonFactory:createJsonParser	(Ljava/io/InputStream;)Lorg/codehaus/jackson/JsonParser;
    //   57: astore_2
    //   58: aload_2
    //   59: invokevirtual 219	org/codehaus/jackson/JsonParser:nextToken	()Lorg/codehaus/jackson/JsonToken;
    //   62: getstatic 225	org/codehaus/jackson/JsonToken:END_OBJECT	Lorg/codehaus/jackson/JsonToken;
    //   65: if_acmpeq +159 -> 224
    //   68: aload_2
    //   69: invokevirtual 228	org/codehaus/jackson/JsonParser:getCurrentName	()Ljava/lang/String;
    //   72: astore 18
    //   74: ldc 230
    //   76: aload 18
    //   78: invokevirtual 231	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   81: ifeq +70 -> 151
    //   84: aload_2
    //   85: invokevirtual 219	org/codehaus/jackson/JsonParser:nextToken	()Lorg/codehaus/jackson/JsonToken;
    //   88: pop
    //   89: aload_2
    //   90: invokevirtual 234	org/codehaus/jackson/JsonParser:getCurrentToken	()Lorg/codehaus/jackson/JsonToken;
    //   93: getstatic 237	org/codehaus/jackson/JsonToken:VALUE_NULL	Lorg/codehaus/jackson/JsonToken;
    //   96: if_acmpeq -38 -> 58
    //   99: aload_2
    //   100: invokevirtual 219	org/codehaus/jackson/JsonParser:nextToken	()Lorg/codehaus/jackson/JsonToken;
    //   103: getstatic 225	org/codehaus/jackson/JsonToken:END_OBJECT	Lorg/codehaus/jackson/JsonToken;
    //   106: if_acmpeq -48 -> 58
    //   109: aload_2
    //   110: invokevirtual 240	org/codehaus/jackson/JsonParser:getText	()Ljava/lang/String;
    //   113: pop
    //   114: aload_2
    //   115: invokevirtual 219	org/codehaus/jackson/JsonParser:nextToken	()Lorg/codehaus/jackson/JsonToken;
    //   118: pop
    //   119: aload 4
    //   121: aload_2
    //   122: invokestatic 244	com/instagram/android/model/AutoCompleteUser:fromJsonParser	(Lorg/codehaus/jackson/JsonParser;)Lcom/instagram/android/model/AutoCompleteUser;
    //   125: invokevirtual 247	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   128: pop
    //   129: goto -30 -> 99
    //   132: astore 11
    //   134: aload_2
    //   135: ifnull +7 -> 142
    //   138: aload_2
    //   139: invokevirtual 250	org/codehaus/jackson/JsonParser:close	()V
    //   142: aload_3
    //   143: ifnull +7 -> 150
    //   146: aload_3
    //   147: invokevirtual 253	java/io/InputStream:close	()V
    //   150: return
    //   151: ldc 255
    //   153: aload 18
    //   155: invokevirtual 231	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   158: ifeq -100 -> 58
    //   161: aload_2
    //   162: invokevirtual 219	org/codehaus/jackson/JsonParser:nextToken	()Lorg/codehaus/jackson/JsonToken;
    //   165: pop
    //   166: aload_2
    //   167: invokevirtual 258	org/codehaus/jackson/JsonParser:getLongValue	()J
    //   170: lstore 20
    //   172: invokestatic 33	com/instagram/android/service/AutoCompleteUserService:getUserPrefs	()Landroid/content/SharedPreferences;
    //   175: invokeinterface 39 1 0
    //   180: ldc 11
    //   182: lload 20
    //   184: invokeinterface 262 4 0
    //   189: invokeinterface 57 1 0
    //   194: pop
    //   195: goto -137 -> 58
    //   198: astore 8
    //   200: aload_2
    //   201: ifnull +7 -> 208
    //   204: aload_2
    //   205: invokevirtual 250	org/codehaus/jackson/JsonParser:close	()V
    //   208: aload_3
    //   209: ifnull -59 -> 150
    //   212: aload_3
    //   213: invokevirtual 253	java/io/InputStream:close	()V
    //   216: goto -66 -> 150
    //   219: astore 9
    //   221: goto -71 -> 150
    //   224: aload 4
    //   226: invokestatic 264	com/instagram/android/service/AutoCompleteUserService:addUsers	(Ljava/util/ArrayList;)V
    //   229: aload_2
    //   230: ifnull +7 -> 237
    //   233: aload_2
    //   234: invokevirtual 250	org/codehaus/jackson/JsonParser:close	()V
    //   237: aload_3
    //   238: ifnull -88 -> 150
    //   241: aload_3
    //   242: invokevirtual 253	java/io/InputStream:close	()V
    //   245: goto -95 -> 150
    //   248: astore 16
    //   250: goto -100 -> 150
    //   253: astore 5
    //   255: aload_2
    //   256: ifnull +7 -> 263
    //   259: aload_2
    //   260: invokevirtual 250	org/codehaus/jackson/JsonParser:close	()V
    //   263: aload_3
    //   264: ifnull +7 -> 271
    //   267: aload_3
    //   268: invokevirtual 253	java/io/InputStream:close	()V
    //   271: aload 5
    //   273: athrow
    //   274: astore 17
    //   276: goto -39 -> 237
    //   279: astore 13
    //   281: goto -139 -> 142
    //   284: astore 12
    //   286: goto -136 -> 150
    //   289: astore 10
    //   291: goto -83 -> 208
    //   294: astore 7
    //   296: goto -33 -> 263
    //   299: astore 6
    //   301: goto -30 -> 271
    //
    // Exception table:
    //   from	to	target	type
    //   26	129	132	org/codehaus/jackson/JsonParseException
    //   151	195	132	org/codehaus/jackson/JsonParseException
    //   224	229	132	org/codehaus/jackson/JsonParseException
    //   26	129	198	java/io/IOException
    //   151	195	198	java/io/IOException
    //   224	229	198	java/io/IOException
    //   212	216	219	java/lang/Exception
    //   241	245	248	java/lang/Exception
    //   26	129	253	finally
    //   151	195	253	finally
    //   224	229	253	finally
    //   233	237	274	java/lang/Exception
    //   138	142	279	java/lang/Exception
    //   146	150	284	java/lang/Exception
    //   204	208	289	java/lang/Exception
    //   259	263	294	java/lang/Exception
    //   267	271	299	java/lang/Exception
  }

  public static void removeUser(AutoCompleteUser paramAutoCompleteUser)
  {
    getUserPrefs().edit().remove(paramAutoCompleteUser.getId()).commit();
    UsernameAutoCompleteFilter.removeUser(paramAutoCompleteUser);
  }

  protected void onHandleIntent(Intent paramIntent)
  {
    try
    {
      if (!loadCachedSetFromDisk())
        loadCachedSetFromNetwork();
      return;
    }
    catch (IOException localIOException)
    {
      while (true)
        Log.d("AutoCompleteUserService", "An IOException occured reading from disk");
    }
    catch (Exception localException)
    {
      while (true)
        Log.d("AutoCompleteUserService", "An exception occurred fetching autocomplete users");
    }
  }

  public static class AutoCompleteUpdateReceiver extends BroadcastReceiver
  {
    public void onReceive(Context paramContext, Intent paramIntent)
    {
      String str = paramIntent.getExtras().getString("id");
      User localUser = (User)UserStore.getInstance(paramContext).get(str);
      if ((localUser != null) && (localUser.getLastFollowStatus() != null) && (localUser.getLastFollowStatus() != localUser.getFollowStatus()) && ((localUser.getLastFollowStatus() == User.FollowStatus.FollowStatusRequested) || (localUser.getLastFollowStatus() == User.FollowStatus.FollowStatusFollowing) || (localUser.getLastFollowStatus() == User.FollowStatus.FollowStatusNotFollowing)))
      {
        if (localUser.getFollowStatus() != User.FollowStatus.FollowStatusFollowing)
          break label106;
        AutoCompleteUserService.addUser(new AutoCompleteUser(localUser));
      }
      while (true)
      {
        return;
        label106: if (localUser.getFollowStatus() == User.FollowStatus.FollowStatusNotFollowing)
        {
          AutoCompleteUserService.removeUser(new AutoCompleteUser(localUser));
          continue;
        }
      }
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.service.AutoCompleteUserService
 * JD-Core Version:    0.6.0
 */
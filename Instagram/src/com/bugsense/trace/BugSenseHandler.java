package com.bugsense.trace;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import java.io.File;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class BugSenseHandler
{
  private static Context gContext;
  private static int sMinDelay;
  private static boolean sSetupCalled;
  private static ArrayList<String[]> sStackTraces = null;
  private static ActivityAsyncTask<Processor, Object, Object, Object> sTask;
  private static int sTimeout;
  private static boolean sVerbose = false;

  static
  {
    sMinDelay = 0;
    sTimeout = 1;
    sSetupCalled = false;
    gContext = null;
  }

  private static String CheckNetworkConnection(String paramString)
  {
    if (gContext.getPackageManager().checkPermission("android.permission.ACCESS_NETWORK_STATE", G.APP_PACKAGE) == 0)
    {
      NetworkInfo[] arrayOfNetworkInfo = ((ConnectivityManager)gContext.getSystemService("connectivity")).getAllNetworkInfo();
      int i = arrayOfNetworkInfo.length;
      str = "false";
      for (int j = 0; j < i; j++)
      {
        NetworkInfo localNetworkInfo = arrayOfNetworkInfo[j];
        if ((!localNetworkInfo.getTypeName().equalsIgnoreCase(paramString)) || (!localNetworkInfo.isConnected()))
          continue;
        str = "true";
      }
    }
    String str = "not available [permissions]";
    return str;
  }

  public static String[] ScreenProperties()
  {
    String[] arrayOfString = new String[5];
    arrayOfString[0] = "Not available";
    arrayOfString[1] = "Not available";
    arrayOfString[2] = "Not available";
    arrayOfString[3] = "Not available";
    arrayOfString[4] = "Not available";
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    gContext.getPackageManager();
    Display localDisplay = ((WindowManager)gContext.getSystemService("window")).getDefaultDisplay();
    int i = localDisplay.getWidth();
    int j = localDisplay.getHeight();
    Log.i(G.TAG, Build.VERSION.RELEASE);
    int k = localDisplay.getOrientation();
    arrayOfString[0] = Integer.toString(i);
    arrayOfString[1] = Integer.toString(j);
    String str = "";
    switch (k)
    {
    default:
    case 0:
    case 2:
    case 3:
    case 1:
    }
    while (true)
    {
      arrayOfString[2] = str;
      localDisplay.getMetrics(localDisplayMetrics);
      arrayOfString[3] = Float.toString(localDisplayMetrics.xdpi);
      arrayOfString[4] = Float.toString(localDisplayMetrics.ydpi);
      return arrayOfString;
      str = "normal";
      continue;
      str = "180";
      continue;
      str = "270";
      continue;
      str = "90";
    }
  }

  public static void clear()
  {
    sStackTraces = null;
  }

  public static int getResId(String paramString, Context paramContext, Class<?> paramClass)
  {
    try
    {
      Field localField = paramClass.getDeclaredField(paramString);
      int j = localField.getInt(localField);
      i = j;
      return i;
    }
    catch (Exception localException)
    {
      while (true)
      {
        localException.printStackTrace();
        int i = -1;
      }
    }
  }

  // ERROR //
  private static ArrayList<String[]> getStackTraces()
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore_0
    //   2: getstatic 33	com/bugsense/trace/BugSenseHandler:sStackTraces	Ljava/util/ArrayList;
    //   5: ifnull +11 -> 16
    //   8: getstatic 33	com/bugsense/trace/BugSenseHandler:sStackTraces	Ljava/util/ArrayList;
    //   11: astore 11
    //   13: aload 11
    //   15: areturn
    //   16: getstatic 129	com/bugsense/trace/G:TAG	Ljava/lang/String;
    //   19: new 208	java/lang/StringBuilder
    //   22: dup
    //   23: invokespecial 209	java/lang/StringBuilder:<init>	()V
    //   26: ldc 211
    //   28: invokevirtual 215	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   31: getstatic 218	com/bugsense/trace/G:FILES_PATH	Ljava/lang/String;
    //   34: invokevirtual 215	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   37: invokevirtual 220	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   40: invokestatic 223	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   43: pop
    //   44: new 225	java/io/File
    //   47: dup
    //   48: new 208	java/lang/StringBuilder
    //   51: dup
    //   52: invokespecial 209	java/lang/StringBuilder:<init>	()V
    //   55: getstatic 218	com/bugsense/trace/G:FILES_PATH	Ljava/lang/String;
    //   58: invokevirtual 215	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   61: ldc 227
    //   63: invokevirtual 215	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   66: invokevirtual 220	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   69: invokespecial 230	java/io/File:<init>	(Ljava/lang/String;)V
    //   72: astore_2
    //   73: aload_2
    //   74: invokevirtual 233	java/io/File:exists	()Z
    //   77: ifne +8 -> 85
    //   80: aload_2
    //   81: invokevirtual 236	java/io/File:mkdir	()Z
    //   84: pop
    //   85: aload_2
    //   86: new 12	com/bugsense/trace/BugSenseHandler$4
    //   89: dup
    //   90: invokespecial 237	com/bugsense/trace/BugSenseHandler$4:<init>	()V
    //   93: invokevirtual 241	java/io/File:list	(Ljava/io/FilenameFilter;)[Ljava/lang/String;
    //   96: astore_3
    //   97: getstatic 129	com/bugsense/trace/G:TAG	Ljava/lang/String;
    //   100: new 208	java/lang/StringBuilder
    //   103: dup
    //   104: invokespecial 209	java/lang/StringBuilder:<init>	()V
    //   107: ldc 243
    //   109: invokevirtual 215	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   112: aload_3
    //   113: arraylength
    //   114: invokevirtual 246	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   117: ldc 248
    //   119: invokevirtual 215	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   122: invokevirtual 220	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   125: invokestatic 223	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   128: pop
    //   129: new 250	java/util/ArrayList
    //   132: dup
    //   133: invokespecial 251	java/util/ArrayList:<init>	()V
    //   136: putstatic 33	com/bugsense/trace/BugSenseHandler:sStackTraces	Ljava/util/ArrayList;
    //   139: iconst_0
    //   140: istore 9
    //   142: iload 9
    //   144: aload_3
    //   145: arraylength
    //   146: if_icmpge +13 -> 159
    //   149: getstatic 33	com/bugsense/trace/BugSenseHandler:sStackTraces	Ljava/util/ArrayList;
    //   152: invokevirtual 254	java/util/ArrayList:size	()I
    //   155: iconst_5
    //   156: if_icmplt +58 -> 214
    //   159: getstatic 33	com/bugsense/trace/BugSenseHandler:sStackTraces	Ljava/util/ArrayList;
    //   162: astore 10
    //   164: iload_0
    //   165: aload_3
    //   166: arraylength
    //   167: if_icmpge +403 -> 570
    //   170: new 225	java/io/File
    //   173: dup
    //   174: new 208	java/lang/StringBuilder
    //   177: dup
    //   178: invokespecial 209	java/lang/StringBuilder:<init>	()V
    //   181: getstatic 218	com/bugsense/trace/G:FILES_PATH	Ljava/lang/String;
    //   184: invokevirtual 215	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   187: ldc 227
    //   189: invokevirtual 215	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   192: aload_3
    //   193: iload_0
    //   194: aaload
    //   195: invokevirtual 215	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   198: invokevirtual 220	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   201: invokespecial 230	java/io/File:<init>	(Ljava/lang/String;)V
    //   204: invokevirtual 257	java/io/File:delete	()Z
    //   207: pop
    //   208: iinc 0 1
    //   211: goto -47 -> 164
    //   214: new 208	java/lang/StringBuilder
    //   217: dup
    //   218: invokespecial 209	java/lang/StringBuilder:<init>	()V
    //   221: getstatic 218	com/bugsense/trace/G:FILES_PATH	Ljava/lang/String;
    //   224: invokevirtual 215	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   227: ldc 227
    //   229: invokevirtual 215	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   232: aload_3
    //   233: iload 9
    //   235: aaload
    //   236: invokevirtual 215	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   239: invokevirtual 220	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   242: astore 15
    //   244: aload_3
    //   245: iload 9
    //   247: aaload
    //   248: ldc_w 259
    //   251: invokevirtual 263	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   254: iconst_0
    //   255: aaload
    //   256: astore 20
    //   258: getstatic 129	com/bugsense/trace/G:TAG	Ljava/lang/String;
    //   261: new 208	java/lang/StringBuilder
    //   264: dup
    //   265: invokespecial 209	java/lang/StringBuilder:<init>	()V
    //   268: ldc_w 265
    //   271: invokevirtual 215	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   274: aload 15
    //   276: invokevirtual 215	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   279: ldc_w 267
    //   282: invokevirtual 215	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   285: aload 20
    //   287: invokevirtual 215	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   290: invokevirtual 220	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   293: invokestatic 223	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   296: pop
    //   297: new 208	java/lang/StringBuilder
    //   300: dup
    //   301: invokespecial 209	java/lang/StringBuilder:<init>	()V
    //   304: astore 22
    //   306: new 269	java/io/BufferedReader
    //   309: dup
    //   310: new 271	java/io/FileReader
    //   313: dup
    //   314: aload 15
    //   316: invokespecial 272	java/io/FileReader:<init>	(Ljava/lang/String;)V
    //   319: invokespecial 275	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   322: astore 23
    //   324: aconst_null
    //   325: astore 24
    //   327: aconst_null
    //   328: astore 25
    //   330: aload 23
    //   332: invokevirtual 278	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   335: astore 27
    //   337: aload 27
    //   339: ifnull +68 -> 407
    //   342: aload 25
    //   344: ifnonnull +272 -> 616
    //   347: aload 27
    //   349: astore 25
    //   351: goto -21 -> 330
    //   354: aload 22
    //   356: aload 27
    //   358: invokevirtual 215	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   361: pop
    //   362: aload 22
    //   364: ldc_w 280
    //   367: invokestatic 285	java/lang/System:getProperty	(Ljava/lang/String;)Ljava/lang/String;
    //   370: invokevirtual 215	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   373: pop
    //   374: goto -44 -> 330
    //   377: astore 26
    //   379: aload 23
    //   381: invokevirtual 288	java/io/BufferedReader:close	()V
    //   384: aload 26
    //   386: athrow
    //   387: astore 18
    //   389: getstatic 129	com/bugsense/trace/G:TAG	Ljava/lang/String;
    //   392: ldc_w 290
    //   395: aload 18
    //   397: invokestatic 294	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   400: pop
    //   401: iinc 9 1
    //   404: goto -262 -> 142
    //   407: aload 23
    //   409: invokevirtual 288	java/io/BufferedReader:close	()V
    //   412: aload 22
    //   414: invokevirtual 220	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   417: astore 28
    //   419: getstatic 33	com/bugsense/trace/BugSenseHandler:sStackTraces	Ljava/util/ArrayList;
    //   422: astore 29
    //   424: iconst_4
    //   425: anewarray 90	java/lang/String
    //   428: astore 30
    //   430: aload 30
    //   432: iconst_0
    //   433: aload 20
    //   435: aastore
    //   436: aload 30
    //   438: iconst_1
    //   439: aload 25
    //   441: aastore
    //   442: aload 30
    //   444: iconst_2
    //   445: aload 24
    //   447: aastore
    //   448: aload 30
    //   450: iconst_3
    //   451: aload 28
    //   453: aastore
    //   454: aload 29
    //   456: aload 30
    //   458: invokevirtual 298	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   461: pop
    //   462: goto -61 -> 401
    //   465: astore 16
    //   467: getstatic 129	com/bugsense/trace/G:TAG	Ljava/lang/String;
    //   470: ldc_w 290
    //   473: aload 16
    //   475: invokestatic 294	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   478: pop
    //   479: goto -78 -> 401
    //   482: astore 5
    //   484: iload_0
    //   485: aload_3
    //   486: arraylength
    //   487: if_icmpge +126 -> 613
    //   490: new 225	java/io/File
    //   493: dup
    //   494: new 208	java/lang/StringBuilder
    //   497: dup
    //   498: invokespecial 209	java/lang/StringBuilder:<init>	()V
    //   501: getstatic 218	com/bugsense/trace/G:FILES_PATH	Ljava/lang/String;
    //   504: invokevirtual 215	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   507: ldc 227
    //   509: invokevirtual 215	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   512: aload_3
    //   513: iload_0
    //   514: aaload
    //   515: invokevirtual 215	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   518: invokevirtual 220	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   521: invokespecial 230	java/io/File:<init>	(Ljava/lang/String;)V
    //   524: invokevirtual 257	java/io/File:delete	()Z
    //   527: pop
    //   528: iinc 0 1
    //   531: goto -47 -> 484
    //   534: astore 12
    //   536: getstatic 129	com/bugsense/trace/G:TAG	Ljava/lang/String;
    //   539: new 208	java/lang/StringBuilder
    //   542: dup
    //   543: invokespecial 209	java/lang/StringBuilder:<init>	()V
    //   546: ldc_w 300
    //   549: invokevirtual 215	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   552: aload_3
    //   553: iload_0
    //   554: aaload
    //   555: invokevirtual 215	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   558: invokevirtual 220	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   561: aload 12
    //   563: invokestatic 294	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   566: pop
    //   567: goto -359 -> 208
    //   570: aload 10
    //   572: astore 11
    //   574: goto -561 -> 13
    //   577: astore 6
    //   579: getstatic 129	com/bugsense/trace/G:TAG	Ljava/lang/String;
    //   582: new 208	java/lang/StringBuilder
    //   585: dup
    //   586: invokespecial 209	java/lang/StringBuilder:<init>	()V
    //   589: ldc_w 300
    //   592: invokevirtual 215	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   595: aload_3
    //   596: iload_0
    //   597: aaload
    //   598: invokevirtual 215	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   601: invokevirtual 220	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   604: aload 6
    //   606: invokestatic 294	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   609: pop
    //   610: goto -82 -> 528
    //   613: aload 5
    //   615: athrow
    //   616: aload 24
    //   618: ifnonnull -264 -> 354
    //   621: aload 27
    //   623: astore 24
    //   625: goto -295 -> 330
    //
    // Exception table:
    //   from	to	target	type
    //   330	374	377	finally
    //   244	324	387	java/io/FileNotFoundException
    //   379	387	387	java/io/FileNotFoundException
    //   407	462	387	java/io/FileNotFoundException
    //   244	324	465	java/io/IOException
    //   379	387	465	java/io/IOException
    //   407	462	465	java/io/IOException
    //   129	164	482	finally
    //   214	244	482	finally
    //   244	324	482	finally
    //   379	387	482	finally
    //   389	401	482	finally
    //   407	462	482	finally
    //   467	479	482	finally
    //   170	208	534	java/lang/Exception
    //   490	528	577	java/lang/Exception
  }

  public static boolean hasStrackTraces()
  {
    if (getStackTraces().size() > 0);
    for (int i = 1; ; i = 0)
      return i;
  }

  private static void installHandler()
  {
    Thread.UncaughtExceptionHandler localUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
    if ((localUncaughtExceptionHandler != null) && (sVerbose))
      Log.d(G.TAG, "current handler class=" + localUncaughtExceptionHandler.getClass().getName());
    if (!(localUncaughtExceptionHandler instanceof DefaultExceptionHandler))
      Thread.setDefaultUncaughtExceptionHandler(new DefaultExceptionHandler(localUncaughtExceptionHandler));
  }

  public static String isGPSOn()
  {
    String str;
    if (gContext.getPackageManager().checkPermission("android.permission.ACCESS_FINE_LOCATION", G.APP_PACKAGE) == 0)
    {
      if (((LocationManager)gContext.getSystemService("location")).isProviderEnabled("gps"))
        break label50;
      str = "false";
    }
    while (true)
    {
      return str;
      str = "not available [permissions]";
      continue;
      label50: str = "true";
    }
  }

  public static String isMobileNetworkOn()
  {
    return CheckNetworkConnection("MOBILE");
  }

  public static String isWifiOn()
  {
    return CheckNetworkConnection("WIFI");
  }

  public static void log(String paramString, Exception paramException)
  {
    log(paramString, new HashMap(), paramException);
  }

  public static void log(String paramString, Map<String, String> paramMap, Exception paramException)
  {
    StringWriter localStringWriter = new StringWriter();
    PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
    if (G.API_KEY == null)
      Log.d(G.TAG, "Could not send: API KEY is missing");
    while (true)
    {
      return;
      Log.d(G.TAG, "Transmitting log data");
      try
      {
        paramException.printStackTrace(localPrintWriter);
        BugSense.submitError(0, null, localStringWriter.toString(), "LOG_" + paramString, paramMap);
      }
      catch (Exception localException)
      {
        Log.d(G.TAG, "Failed to transmit log data:");
      }
    }
  }

  public static void notifyContextGone()
  {
    if (sTask == null);
    while (true)
    {
      return;
      sTask.connectTo(null);
    }
  }

  public static void setHttpTimeout(int paramInt)
  {
    sTimeout = paramInt;
  }

  public static void setMinDelay(int paramInt)
  {
    sMinDelay = paramInt;
  }

  public static void setTag(String paramString)
  {
    G.TAG = paramString;
  }

  public static void setUrl(String paramString)
  {
    G.URL = paramString;
  }

  public static void setVerbose(boolean paramBoolean)
  {
    sVerbose = paramBoolean;
  }

  public static boolean setup(Context paramContext, Processor paramProcessor, String paramString)
  {
    boolean bool = false;
    G.API_KEY = paramString;
    gContext = paramContext;
    if (sSetupCalled)
      if ((sTask != null) && (!sTask.postProcessingDone()))
      {
        sTask.connectTo(null);
        sTask.connectTo(paramProcessor);
      }
    while (true)
    {
      return bool;
      sSetupCalled = true;
      Log.i(G.TAG, "Registering default exceptions handler");
      G.FILES_PATH = paramContext.getFilesDir().getAbsolutePath();
      G.PHONE_MODEL = Build.MODEL;
      G.ANDROID_VERSION = Build.VERSION.RELEASE;
      PackageManager localPackageManager = paramContext.getPackageManager();
      try
      {
        PackageInfo localPackageInfo = localPackageManager.getPackageInfo(paramContext.getPackageName(), 0);
        G.APP_VERSION = localPackageInfo.versionName;
        G.APP_PACKAGE = localPackageInfo.packageName;
        if (sVerbose)
        {
          Log.i(G.TAG, "TRACE_VERSION: " + G.TraceVersion);
          Log.d(G.TAG, "APP_VERSION: " + G.APP_VERSION);
          Log.d(G.TAG, "APP_PACKAGE: " + G.APP_PACKAGE);
          Log.d(G.TAG, "FILES_PATH: " + G.FILES_PATH);
          Log.d(G.TAG, "URL: " + G.URL);
        }
        getStackTraces();
        installHandler();
        paramProcessor.handlerInstalled();
        bool = submit(paramProcessor);
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        while (true)
          Log.e(G.TAG, "Error collecting trace information", localNameNotFoundException);
      }
    }
  }

  public static boolean setup(Context paramContext, String paramString)
  {
    return setup(paramContext, new Processor()
    {
      public boolean beginSubmit()
      {
        return true;
      }

      public void handlerInstalled()
      {
      }

      public void submitDone()
      {
      }
    }
    , paramString);
  }

  public static void showUpgradeNotification(String paramString)
  {
    try
    {
      Context localContext = gContext;
      NotificationManager localNotificationManager = (NotificationManager)gContext.getSystemService("notification");
      JSONObject localJSONObject = new JSONObject(new JSONObject(paramString).getString("data"));
      String str = localJSONObject.getString("tickerText");
      long l = System.currentTimeMillis();
      Notification localNotification = new Notification(gContext.getResources().getIdentifier("icon", "drawable", gContext.getPackageName()), str, l);
      localNotification.flags = 16;
      localNotification.setLatestEventInfo(localContext, localJSONObject.getString("contentTitle"), localJSONObject.getString("contentText"), PendingIntent.getActivity(localContext, 0, new Intent("android.intent.action.VIEW", Uri.parse(localJSONObject.getString("url"))), 268435456));
      localNotificationManager.notify(1, localNotification);
      return;
    }
    catch (Exception localException)
    {
      while (true)
        Log.e(G.TAG, "Error starting", localException);
    }
  }

  public static boolean submit()
  {
    return submit(new Processor()
    {
      public boolean beginSubmit()
      {
        return true;
      }

      public void handlerInstalled()
      {
      }

      public void submitDone()
      {
      }
    });
  }

  public static boolean submit(Processor paramProcessor)
  {
    if (!sSetupCalled)
      throw new RuntimeException("you need to call setup() first");
    boolean bool = hasStrackTraces();
    if ((bool) && (paramProcessor.beginSubmit()))
    {
      ArrayList localArrayList = sStackTraces;
      sStackTraces = null;
      sTask = new ActivityAsyncTask(paramProcessor, localArrayList)
      {
        private long mTimeStarted;

        protected Object doInBackground(Object[] paramArrayOfObject)
        {
          BugSenseHandler.access$000(this.val$tracesNowSubmitting);
          long l = BugSenseHandler.sMinDelay - (System.currentTimeMillis() - this.mTimeStarted);
          if (l > 0L);
          try
          {
            Thread.sleep(l);
            return null;
          }
          catch (InterruptedException localInterruptedException)
          {
            while (true)
              localInterruptedException.printStackTrace();
          }
        }

        protected void onCancelled()
        {
          super.onCancelled();
        }

        protected void onPreExecute()
        {
          super.onPreExecute();
          this.mTimeStarted = System.currentTimeMillis();
        }

        protected void processPostExecute(Object paramObject)
        {
          ((BugSenseHandler.Processor)this.mWrapped).submitDone();
        }
      };
      sTask.execute(new Object[0]);
    }
    return bool;
  }

  private static void submitStackTraces(ArrayList<String[]> paramArrayList)
  {
    if (paramArrayList == null);
    while (true)
    {
      return;
      int i = 0;
      try
      {
        while (i < paramArrayList.size())
        {
          String[] arrayOfString = (String[])paramArrayList.get(i);
          arrayOfString[0];
          arrayOfString[1];
          arrayOfString[2];
          String str = arrayOfString[3];
          BugSense.submitError(sTimeout, null, str);
          i++;
        }
      }
      catch (Exception localException)
      {
        Log.e(G.TAG, "Error submitting trace", localException);
      }
    }
  }

  public static abstract interface Processor
  {
    public abstract boolean beginSubmit();

    public abstract void handlerInstalled();

    public abstract void submitDone();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.bugsense.trace.BugSenseHandler
 * JD-Core Version:    0.6.0
 */
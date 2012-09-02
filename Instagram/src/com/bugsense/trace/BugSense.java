package com.bugsense.trace;

import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.json.JSONObject;

public class BugSense
{
  private static ActivityAsyncTask<Processor, Object, Object, Object> sTask;

  public static String MD5(String paramString)
    throws Exception
  {
    MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
    localMessageDigest.update(paramString.getBytes(), 0, paramString.length());
    return new BigInteger(1, localMessageDigest.digest()).toString(16);
  }

  public static String createJSON(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String[] paramArrayOfString, Date paramDate, String paramString9, Map<String, String> paramMap)
    throws Exception
  {
    JSONObject localJSONObject1 = new JSONObject();
    JSONObject localJSONObject2 = new JSONObject();
    JSONObject localJSONObject3 = new JSONObject();
    JSONObject localJSONObject4 = new JSONObject();
    JSONObject localJSONObject5 = new JSONObject();
    JSONObject localJSONObject6 = new JSONObject();
    localJSONObject2.put("remote_ip", "");
    localJSONObject2.put("tag", paramString9);
    localJSONObject1.put("request", localJSONObject2);
    BufferedReader localBufferedReader = new BufferedReader(new StringReader(paramString5));
    if (paramDate == null)
      localJSONObject3.put("occured_at", localBufferedReader.readLine());
    while (true)
    {
      localJSONObject3.put("message", localBufferedReader.readLine());
      String str = localBufferedReader.readLine();
      localJSONObject3.put("where", str.substring(1 + str.lastIndexOf("("), str.lastIndexOf(")")));
      localJSONObject3.put("klass", getClass(paramString5));
      localJSONObject3.put("backtrace", paramString5);
      localJSONObject1.put("exception", localJSONObject3);
      localBufferedReader.close();
      localJSONObject5.put("phone", paramString3);
      localJSONObject5.put("appver", paramString2);
      localJSONObject5.put("appname", paramString1);
      localJSONObject5.put("osver", paramString4);
      localJSONObject5.put("wifi_on", paramString6);
      localJSONObject5.put("mobile_net_on", paramString7);
      localJSONObject5.put("gps_on", paramString8);
      localJSONObject5.put("screen:width", paramArrayOfString[0]);
      localJSONObject5.put("screen:height", paramArrayOfString[1]);
      localJSONObject5.put("screen:orientation", paramArrayOfString[2]);
      localJSONObject5.put("screen_dpi(x:y)", paramArrayOfString[3] + ":" + paramArrayOfString[4]);
      if ((paramMap == null) || (paramMap.isEmpty()))
        break;
      Iterator localIterator = paramMap.entrySet().iterator();
      while (true)
        if (localIterator.hasNext())
        {
          Map.Entry localEntry = (Map.Entry)localIterator.next();
          localJSONObject4.put((String)localEntry.getKey(), localEntry.getValue());
          continue;
          localJSONObject3.put("occured_at", paramDate);
          break;
        }
      localJSONObject5.put("log_data", localJSONObject4);
    }
    localJSONObject1.put("application_environment", localJSONObject5);
    localJSONObject6.put("version", "bugsense-version-0.6");
    localJSONObject6.put("name", "bugsense-android");
    localJSONObject1.put("client", localJSONObject6);
    return localJSONObject1.toString();
  }

  public static String getClass(String paramString)
  {
    String str = "";
    int i = paramString.indexOf(":");
    if ((i != -1) && (i + 1 < paramString.length()))
      str = paramString.substring(0, i);
    return str;
  }

  private static void sendError(int paramInt, Date paramDate, String paramString1, String paramString2, Map<String, String> paramMap)
  {
    HttpEntity localHttpEntity;
    try
    {
      Log.d(G.TAG, "Transmitting stack trace: " + paramString1);
      DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
      HttpParams localHttpParams = localDefaultHttpClient.getParams();
      HttpProtocolParams.setUseExpectContinue(localHttpParams, false);
      if (paramInt != 0)
      {
        HttpConnectionParams.setConnectionTimeout(localHttpParams, paramInt);
        HttpConnectionParams.setSoTimeout(localHttpParams, paramInt);
      }
      HttpPost localHttpPost = new HttpPost(G.URL);
      localHttpPost.addHeader("X-BugSense-Api-Key", G.API_KEY);
      ArrayList localArrayList = new ArrayList();
      BasicNameValuePair localBasicNameValuePair = new BasicNameValuePair("data", createJSON(G.APP_PACKAGE, G.APP_VERSION, G.PHONE_MODEL, G.ANDROID_VERSION, paramString1, BugSenseHandler.isWifiOn(), BugSenseHandler.isMobileNetworkOn(), BugSenseHandler.isGPSOn(), BugSenseHandler.ScreenProperties(), paramDate, paramString2, paramMap));
      localArrayList.add(localBasicNameValuePair);
      localArrayList.add(new BasicNameValuePair("hash", MD5(paramString1)));
      localHttpPost.setEntity(new UrlEncodedFormEntity(localArrayList, "UTF-8"));
      Log.d(G.TAG, "Ready to send report");
      localHttpEntity = localDefaultHttpClient.execute(localHttpPost).getEntity();
      if (localHttpEntity == null)
        throw new Exception("no internet connection");
    }
    catch (Exception localException)
    {
      Log.e(G.TAG, "Error sending exception stacktrace", localException);
    }
    while (true)
    {
      return;
      BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localHttpEntity.getContent()));
      String str = localBufferedReader.readLine();
      localBufferedReader.close();
      Log.d("BUGSENSE", str);
      if (str.contains("[]"))
        continue;
      BugSenseHandler.showUpgradeNotification(str);
    }
  }

  public static void submitError(int paramInt, Date paramDate, String paramString)
    throws Exception
  {
    submitError(paramInt, paramDate, paramString, "", new HashMap());
  }

  public static void submitError(int paramInt, Date paramDate, String paramString1, String paramString2, Map<String, String> paramMap)
    throws Exception
  {
    long l1 = System.currentTimeMillis();
    String str = Thread.currentThread().getName();
    if (!str.equals("main"))
    {
      Log.d(G.TAG, "Error in thread: " + str);
      sendError(paramInt, paramDate, paramString1, "", paramMap);
    }
    while (true)
    {
      return;
      sTask = new ActivityAsyncTask(new Processor()
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
      , paramInt, paramDate, paramString1, paramMap)
      {
        protected Object doInBackground(Object[] paramArrayOfObject)
        {
          BugSense.access$000(this.val$sTimeout, this.val$occuredAt, this.val$stacktrace, "", this.val$extraData);
          return null;
        }

        protected void onCancelled()
        {
          super.onCancelled();
        }

        protected void onPreExecute()
        {
          super.onPreExecute();
        }

        protected void processPostExecute(Object paramObject)
        {
          ((BugSense.Processor)this.mWrapped).submitDone();
        }
      };
      sTask.execute(new Object[0]);
      long l2 = 2000L - (System.currentTimeMillis() - l1);
      if (l2 <= 0L)
        continue;
      try
      {
        Thread.sleep(l2);
      }
      catch (InterruptedException localInterruptedException)
      {
        localInterruptedException.printStackTrace();
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
 * Qualified Name:     com.bugsense.trace.BugSense
 * JD-Core Version:    0.6.0
 */
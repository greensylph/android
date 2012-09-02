package com.instagram.android.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import com.instagram.android.Log;

public class TumblrService extends IntentService
{
  private static final String INTENT_EXTRA_SERVICE_ACTION = "com.instagram.android.TumblrService.INTENT_EXTRA_SERVICE_ACTION";
  private static final int SERVICE_ACTION_FOLLOW_INSTAGRAM = 0;
  private static final String TAG = "TumblrService";

  public TumblrService()
  {
    super("TumblrService");
  }

  // ERROR //
  private void followInstagramBlog()
    throws TumblrService.TumblrAccountNotFoundException
  {
    // Byte code:
    //   0: new 33	org/apache/http/impl/client/DefaultHttpClient
    //   3: dup
    //   4: invokespecial 35	org/apache/http/impl/client/DefaultHttpClient:<init>	()V
    //   7: astore_1
    //   8: new 37	org/apache/http/client/methods/HttpPost
    //   11: dup
    //   12: ldc 39
    //   14: invokespecial 40	org/apache/http/client/methods/HttpPost:<init>	(Ljava/lang/String;)V
    //   17: astore_2
    //   18: aload_0
    //   19: invokestatic 46	com/instagram/tumblr/TumblrAccount:get	(Landroid/content/Context;)Lcom/instagram/tumblr/TumblrAccount;
    //   22: astore_3
    //   23: aload_3
    //   24: ifnonnull +13 -> 37
    //   27: new 8	com/instagram/android/service/TumblrService$TumblrAccountNotFoundException
    //   30: dup
    //   31: aload_0
    //   32: aconst_null
    //   33: invokespecial 49	com/instagram/android/service/TumblrService$TumblrAccountNotFoundException:<init>	(Lcom/instagram/android/service/TumblrService;Lcom/instagram/android/service/TumblrService$1;)V
    //   36: athrow
    //   37: new 51	oauth/signpost/commonshttp/CommonsHttpOAuthConsumer
    //   40: dup
    //   41: invokestatic 57	com/instagram/tumblr/TumblrConstants:getConsumerKey	()Ljava/lang/String;
    //   44: invokestatic 60	com/instagram/tumblr/TumblrConstants:getConsumerSecret	()Ljava/lang/String;
    //   47: invokespecial 63	oauth/signpost/commonshttp/CommonsHttpOAuthConsumer:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   50: astore 4
    //   52: aload 4
    //   54: aload_3
    //   55: invokevirtual 66	com/instagram/tumblr/TumblrAccount:getToken	()Ljava/lang/String;
    //   58: aload_3
    //   59: invokevirtual 69	com/instagram/tumblr/TumblrAccount:getSecret	()Ljava/lang/String;
    //   62: invokevirtual 72	oauth/signpost/commonshttp/CommonsHttpOAuthConsumer:setTokenWithSecret	(Ljava/lang/String;Ljava/lang/String;)V
    //   65: iconst_1
    //   66: anewarray 74	org/apache/http/message/BasicNameValuePair
    //   69: astore 5
    //   71: aload 5
    //   73: iconst_0
    //   74: new 74	org/apache/http/message/BasicNameValuePair
    //   77: dup
    //   78: ldc 76
    //   80: ldc 78
    //   82: invokespecial 79	org/apache/http/message/BasicNameValuePair:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   85: aastore
    //   86: aload 5
    //   88: invokestatic 85	java/util/Arrays:asList	([Ljava/lang/Object;)Ljava/util/List;
    //   91: astore 6
    //   93: new 87	org/apache/http/client/entity/UrlEncodedFormEntity
    //   96: dup
    //   97: aload 6
    //   99: ldc 89
    //   101: invokespecial 92	org/apache/http/client/entity/UrlEncodedFormEntity:<init>	(Ljava/util/List;Ljava/lang/String;)V
    //   104: astore 7
    //   106: aload_2
    //   107: aload 7
    //   109: invokevirtual 96	org/apache/http/client/methods/HttpPost:setEntity	(Lorg/apache/http/HttpEntity;)V
    //   112: aload 4
    //   114: aload_2
    //   115: invokevirtual 100	oauth/signpost/commonshttp/CommonsHttpOAuthConsumer:sign	(Ljava/lang/Object;)Loauth/signpost/http/HttpRequest;
    //   118: pop
    //   119: aload_1
    //   120: aload_2
    //   121: invokeinterface 106 2 0
    //   126: invokeinterface 112 1 0
    //   131: pop
    //   132: return
    //   133: astore 12
    //   135: new 114	java/lang/RuntimeException
    //   138: dup
    //   139: ldc 116
    //   141: invokespecial 117	java/lang/RuntimeException:<init>	(Ljava/lang/String;)V
    //   144: athrow
    //   145: astore 8
    //   147: aload 8
    //   149: invokevirtual 120	oauth/signpost/exception/OAuthException:printStackTrace	()V
    //   152: goto -33 -> 119
    //   155: astore 9
    //   157: aload 9
    //   159: invokevirtual 121	java/io/IOException:printStackTrace	()V
    //   162: goto -30 -> 132
    //
    // Exception table:
    //   from	to	target	type
    //   93	106	133	java/io/UnsupportedEncodingException
    //   112	119	145	oauth/signpost/exception/OAuthException
    //   119	132	155	java/io/IOException
  }

  public static void followInstagramBlog(Context paramContext)
  {
    startService(paramContext, 0);
  }

  private int getAction(Intent paramIntent)
  {
    return paramIntent.getIntExtra("com.instagram.android.TumblrService.INTENT_EXTRA_SERVICE_ACTION", -1);
  }

  public static void startService(Context paramContext, int paramInt)
  {
    Context localContext = paramContext.getApplicationContext();
    Intent localIntent = new Intent(localContext, TumblrService.class);
    localIntent.putExtra("com.instagram.android.TumblrService.INTENT_EXTRA_SERVICE_ACTION", paramInt);
    localContext.startService(localIntent);
  }

  protected void onHandleIntent(Intent paramIntent)
  {
    switch (getAction(paramIntent))
    {
    default:
    case 0:
    }
    while (true)
    {
      return;
      try
      {
        followInstagramBlog();
      }
      catch (TumblrAccountNotFoundException localTumblrAccountNotFoundException)
      {
        Log.e("TumblrService", "Tumblr account not found", localTumblrAccountNotFoundException);
      }
    }
  }

  private class TumblrAccountNotFoundException extends Exception
  {
    private TumblrAccountNotFoundException()
    {
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.service.TumblrService
 * JD-Core Version:    0.6.0
 */
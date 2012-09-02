package com.instagram.util;

class NoopUtil$1
  implements Runnable
{
  // ERROR //
  public void run()
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: invokestatic 30	com/instagram/android/service/AppContext:getContext	()Landroid/content/Context;
    //   5: invokestatic 36	com/instagram/api/ApiHttpClient:getInstance	(Landroid/content/Context;)Lcom/instagram/api/ApiHttpClient;
    //   8: aload_0
    //   9: getfield 16	com/instagram/util/NoopUtil$1:val$url	Ljava/lang/String;
    //   12: invokevirtual 40	com/instagram/api/ApiHttpClient:get	(Ljava/lang/String;)Lch/boye/httpclientandroidlib/HttpResponse;
    //   15: astore 8
    //   17: aload 8
    //   19: astore_1
    //   20: aload_1
    //   21: ifnull +12 -> 33
    //   24: aload_1
    //   25: invokeinterface 46 1 0
    //   30: invokestatic 52	ch/boye/httpclientandroidlib/util/EntityUtils:consume	(Lch/boye/httpclientandroidlib/HttpEntity;)V
    //   33: ldc 54
    //   35: astore 6
    //   37: aload_1
    //   38: ifnull +28 -> 66
    //   41: aload_1
    //   42: invokeinterface 58 1 0
    //   47: ifnull +19 -> 66
    //   50: aload_1
    //   51: invokeinterface 58 1 0
    //   56: invokeinterface 64 1 0
    //   61: invokestatic 70	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   64: astore 6
    //   66: ldc 72
    //   68: new 74	java/lang/StringBuilder
    //   71: dup
    //   72: invokespecial 75	java/lang/StringBuilder:<init>	()V
    //   75: ldc 77
    //   77: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   80: aload_0
    //   81: getfield 16	com/instagram/util/NoopUtil$1:val$url	Ljava/lang/String;
    //   84: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   87: ldc 83
    //   89: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   92: aload 6
    //   94: invokevirtual 81	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   97: invokevirtual 87	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   100: invokestatic 93	com/instagram/android/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   103: pop
    //   104: return
    //   105: astore 4
    //   107: iconst_0
    //   108: ifeq -75 -> 33
    //   111: aconst_null
    //   112: invokeinterface 46 1 0
    //   117: invokestatic 52	ch/boye/httpclientandroidlib/util/EntityUtils:consume	(Lch/boye/httpclientandroidlib/HttpEntity;)V
    //   120: goto -87 -> 33
    //   123: astore 5
    //   125: goto -92 -> 33
    //   128: astore_2
    //   129: iconst_0
    //   130: ifeq +12 -> 142
    //   133: aconst_null
    //   134: invokeinterface 46 1 0
    //   139: invokestatic 52	ch/boye/httpclientandroidlib/util/EntityUtils:consume	(Lch/boye/httpclientandroidlib/HttpEntity;)V
    //   142: aload_2
    //   143: athrow
    //   144: astore_3
    //   145: goto -3 -> 142
    //   148: astore 9
    //   150: goto -117 -> 33
    //
    // Exception table:
    //   from	to	target	type
    //   2	17	105	java/lang/Exception
    //   111	120	123	java/io/IOException
    //   2	17	128	finally
    //   133	142	144	java/io/IOException
    //   24	33	148	java/io/IOException
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.util.NoopUtil.1
 * JD-Core Version:    0.6.0
 */
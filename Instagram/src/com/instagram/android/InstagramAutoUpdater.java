package com.instagram.android;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Handler;
import android.widget.Toast;
import ch.boye.httpclientandroidlib.HttpEntity;
import ch.boye.httpclientandroidlib.HttpResponse;
import ch.boye.httpclientandroidlib.StatusLine;
import ch.boye.httpclientandroidlib.client.HttpClient;
import ch.boye.httpclientandroidlib.client.methods.HttpGet;
import ch.boye.httpclientandroidlib.impl.client.DefaultHttpClient;
import ch.boye.httpclientandroidlib.util.EntityUtils;
import com.instagram.android.widget.IgDialogBuilder;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class InstagramAutoUpdater
{
  public static final String MANIFEST_URL = "https://s3.amazonaws.com/instagram-android/private_beta/MANIFEST";
  private static final String TAG = "InstagramAutoUpdater";
  public static final String UPDATE_APK_FILE_NAME = "Instagram.apk";
  private ProgressDialog mDownloadProgressDialog = null;
  private boolean mDownloadWasCanceled;
  private Handler mHandler = new Handler();
  private Manifest mManifest;
  private ProgressDialog mProgressDialog;
  private boolean mShowProgressDialog;

  public InstagramAutoUpdater()
  {
    this(false);
  }

  public InstagramAutoUpdater(boolean paramBoolean)
  {
    this.mShowProgressDialog = paramBoolean;
  }

  private void askToDownloadUpdate()
  {
    new IgDialogBuilder(getContext()).setTitle("New version available (" + this.mManifest.getVersionString() + ")").setMessage(this.mManifest.getReleaseNotes()).setPositiveButton(2131231049, new DownloadUpdateButtonListener(null)).setNeutralButton(2131231050, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        paramDialogInterface.dismiss();
      }
    }).create().show();
  }

  private void downloadUpdate()
  {
    if (this.mManifest == null)
      Toast.makeText(getContext(), "downloadUpdate() called with null mManifest", 0).show();
    while (true)
    {
      return;
      3 local3 = new AsyncHttpRequestTask(new ApkHttpRequestCallbacks(null))
      {
        protected void onPostExecute(Boolean paramBoolean)
        {
          super.onPostExecute(paramBoolean);
          if (InstagramAutoUpdater.this.mDownloadProgressDialog != null)
          {
            InstagramAutoUpdater.this.mDownloadProgressDialog.dismiss();
            InstagramAutoUpdater.access$702(InstagramAutoUpdater.this, null);
          }
        }

        protected void onPreExecute()
        {
          InstagramAutoUpdater.this.showDownloadProgressDialog();
        }
      };
      String[] arrayOfString = new String[1];
      arrayOfString[0] = this.mManifest.getApkUrlString();
      local3.execute(arrayOfString);
    }
  }

  private void fetchManifest()
  {
    AsyncHttpRequestTask localAsyncHttpRequestTask = new AsyncHttpRequestTask(new ManifestHttpRequestCallbacks(null));
    String[] arrayOfString = new String[1];
    arrayOfString[0] = "https://s3.amazonaws.com/instagram-android/private_beta/MANIFEST";
    localAsyncHttpRequestTask.execute(arrayOfString);
  }

  private void showDownloadProgressDialog()
  {
    if (this.mDownloadProgressDialog != null)
      this.mDownloadProgressDialog.dismiss();
    this.mDownloadProgressDialog = new ProgressDialog(getContext());
    this.mDownloadProgressDialog.setMessage("Downloading update...");
    this.mDownloadProgressDialog.setCancelable(false);
    this.mDownloadProgressDialog.setIndeterminate(false);
    this.mDownloadProgressDialog.setProgressStyle(1);
    this.mDownloadProgressDialog.setProgress(0);
    this.mDownloadProgressDialog.show();
  }

  public void checkForUpdates()
  {
    if (this.mShowProgressDialog)
    {
      this.mProgressDialog = ProgressDialog.show(getContext(), "", "Checking for updates...", true, true);
      this.mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener()
      {
        public void onCancel(DialogInterface paramDialogInterface)
        {
          InstagramAutoUpdater.access$402(InstagramAutoUpdater.this, true);
        }
      });
    }
    fetchManifest();
  }

  public abstract Context getContext();

  private class ApkHttpRequestCallbacks extends InstagramAutoUpdater.AsyncHttpRequestCallbacks<Boolean>
  {
    private ApkHttpRequestCallbacks()
    {
      super();
    }

    private void saveApkFile(HttpResponse paramHttpResponse)
      throws InstagramAutoUpdater.LogErrorException
    {
      HttpEntity localHttpEntity = null;
      FileOutputStream localFileOutputStream = null;
      try
      {
        localHttpEntity = paramHttpResponse.getEntity();
        StatusLine localStatusLine = paramHttpResponse.getStatusLine();
        if ((localStatusLine == null) || (localStatusLine.getStatusCode() != 200))
          throw new InstagramAutoUpdater.LogErrorException(InstagramAutoUpdater.this, "Error downloading APK file");
      }
      catch (IOException localIOException3)
      {
        throw new InstagramAutoUpdater.LogErrorException(InstagramAutoUpdater.this, "Problem saving file APK to disk", localIOException3);
      }
      finally
      {
        if (localHttpEntity == null);
      }
      try
      {
        EntityUtils.consume(localHttpEntity);
        if (localFileOutputStream == null);
      }
      catch (IOException localIOException2)
      {
        try
        {
          localFileOutputStream.close();
          label87: throw localObject;
          localFileOutputStream = InstagramAutoUpdater.this.getContext().openFileOutput("Instagram.apk", 1);
          saveBuffered(localHttpEntity, localFileOutputStream);
          if (localHttpEntity != null);
          try
          {
            EntityUtils.consume(localHttpEntity);
            if (localFileOutputStream == null);
          }
          catch (IOException localIOException5)
          {
            try
            {
              localFileOutputStream.close();
              label126: return;
              localIOException5 = localIOException5;
            }
            catch (IOException localIOException4)
            {
              break label126;
            }
          }
          localIOException2 = localIOException2;
        }
        catch (IOException localIOException1)
        {
          break label87;
        }
      }
    }

    @TargetApi(11)
    private void setProgressNumberFormat()
    {
      InstagramAutoUpdater.this.mDownloadProgressDialog.setProgressNumberFormat("%1d/%d KB");
    }

    public Boolean processResponseInBackground(HttpResponse paramHttpResponse)
    {
      try
      {
        saveApkFile(paramHttpResponse);
        Boolean localBoolean2 = Boolean.valueOf(true);
        localBoolean1 = localBoolean2;
        return localBoolean1;
      }
      catch (InstagramAutoUpdater.LogErrorException localLogErrorException)
      {
        while (true)
        {
          localLogErrorException.log();
          Boolean localBoolean1 = Boolean.valueOf(false);
        }
      }
    }

    void reportProgress(int paramInt)
    {
      if (InstagramAutoUpdater.this.mDownloadProgressDialog != null)
      {
        int i = paramInt * InstagramAutoUpdater.this.mDownloadProgressDialog.getMax() / 100;
        InstagramAutoUpdater.this.mDownloadProgressDialog.setProgress(i);
      }
    }

    public void requestFinished(Boolean paramBoolean)
    {
      if (!paramBoolean.booleanValue());
      while (true)
      {
        return;
        try
        {
          Intent localIntent = new Intent();
          String str = "file://" + InstagramAutoUpdater.this.getContext().getFilesDir().getAbsolutePath() + "/" + "Instagram.apk";
          localIntent.setAction("android.intent.action.VIEW");
          localIntent.setFlags(268435456);
          localIntent.setDataAndType(Uri.parse(str), "application/vnd.android.package-archive");
          InstagramAutoUpdater.this.getContext().startActivity(localIntent);
        }
        catch (Exception localException)
        {
          Toast.makeText(InstagramAutoUpdater.this.getContext(), "Update available, but failed to show alert dialog.", 0);
        }
      }
    }

    public void saveBuffered(HttpEntity paramHttpEntity, OutputStream paramOutputStream)
      throws IOException
    {
      if (paramHttpEntity == null)
        throw new IllegalArgumentException("HTTP entity may not be null");
      InputStream localInputStream = paramHttpEntity.getContent();
      if (localInputStream == null)
        return;
      try
      {
        if (paramHttpEntity.getContentLength() > 2147483647L)
          throw new IllegalArgumentException("HTTP entity too large to be buffered in memory");
      }
      finally
      {
        localInputStream.close();
      }
      int i = (int)paramHttpEntity.getContentLength();
      InstagramAutoUpdater.this.mDownloadProgressDialog.setMax(i / 1024);
      if (Build.VERSION.SDK_INT >= 11)
        setProgressNumberFormat();
      while (true)
      {
        label98: byte[] arrayOfByte = new byte[j];
        int k = 0;
        int m = 0;
        while (true)
        {
          int n = localInputStream.read(arrayOfByte);
          if (n == -1)
            break;
          paramOutputStream.write(arrayOfByte, 0, n);
          m += n;
          if (i <= 0)
            continue;
          int i1 = (int)(100.0F * (m / i));
          if (i1 / 10 <= k)
            continue;
          getTask().publishProgress(i1);
          k = i1 / 10;
        }
        do
        {
          j = i / 10;
          break label98;
          localInputStream.close();
          break;
        }
        while (i >= 0);
        int j = 4096;
      }
    }
  }

  private static abstract class AsyncHttpRequestCallbacks<T>
  {
    private InstagramAutoUpdater.AsyncHttpRequestTask<T> mTask = null;

    public InstagramAutoUpdater.AsyncHttpRequestTask<T> getTask()
    {
      return this.mTask;
    }

    abstract T processResponseInBackground(HttpResponse paramHttpResponse);

    void reportProgress(int paramInt)
    {
    }

    abstract void requestFinished(T paramT);

    public void setTask(InstagramAutoUpdater.AsyncHttpRequestTask<T> paramAsyncHttpRequestTask)
    {
      this.mTask = paramAsyncHttpRequestTask;
    }
  }

  private static class AsyncHttpRequestTask<T> extends AsyncTask<String, Integer, T>
  {
    private InstagramAutoUpdater.AsyncHttpRequestCallbacks<T> mAsyncHttpRequestCallbacks;

    public AsyncHttpRequestTask(InstagramAutoUpdater.AsyncHttpRequestCallbacks<T> paramAsyncHttpRequestCallbacks)
    {
      this.mAsyncHttpRequestCallbacks = paramAsyncHttpRequestCallbacks;
      this.mAsyncHttpRequestCallbacks.setTask(this);
    }

    protected T doInBackground(String[] paramArrayOfString)
    {
      Object localObject = null;
      DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
      try
      {
        localHttpResponse = localDefaultHttpClient.execute(new HttpGet(paramArrayOfString[0]));
        if ((localHttpResponse == null) || (localHttpResponse.getStatusLine() == null) || (localHttpResponse.getStatusLine().getStatusCode() != 200))
        {
          Log.e("InstagramAutoUpdater", "Bad HTTP response");
          return localObject;
        }
      }
      catch (Exception localException)
      {
        while (true)
        {
          HttpResponse localHttpResponse;
          continue;
          localObject = this.mAsyncHttpRequestCallbacks.processResponseInBackground(localHttpResponse);
        }
      }
    }

    protected void onPostExecute(T paramT)
    {
      this.mAsyncHttpRequestCallbacks.requestFinished(paramT);
    }

    protected void onProgressUpdate(Integer[] paramArrayOfInteger)
    {
      this.mAsyncHttpRequestCallbacks.reportProgress(paramArrayOfInteger[0].intValue());
    }

    public void publishProgress(int paramInt)
    {
      Integer[] arrayOfInteger = new Integer[1];
      arrayOfInteger[0] = Integer.valueOf(paramInt);
      super.publishProgress(arrayOfInteger);
    }
  }

  private class DownloadUpdateButtonListener
    implements DialogInterface.OnClickListener
  {
    private DownloadUpdateButtonListener()
    {
    }

    public void onClick(DialogInterface paramDialogInterface, int paramInt)
    {
      InstagramAutoUpdater.this.downloadUpdate();
    }
  }

  private class LogErrorException extends Throwable
  {
    public Exception mException;
    public String mMessage;

    public LogErrorException(String arg2)
    {
      Object localObject;
      this.mMessage = localObject;
    }

    public LogErrorException(String paramException, Exception arg3)
    {
      this.mMessage = paramException;
      Object localObject;
      this.mException = localObject;
    }

    public void log()
    {
      InstagramAutoUpdater.this.mHandler.post(new InstagramAutoUpdater.LogErrorException.1(this));
    }
  }

  private class Manifest
  {
    private String mApkUrlString;
    private String mReleaseNotes;
    private String mVersionString;

    // ERROR //
    public Manifest(HttpResponse arg2)
      throws InstagramAutoUpdater.LogErrorException
    {
      // Byte code:
      //   0: aload_0
      //   1: aload_1
      //   2: putfield 21	com/instagram/android/InstagramAutoUpdater$Manifest:this$0	Lcom/instagram/android/InstagramAutoUpdater;
      //   5: aload_0
      //   6: invokespecial 24	java/lang/Object:<init>	()V
      //   9: aload_2
      //   10: invokeinterface 30 1 0
      //   15: astore_3
      //   16: aload_2
      //   17: invokeinterface 34 1 0
      //   22: astore 4
      //   24: aload 4
      //   26: ifnull +16 -> 42
      //   29: aload 4
      //   31: invokeinterface 40 1 0
      //   36: sipush 200
      //   39: if_icmpeq +14 -> 53
      //   42: new 17	com/instagram/android/InstagramAutoUpdater$LogErrorException
      //   45: dup
      //   46: aload_1
      //   47: ldc 42
      //   49: invokespecial 45	com/instagram/android/InstagramAutoUpdater$LogErrorException:<init>	(Lcom/instagram/android/InstagramAutoUpdater;Ljava/lang/String;)V
      //   52: athrow
      //   53: aload_0
      //   54: aload_3
      //   55: invokespecial 49	com/instagram/android/InstagramAutoUpdater$Manifest:initWithEntity	(Lch/boye/httpclientandroidlib/HttpEntity;)V
      //   58: aload_3
      //   59: invokestatic 54	ch/boye/httpclientandroidlib/util/EntityUtils:consume	(Lch/boye/httpclientandroidlib/HttpEntity;)V
      //   62: return
      //   63: astore 5
      //   65: aload_3
      //   66: invokestatic 54	ch/boye/httpclientandroidlib/util/EntityUtils:consume	(Lch/boye/httpclientandroidlib/HttpEntity;)V
      //   69: aload 5
      //   71: athrow
      //   72: astore 7
      //   74: goto -12 -> 62
      //   77: astore 6
      //   79: goto -10 -> 69
      //
      // Exception table:
      //   from	to	target	type
      //   53	58	63	finally
      //   58	62	72	java/io/IOException
      //   65	69	77	java/io/IOException
    }

    // ERROR //
    private void initWithEntity(HttpEntity paramHttpEntity)
      throws InstagramAutoUpdater.LogErrorException
    {
      // Byte code:
      //   0: aload_1
      //   1: ldc 64
      //   3: invokestatic 68	ch/boye/httpclientandroidlib/util/EntityUtils:toString	(Lch/boye/httpclientandroidlib/HttpEntity;Ljava/lang/String;)Ljava/lang/String;
      //   6: astore_3
      //   7: aconst_null
      //   8: astore 4
      //   10: aload_3
      //   11: ldc 70
      //   13: iconst_3
      //   14: invokevirtual 76	java/lang/String:split	(Ljava/lang/String;I)[Ljava/lang/String;
      //   17: astore 6
      //   19: aload 6
      //   21: astore 4
      //   23: aload 4
      //   25: ifnull +10 -> 35
      //   28: aload 4
      //   30: arraylength
      //   31: iconst_2
      //   32: if_icmpge +33 -> 65
      //   35: new 17	com/instagram/android/InstagramAutoUpdater$LogErrorException
      //   38: dup
      //   39: aload_0
      //   40: getfield 21	com/instagram/android/InstagramAutoUpdater$Manifest:this$0	Lcom/instagram/android/InstagramAutoUpdater;
      //   43: ldc 78
      //   45: invokespecial 45	com/instagram/android/InstagramAutoUpdater$LogErrorException:<init>	(Lcom/instagram/android/InstagramAutoUpdater;Ljava/lang/String;)V
      //   48: athrow
      //   49: astore_2
      //   50: new 17	com/instagram/android/InstagramAutoUpdater$LogErrorException
      //   53: dup
      //   54: aload_0
      //   55: getfield 21	com/instagram/android/InstagramAutoUpdater$Manifest:this$0	Lcom/instagram/android/InstagramAutoUpdater;
      //   58: ldc 80
      //   60: aload_2
      //   61: invokespecial 83	com/instagram/android/InstagramAutoUpdater$LogErrorException:<init>	(Lcom/instagram/android/InstagramAutoUpdater;Ljava/lang/String;Ljava/lang/Exception;)V
      //   64: athrow
      //   65: aload 4
      //   67: arraylength
      //   68: tableswitch	default:+24 -> 92, 2:+47->115, 3:+36->104
      //   93: aload 4
      //   95: iconst_0
      //   96: aaload
      //   97: invokevirtual 87	java/lang/String:trim	()Ljava/lang/String;
      //   100: putfield 89	com/instagram/android/InstagramAutoUpdater$Manifest:mApkUrlString	Ljava/lang/String;
      //   103: return
      //   104: aload_0
      //   105: aload 4
      //   107: iconst_2
      //   108: aaload
      //   109: invokevirtual 87	java/lang/String:trim	()Ljava/lang/String;
      //   112: putfield 91	com/instagram/android/InstagramAutoUpdater$Manifest:mReleaseNotes	Ljava/lang/String;
      //   115: aload_0
      //   116: aload 4
      //   118: iconst_1
      //   119: aaload
      //   120: invokevirtual 87	java/lang/String:trim	()Ljava/lang/String;
      //   123: putfield 93	com/instagram/android/InstagramAutoUpdater$Manifest:mVersionString	Ljava/lang/String;
      //   126: goto -34 -> 92
      //   129: astore 5
      //   131: goto -108 -> 23
      //
      // Exception table:
      //   from	to	target	type
      //   0	7	49	java/io/IOException
      //   10	19	129	java/lang/NullPointerException
    }

    private boolean isNewerApkAvailable()
      throws InstagramAutoUpdater.LogErrorException
    {
      int i = 0;
      while (true)
      {
        PackageInfo localPackageInfo;
        try
        {
          localPackageInfo = InstagramAutoUpdater.this.getContext().getPackageManager().getPackageInfo(InstagramAutoUpdater.this.getContext().getPackageName(), 0);
          if (localPackageInfo.versionName.contains("ib"))
            return i;
        }
        catch (PackageManager.NameNotFoundException localNameNotFoundException)
        {
          throw new InstagramAutoUpdater.LogErrorException(InstagramAutoUpdater.this, "Unable to read current version");
        }
        if (this.mVersionString.equalsIgnoreCase(localPackageInfo.versionName))
          continue;
        i = 1;
      }
    }

    public String getApkUrlString()
    {
      return this.mApkUrlString;
    }

    public String getReleaseNotes()
    {
      return this.mReleaseNotes;
    }

    public String getVersionString()
    {
      return this.mVersionString;
    }
  }

  private class ManifestHttpRequestCallbacks extends InstagramAutoUpdater.AsyncHttpRequestCallbacks<InstagramAutoUpdater.Manifest>
  {
    private ManifestHttpRequestCallbacks()
    {
      super();
    }

    public InstagramAutoUpdater.Manifest processResponseInBackground(HttpResponse paramHttpResponse)
    {
      try
      {
        localManifest = new InstagramAutoUpdater.Manifest(InstagramAutoUpdater.this, paramHttpResponse);
        return localManifest;
      }
      catch (InstagramAutoUpdater.LogErrorException localLogErrorException)
      {
        while (true)
        {
          localLogErrorException.log();
          InstagramAutoUpdater.Manifest localManifest = null;
        }
      }
    }

    public void requestFinished(InstagramAutoUpdater.Manifest paramManifest)
    {
      InstagramAutoUpdater.access$102(InstagramAutoUpdater.this, paramManifest);
      if (InstagramAutoUpdater.this.mProgressDialog != null)
        InstagramAutoUpdater.this.mProgressDialog.dismiss();
      try
      {
        if ((InstagramAutoUpdater.this.mManifest != null) && (InstagramAutoUpdater.this.mManifest.isNewerApkAvailable()) && (!InstagramAutoUpdater.this.mDownloadWasCanceled))
        {
          InstagramAutoUpdater.this.askToDownloadUpdate();
        }
        else if (InstagramAutoUpdater.this.mShowProgressDialog)
        {
          Toast localToast = Toast.makeText(InstagramAutoUpdater.this.getContext(), "Your version of Instagram is up-to-date.", 0);
          localToast.setGravity(17, 0, 0);
          localToast.show();
        }
      }
      catch (InstagramAutoUpdater.LogErrorException localLogErrorException)
      {
        localLogErrorException.log();
      }
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.InstagramAutoUpdater
 * JD-Core Version:    0.6.0
 */
package com.facebook.android;

import android.content.Context;
import android.os.Bundle;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

public class AsyncFacebookRunner
{
  Facebook fb;

  public AsyncFacebookRunner(Facebook paramFacebook)
  {
    this.fb = paramFacebook;
  }

  public void logout(Context paramContext, RequestListener paramRequestListener)
  {
    logout(paramContext, paramRequestListener, null);
  }

  public void logout(Context paramContext, RequestListener paramRequestListener, Object paramObject)
  {
    new Thread(paramContext, paramRequestListener, paramObject)
    {
      public void run()
      {
        try
        {
          String str = AsyncFacebookRunner.this.fb.logout(this.val$context);
          if ((str.length() == 0) || (str.equals("false")))
            this.val$listener.onFacebookError(new FacebookError("auth.expireSession failed"), this.val$state);
          else
            this.val$listener.onComplete(str, this.val$state);
        }
        catch (FileNotFoundException localFileNotFoundException)
        {
          this.val$listener.onFileNotFoundException(localFileNotFoundException, this.val$state);
        }
        catch (MalformedURLException localMalformedURLException)
        {
          this.val$listener.onMalformedURLException(localMalformedURLException, this.val$state);
        }
        catch (IOException localIOException)
        {
          this.val$listener.onIOException(localIOException, this.val$state);
        }
      }
    }
    .start();
  }

  public void request(Bundle paramBundle, RequestListener paramRequestListener)
  {
    request(null, paramBundle, "GET", paramRequestListener, null);
  }

  public void request(Bundle paramBundle, RequestListener paramRequestListener, Object paramObject)
  {
    request(null, paramBundle, "GET", paramRequestListener, paramObject);
  }

  public void request(String paramString, Bundle paramBundle, RequestListener paramRequestListener)
  {
    request(paramString, paramBundle, "GET", paramRequestListener, null);
  }

  public void request(String paramString, Bundle paramBundle, RequestListener paramRequestListener, Object paramObject)
  {
    request(paramString, paramBundle, "GET", paramRequestListener, paramObject);
  }

  public void request(String paramString1, Bundle paramBundle, String paramString2, RequestListener paramRequestListener, Object paramObject)
  {
    new Thread(paramString1, paramBundle, paramString2, paramRequestListener, paramObject)
    {
      public void run()
      {
        try
        {
          String str = AsyncFacebookRunner.this.fb.request(this.val$graphPath, this.val$parameters, this.val$httpMethod);
          this.val$listener.onComplete(str, this.val$state);
          return;
        }
        catch (FileNotFoundException localFileNotFoundException)
        {
          while (true)
            this.val$listener.onFileNotFoundException(localFileNotFoundException, this.val$state);
        }
        catch (MalformedURLException localMalformedURLException)
        {
          while (true)
            this.val$listener.onMalformedURLException(localMalformedURLException, this.val$state);
        }
        catch (IOException localIOException)
        {
          while (true)
            this.val$listener.onIOException(localIOException, this.val$state);
        }
      }
    }
    .start();
  }

  public void request(String paramString, RequestListener paramRequestListener)
  {
    request(paramString, new Bundle(), "GET", paramRequestListener, null);
  }

  public void request(String paramString, RequestListener paramRequestListener, Object paramObject)
  {
    request(paramString, new Bundle(), "GET", paramRequestListener, paramObject);
  }

  public static abstract interface RequestListener
  {
    public abstract void onComplete(String paramString, Object paramObject);

    public abstract void onFacebookError(FacebookError paramFacebookError, Object paramObject);

    public abstract void onFileNotFoundException(FileNotFoundException paramFileNotFoundException, Object paramObject);

    public abstract void onIOException(IOException paramIOException, Object paramObject);

    public abstract void onMalformedURLException(MalformedURLException paramMalformedURLException, Object paramObject);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.facebook.android.AsyncFacebookRunner
 * JD-Core Version:    0.6.0
 */
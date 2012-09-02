package com.bugsense.trace;

import android.util.Log;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.Random;

public class DefaultExceptionHandler
  implements Thread.UncaughtExceptionHandler
{
  private Thread.UncaughtExceptionHandler defaultExceptionHandler;

  public DefaultExceptionHandler(Thread.UncaughtExceptionHandler paramUncaughtExceptionHandler)
  {
    this.defaultExceptionHandler = paramUncaughtExceptionHandler;
  }

  public void uncaughtException(Thread paramThread, Throwable paramThrowable)
  {
    Date localDate = new Date();
    StringWriter localStringWriter = new StringWriter();
    paramThrowable.printStackTrace(new PrintWriter(localStringWriter));
    try
    {
      BugSense.submitError(0, localDate, localStringWriter.toString());
      Log.d(G.TAG, localStringWriter.toString());
      this.defaultExceptionHandler.uncaughtException(paramThread, paramThrowable);
      return;
    }
    catch (Exception localException1)
    {
      while (true)
      {
        Log.e(G.TAG, "Error sending exception stacktrace", paramThrowable);
        try
        {
          int i = new Random().nextInt(99999);
          String str = G.APP_VERSION + "-" + Integer.toString(i);
          Log.d(G.TAG, "Writing unhandled exception to: " + G.FILES_PATH + "/" + str + ".stacktrace");
          BufferedWriter localBufferedWriter = new BufferedWriter(new FileWriter(G.FILES_PATH + "/" + str + ".stacktrace"));
          localBufferedWriter.write(G.ANDROID_VERSION + "\n");
          localBufferedWriter.write(G.PHONE_MODEL + "\n");
          localBufferedWriter.write(localDate + "\n");
          localBufferedWriter.write(localStringWriter.toString());
          localBufferedWriter.flush();
          localBufferedWriter.close();
        }
        catch (Exception localException2)
        {
          Log.e(G.TAG, "Error saving exception stacktrace", paramThrowable);
        }
      }
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.bugsense.trace.DefaultExceptionHandler
 * JD-Core Version:    0.6.0
 */
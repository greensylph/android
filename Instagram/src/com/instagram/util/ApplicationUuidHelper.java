package com.instagram.util;

import android.content.Context;
import android.content.res.Resources;
import android.provider.Settings.Secure;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

public class ApplicationUuidHelper
{
  public static final String ANDROID_UUID_PREFIX = "android-";
  private static final String INSTALLATION = "INSTALLATION";
  private static String sID = null;

  public static String generateOldStyleUuid(Context paramContext)
  {
    File localFile = new File(paramContext.getFilesDir(), "INSTALLATION");
    try
    {
      if (!localFile.exists())
        writeInstallationFile(localFile);
      sID = readInstallationFile(localFile);
      String str = sID;
      return str;
    }
    catch (Exception localException)
    {
    }
    throw new RuntimeException(localException);
  }

  public static String id(Context paramContext)
  {
    monitorenter;
    try
    {
      if (sID == null)
      {
        sID = Settings.Secure.getString(paramContext.getContentResolver(), "android_id");
        if ((sID == null) || (sID.length() == 0) || (isFaultyId(sID, paramContext)))
          generateOldStyleUuid(paramContext);
      }
      String str = sID;
      monitorexit;
      return str;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  private static boolean isFaultyId(String paramString, Context paramContext)
  {
    String[] arrayOfString = paramContext.getResources().getStringArray(2131296265);
    int i = arrayOfString.length;
    int j = 0;
    if (j < i)
      if (!arrayOfString[j].equalsIgnoreCase(paramString));
    for (int k = 1; ; k = 0)
    {
      return k;
      j++;
      break;
    }
  }

  private static String readInstallationFile(File paramFile)
    throws IOException
  {
    RandomAccessFile localRandomAccessFile = new RandomAccessFile(paramFile, "r");
    byte[] arrayOfByte = new byte[(int)localRandomAccessFile.length()];
    localRandomAccessFile.readFully(arrayOfByte);
    localRandomAccessFile.close();
    return new String(arrayOfByte);
  }

  private static void writeInstallationFile(File paramFile)
    throws IOException
  {
    FileOutputStream localFileOutputStream = new FileOutputStream(paramFile);
    localFileOutputStream.write(UUID.randomUUID().toString().getBytes());
    localFileOutputStream.close();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.util.ApplicationUuidHelper
 * JD-Core Version:    0.6.0
 */
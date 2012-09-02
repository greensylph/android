package com.instagram.util;

import com.instagram.android.gl.NativeBridge;
import com.instagram.api.RequestParams;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class RequestUtil
{
  private static String generateSignature(String paramString)
  {
    StringBuilder localStringBuilder;
    String str;
    try
    {
      Mac localMac = getSharedMac();
      byte[] arrayOfByte = localMac.doFinal(paramString.getBytes());
      localStringBuilder = new StringBuilder();
      int i = arrayOfByte.length;
      for (int j = 0; j < i; j++)
        localStringBuilder.append(Integer.toString(256 + (0xFF & arrayOfByte[j]), 16).substring(1));
    }
    catch (Exception localException)
    {
      str = null;
    }
    while (true)
    {
      return str;
      str = localStringBuilder.toString();
    }
  }

  private static Mac getSharedMac()
    throws NoSuchAlgorithmException, InvalidKeyException
  {
    Mac localMac = Mac.getInstance("HmacSHA256");
    localMac.init(new SecretKeySpec(NativeBridge.getInstagramString("derahs_k").getBytes(), "HmacSHA256"));
    return localMac;
  }

  public static void setSignedBody(RequestParams paramRequestParams, String paramString)
  {
    String str1 = null;
    if (paramString != null)
    {
      String str2 = generateSignature(paramString);
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = str2;
      arrayOfObject[1] = paramString;
      str1 = String.format("%s.%s", arrayOfObject);
    }
    paramRequestParams.put("signed_body", str1);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.util.RequestUtil
 * JD-Core Version:    0.6.0
 */
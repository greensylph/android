package com.loopj.android.http;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.message.BasicHeader;

class SimpleMultipartEntity
  implements HttpEntity
{
  private static final char[] MULTIPART_CHARS = "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
  private String boundary = null;
  boolean isSetFirst = false;
  boolean isSetLast = false;
  ByteArrayOutputStream out = new ByteArrayOutputStream();

  public SimpleMultipartEntity()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    Random localRandom = new Random();
    while (i < 30)
    {
      localStringBuffer.append(MULTIPART_CHARS[localRandom.nextInt(MULTIPART_CHARS.length)]);
      i++;
    }
    this.boundary = localStringBuffer.toString();
  }

  public void addPart(String paramString, File paramFile)
  {
    try
    {
      addPart(paramString, paramFile.getName(), new FileInputStream(paramFile));
      return;
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      while (true)
        localFileNotFoundException.printStackTrace();
    }
  }

  public void addPart(String paramString1, String paramString2)
  {
    writeFirstBoundaryIfNeeds();
    try
    {
      this.out.write(("Content-Disposition: form-data; name=\"" + paramString1 + "\"\r\n\r\n").getBytes());
      this.out.write(paramString2.getBytes());
      this.out.write(("\r\n--" + this.boundary + "\r\n").getBytes());
      return;
    }
    catch (IOException localIOException)
    {
      while (true)
        localIOException.printStackTrace();
    }
  }

  public void addPart(String paramString1, String paramString2, InputStream paramInputStream)
  {
    addPart(paramString1, paramString2, paramInputStream, "application/octet-stream");
  }

  public void addPart(String paramString1, String paramString2, InputStream paramInputStream, String paramString3)
  {
    writeFirstBoundaryIfNeeds();
    try
    {
      String str = "Content-Type: " + paramString3 + "\r\n";
      this.out.write(("Content-Disposition: form-data; name=\"" + paramString1 + "\"; filename=\"" + paramString2 + "\"\r\n").getBytes());
      this.out.write(str.getBytes());
      this.out.write("Content-Transfer-Encoding: binary\r\n\r\n".getBytes());
      byte[] arrayOfByte = new byte[4096];
      while (true)
      {
        int i = paramInputStream.read(arrayOfByte);
        if (i == -1)
          break;
        this.out.write(arrayOfByte, 0, i);
      }
    }
    catch (IOException localIOException2)
    {
      localIOException2.printStackTrace();
      try
      {
        paramInputStream.close();
        while (true)
        {
          return;
          this.out.flush();
          try
          {
            paramInputStream.close();
          }
          catch (IOException localIOException4)
          {
            localIOException4.printStackTrace();
          }
        }
      }
      catch (IOException localIOException3)
      {
        while (true)
          localIOException3.printStackTrace();
      }
    }
    finally
    {
    }
    try
    {
      paramInputStream.close();
      throw localObject;
    }
    catch (IOException localIOException1)
    {
      while (true)
        localIOException1.printStackTrace();
    }
  }

  public void consumeContent()
    throws IOException, UnsupportedOperationException
  {
    if (isStreaming())
      throw new UnsupportedOperationException("Streaming entity does not implement #consumeContent()");
  }

  public InputStream getContent()
    throws IOException, UnsupportedOperationException
  {
    return new ByteArrayInputStream(this.out.toByteArray());
  }

  public Header getContentEncoding()
  {
    return null;
  }

  public long getContentLength()
  {
    writeLastBoundaryIfNeeds();
    return this.out.toByteArray().length;
  }

  public Header getContentType()
  {
    return new BasicHeader("Content-Type", "multipart/form-data; boundary=" + this.boundary);
  }

  public boolean isChunked()
  {
    return false;
  }

  public boolean isRepeatable()
  {
    return false;
  }

  public boolean isStreaming()
  {
    return false;
  }

  public void writeFirstBoundaryIfNeeds()
  {
    if (!this.isSetFirst);
    try
    {
      this.out.write(("--" + this.boundary + "\r\n").getBytes());
      this.isSetFirst = true;
      return;
    }
    catch (IOException localIOException)
    {
      while (true)
        localIOException.printStackTrace();
    }
  }

  public void writeLastBoundaryIfNeeds()
  {
    if (this.isSetLast);
    while (true)
    {
      return;
      try
      {
        this.out.write(("\r\n--" + this.boundary + "--\r\n").getBytes());
        this.isSetLast = true;
      }
      catch (IOException localIOException)
      {
        while (true)
          localIOException.printStackTrace();
      }
    }
  }

  public void writeTo(OutputStream paramOutputStream)
    throws IOException
  {
    paramOutputStream.write(this.out.toByteArray());
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.loopj.android.http.SimpleMultipartEntity
 * JD-Core Version:    0.6.0
 */
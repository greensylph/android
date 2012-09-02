package com.instagram.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class InputStreamWrapper extends InputStream
{
  private BufferedOutputStream bufferedOutputStream;
  private final File mFile;
  private final InputStream mInputStream;

  public InputStreamWrapper(InputStream paramInputStream, File paramFile)
    throws FileNotFoundException
  {
    this.mInputStream = paramInputStream;
    this.mFile = paramFile;
    this.bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(this.mFile));
  }

  public int available()
    throws IOException
  {
    return this.mInputStream.available();
  }

  public void close()
    throws IOException
  {
    this.bufferedOutputStream.close();
    this.mInputStream.close();
  }

  public void mark(int paramInt)
  {
    throw new RuntimeException("Operation not supported");
  }

  public boolean markSupported()
  {
    throw new RuntimeException("Operation not supported");
  }

  public int read()
    throws IOException
  {
    int i = this.mInputStream.read();
    this.bufferedOutputStream.write(i);
    return i;
  }

  public int read(byte[] paramArrayOfByte)
    throws IOException
  {
    int i = this.mInputStream.read(paramArrayOfByte);
    this.bufferedOutputStream.write(paramArrayOfByte, 0, i);
    return i;
  }

  public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    int i = this.mInputStream.read(paramArrayOfByte, paramInt1, paramInt2);
    this.bufferedOutputStream.write(paramArrayOfByte, paramInt1, i);
    return i;
  }

  public void reset()
    throws IOException
  {
    throw new RuntimeException("Operation not supported");
  }

  public long skip(long paramLong)
    throws IOException
  {
    throw new RuntimeException("Operation not supported");
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.util.InputStreamWrapper
 * JD-Core Version:    0.6.0
 */
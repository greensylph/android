package com.loopj.android.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

public class RequestParams
{
  private static String ENCODING = "UTF-8";
  protected ConcurrentHashMap<String, FileWrapper> fileParams;
  protected ConcurrentHashMap<String, String> urlParams;

  public RequestParams()
  {
    init();
  }

  public RequestParams(String paramString1, String paramString2)
  {
    init();
    put(paramString1, paramString2);
  }

  public RequestParams(Map<String, String> paramMap)
  {
    init();
    Iterator localIterator = paramMap.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      put((String)localEntry.getKey(), (String)localEntry.getValue());
    }
  }

  private List<BasicNameValuePair> getParamsList()
  {
    LinkedList localLinkedList = new LinkedList();
    Iterator localIterator = this.urlParams.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      localLinkedList.add(new BasicNameValuePair((String)localEntry.getKey(), (String)localEntry.getValue()));
    }
    return localLinkedList;
  }

  private void init()
  {
    this.urlParams = new ConcurrentHashMap();
    this.fileParams = new ConcurrentHashMap();
  }

  HttpEntity getEntity()
  {
    Object localObject;
    if (!this.fileParams.isEmpty())
    {
      SimpleMultipartEntity localSimpleMultipartEntity = new SimpleMultipartEntity();
      Iterator localIterator1 = this.urlParams.entrySet().iterator();
      while (localIterator1.hasNext())
      {
        Map.Entry localEntry2 = (Map.Entry)localIterator1.next();
        localSimpleMultipartEntity.addPart((String)localEntry2.getKey(), (String)localEntry2.getValue());
      }
      Iterator localIterator2 = this.fileParams.entrySet().iterator();
      while (localIterator2.hasNext())
      {
        Map.Entry localEntry1 = (Map.Entry)localIterator2.next();
        FileWrapper localFileWrapper = (FileWrapper)localEntry1.getValue();
        if (localFileWrapper.inputStream == null)
          continue;
        if (localFileWrapper.contentType != null)
        {
          localSimpleMultipartEntity.addPart((String)localEntry1.getKey(), localFileWrapper.getFileName(), localFileWrapper.inputStream, localFileWrapper.contentType);
          continue;
        }
        localSimpleMultipartEntity.addPart((String)localEntry1.getKey(), localFileWrapper.getFileName(), localFileWrapper.inputStream);
      }
      localObject = localSimpleMultipartEntity;
    }
    while (true)
    {
      return localObject;
      try
      {
        localObject = new UrlEncodedFormEntity(getParamsList(), ENCODING);
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
        localUnsupportedEncodingException.printStackTrace();
        localObject = null;
      }
    }
  }

  String getParamString()
  {
    if (!this.fileParams.isEmpty())
      throw new RuntimeException("Uploading files is not supported with Http GET requests.");
    return URLEncodedUtils.format(getParamsList(), ENCODING);
  }

  public void put(String paramString, File paramFile)
    throws FileNotFoundException
  {
    put(paramString, new FileInputStream(paramFile), paramFile.getName());
  }

  public void put(String paramString, InputStream paramInputStream)
  {
    put(paramString, paramInputStream, null);
  }

  public void put(String paramString1, InputStream paramInputStream, String paramString2)
  {
    put(paramString1, paramInputStream, paramString2, null);
  }

  public void put(String paramString1, InputStream paramInputStream, String paramString2, String paramString3)
  {
    if ((paramString1 != null) && (paramInputStream != null))
      this.fileParams.put(paramString1, new FileWrapper(paramInputStream, paramString2, paramString3));
  }

  public void put(String paramString1, String paramString2)
  {
    if ((paramString1 != null) && (paramString2 != null))
      this.urlParams.put(paramString1, paramString2);
  }

  public void remove(String paramString)
  {
    this.urlParams.remove(paramString);
    this.fileParams.remove(paramString);
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    Iterator localIterator1 = this.urlParams.entrySet().iterator();
    while (localIterator1.hasNext())
    {
      Map.Entry localEntry2 = (Map.Entry)localIterator1.next();
      if (localStringBuilder.length() > 0)
        localStringBuilder.append("&");
      localStringBuilder.append((String)localEntry2.getKey());
      localStringBuilder.append("=");
      localStringBuilder.append((String)localEntry2.getValue());
    }
    Iterator localIterator2 = this.fileParams.entrySet().iterator();
    while (localIterator2.hasNext())
    {
      Map.Entry localEntry1 = (Map.Entry)localIterator2.next();
      if (localStringBuilder.length() > 0)
        localStringBuilder.append("&");
      localStringBuilder.append((String)localEntry1.getKey());
      localStringBuilder.append("=");
      localStringBuilder.append("FILE");
    }
    return localStringBuilder.toString();
  }

  private static class FileWrapper
  {
    public String contentType;
    public String fileName;
    public InputStream inputStream;

    public FileWrapper(InputStream paramInputStream, String paramString1, String paramString2)
    {
      this.inputStream = paramInputStream;
      this.fileName = paramString1;
      this.contentType = paramString2;
    }

    public String getFileName()
    {
      if (this.fileName != null);
      for (String str = this.fileName; ; str = "nofilename")
        return str;
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.loopj.android.http.RequestParams
 * JD-Core Version:    0.6.0
 */
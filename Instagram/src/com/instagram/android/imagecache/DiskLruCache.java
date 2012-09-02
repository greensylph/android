package com.instagram.android.imagecache;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class DiskLruCache
  implements Closeable
{
  static final long ANY_SEQUENCE_NUMBER = -1L;
  private static final String CLEAN = "CLEAN";
  private static final String DIRTY = "DIRTY";
  static final String JOURNAL_FILE = "journal";
  static final String JOURNAL_FILE_TMP = "journal.tmp";
  static final String MAGIC = "libcore.io.DiskLruCache";
  private static final String READ = "READ";
  private static final String REMOVE = "REMOVE";
  private static final Charset UTF_8 = Charset.forName("UTF-8");
  static final String VERSION_1 = "1";
  private final int appVersion;
  private final Callable<Void> cleanupCallable = new Callable()
  {
    public Void call()
      throws Exception
    {
      synchronized (DiskLruCache.this)
      {
        if (DiskLruCache.this.journalWriter != null)
        {
          DiskLruCache.this.trimToSize();
          if (DiskLruCache.this.journalRebuildRequired())
          {
            DiskLruCache.this.rebuildJournal();
            DiskLruCache.access$402(DiskLruCache.this, 0);
          }
        }
      }
      return null;
    }
  };
  private final File directory;
  private final ExecutorService executorService = new ThreadPoolExecutor(0, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue());
  private final File journalFile;
  private final File journalFileTmp;
  private Writer journalWriter;
  private final LinkedHashMap<String, Entry> lruEntries = new LinkedHashMap(0, 0.75F, true);
  private final long maxSize;
  private long nextSequenceNumber = 0L;
  private int redundantOpCount;
  private long size = 0L;
  private final int valueCount;

  private DiskLruCache(File paramFile, int paramInt1, int paramInt2, long paramLong)
  {
    this.directory = paramFile;
    this.appVersion = paramInt1;
    this.journalFile = new File(paramFile, "journal");
    this.journalFileTmp = new File(paramFile, "journal.tmp");
    this.valueCount = paramInt2;
    this.maxSize = paramLong;
  }

  private void checkNotClosed()
  {
    if (this.journalWriter == null)
      throw new IllegalStateException("cache is closed");
  }

  private static void closeQuietly(Closeable paramCloseable)
  {
    if (paramCloseable != null);
    try
    {
      paramCloseable.close();
      label10: return;
    }
    catch (RuntimeException localRuntimeException)
    {
      throw localRuntimeException;
    }
    catch (Exception localException)
    {
      break label10;
    }
  }

  private void completeEdit(Editor paramEditor, boolean paramBoolean)
    throws IOException
  {
    monitorenter;
    Entry localEntry;
    try
    {
      localEntry = paramEditor.entry;
      if (localEntry.currentEditor != paramEditor)
        throw new IllegalStateException();
    }
    finally
    {
      monitorexit;
    }
    if ((paramBoolean) && (!localEntry.readable))
      for (int j = 0; j < this.valueCount; j++)
      {
        if (localEntry.getDirtyFile(j).exists())
          continue;
        paramEditor.abort();
        monitorexit;
        return;
      }
    for (int i = 0; ; i++)
      if (i < this.valueCount)
      {
        File localFile1 = localEntry.getDirtyFile(i);
        if (paramBoolean)
        {
          if (!localFile1.exists())
            continue;
          File localFile2 = localEntry.getCleanFile(i);
          localFile1.renameTo(localFile2);
          long l2 = localEntry.lengths[i];
          long l3 = localFile2.length();
          localEntry.lengths[i] = l3;
          this.size = (l3 + (this.size - l2));
        }
        else
        {
          deleteIfExists(localFile1);
        }
      }
      else
      {
        this.redundantOpCount = (1 + this.redundantOpCount);
        Entry.access$702(localEntry, null);
        if ((paramBoolean | localEntry.readable))
        {
          Entry.access$602(localEntry, true);
          this.journalWriter.write("CLEAN " + localEntry.key + localEntry.getLengths() + '\n');
          if (paramBoolean)
          {
            long l1 = this.nextSequenceNumber;
            this.nextSequenceNumber = (1L + l1);
            Entry.access$1202(localEntry, l1);
          }
        }
        while (true)
        {
          if ((this.size <= this.maxSize) && (!journalRebuildRequired()))
            break label371;
          this.executorService.submit(this.cleanupCallable);
          break;
          this.lruEntries.remove(localEntry.key);
          this.journalWriter.write("REMOVE " + localEntry.key + '\n');
        }
        label371: break;
      }
  }

  private static <T> T[] copyOfRange(T[] paramArrayOfT, int paramInt1, int paramInt2)
  {
    int i = paramArrayOfT.length;
    if (paramInt1 > paramInt2)
      throw new IllegalArgumentException();
    if ((paramInt1 < 0) || (paramInt1 > i))
      throw new ArrayIndexOutOfBoundsException();
    int j = paramInt2 - paramInt1;
    int k = Math.min(j, i - paramInt1);
    Object[] arrayOfObject = (Object[])(Object[])Array.newInstance(paramArrayOfT.getClass().getComponentType(), j);
    System.arraycopy(paramArrayOfT, paramInt1, arrayOfObject, 0, k);
    return arrayOfObject;
  }

  private static void deleteContents(File paramFile)
    throws IOException
  {
    File[] arrayOfFile = paramFile.listFiles();
    if (arrayOfFile == null)
      throw new IllegalArgumentException("not a directory: " + paramFile);
    int i = arrayOfFile.length;
    for (int j = 0; j < i; j++)
    {
      File localFile = arrayOfFile[j];
      if (localFile.isDirectory())
        deleteContents(localFile);
      if (localFile.delete())
        continue;
      throw new IOException("failed to delete file: " + localFile);
    }
  }

  private static void deleteIfExists(File paramFile)
    throws IOException
  {
    if ((paramFile.exists()) && (!paramFile.delete()))
      throw new IOException();
  }

  private Editor edit(String paramString, long paramLong)
    throws IOException
  {
    Editor localEditor1 = null;
    monitorenter;
    while (true)
    {
      Entry localEntry;
      try
      {
        checkNotClosed();
        validateKey(paramString);
        localEntry = (Entry)this.lruEntries.get(paramString);
        if (paramLong == -1L)
          continue;
        if (localEntry == null)
          continue;
        long l = localEntry.sequenceNumber;
        if (l != paramLong)
          return localEditor1;
        if (localEntry == null)
        {
          localEntry = new Entry(paramString, null);
          this.lruEntries.put(paramString, localEntry);
          localEditor1 = new Editor(localEntry, null);
          Entry.access$702(localEntry, localEditor1);
          this.journalWriter.write("DIRTY " + paramString + '\n');
          this.journalWriter.flush();
          continue;
        }
      }
      finally
      {
        monitorexit;
      }
      Editor localEditor2 = localEntry.currentEditor;
      if (localEditor2 == null)
        continue;
    }
  }

  private static String inputStreamToString(InputStream paramInputStream)
    throws IOException
  {
    return readFully(new InputStreamReader(paramInputStream, UTF_8));
  }

  private boolean journalRebuildRequired()
  {
    if ((this.redundantOpCount >= 2000) && (this.redundantOpCount >= this.lruEntries.size()));
    for (int i = 1; ; i = 0)
      return i;
  }

  public static DiskLruCache open(File paramFile, int paramInt1, int paramInt2, long paramLong)
    throws IOException
  {
    if (paramLong <= 0L)
      throw new IllegalArgumentException("maxSize <= 0");
    if (paramInt2 <= 0)
      throw new IllegalArgumentException("valueCount <= 0");
    DiskLruCache localDiskLruCache1 = new DiskLruCache(paramFile, paramInt1, paramInt2, paramLong);
    if (localDiskLruCache1.journalFile.exists());
    while (true)
    {
      try
      {
        localDiskLruCache1.readJournal();
        localDiskLruCache1.processJournal();
        localDiskLruCache1.journalWriter = new BufferedWriter(new FileWriter(localDiskLruCache1.journalFile, true));
        localObject = localDiskLruCache1;
        return localObject;
      }
      catch (IOException localIOException)
      {
        System.out.println("DiskLruCache " + paramFile + " is corrupt: " + localIOException.getMessage() + ", removing");
        localDiskLruCache1.delete();
      }
      paramFile.mkdirs();
      DiskLruCache localDiskLruCache2 = new DiskLruCache(paramFile, paramInt1, paramInt2, paramLong);
      localDiskLruCache2.rebuildJournal();
      Object localObject = localDiskLruCache2;
    }
  }

  private void processJournal()
    throws IOException
  {
    deleteIfExists(this.journalFileTmp);
    Iterator localIterator = this.lruEntries.values().iterator();
    while (localIterator.hasNext())
    {
      Entry localEntry = (Entry)localIterator.next();
      if (localEntry.currentEditor == null)
      {
        for (int j = 0; j < this.valueCount; j++)
          this.size += localEntry.lengths[j];
        continue;
      }
      Entry.access$702(localEntry, null);
      for (int i = 0; i < this.valueCount; i++)
      {
        deleteIfExists(localEntry.getCleanFile(i));
        deleteIfExists(localEntry.getDirtyFile(i));
      }
      localIterator.remove();
    }
  }

  private static String readAsciiLine(InputStream paramInputStream)
    throws IOException
  {
    StringBuilder localStringBuilder = new StringBuilder(80);
    while (true)
    {
      int i = paramInputStream.read();
      if (i == -1)
        throw new EOFException();
      if (i == 10)
      {
        int j = localStringBuilder.length();
        if ((j > 0) && (localStringBuilder.charAt(j - 1) == '\r'))
          localStringBuilder.setLength(j - 1);
        return localStringBuilder.toString();
      }
      localStringBuilder.append((char)i);
    }
  }

  private static String readFully(Reader paramReader)
    throws IOException
  {
    StringWriter localStringWriter;
    try
    {
      localStringWriter = new StringWriter();
      char[] arrayOfChar = new char[1024];
      while (true)
      {
        int i = paramReader.read(arrayOfChar);
        if (i == -1)
          break;
        localStringWriter.write(arrayOfChar, 0, i);
      }
    }
    finally
    {
      paramReader.close();
    }
    String str = localStringWriter.toString();
    paramReader.close();
    return str;
  }

  private void readJournal()
    throws IOException
  {
    FileReader localFileReader = new FileReader(this.journalFile);
    BufferedReader localBufferedReader = new BufferedReader(localFileReader);
    try
    {
      String str1 = localBufferedReader.readLine();
      String str2 = localBufferedReader.readLine();
      String str3 = localBufferedReader.readLine();
      String str4 = localBufferedReader.readLine();
      String str5 = localBufferedReader.readLine();
      if ((!"libcore.io.DiskLruCache".equals(str1)) || (!"1".equals(str2)) || (!Integer.toString(this.appVersion).equals(str3)) || (!Integer.toString(this.valueCount).equals(str4)) || (!"".equals(str5)))
        throw new IOException("unexpected journal header: [" + str1 + ", " + str2 + ", " + str4 + ", " + str5 + "]");
    }
    finally
    {
      closeQuietly(localFileReader);
    }
    try
    {
      String str6;
      do
      {
        readJournalLine(str6);
        str6 = localBufferedReader.readLine();
      }
      while (str6 != null);
      label204: closeQuietly(localFileReader);
      return;
    }
    catch (EOFException localEOFException)
    {
      break label204;
    }
  }

  private void readJournalLine(String paramString)
    throws IOException
  {
    String[] arrayOfString = paramString.split(" ");
    if (arrayOfString.length < 2)
      throw new IOException("unexpected journal line: " + paramString);
    String str = arrayOfString[1];
    if ((arrayOfString[0].equals("REMOVE")) && (arrayOfString.length == 2))
      this.lruEntries.remove(str);
    do
      while (true)
      {
        return;
        Entry localEntry = (Entry)this.lruEntries.get(str);
        if (localEntry == null)
        {
          localEntry = new Entry(str, null);
          this.lruEntries.put(str, localEntry);
        }
        if ((arrayOfString[0].equals("CLEAN")) && (arrayOfString.length == 2 + this.valueCount))
        {
          Entry.access$602(localEntry, true);
          Entry.access$702(localEntry, null);
          localEntry.setLengths((String[])copyOfRange(arrayOfString, 2, arrayOfString.length));
          continue;
        }
        if ((!arrayOfString[0].equals("DIRTY")) || (arrayOfString.length != 2))
          break;
        Entry.access$702(localEntry, new Editor(localEntry, null));
      }
    while ((arrayOfString[0].equals("READ")) && (arrayOfString.length == 2));
    throw new IOException("unexpected journal line: " + paramString);
  }

  private void rebuildJournal()
    throws IOException
  {
    monitorenter;
    BufferedWriter localBufferedWriter;
    while (true)
    {
      Entry localEntry;
      try
      {
        if (this.journalWriter == null)
          continue;
        this.journalWriter.close();
        localBufferedWriter = new BufferedWriter(new FileWriter(this.journalFileTmp));
        localBufferedWriter.write("libcore.io.DiskLruCache");
        localBufferedWriter.write("\n");
        localBufferedWriter.write("1");
        localBufferedWriter.write("\n");
        localBufferedWriter.write(Integer.toString(this.appVersion));
        localBufferedWriter.write("\n");
        localBufferedWriter.write(Integer.toString(this.valueCount));
        localBufferedWriter.write("\n");
        localBufferedWriter.write("\n");
        Iterator localIterator = this.lruEntries.values().iterator();
        if (!localIterator.hasNext())
          break;
        localEntry = (Entry)localIterator.next();
        if (localEntry.currentEditor != null)
        {
          localBufferedWriter.write("DIRTY " + localEntry.key + '\n');
          continue;
        }
      }
      finally
      {
        monitorexit;
      }
      localBufferedWriter.write("CLEAN " + localEntry.key + localEntry.getLengths() + '\n');
    }
    localBufferedWriter.close();
    this.journalFileTmp.renameTo(this.journalFile);
    this.journalWriter = new BufferedWriter(new FileWriter(this.journalFile, true));
    monitorexit;
  }

  private void trimToSize()
    throws IOException
  {
    while (this.size > this.maxSize)
      remove((String)((Map.Entry)this.lruEntries.entrySet().iterator().next()).getKey());
  }

  private void validateKey(String paramString)
  {
    if ((paramString.contains(" ")) || (paramString.contains("\n")) || (paramString.contains("\r")))
      throw new IllegalArgumentException("keys must not contain spaces or newlines: \"" + paramString + "\"");
  }

  public void close()
    throws IOException
  {
    monitorenter;
    while (true)
    {
      try
      {
        Writer localWriter = this.journalWriter;
        if (localWriter == null)
          return;
        Iterator localIterator = new ArrayList(this.lruEntries.values()).iterator();
        if (localIterator.hasNext())
        {
          Entry localEntry = (Entry)localIterator.next();
          if (localEntry.currentEditor == null)
            continue;
          localEntry.currentEditor.abort();
          continue;
        }
      }
      finally
      {
        monitorexit;
      }
      trimToSize();
      this.journalWriter.close();
      this.journalWriter = null;
    }
  }

  public void delete()
    throws IOException
  {
    close();
    deleteContents(this.directory);
  }

  public Editor edit(String paramString)
    throws IOException
  {
    return edit(paramString, -1L);
  }

  public void flush()
    throws IOException
  {
    monitorenter;
    try
    {
      checkNotClosed();
      trimToSize();
      this.journalWriter.flush();
      monitorexit;
      return;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public Snapshot get(String paramString)
    throws IOException
  {
    Snapshot localSnapshot = null;
    monitorenter;
    try
    {
      checkNotClosed();
      validateKey(paramString);
      Entry localEntry = (Entry)this.lruEntries.get(paramString);
      if (localEntry == null);
      while (true)
      {
        return localSnapshot;
        if (!localEntry.readable)
          continue;
        InputStream[] arrayOfInputStream = new InputStream[this.valueCount];
        int i = 0;
        try
        {
          while (i < this.valueCount)
          {
            arrayOfInputStream[i] = new FileInputStream(localEntry.getCleanFile(i));
            i++;
          }
        }
        catch (FileNotFoundException localFileNotFoundException)
        {
        }
        continue;
        this.redundantOpCount = (1 + this.redundantOpCount);
        this.journalWriter.append("READ " + paramString + '\n');
        if (journalRebuildRequired())
          this.executorService.submit(this.cleanupCallable);
        localSnapshot = new Snapshot(paramString, localEntry.sequenceNumber, arrayOfInputStream, null);
      }
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public File getDirectory()
  {
    return this.directory;
  }

  public boolean isClosed()
  {
    if (this.journalWriter == null);
    for (int i = 1; ; i = 0)
      return i;
  }

  public long maxSize()
  {
    return this.maxSize;
  }

  public boolean remove(String paramString)
    throws IOException
  {
    monitorenter;
    while (true)
    {
      Entry localEntry;
      int j;
      try
      {
        checkNotClosed();
        validateKey(paramString);
        localEntry = (Entry)this.lruEntries.get(paramString);
        if (localEntry == null)
          continue;
        Editor localEditor = localEntry.currentEditor;
        if (localEditor == null)
          continue;
        i = 0;
        return i;
        j = 0;
        if (j >= this.valueCount)
          break label146;
        File localFile = localEntry.getCleanFile(j);
        if ((!localFile.delete()) && (localFile.exists()))
          throw new IOException("failed to delete " + localFile);
      }
      finally
      {
        monitorexit;
      }
      this.size -= localEntry.lengths[j];
      localEntry.lengths[j] = 0L;
      j++;
      continue;
      label146: this.redundantOpCount = (1 + this.redundantOpCount);
      this.journalWriter.append("REMOVE " + paramString + '\n');
      this.lruEntries.remove(paramString);
      if (journalRebuildRequired())
        this.executorService.submit(this.cleanupCallable);
      int i = 1;
    }
  }

  public long size()
  {
    monitorenter;
    try
    {
      long l = this.size;
      monitorexit;
      return l;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public final class Editor
  {
    private final DiskLruCache.Entry entry;
    private boolean hasErrors;

    private Editor(DiskLruCache.Entry arg2)
    {
      Object localObject;
      this.entry = localObject;
    }

    public void abort()
      throws IOException
    {
      DiskLruCache.this.completeEdit(this, false);
    }

    public void commit()
      throws IOException
    {
      if (this.hasErrors)
      {
        DiskLruCache.this.completeEdit(this, false);
        DiskLruCache.this.remove(DiskLruCache.Entry.access$1100(this.entry));
      }
      while (true)
      {
        return;
        DiskLruCache.this.completeEdit(this, true);
      }
    }

    public String getString(int paramInt)
      throws IOException
    {
      InputStream localInputStream = newInputStream(paramInt);
      if (localInputStream != null);
      for (String str = DiskLruCache.access$1600(localInputStream); ; str = null)
        return str;
    }

    // ERROR //
    public InputStream newInputStream(int paramInt)
      throws IOException
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 20	com/instagram/android/imagecache/DiskLruCache$Editor:this$0	Lcom/instagram/android/imagecache/DiskLruCache;
      //   4: astore_2
      //   5: aload_2
      //   6: monitorenter
      //   7: aload_0
      //   8: getfield 25	com/instagram/android/imagecache/DiskLruCache$Editor:entry	Lcom/instagram/android/imagecache/DiskLruCache$Entry;
      //   11: invokestatic 66	com/instagram/android/imagecache/DiskLruCache$Entry:access$700	(Lcom/instagram/android/imagecache/DiskLruCache$Entry;)Lcom/instagram/android/imagecache/DiskLruCache$Editor;
      //   14: aload_0
      //   15: if_acmpeq +16 -> 31
      //   18: new 68	java/lang/IllegalStateException
      //   21: dup
      //   22: invokespecial 69	java/lang/IllegalStateException:<init>	()V
      //   25: athrow
      //   26: astore_3
      //   27: aload_2
      //   28: monitorexit
      //   29: aload_3
      //   30: athrow
      //   31: aload_0
      //   32: getfield 25	com/instagram/android/imagecache/DiskLruCache$Editor:entry	Lcom/instagram/android/imagecache/DiskLruCache$Entry;
      //   35: invokestatic 73	com/instagram/android/imagecache/DiskLruCache$Entry:access$600	(Lcom/instagram/android/imagecache/DiskLruCache$Entry;)Z
      //   38: ifne +11 -> 49
      //   41: aconst_null
      //   42: astore 4
      //   44: aload_2
      //   45: monitorexit
      //   46: goto +22 -> 68
      //   49: new 75	java/io/FileInputStream
      //   52: dup
      //   53: aload_0
      //   54: getfield 25	com/instagram/android/imagecache/DiskLruCache$Editor:entry	Lcom/instagram/android/imagecache/DiskLruCache$Entry;
      //   57: iload_1
      //   58: invokevirtual 79	com/instagram/android/imagecache/DiskLruCache$Entry:getCleanFile	(I)Ljava/io/File;
      //   61: invokespecial 82	java/io/FileInputStream:<init>	(Ljava/io/File;)V
      //   64: astore 4
      //   66: aload_2
      //   67: monitorexit
      //   68: aload 4
      //   70: areturn
      //
      // Exception table:
      //   from	to	target	type
      //   7	29	26	finally
      //   31	68	26	finally
    }

    // ERROR //
    public OutputStream newOutputStream(int paramInt)
      throws IOException
    {
      // Byte code:
      //   0: aload_0
      //   1: getfield 20	com/instagram/android/imagecache/DiskLruCache$Editor:this$0	Lcom/instagram/android/imagecache/DiskLruCache;
      //   4: astore_2
      //   5: aload_2
      //   6: monitorenter
      //   7: aload_0
      //   8: getfield 25	com/instagram/android/imagecache/DiskLruCache$Editor:entry	Lcom/instagram/android/imagecache/DiskLruCache$Entry;
      //   11: invokestatic 66	com/instagram/android/imagecache/DiskLruCache$Entry:access$700	(Lcom/instagram/android/imagecache/DiskLruCache$Entry;)Lcom/instagram/android/imagecache/DiskLruCache$Editor;
      //   14: aload_0
      //   15: if_acmpeq +16 -> 31
      //   18: new 68	java/lang/IllegalStateException
      //   21: dup
      //   22: invokespecial 69	java/lang/IllegalStateException:<init>	()V
      //   25: athrow
      //   26: astore_3
      //   27: aload_2
      //   28: monitorexit
      //   29: aload_3
      //   30: athrow
      //   31: new 9	com/instagram/android/imagecache/DiskLruCache$Editor$FaultHidingOutputStream
      //   34: dup
      //   35: aload_0
      //   36: new 86	java/io/FileOutputStream
      //   39: dup
      //   40: aload_0
      //   41: getfield 25	com/instagram/android/imagecache/DiskLruCache$Editor:entry	Lcom/instagram/android/imagecache/DiskLruCache$Entry;
      //   44: iload_1
      //   45: invokevirtual 89	com/instagram/android/imagecache/DiskLruCache$Entry:getDirtyFile	(I)Ljava/io/File;
      //   48: invokespecial 90	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
      //   51: aconst_null
      //   52: invokespecial 93	com/instagram/android/imagecache/DiskLruCache$Editor$FaultHidingOutputStream:<init>	(Lcom/instagram/android/imagecache/DiskLruCache$Editor;Ljava/io/OutputStream;Lcom/instagram/android/imagecache/DiskLruCache$1;)V
      //   55: astore 4
      //   57: aload_2
      //   58: monitorexit
      //   59: aload 4
      //   61: areturn
      //
      // Exception table:
      //   from	to	target	type
      //   7	29	26	finally
      //   31	59	26	finally
    }

    // ERROR //
    public void set(int paramInt, String paramString)
      throws IOException
    {
      // Byte code:
      //   0: aconst_null
      //   1: astore_3
      //   2: new 97	java/io/OutputStreamWriter
      //   5: dup
      //   6: aload_0
      //   7: iload_1
      //   8: invokevirtual 99	com/instagram/android/imagecache/DiskLruCache$Editor:newOutputStream	(I)Ljava/io/OutputStream;
      //   11: invokestatic 103	com/instagram/android/imagecache/DiskLruCache:access$1900	()Ljava/nio/charset/Charset;
      //   14: invokespecial 106	java/io/OutputStreamWriter:<init>	(Ljava/io/OutputStream;Ljava/nio/charset/Charset;)V
      //   17: astore 4
      //   19: aload 4
      //   21: aload_2
      //   22: invokevirtual 112	java/io/Writer:write	(Ljava/lang/String;)V
      //   25: aload 4
      //   27: invokestatic 116	com/instagram/android/imagecache/DiskLruCache:access$1700	(Ljava/io/Closeable;)V
      //   30: return
      //   31: astore 5
      //   33: aload_3
      //   34: invokestatic 116	com/instagram/android/imagecache/DiskLruCache:access$1700	(Ljava/io/Closeable;)V
      //   37: aload 5
      //   39: athrow
      //   40: astore 5
      //   42: aload 4
      //   44: astore_3
      //   45: goto -12 -> 33
      //
      // Exception table:
      //   from	to	target	type
      //   2	19	31	finally
      //   19	25	40	finally
    }

    private class FaultHidingOutputStream extends FilterOutputStream
    {
      private FaultHidingOutputStream(OutputStream arg2)
      {
        super();
      }

      public void close()
      {
        try
        {
          this.out.close();
          return;
        }
        catch (IOException localIOException)
        {
          while (true)
            DiskLruCache.Editor.access$2102(DiskLruCache.Editor.this, true);
        }
      }

      public void flush()
      {
        try
        {
          this.out.flush();
          return;
        }
        catch (IOException localIOException)
        {
          while (true)
            DiskLruCache.Editor.access$2102(DiskLruCache.Editor.this, true);
        }
      }

      public void write(int paramInt)
      {
        try
        {
          this.out.write(paramInt);
          return;
        }
        catch (IOException localIOException)
        {
          while (true)
            DiskLruCache.Editor.access$2102(DiskLruCache.Editor.this, true);
        }
      }

      public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
      {
        try
        {
          this.out.write(paramArrayOfByte, paramInt1, paramInt2);
          return;
        }
        catch (IOException localIOException)
        {
          while (true)
            DiskLruCache.Editor.access$2102(DiskLruCache.Editor.this, true);
        }
      }
    }
  }

  private final class Entry
  {
    private DiskLruCache.Editor currentEditor;
    private final String key;
    private final long[] lengths;
    private boolean readable;
    private long sequenceNumber;

    private Entry(String arg2)
    {
      Object localObject;
      this.key = localObject;
      this.lengths = new long[DiskLruCache.this.valueCount];
    }

    private IOException invalidLengths(String[] paramArrayOfString)
      throws IOException
    {
      throw new IOException("unexpected journal line: " + Arrays.toString(paramArrayOfString));
    }

    private void setLengths(String[] paramArrayOfString)
      throws IOException
    {
      if (paramArrayOfString.length != DiskLruCache.this.valueCount)
        throw invalidLengths(paramArrayOfString);
      int i = 0;
      try
      {
        while (i < paramArrayOfString.length)
        {
          this.lengths[i] = Long.parseLong(paramArrayOfString[i]);
          i++;
        }
      }
      catch (NumberFormatException localNumberFormatException)
      {
        throw invalidLengths(paramArrayOfString);
      }
    }

    public File getCleanFile(int paramInt)
    {
      return new File(DiskLruCache.this.directory, this.key + "." + paramInt);
    }

    public File getDirtyFile(int paramInt)
    {
      return new File(DiskLruCache.this.directory, this.key + "." + paramInt + ".tmp");
    }

    public String getLengths()
      throws IOException
    {
      StringBuilder localStringBuilder = new StringBuilder();
      for (long l : this.lengths)
        localStringBuilder.append(' ').append(l);
      return localStringBuilder.toString();
    }
  }

  public final class Snapshot
    implements Closeable
  {
    private final InputStream[] ins;
    private final String key;
    private final long sequenceNumber;

    private Snapshot(String paramLong, long arg3, InputStream[] arg5)
    {
      this.key = paramLong;
      this.sequenceNumber = ???;
      Object localObject;
      this.ins = localObject;
    }

    public void close()
    {
      InputStream[] arrayOfInputStream = this.ins;
      int i = arrayOfInputStream.length;
      for (int j = 0; j < i; j++)
        DiskLruCache.access$1700(arrayOfInputStream[j]);
    }

    public DiskLruCache.Editor edit()
      throws IOException
    {
      return DiskLruCache.this.edit(this.key, this.sequenceNumber);
    }

    public InputStream getInputStream(int paramInt)
    {
      return this.ins[paramInt];
    }

    public String getString(int paramInt)
      throws IOException
    {
      return DiskLruCache.access$1600(getInputStream(paramInt));
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.imagecache.DiskLruCache
 * JD-Core Version:    0.6.0
 */
package com.instagram.android.support.camera.gallery;

import android.net.Uri;
import com.instagram.android.support.camera.Util;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class ImageListUber
  implements IImageList
{
  private static final String TAG = "ImageListUber";
  private int mLastListIndex;
  private final PriorityQueue<MergeSlot> mQueue;
  private int[] mSkipCounts;
  private long[] mSkipList;
  private int mSkipListSize;
  private final IImageList[] mSubList;

  public ImageListUber(IImageList[] paramArrayOfIImageList, int paramInt)
  {
    this.mSubList = ((IImageList[])paramArrayOfIImageList.clone());
    if (paramInt == 1);
    for (Object localObject = new AscendingComparator(null); ; localObject = new DescendingComparator(null))
    {
      this.mQueue = new PriorityQueue(4, (Comparator)localObject);
      this.mSkipList = new long[16];
      this.mSkipListSize = 0;
      this.mSkipCounts = new int[this.mSubList.length];
      this.mLastListIndex = -1;
      this.mQueue.clear();
      int i = 0;
      int j = this.mSubList.length;
      while (i < j)
      {
        MergeSlot localMergeSlot = new MergeSlot(this.mSubList[i], i);
        if (localMergeSlot.next())
          this.mQueue.add(localMergeSlot);
        i++;
      }
    }
  }

  private void modifySkipCountForDeletedImage(int paramInt)
  {
    int i = 0;
    int j = 0;
    int k = this.mSkipListSize;
    while (true)
    {
      int m;
      if (j < k)
      {
        long l = this.mSkipList[j];
        m = (int)(0xFFFFFFFF & l);
        if (i + m > paramInt)
          this.mSkipList[j] = (l - 1L);
      }
      else
      {
        return;
      }
      i += m;
      j++;
    }
  }

  private MergeSlot nextMergeSlot()
  {
    MergeSlot localMergeSlot = (MergeSlot)this.mQueue.poll();
    if (localMergeSlot == null)
      localMergeSlot = null;
    while (true)
    {
      return localMergeSlot;
      if (localMergeSlot.mListIndex == this.mLastListIndex)
      {
        int j = -1 + this.mSkipListSize;
        long[] arrayOfLong3 = this.mSkipList;
        arrayOfLong3[j] = (1L + arrayOfLong3[j]);
        continue;
      }
      this.mLastListIndex = localMergeSlot.mListIndex;
      if (this.mSkipList.length == this.mSkipListSize)
      {
        long[] arrayOfLong2 = new long[2 * this.mSkipListSize];
        System.arraycopy(this.mSkipList, 0, arrayOfLong2, 0, this.mSkipListSize);
        this.mSkipList = arrayOfLong2;
      }
      long[] arrayOfLong1 = this.mSkipList;
      int i = this.mSkipListSize;
      this.mSkipListSize = (i + 1);
      arrayOfLong1[i] = (1L | this.mLastListIndex << 32);
    }
  }

  private boolean removeImage(IImage paramIImage, int paramInt)
  {
    IImageList localIImageList = paramIImage.getContainer();
    if ((localIImageList != null) && (localIImageList.removeImage(paramIImage)))
      modifySkipCountForDeletedImage(paramInt);
    for (int i = 1; ; i = 0)
      return i;
  }

  public void close()
  {
    int i = 0;
    int j = this.mSubList.length;
    while (i < j)
    {
      this.mSubList[i].close();
      i++;
    }
  }

  public HashMap<String, String> getBucketIds()
  {
    HashMap localHashMap = new HashMap();
    IImageList[] arrayOfIImageList = this.mSubList;
    int i = arrayOfIImageList.length;
    for (int j = 0; j < i; j++)
      localHashMap.putAll(arrayOfIImageList[j].getBucketIds());
    return localHashMap;
  }

  public int getCount()
  {
    int i = 0;
    IImageList[] arrayOfIImageList = this.mSubList;
    int j = arrayOfIImageList.length;
    for (int k = 0; k < j; k++)
      i += arrayOfIImageList[k].getCount();
    return i;
  }

  public IImage getImageAt(int paramInt)
  {
    if ((paramInt < 0) || (paramInt > getCount()))
      throw new IndexOutOfBoundsException("index " + paramInt + " out of range max is " + getCount());
    Arrays.fill(this.mSkipCounts, 0);
    int i = 0;
    int j = 0;
    int k = this.mSkipListSize;
    int m;
    int n;
    IImage localIImage;
    if (j < k)
    {
      long l = this.mSkipList[j];
      m = (int)(0xFFFFFFFF & l);
      n = (int)(l >> 32);
      if (i + m > paramInt)
      {
        int i1 = this.mSkipCounts[n] + (paramInt - i);
        localIImage = this.mSubList[n].getImageAt(i1);
      }
    }
    while (true)
    {
      return localIImage;
      i += m;
      int[] arrayOfInt = this.mSkipCounts;
      arrayOfInt[n] = (m + arrayOfInt[n]);
      j++;
      break;
      Object localObject;
      do
      {
        if (((MergeSlot)localObject).next())
          this.mQueue.add(localObject);
        i++;
        localObject = nextMergeSlot();
        if (localObject != null)
          continue;
        localIImage = null;
        break;
      }
      while (i != paramInt);
      localIImage = ((MergeSlot)localObject).mImage;
      if (!((MergeSlot)localObject).next())
        continue;
      this.mQueue.add(localObject);
    }
  }

  public IImage getImageForUri(Uri paramUri)
  {
    IImageList[] arrayOfIImageList = this.mSubList;
    int i = arrayOfIImageList.length;
    int j = 0;
    IImage localIImage;
    if (j < i)
    {
      localIImage = arrayOfIImageList[j].getImageForUri(paramUri);
      if (localIImage == null);
    }
    while (true)
    {
      return localIImage;
      j++;
      break;
      localIImage = null;
    }
  }

  public int getImageIndex(IImage paramIImage)
  {
    int i = -1;
    monitorenter;
    IImageList localIImageList;
    int j;
    try
    {
      localIImageList = paramIImage.getContainer();
      j = Util.indexOf(this.mSubList, localIImageList);
      if (j == i)
        throw new IllegalArgumentException();
    }
    finally
    {
      monitorexit;
    }
    int k = localIImageList.getImageIndex(paramIImage);
    int m = 0;
    int n = 0;
    int i1 = this.mSkipListSize;
    label138: Object localObject2;
    while (n < i1)
    {
      long l = this.mSkipList[n];
      int i2 = (int)(0xFFFFFFFF & l);
      if ((int)(l >> 32) == j)
      {
        if (k < i2)
        {
          i = m + k;
          monitorexit;
          return i;
        }
        k -= i2;
      }
      m += i2;
      n++;
      continue;
      if (!((MergeSlot)localObject2).next())
        break label203;
      this.mQueue.add(localObject2);
      break label203;
    }
    while (true)
    {
      localObject2 = nextMergeSlot();
      if (localObject2 == null)
        break;
      if (((MergeSlot)localObject2).mImage != paramIImage)
        break label138;
      if (((MergeSlot)localObject2).next())
        this.mQueue.add(localObject2);
      i = m;
      break;
      label203: m++;
    }
  }

  public boolean isEmpty()
  {
    IImageList[] arrayOfIImageList = this.mSubList;
    int i = arrayOfIImageList.length;
    int j = 0;
    if (j < i)
      if (arrayOfIImageList[j].isEmpty());
    for (int k = 0; ; k = 1)
    {
      return k;
      j++;
      break;
    }
  }

  public boolean removeImage(IImage paramIImage)
  {
    return removeImage(paramIImage, getImageIndex(paramIImage));
  }

  public boolean removeImageAt(int paramInt)
  {
    IImage localIImage = getImageAt(paramInt);
    if (localIImage == null);
    for (boolean bool = false; ; bool = removeImage(localIImage, paramInt))
      return bool;
  }

  private static class AscendingComparator
    implements Comparator<ImageListUber.MergeSlot>
  {
    public int compare(ImageListUber.MergeSlot paramMergeSlot1, ImageListUber.MergeSlot paramMergeSlot2)
    {
      int i;
      if (paramMergeSlot1.mDateTaken != paramMergeSlot2.mDateTaken)
        if (paramMergeSlot1.mDateTaken < paramMergeSlot2.mDateTaken)
          i = -1;
      while (true)
      {
        return i;
        i = 1;
        continue;
        i = paramMergeSlot1.mListIndex - paramMergeSlot2.mListIndex;
      }
    }
  }

  private static class DescendingComparator
    implements Comparator<ImageListUber.MergeSlot>
  {
    public int compare(ImageListUber.MergeSlot paramMergeSlot1, ImageListUber.MergeSlot paramMergeSlot2)
    {
      int i;
      if (paramMergeSlot1.mDateTaken != paramMergeSlot2.mDateTaken)
        if (paramMergeSlot1.mDateTaken < paramMergeSlot2.mDateTaken)
          i = 1;
      while (true)
      {
        return i;
        i = -1;
        continue;
        i = paramMergeSlot1.mListIndex - paramMergeSlot2.mListIndex;
      }
    }
  }

  private static class MergeSlot
  {
    long mDateTaken;
    IImage mImage;
    private final IImageList mList;
    int mListIndex;
    private int mOffset = -1;

    public MergeSlot(IImageList paramIImageList, int paramInt)
    {
      this.mList = paramIImageList;
      this.mListIndex = paramInt;
    }

    public boolean next()
    {
      if (this.mOffset >= -1 + this.mList.getCount());
      for (int j = 0; ; j = 1)
      {
        return j;
        IImageList localIImageList = this.mList;
        int i = 1 + this.mOffset;
        this.mOffset = i;
        this.mImage = localIImageList.getImageAt(i);
        this.mDateTaken = this.mImage.getDateTaken();
      }
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.support.camera.gallery.ImageListUber
 * JD-Core Version:    0.6.0
 */
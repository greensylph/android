package com.instagram.android.imagecache;

import android.os.SystemClock;
import android.util.Log;
import java.util.ArrayList;

public class IgTimingLogger
{
  private boolean mDisabled;
  private String mLabel;
  ArrayList<String> mSplitLabels;
  ArrayList<Long> mSplits;
  private String mTag;

  public IgTimingLogger(String paramString1, String paramString2)
  {
    reset(paramString1, paramString2);
  }

  public void addSplit(String paramString)
  {
    long l = SystemClock.elapsedRealtime();
    this.mSplits.add(Long.valueOf(l));
    this.mSplitLabels.add(paramString);
  }

  public void dumpToLog()
  {
    Log.d(this.mTag, this.mLabel + ": begin");
    long l1 = ((Long)this.mSplits.get(0)).longValue();
    long l2 = l1;
    for (int i = 1; i < this.mSplits.size(); i++)
    {
      l2 = ((Long)this.mSplits.get(i)).longValue();
      String str = (String)this.mSplitLabels.get(i);
      long l3 = ((Long)this.mSplits.get(i - 1)).longValue();
      Log.d(this.mTag, this.mLabel + ":      " + (l2 - l3) + " ms, " + str);
    }
    Log.d(this.mTag, this.mLabel + ": end, " + (l2 - l1) + " ms");
  }

  public void reset()
  {
    boolean bool;
    if (!Log.isLoggable(this.mTag, 2))
    {
      bool = true;
      this.mDisabled = bool;
      if (this.mSplits != null)
        break label58;
      this.mSplits = new ArrayList();
      this.mSplitLabels = new ArrayList();
    }
    while (true)
    {
      addSplit(null);
      return;
      bool = false;
      break;
      label58: this.mSplits.clear();
      this.mSplitLabels.clear();
    }
  }

  public void reset(String paramString1, String paramString2)
  {
    this.mTag = paramString1;
    this.mLabel = paramString2;
    reset();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.imagecache.IgTimingLogger
 * JD-Core Version:    0.6.0
 */
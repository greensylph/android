package com.instagram.android.support.camera;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.Iterator;

public class MonitoredActivity extends NoSearchActivity
{
  private final ArrayList<LifeCycleListener> mListeners = new ArrayList();

  public void addLifeCycleListener(LifeCycleListener paramLifeCycleListener)
  {
    if (this.mListeners.contains(paramLifeCycleListener));
    while (true)
    {
      return;
      this.mListeners.add(paramLifeCycleListener);
    }
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Iterator localIterator = this.mListeners.iterator();
    while (localIterator.hasNext())
      ((LifeCycleListener)localIterator.next()).onActivityCreated(this);
  }

  protected void onDestroy()
  {
    super.onDestroy();
    Iterator localIterator = this.mListeners.iterator();
    while (localIterator.hasNext())
      ((LifeCycleListener)localIterator.next()).onActivityDestroyed(this);
  }

  protected void onStart()
  {
    super.onStart();
    Iterator localIterator = this.mListeners.iterator();
    while (localIterator.hasNext())
      ((LifeCycleListener)localIterator.next()).onActivityStarted(this);
  }

  protected void onStop()
  {
    super.onStop();
    Iterator localIterator = this.mListeners.iterator();
    while (localIterator.hasNext())
      ((LifeCycleListener)localIterator.next()).onActivityStopped(this);
  }

  public void removeLifeCycleListener(LifeCycleListener paramLifeCycleListener)
  {
    this.mListeners.remove(paramLifeCycleListener);
  }

  public static class LifeCycleAdapter
    implements MonitoredActivity.LifeCycleListener
  {
    public void onActivityCreated(MonitoredActivity paramMonitoredActivity)
    {
    }

    public void onActivityDestroyed(MonitoredActivity paramMonitoredActivity)
    {
    }

    public void onActivityStarted(MonitoredActivity paramMonitoredActivity)
    {
    }

    public void onActivityStopped(MonitoredActivity paramMonitoredActivity)
    {
    }
  }

  public static abstract interface LifeCycleListener
  {
    public abstract void onActivityCreated(MonitoredActivity paramMonitoredActivity);

    public abstract void onActivityDestroyed(MonitoredActivity paramMonitoredActivity);

    public abstract void onActivityStarted(MonitoredActivity paramMonitoredActivity);

    public abstract void onActivityStopped(MonitoredActivity paramMonitoredActivity);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.support.camera.MonitoredActivity
 * JD-Core Version:    0.6.0
 */
package com.instagram.android.support.camera;

import android.app.ProgressDialog;

class Util$BackgroundJob$1
  implements Runnable
{
  public void run()
  {
    Util.BackgroundJob.access$000(this.this$0).removeLifeCycleListener(this.this$0);
    if (Util.BackgroundJob.access$100(this.this$0).getWindow() != null)
      Util.BackgroundJob.access$100(this.this$0).dismiss();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.support.camera.Util.BackgroundJob.1
 * JD-Core Version:    0.6.0
 */
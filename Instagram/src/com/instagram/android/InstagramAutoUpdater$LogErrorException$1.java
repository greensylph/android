package com.instagram.android;

import android.widget.Toast;

class InstagramAutoUpdater$LogErrorException$1
  implements Runnable
{
  public void run()
  {
    Log.e("InstagramAutoUpdater", this.this$1.mMessage, this.this$1.mException);
    Toast.makeText(this.this$1.this$0.getContext(), this.this$1.mMessage, 0).show();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.InstagramAutoUpdater.LogErrorException.1
 * JD-Core Version:    0.6.0
 */
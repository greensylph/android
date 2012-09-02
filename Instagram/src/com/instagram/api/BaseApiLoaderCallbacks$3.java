package com.instagram.api;

import android.support.v4.app.FragmentManager;
import com.instagram.android.fragment.AlertDialogFragment;

class BaseApiLoaderCallbacks$3
  implements Runnable
{
  public void run()
  {
    AlertDialogFragment.newInstance(this.val$title, this.val$message).show(this.val$fragmentManager, "alertDialog");
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.BaseApiLoaderCallbacks.3
 * JD-Core Version:    0.6.0
 */
package com.instagram.android.activity;

import com.instagram.android.fragment.AlertDialogFragment;

class XAuthActivity$2
  implements Runnable
{
  public void run()
  {
    AlertDialogFragment.newInstance(this.val$errorMessage).show(this.this$0.getSupportFragmentManager(), "alertDialog");
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.activity.XAuthActivity.2
 * JD-Core Version:    0.6.0
 */
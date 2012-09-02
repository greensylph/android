package com.instagram.android.activity;

import android.content.res.Resources;
import android.widget.CheckBox;
import com.instagram.android.Log;
import com.instagram.android.widget.IgProgressDialog;
import com.instagram.twitter.TwitterAccount;
import com.instagram.twitter.TwitterService;
import com.instagram.twitter.TwitterXAuth.Listener;

class TwitterAuthActivity$1
  implements TwitterXAuth.Listener
{
  public void onComplete(TwitterAccount paramTwitterAccount)
  {
    this.val$dialog.dismiss();
    String str1 = TwitterAuthActivity.access$000(this.this$0);
    StringBuilder localStringBuilder = new StringBuilder().append("Twitter login was ");
    String str2;
    if (paramTwitterAccount == null)
    {
      str2 = "unsuccessful!";
      Log.d(str1, str2);
      if (paramTwitterAccount != null)
        break label108;
      TwitterAuthActivity localTwitterAuthActivity = this.this$0;
      Resources localResources = this.this$0.getResources();
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = this.this$0.getServiceName();
      localTwitterAuthActivity.showAlertDialog(localResources.getString(2131231042, arrayOfObject));
    }
    while (true)
    {
      return;
      str2 = "a success.";
      break;
      label108: if (((CheckBox)this.this$0.findViewById(2131493106)).isChecked())
        TwitterService.followInstagram(paramTwitterAccount);
      this.this$0.setResult(-1);
      this.this$0.finish();
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.activity.TwitterAuthActivity.1
 * JD-Core Version:    0.6.0
 */
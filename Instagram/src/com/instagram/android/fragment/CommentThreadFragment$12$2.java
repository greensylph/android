package com.instagram.android.fragment;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import com.instagram.api.request.WebUrlHelper;

class CommentThreadFragment$12$2
  implements DialogInterface.OnClickListener
{
  public void onClick(DialogInterface paramDialogInterface, int paramInt)
  {
    Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse(WebUrlHelper.addAndroidVersionToUrl("http://help.instagram.com/customer/portal/articles/245800-reducing-comment-spam", this.this$1.this$0.getActivity())));
    this.this$1.this$0.startActivity(localIntent);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.CommentThreadFragment.12.2
 * JD-Core Version:    0.6.0
 */
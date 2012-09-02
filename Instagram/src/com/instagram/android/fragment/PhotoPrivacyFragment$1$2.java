package com.instagram.android.fragment;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import com.instagram.android.widget.IgProgressDialog;
import com.instagram.api.request.ChangePrivacyRequest;

class PhotoPrivacyFragment$1$2
  implements DialogInterface.OnClickListener
{
  public void onClick(DialogInterface paramDialogInterface, int paramInt)
  {
    paramDialogInterface.dismiss();
    this.this$1.this$0.mProgressDialog = IgProgressDialog.show(this.this$1.this$0.getActivity(), null, this.this$1.this$0.getString(2131230889), true, true);
    this.this$1.this$0.mProgressDialog.setOnCancelListener(new PhotoPrivacyFragment.1.2.1(this));
    new ChangePrivacyRequest(this.this$1.this$0.getActivity(), this.this$1.this$0.getLoaderManager(), new PhotoPrivacyFragment.ChangePhotoPrivacyCallbacks(this.this$1.this$0, this.this$1.this$0), this.val$willBePrivate).perform();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.PhotoPrivacyFragment.1.2
 * JD-Core Version:    0.6.0
 */
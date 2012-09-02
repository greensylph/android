package com.instagram.api.request;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import com.instagram.android.service.AppContext;

public class UpdateAvatarHelper extends AvatarHelper
{
  public UpdateAvatarHelper(Fragment paramFragment, Bundle paramBundle)
  {
    super(paramFragment, paramBundle);
  }

  private UpdateAvatarRequest makeRequest()
  {
    return new UpdateAvatarRequest(this.mFragment.getActivity(), this.mFragment.getLoaderManager(), new AvatarHelper.UpdateProfilePictureCallbacks(this));
  }

  public CharSequence[] getOptions()
  {
    Context localContext = AppContext.getContext();
    if (this.mOptions == null)
    {
      CharSequence[] arrayOfCharSequence = new CharSequence[5];
      arrayOfCharSequence[0] = localContext.getString(2131230996);
      arrayOfCharSequence[1] = localContext.getString(2131230992);
      arrayOfCharSequence[2] = localContext.getString(2131230993);
      arrayOfCharSequence[3] = localContext.getString(2131230994);
      arrayOfCharSequence[4] = localContext.getString(2131230995);
      this.mOptions = arrayOfCharSequence;
    }
    return this.mOptions;
  }

  protected void loadFacebookProfilePicture()
  {
    makeRequest().performWithType(0);
  }

  protected void loadTwitterProfilePicture()
  {
    makeRequest().performWithType(1);
  }

  protected void loadUriProfilePicture(Uri paramUri)
  {
    UpdateAvatarRequest localUpdateAvatarRequest = makeRequest();
    localUpdateAvatarRequest.setUri(paramUri);
    localUpdateAvatarRequest.performWithType(2);
  }

  protected void performProfileDelete()
  {
    LoaderManager.enableDebugLogging(true);
    makeRequest().performRemove();
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.request.UpdateAvatarHelper
 * JD-Core Version:    0.6.0
 */
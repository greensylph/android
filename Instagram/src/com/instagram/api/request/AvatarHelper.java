package com.instagram.api.request;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.instagram.android.Log;
import com.instagram.android.Preferences;
import com.instagram.android.activity.TwitterAuthActivity;
import com.instagram.android.model.User;
import com.instagram.android.service.AppContext;
import com.instagram.android.widget.IgDialogBuilder;
import com.instagram.api.AbstractApiCallbacks;
import com.instagram.api.ApiResponse;
import com.instagram.facebook.FacebookAccount;
import com.instagram.facebook.FacebookConstants;
import com.instagram.twitter.TwitterAccount;
import com.instagram.util.BuiltInCameraUtil;
import com.instagram.util.FileUtil;
import com.instagram.util.GalleryUtil;
import java.io.File;

public abstract class AvatarHelper
{
  private static final String BUNDLE_TEMP_CAMERA_PHOTO_FILE = "tempCameraPhotoFile";
  private static final String BUNDLE_TEMP_GALLERY_PHOTO_FILE = "tempGalleryPhotoFile";
  private static final String LOG_TAG = "UpdateAvatarHelper";
  public static final String PROGRESS = "progress";
  public static final int REQUEST_BUILT_IN_CAMERA = 4;
  public static final int REQUEST_CROP_PHOTO = 3;
  public static final int REQUEST_PICK_FROM_GALLERY = 2;
  public static final int REQUEST_TWITTER_AUTH = 1;
  protected Fragment mFragment;
  private File mGalleryTempFile;
  private Handler mHandler = new Handler();
  protected CharSequence[] mOptions;
  private File mTempCameraPhotoFile;

  public AvatarHelper(Fragment paramFragment, Bundle paramBundle)
  {
    this.mFragment = paramFragment;
    if (paramBundle != null)
    {
      String str1 = paramBundle.getString("tempCameraPhotoFile");
      if (str1 != null)
        this.mTempCameraPhotoFile = new File(str1);
      String str2 = paramBundle.getString("tempGalleryPhotoFile");
      if (str2 != null)
        this.mGalleryTempFile = new File(str2);
    }
  }

  public CharSequence[] getOptions()
  {
    Context localContext = AppContext.getContext();
    if (this.mOptions == null)
    {
      CharSequence[] arrayOfCharSequence = new CharSequence[4];
      arrayOfCharSequence[0] = localContext.getString(2131230992);
      arrayOfCharSequence[1] = localContext.getString(2131230993);
      arrayOfCharSequence[2] = localContext.getString(2131230994);
      arrayOfCharSequence[3] = localContext.getString(2131230995);
      this.mOptions = arrayOfCharSequence;
    }
    return this.mOptions;
  }

  protected abstract void loadFacebookProfilePicture();

  protected abstract void loadTwitterProfilePicture();

  protected abstract void loadUriProfilePicture(Uri paramUri);

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (paramInt2 == -1)
      switch (paramInt1)
      {
      default:
        FacebookAccount.getFacebook().authorizeCallback(paramInt1, paramInt2, paramIntent);
      case 1:
      case 2:
      case 3:
      case 4:
      }
    while (true)
    {
      return;
      loadTwitterProfilePicture();
      continue;
      GalleryUtil.handleActivityResult(this.mFragment, paramIntent, this.mGalleryTempFile, 3);
      continue;
      loadUriProfilePicture(Uri.parse(paramIntent.getAction()));
      continue;
      BuiltInCameraUtil.handleActivityResult(this.mFragment, paramIntent, this.mTempCameraPhotoFile, 3);
    }
  }

  public void onSaveInstanceState(Bundle paramBundle)
  {
    if (this.mTempCameraPhotoFile != null)
      paramBundle.putString("tempCameraPhotoFile", this.mTempCameraPhotoFile.getPath());
    if (this.mGalleryTempFile != null)
      paramBundle.putString("tempGalleryPhotoFile", this.mGalleryTempFile.getPath());
  }

  protected abstract void performProfileDelete();

  public void showDialog()
  {
    Context localContext = AppContext.getContext();
    new IgDialogBuilder(this.mFragment.getActivity()).setTitle(2131230997).setItems(getOptions(), new DialogInterface.OnClickListener(localContext)
    {
      private boolean matches(int paramInt1, int paramInt2)
      {
        return AvatarHelper.this.getOptions()[paramInt1].equals(this.val$context.getString(paramInt2));
      }

      private void performCamera()
      {
        AvatarHelper.access$202(AvatarHelper.this, BuiltInCameraUtil.getPhotoOutputMediaFile());
        BuiltInCameraUtil.show(AvatarHelper.this.mFragment, 4, AvatarHelper.this.mTempCameraPhotoFile);
      }

      private void performFacebook()
      {
        Facebook localFacebook = FacebookAccount.getFacebook();
        if (localFacebook.isSessionValid())
          AvatarHelper.this.loadFacebookProfilePicture();
        while (true)
        {
          return;
          localFacebook.authorize(AvatarHelper.this.mFragment, FacebookConstants.FACEBOOK_PERMISSIONS, new AvatarHelper.FacebookAuthListener(AvatarHelper.this, null));
        }
      }

      private void performGallery()
      {
        AvatarHelper.access$002(AvatarHelper.this, FileUtil.generateTempFile(AppContext.getContext()));
        GalleryUtil.show(AvatarHelper.this.mFragment, 2, AvatarHelper.this.mGalleryTempFile);
      }

      private void performTwitter()
      {
        if (TwitterAccount.isConfigured(AppContext.getContext()))
          AvatarHelper.this.loadTwitterProfilePicture();
        while (true)
        {
          return;
          TwitterAuthActivity.show(AvatarHelper.this.mFragment, 1);
        }
      }

      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        if (matches(paramInt, 2131230996))
          AvatarHelper.this.performProfileDelete();
        while (true)
        {
          return;
          if (matches(paramInt, 2131230992))
          {
            performCamera();
            continue;
          }
          if (matches(paramInt, 2131230993))
          {
            performGallery();
            continue;
          }
          if (matches(paramInt, 2131230994))
          {
            performFacebook();
            continue;
          }
          if (!matches(paramInt, 2131230995))
            continue;
          performTwitter();
        }
      }
    }).setPositiveButton(2131230921, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        paramDialogInterface.dismiss();
      }
    }).create().show();
  }

  private class FacebookAuthListener
    implements Facebook.DialogListener
  {
    private FacebookAuthListener()
    {
    }

    public void onCancel()
    {
      Log.d("UpdateAvatarHelper", "Facebook onCancel");
    }

    public void onComplete(Bundle paramBundle)
    {
      FacebookAccount.saveCredentials();
      AvatarHelper.this.mHandler.post(new AvatarHelper.FacebookAuthListener.1(this));
    }

    public void onError(DialogError paramDialogError)
    {
      Log.d("UpdateAvatarHelper", "Facebook onError");
    }

    public void onFacebookError(FacebookError paramFacebookError)
    {
      Log.d("UpdateAvatarHelper", "Facebook onFacebookError");
    }
  }

  protected class UpdateProfilePictureCallbacks extends AbstractApiCallbacks<User>
  {
    protected UpdateProfilePictureCallbacks()
    {
    }

    protected void onRequestFail(ApiResponse<User> paramApiResponse)
    {
      new IgDialogBuilder(AvatarHelper.this.mFragment.getActivity()).setTitle(2131230953).setMessage(2131230998).setPositiveButton(2131230955, new AvatarHelper.UpdateProfilePictureCallbacks.1(this)).create().show();
    }

    public void onRequestFinished()
    {
      super.onRequestFinished();
      AvatarHelper.this.mHandler.post(new AvatarHelper.UpdateProfilePictureCallbacks.3(this));
    }

    public void onRequestStart()
    {
      super.onRequestStart();
      AvatarHelper.this.mHandler.post(new AvatarHelper.UpdateProfilePictureCallbacks.2(this));
    }

    protected void onSuccess(User paramUser)
    {
      if (paramUser != null)
        Preferences.getInstance(AvatarHelper.this.mFragment.getActivity()).storeLogin(paramUser);
      while (true)
      {
        return;
        Log.w("UpdateAvatarHelper", "Did not receive user object back");
      }
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.request.AvatarHelper
 * JD-Core Version:    0.6.0
 */
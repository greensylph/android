package com.instagram.api.request;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import com.instagram.android.Log;
import com.instagram.android.fragment.SignUpFragment;
import com.instagram.android.widget.IgDialogBuilder;
import com.instagram.util.BitmapHelper;

public class AddAvatarHelper extends AvatarHelper
{
  private static final String BITMAP_KEY = "AddAvatarHelper.BITMAP_KEY";
  public static final String LOG_TAG = "AddAvatarHelper";
  private Bitmap mBitmap;
  private SignUpFragment mFragment;
  private Handler mHandler = new Handler();

  public AddAvatarHelper(SignUpFragment paramSignUpFragment, Bundle paramBundle)
  {
    super(paramSignUpFragment, paramBundle);
    this.mFragment = paramSignUpFragment;
    if ((paramBundle != null) && (paramBundle.containsKey("AddAvatarHelper.BITMAP_KEY")))
      this.mBitmap = ((Bitmap)paramBundle.getParcelable("AddAvatarHelper.BITMAP_KEY"));
  }

  public Bitmap getBitmap()
  {
    return this.mBitmap;
  }

  protected void loadFacebookProfilePicture()
  {
    new LoadAvatarImage(0, null).execute(new Void[0]);
  }

  protected void loadTwitterProfilePicture()
  {
    new LoadAvatarImage(1, null).execute(new Void[0]);
  }

  protected void loadUriProfilePicture(Uri paramUri)
  {
    new LoadAvatarImage(2, paramUri).execute(new Void[0]);
  }

  public void onDestroy()
  {
    this.mBitmap = null;
    this.mFragment = null;
    this.mFragment = null;
  }

  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    if (this.mBitmap != null)
      paramBundle.putParcelable("AddAvatarHelper.BITMAP_KEY", this.mBitmap);
  }

  public void onViewCreated()
  {
    if (this.mBitmap != null)
      this.mFragment.getImageview().setImageBitmap(this.mBitmap);
  }

  protected void performProfileDelete()
  {
  }

  public class LoadAvatarImage extends AsyncTask<Void, Void, Bitmap>
  {
    public static final String PROGRESS_DIALOG = "progressDialog";
    private final int mTypeUri;
    private Uri mUri;

    public LoadAvatarImage(int paramUri, Uri arg3)
    {
      this.mTypeUri = paramUri;
      Object localObject;
      this.mUri = localObject;
    }

    private void showErrorDialog()
    {
      new IgDialogBuilder(AddAvatarHelper.this.mFragment.getActivity()).setTitle(2131230953).setMessage(2131230999).setPositiveButton(2131230955, new AddAvatarHelper.LoadAvatarImage.1(this)).create().show();
    }

    protected Bitmap doInBackground(Void[] paramArrayOfVoid)
    {
      try
      {
        localObject = UpdateAvatarRequest.getBitmapToSend(this.mTypeUri, this.mUri);
        Bitmap localBitmap = BitmapHelper.squarifyIfNeeded((Bitmap)localObject);
        if (localObject != localBitmap)
        {
          ((Bitmap)localObject).recycle();
          localObject = localBitmap;
        }
        return localObject;
      }
      catch (Exception localException)
      {
        while (true)
        {
          Log.d("AddAvatarHelper", "An error occurred fetching your image");
          Object localObject = null;
        }
      }
    }

    protected void onPostExecute(Bitmap paramBitmap)
    {
      if (AddAvatarHelper.this.mFragment != null)
      {
        if (paramBitmap == null)
          break label43;
        AddAvatarHelper.access$102(AddAvatarHelper.this, paramBitmap);
        AddAvatarHelper.this.mFragment.getImageview().setImageBitmap(paramBitmap);
      }
      while (true)
      {
        super.onPostExecute(paramBitmap);
        return;
        label43: showErrorDialog();
      }
    }

    protected void onPreExecute()
    {
      if (AddAvatarHelper.this.mFragment != null);
      super.onPreExecute();
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.request.AddAvatarHelper
 * JD-Core Version:    0.6.0
 */
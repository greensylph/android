package com.instagram.android.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ToggleButton;
import com.instagram.android.Log;
import com.instagram.android.model.User;
import com.instagram.android.model.User.PrivacyStatus;
import com.instagram.android.service.AuthHelper;
import com.instagram.android.widget.IgDialogBuilder;
import com.instagram.android.widget.IgProgressDialog;
import com.instagram.api.AbstractApiCallbacks;
import com.instagram.api.ApiResponse;

public class PhotoPrivacyFragment extends Fragment
  implements ActionBarConfigurer.ActionBarConfigurerFactory
{
  protected static final String TAG = "PhotoPrivacyFragment";
  private Handler handler = new Handler();
  IgProgressDialog mProgressDialog;
  ToggleButton mToggleButton;

  private void initViews()
  {
    ToggleButton localToggleButton = (ToggleButton)getActivity().findViewById(2131492954);
    this.mToggleButton = localToggleButton;
    if (AuthHelper.getInstance().getCurrentUser().getPrivacyStatus() == User.PrivacyStatus.PrivacyStatusPrivate);
    for (boolean bool = true; ; bool = false)
    {
      localToggleButton.setChecked(bool);
      localToggleButton.setOnClickListener(new View.OnClickListener(localToggleButton)
      {
        public void onClick(View paramView)
        {
          boolean bool = this.val$toggleButton.isChecked();
          IgDialogBuilder localIgDialogBuilder = new IgDialogBuilder(PhotoPrivacyFragment.this.getActivity());
          PhotoPrivacyFragment localPhotoPrivacyFragment = PhotoPrivacyFragment.this;
          if (bool);
          for (int i = 2131231021; ; i = 2131231020)
          {
            localIgDialogBuilder.setMessage(localPhotoPrivacyFragment.getString(i)).setPositiveButton(2131230978, new PhotoPrivacyFragment.1.2(this, bool)).setNeutralButton(2131230921, new PhotoPrivacyFragment.1.1(this, bool)).create().show();
            return;
          }
        }
      });
      return;
    }
  }

  public void dismissProgressDialog()
  {
    if (this.mProgressDialog != null)
      this.mProgressDialog.dismiss();
  }

  public void flipToggleButton()
  {
    ToggleButton localToggleButton = this.mToggleButton;
    if (!this.mToggleButton.isChecked());
    for (boolean bool = true; ; bool = false)
    {
      localToggleButton.setChecked(bool);
      return;
    }
  }

  public ActionBarConfigurer getActionBarConfigurer()
  {
    return new ActionBarConfigurer()
    {
      public View customTitleView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup)
      {
        return null;
      }

      public String getTitle()
      {
        return PhotoPrivacyFragment.this.getString(2131231017);
      }

      public boolean isLoading()
      {
        return false;
      }

      public boolean showBackButton()
      {
        if (PhotoPrivacyFragment.this.getFragmentManager().getBackStackEntryCount() > 0);
        for (int i = 1; ; i = 0)
          return i;
      }

      public boolean showRefreshButton()
      {
        return false;
      }
    };
  }

  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    initViews();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903074, paramViewGroup, false);
  }

  public void onDestroyView()
  {
    super.onDestroyView();
  }

  private final class ChangePhotoPrivacyCallbacks extends AbstractApiCallbacks<Boolean>
  {
    PhotoPrivacyFragment mFragment;

    public ChangePhotoPrivacyCallbacks(PhotoPrivacyFragment arg2)
    {
      Object localObject;
      this.mFragment = localObject;
    }

    protected void onRequestFail(ApiResponse<Boolean> paramApiResponse)
    {
      this.mFragment.flipToggleButton();
      this.mFragment.dismissProgressDialog();
      super.onRequestFail(paramApiResponse);
    }

    protected void onSuccess(Boolean paramBoolean)
    {
      Log.d("PhotoPrivacyFragment", "User is now private? " + paramBoolean);
      User localUser = AuthHelper.getInstance().getCurrentUser();
      if (paramBoolean.booleanValue());
      for (User.PrivacyStatus localPrivacyStatus = User.PrivacyStatus.PrivacyStatusPrivate; ; localPrivacyStatus = User.PrivacyStatus.PrivacyStatusPublic)
      {
        localUser.setPrivacyStatus(localPrivacyStatus);
        this.mFragment.dismissProgressDialog();
        PhotoPrivacyFragment.this.handler.post(new PhotoPrivacyFragment.ChangePhotoPrivacyCallbacks.1(this));
        return;
      }
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.PhotoPrivacyFragment
 * JD-Core Version:    0.6.0
 */
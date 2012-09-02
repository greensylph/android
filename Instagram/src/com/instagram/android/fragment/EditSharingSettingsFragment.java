package com.instagram.android.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.instagram.android.activity.FoursquareAuthActivity;
import com.instagram.android.activity.TumblrAuthActivity;
import com.instagram.android.activity.TwitterAuthActivity;
import com.instagram.android.widget.IgDialogBuilder;
import com.instagram.facebook.FacebookAccount;
import com.instagram.facebook.FacebookConstants;

public class EditSharingSettingsFragment extends Fragment
  implements ActionBarConfigurer.ActionBarConfigurerFactory
{
  private static final int REQUEST_AUTH_FOURSQUARE = 1;
  private static final int REQUEST_AUTH_TUMBLR = 2;
  private static final int REQUEST_AUTH_TWITTER;

  private void configureRow(Account paramAccount)
  {
    View localView = getView().findViewById(paramAccount.getResId());
    CheckBox localCheckBox = (CheckBox)localView.findViewById(2131492941);
    localView.setOnClickListener(null);
    localCheckBox.setOnCheckedChangeListener(null);
    localCheckBox.setChecked(paramAccount.isConnected(getActivity()));
    1 local1 = new View.OnClickListener(paramAccount, localCheckBox)
    {
      public void onClick(View paramView)
      {
        EditSharingSettingsFragment.this.handleRowClick(this.val$account, this.val$checkBox.isChecked());
      }
    };
    localView.setOnClickListener(local1);
    localCheckBox.setOnClickListener(local1);
    localCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
    {
      public void onCheckedChanged(CompoundButton paramCompoundButton, boolean paramBoolean)
      {
        if (!paramBoolean);
        for (boolean bool = true; ; bool = false)
        {
          paramCompoundButton.setChecked(bool);
          return;
        }
      }
    });
  }

  private void connectAccount(Account paramAccount)
  {
    switch (7.$SwitchMap$com$instagram$android$fragment$EditSharingSettingsFragment$Account[paramAccount.ordinal()])
    {
    default:
    case 1:
    case 2:
    case 3:
    case 4:
    }
    while (true)
    {
      return;
      FacebookAccount.getFacebook().authorize(this, FacebookConstants.FACEBOOK_PERMISSIONS, new FacebookAuthListener(null));
      continue;
      TwitterAuthActivity.show(this, 0);
      continue;
      FoursquareAuthActivity.show(this, 1);
      continue;
      TumblrAuthActivity.show(this, 2);
    }
  }

  private String getRowTitle(Account paramAccount)
  {
    return ((TextView)getView().findViewById(paramAccount.getResId()).findViewById(2131492942)).getText().toString();
  }

  private void handleRowClick(Account paramAccount, boolean paramBoolean)
  {
    if (paramBoolean)
      unlinkAccount(paramAccount, true);
    while (true)
    {
      return;
      connectAccount(paramAccount);
    }
  }

  private void showUnlinkPrompt(Account paramAccount)
  {
    IgDialogBuilder localIgDialogBuilder = new IgDialogBuilder(getActivity());
    String str = getString(2131231054);
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = getRowTitle(paramAccount);
    localIgDialogBuilder.setTitle(String.format(str, arrayOfObject)).setNegativeButton(2131230921, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        paramDialogInterface.dismiss();
      }
    }).setPositiveButton(2131231055, new DialogInterface.OnClickListener(paramAccount)
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        EditSharingSettingsFragment.this.unlinkAccount(this.val$account, false);
        paramDialogInterface.dismiss();
      }
    }).create().show();
  }

  private void unlinkAccount(Account paramAccount, boolean paramBoolean)
  {
    if (paramBoolean)
      showUnlinkPrompt(paramAccount);
    while (true)
    {
      return;
      if (paramAccount == Account.Facebook)
      {
        new AsyncTask()
        {
          protected Void doInBackground(Void[] paramArrayOfVoid)
          {
            EditSharingSettingsFragment.Account.Facebook.unlink(EditSharingSettingsFragment.this.getActivity());
            return null;
          }

          protected void onPostExecute(Void paramVoid)
          {
            EditSharingSettingsFragment.this.configureRow(EditSharingSettingsFragment.Account.Facebook);
          }
        }
        .execute((Void[])null);
        continue;
      }
      paramAccount.unlink(getActivity());
      configureRow(paramAccount);
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
        return EditSharingSettingsFragment.this.getString(2131230969);
      }

      public boolean isLoading()
      {
        return false;
      }

      public boolean showBackButton()
      {
        return true;
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
    configureRow(Account.Facebook);
    configureRow(Account.Twitter);
    configureRow(Account.Foursquare);
    configureRow(Account.Tumblr);
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (paramInt2 != -1)
      return;
    Account localAccount = null;
    switch (paramInt1)
    {
    default:
      FacebookAccount.getFacebook().authorizeCallback(paramInt1, paramInt2, paramIntent);
    case 1:
    case 0:
    case 2:
    }
    while (localAccount != null)
    {
      configureRow(localAccount);
      break;
      localAccount = Account.Foursquare;
      continue;
      localAccount = Account.Twitter;
      continue;
      localAccount = Account.Tumblr;
    }
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903068, paramViewGroup, false);
  }

  private static abstract enum Account
  {
    static
    {
      Foursquare = new EditSharingSettingsFragment.Account.3("Foursquare", 2);
      Tumblr = new EditSharingSettingsFragment.Account.4("Tumblr", 3);
      Account[] arrayOfAccount = new Account[4];
      arrayOfAccount[0] = Facebook;
      arrayOfAccount[1] = Twitter;
      arrayOfAccount[2] = Foursquare;
      arrayOfAccount[3] = Tumblr;
      $VALUES = arrayOfAccount;
    }

    public abstract String getName(Context paramContext);

    public abstract int getResId();

    public abstract boolean isConnected(Context paramContext);

    public abstract void unlink(Context paramContext);
  }

  private class FacebookAuthListener
    implements Facebook.DialogListener
  {
    private FacebookAuthListener()
    {
    }

    public void onCancel()
    {
    }

    public void onComplete(Bundle paramBundle)
    {
      FacebookAccount.saveCredentials();
      EditSharingSettingsFragment.this.configureRow(EditSharingSettingsFragment.Account.Facebook);
    }

    public void onError(DialogError paramDialogError)
    {
      Toast.makeText(EditSharingSettingsFragment.this.getActivity(), paramDialogError.getLocalizedMessage(), 0);
    }

    public void onFacebookError(FacebookError paramFacebookError)
    {
      Toast.makeText(EditSharingSettingsFragment.this.getActivity(), paramFacebookError.getLocalizedMessage(), 0);
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.EditSharingSettingsFragment
 * JD-Core Version:    0.6.0
 */
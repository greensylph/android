package com.instagram.android.fragment;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.instagram.android.Preferences;
import com.instagram.android.activity.MainTabActivity;
import com.instagram.android.model.User;
import com.instagram.android.receiver.C2DMReceiver;
import com.instagram.android.service.AuthHelper;
import com.instagram.api.AbstractApiCallbacks;
import com.instagram.api.request.LoginRequest;
import com.instagram.api.request.WebUrlHelper;
import com.instagram.util.ApplicationUuidHelper;
import com.instagram.util.StringUtil;

public class LoginFragment extends Fragment
  implements ActionBarConfigurer.ActionBarConfigurerFactory
{
  public static final String TAG = "LoginFragment";
  private Handler mHandler = new Handler();
  private LoginRequest mLoginRequest;

  private void doLogin()
  {
    this.mLoginRequest.perform(getUsername(), getPassword(), "android-" + ApplicationUuidHelper.id(getActivity()), ApplicationUuidHelper.generateOldStyleUuid(getActivity()));
  }

  private String getPassword()
  {
    return ((EditText)getActivity().findViewById(2131492905)).getText().toString();
  }

  private String getUsername()
  {
    return ((EditText)getActivity().findViewById(2131492904)).getText().toString();
  }

  private void hideKeyboard()
  {
    ((InputMethodManager)getActivity().getSystemService("input_method")).hideSoftInputFromWindow(getView().getWindowToken(), 0);
  }

  private void showKeyboard()
  {
    ((InputMethodManager)getActivity().getSystemService("input_method")).toggleSoftInput(2, 1);
  }

  private void validate()
  {
    if ((StringUtil.isNullOrEmpty(getPassword())) || (StringUtil.isNullOrEmpty(getUsername())))
      getActivity().findViewById(2131492949).setEnabled(false);
    while (true)
    {
      return;
      getActivity().findViewById(2131492949).setEnabled(true);
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
        return LoginFragment.this.getResources().getString(2131230863);
      }

      public boolean isLoading()
      {
        return false;
      }

      public boolean showBackButton()
      {
        if (LoginFragment.this.getFragmentManager().getBackStackEntryCount() > 0);
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
    super.onCreate(paramBundle);
    this.mLoginRequest = new LoginRequest(getActivity(), getLoaderManager(), new LoginCallbacks(null));
    getActivity().findViewById(2131492949).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        LoginFragment.this.doLogin();
      }
    });
    getActivity().findViewById(2131492950).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse(WebUrlHelper.expandPath("/accounts/password/reset/", false)));
        LoginFragment.this.startActivity(localIntent);
      }
    });
    getActivity().findViewById(2131492904).setOnFocusChangeListener(new View.OnFocusChangeListener()
    {
      public void onFocusChange(View paramView, boolean paramBoolean)
      {
        if (paramBoolean)
          LoginFragment.this.getActivity().getWindow().setSoftInputMode(5);
      }
    });
    getActivity().findViewById(2131492904).requestFocus();
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(2130903072, paramViewGroup, false);
    FormValidator localFormValidator = new FormValidator(null);
    ((EditText)localView.findViewById(2131492904)).addTextChangedListener(localFormValidator);
    ((EditText)localView.findViewById(2131492905)).addTextChangedListener(localFormValidator);
    ((EditText)localView.findViewById(2131492905)).setOnEditorActionListener(new TextView.OnEditorActionListener()
    {
      public boolean onEditorAction(TextView paramTextView, int paramInt, KeyEvent paramKeyEvent)
      {
        if (paramInt == 6)
          if (LoginFragment.this.getActivity().findViewById(2131492949).isEnabled())
            LoginFragment.this.doLogin();
        for (int i = 0; ; i = 1)
          return i;
      }
    });
    return localView;
  }

  public void onResume()
  {
    super.onResume();
    validate();
  }

  public void onStop()
  {
    hideKeyboard();
    super.onStop();
  }

  private class FormValidator
    implements TextWatcher
  {
    private FormValidator()
    {
    }

    public void afterTextChanged(Editable paramEditable)
    {
      LoginFragment.this.validate();
    }

    public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
    {
    }

    public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
    {
    }
  }

  public static class LoggingInDialogFragment extends ProgressDialogFragment
  {
    protected String getProgressMessage()
    {
      return getString(2131230983);
    }
  }

  private class LoginCallbacks extends AbstractApiCallbacks<User>
  {
    public static final String PROGRESS_DIALOG = "progressDialog";

    private LoginCallbacks()
    {
    }

    public void onRequestFinished()
    {
      DialogFragment localDialogFragment = (DialogFragment)LoginFragment.this.getFragmentManager().findFragmentByTag("progressDialog");
      LoginFragment.this.mHandler.post(new LoginFragment.LoginCallbacks.1(this, localDialogFragment));
      super.onRequestFinished();
    }

    public void onRequestStart()
    {
      new LoginFragment.LoggingInDialogFragment().show(LoginFragment.this.getFragmentManager(), "progressDialog");
      super.onRequestStart();
    }

    protected void onSuccess(User paramUser)
    {
      AuthHelper.getInstance().saveCurrentUser(paramUser);
      Preferences.getInstance(LoginFragment.this.getActivity()).storeLogin(paramUser);
      C2DMReceiver.refreshAppC2DMRegistrationState(LoginFragment.this.getActivity());
      Intent localIntent = new Intent(LoginFragment.this.getActivity(), MainTabActivity.class);
      localIntent.setFlags(67108864);
      LoginFragment.this.startActivity(localIntent);
      LoginFragment.this.getActivity().finish();
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.LoginFragment
 * JD-Core Version:    0.6.0
 */
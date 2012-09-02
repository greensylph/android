package com.instagram.android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.instagram.android.Log;
import com.instagram.android.Preferences;
import com.instagram.android.model.User;
import com.instagram.android.service.AuthHelper;
import com.instagram.api.AbstractApiCallbacks;
import com.instagram.api.ApiResponse;
import com.instagram.api.BaseApiLoaderCallbacks;
import com.instagram.api.request.AddAvatarHelper;
import com.instagram.api.request.CreateAccountRequest;
import com.instagram.api.request.CreateAccountRequest.Params;
import com.instagram.util.ApplicationUuidHelper;
import com.instagram.util.StringUtil;

public class SignUpFragment extends Fragment
  implements ActionBarConfigurer.ActionBarConfigurerFactory
{
  protected static final String TAG = "SignUpFragment";
  private Handler handler = new Handler();
  private AddAvatarHelper mAddAvatarHelper;
  private CreateAccountRequest mCreateAccountRequest;
  private EditText mEditTextPassword;
  private Handler mHandler = new Handler();

  private String getEmail()
  {
    return ((EditText)getActivity().findViewById(2131492963)).getText().toString();
  }

  private String getPassword()
  {
    return this.mEditTextPassword.getText().toString();
  }

  private String getPhone()
  {
    return ((EditText)getActivity().findViewById(2131492964)).getText().toString();
  }

  private String getUsername()
  {
    return ((EditText)getActivity().findViewById(2131492904)).getText().toString();
  }

  private void hideKeyboard()
  {
    ((InputMethodManager)getActivity().getSystemService("input_method")).hideSoftInputFromWindow(getView().getWindowToken(), 0);
  }

  private void initViews()
  {
    getActivity().findViewById(2131492969).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        CreateAccountRequest.Params localParams = new CreateAccountRequest.Params();
        localParams.email = SignUpFragment.this.getEmail();
        localParams.username = SignUpFragment.this.getUsername();
        localParams.password = SignUpFragment.this.getPassword();
        localParams.phone = SignUpFragment.this.getPhone();
        localParams.profilePic = SignUpFragment.this.mAddAvatarHelper.getBitmap();
        localParams.guid = ApplicationUuidHelper.id(SignUpFragment.this.getActivity());
        localParams.deviceId = ("android-" + ApplicationUuidHelper.id(SignUpFragment.this.getActivity()));
        SignUpFragment.this.mCreateAccountRequest.perform(localParams);
      }
    });
    getActivity().findViewById(2131492965).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramView)
      {
        SignUpFragment.this.mAddAvatarHelper.showDialog();
      }
    });
  }

  private void validate()
  {
    if ((StringUtil.isNullOrEmpty(getEmail())) || (StringUtil.isNullOrEmpty(getPassword())) || (StringUtil.isNullOrEmpty(getUsername())))
      getActivity().findViewById(2131492969).setEnabled(false);
    while (true)
    {
      return;
      getActivity().findViewById(2131492969).setEnabled(true);
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
        return SignUpFragment.this.getString(2131230871);
      }

      public boolean isLoading()
      {
        return false;
      }

      public boolean showBackButton()
      {
        if (SignUpFragment.this.getFragmentManager().getBackStackEntryCount() > 0);
        for (int i = 1; ; i = 0)
          return i;
      }

      public boolean showRefreshButton()
      {
        return false;
      }
    };
  }

  public ImageView getImageview()
  {
    return (ImageView)getActivity().findViewById(2131492967);
  }

  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    initViews();
    this.mCreateAccountRequest = new CreateAccountRequest(getActivity(), getLoaderManager(), new CreateAccountCallbacks(null));
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    this.mAddAvatarHelper.onActivityResult(paramInt1, paramInt2, paramIntent);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mAddAvatarHelper = new AddAvatarHelper(this, paramBundle);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(2130903079, paramViewGroup, false);
    this.mEditTextPassword = ((EditText)localView.findViewById(2131492905));
    FormValidator localFormValidator = new FormValidator(null);
    ((EditText)localView.findViewById(2131492963)).addTextChangedListener(localFormValidator);
    ((EditText)localView.findViewById(2131492905)).addTextChangedListener(localFormValidator);
    ((EditText)localView.findViewById(2131492904)).addTextChangedListener(localFormValidator);
    TextView localTextView = (TextView)localView.findViewById(2131492968);
    localTextView.setMovementMethod(new LinkMovementMethod());
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Html.fromHtml("&lt;a href=&quot;http://instagr.am/legal/terms/&quot;&gt;" + getString(2131231034) + "&lt;/a&gt");
    localTextView.setText(Html.fromHtml(getString(2131231053, arrayOfObject)));
    return localView;
  }

  public void onDestroyView()
  {
    this.mAddAvatarHelper.onDestroy();
    super.onDestroyView();
  }

  public void onResume()
  {
    super.onResume();
    validate();
  }

  public void onSaveInstanceState(Bundle paramBundle)
  {
    this.mAddAvatarHelper.onSaveInstanceState(paramBundle);
    super.onSaveInstanceState(paramBundle);
  }

  public void onStop()
  {
    hideKeyboard();
    super.onStop();
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.mAddAvatarHelper.onViewCreated();
  }

  private final class CreateAccountCallbacks extends AbstractApiCallbacks<User>
  {
    private static final String PROGRESS_DIALOG = "ProgressDialog";

    private CreateAccountCallbacks()
    {
    }

    protected void onRequestFail(ApiResponse<User> paramApiResponse)
    {
      BaseApiLoaderCallbacks.handleRequestServerErrorMessage(paramApiResponse.getErrorTitle(), paramApiResponse.getErrorMessage());
    }

    public void onRequestFinished()
    {
      DialogFragment localDialogFragment = (DialogFragment)SignUpFragment.this.getFragmentManager().findFragmentByTag("ProgressDialog");
      SignUpFragment.this.mHandler.post(new SignUpFragment.CreateAccountCallbacks.2(this, localDialogFragment));
      super.onRequestFinished();
    }

    public void onRequestStart()
    {
      new SignUpFragment.SigningUpDialogFragment().show(SignUpFragment.this.getFragmentManager(), "ProgressDialog");
      super.onRequestStart();
    }

    protected void onSuccess(User paramUser)
    {
      Log.d("SignUpFragment", "User signed up: " + paramUser.getUsername());
      AuthHelper.getInstance().saveCurrentUser(paramUser);
      Preferences.getInstance(SignUpFragment.this.getActivity()).storeLogin(paramUser);
      SignUpFragment.this.handler.post(new SignUpFragment.CreateAccountCallbacks.1(this));
    }
  }

  private class FormValidator
    implements TextWatcher
  {
    private FormValidator()
    {
    }

    public void afterTextChanged(Editable paramEditable)
    {
      SignUpFragment.this.validate();
    }

    public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
    {
    }

    public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
    {
    }
  }

  public static class SigningUpDialogFragment extends ProgressDialogFragment
  {
    protected String getProgressMessage()
    {
      return getString(2131231028);
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.SignUpFragment
 * JD-Core Version:    0.6.0
 */
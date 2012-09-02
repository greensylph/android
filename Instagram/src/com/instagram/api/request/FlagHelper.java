package com.instagram.api.request;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.widget.EditText;
import com.instagram.android.fragment.ProgressDialogFragment;
import com.instagram.android.model.User;
import com.instagram.android.service.AuthHelper;
import com.instagram.android.widget.IgDialogBuilder;
import com.instagram.api.AbstractApiCallbacks;
import com.instagram.api.ApiResponse;

public class FlagHelper
{
  private AbstractApiCallbacks<Void> mApiCallbacks = new AbstractApiCallbacks()
  {
    private void dismissProgressDialog()
    {
      DialogFragment localDialogFragment = (DialogFragment)FlagHelper.this.mFragmentManager.findFragmentByTag("progressDialog");
      if (localDialogFragment != null)
        localDialogFragment.dismiss();
    }

    private void showProgressDialog()
    {
      ProgressDialogFragment.newInstance().show(FlagHelper.this.mFragmentManager, "progressDialog");
    }

    protected void onRequestFail(ApiResponse<Void> paramApiResponse)
    {
      FlagHelper.this.mHandler.post(new FlagHelper.1.2(this));
    }

    public void onRequestFinished()
    {
      FlagHelper.this.mHandler.post(new FlagHelper.1.4(this));
      super.onRequestFinished();
    }

    public void onRequestStart()
    {
      FlagHelper.this.mHandler.post(new FlagHelper.1.3(this));
      super.onRequestStart();
    }

    protected void onSuccess(Void paramVoid)
    {
      if (FlagHelper.this.mFlagType == FlagHelper.FlagType.Media);
      for (int i = 2131230951; ; i = 2131230952)
      {
        FlagHelper.this.mHandler.post(new FlagHelper.1.1(this, i));
        return;
      }
    }
  };
  private final Context mContext;
  private FlagReason mCurrentReason;
  private FlagType mFlagType;
  private FragmentManager mFragmentManager;
  private Handler mHandler = new Handler();
  private String mItemId;
  private LoaderManager mLoaderManager;
  private CharSequence[] mReasons;

  public FlagHelper(Context paramContext, FlagType paramFlagType, String paramString, LoaderManager paramLoaderManager, FragmentManager paramFragmentManager)
  {
    this.mContext = paramContext;
    this.mFlagType = paramFlagType;
    this.mItemId = paramString;
    this.mFragmentManager = paramFragmentManager;
    this.mLoaderManager = paramLoaderManager;
  }

  private DialogInterface.OnClickListener getReasonClickListener()
  {
    return new ReasonClickListener(null);
  }

  private CharSequence[] getReasons()
  {
    if (this.mReasons == null)
    {
      if (this.mFlagType != FlagType.User)
        break label92;
      CharSequence[] arrayOfCharSequence3 = new CharSequence[5];
      arrayOfCharSequence3[0] = this.mContext.getString(2131230945);
      arrayOfCharSequence3[1] = this.mContext.getString(2131230946);
      arrayOfCharSequence3[2] = this.mContext.getString(2131230947);
      arrayOfCharSequence3[3] = this.mContext.getString(2131230948);
      arrayOfCharSequence3[4] = this.mContext.getString(2131230949);
      this.mReasons = arrayOfCharSequence3;
    }
    while (true)
    {
      return this.mReasons;
      label92: if (AuthHelper.getInstance().getCurrentUser().isIsStaff())
      {
        CharSequence[] arrayOfCharSequence2 = new CharSequence[4];
        arrayOfCharSequence2[0] = this.mContext.getString(2131230945);
        arrayOfCharSequence2[1] = this.mContext.getString(2131230946);
        arrayOfCharSequence2[2] = this.mContext.getString(2131230949);
        arrayOfCharSequence2[3] = this.mContext.getString(2131230950);
        this.mReasons = arrayOfCharSequence2;
        continue;
      }
      CharSequence[] arrayOfCharSequence1 = new CharSequence[3];
      arrayOfCharSequence1[0] = this.mContext.getString(2131230945);
      arrayOfCharSequence1[1] = this.mContext.getString(2131230946);
      arrayOfCharSequence1[2] = this.mContext.getString(2131230949);
      this.mReasons = arrayOfCharSequence1;
    }
  }

  private void sendFlagNotice()
  {
    sendFlagNoticeWithDetails(null);
  }

  private void sendFlagNoticeWithDetails(String paramString)
  {
    new FlagRequest(this.mContext, this.mLoaderManager, this.mApiCallbacks).perform(this.mFlagType, this.mItemId, this.mCurrentReason, paramString);
  }

  private void showInputAlertWithPrompt(int paramInt)
  {
    EditText localEditText = new EditText(this.mContext);
    3 local3 = new DialogInterface.OnClickListener(localEditText)
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        paramDialogInterface.dismiss();
        if (paramInt == -1)
          FlagHelper.this.sendFlagNoticeWithDetails(this.val$input.getText().toString());
      }
    };
    new IgDialogBuilder(this.mContext).setTitle(paramInt).setPositiveButton(2131230959, local3).setNegativeButton(2131230921, local3).setView(localEditText).create().show();
  }

  public void showFlaggingOptions()
  {
    CharSequence[] arrayOfCharSequence = getReasons();
    new IgDialogBuilder(this.mContext).setTitle(2131230944).setItems(arrayOfCharSequence, getReasonClickListener()).setPositiveButton(2131230921, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        paramDialogInterface.cancel();
      }
    }).create().show();
  }

  static enum FlagReason
  {
    static
    {
      Copyright = new FlagReason("Copyright", 1);
      Abuse = new FlagReason("Abuse", 2);
      Spam = new FlagReason("Spam", 3);
      Terms = new FlagReason("Terms", 4);
      RemoveFromPopular = new FlagReason("RemoveFromPopular", 5);
      FlagReason[] arrayOfFlagReason = new FlagReason[6];
      arrayOfFlagReason[0] = Nudity;
      arrayOfFlagReason[1] = Copyright;
      arrayOfFlagReason[2] = Abuse;
      arrayOfFlagReason[3] = Spam;
      arrayOfFlagReason[4] = Terms;
      arrayOfFlagReason[5] = RemoveFromPopular;
      $VALUES = arrayOfFlagReason;
    }

    public int getReasonCode(FlagReason paramFlagReason)
    {
      int i;
      switch (FlagHelper.4.$SwitchMap$com$instagram$api$request$FlagHelper$FlagReason[paramFlagReason.ordinal()])
      {
      default:
        throw new RuntimeException("No flag with that reason code");
      case 1:
        i = 0;
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      }
      while (true)
      {
        return i;
        i = 1;
        continue;
        i = 2;
        continue;
        i = 3;
        continue;
        i = 4;
        continue;
        i = 99;
      }
    }
  }

  public static enum FlagType
  {
    static
    {
      FlagType[] arrayOfFlagType = new FlagType[2];
      arrayOfFlagType[0] = Media;
      arrayOfFlagType[1] = User;
      $VALUES = arrayOfFlagType;
    }
  }

  private class ReasonClickListener
    implements DialogInterface.OnClickListener
  {
    private ReasonClickListener()
    {
    }

    public void onClick(DialogInterface paramDialogInterface, int paramInt)
    {
      paramDialogInterface.dismiss();
      CharSequence localCharSequence = FlagHelper.this.mReasons[paramInt];
      if (localCharSequence.equals(FlagHelper.this.mContext.getString(2131230945)))
      {
        FlagHelper.access$802(FlagHelper.this, FlagHelper.FlagReason.Nudity);
        FlagHelper.this.sendFlagNotice();
      }
      while (true)
      {
        return;
        if (localCharSequence.equals(FlagHelper.this.mContext.getString(2131230946)))
        {
          FlagHelper.access$802(FlagHelper.this, FlagHelper.FlagReason.Copyright);
          FlagHelper localFlagHelper = FlagHelper.this;
          if (FlagHelper.this.mFlagType == FlagHelper.FlagType.Media);
          for (int i = 2131230957; ; i = 2131230958)
          {
            localFlagHelper.showInputAlertWithPrompt(i);
            break;
          }
        }
        if (localCharSequence.equals(FlagHelper.this.mContext.getString(2131230947)))
        {
          FlagHelper.access$802(FlagHelper.this, FlagHelper.FlagReason.Abuse);
          FlagHelper.this.sendFlagNotice();
          continue;
        }
        if (localCharSequence.equals(FlagHelper.this.mContext.getString(2131230948)))
        {
          FlagHelper.access$802(FlagHelper.this, FlagHelper.FlagReason.Spam);
          FlagHelper.this.sendFlagNotice();
          continue;
        }
        if (localCharSequence.equals(FlagHelper.this.mContext.getString(2131230949)))
        {
          FlagHelper.access$802(FlagHelper.this, FlagHelper.FlagReason.Terms);
          FlagHelper.this.sendFlagNotice();
          continue;
        }
        if (!localCharSequence.equals(FlagHelper.this.mContext.getString(2131230950)))
          continue;
        FlagHelper.access$802(FlagHelper.this, FlagHelper.FlagReason.RemoveFromPopular);
        FlagHelper.this.sendFlagNotice();
      }
    }
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.api.request.FlagHelper
 * JD-Core Version:    0.6.0
 */
package com.instagram.android.service;

import android.app.Activity;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import com.instagram.android.listener.UserLinkClickListener;
import com.instagram.api.request.UpdateAvatarHelper;

public class ClickManager
{
  private static ClickManager mInstance;
  private Activity mCurrentActivity;
  private FragmentManager mFragmentManager;
  private Handler mHandler;
  private UpdateAvatarHelper mUpdateAvatarHelper;
  private UserLinkClickListener mUserLinkClickListener;

  public static ClickManager getInstance()
  {
    if (mInstance == null)
      mInstance = new ClickManager();
    return mInstance;
  }

  public Activity getCurrentActivity()
  {
    return this.mCurrentActivity;
  }

  public FragmentManager getCurrentFragmentManager()
  {
    return this.mFragmentManager;
  }

  public Handler getHandler()
  {
    return this.mHandler;
  }

  public UpdateAvatarHelper getUpdateAvatarHelper()
  {
    return this.mUpdateAvatarHelper;
  }

  public UserLinkClickListener getUserLinkClickListener()
  {
    return new ClickManager.1(this);
  }

  public void setCurrentActivity(Activity paramActivity)
  {
    this.mCurrentActivity = paramActivity;
  }

  public void setFragmentManager(FragmentManager paramFragmentManager)
  {
    this.mFragmentManager = paramFragmentManager;
  }

  public void setHandler(Handler paramHandler)
  {
    this.mHandler = paramHandler;
  }

  public void setUpdateAvatarHelper(UpdateAvatarHelper paramUpdateAvatarHelper)
  {
    this.mUpdateAvatarHelper = paramUpdateAvatarHelper;
  }

  public void setUserLinkClickListener(UserLinkClickListener paramUserLinkClickListener)
  {
    this.mUserLinkClickListener = paramUserLinkClickListener;
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.service.ClickManager
 * JD-Core Version:    0.6.0
 */
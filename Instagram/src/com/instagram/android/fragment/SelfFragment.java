package com.instagram.android.fragment;

import android.content.Intent;
import android.os.Bundle;
import com.instagram.android.service.ClickManager;
import com.instagram.api.request.UpdateAvatarHelper;

public class SelfFragment extends UserDetailFragment
{
  private UpdateAvatarHelper mUpdateAvatarHelper;

  public ActionBarConfigurer getActionBarConfigurer()
  {
    return new SelfFragment.1(this);
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    this.mUpdateAvatarHelper.onActivityResult(paramInt1, paramInt2, paramIntent);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mUpdateAvatarHelper = new UpdateAvatarHelper(this, paramBundle);
  }

  public void onPause()
  {
    super.onPause();
    ClickManager.getInstance().setUpdateAvatarHelper(null);
  }

  public void onResume()
  {
    super.onResume();
    ClickManager.getInstance().setUpdateAvatarHelper(this.mUpdateAvatarHelper);
  }

  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    this.mUpdateAvatarHelper.onSaveInstanceState(paramBundle);
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.SelfFragment
 * JD-Core Version:    0.6.0
 */
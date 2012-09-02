package com.instagram.android.fragment;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.instagram.android.service.ActionBarService;

public class ActionBarFragment extends Fragment
{
  private BroadcastReceiver actionBarReceiver = new ActionBarFragment.1(this);

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903040, paramViewGroup, false);
  }

  public void onPause()
  {
    super.onPause();
    LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(this.actionBarReceiver);
  }

  public void onResume()
  {
    super.onResume();
    LocalBroadcastManager.getInstance(getActivity()).registerReceiver(this.actionBarReceiver, new IntentFilter("com.instagram.android.service.action_bar_updated"));
    ActionBarService.getInstance(getActivity()).configureActionBar(getView().findViewById(2131492874));
  }
}

/* Location:           C:\Temp\android\apktool\Instagram_1.1.0\
 * Qualified Name:     com.instagram.android.fragment.ActionBarFragment
 * JD-Core Version:    0.6.0
 */